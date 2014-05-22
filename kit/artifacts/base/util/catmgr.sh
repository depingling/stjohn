
addItem() {

##-DdbUrl="jdbc:oracle:thin:@localhost:1521:CWISE" -DdbUser="stjohn_dv" -DdbPwd="stjohn_dv" \

java -cp "C:\Oracle\Ora81\jdbc\lib\classes12.zip;." \
-DdbUrl="@dbUrl@" -DdbUser="@dbUser@" \
-DdbPwd="@dbPassword@" \
-Dcmd=addSku -Dsku=$1 -Dfromcatalog_id=$2 -Dtocatalog_id=$3 -Dtodist_id=$4 \
CatalogMgr
}

addDist() {
java -cp "C:\Oracle\Ora81\jdbc\lib\classes12.zip;." \
-DdbUrl="@dbUrl@" -DdbUser="@dbUser@" -DdbPwd="@dbPassword@" \-Dcmd=addCatalogDist -Dcatid=$1 -Ddistid=87668 \
CatalogMgr
}

il="28564 
28565
28566
28567
28568
28569
28570
28571
28572
28573
28574
28584
28585
28586
28587
28588
28589
"
cl="66
96
110
117
118
120
123
141
145
147
151
156
158
163
164
165
166
167
168
169
170
172
173
174
177
178
179
181
182
183
198
204
228
229
230
231
232
234
235
236
237
335
417
447
519
547
627
637
647
660
697
730
732
737
757
770
783
808
817
900
937
1042
1047
1067
1077
1147
1157
1187
1217
1240
"

for c in $cl
do

addDist $c

for i in $il
do
  addItem $i 36 $c 87668
done


done


exit

