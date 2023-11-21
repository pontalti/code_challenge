# code_challenge

1. My Dev enviroment 👍
   - Windows 10
   - Eclipse IDE for Enterprise Java and Web Developers
   		- Eclipse Plugins
   			- Spring Tools 4 for Eclipse (Spring Tool Suite 4 - 4.20.1.RELEASE ) 
   			- Java 21 support for Eclipse
   			- Project Lombok v1.18.30 
   - JDK 21
   - Maven - 3.8.7

2. If necessary install the JDK 21, download it on the link below
	- ```
	   https://jdk.java.net/21/
	   ```
	- Choose your distribution and install the JDK
	- Create the Java Home
		- Windows
			- ``` 
			   JAVA_HOME = [YOUR_PATCH]\jdk-21
			  ```
		- Linux
			- ``` 
			  JAVA_HOME = [YOUR_PATCH]/jdk-21
			  ```
	- Put the JAVA_HOME on the System Patch
		- For Windows
			- ```
			   %JAVA_HOME%\bin
			  ```
		- For Linux 
			- ``` 
			   export PATH=$JAVA_HOME/bin:$PATH 
			  ```
	- Test JDK on command line
		- ``` 
		   java -version 
		  ```		

3. If necessary install Maven, download it on the link below
	- ``` 
	   https://maven.apache.org/download.cgi 
	   ```
	- Extract compressed file in your prefered tool folder.
	- Create the M2_HOME
		- Windows
			- ``` 
			   M2_HOME = [YOUR_PATCH]\apache-maven-3.8.7 
			  ```
		- Linux
			- ```
			   M2_HOME = [YOUR_PATCH]/apache-maven-3.8.7
			  ```
	- Put the Maven on the System Patch
		- For Windows
			- ```
			  %M2_HOME%\bin
			  ```
		- For Linux
			- ```
			   export PATH=$M2_HOME/bin:$PATH
			  ```
	- Test Maven on command line
		- ```
		   mvn --version
		  ```

4. If necessary install your favorite IDE with support to JDK 21.

5. if necessary Install the project Lombok on your IDE, follow the instruction on the link below.
	- ```
	   https://projectlombok.org/setup/overview
	  ```

6. Open the project in your favotite IDE

7. To build please.
	- Go to the project root folder.
	- Run the command below.
		- ```
		   mvn -U clean install package spring-boot:repackage
		  ```

8. To run the SpringBoot application on Windows CMD.
	- Go to the project root folder.
	- Run the command below to use the embedded input.txt.
		- ```
		   mvn spring-boot:run
		  ```
	- Run the command below to use an external input file.
		- ```
		   mvn spring-boot:run -Dspring-boot.run.arguments=<PATH_AND_FILENAME>
		  ```
		- ```
		   Ex.: mvn spring-boot:run -Dspring-boot.run.arguments=C:\dev\input.txt
		  ```
9. To run the SpringBoot application on Windows PowerShell.
	- **Attention 1:** the Windows PowerShell does not run the command ``` mvn spring-boot:run ``` the same way as Windows CMD and Linux.
	- **Attention 2:** On Windows PowerShell you have to include the file with you complete path as the example below.
	- Go to the project root folder.
	- Run the command below using an external input file.
		- ```
		   java -jar .\target\code_challenge.jar <PATH_AND_FILENAME>
		  ```
		- ``` 
		   Ex.: java -jar .\target\code_challenge.jar C:\dev\input.txt
		  ```
		
10. To run the SpringBoot application on Ubuntu Linux.
	- Go to the project root folder.
	- Run the command below to use the embedded input.txt.
		- ```
		   mvn spring-boot:run
		  ```
	- Run the command below to use an external input file.
		- ```
		   mvn spring-boot:run -Dspring-boot.run.arguments=<PATH_AND_FILENAME>
		  ```
		- ```
		   Ex.: mvn spring-boot:run -Dspring-boot.run.arguments=/home/gustavo/sw/input.txt
		  ```