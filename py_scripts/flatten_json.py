__author__ = 'dagilmore'

import os
import json

def politicians_to_csv():
    writer = open('politician_metadata.csv', 'wb')
    newline = 'bioguide_id,recipient_ext_id,chamber,state_name,first_name,last_name,party,in_office,website,contact_form,'
    for file in os.listdir("."):
        if file.endswith(".json"):
            writer.write(newline)
            reader = json.loads(open(file).read())
            print newline
            newline = '{0},'.format(reader['bioguide_id'])
            newline += '{0},'.format(reader['crp_id'])
            newline += '{0},'.format(reader['chamber'])
            newline += '{0},'.format(reader['state_name'])
            newline += '{0},'.format(reader['first_name'].encode('ascii', 'ignore'))
            newline += '{0},'.format(reader['last_name'].encode('ascii', 'ignore'))
            newline += '{0},'.format(reader['party'])
            newline += '{0},'.format(str(reader['in_office']))
            try:
                newline += '{0},'.format(reader['twitter_id'])
            except:
                newline += ','
            newline += '{0},'.format(reader['website'])
            newline += '{0},'.format(str(reader['contact_form']))


def bills_to_csv():
    writer = open('bills.csv', 'wb')
    # newline = 'bill_id,sponsor_id,result,congress,introduced_on,bioguide_id,vote'
    for file in os.listdir("."):
        if not file.endswith(".py") and not file.endswith(".csv"):
            print file
            reader = json.loads(open(file).read())
            for id in reader['yeas_ids']:
                newline = '{0},'.format(reader['bill_id'])
                newline += '{0},'.format(reader['sponsor_id'])
                newline += '{0},'.format(reader['result'])
                newline += '{0},'.format(reader['congress'])
                newline += '{0},'.format(reader['introduced_on'])
                newline += '{0},'.format(id)
                newline += '{0},'.format('yea')
                writer.write(newline+'\n')
            for id in reader['nays_ids']:
                newline = '{0},'.format(reader['bill_id'])
                newline += '{0},'.format(reader['sponsor_id'])
                newline += '{0},'.format(reader['result'])
                newline += '{0},'.format(reader['congress'])
                newline += '{0},'.format(reader['introduced_on'])
                newline += '{0},'.format(id)
                newline += '{0},'.format('nay')
                writer.write(newline+'\n')

def remove_spaces(filename):
    file = open(filename)
    for line in file:
        line.replace(" ", "")


if __name__ == '__main__':
    bills_to_csv()