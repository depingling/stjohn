
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
  exit
fi

expectOne=` sh ${XFTP_DIR}/network_check.sh | grep DONE | wc -l`
if [ $expectOne -lt 1 ]
then
   echo "Exception, network does not seem to be operational."
   rm $lockfile
   exit
fi

logtstamp=`date '+%Y%m%d.%H%M%S'`

logDir=$XSUITE_HOME/xsuite/edi/processed_log
logFile=${logDir}/network_send_$logtstamp.log

if [ `uname` = "CYGWIN_NT-5.0" ]
then PSEP="\;"
else PSEP=":";
fi



tstamp=`date '+%Y%m%d%H%M%S'`

cd $XDIR/edi
outdir=outbound_network
outFile=po850_cleanwisedat${tstamp}.txt

if [ ! -d ${outdir} ]
then
  mkdir ${outdir}
fi


cd $outdir


### START generation 

gen850() {
BUS_ENT=$1
tstamp=`date '+%Y%m%d%H%M%S'`
outFile=po850_cleanwisedat${tstamp}.txt

echo " START ========= BUS_ENT=$BUS_ENT outFile=$outFile =========="

java -classpath "${TJCPATH}" \
 -Duser.home=${XSUITE_HOME}/xsuite/edi/tools/oboe \
 -Dconf="${XSUITE_HOME}/xsuite/app.properties" \
 -Dofile="${outFile}" \
 -DbusEntityIdList=${BUS_ENT} -Dtype=850 \
com.cleanwise.service.apps.dataexchange.OutboundTranslate >> ${logFile} 2>&1

echo " END ========= BUS_ENT=$BUS_ENT outFile=$outFile =========="

sleep 2
}

gen850_andAddressLog() {
BUS_ENT=$1
addrLogFile=$2
tstamp=`date '+%Y%m%d%H%M%S'`
outFile=po850_cleanwisedat${tstamp}.txt

echo " START ========= BUS_ENT=$BUS_ENT outFile=$outFile addrLogFile=$addrLogFile =========="

java -classpath "${TJCPATH}" \
 -Duser.home=${XSUITE_HOME}/xsuite/edi/tools/oboe \
 -Dconf="${XSUITE_HOME}/xsuite/app.properties" \
 -Dofile="${outFile}" \
 -DaddressLogFile="$addrLogFile" \
 -DbusEntityIdList=${BUS_ENT} -Dtype=850 \
com.cleanwise.service.apps.dataexchange.OutboundTranslate >> ${logFile} 2>&1
echo " END ========= BUS_ENT=$BUS_ENT outFile=$outFile addrLogFile=$addrLogFile =========="

sleep 2
}


# NSC distributors using the vendor part numbers

# Kellermeyer has the windowbook software that is
# going to look in this directory for address files.
addrLogFile="/tmp/cw-order-10-address-${tstamp}.txt"
gen850_andAddressLog 10 $addrLogFile
if [ -f $addrLogFile ]
then
  cp $addrLogFile $XDIR/edi/sent
  # changed to the fedex area, durval 2006-8-22
  mv $addrLogFile /apps/xadmin3/pub_ftp/kelfedex/address_files
fi

gen850 24530
gen850 45
gen850 28
gen850 11
gen850 89018
#Disabled Kranz 7/14/2009. Reason: they are going fax. Jason R.
#gen850 99560
# Western paper AZ, added on 2006-8-23, durval
gen850 161454

# NSC distributors using the NSC numbers

# NSC/Kellermeyer	103560
gen850 103560

# NSC/M Conley Company	103561
gen850 103561

# NSC/Kranz, Inc.	103573
gen850 103573

# NSC/American Paper & Twine	103579
gen850 103579

# NSC/Western Paper Distributors	103584
gen850 103584

# NSC/Waxie Sanitary Supply	103593
gen850 103593

# NSC/Western Paper - AZ	161455
gen850 161455

# NSC/Western Paper - NM        179253
gen850 179253

#Dist id # 110900 - NSC/Waxie AK
gen850 110900

#Dist id # 24497 - NSC/Waxie WA
gen850 24497

gen850 103574
gen850 385873
gen850 86650
gen850 384278
gen850 385869
gen850 384279
gen850 386913

gen850 389663
gen850 389664
gen850 389665
gen850 389666
gen850 103570
gen850 390999
gen850 391154
gen850 391155
gen850 391156
gen850 103578
### END generation 

# now send all the files generated
cd $XDIR/edi

sh ${XFTP_DIR}/cwii_send.sh ${outdir} Network true>> ${logFile}
sh ${XFTP_DIR}/network_send.sh ${outdir} true>> ${logFile}
mv ${outdir}/* $XDIR/edi/sent
rm -f ${outdir}/*


rm $lockfile

maillist=jobreporting@cleanwise.com
mail ${maillist} <<EOF
subject: $0 processed

--- $0 

`cat $logFile`

EOF           
