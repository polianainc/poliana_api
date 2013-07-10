__author__ = 'dagilmore'

import csv
from boto.dynamodb2.fields import HashKey, STRING
from boto.dynamodb2.table import Table, Item



def initialize_contributions_table(name='contributions'):
    """
    Initialize DynamoDB table for contributions
    """
    Table.create(
        name,
        schema=[
            HashKey('recipient_ext_id', data_type=STRING)
        ],
        throughput={
            'read': 15,
            'write': 5,
        },
    )


def load_contribution_table():
    """
    Load DynamoDB table from dumps in s3
    """
    contributions = Table('contributions')
    csvfile = open('../../data/contribution_table.txt')
    input = csv.DictReader(csvfile, delimiter=',')
    for row in input:
        contribution = Item(contributions, data={
            'recipient_ext_id': row['recipient_ext_id']
        })

if __name__ == '__main__':
    initialize_contributions_table('test_contributions')
    load_contribution_table()


