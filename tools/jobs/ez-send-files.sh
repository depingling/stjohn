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

expectOne=` sh ${XFTP_DIR}/ez_check.sh | grep DONE | wc -l`
if [ $expectOne -lt 1 ]
then
   echo "Exception, EZLink does not seem to be operational."
   rm $lockfile
   exit
fi

logtstamp=`date '+%Y%m%d.%H%M%S'`

logDir=$XSUITE_HOME/xsuite/edi/processed_log
logFile=${logDir}/ez_send_$logtstamp.log


cd $XDIR/edi
outdir=outbound_ez

if [ ! -d ${outdir} ]
then
  mkdir ${outdir}
fi


cd $outdir

tstamp=`date '+%Y%m%d%H%M%S'`  

# generate 856 (ship notice) documents for JCP, durval 2006-8-16
ERP_NUM=10053,10131

java -classpath "${TJCPATH}" \
 -Duser.home=${XSUITE_HOME}/xsuite/edi/tools/oboe \
 -Dconf="${XSUITE_HOME}/xsuite/app.properties" \
 -Derpnum=${ERP_NUM} -Dtype=856 \
com.cleanwise.service.apps.dataexchange.OutboundTranslate >> ${logFile} 2>&1


# added ,10172,10173,10174 durval 4/25/2005
# added 10208 durval 8/23/2005 JCI Automotive
# added 10209 vladimir 10/03/2006 JCI Kodak
##10097 = JCI - Detroit Airport
#Removed 10224 JCI Weatherford College, since they are going FAX Jason R. 3/5/2009
#Removed 10129,10097, Johnson Controls and JCI-Detroit Airport. We no longer use that trading partner. Jason R. 7/22/2009
#Removed 10053 JC Penney. They are going via the event system. Jason R. 7/24/2009 
#ERP_NUM=10104,10136,10099,10106,10111,10128,10131,10145,10155,10172,10173,10174,10208,10209,10229,10228

### START 810 generation 
java -classpath "${TJCPATH}" \
 -Duser.home=${XSUITE_HOME}/xsuite/edi/tools/oboe \
 -Dconf="${XSUITE_HOME}/xsuite/app.properties" \
 -Derpnum=${ERP_NUM} -Dtype=810 \
com.cleanwise.service.apps.dataexchange.OutboundTranslate >> ${logFile} 2>&1

cd $XDIR/edi

sh ${XFTP_DIR}/cwii_send.sh ${outdir} EZ true>> ${logFile}
sh ${XFTP_DIR}/ez_send.sh ${outdir} true>> ${logFile}
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
