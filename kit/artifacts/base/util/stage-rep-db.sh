
. ${XDIR}/app.sh.env

XCP="ojdbc14.jar${PSEP}."

java -cp $XCP \
-DdbUrl="jdbc:oracle:thin:@192.168.3.123:1521:cwcamdb1" -DdbUser="stjohn_prod" \
-DdbPwd="stjohn_prod" \
StageReportDB
