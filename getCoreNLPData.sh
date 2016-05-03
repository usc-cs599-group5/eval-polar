#!/bin/bash
j=outputs/part9/corenlp/outputs/temp1.txt
k=outputs/part9/corenlp/outputs/temp2.txt
l=outputs/part9/corenlp/outputs/temp3.txt
m=outputs/part9/corenlp/outputs/temp4.txt
final=outputs/part9/corenlp/outputs/corenlp_final.txt
json=outputs/part9/corenlp/outputs/corenlpNames.json
count=1
export CORE_NLP_JAR=`find /home/dipalibhatt/src/tika-ner-corenlp/target/tika-ner-corenlp-addon-*jar-with-dependencies.jar`
export TIKA_APP=/home/dipalibhatt/tika-app-1.12.jar
#for file in ~/shared/polar-fulldump-allinone/*
for file in ../polar8k/*
do
i=$file
echo "*******"$count" "`basename $file`"*******"
count=$((count+1))

java -Dner.impl.class=org.apache.tika.parser.ner.corenlp.CoreNLPNERecogniser \
      -classpath $TIKA_APP:$CORE_NLP_JAR org.apache.tika.cli.TikaCLI \
      --config=../../src/tika-ner-corenlp/tika-config.xml -m $i > $j
sed -e '1,1d' < $j > $k
head -n -3 < $k > $l
printf "CSCI599FileName "`basename $file`"\n" >> $final
mvn exec:java -Dexec.mainClass="content_evaluation" -Dexec.args="getFileSize "$i" "$m
cat $m >> $final
#printf "\n" >> $final
cat $l >> $final
done
mvn exec:java -Dexec.mainClass="content_evaluation" -Dexec.args="part9 corenlp "$final" "$json
