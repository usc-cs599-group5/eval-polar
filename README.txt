Part 4:
# Generate classification-path.json.
# This assumes Solr is running as set up in HW2 part 9.
python classification-path.py /path/to/files

Note that we fixed an important bug in the HW2 part 9 script which probably explains why we ended up making separate versions of it for HW2 parts 11 and 12. The fixed solr_ingest.py script is included here.

When the assignment states "what categories of pages were part of the request", we took "categories of pages" to mean MIME types. We later learned this wasn't what was originally intended but that our interpretation is OK.

Part 5:
# Generate size-diversity.json.
# This assumes Solr is running as set up in HW2 part 9.
python size-diversity.py /path/to/files

metadata-average:
# We modified our HW2 metadata quality script to output the average of each component of the score across all files, instead of the final score separately for each file.
# This was not required for the assignment but provided additional statistics for the report.
python metadata_quality_average.py

Part 6:
# For all 4 parsers: Tika's AutoDetect parser, NLTK, OpenNLP and CoreNLP for we calculated the ratios of amount of text extracted to file size and amount of metadata extracted to file size grouped by MIME Types.
mvn exec:java -Dexec.mainClass="Content_Evaluation" -Dexec.args="part6"

Part 7
# Run following command to get the language diversity across the data set. We will get languagDetector.json and languageDetector.json as output.
mvn exec:java -Dexec .mainClass="Content_Evaluation" -Dexec.args="lang"

Part 8
# We used the output from HW2 that conatains sweet ontologies found in entire dataset and converted the json for d3.
# Run the following command to convert and get SweetD3_output.json file
mvn exec:java -Dexec .mainClass="Content_Evaluation" -Dexec.args="convert"

Part 9:
# To generate NLTK run "sh getNLTKData.sh" from Unix terminal. You will get nltk.json as the output.
# To generate CoreNLP run "sh getCoreNLPData.sh" from Unix terminal. You will get coreNLP.json as the output.
# To generate OpenNLP run the following command. You will get openNLP.json as the output.
mvn exec:java -Dexec .mainClass="Content_Evaluation" -Dexec.args="part9 opennlp"
# To generate Grobid run "sh getGrobidData.sh" from Unix terminal. You will get grobid.json as the output.
# To generate maximal joint agreement run the following command. You will get Part9Output.json as the output.
mvn exec:java -Dexec .mainClass="Content_Evaluation" -Dexec.args="Part9AgreementParser"

Part 10:
# To get the spectrum for all the measurements run the following command.
mvn exec:java -Dexec .mainClass="Content_Evaluation" -Dexec.args="part10"
# It will generate measurementSpectrum.json, measurementContentTypesSpectrum.json, measurementSpectrum_abc7news.json and measurementTypesSpectrum.json.

Part 11:
# vis folder contains all the D3 Visualizations separated by Part Numbers.

Part 12:
# Link to the video: 
