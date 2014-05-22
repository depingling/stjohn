
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


outdir=outbound_usps

if [ ! -d ${outdir} ]
then
  mkdir ${outdir}
fi

cd $outdir

##--------------------------START LOOP----------------------------
# 113992 - remove the IMPACT card account until
# we determine whether or not they need the EDI version of the
# credit card orders, durval, 2006-8-28

for ID in 100 89417 168880
do

logtstamp=`date '+%Y%m%d.%H%M%S'`
tstamp=`date '+%Y%m%d%H%M%S'`

logDir=$XSUITE_HOME/xsuite/edi/processed_log
logFile=${logDir}/usps_send_${ID}_${logtstamp}.log

echo "Processing Bus Entity Id: $ID" > $logFile





### START 855 generation 
outFile=ack855_cleanwise.dat.${tstamp}.txt
encFile=ack855_cleanwise.dat.${tstamp}

java -classpath "${TJCPATH}" \
 -Duser.home=${XSUITE_HOME}/xsuite/edi/tools/oboe \
 -Dconf="${XSUITE_HOME}/xsuite/app.properties" \
 -Dofile=$outFile -DbusEntityIdList=${ID} -Dtype=855 \
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
  echo "No 855 generated for account ${ID}"
  rm -f $outFile
fi

### END 855 generation 

sleep 10

### START 856 generation 
outFile=sn856_cleanwise.dat.${tstamp}.txt
encFile=sn856_cleanwise.dat.${tstamp}

java -classpath "${TJCPATH}" \
 -Duser.home=${XSUITE_HOME}/xsuite/edi/tools/oboe \
 -Dconf="${XSUITE_HOME}/xsuite/app.properties" \
 -Dofile=$outFile -DbusEntityIdList=${ID} -Dtype=856 \
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
  echo "No 856 generated for account ${ID}"
  rm -f $outFile
fi

### END 856 generation 

maillist=jobreporting@cleanwise.com
mail ${maillist} <<EOF
subject: $0 done

---
`cat $logFile`

EOF

done
##--------------------------END LOOP----------------------------

rm $lockfile

