#!/usr/bin/python

import sys
import string
import urllib
import re
import math

countries = {}
ranges = {}
p1 = re.compile(r"gi.area([0-9]*).text\s*=\s*\"([^\"]*)\";*")
p2 = re.compile(r"gi.area([0-9]*).pubrange\s*=\s*\"([^\"]*)\";*")

#data = urllib.urlopen('ranges.js').read()
data = urllib.urlopen('http://www.isbn-international.org/converter/ranges.js').read()

for line in data.split('\n'):
    m = p1.match(line)

    if m:
        countries[int(m.group(1))] = m.group(2).strip()
    else:
        m = p2.match(line)

        if m:
            country = int(m.group(1))

            if m.group(2).strip() != '':
                ranges[country] = []

                for r in m.group(2).strip().split(";"):
                    r2 = r.strip().split("-")

                    if len(r2) == 1:
                        ranges[country].append([r2[0], r2[0]])
                    elif len(r2) == 2:
                        ranges[country].append(r2);

for country in sorted(countries):
    print "#", country, "-", countries[country]

    if country in ranges:
        for range in ranges[country]:
            if range != [] and range != ['']:
                if len(range) == 2:
                    print str(country) + "-" + range[0] + "." + range[1]
                elif len(range) == 1:
                    print str(country) + "-" + range[0] + "." + range[0]

    print

#for line in sys.stdin:
#    # find country
#    
#    for i in [6,5,4,3,2,1]:
#        c = int(line[:i])
#
#        if c in countries and c in ranges:
#            range = ranges[c]
#
#            for r in range:
#                s = line[i:i + len(r[0])]
#
#                if int(s) >= int(r[0]) and int(s) <= int(r[1]):
#                    print c, s, line[i + len(r[0]):9], line[9]
#
#                    break

