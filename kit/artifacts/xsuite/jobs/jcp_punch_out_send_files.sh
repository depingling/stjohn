
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

logtstamp=`date '+%Y%m%d.%H%M%S'`

logDir=$XSUITE_HOME/xsuite/edi/processed_log
logFile=${logDir}/jcp_send_$logtstamp.log

if [ `uname` = "CYGWIN_NT-5.0" ]
then PSEP="\;"
else PSEP=":";
fi



cd $XDIR/edi
outdir=outbound_jcp_punch_out

if [ ! -d ${outdir} ]
then
  mkdir ${outdir}
fi

cd $outdir

BUS_ENT_IDS=94010

tstamp=`date '+%Y%m%d%H%M%S'`  
### START generation 
outFile=rec_import_file_cleanwise${tstamp}.xls

java -classpath "${TJCPATH}" \
 -Duser.home=${XSUITE_HOME}/xsuite/edi/tools/oboe \
 -Dconf="${XSUITE_HOME}/xsuite/app.properties" \
 -Dofile=$outFile -DbusEntityIdList="${BUS_ENT_IDS}" \
 -Dtype="PUNCH_OUT_ORDER_OUT" \
com.cleanwise.service.apps.dataexchange.OutboundTranslate >> ${logFile} 2>&1

# If there's anything in the file, move it to the outbound directory.
if [ -s $outFile ] 
then
      sh ${XDIR}/util/send-email.sh "Cleanwise Order File" "bstevens@cleanwise.com,kcuddihy@cleanwise.com,jcp-order-files@cleanwise.com" "Here are your orders placed on the Cleanwise web site" "${outFile}" >> ${logFile}
      mv $outFile $XDIR/edi/sent
else
  sh ${XDIR}/util/send-email.sh "Cleanwise Order File" "bstevens@cleanwise.com,kcuddihy@cleanwise.com,jcp-order-files@cleanwise.com" "There were no orders placed on the Cleanwise web site today" >> ${logFile}
  echo "No generated for account ids: ${BUS_ENT_IDS}"
  rm -f $outFile
fi

### END generation 

rm $lockfile


maillist=jobreporting@cleanwise.com
mail ${maillist} <<EOF
subject: $0 ${outFile} processed

--- $0

`cat $logFile`

EOF                  

