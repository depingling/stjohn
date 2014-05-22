
lockfile=/tmp/$0.lockfile
if [ -f $lockfile ]
then
  echo "error lock file $lockfile in place."
  exit
fi

echo $$ > $lockfile

. /apps/ixtendx/.profile

cd $XDIR
. app.sh.env

if [ -z ${XSUITE_HOME} ] 
then
  echo "XSUITE_HOME is not defined."
  exit
fi

expectOne=` sh ${XFTP_DIR}/ez_check.sh | grep DONE | wc -l`
if [ $expectOne -lt 1 ]
then
   echo "Exception, EZLink does not seem to be operational."
   rm $lockfile
   exit
fi

logtstamp=`date '+%Y%m%d.%H%M%S'`

logDir=$XSUITE_HOME/xsuite/edi/processed_log
logFile=${logDir}/jcp_send_$logtstamp.log


cd $XDIR/edi
outdir=outbound_jcp

if [ ! -d ${outdir} ]
then
  mkdir ${outdir}
fi

cd $outdir

ERP_NUM=10053

tstamp=`date '+%Y%m%d%H%M%S'`  
### START 810 generation 
outFile=inv810_cleanwise.dat.${tstamp}.txt

java -classpath "${TJCPATH}" \
 -Duser.home=${XSUITE_HOME}/xsuite/edi/tools/oboe \
 -Dconf="${XSUITE_HOME}/xsuite/app.properties" \
 -Dofile=$outFile -Derpnum=${ERP_NUM} -Dtype=810 \
com.cleanwise.service.apps.dataexchange.OutboundTranslate >> ${logFile} 2>&1

# If there's anything in the file, move it to the outbound directory.
if [ -s $outFile ] 
then
      sh ${XFTP_DIR}/cwii_send.sh $outFile EZ >> ${logFile}
      sh ${XFTP_DIR}/ez_send.sh $outFile >> ${logFile}
      mv $outFile $XDIR/edi/sent
else
  echo "No 810 generated for account ${ERP_NUM}"
  rm -f $outFile
fi

### END 810 generation 

rm $lockfile


maillist=jobreporting@cleanwise.com
mail ${maillist} <<EOF
subject: $0 ${outFile} processed

--- $0

`cat $logFile`

EOF                  

