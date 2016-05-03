from itertools import izip
import json
from datetime import datetime

from scipy.weave.ext_tools import indent

n = 0.0
avg_description = 0
avg_title = 0
avg_version = 0
avg_license = 0
avg_no_of_fields = 0

def no_of_fields_score(json_line):
    count = 0
    score = 0.0
    for key in json_line:
        count += 1
    if count<20:
        score = count/20.0
    if count >= 20:
        score =1
    return count # changed from score

def get_metadata_score(json_line):
    global n, avg_description, avg_title, avg_version, avg_license, avg_no_of_fields
    metadata_score = 0
    if 'description' in json_line:
        avg_description += 1
    if 'title' in json_line:
        avg_title += 1
    if 'version' in json_line:
        avg_version += 1
    if 'license' in json_line:
        avg_license += 1
    #calculates scores based on the keys in the json file
    avg_no_of_fields += no_of_fields_score(json_line)
    #adds DOI score
    metadata_score+=1
    #for long term management constant score 1
    metadata_score+=1
    #normalizing the score
    metadata_score = '%.3f'%(metadata_score/6)    
    n += 1


    
    
def main():
    global n, avg_description, avg_title, avg_version, avg_license, avg_no_of_fields

    merged_file_path = 'merged_features.json'

    with open(merged_file_path) as merged_data:
        for line in merged_data:
            json_line = json.loads(line.strip())
            get_metadata_score(json_line)
            
    avg_description /= n
    avg_title /= n
    avg_version /= n
    avg_license /= n
    avg_no_of_fields /= n
    print("""Percentage of files with description: {}%
Percentage of files with title: {}%
Percentage of files with version: {}%
Percentage of files with license: {}%
Average number of fields: {}"""
        .format(avg_description * 100, avg_title * 100, avg_version * 100, avg_license * 100, avg_no_of_fields))


if __name__ == '__main__':
    main()
