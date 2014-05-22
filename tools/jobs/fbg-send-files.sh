
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
logFile=${logDir}/fbg_send_$logtstamp.log

echo "cd $XDIR/dataexchange"
cd $XDIR/dataexchange
outdirorder=fbg_outbound_order
outdirinvoice=fbg_outbound_invoice
archdir=$XDIR/dataexchange/sent

if [ ! -d $outdirorder ]
then
  mkdir $outdirorder
fi

if [ ! -d $outdirinvoice ]
then
  mkdir $outdirinvoice
fi

if [ ! -d $archdir ]
then
  mkdir $archdir
fi

cd $outdirorder

echo "logFile=${logFile}"

### START order generation 
echo "START order generation."
TRADING_IDS=3279

java -classpath "${TJCPATH}" \
 -Duser.home=${XSUITE_HOME}/xsuite/edi/tools/oboe \
 -Dconf="${XSUITE_HOME}/xsuite/app.properties" \
 -DtradingPartnerIdList=${TRADING_IDS} -Dtype=TORDER_REPORT_XML_OUT \
com.cleanwise.service.apps.dataexchange.OutboundTranslate >> ${logFile} 2>&1

cd ..
cd $outdirinvoice

### START invoice generation 
echo "START invoice generation."
java -classpath "${TJCPATH}" \
 -Duser.home=${XSUITE_HOME}/xsuite/edi/tools/oboe \
 -Dconf="${XSUITE_HOME}/xsuite/app.properties" \
 -DtradingPartnerIdList=${TRADING_IDS} -Dtype=810 \
com.cleanwise.service.apps.dataexchange.OutboundTranslate >> ${logFile} 2>&1

cd ..

	echo sh ${XFTP_DIR}/fbg_send.sh
	sh ${XFTP_DIR}/fbg_send.sh $outdirorder ProcessOrder  >> ${logFile}
	sh ${XFTP_DIR}/cwii_send.sh $outdirorder FBG true >> ${logFile}
        sh ${XFTP_DIR}/fbg_send.sh $outdirinvoice ProcessInvoice  >> ${logFile}
        sh ${XFTP_DIR}/cwii_send.sh $outdirinvoice FBG true >> ${logFile}
	echo mv $outdir/*.* $archdir
	mv $outdirorder/*.* $archdir
        mv $outdirinvoice/*.* $archdir

rm $lockfile

maillist=jobreporting@cleanwise.com
mail ${maillist} <<EOF
subject: $0 ${outdir} processed

--- $0 


`cat $logFile`

EOF           
