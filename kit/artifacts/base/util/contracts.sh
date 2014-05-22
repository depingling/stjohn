
cloneContract() {

##-DdbUrl="jdbc:oracle:thin:@localhost:1521:CWISE" -DdbUser="stjohn_dv" -DdbPwd="stjohn_dv" \
echo "copy contract $1"

java -cp "C:\Oracle\Ora81\jdbc\lib\classes12.zip;." \
-DdbUrl="@dbUrl@" \
-DdbUser="@dbUser@" -DdbPwd="@dbPassword@" \
-Dcmd=cloneContract -Dcontract_id=$1 CatalogMgr


}

conl="
856
859
862
863
864
865
866
868
869
872
873
874
876
877
878
879
880
881
882
883
884
886
888
893
895
897
898
900
901
902
903
904
905
906
908
911
922
959
969
1180
1239
1243
1359
1370
1409
1431
1440
1472
1539
"

for c in $conl
do

cloneContract $c

done


exit

