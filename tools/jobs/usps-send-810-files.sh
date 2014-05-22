
lockfile=/tmp/$0.lockfile
if [ -f $lockfile ]
then
  echo "error lock file $lockfile in place."
  exit
fi

echo $$ > $lockfile

. /apps/xadmin3/.profile


. $XDIR/app.sh.env


cd $XDIR
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

outdir=outbound_usps_810

if [ ! -d ${outdir} ]
then
  mkdir ${outdir}
fi

cd $outdir

##--------------------------START LOOP----------------------------
# for ID in 100 89417 168880
for ID in 100 89417 168880
do

logtstamp=`date '+%Y%m%d.%H%M%S'`
tstamp=`date '+%Y%m%d%H%M%S'`

logDir=$XSUITE_HOME/xsuite/edi/processed_log
logFile=${logDir}/usps_send_810_$logtstamp.log
echo "Processing Bus Entity Id: $ID" > $logFile




### START 810 generation 
outFile=inv810_cleanwise.dat.${tstamp}.txt
encFile=inv810_cleanwise.dat.${tstamp}

java -classpath "${TJCPATH}" \
 -Duser.home=${XSUITE_HOME}/xsuite/edi/tools/oboe \
 -Dconf="${XSUITE_HOME}/xsuite/app.properties" \
 -Dofile=$outFile -DbusEntityIdList=${ID} -Dtype=810 \
com.cleanwise.service.apps.dataexchange.OutboundTranslate >> ${logFile} 2>&1

# If there's anything in the file, move it to the outbound directory.
if [ -s $outFile ] 
then
      sh ${XFTP_DIR}/cwii_send.sh $outFile USPS >> ${logFile}
      # encrypt the file to USPS
      # this file needs to be delivered to USPS.
      sh -x ../util/usps-encrypt-file.sh $outFile $encFile >> ${logFile}
      sh ${XFTP_DIR}/usps_send.sh $encFile >> ${logFile}
      mv $outFile $XDIR/edi/sent
      mv $encFile $XDIR/edi/sent                                      
else
  echo "No 810 generated for account ${USPS_ERP_NUM}"
  rm -f $outFile
fi

### END 810 generation 

maillist=jobreporting@cleanwise.com
mail ${maillist} <<EOmail
subject: $0 done

---
`cat $logFile`

EOmail

done

##--------------------------END LOOP----------------------------
rm $lockfile

