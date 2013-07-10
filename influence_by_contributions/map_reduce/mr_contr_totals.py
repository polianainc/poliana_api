__author__ = 'dagilmore'

from mrjob.job import MRJob
from map_reduce.csv_protocol import CSVProtocol


class ContributionTotals(MRJob):
    """
    A class for processing all contributors to a body of legislators voting
     on a bill.
    """

    DEFAULT_INPUT_PROTOCOL = CSVProtocol  # TODO Determine import from dynamo

    def map_unique_contributors(self, _, contributions):
        """
        For a json file of contributors, map the contributors to the
         their contribution amount

        INPUT FORMAT:

        """
        #TODO handle info
        yield 'recipient_ext_id', 'amount'

    def reduce_contributors(self, contributor, contributions):
        """
        For a contributor, reduce their contributions to a total sum
        """
        yield contributor, sum(contributions)