#!/bin/sh
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


if [ -f ${XDIR}/app.sh.env ]
then
    . ${XDIR}/app.sh.env
fi

if [ -z ${XSUITE_HOME} ] 
then
  echo "XSUITE_HOME is not defined."
  exit
fi

logtstamp=`date '+%Y%m%d.%H%M%S'`

logDir=$XSUITE_HOME/xsuite/edi/processed_log
logFile=${logDir}/xpedex_rms_send_$logtstamp.log

cd $XDIR/edi
outdir=outbound_xpedx_rms
indir=inbound_xpedx_rms

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

TRADING_IDS=2369

java -classpath "${TJCPATH}" \
 -Duser.home=${XSUITE_HOME}/xsuite/edi/tools/oboe \
 -Dconf="${XSUITE_HOME}/xsuite/app.properties" \
 -DtradingPartnerIdList=${TRADING_IDS} -Dtype=850 \
com.cleanwise.service.apps.dataexchange.OutboundTranslate >> ${logFile} 2>&1



cd $XDIR/edi

sh ${XFTP_DIR}/cwii_send.sh ${outdir} XPEDX true>> ${logFile}
sh ${XFTP_DIR}/xpedx_RMS_send.sh ${outdir} ${indir} sendOrder sXMLDocument  company ""
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
