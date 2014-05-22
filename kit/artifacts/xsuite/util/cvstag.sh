v_tag=V_5_sep_06
v_tag=`date +V_%d_%b_%y`
printf "tag the stjohn with $v_tag (y/n):"
read resp
if [ ${resp}x = "yx" ]
then
  echo tagging with version $v_tag
  cd ../../
  cvs tag -R $v_tag 
fi
echo done
exit

