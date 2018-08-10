# Standalone-Java-Telematics-App
---

This standalone app is based on a Swiss Transportation API and it calculates transportation schedules with Dijkstra implementation, finds the best available route. The application is written in Java and MySQL. 
For a more detailed informational guide about the Swiss Transportation API, please do check the following link https://transport.opendata.ch/docs.html 
The application has both a UI and a terminal build.

---

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them

```
Java Version 1.6 or higher - More info follow https://java.com/en/download/manual.jsp
Netbeans IDE is used to code the application and it's the recommended choice alongside with Eclipse - https://netbeans.org/downloads/ 
JDBC Connector - Download the appropriate JAR file depending the Database. In this case from MySQL use this, https://dev.mysql.com/downloads/connector/j/
MySQL Database - Set a database server and create the a "go_swiss" schema with the SQL code provided in Github

```

### Installing

A step by step series of examples that tell you how to get a development env running

Step 1

```
clone project from github using the following command
```
`git clone https://github.com/HUADevs/Standalone-Java-Telematics-App.git`


Step 2

```
Open project with IDE and perform changes in the database credentials in case you build your own database
```

Step 3 (Case of changing DB credentials)

```
Need to change the encryption file Login.encrypted to accommodate your own. 
Use the encrypt method to achieve that. 
```

End with an example of getting some data out of the system or using it for a little demo

## Running the tests

Main Page:
!(https://github.com/HUADevs/Standalone-Java-Telematics-App/blob/master/go_swiss.png)
Location Option:
!(https://github.com/HUADevs/Standalone-Java-Telematics-App/blob/master/goSwiss1.png)
Connection Option:
!(https://github.com/HUADevs/Standalone-Java-Telematics-App/blob/master/goswiss3.png)

## Deployment

As it is standalone application the only deployable entity is the database. A very useful tool is phpMyAdmin for DB Deployment (https://www.phpmyadmin.net/)

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management


## Authors

* **Evan Markou** - [EvanMark](https://github.com/EvanMark)
* **Giorgos Alexandridis** - [galexandridis](https://github.com/galexandridis)
* **Marios Alexiou** - [iceberg24](https://github.com/iceberg24)


## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details


