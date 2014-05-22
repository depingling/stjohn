. /apps/ixtendx/.profile
. ../app.sh.env
# the sqlplus client does not support this env variable
unset NLS_LANG


sqlplus log_${XSUITE_DBUSER}/log_${XSUITE_DBPASS}@${XSUITE_DBSERVER} @t.sql

