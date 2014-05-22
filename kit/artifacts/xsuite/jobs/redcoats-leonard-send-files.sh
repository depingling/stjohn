#!/bin/sh
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
logFile=${logDir}/redcoats_send_$logtstamp.log




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

BUS_ENT_IDS=162190

tstamp=`date '+%Y%m%d%H%M%S'`  
### START invoice generation 
outFile=po_cleanwise.dat.${tstamp}.txt

java -classpath "${TJCPATH}" \
 -Duser.home=${XSUITE_HOME}/xsuite/edi/tools/oboe \
 -Dconf="${XSUITE_HOME}/xsuite/app.properties" \
 -Dofile=$outFile -DbusEntityIdList=${BUS_ENT_IDS} -Dtype=850 \
com.cleanwise.service.apps.dataexchange.OutboundTranslate 


###>> ${logFile} 2>&1


if [ -s $outFile ] 
then
	echo ${XFTP_DIR}/cwii_send.sh $outFile RED
      sh ${XFTP_DIR}/cwii_send.sh $outFile RED >> ${logFile}
###      sh ${XFTP_DIR}/redcoats_send.sh $outFile >> ${logFile}
      sh ${XSUITE_HOME}/xsuite/util/send-email.sh "Leonard Orders" nikki@leonardpaper.com,tiana@leonardpaper.com,rclleo@leonardpaper.com,SRoca@redcoats.com "RedCoats and Potomac Orders" $outFile
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
