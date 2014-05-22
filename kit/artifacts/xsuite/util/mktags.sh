
TF=TAGS
printf "Making $TF tags db file"
if [ -f $TF ]
then
  rm $TF
fi

FL=`find . -name "*.java" -print`

for f in $FL
do
 etags -ao $TF $f
done


  

