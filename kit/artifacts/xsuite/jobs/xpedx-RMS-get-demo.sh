echo $0

lockfile=/tmp/$0.lockfile
if [ -f $lockfile ]
then
  echo "lock file $lockfile in place."
  #exit
fi

if [ -f /apps/ixtendx/.profile ]
then
    . /apps/ixtendx/.profile
fi

if [ -f ${HOME}/.profile ]
then
    . ${HOME}/.profile
fi

if [ -f ${XDIR}/app.sh.env ]
then
    . ${XDIR}/app.sh.env
fi

if [ -f ../app.sh.env ]
then
    . ../app.sh.env
fi

if [ -z ${XSUITE_HOME} ] 
then
  echo "XSUITE_HOME is not defined."
  exit
fi

logtstamp=`date '+%Y%m%d.%H%M%S'`

logDir=$XSUITE_HOME/xsuite/edi/processed_log
logFile=${logDir}/ez_send_$logtstamp.log

cd $XDIR/dataexchange/inbound
indir=XPEDXRMS.COM

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

TRADING_IDS=762

java -classpath "${TJCPATH}" \
 -Duser.home=${XSUITE_HOME}/xsuite/edi/tools/oboe \
 -Dconf="${XSUITE_HOME}/xsuite/app.properties" \
 -DtradingPartnerIdList=${TRADING_IDS} -Dtype=850 \
com.cleanwise.service.apps.dataexchange.OutboundTranslate 


echo test >> ${logFile} 2>&1


cd $XDIR/edi

sh ${XFTP_DIR}/xpedx_RMS_send_demo.sh ${outdir} ${indir} sendOrder sXMLDocument  company ""
mv ${outdir}/* $XDIR/edi/sent
rm -f ${outdir}/*

### END 810 generation 

rm $lockfile

maillist=jobreporting@cleanwise.com
mail ${maillist} <<EOF
subject: $0 processed

--- $0 

`cat $logFile`

EOF           
