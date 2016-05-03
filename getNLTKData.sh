#!/bin/bash
j=outputs/part10/nltk/outputs/temp1.txt
k=outputs/part10/nltk/outputs/temp2.txt
l=outputs/part10/nltk/outputs/temp3.txt
m=outputs/part10/nltk/outputs/temp4.txt
n=outputs/part10/nltk/outputs/temp5.txt
final=outputs/part10/nltk/outputs/measurements_final.txt
count=1
#for file in ~/shared/polar-fulldump-allinone/*
for file in ../benchfly/*
do
i=$file
echo "*******"$count" "`basename $file`"*******"
count=$((count+1))
#echo $file
#echo 'mvn exec:java -Dexec.mainClass="content_evaluation" -Dexec.args="getdata "'$file
#mvn exec:java -Dexec.mainClass="content_evaluation" -Dexec.args="getdata "$file
mvn exec:java -Dexec.mainClass="content_evaluation" -Dexec.args="write "$file" "$n
curl -X POST -d @$file http://127.0.0.1:8888/nltk > $j
cat $j | jq '.names' > $m
names="$(jq '.names' $j)"
echo ""${names}""
printf "{\n \"id\":\""`basename $file`"\",\n" >> $final
cat $n >> $final
printf " \"names\":" >> $final
if [ "${names}" = "" ]
then
printf "[]\n" >> $final
else
cat $m >> $final
fi
printf ",\n" >> $final
cat $j | jq '.units' > $k
units="$(jq '.units' $j)"
echo ""${units}""
printf "\"units\":" >> $final
if [ "${units}" = "" ]
then
printf "[]\n" >> $final
else
cat $k >> $final
fi
printf "},\n" >> $final
done
sed -i '$s/.*/}/' $final
