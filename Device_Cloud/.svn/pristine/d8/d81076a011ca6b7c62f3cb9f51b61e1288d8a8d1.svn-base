To start WebORB, open a command prompt window and change the current directory to the WebORB installation directory.
Run the following command:
    
	java -jar weborb.jar

---------------------------------------------------------------------------------------------------------------------
Before starting please update the JMS ip in the following files 

1.D:\Softwares\WebORB 5.1\WebORB 5.1\webapp\WEB-INF\flex\messaging-config.xml
under this section give the JMS implementation ip
<destination id="ActiveMQDestination">
........
<property>
                   <name>java.naming.factory.initial</name>
                   <value>org.apache.activemq.jndi.ActiveMQInitialContextFactory</value>
                 </property>
                 <property>
                   <name>java.naming.provider.url</name>
                   <value>tcp://10.234.31.156:61616</value>
                 </property> 
........
</destination>

2.D:\Softwares\WebORB 5.1\WebORB 5.1\webapp\WEB-INF\web-core-activemq.xml
under this section change the the JMS implementation ip
<web-app>

    <!-- parameter used by the ActiveMQ broker initializer. The parameter
    contains the path to start the broker on -->
    <context-param>
        <param-name>ActiveMQBrokerPath</param-name>
        <param-value>tcp://localhost:61616?trace=true</param-value>
    </context-param>
	
	
	................
</web-app>

3.D:\Softwares\WebORB 5.1\WebORB 5.1\webapp\WEB-INF\web-core-rtmp-activemq
under this section change the the JMS implementation ip
<web-app>
    <!-- parameter used by the ActiveMQ broker initializer. The parameter
    contains the path to start the broker on -->
    <context-param>
        <param-name>ActiveMQBrokerPath</param-name>
        <param-value>tcp://localhost:61616?trace=true</param-value>
    </context-param>
		................
</web-app>

4.D:\Softwares\WebORB 5.1\WebORB 5.1\webapp\WEB-INF\web-core-spring-activemq
under this section change the the JMS implementation ip
<web-app>

    <!-- parameter used by the ActiveMQ broker initializer. The parameter
    contains the path to start the broker on -->
    <context-param>
        <param-name>ActiveMQBrokerPath</param-name>
        <param-value>tcp://localhost:61616?trace=true</param-value>
    </context-param>
	
			................
</web-app>

5.D:\Softwares\WebORB 5.1\WebORB 5.1\webapp\WEB-INF\classes\jndi.properties
change the the JMS implementation ip
....
java.naming.factory.initial = org.apache.activemq.jndi.ActiveMQInitialContextFactory
java.naming.provider.url = tcp://10.234.31.156:61616
..........

6.D:\Softwares\WebORB 5.1\WebORB 5.1\webapp\WEB-INF\lib\weborb.messaging-1.0.0.jar
change the JMS implementation ip in the config.properties inside the jar