COMP3005 - Assignment 3 - Part 1
(Running instructions for PostgreSQL + JDBC CRUD DEMO)

Summary:
This is a console app in Java that connects to PostgreSQL and performs CRUD operations on a students table (student_db.sql)

Here is a brief overview of all the functions supported…
        - getAllStudents() - read/print all rows operation
        - addStudent(first_name, last_name, email, enrollment_date) - insert operation
        - updateStudentEmail(student_id, new_email) - update operation
        - deleteStudent(student_id) - delete operation
        - resetStudents() - testing helper function to re-initialize the 3 rows

Scheme .sql files provided:
        - student_db.sql - .sql file that creates the students table
        - student_table_initial_data.sql - .sql file that initializes the table with some data to work with

Requirements:
        - JDK: 17+ or higher 
        - PostgreSQL: running on local machine (default port 5432)
        - JDBC driver: PostgreSQL-42.7.8.jar
        - pgAdmin 4: (helps with visualizing the changes after CRUD operations) 
        - IntelliJ: not required but these instructions are specifically tailored to IntelliJ

Instructions:

        Step 1: Create the database using pgAdmin 4...

                        - After launching pgAdmin4, in the browser panel on the left, right-clock on Databases
                          and select Create > Database.
                        - Name the new database “student_db” and click save
                        - Select the newly created database by clicking on it
                        - Open the query tool by right-clicking on student_db and selecting the Query tool
                        - Click on the Open File icon (looks like a folder in the toolbar)
                        - Navigate to the location where student_db.sql is saved and open it
                        - Now the content of student_db.sql should be displayed on the query editor
                        - Just click on the Run button (triangle) to execute the SQL commands 

        Step 2: Initializing the database using pgAdmin 4 and the “student_table_initial_data.sql” file
                        - Expand the server list on the left and select the “student_db” 
                        - Right click the database student_db in pgAdmin 4
                        - Choose the Query Tool to open an SQL editor window
                        - In the query tool, click on the Open File button (it looks like a folder) and navigate to 
                          the location of the “student_table_initial_data.sql” file
                        - Once that file is open in the editor, click on the green Run button to execute the SQL
                          commands 

        Step 3: App configuration for JDBC
                        - To keep the video under5 minutes will not go over this in depth but here is a video from
                          the professor explaining everything in more detail: 
                          https://brightspace.carleton.ca/d2l/le/content/396898/viewContent/4367649/View
                        - The important takeaway is that in Main.java, these constants should be set already…
                          private static final String url = "jdbc:postgresql://localhost:5432/student_db";
                          private static final String user = "postgres";
                          private static final String password = "<your_password>";
           
                          - Feel free to adjust the localhost port number as well as your password for
                            pgAdmin4 (it will vary by device and depending on what you set your password
                            to initially)…

        Step 4: Running the application instructions:

                        - Assuming everything is in order now with the steps above you should be good to run
                          the application(Hooray!!) 
                        
                        - Open the project
        
                        - Make sure the Maven module is imported: right-click Assignment_3_PART1_MAIN_CODE/pom.xml →
			  Add as Maven Project (or Maven → Sync)

                        - ENSURE sources are marked (could run into errors otherwise…): right-click
                          Assignment_3_PART1_MAIN_CODE/src/main/java → 
                          Mark Directory As → 
                          Sources Root.

                        - Open Main.java (the one under …/Assignment_3_PART1_MAIN_Code/src/main/java/)

                        - Right-click in the editor -> Run Main.main()

        Step 5: Additional helpful notes:

                        - Reset: Truncates the table and re-inserts the 3 starter rows (John/Jane/Jim).
                        - Show all: Reads and prints all columns, ordered by student_id
                        - Add sample: Inserts Eren Kemal with a fixed date, then prints the table.
                        - Update email: You’ll be prompted for student_id and new_email, then it prints the table.
                        - Delete: You’ll be prompted for student_id, then it prints the table.
                        - Exit: Quits the application 

Video tutorial link: https://youtu.be/y11v47LH9fs