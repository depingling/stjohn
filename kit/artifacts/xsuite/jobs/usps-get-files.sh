
lockfile=/tmp/$0.lockfile
if [ -f $lockfile ]
then
  echo "error lock file $lockfile in place."
  exit
fi

echo $$ > $lockfile

. /apps/xadmin3/.profile

cd $XDIR
. app.sh.env

cd $XDIR/edi

if [ -z ${XSUITE_HOME} ] 
then
  echo "XSUITE_HOME is not defined."
  exit
fi

# before continuing, make sure the USPS site is up
expectOne=` sh ${XFTP_DIR}/usps_check.sh | grep DONE | wc -l`
if [ $expectOne -lt 1 ]
then
   echo "Exception, USPS does not seem to be operational."
   rm $lockfile
   exit
fi

expectOne=` sh util/usps-crypto-test.sh | grep DONE | wc -l`
if [ $expectOne -lt 1 ]
then
   echo "Exception, encryption setup check failed!"
   rm $lockfile
   exit
fi

logtstamp=`date '+%Y%m%d.%H%M%S'`

logDir=$XSUITE_HOME/xsuite/edi/processed_log
logFile=${logDir}/usps_get_$logtstamp.log

if [ `uname` = "CYGWIN_NT-5.0" ]
then PSEP="\;"
else PSEP=":";
fi



indir=inbound_usps

if [ ! -d $indir ]
then
  mkdir $indir
fi


cd $indir

# get the file(s) from USPS using ftp.
echo " === " > ${logFile}
sh ${XFTP_DIR}/usps_get.sh >> ${logFile}

# parse each of the files downloaded.
flist=`ls -1 | grep -v decrypted`
for frcvd in $flist
do
  sh ../util/usps-decrypt-file.sh $frcvd
  decFile=${frcvd}.decrypted
  if [ -f ${XSUITE_HOME}/xsuite/edi/processed/$decFile ] 
  then
    echo "file $decFile is already in ${XSUITE_HOME}/xsuite/edi/processed"
    mv $frcvd   $XDIR/edi/processed/$frcvd.$logtstamp.duplicate
    mv $decFile $XDIR/edi/processed/$decFile.$logtstamp.duplicate
  else
    tstamp=`date '+%Y%m%d%H%M%S'`
    outFile="fa997_cleanwise.dat.${tstamp}.txt" 
    # archive the decrypted file
    sh ${XFTP_DIR}/cwii_to_archrcv.sh $decFile USPS >> ${logFile}



    case $decFile in
	058-*) 
            sh $XDIR/edi/util/usps-process-058-file.sh $decFile
                ;;
	*) 
            # parse the file received
            java -classpath "${TJCPATH}" \
             -Duser.home=${XSUITE_HOME}/xsuite/edi/tools/oboe \
             -Dconf="${XSUITE_HOME}/xsuite/app.properties" \
             -Difile=$decFile -Dofile=$outFile \
            com.cleanwise.service.apps.dataexchange.InboundTranslate >> ${logFile} 2>&1
                ;;
    esac


    # move the files to the processed area.
    mv $frcvd   $XDIR/edi/processed
    mv $decFile $XDIR/edi/processed

    if [ -f $outFile ] && [ -s $outFile ]
    then
      sh ${XFTP_DIR}/cwii_send.sh $outFile USPS >> ${logFile}
      # encrypt the file to USPS
      # this file needs to be delivered to USPS.
      encFile=fa997_cleanwise.dat.${tstamp} 
      sh ../util/usps-encrypt-file.sh $outFile $encFile
      sh ${XFTP_DIR}/usps_send.sh $encFile >> ${logFile}
      mv $outFile $XDIR/edi/sent
      mv $encFile $XDIR/edi/sent
    fi

    if [ -f $outFile ]
    then
      echo "produced an empty file $outFile" >> ${logFile}
      rm ${outFile}
    fi

  fi
done

rm $lockfile

# we should be done processing files
fl=`ls -1`
if [ "x" = "x$fl" ]
then
  echo "All done, no files left."
else
  echo "Exception: files $fl were not processed in $indir" >> $logFile
fi

maillist=jobreporting@cleanwise.com 
mail ${maillist} <<EOF
subject: $0 done

---
`cat $logFile`

EOF

