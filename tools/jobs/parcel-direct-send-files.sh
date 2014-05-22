
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


expectOne=` sh ${XFTP_DIR}/parcel_direct_check.sh | grep DONE | wc -l`
if [ $expectOne -lt 1 ]
then
   echo "Exception, Parcel Direct does not seem to be operational."
   rm $lockfile
   exit
fi

logtstamp=`date '+%Y%m%d.%H%M%S'`

logDir=$XSUITE_HOME/xsuite/edi/processed_log
logFile=${logDir}/parcel_direct_send_$logtstamp.log


cd $XDIR/edi
outdir=outbound_parcel_direct

if [ ! -d ${outdir} ]
then
  mkdir ${outdir}
fi

cd $outdir

ERP_NUM=asdf

tstamp=`date '+%Y%m%d%H%M%S'`  
### START Manifest Generation


java -classpath "${TJCPATH}" \
 -Duser.home=${XSUITE_HOME}/xsuite/edi/tools/oboe \
 -Dconf="${XSUITE_HOME}/xsuite/app.properties" \
 -Derpnum=${ERP_NUM} -Dtype=MANIFEST_OUT \
com.cleanwise.service.apps.dataexchange.OutboundTranslate | tee -a ${logFile}

exists=` find . -type f`
if [ $exists ]
then
  
  sh ${XFTP_DIR}/cwii_send.sh . ParcelDirect >> ${logFile}
  sh ${XFTP_DIR}/parcel_direct_send.sh .>> ${logFile}
  
  find . -type f -exec mv {} $XDIR/edi/sent \;
fi

### END MANIFEST generation 

rm $lockfile

maillist=jobreporting@cleanwise.com
mail ${maillist} <<EOF
subject: $0 manifest processed

--- $0 

`cat $logFile`

EOF           
