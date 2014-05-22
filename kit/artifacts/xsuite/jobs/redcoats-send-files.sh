
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
logFile=${logDir}/${0}_$logtstamp.log



cd $XDIR/dataexchange
outdir=redcoats_outbound
archdir=$XDIR/dataexchange/sent

if [ ! -d $outdir ]
then
  mkdir $outdir
fi

if [ ! -d $archdir ]
then
  mkdir $archdir
fi

cd $outdir

BUS_ENT_IDS=161104

tstamp=`date '+%Y%m%d%H%M%S'`  
### START invoice generation 
outFile=inv_cleanwise.dat.${tstamp}.txt

java -classpath "${TJCPATH}" \
 -Duser.home=${XSUITE_HOME}/xsuite/edi/tools/oboe \
 -Dconf="${XSUITE_HOME}/xsuite/app.properties" \
 -Dofile=$outFile -DbusEntityIdList=${BUS_ENT_IDS} -Dtype=810 \
com.cleanwise.service.apps.dataexchange.OutboundTranslate 


###>> ${logFile} 2>&1


if [ -s $outFile ] 
then
	echo ${XFTP_DIR}/cwii_send.sh $outFile RED
      sh ${XFTP_DIR}/cwii_send.sh $outFile RED >> ${logFile}
      sh ${XSUITE_HOME}/xsuite/util/send-email.sh "Invoices" tzelano@espendwise.com,SRoca@redcoats.com,RStokes@redcoats.com,criley@espendwise.com,esupport@espendwise.com "Invoices" $outFile
      mv $outFile $archdir
else
  echo "Nothing  generated for id ${BUS_ENT_IDS}"
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
