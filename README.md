# Team Delta Project

This is the project for team Delta of CMPT 373's fall 2017 offering.

## Setting up the Database

I'm taking sections from this tutorial: https://spring.io/guides/gs/accessing-data-mysql/

Open your MySql client in the terminal. On Linux, you can do this with the following command:

```sh
$ sudo mysql --password
```

Then run the following commands to create a database and user

```sh
mysql> create database db_example; -- Create the new database
mysql> create user 'springuser'@'localhost' identified by 'ThePassword'; -- Creates the user
mysql> grant all on db_example.* to 'springuser'@'localhost';
```

## Running Web Application

First, we need to set up the `src/main/resources/application.properties` file to reflect database properties. Copy 
and paste the following into it, if it doesn't already exist. Notice the username and password and databaase name is 
the same from the previous section.

```shell
spring.jpa.hibernate.ddl-auto=create
spring.datasource.url=jdbc:mysql://localhost:3306/db_example
spring.datasource.username=springuser
spring.datasource.password=ThePassword
```

Build this application with [gradle](https://gradle.org/install/), and run the resulting jar file.

On Linux you may do this in the terminal:
```
$ gradle build
$ java -jar build/libs/*.jar
```

With the application running, open your browser to http://localhost:8080 to see it.

To see the database demo navigate to:
http://localhost:8080/demo/add?name=Fin the Human&email=fin@domain.ooo

Then navigate to:
http://localhost:8080/demo/all