__author__ = 'dagilmore'

import csv
import matplotlib.pyplot as plt


def plot():
    plt.plot([1,2,3,4])
    plt.ylabel('some numbers')
    plt.show()


def contr_distribution(infile, outfile):
    file = open(infile)
    outfile = open(outfile, 'wb')
    new_line = 'contributor_id,bias,difference,count,sum,average,\n'
    outfile.write(new_line)
    for line in file:
        items = line.split('\t')
        new_line = ''

        try:
            diff = float(items[2]) - float(items[4])
            if diff > 0:
                bias = 'yea'
                new_line += items[0] +','+ bias +','+ str(diff) +','
                new_line += items[2] +','+ items[1] +','+ str(float(items[1])/float(items[2])) +',\n'
                outfile.write(new_line)
            elif diff < 0:
                bias = 'nay'
                diff *= -1
                new_line += items[0] +','+ bias +','+ str(diff) +','
                new_line += items[4] +','+ items[3] +','+ str(float(items[3])/float(items[4])) +',\n'
                outfile.write(new_line)

        except Exception, e:
            print items[2] + ' ' + items[4] + ' ' + str(e)



if __name__ == '__main__':
    contr_distribution(
        infile='bill_samples/sample_balanced_output.txt',
        outfile='bill_samples/s47_diff.csv'
    )
