# TCP Monitor

<b>To build the project:</b>
<pre>
mvn clean install package
</pre>  
<sub>* Note: After packaging, an executable JAR file will be generated in target folder. </sub> 

<b>To run the TCPMon:</b>
<pre>
---------+---------+---------+---------+---------+---------+---------+---------+
java [VMargs] -jar spring-tcpmon-1.1.0.jar [properties]

Program arguments (properties):
  --spring.config.location=file:{path}
                   The external configuration path. If profile is used, this
                   setting will be ignored.
                   
** The following program arguments are only applicable when the profile and 
external file setting are not used.           
  --listenPort     Local Listener Port Number.
  --targetHost     Target Host Name / IP.
  --targetPort     Target Port.

VM arguments (VMargs) for properties setting (-D<name>=<value>):
  -Dspring.profiles.active={profile}
                   A Spring Environment property to locate the profile-specific
                   application properties.
  -Dtcpmon={profile}
                   Equivalent to spring.profiles.active. 
                   If spring.profiles.active is found, this argument will be 
                   ignored.

examples:
  > java -jar spring-tcpmon-1.1.0.jar
      *|
      *| Start the TCPMon.
      *| This command searches application.properties from your 
      *| execution path. If application.properties is not found, 
      *| default properties will be loaded from the package.
      *|

  > java -jar spring-tcpmon-1.1.0.jar --spring.config.location=file:c:\my.properties
      *|
      *| Start the TCPMon.
      *| This command uses spring.config.location to locate the external 
      *| properties.
      *|
  
  > java -Dspring.profiles.active=dev -jar spring-tcpmon-1.1.0.jar
      *|
      *| Start the TCPMon.
      *| This command uses profile to locate the external 
      *| application-{profile}.properties from your execution path.
      *| Profile setting will override the spring.config.location, 
      *| application.properties and default properties setting.
      *|

  > java -jar spring-tcpmon-1.1.0.jar --port=19080 --targetHost=127.0.0.1 --targetPort=9080
      *|
      *| Start the TCPMon.
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
  4. program arguments (i.e. --port, --targetHost, --targetPort)
  5. default properties provided by the package

... Copyright (c) 2015, FCPY Studio.
---------+---------+---------+---------+---------+---------+---------+---------+
</pre>  
<br/>
<b>Release Notes:</b>
<pre>
1.1.0 - Added Record List.
1.0.0 - Initial Release.
</pre>  
