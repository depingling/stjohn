#!/bin/sh
#Modified by Manoj(10/11/2007)
lockfile=/tmp/$0.lockfile
if [ -f $lockfile ]
then
  echo "error lock file $lockfile in place."
  exit
fi

echo $$ > $lockfile

. /apps/xadmin3/.profile

cd $XDIR
. app.sh.env

if [ -z ${XSUITE_HOME} ] 
then
  echo "XSUITE_HOME is not defined."
  exit
fi

expectOne=` sh ${XFTP_DIR}/bunzl_check.sh | grep DONE | wc -l`
if [ $expectOne -lt 1 ]
then
   echo "Exception, BunzlPA does not seem to be operational."
   rm $lockfile
   exit
fi

logtstamp=`date '+%Y%m%d.%H%M%S'`

logDir=$XSUITE_HOME/xsuite/edi/processed_log
logFile=${logDir}/bunzl_send_$logtstamp.log


cd $XDIR/edi
outdir=outbound_bunzl

if [ ! -d $outdir ]
then
  mkdir $outdir
fi


cd $outdir

ERP_NUM=1446

tstamp=`date '+%Y%m%d%H%M%S'`  
### START 850 generation 
outFile=po850_cleanwise.dat.${tstamp}.txt

java -classpath "${TJCPATH}" \
 -Duser.home=${XSUITE_HOME}/xsuite/edi/tools/oboe \
 -Dconf="${XSUITE_HOME}/xsuite/app.properties" \
 -Dofile=$outFile -Derpnum=${ERP_NUM} -Dtype=850 \
com.cleanwise.service.apps.dataexchange.OutboundTranslate >> ${logFile} 2>&1

if [ -s $outFile ] 
then
      sh ${XFTP_DIR}/cwii_send.sh $outFile BunzlPA >> ${logFile}
      sh ${XFTP_DIR}/bunzl_send.sh $outFile >> ${logFile}
      mv $outFile $XDIR/edi/sent
else
  echo "No 850 generated for account ${ERP_NUM}"
  rm -f $outFile
fi

### END 850 generation 

rm $lockfile

maillist=jobreporting@cleanwise.com
mail ${maillist} <<EOF
subject: $0 ${outFile} processed

--- $0 

`cat $logFile`

EOF           
