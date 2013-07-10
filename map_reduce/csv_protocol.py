__author__ = 'dagilmore'


class CSVProtocol(object):

    def read(self, line):
        values = line.split(',')
        return values

