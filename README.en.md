# security-web

#### Introduction
A security permission authentication framework based on the java web layer

#### Software Architecture
Developed based on JDK17
It is currently compatible with springboot3.0+
Support distributed microservice systems
Supports Redis-based data caching, with default data caching in memory

#### currently supports features
1. Login authentication (supports customization and multiple authentication methods)
2. Based on login authentication
3. Character authentication
4. Permission code authentication
5. Support custom authentication methods

#### To be improved
1. Currently, it is a development preview version. The official version is to be released
2. Support for oauth2 is still under development (temporarily unavailable)
3. bug to be tested


#### Installation Guide

1. Pull the branch code
2. You can directly import the project through maven or compile it into a jar package and then import it into the project

#### Instructions for Use

1. Introduce dependencies
2. Use the @EnabledSecurity annotation to mark it in the main program entry
3. Just start the project

#### Note
If you have any questions, please contact me (project developer) at **tx1611235218@gmail.com**.