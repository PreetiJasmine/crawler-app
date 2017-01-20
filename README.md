# crawler-app

AUTHOR - Preeti Jasmine
DATE - 19 JAN, 2017
VERSION - 1.0

Basic Functionality
------------------------------------------------------------------------------
The Web Crawler application visit all the pages within a  given domain (say http://wiprodigital.com). As of now, we are restricting to one domain. With this program, the user will be be able to fetch details about the links to other pages, links to external URLs as well as the links to static content (for example, images) within the same domain.


Design & Implementation
--------------------------------------------------------------------------------
The application is implemented in JAVA. 

We have a class named ‘CrawlerForWipro.java’ that contains the logic of reading through the pages within a given domain and display all the related links (links to other pages and external links as well).  


Installation / Execution Procedure
---------------------------------------------------------------------------
i. You need to have jre7 installed on your system as the application is JAVA 7 compliant.  
ii. Ensure “jsoup-1.7.2.jar” jar is available in the project build path
	
	
When you run the program as a Java application, the user will be prompted to enter the output folder path where the output file (an HTML format) will be stored. 
The user should open the output HTML file on a browser.


Major bottlenecks
------------------
No
