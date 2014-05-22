
#
# 12/20/2002, added account 85668, Century Campus, durval
# 10/23/2003, changes to accomodate the new USPS account (89417)
# 8/10/2004, added account 94010, the new JCP type 1, durval

. /apps/ixtendx/.profile
. ../app.sh.env
cd $XSUITE_HOME/xsuite/util

sh load-address-log.sh
# the sqlplus client does not support this env variable
unset NLS_LANG

tstamp=`date '+%Y.%m.%d'`
tstamp_rundate=`date '+%m/%d/%Y'`
lagassefile="../reports/lagasse/lagasse-addresses-${tstamp}.csv"
if [ ! -d "../reports/lagasse/" ]
then
  mkdir "../reports/lagasse/"
fi

xct=0
while [ -f $lagassefile ]
do
 lagassefile="../reports/lagasse/lagasse-addresses-${tstamp}-${xct}.csv"
 xct=`expr $xct + 1`
done

fAdditions=n
fChanges=n
if [ $# -eq 1 ]
then
  if [ $1 = "additions" ]
  then
    echo "a $1"
    fAdditions=y
  fi

  if [ $1 = "changes" ]
  then
    echo "c $1"
    fChanges=y
  fi
fi

if [ $fAdditions = "n" ] && [ $fChanges = "n" ]
then
  echo "Usage $0 additions or changes"
  exit
fi

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

if [ $fAdditions = "y" ]
then
sqlplus ${XSUITE_DBUSER}/${XSUITE_DBPASS}@${XSUITE_DBSERVER} <<EOF
set echo off
set head off
set wrap off
set linesize 300
set newpage NONE 
spool /tmp/lag-address-report.txt 


select '"A",' || rep_line from dist_address_log where sent_date is null 
    and (account_id <= 103 
or account_id = 89417
or account_id = 94010)
/

update dist_address_log set sent_date = sysdate where sent_date is null 
/

quit;
EOF
fi

if [ $fChanges = "y" ]
then
sqlplus ${XSUITE_DBUSER}/${XSUITE_DBPASS}@${XSUITE_DBSERVER} <<EOF
set echo off
set head off
set wrap off
set linesize 300
set newpage NONE 
spool /tmp/lag-address-report.txt 


select '"C",' || rep_line from dist_address_log where sent_date < mod_date 
    and (account_id <= 103 
or account_id = 89417
or account_id = 94010)
/

update dist_address_log set sent_date = sysdate where sent_date < mod_date 
/

quit;
EOF
fi

grep "^\""  /tmp/lag-address-report.txt  > $lagassefile 

maillist="srizkalla@cleanwise.com,kvarner@cleanwise.com,dclewis@Lagassenet.com,cpatterson@lagassenet.com,dvieira@cleanwise.com,dwestley@lagassenet.com,rbergeron@lagassenet.com"

if [ -s $lagassefile ]
then
sh send-email.sh "Lagasse $1 Address file $tstamp" \
 $maillist "No Message" $lagassefile
fi
