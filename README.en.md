# security-web

#### introduces
An extremely lightweight web layer security permission authentication framework based on the java language, developed based on the JDK17 version. For specific details on supported functions and current progress, please refer to the following list.

#### currently supported features
1. Login Authentication (Supports customization and multiple authentication methods)
2. Support authentication based on login status, role information, and permission codes
3. Support multi-account architecture authentication; What is a multi-account architecture? For instance, if your system maintains multiple account tables of different types, this framework supports differentiated verification and authentication based on the types
4. Support distributed microservice systems (default local memory caching is not supported), and data caching support for distribution is required, such as based on redis, mysql caching... Such as
5. Support data caching based on the redis database
6. Supports running on projects using the Spring Boot 3.x version framework

#### current work progress
It has not been launched normally yet. This is a preview version for development. The code will be updated and pushed from time to time. Due to the support of the oauth2 protocol, the official version is yet to be released (the basic functions are relatively stable).
Support for the oauth2 protocol is still under development (temporarily unavailable).

#### Installation tutorial (to be uploaded to maven)
1. Pull branch code
2. You can directly import the project through maven or compile it into a jar package and import it into the project

#### Instructions for use
1. Introduce dependencies
2. Use the @EnabledSecurity annotation to mark it in the main program entry
3. Just start the project
4. Details refer to the demo project (https://github.com/xiaotan666/security-web/tree/master/security-web-demo)

#### note
If you have any problems using it at present, please contact me (project developer) via email **tx1611235218@gmail.com**