
. ${XDIR}/app.sh.env

XCP="@jdbcFile@${PSEP}."

ct=5
while [ $ct -lt 31 ]
do
 java -cp $XCP \
  -DdbUrl=@dbUrl@ \
  -DdbUser=@dbUser@  -DdbPwd=@dbPassword@ \
  -Ddaysback=$ct SynchJanitorsCloset

ct=`expr $ct + 10`
done


