# MQ Utility

<b>To build the project:</b>
<pre>
mvn clean install package
</pre>  
<sub>* Note: After packaging, an executable JAR file will be generated in target folder. </sub> 

<b>To run the MQUtil:</b>
<pre>
---------+---------+---------+---------+---------+---------+---------+---------+
java [VMargs] -jar spring-mqutil-1.2.0.jar [options] [properties]

Program arguments (options):
  -c               Count the number of message in the queue.
  -b               Browse and print all messages in the queue.
  -g               Get and print all messages in the queue.
  -e               Clean the queue.
  -f <file>        Read a file and put the content into the queue.
  -p <value>       Put a String value into the queue.


Program arguments (properties):
  --spring.config.location=file:{path}
                   The external configuration path. If profile is used, this
                   setting will be ignored.
                   
** The following program arguments are only applicable when the profile and 
external file setting are not used.           
  --host           Host Name of MQ Manager.
  --port           Port Number of MQ Manager.
  --ch             Server Connection Channel.
  --mqm            Target MQ Manager.
  --queue          Target Queue.

** Extra program arguments can be set thru profile. Please refer to default and
example properties for more information.


VM arguments (VMargs) for properties setting (-D<name>=<value>):
  -Dspring.profiles.active={profile}
                   A Spring Environment property to locate the profile-specific
                   application properties.
  -Dmqutil={profile}
                   Equivalent to spring.profiles.active. 
                   If spring.profiles.active is found, this argument will be 
                   ignored.


examples:
  > java -jar spring-mqutil-1.2.0.jar -c
      *|
      *| Count the number of message in the queue.
      *| This command searches application.properties from your 
      *| execution path. If application.properties is not found, 
      *| default properties will be loaded from the package.
      *|

  > java -jar spring-mqutil-1.2.0.jar -c --spring.config.location=file:c:\my.properties
      *|
      *| Count the number of message in the queue.
      *| This command uses spring.config.location to locate the external 
      *| properties.
      *|
  
  > java -Dspring.profiles.active=dev -jar spring-mqutil-1.2.0.jar -c
      *|
      *| Count the number of message in the queue.
      *| This command uses profile to locate the external 
      *| application-{profile}.properties from your execution path.
      *| Profile setting will override the spring.config.location, 
      *| application.properties and default properties setting.
      *|

  > java -jar spring-mqutil-1.2.0.jar -c --host=127.0.0.1 --port=6777 --ch=TEST.CHANNEL --mqm=TESTMQM --queue=TEST.SEND
      *|
      *| Count the number of message in the queue.
      *| This command supplies each properties through arguments.
      *| It is only applicable when application.properties is not found,
      *| external properties file is not provided and profile is
      *| not assigned.
      *|
    
  > java -jar spring-mqutil-1.2.0.jar -b
      *|
      *| Browser all messages in the queue.
      *|
      
  > java -jar spring-mqutil-1.2.0.jar -g
      *|
      *| Get all messages in the queue.
      *|
      
  > java -jar spring-mqutil-1.2.0.jar -f "C:\temp\datainput.txt"
      *|
      *| Read a file and put the content into the queue.
      *|
      
  > java -jar spring-mqutil-1.2.0.jar -p "ABCDEF123456"
      *|
      *| Put a String value into the queue.
      *|

notes (order of properties loading):
  1. profile assignment using -Dspring.profiles.active
     i.e. (application-{profile}.properties)
  2. external file using --spring.config.location to locate
  3. external file named application.properties
  4. program arguments (i.e. --host, --port, --ch, --mqm, --queue)
  5. default properties provided by the package
  
... Copyright (c) 2015, FCPY Studio.
---------+---------+---------+---------+---------+---------+---------+---------+
</pre>  
<br/>
<b>Release Notes:</b>
<pre>
1.2.0 - Added replyToQueue support.
1.1.0 - Refactored the code to support external properties better.
1.0.0 - Initial Release.
</pre>  
