__author__ = 'dagilmore'

import unittest
from influence_by_contributions.collection import Collection


class TestContributionCollection(unittest.TestCase):
    """
    Tests the collection of contribution data by
     InfluenceByContributionsProcessing
    """

    def test_get_votes(self):
        """
        Return a dictionary of vote information for a particular bill
         The return info includes meta-data about the bill
        """
        pass

    def test_get_contributions(self):
        """
        A dictionary of contributors and their contribution amount to a given
         legislator in a specific cycle is returned
        """
        collection = Collection()

        contributions = collection.get_contributions(
            legislator='mccain',
            cycle='2009',
        )
        assert 'contributors' in contributions
        assert 'total_received' in contributions
        assert 'cycle' in contributions['total_received']
        assert 'amount' in contributions['total_received']

    def test_get_contributions__no_result(self):
        """
        No result returns an error message
        """
        collection = Collection()

        legislator = 'MacDaddy'
        cycle = '1800'

        contributions = collection.get_contributions(
            legislator=legislator,
            cycle=cycle,
        )
        assert contributions == 'No contributors to MacDaddy in 1800'