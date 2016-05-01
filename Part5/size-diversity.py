#!/usr/bin/env python

from __future__ import division
import json
import os
from SolrClient import SolrClient
import sys
from tika import detector

solr = SolrClient('http://localhost:8983/solr')
walk_n = sum(len(files) for root, dirs, files in os.walk(sys.argv[1]))
walk_i = 0
ratios = {}
for root, dirs, files in os.walk(sys.argv[1]):
    for file in files:
        path = root + '/' + file
        file_size = os.stat(path).st_size
        if file_size == 0: continue
        mime = detector.from_file(path)
        sum, n = ratios.get(mime, (0, 0))
        ratios[mime] = sum + len(solr.query('collection1', {'q': 'id:' + file}).data['response']['docs']) / file_size, n + 1
        walk_i += 1
        print str(walk_i * 100 // walk_n) + '%\r',
with open('size-diversity.json', 'w') as f:
    json.dump({mime: sum / n for mime, (sum, n) in ratios.iteritems()}, f)
