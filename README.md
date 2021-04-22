# Java React Todo

[![Build Status](https://www.travis-ci.com/RyanEllingson/java-react-todo.svg?branch=master)](https://travis-ci.com/RyanEllingson/java-react-todo)

Check out the deployed app at https://java-react-todo.herokuapp.com/

This is a todo-list application with a React front end and a Java back end interfacing with a PostgreSQL database.  It was built to practice and demonstrate some of the more sophisticated web application infrastructure I am familiar with at this time.  It implements a multi-layer architecture on the back end, with a database layer, a business logic layer, and an HTTP web server layer.  It uses JSON Web Token-based user authentication, with hashed and salted passwords stored in the database.  It also uses email to allow for logging in via a randomly-generated code sent to the email address on file if the user forgets their password.  On the front end, the React app uses the Context API to manage user authentication state across the application, as well as client-side routing.

## Using the App

Upon loading the app, the user is sent to the Login page.  Here they are presented with a form asking for the email address and password associated with their account.

![screenshot 1](src/main/resources/Screenshot1.jpg)

If the user enters invalid information in the form, the server will respond with error messages, which are displayed to the user beneath the appropriate form fields.

![screenshot 2](src/main/resources/Screenshot2.jpg)

If the user does not yet have an account, they can navigate to the Register page by either clicking on the link at the bottom of the login form, or clicking on the Register link in the navbar.  Here the user will be presented with a form asking for information required to create a new account.

![screenshot 3](src/main/resources/Screenshot3.jpg)

Again, if there are any issues with the user's input, error messages are returned from the server and displayed on the form.

![screenshot 4](src/main/resources/Screenshot4.jpg)

If the user has an account, but cannot remember their password, they may instead click on the "Login via email" link on the Login form.  This will take them to a form asking for the email associated with the account.

![screenshot 5](src/main/resources/Screenshot5.jpg)

Entering a valid email address and clicking submit will cause the application to send an email to the user's email address containing a random sequence of characters (or "reset code") to be used as a one-time password.  It will also present a form to the user where they can input the reset code.

![screenshot 6](src/main/resources/Screenshot6.jpg) 
![screenshot 7](src/main/resources/Screenshot7.jpg)

Upon successfully logging in or registering an accound, the user is taken to the home page, where they can see the new todo form and all existing todos for that user.

![screenshot 8](src/main/resources/Screenshot8.jpg)

Using the "Add a new todo" form, the user can enter the description of a new todo they would like to save, as well as choose whether it has been completed or not.

![screenshot 9](src/main/resources/Screenshot9.jpg)

Clicking the "Submit" button adds the todo to the database, and the new todo is displayed on the bottom of the list of todos.

![screenshot 10](src/main/resources/Screenshot10.jpg)

The user can click on the "Completed" or "Not completed" text on the todo to toggle between the two states.  They can also click the "X" button at the top right of a todo to delete it entirely.

![screenshot 11](src/main/resources/Screenshot11.jpg)

A user may update their user information by clicking the "Update Info" link in the navbar.  Here they can change their first name, last name, or email address, and, provided all input is valid, clicking the "Submit" button will update this information in the database.

![screenshot 12](src/main/resources/Screenshot12.jpg) 
![screenshot 13](src/main/resources/Screenshot13.jpg)

Finally, a user may change their password by clicking the "Change Password" link in the navbar.  Here they must enter and confirm the password they would like to change to, and like all other forms any invalid user input is communicated to the user on the form.  If there are no issues with the submission, the user's password is updated in the database for when they next log in.

![screenshot 14](src/main/resources/Screenshot14.jpg)

## Techniques and Technologies Used

As previously mentioned, the main purpose of this project was to practice and demonstrate a variety of security and architectural features that can be leveraged for use in future projects with an end use hopefully more interesting than a simple todo list.

The project uses a PostgreSQL database for storing data on registered users and their todos.  It is comprised of a Users table and a Todos table, with each entry in the Todos table containing a user id used as a foreign key to reference an entry in the Users table.  Two separate databases are actually used by the application - the main production database used by the deployed application, as well as a test database that is cleared and rebuilt by a stored procedure each time the test suite is run.  All database transactions are rigorously tested using JUnit.  Additionally, a Singleton design pattern is implemented for the database connection instance - the connection is created only once each time the application is launched, and this same instance is saved and returned each time a new database transaction is requested.

The business logic is handled by a Service layer, which contains classes that perform all validations on data being sent by the client, as well as that interface with the Data layer.  The services classes take instances of the data (or "repository") classes as dependencies.  This allows test doubles to be provided as alternative dependencies in the testing environment in order to test the business logic independently from the database logic.

Web server functionality is provided by an embedded Apache Tomcat server.  The application implements a Front Controller design pattern - a single "Master Servlet" listens for all incoming HTTP requests, and passes the request and response objects to a set of helper classes depending on the method of the request (GET, POST, etc).  These helper classes then further parse the request and execute the appropriate Controller method based on the request URI.  These Controller methods all call upon necessary Service and other utility methods and respond to the client with a JSON response.  The jackson-databind library is used for parsing and creating JSON objects in the back end.  All API routes were tested using Postman before being used by the front end application.

The front end is a React application built using functional components and the hooks API.  React router is used for client-side routing, and Context is used to manage user authentication information.  Axios is used for sending HTTP requests.  Bootstrap was used for front end layout and styling.  The frontend-maven-plugin is used to facilitate creating a production build of the front end when the project is deployed and moving it to the correct directory where it can be served to the client.

User authentication is handled using JSON Web Tokens.  The JJWT library is used to create and decode the JWT in the back end, and after successful login or registration a JWT is returned to the client.  The client then saves the JWT in local storage for retrieval in the event the user closes the browser or a Redirect event is forced to take place due to route protection.  Additionally, the JWT is decoded on the client side using the jwt_decode library and saved in the authentication context provided to the rest of the app.  Finally, the JWT is added as a header to all subsequent Axios HTTP requests.  If a request is made and the server is unable to validate the JWT provided (either one is not present or it has expired), it responds with a 401 status code, which triggers a Logout to take place and clears all saved authentication information on the client.

User passwords and reset codes are securely stored in the database by running them through the PBKDF2 hashing algorithm after applying a randomly-generated salt.  The hashed password is saved in the database, concatenated with the salt used to create it.  When a user attempts to log in, the hashed password/salt is retrieved from the database and split using a delimiter character.  The salt is applied to the user-supplied password and then run through the hashing algorithm before being compared to the hashed password in the database.  This scheme allows for successful user authentication while protecting the users from potential data breaches.

Email functionality is enabled by utilizing the Java Mail API.  Additionally, MailGun is used as the SMTP server that the Java program interfaces with.  The service not only sends emails to the address provided by the user, but also knows whether the email was successfully received.  If it is not, this is communicated to the user via an error message.

Finally, a Continuous-Integration/Continuous-Delivery pipeline was set up for this project using Travis-CI and Heroku.  All changes to the master branch must come via pull request, and each pull request triggers a Travis-CI build of the updated project.  A merge is only allowed if the build succeeds, meaning the complete test suite passes and there are no errors or warnings generated during the build process.  A sample of such a build is shown below: 
![screenshot 15](src/main/resources/Screenshot15.jpg)

Then once the updates are merged into the master branch, the updated version of the project is automatically deployed to Heroku, where the application as well as its databases and email service are hosted.