#Installation
---
##Requirements
> maven(3.0.3)

> jdk (7)

> tomcat (7.0.42)

##Requirements for running it all locally
> cloudera cdh4 (Impala 1.1, Hive 0.11, Hadoop 1 or greater)



##Getting Started
>###1. Pull down the project

>###2. Run mvn install, compile, and package

>###3. Setup Tomcat:

>>a. Project Structure -> Artifacts 
>>>1. add Web Application Exploded PolianaAPI

>>>2. Add WEB-INF top level directory
>>>>* Add classes directory, drag data-manager compiled output to this directory
>>>>* Add lib directory, drag all Maven dependencies to this directory

>>b. Edit Configurations -> Add Tomcat Server(local)
>>>* Add the PolianaAPI artifact to the server


GLOBAL PARAMETERS:
       ?format=json/csv

       ?api_key=XXX
       
       ?fields

       ?start=1010101&end=12309314 
       ?year=2003
       ?congress=110  DEFAULT current
       
       ?compare_to=analysis_type

/politician 

       /contributions
              /organizations
                     ?politician_id=XXXXXX *
                     ?organization_id=XXXXX 
                     
              /industries
                     ?politician_id=XXXXXX *
                     ?industry_id=XXX
                     ?category_id=XXXXX
                     
              /pacs
                     ?politician_id=XXXXXX *
                     ?pac_id=XXXX 
       
       /expenditures

       /committees

       /votes

       /sponsorships

/industry

       /contributions
              ?industry_id=XXX **
              ?category_id=XXXXX **
              ?chamber=s


       /memberships


/pac

       /contributions 

              /pacs
                     ?pac_id=XXX *
                     ?other_pac_id=XXX
                     ?chamber=s

              /politician
                     ?pac_id *
                     ?politician_id
                     ?chamber=s


/bill

       /amendments
       /votes
       /related

/congress
