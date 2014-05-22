
. ${XDIR}/app.sh.env


#TEMPORARY EASTERN BAG SOLUTION.  HARDCODED IDS and paths.

#XCP="${jdbcDir}/${jdbcFile}${PSEP}."
#XCP="/xadmin/stjohn/installation/jdbc/lib/ojdbc14.jar${PSEP}.${PSEP}/xadmin/stjohn/kit/"
XCP="@jdbcLib@${PSEP}.${PSEP}../stjohn/kit/"

echo ":::::::::::::::: Start: "; date
# look for items that may have been purchased without a cost center (0)
# and update the budget entries is a cost center has been specified.
tstamp=`date '+%Y%m%d%H'`
addrLogFile="/tmp/cw-order-31-address-${tstamp}.txt"

 java -cp $XCP \
  -DdbUrl=@dbUrl@ \
  -DdbUser=@dbUser@  -DdbPwd=@dbPassword@ \
  -DaddressLogFile=$addrLogFile \
  -DstartOrd=${1} -DendOrd=${2} \
  -Ddist=1010 \
   TempLogShipToAddr

   
if [ -f $addrLogFile ]
then
  cp $addrLogFile $XDIR/edi/sent
  # changed to the fedex area, vladimir 2007-1-12
  mv $addrLogFile /apps/ixtendx/pub_ftp/ebfedex/address_files
fi

echo ":::::::::::::::: End   : "; date
