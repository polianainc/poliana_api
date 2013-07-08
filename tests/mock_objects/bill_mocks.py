__author__ = 'dagilmore'

import json
import numpy.random as rand


def legislator_votes_by_yea_nay():
    """
    A dictionary of votes arranged by yea and nay

    FORMAT:
        votes: [
            yea: [
                pol1,
                pol3,
                ...
            ],
            nay: [
                pol2,
                pol4,
                ...
            ]
        ]
    """
    votes = dict()
    yea = []
    nay = []
    for x in xrange(20):
        if x % 2 == 0:
            yea.append('pol{0})'.format(x))
        else:
            nay.append('pol{0})'.format(x))
    votes['yea'] = yea
    votes['nay'] = nay
    return votes


def legislator_vote_detailed():
    """
    A dictionary of legislators:

    FORMAT:
        legistlators: [
            {
                name:
                vote:
                party:
                contributors: [
                    {
                        name:
                        contribution:
                    }
                ]
            }
            ...
        ]
    """
    parties = ['d', 'r', 'i']
    legislators = []

    contributors = []
    for x in xrange(3):
        contributor = dict()
        contributor['name'] = x
        contributor['contribution'] = x
        contributors.append(contributor)

    vote = lambda x: 'yea' if x % 2 == 0 else 'nay'
    for x in xrange(30):
        politician = dict()
        politician['name'] = x
        politician['vote'] = vote(x)
        politician['party'] = parties[rand.randint(0, 3)]
        politician['contributors'] = contributors
        legislators.append(politician)

    return legislators


def contributor_totals():
    """
    A dictionary of contributor totals

    FORMAT:
        contributor: [
            {
                name:
                yea_contribution: {
                    total:
                    d_influence:
                    r_influence:
                    i_influence:
                }
                nay_contribution: {
                    total:
                    d_influence:
                    r_influence:
                    i_influence:
                }
            }
            ...
        ]
    """
    return 'TODO'

def construct_mock_bill():
    """
    This is the original format of bill returns; it should not be used but for
     reference
    """
    yoyoma = dict()
    yoyoma['industry'] = []

    party = ['D', 'R', 'I']

    yoyoma['info'] = {
        'bill_id': '1234',
        'subject': 'fucking_shit_up',
    }

    yea = dict()
    yea_sums = {
        'industry1': 0,
        'industry2': 0,
        'industry3': 0,
        'industry4': 0,
        'industry5': 0,
    }

    nay_sums = industries = {
        'industry1': 0,
        'industry2': 0,
        'industry3': 0,
        'industry4': 0,
        'industry5': 0,
    }

    for x in xrange(37):
        yea['pol{0}'.format(x+37)] = [
            {
                'industries': [
                    {'industry1': rand.randint(0, 24000)},
                    {'industry2': rand.randint(0, 4000)},
                    {'industry3': rand.randint(0, 1000)},
                    {'industry4': rand.randint(0, 9000)},
                    {'industry5': rand.randint(0, 100)},
                ]
            },
            {'party': party[rand.randint(0, 3)]},
        ]

        i = 1
        for key in yea['pol{0}'.format(x+37)][0]['industries']:
            yea_sums['industry{0}'.format(x)] += yea['pol{0}'.format(x+37)][0]['industries'][key]
            i += 1

    yoyoma['yea'] = yea

    nay = dict()

    for x in xrange(63):

        nay['pol{0}'.format(x+37)] = [
            {
                'industries': [
                    {'industry1': rand.randint(0, 18000)},
                    {'industry2': rand.randint(0, 40000)},
                    {'industry3': rand.randint(0, 25000)},
                    {'industry4': rand.randint(0, 20000)},
                    {'industry5': rand.randint(0, 30000)},
                ]
            },
            {'party': party[rand.randint(0, 3)]},
        ]

        for key, value in nay['pol{0}'.format(x+37)][0].iteritems():
            nay_sums[key] += value

    yoyoma['nay'] = nay

    for industry in industries.iterkeys():
        industries[industry] = {
            'yea': yea_sums[industry],
            'nay': nay_sums[industry],
        }



    yoyoma['industries'] = industries


    print json.dumps(yoyoma)


if __name__ == '__main__':
    print '#__________Legislator Votes: Yea/Nay__________#'

    print legislator_votes_by_yea_nay()

    print '#__________Legislator Votes Detailed__________#'

    print legislator_vote_detailed()

    print '#______________Contributor Totals_____________#'

    print contributor_totals()

    # print '#_____________Origininal Mock Bill____________#'
    #
    # print construct_mock_bill()
