#!/bin/sh
echo $0

lockfile=/tmp/$0.lockfile
if [ -f $lockfile ]
then
  echo "Error lock file $lockfile in place."
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
logFile=${logDir}/ez_send_freq_$logtstamp.log




cd $XDIR/edi
outdir=outbound_ez_freq

if [ ! -d ${outdir} ]
then
  mkdir ${outdir}
fi


cd $outdir


##
# generate 850s for the following distributors
#
# 6	Jan-Pak TX
# 86937	Jan-Pak NC
#102165 GCA JanPak North Carolina
#103632 GCA JanPak Houston TX
#108880 GCA JanPak Dallas TX
#111960 GCA JanPak Georgia
#112788 GCA JanPak Florida
#113543 GCA JanPak West Virginia
#114270 GCA JanPak Piedmont SC
#114272 GCA JanPak Huntsville AL
#114291 GCA JanPak Columbia SC
#113826 GCA JanPak Wando SC
#114928 GCA JanPak Moody AL
#158960	JanPak Huntsville AL JanPak Store
#158961	JanPak Jacksonville FL JanPak Store
#158970	JanPak Moody AL JanPak Store
#158972	JanPak Winston-Salem NC JanPak Store
#159028	JanPak Jacksonville FL JanPak Store
#159029	JanPak Atlanta GA JanPak Store
#159030	JanPak Moody AL JanPak Store
#159031	JanPak Piedmont SC JanPak Store
#159032	JanPak Columbia SC JanPak Store
#159033	JanPak Winston-Salem NC JanPak Store
#159034	JanPak Houston TX JanPak Store
#112828	JanPak Piedmont SC JanPak Store
#113922	JanPak Columbia SC JanPak Store
#158484	JanPak Atlanta GA JanPak Store
#158488	JanPak Moody AL JanPak Store
#158489	JanPak Winston-Salem NC JanPak Store
#158490	JanPak DFW TX JanPak Store
#159179	JanPak Huntsville AL JanPak Store
#159180	JanPak Jacksonville FL JanPak Store
# 97180	MSC Industrial Supply Co., Inc
# 94942	Tennant
# 106812,106804 GCA Pollock Papers
# 114907 Nichols
# 110690 Kelsan, Inc.
# 157071 Eastern Bag
# 114721 North State Supply
# 9-22-2005, durval, added the Jan-Pak distributors
#
# 1-19-2006, brook, added GCA Pollock Paper distributors
#
# 8-8-2006 disabled janpak store due to EDI setup issue.
#
# 10-11-2006, vladimir, added the Nichols distributor
# 10-25-2006, vladimir, added the Kelsan distributor
# 10-31-2006, vladimir, added the Eastern Bag distributor
# 11-14-2006, vladimir, added the Budd Group - North State distributor
# 01-31-2007, vladimir, added H.P. Products Corporation
# 03-13-2007, vladimir, Eastern Bag and Paper (id 31) is back
# 05-02-2007, vladimir, added Dixon Services Inc (id 157655) and American Paper & Twine (id 112780) 
# 06-07-2007, vladimir, added NY City Industry for the Blind (id 87527)
#
BUS_ENT=94942,97180,86937,6,106804,106812,102165,103632,108880,111960,112788,113543,114270,114272,114291,113826,114928,158960,158961,158970,158972,159028,159029,159030,159031,159032,159033,159034,114907,110690,114721,41,157071,
#31,157655,112780,158490...moved to event system
#87527 disable ny city for blind...moved to event system#,111462 disabled Lawrence Sanitary
TRADING_IDS=1994  ##JanPack self service store

tstamp=`date '+%Y%m%d%H%M%S'`  
### START generation 

java -classpath "${TJCPATH}" \
 -Duser.home=${XSUITE_HOME}/xsuite/edi/tools/oboe \
 -Dconf="${XSUITE_HOME}/xsuite/app.properties" \
 -DtradingPartnerIdList=${TRADING_IDS} \
 -DbusEntityIdList=${BUS_ENT} -Dtype=850 \
com.cleanwise.service.apps.dataexchange.OutboundTranslate >> ${logFile} 2>&1

###########
# generate 850s and address log file for the Eastern Bag & Paper
# Eastern Bag and FEDEX have the windowbook software that is
# going to look in this directory for address files.

## See file with this part. 02/15/07 Vladimir

cd $XDIR/edi

sh ${XFTP_DIR}/cwii_send.sh ${outdir} EZ true>> ${logFile}
sh ${XFTP_DIR}/ez_send.sh ${outdir} true>> ${logFile}
mv ${outdir}/* $XDIR/edi/sent
rm -f ${outdir}/*

### END generation 

echo "removing lock file $lockfile" >> ${logFile} 2>&1

rm $lockfile
echo "DONE removing lock file $lockfile" >> ${logFile} 2>&1

### Run xsuite-after-send-reports.job
#date >> ${logFile} 2>&1
#echo "Running xsuite-after-send-reports.job" >> ${logFile} 2>&1
#cd $XDIR/jobs
#sh xsuite-after-send-reports.job >> ${logFile} 2>&1 &
#echo "Done Running xsuite-after-send-reports.job" >> ${logFile} 2>&1
#date >> ${logFile} 2>&1
maillist=jobreporting@cleanwise.com
mail ${maillist} <<EOF
subject: $0 processed

--- $0 

`cat $logFile`

EOF 

