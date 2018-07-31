#!/usr/bin/python
#encoding=utf-8

import os
from xml.dom.minidom import parse
import xml.dom.minidom

path = "/software/servers/azkaban-executor-2.5.0/bin/projects"
pId = 0

for p in os.listdir(path):
    if os.path.exists(path+"/"+p+"/report_job/report_framework/report_data/") and float(p)*10000 > pId:
        pId = float(p)*10000

authorized = {}
path = path+"/"+str(pId/10000)+"/report_job/report_framework/report_data/"
for xmlfile in os.listdir(path):
    if ".xml" not in xmlfile:
        continue

    DOMTree = xml.dom.minidom.parse(path+xmlfile)
    collection = DOMTree.documentElement
    report = collection.getElementsByTagName("template")
    email = []
    if len(report) < 1:
        continue
    report = report[0].firstChild.data
    principal = collection.getElementsByTagName("principal")
    if len(principal) > 0 and principal[0].firstChild is not None:
        email += principal[0].firstChild.data.strip().split(",")
    to = collection.getElementsByTagName("to")
    if len(to) > 0 and to[0].firstChild is not None:
        email += to[0].firstChild.data.strip().split(",")
    cc = collection.getElementsByTagName("cc")
    if len(cc) > 0 and cc[0].firstChild is not None:
        email += cc[0].firstChild.data.strip().split(",")

    for user in email:
        user = user.strip()
        if user == "":
            continue
        elif user not in authorized:
            authorized[user] = []
        if report not in authorized[user]:
            authorized[user].append(report)
        else:
            continue

f = open('authorized', 'w')
for user in authorized:
    content = user+":"+",".join(authorized[user])+'\n'
    f.write(content.encode('utf-8'))
f.close()
