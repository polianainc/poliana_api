__author__ = 'dagilmore'

import csv


def fix_csv(file='contribution_sample.csv', outfile='out.csv'):
    with open(file, 'rb') as csvfile:
        reader = csv.reader(csvfile, delimiter=',', quotechar='"')
        writer = open(outfile, 'wb')
        for row in reader:
            new_line = ''
            for value in row:
                new_line += value.replace(",", "") + ','
            new_line += '\n'
            writer.write(new_line)

if __name__ == '__main__':
    fix_csv('../../data/contributions.fec.2012.csv', '../../data/contributions2012.csv')
