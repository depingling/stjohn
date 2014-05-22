
. /apps/xadmin3/.profile
. ../app.sh.env
# the sqlplus client does not support this env variable
unset NLS_LANG
# clean out the shared memory pool of unused data.
sqlplus ${XSUITE_DBUSER}/${XSUITE_DBPASS}@${XSUITE_REPORT_DBSERVER} @oracle-db-check.sql > /tmp/oracle-memory-check.txt

# no need to recreate indexes (durval 9/18/2002)
exit

# recreate indexes
sqlplus ${XSUITE_DBUSER}/${XSUITE_DBPASS}@${XSUITE_DBSERVER} @oracle-db-indexes.sql > /tmp/oracle-indexes.txt
