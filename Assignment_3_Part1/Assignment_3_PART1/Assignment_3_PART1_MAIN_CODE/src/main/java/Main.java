import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {

    private static final String url = "jdbc:postgresql://localhost:5432/student_db";
    private static final String user = "postgres";
    private static final String password = "#KemalSQL1938";

    // getAllStudents(): Will retrieve and display the records of all the students in the database
    public static void getAllStudents(){
        // sql statement to fetch all the necessary information from the students table
        String sql = "SELECT student_id, first_name, last_name, email, enrollment_date FROM public.students ORDER BY student_id";

        // try and catch block for error handling
        try {
            // load the driver (kept for your course style) and open a connection internally
            Class.forName("org.postgresql.Driver");
            try (java.sql.Connection connection = java.sql.DriverManager.getConnection(url, user, password);
                 java.sql.Statement stmt = connection.createStatement();
                 // resultSet starts before the first row and next() moves to the first row
                 java.sql.ResultSet resultSet = stmt.executeQuery(sql)) {

                // NOTE: if no rows are left, next() returns false and exits
                if (!resultSet.next()) {
                    System.out.println("no students");
                    return;
                }

                // Extract all the student information, store it, and return it
                // NOTE: we repeat this process until there are no more rows to process
                do {
                    int student_id = resultSet.getInt("student_id");
                    String first_name = resultSet.getString("first_name");
                    String last_name  = resultSet.getString("last_name");
                    String student_email = resultSet.getString("email");
                    java.sql.Date student_enrollment_date = resultSet.getDate("enrollment_date");
                    System.out.println(student_id + " | " + first_name + " " + last_name + " | " + student_email + " | " + student_enrollment_date);
                } while (resultSet.next());
            }
        }
        // Catch block to catch any exceptions: prints the caught error as well for debugging purposes
        catch (Exception e) {
            System.out.println("ERROR: The query failed: " + e);
        }
    }

    // addStudent(): Given the entry parameters, this function inserts a new student row into the database
    public static void addStudent(String first_name, String last_name, String email, java.sql.Date enrollment_date){
        // sql statement to insert a new student
        // NOTE: enrollment_date could potentially be a null value so we account for this
        String dateLiteral = (enrollment_date != null) ? "'" + enrollment_date.toString() + "'" : "NULL";
        String sql = "INSERT INTO public.students (first_name, last_name, email, enrollment_date) " +
                "VALUES ('" + first_name + "', '" + last_name + "', '" + email + "', " + dateLiteral + ")";

        // try and catch block for error handling
        try {
            // load the driver (kept for your course style) and open a connection internally
            Class.forName("org.postgresql.Driver");
            try (java.sql.Connection connection = java.sql.DriverManager.getConnection(url, user, password);
                 java.sql.Statement statement = connection.createStatement()) {

                // execute the insert statement; returns number of rows affected
                int rows_inserted = statement.executeUpdate(sql);

                // basic confirmation message
                if (rows_inserted > 0) {
                    System.out.println("SUCCESS: student inserted successfully: " + first_name + " " + last_name);
                } else {
                    System.out.println("SOMETHING WENT WRONG: student not inserted...");
                }
            }
        }
        // Catch block to catch any exceptions: prints the caught error as well for debugging purposes
        catch (Exception e) {
            System.out.println("ERROR: Insert failed: " + e);
        }
    }

    // updateStudentEmail(student_id, new_email): Updates the email address for a student with the specified student_id
    public static void updateStudentEmail(int student_id, String new_email){
        // sql statement to update the student's email
        String sql = "UPDATE public.students SET email = '" + new_email + "' WHERE student_id = " + student_id;

        // try and catch block for error handling
        try {
            Class.forName("org.postgresql.Driver");
            try (java.sql.Connection connection = java.sql.DriverManager.getConnection(url, user, password);
                 java.sql.Statement statement = connection.createStatement()) {

                // execute the update statement; returns number of rows affected
                int rows_updated = statement.executeUpdate(sql);

                // basic confirmation message
                if (rows_updated > 0) {
                    System.out.println("SUCCESS: email updated for student_id=" + student_id + " → " + new_email);
                } else {
                    System.out.println("SOMETHING WENT WRONG: no student with student_id=" + student_id);
                }
            }
        }
        // Catch block to catch any exceptions: prints the caught error as well for debugging purposes
        catch (Exception e) {
            System.out.println("ERROR: Update failed: " + e);
        }
    }

    // deleteStudent(student_id): Deletes the record of the student with the specified student_id
    public static void deleteStudent(int student_id){
        // sql statement to delete the student row with the given id
        String sql = "DELETE FROM public.students WHERE student_id = " + student_id;

        // try and catch block for error handling
        try {
            Class.forName("org.postgresql.Driver");
            try (java.sql.Connection connection = java.sql.DriverManager.getConnection(url, user, password);
                 java.sql.Statement statement = connection.createStatement()) {

                // execute the delete statement; returns number of rows affected
                int rows_deleted = statement.executeUpdate(sql);

                // basic confirmation message
                if (rows_deleted > 0) {
                    System.out.println("SUCCESS: student deleted: student_id=" + student_id);
                } else {
                    System.out.println("SOMETHING WENT WRONG: no student with student_id=" + student_id);
                }
            }
        }
        // Catch block to catch any exceptions: prints the caught error as well for debugging purposes
        catch (Exception e) {
            System.out.println("ERROR: Delete failed: " + e);
        }
    }

    // NOTE: THIS FUNCTION IS NOT REQUIRED BY PURELY HERE FOR TESTING PURPOSES TO RESET THE TABLE TO ITS INITIAL STATE
    //       AFTER TESTING SERIES OF CRUD OPERATIONS
    // resetStudents(): Clears the table and puts it back to the initial state
    public static void resetStudents(){
        // delete all rows very quickly and reset the auto-increment sequence for student_id by using "TRUNCATE"
        // (so it begins at 1 again)
        String truncate_SQL = "TRUNCATE TABLE public.students RESTART IDENTITY";

        // 2) re-insert the original rows
        String initial_data_insert_SQL =
                "INSERT INTO public.students (first_name, last_name, email, enrollment_date) VALUES " +
                        "('John','Doe','john.doe@example.com','2023-09-01')," +
                        "('Jane','Smith','jane.smith@example.com','2023-09-01')," +
                        "('Jim','Beam','jim.beam@example.com','2023-09-02')";

        // try and catch block for error handling
        try {
            // load the driver and open a connection internally
            Class.forName("org.postgresql.Driver");
            try (java.sql.Connection connection = java.sql.DriverManager.getConnection(url, user, password);
                 java.sql.Statement stmt = connection.createStatement()) {

                // execute truncate and then re-insert data
                stmt.executeUpdate(truncate_SQL);
                int rows = stmt.executeUpdate(initial_data_insert_SQL);

                System.out.println("RESET: students table truncated, identity reset, and data re-initialized (" + rows + " rows).");
            }
        }
        // Catch block to catch any exceptions: prints the caught error as well for debugging purposes
        catch (Exception e) {
            System.out.println("ERROR: Reset failed: " + e);
        }
    }

    public static void main(String[] args) {
        // main(): simple menu to demonstrate CRUD step-by-step
        java.util.Scanner in = new java.util.Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n======== MENU ========");
            System.out.println("0) Exit");
            System.out.println("1) Reset to initial data");
            System.out.println("2) Show all students");
            System.out.println("3) Add sample student (Eren Kemal)");
            System.out.println("4) Update email by student_id");
            System.out.println("5) Delete by student_id");
            System.out.print("Choose an option: ");

            String choice = in.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        resetStudents();
                        System.out.println("\n== AFTER RESET ==");
                        getAllStudents();
                        break;
                    case "2":
                        System.out.println("\n== SHOW ALL ==");
                        getAllStudents();
                        break;
                    case "3":
                        addStudent("Eren", "Kemal", "eren.kemal@example.com",
                                java.sql.Date.valueOf("2020-01-01"));
                        System.out.println("\n== AFTER INSERT (Eren Kemal) ==");
                        getAllStudents();
                        break;
                    case "4":
                        System.out.print("Enter student_id to update: ");
                        int updId = Integer.parseInt(in.nextLine().trim());
                        System.out.print("Enter new email: ");
                        String newEmail = in.nextLine().trim();
                        updateStudentEmail(updId, newEmail);
                        System.out.println("\n== AFTER UPDATE ==");
                        getAllStudents();
                        break;
                    case "5":
                        System.out.print("Enter student_id to delete: ");
                        int delId = Integer.parseInt(in.nextLine().trim());
                        deleteStudent(delId);
                        System.out.println("\n== AFTER DELETE ==");
                        getAllStudents();
                        break;
                    case "0":
                        running = false;
                        break;
                    default:
                        System.out.println("Unknown option. Please choose 0–5.");
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e);
            }
        }

        in.close();
    }
}