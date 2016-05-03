#!/bin/bash
temp1=outputs/part9/grobid/outputs/temp1.txt
temp2=outputs/part9/grobid/outputs/temp2.txt
temp3=outputs/part9/grobid/outputs/temp3.txt
final=outputs/part9/grobid/outputs/grobid_final.txt
count=1
printf "[\n" >> $final
for file in ../polar8k/*
do
i=$file
echo "*******"$count" "`basename $file`"*******"
count=$((count+1))

curl -X POST -d "text=$(cat "$file")" localhost:8080/processQuantityText > $temp1

line="$(head -n 1 $temp1)"
#echo $line
first="$(head -c 1 $temp1)"
#if [ "${line}" != "<html>" ]
if [ "${first}" = "{" ]
then
printf "{\n\"id\":\""`basename $file`"\",\n" >> $final
#echo $temp2
mvn exec:java -Dexec.mainClass="content_evaluation" -Dexec.args="write "$file" "$temp2
cat $temp2 >> $final
printf "\"grobidData\":\n" >> $final
if [ "${line}" = "" ]
then
printf "{}" >> $final
else
#cat $temp1 | jq '.measurements.quantity.rawValue' > $temp3
#value="$(jq '.measurements.quantity.rawValue' $temp1)"
#echo ""${value}""
cat $temp1 >> $final
fi
printf "},\n" >> $final
fi
done
sed -i '$s/.*/}/' $final
printf "]" >> $final
