echo $0

lockfile=/tmp/$0.lockfile
if [ -f $lockfile ]
then
  echo "Error lock file $lockfile in place."
  exit
fi

echo $$ > $lockfile

. ../app.sh.env

if [ -z ${XSUITE_HOME} ] 
then
  echo "error XSUITE_HOME is not defined."
  exit
fi

expectOne=` sh ${XFTP_DIR}/gca_waxie_check.sh | grep DONE | wc -l`
if [ $expectOne -lt 1 ]
then
   echo "Exception, Waxie does not seem to be operational."
   rm $lockfile
   exit
fi

logtstamp=`date '+%Y%m%d.%H%M%S'`

logDir=$XSUITE_HOME/xsuite/edi/processed_log
logFile=${logDir}/gca_waxie_send_$logtstamp.log





cd $XDIR/edi
outdir=gca_waxie_outbound

if [ ! -d ${outdir} ]
then
  mkdir ${outdir}
fi


cd $outdir

TRADING_IDS=1829

tstamp=`date '+%Y%m%d%H%M%S'`  
### START generation 

java -classpath "${TJCPATH}" \
 -Duser.home=${XSUITE_HOME}/xsuite/edi/tools/oboe \
 -Dconf="${XSUITE_HOME}/xsuite/app.properties" \
 -DtradingPartnerIdList=${TRADING_IDS} -Dtype=850 \
com.cleanwise.service.apps.dataexchange.OutboundTranslate >> ${logFile} 2>&1

cd $XDIR/edi

sh ${XFTP_DIR}/cwii_send.sh ${outdir} GCA_WAXIE true>> ${logFile}
sh ${XFTP_DIR}/gca_waxie_send.sh ${outdir} true>> ${logFile}
mv ${outdir}/* $XDIR/edi/sent
rm -f ${outdir}/*

### END generation 

echo "removing lock file $lockfile" >> ${logFile} 2>&1

rm $lockfile
echo "DONE removing lock file $lockfile" >> ${logFile} 2>&1


maillist=jobreporting@cleanwise.com
mail ${maillist} <<EOF
subject: $0 processed

--- $0 

`cat $logFile`

EOF 
