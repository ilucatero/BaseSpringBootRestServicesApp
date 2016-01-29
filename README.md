Base Spring-Boot REST Services App
=======================================

This project is aimed to show a base Spring-Boot web application implementation, along with basic storage in a NoSQL DB.

Technology
---------------------------
This is a Maven project using Spring-Boot as base Web framework, allowing to provide REST Web Services. It uses also a NoSQL BD (MongoDB) t store data documents.

Configuration
---------------------------
The configuration is done by using Spring-Boot annotations. Other Configurations can be found in the Application.yml file.

Mongo connexion conf info is in the Application.yml file, and used/configured in the "spring.config.mongo" package.

IntelliJ execution conf
---------------------------
First of all, remember to configure Maven into the IDE since sometimes us required to use a different maven installation/dist... and to run up you DB Server.

The runner Maven App configuration as next:

Command: the *mvn* params (clean, install, packaged, etc.)
> spring-boot:run

![Alt text](/images/intelliJRun_Params.PNG?raw=true)

General: Maven Home Directory
![Alt text](/images/intelliJRun_General.PNG?raw=true)

Runner: VM Options
> -Dmaven.multiModuleProjectDirectory=Your_MVN_DIST_DIR

![Alt text](/images/intelliJRun_Runner.PNG?raw=true)

Run
---------------------------
To execute Spring-Boot -which has embedded a TomCat server implementation-, you will required to run the below command (notice that this should be done in the same directory where pom.xml file is located):

>mvn spring-boot:run

Also, the "old-school" procedure to execute it is by packaging by running **mvn package** in the shell. The resulting jar file in the target folder contains all classes and libraries. To execute the jar we need to run the following command:

>java -jar target/spring-skeleton-0.0.3-SNAPSHOT.jar.
