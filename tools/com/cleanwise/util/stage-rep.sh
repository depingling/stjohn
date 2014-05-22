lockfile=/tmp/$0.lockfile

if [ -f $lockfile ]
then
  echo "error lock file $lockfile in place."
  exit
fi

echo $$ > $lockfile

XDIR=@jbossHome@/xsuite
export XDIR
. ${XDIR}/app.sh.env



XCP="ojdbc14.jar${PSEP}."

echo ":::::::::::::::: Start: "; date
# look for items that may have been purchased without a cost center (0)
# and update the budget entries is a cost center has been specified.

 java -cp $XCP \
  -DdbUrl="jdbc:oracle:thin:@(DESCRIPTION = (ADDRESS = (PROTOCOL = TCP)(HOST = scan01-989991.racscan.com)(PORT = 1521)) (CONNECT_DATA = (SERVER = DEDICATED) (SERVICE_NAME = cwcamdb1)))" \
  -DdbUser=stjohn_prod  -DdbPwd=stjohn_prod \
   StageBudgetReport


# stage the customer invoice data on the reporting server.
##sh stage-rep-db.sh

echo ":::::::::::::::: End   : "; date

rm $lockfile
