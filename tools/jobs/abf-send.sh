
lockfile=/tmp/$0.lockfile
if [ -f $lockfile ]
then
  echo "error lock file $lockfile in place."
  exit
fi

echo $$ > $lockfile

if [ -f /apps/ixtendx/.profile ]
then
	. /apps/ixtendx/.profile
fi
if [ -f /apps/xadmin3/.profile ]
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


logtstamp=`date '+%Y%m%d.%H%M%S'`

logDir=$XSUITE_HOME/xsuite/edi/processed_log
logFile=${logDir}/abf_send_$logtstamp.log





cd $XDIR/edi
outdir=abf_outbound
archdir=$XDIR/edi/sent

if [ ! -d $outdir ]
then
  mkdir $outdir
fi

if [ ! -d $archdir ]
then
  mkdir $archdir
fi

cd $outdir

TRADING_IDS=2939

tstamp=`date '+%Y%m%d%H%M%S'`  
### START invoice generation 

java -classpath "${TJCPATH}" \
 -Duser.home=${XSUITE_HOME}/xsuite/edi/tools/oboe \
 -Dconf="${XSUITE_HOME}/xsuite/app.properties" \
 -DtradingPartnerIdList=${TRADING_IDS} -Dtype=850 \
com.cleanwise.service.apps.dataexchange.OutboundTranslate 

echo "asdf" >> ${logFile} 2>&1


cd $XDIR/edi

##sh ${XFTP_DIR}/cwii_send.sh ${outdir} ABF >> ${logFile}
sh ${XFTP_DIR}/abf_send.sh ${outdir}
mv ${outdir}/* $XDIR/edi/sent

### END 850 generation 

rm $lockfile

maillist=jobreporting@cleanwise.com
mail ${maillist} <<EOF
subject: $0 ${outFile} processed

--- $0 

`cat $logFile`

EOF           
