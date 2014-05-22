
lockfile=/tmp/$0.lockfile
if [ -f $lockfile ]
then
  echo "error lock file $lockfile in place."
  exit;
fi

echo $$ > $lockfile

cd ..
. app.sh.env

if [ -z ${XSUITE_HOME} ] 
then
  echo "XSUITE_HOME is not defined."
  exit
fi

logtstamp=`date '+%Y%m%d.%H%M%S'`

logFile=${logDir}/outbound-event_$logtstamp.log

TRADING_IDS=3259

tstamp=`date '+%Y%m%d%H%M%S'`  
### START invoice generation 

java -classpath "${TJCPATH}" \
 -Duser.home=${XSUITE_HOME}/xsuite/edi/tools/oboe \
 -Dconf="${XSUITE_HOME}/xsuite/app.properties" \
 -Dtype=850 \
 -DeventSys=true \
 -Derpnum="#180065" \
com.cleanwise.service.apps.dataexchange.OutboundTranslate 

java -classpath "${TJCPATH}" \
 -Duser.home=${XSUITE_HOME}/xsuite/edi/tools/oboe \
 -Dconf="${XSUITE_HOME}/xsuite/app.properties" \
 -Dtype=850 \
 -DeventSys=true \
 -Derpnum="#177753" \
com.cleanwise.service.apps.dataexchange.OutboundTranslate


### END 850 generation 

rm $lockfile

maillist=jobreporting@cleanwise.com
mail ${maillist} <<EOF
subject: $0 ${outFile} processed

--- $0 

`cat $logFile`

EOF           
