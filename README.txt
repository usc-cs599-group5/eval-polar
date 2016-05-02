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
