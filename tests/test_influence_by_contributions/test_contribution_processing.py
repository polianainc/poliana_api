__author__ = 'dagilmore'

import os
import unittest
import json
from influence_by_contributions.processing import Processing


class TestContributionProcessing(unittest.TestCase):
    """
    Tests the processing of contribution data by InfluenceByContributions
    """

    def test_process_contributions_by_type(self):
        """
        - For each senator, contributions to he/she are put in a dictionary
        - Class variable contribution_totals holds all contributors that
            contributed to this list of senators within the year
        - For each contribution, a running total is logged for that contributor
        """
        #TODO: Replace integration test with unit
        processing = Processing()
        location = os.path.join(os.path.dirname(__file__), '../../data/s744-113.json')
        dump = open('../../data/dump.txt', 'wb')
        dump.write(processing.process_contributions_by_type(json.load(open(location))))


    def test_get_contributions_by_legislator(self):
        """
        Get a dictionary of contributing industries and the
         amount each contributed
        """
        #TODO: Test?
        assert False