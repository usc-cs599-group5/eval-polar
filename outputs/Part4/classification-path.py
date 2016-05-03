#!/usr/bin/env python

from __future__ import division
import collections
import json
import os
from SolrClient import SolrClient
import sys
from tika import detector

solr = SolrClient('http://localhost:8983/solr')
walk_n = sum(len(files) for root, dirs, files in os.walk(sys.argv[1]))
walk_i = 0
entities = collections.defaultdict(lambda: [])
for root, dirs, files in os.walk(sys.argv[1]):
    for file in files:
        path = root + '/' + file
        mime = detector.from_file(path)
        for val in solr.query('collection1', {'q': 'id:' + file}).data['response']['docs'][0].values():
            if type(val) is list:
                entities[mime].extend(val)
        walk_i += 1
        print str(walk_i * 100 // walk_n) + '%\r',
with open('classification-path.json', 'w') as f:
    json.dump({k: collections.Counter(v) for k, v in entities.iteritems()}, f)
