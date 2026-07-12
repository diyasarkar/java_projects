import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    static Connection con;

    public static Connection createConnection() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/expense_tracker",
                    "root",
                    ""
            );

            System.out.println("Connection Success");

        } catch (Exception e) {

            System.out.println(e);
        }

        return con;
    }
}