# TCP Client

<b>To build the project:</b>
<pre>
mvn clean install package
</pre>  
<sub>* Note: After packaging, an executable JAR file will be generated in target folder. </sub> 

<b>To run the TCPClient:</b>
<pre>
---------+---------+---------+---------+---------+---------+---------+---------+
java [VMargs] -jar spring-tcpclient-1.0.0.jar [properties]

Program arguments (properties):
  --spring.config.location=file:{path}
                   The external configuration path. If profile is used, this
                   setting will be ignored.
                   
** The following program arguments are only applicable when the profile and 
external file setting are not used.           
  --host           Host Name/IP of the remote server.
  --port           Port Number of the remote server.
  --file           File Path of the request data.

** Extra program arguments can be set thru profile. Please refer to default and
example properties for more information.


VM arguments (VMargs) for properties setting (-D<name>=<value>):
  -Dspring.profiles.active={profile}
                   A Spring Environment property to locate the profile-specific
                   application properties.
  -Dtcpclient={profile}
                   Equivalent to spring.profiles.active. 
                   If spring.profiles.active is found, this argument will be 
                   ignored.


examples:
  > java -jar spring-tcpclient-1.0.0.jar
      *|
      *| This command searches application.properties from your 
      *| execution path. If application.properties is not found, 
      *| default properties will be loaded from the package.
      *|

  > java -jar spring-tcpclient-1.0.0.jar --spring.config.location=file:c:\my.properties
      *|
      *| This command uses spring.config.location to locate the external 
      *| properties.
      *|
  
  > java -Dspring.profiles.active=dev -jar spring-tcpclient-1.0.0.jar
      *|
      *| This command uses profile to locate the external 
      *| application-{profile}.properties from your execution path.
      *| Profile setting will override the spring.config.location, 
      *| application.properties and default properties setting.
      *|

  > java -jar spring-tcpclient-1.0.0.jar --host=127.0.0.1 --port=9080 --file="C:\TEMP\requestData.txt"
      *|
      *| This command supplies each properties through arguments.
      *| It is only applicable when application.properties is not found,
      *| external properties file is not provided and profile is
      *| not assigned.
      *|


notes (order of properties loading):
  1. profile assignment using -Dspring.profiles.active
     i.e. (application-{profile}.properties)
  2. external file using --spring.config.location to locate
  3. external file named application.properties
  4. program arguments (i.e. --host, --port, --file)
  5. default properties provided by the package
  
... Copyright (c) 2015, FCPY Studio.
---------+---------+---------+---------+---------+---------+---------+---------+
</pre>  
<br/>
<b>Release Notes:</b>
<pre>
1.0.0 - Initial Release.
</pre>  
