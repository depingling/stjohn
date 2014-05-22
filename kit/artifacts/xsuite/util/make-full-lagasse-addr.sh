
. /apps/ixtendx/.profile
cd $XDIR/util
. ../app.sh.env

sh load-address-log.sh
# the sqlplus client does not support this env variable
unset NLS_LANG

tstamp=`date '+%Y.%m.%d'`
tstamp_rundate=`date '+%m/%d/%Y'`
lagf1="../reports/lagasse/lagasse-addresses"
lagassefile="${lagf1}-${tstamp}.csv"
xct=0
while [ -f $lagassefile ]
do
 lagassefile="${lagf1}-${tstamp}-${xct}.csv"
 xct=`expr $xct + 1`
done

fAdditions=n
fChanges=y

touch $lagassefile

## Field legend
##
## A: action code? (A = add, C = change, D = delete)
## B: site id (ship to id for the distributor)
## C: account number 
## D: site name
## E: account name
## F: address 1, address 2
## G: address 3, address 4
## H: city
## I: state
## J: zip code
##
##

if [ $fChanges = "y" ]
then
sqlplus ${XSUITE_DBUSER}/${XSUITE_DBPASS}@${XSUITE_DBSERVER} <<EOF
set echo off
set head off
set wrap off
set linesize 300
set newpage NONE 
spool /tmp/lag-full-address-report.txt 


select '"C",' || rep_line from dist_address_log
   where sent_date is not null and account_id <= 103
/

update dist_address_log 
  set sent_date = sysdate
   where sent_date is not null and account_id <= 103
/

quit;
EOF
fi

grep "^\""  /tmp/lag-full-address-report.txt  > $lagassefile 

sh send-email.sh "Lagasse full Address changes file $tstamp" dvieira@cleanwise.com "no message" $lagassefile





