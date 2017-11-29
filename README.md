# Team Delta Project

This is the project for team Delta of CMPT 373's fall 2017 offering, and the latest version is hosted at http://cmpt373-1177d.cmpt.sfu.ca:8080.

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

First, we need to set up the `src/main/resources/application.properties` file to reflect database properties. Copy the file `src/main/resources/application.properties-template` to `src/main/resources/application.properties` (remove the `-template`).
Fill in the fields surrounded by `<>`, such as `<ADD-EMAIL-ADDRESS-HERE>`.

**Note:** Don't change the `application.properties` file in `src/test/resources`. This is file is only for tests.

Build this application with [gradle](https://gradle.org/install/), and run the resulting jar file.

On Linux you may do this in the terminal:
```
$ gradle build
$ java -jar build/libs/*.jar
```

## Intergration
The parameter `spring.jpa.hibernate.ddl-auto` should only be set to `create` for the first time the server is run.
After that it should be set to `none`. **THIS IS IMPORTANT**. According to spring.io guides, the following are the possible values for the parameter.
```
none:           This is the default for MySQL, no change to the database structure.
update:         Hibernate changes the database according to the given Entity structures.
create:         Creates the database every time, but don’t drop it when close.
create-drop:    Creates the database then drops it when the SessionFactory closes.
```
If the server is restarted with the parameter set to `create`, all data in the database tables will be erased.

## Dependencies
Gradle, Java 8, mysql

## Directory Structure
Our directory structure is as follows:
`Documentation/` has the outline of our database inside.
`src/` follows the industry standard for our java code
Everything else is build related (.gradle and node_modules)

## How to use
* The root of the application is where users will fill out and submit forms.
   * Users have the option of authorizing their own request, or sending an authorization request to somebody else (by email).
   * Users will be emailed a link to their form where they can edit it until it has been approved or denied by security.

* /security is where security members can see a list of all requests (security members should bookmark this page).
   * The requests can be filtered, sorted, and searched.
   * Clicking the gear icon in the top right gives two options for exporting requests.
      * Export All to CSV: downloads a .csv of all requests in the database.
      * Export Selected to CSV: downloads a .csv of all requests currently visible on the screen (none that have been filtered out).
   * Clicking on a request will bring you to the details page of that request

* Request details page shows all information related to that request.
	* Security members can fill out fields that are security specific like Recieving Security supervisor. Note: these must be filled out before approving the request.
	* Guards can also be added to this screen, and can vary in hourly rate, hours, and type.
	* There are several export options here as well:
	   * Export Request to CSV: downloads a .csv of this specific request.
	   * Export Guard list to CSV: downloads a .csv of all guards that have been entered to this form.
	   * Generate Invoice: downloads a .pdf invoice with charges for all guards added to the form.
	* Security members can either approve, reject, or update the request at the bottom of the page:
	   * Approve: Approve the request. Options to send emails that notify relevant parties will be given.
	   * Reject: Reject the request. Security members can enter a reason as to why the request has been rejected which will be emailed to the submitter.
	   * Update: Saves the form in it's current state for returning to later.
