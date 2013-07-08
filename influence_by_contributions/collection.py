__author__ = 'dagilmore'

import os
import json
from transparencydata import TransparencyData


class Collection:
    """
    Collect information regarding monetary contributions to legislators

    TODO: Eliminate API dependencies by replicating necessary data on s3
    """
    def __init__(self):
        self.API_KEY = 'e2f50697b79a43cfa3aac99e421e3322'
        self.location = os.path.join(
            os.path.dirname(__file__),
            '../data/s744-113.json'
        )

    def get_votes(self, bill_id=None, body='senate'):
        """
        Get a dictionary of legislator votes in a certain body for a bill

        :param: bill_id
            -specify which bill to search
        :param: body
            -specify which body of legislators to search
        :param: vote
            -OPTIONAL return only legislators that voted a certain way
        """
        #TODO: Make this work... Currently this is opening one hard-coded file
        return json.load(open(self.location))

    def get_contributions(self, legislator='biden', seat='',
                          contributor_type='industry', cycle='2013'):
        """
        Get a dictionary of contributing industries and the
         amount each contributed

        FORMAT:
            contributors:
                contributor: amount
            total_received:
                amount:
                cycle:

        """
        td = TransparencyData(self.API_KEY)

        contributions = td.contributions(
            recipient_ft=legislator,
            cycle=cycle,
        )

        total = 0
        legislator_receipts = dict()
        contributors = []
        for contribution in contributions:

            contributor = dict()

            if contribution[u'organization_name'] != '':
                name = contribution[u'organization_name']
                amount = contribution[u'amount']
                contributor[name] = amount
                total += float(amount)

            elif contribution[u'contributor_name'] != '':
                name = contribution[u'contributor_name']
                amount = contribution[u'amount']
                contributor[name] = amount
                total += float(amount)

            contributors.append(contributor)

        legislator_receipts[u'contributors'] = contributors

        total_received = dict()
        total_received[u'cycle'] = cycle
        total_received[u'amount'] = total
        legislator_receipts[u'total_received'] = total_received

        print legislator_receipts

        if legislator_receipts[u'contributors'] == {}:
            return 'No contributors to {0} in {1}'.format(legislator, cycle)
        else:
            return legislator_receipts



