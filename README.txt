###########################
#   HMRC Onboarding App   #
###########################

This application has been created to help streamline the HMRC onboarding process for new joiners.
All files in the onboarding-app directory are relevant to the project.

TO RUN:
The application is set up to automatically build as part of a CI/CD Pipeline using GitHub Actions.
However, to run it locally (for development purposes):

1. Ensure that MySQL Workbench and MySql Server are installed.
2. Open the project in the IDE/Text Editor of your choice.
3. Navigate to the '/sql-scripts' folder - this folder contains the scripts necessary to create a user for the database,
   and the database tables. If you are wanting to use a custom user, set the username/password combo in
   '/src/main/java/resources/application.properties'.
4. The database should fill with 'dummy' data for the purposes of testing.
5. Click 'Run' on your IDE (or run through the command line).
6. To log in, navigate to 'localhost:8080' and  use the User ID of 11111 (Admin) or 22222 (non-Admin), and a password of 'onboarding' (for both).