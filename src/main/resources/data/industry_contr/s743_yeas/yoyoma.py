old = open('industry_contr_s743_113_yeas_6months.csv')
new = open('newishyo', 'wb')

for line in old:
    new.write(line.replace('\t', ''))