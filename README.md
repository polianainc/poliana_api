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