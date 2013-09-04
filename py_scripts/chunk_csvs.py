reader = open('contributions.fec.2012.csv', 'rb')
rows = 0
f = open('contributions.2012.csv'.format(i), 'wb')
for row in reader:
    if rows % 250000 == 0:
        f = open('2012_contributions/contributions.2012.{0}k.csv'.format(rows/1000), 'wb')
    f.write(row)
    rows += 1


