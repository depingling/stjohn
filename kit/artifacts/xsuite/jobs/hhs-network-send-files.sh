echo $0

lockfile=/tmp/$0.lockfile
if [ -f $lockfile ]
then
  echo "error lock file $lockfile in place."
  exit
fi

echo $$ > $lockfile

if [ -f  /apps/xadmin3/.profile ]
then
        . /apps/xadmin3/.profile
fi

cd $XDIR
. app.sh.env

if [ -z ${XSUITE_HOME} ] 
then
  echo "XSUITE_HOME is not defined."
  exit
fi

if [ -z ${XFTP_DIR} ] 
then
  echo "ERROR XFTP_DIR is not defined."
  XFTP_DIR=/apps/ixtendx/ftpx
  echo "using ${XFTP_DIR} for XFTP_DIR"
fi

expectOne=` sh ${XFTP_DIR}/hhs_network_check.sh | grep DONE | wc -l`
if [ $expectOne -lt 1 ]
then
   echo "Exception, Network Link does not seem to be operational."
   rm $lockfile
   exit
fi

logtstamp=`date '+%Y%m%d.%H%M%S'`

logDir=$XSUITE_HOME/xsuite/edi/processed_log
logFile=${logDir}/hhs_network_send_$logtstamp.log


cd $XDIR/edi
outdir=outbound_hhs_network

if [ ! -d ${outdir} ]
then
  mkdir ${outdir}
fi


cd $outdir


##
# generate 850s for the following distributors
#

TRADING_IDS=1409,2829,2439

tstamp=`date '+%Y%m%d%H%M%S'`  
### START generation 

java -classpath "${TJCPATH}" \
 -Duser.home=${XSUITE_HOME}/xsuite/edi/tools/oboe \
 -Dconf="${XSUITE_HOME}/xsuite/app.properties" \
 -DtradingPartnerIdList=${TRADING_IDS} -Dtype=850 \
com.cleanwise.service.apps.dataexchange.OutboundTranslate >> ${logFile} 2>&1

cd $XDIR/edi

sh ${XFTP_DIR}/cwii_send.sh ${outdir} Network true>> ${logFile}
sh ${XFTP_DIR}/hhs_network_send.sh ${outdir} true>> ${logFile}
mv ${outdir}/* $XDIR/edi/sent
rm -f ${outdir}/*

### END generation 

rm $lockfile

maillist=jobreporting@cleanwise.com
mail ${maillist} <<EOF
subject: $0 processed

--- $0 

`cat $logFile`

EOF           

