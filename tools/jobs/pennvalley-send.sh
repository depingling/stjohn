echo $0

lockfile=/tmp/$0.lockfile
if [ -f $lockfile ]
then
  echo "error lock file $lockfile in place."
  exit
fi

echo $$ > $lockfile

. ../app.sh.env


if [ -z ${XSUITE_HOME} ] 
then
  echo "XSUITE_HOME is not defined."
  exit
fi

logtstamp=`date '+%Y%m%d.%H%M%S'`

logDir=$XSUITE_HOME/xsuite/edi/processed_log
logFile=${logDir}/pennvalley_send_$logtstamp.log

cd $XDIR/edi
outdir=outbound_pennvalley
indir=inbound_pennvalley

if [ ! -d ${outdir} ]
then
  mkdir ${outdir}
fi

if [ ! -d ${indir} ]
then
  mkdir ${indir}
fi


cd $outdir

tstamp=`date '+%Y%m%d%H%M%S'`  


TRADING_IDS=2279

java -classpath "${TJCPATH}" \
 -Duser.home=${XSUITE_HOME}/xsuite/edi/tools/oboe \
 -Dconf="${XSUITE_HOME}/xsuite/app.properties" \
 -DtradingPartnerIdList=${TRADING_IDS} -Dtype=850 \
com.cleanwise.service.apps.dataexchange.OutboundTranslate >>$logFile

echo asdf >> ${logFile} 2>&1

cd $XDIR/edi


sh ${XFTP_DIR}/cwii_send.sh ${outdir} PENNVALLEY true >>$logFile 
sh ${XFTP_DIR}/pennvalley_send.sh ${outdir} true >>$logFile

mv ${outdir}/* $XDIR/edi/sent


### END 850 generation 

rm $lockfile

maillist=jobreporting@cleanwise.com
mail ${maillist} <<EOF
subject: $0 processed

--- $0 

`cat $logFile`

EOF           
