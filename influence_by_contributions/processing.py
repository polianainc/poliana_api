__author__ = 'dagilmore'

import os
import json
from influence_by_contributions.collection import Collection


class Processing(object):
    """
    Find correlations between contributions to politicians and
    their voting patterns.

    BILL INPUT FORMAT:

    {
      "bill": {
        "congress": 113,
        "number": 744,
        "title": "A bill to provide for...",
        "type": "s"
      },

      .....META INFO.....

      "votes": {
        "Nay": [
          {
            "display_name": "Barrasso (R-WY)",
            "first_name": "John",
            "id": "S317",
            "last_name": "Barrasso",
            "party": "R",
            "state": "WY"
          },
          ...
        ],
        "Yea": [
          {
            "display_name": "Barrasso (R-WY)",
            "first_name": "John",
            "id": "S317",
            "last_name": "Barrasso",
            "party": "R",
            "state": "WY"
          },
          ...
        ]
    }


    EXAMPLE RESPONSE:
    {
       "info":{
          "bill_id":"1234",
          "subject":"fucking_shit_up"
       },
       "yea":[
          {"pol40":[
             {"industry5":90},
             {"industry4":4930},
             {"industry3":530},
             {"industry2":3520},
             {"industry1":18730}
          ]},
          ...
       ],
       "nay":[
          {"pol84":[
             {"industry5":18100},
             {"industry4":460},
             {"industry3":8590},
             {"industry2":870},
             {"industry1":17367}
          ]},
          ...
       ],
       "industries": [
          {"industry2":[
             {"yea":70930},
             {"nay":1172791}
          ]},
          {"industry1":[
             {"yea":417548},
             {"nay":604272}
          ]}
       ]
    }

    """

    #TODO: Subdivide this algorithm and/to employ hadoop
    #TODO: Construct string operations to identify name variances
    def process_contributions_by_type(self, bill,
                                      contributor_type='industry'):
        """
        - For each legislator, contributions to her/him are put in a dictionary
        - Class variable contribution_totals holds all contributors that
            contributed to this list of legislators within the year
        - For each contribution, a running total is logged for that contributor
        """
        #We will keep a tally of all contributions here
        contribution_totals = dict()
        contribution_totals['Yea'] = {}
        contribution_totals['Nay'] = {}

        collection = Collection()


        #iterate through 'yeas' and 'nays' separately
        for decision in bill['votes']:
            x = 0
            #for each legislator (the vote variable) get contribution info
            for vote in bill['votes'][decision]:
                # if x > 2:
                #     break
                # else:
                #     x += 1
                contributions = collection.get_contributions(vote['last_name'])['contributors']
                for contribution in contributions:

                    #if the contributor is already logged in contribution_totals
                    #we need to update the values in the dictionary
                    for key in contribution:
                        if key in contribution_totals[decision]:
                            if decision == 'Yea':
                                contribution_totals[decision][key]['amount'] += float(contribution[key])
                                contribution_totals[decision][key]['accounts'] += 1
                            elif decision == 'Nay':
                                contribution_totals[decision][key]['amount'] += float(contribution[key])
                                contribution_totals[decision][key]['accounts'] += 1

                        #if this is the first occurrence of the contributor
                        #we need to initialize the key, value pair
                        else:
                            contributor = dict()
                            contributor['amount'] = float(contribution[key])
                            contributor['accounts'] = 1
                            if decision == 'Yea':
                                contribution_totals[decision][key] = contributor
                            elif decision == 'Nay':
                                contribution_totals[decision][key] = contributor

        return json.dumps(contribution_totals)


if __name__ == '__main__':
    processing = Processing()
    location = os.path.join(os.path.dirname(__file__), '../../data/s744-113.json')
    dump = open('../../data/dump.txt', 'wb')
    dump.write(processing.process_contributions_by_type(json.load(open(location))))