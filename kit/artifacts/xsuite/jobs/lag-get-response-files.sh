
. /apps/ixtendx/.profile

. ../app.sh.env

if [ -z ${XSUITE_HOME} ] 
then
  echo "XSUITE_HOME is not defined."
  exit
fi

# before continuing, make sure the lagasse site is up
expectOne=` sh ${XFTP_DIR}/lag_check.sh | grep DONE | wc -l`
if [ $expectOne -lt 1 ]
then
   echo "Exception, lagasse does not seem to be operational."
   sh ${XFTP_DIR}/lag_check.sh
fi

tstamp=`date '+%Y%m%d'`
logtstamp=`date '+%Y%m%d.%H%M%S'`

logDir=$XSUITE_HOME/xsuite/edi/processed_log
logFile=${logDir}/lag_get_$logtstamp.log


cd $XDIR/edi
indir=inbound_lag_$tstamp
if [ -d $indir ]
then
  echo "directory $indir exists."
  echo "cannot continue"
  exit
fi

# make a temporary directory to store downloaded files.
mkdir $indir
cd $indir

# get the file(s) from lagasse using ftp.
echo " === " >> ${logFile}
sh ${XFTP_DIR}/lag_get.sh >> ${logFile}

# parse each of the files downloaded.
flist=`ls -1`
for frcvd in $flist
do
  if [ -f ${XSUITE_HOME}/xsuite/edi/processed/$frcvd ] 
  then
    echo "file $frcvd is already in ${XSUITE_HOME}/xsuite/edi/processed/$frcvd"
  else
  outFile=clw997_$frcvd
  # parse the file received
  java -classpath "${TJCPATH}" \
   -Duser.home=${XSUITE_HOME}/xsuite/edi/tools/oboe \
   -Dconf="${XSUITE_HOME}/xsuite/app.properties" \
   -Difile=$frcvd -Dofile=$outFile \
  com.cleanwise.service.apps.dataexchange.InboundTranslate >> ${logFile} 2>&1

  # archive the file
  sh ${XFTP_DIR}/cwii_to_archrcv.sh $frcvd >> ${logFile}
  # move the file to the processed area.
  mv $frcvd $XDIR/edi/processed

  if [ -f $outFile ] && [ -s $outFile ]
  then
    # this file needs to be delivered to lagasse.
    sh ${XFTP_DIR}/lag_send.sh $outFile >> ${logFile}
    sh ${XFTP_DIR}/cwii_send.sh $outFile >> ${logFile}
    mv $outFile $XDIR/edi/sent
  fi

  if [ -f $outFile ]
  then
    echo "produced an empty file $outFile" >> ${logFile}
    rm ${outFile}
  fi

  fi
done

# we should be done processing files
fl=`ls -1`
if [ "x" = "x$fl" ]
then
  # no files left, rm this directory
  cd ..
  rm -fr $indir
else
  echo "Exception: files $fl were not processed in $indir" >> $logFile
fi

maillist=jobreporting@cleanwise.com 
mail ${maillist} <<EOF
subject: $0 done

---
`cat $logFile`

EOF

