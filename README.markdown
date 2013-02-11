Daily Email Service Web Application
==================

The Daily Email Service provides a summary email of news and weather information to subscribed users.  The users set their weather location and subscribe to RSS feeds.  They have the option of including various combinations of RSS feeds and weather information.  Additionally, an RSS reader is available to the user for easy browsing of their feeds.  Also, the user has the ability to generate a unique link that can be bookmarked for easy access to their daily email summary in real time.  The website is optimized to work on mobile devices and larger screens.

I started this project for a couple reasons.  First, I wanted a nice summary email that I could customize along with the current temperature every morning before I get ready for work.  Second, I wanted to learn the Spring Framework for building Java web applications.  The main technologies I am using in this project are Spring, Spring Data JPA with Hibernate, Apache Velocity, and ZURB Foundation CSS 3.2 for a responsive layout.  I still consider this project to be in alpha, but all core functionality works and I encourage anyone interested to try it out.

I am using Trello to track bugs, enhancements, and work in progress.  The project board is available for public viewing [here.](https://trello.com/board/daily-email-app/510ddd3589fa1cee62002abd)

You can see this code in action [here.](http://www.vtmnts.com/)

Usage
-----

I am using Apache Maven for dependency management.  In the resources folder, I am including a MySQL schema file to setup the database.  Additionally there is a properties file for configuring the application.  In the WEB-INF/configuration folder, there are four files that need to be configured once the database is setup:

 - database.properties - Set database properties for using Hibernate and connecting to the database
 - socialConfig.properties  - Set keys for connection factories (Facebook shown as an example)
 - emailConfig.properties - Set the email addresses for the daily email and the administrator for errors
 - applicationContext.xml - Primarily for setting up your email connection

Features
------------

- Schedule daily email delivery of customized information
- Subscribe to RSS feeds you want to follow
- Receive weather information from the National Weather Service
- Use the RSS reader to view your feeds any time
- Create/Bookmark a unique link to view your daily email on the go
- Message Queue for delivering emails later if there is an email outage
- RSS links are validated before they are added to the database
- RSS links that fail 3 connection attempts are disabled and an email is sent to the administrator
- RSS feed links are stored in the database to make page loads faster and email generation faster
- RSS feeds are updated every 30 minutes

Roadmap
-------

From a long term perspective, I am hoping to continue to enhance the RSS reader into more of a portal page.  I'd like to add in weather information and possibly incorporate radar/alerts using my other github project (nws-wx-alerts)[https://github.com/npaulus/nws-wx-alerts].  Also, I laid the groundwork for incorporating information from a user's Facebook news feed.  I'd like to add in Twitter and LinkedIn as well. 


License
-------

   Copyright [2013] [Nate Paulus]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   [http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.