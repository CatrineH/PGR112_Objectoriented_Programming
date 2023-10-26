# Student Voting System

This is a simple command-line based student voting system that allows students to nominate candidates, vote for nominees, and add comments to their votes.

---
# How to Use
## MySQL JDBC Driver

In Java, we need a driver to communicate with a MySQL server.

To download MySQL Server, use the following link: https://dev.mysql.com/downloads/mysql/

To access the classes we need, we have to load a package called Connector/J.

### To Install Manually:

 >   Download from: https://dev.mysql.com/downloads/connector/j/

 >  Installation Guide: https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-installing.html

---

In this project, we need to manually load Connector/J.

Download a platform-independent .zip folder, place the .zip file into IntelliJ:

File → Project Structure ... and then navigate to Libraries under Project Settings, click on +, choose Java, 
locate the .ZIP file you downloaded, and add it to the project.

---
## Features

- View all nominees and their information.
- Propose new nominees to be added to the system.
- Vote for nominees and add optional comments.
- See the current top nominee with the highest number of votes.
- View votes and comments for all nominees.
- Exit the program.
1. Run the `Main` class to start the program.
2. Choose options from the main menu options in the command-line:
    - Sign in: Enter your student ID to access nominee options.
    - Change your comment: Update the comment for a nominee you voted for.
    - See the current top Nominee: Display the nominee with the highest votes and comments.
    - See the votes for all Nominees: View all nominees along with their votes and comments.
    - Return to the main menu: Go back to the main menu.
    - Exit: Close the program.

3. If you sign in with a valid student ID, you can:
    - View all nominees.
    - Propose a new nominee by providing their student ID, name, and program.
    - Vote for a nominee and optionally add a comment.

4. After making choices, the program will display relevant information and return to the main menu.

## Database

The program uses a MySQL database to store nominee information, votes, and comments. Make sure to set up the database schema and connection details correctly before running the program.

## Dependencies

- Java SE Development Kit (JDK)
- MySQL Connector/J (not included in the project)

## Usage Notes

- Ensure that your MySQL server is running and accessible.
- Modify database connection details in the `VoteSystem` class if necessary.
- Customize database setup and schema according to your needs.
- Remember to also change the DB properties if so.

## Author

- Candidate number 2050

