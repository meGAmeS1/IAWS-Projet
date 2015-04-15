package UGmont.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by flemoal on 01/04/15.
 */
public class DbUtil {
    private final static String BDD_PATH = "../bdd";
    private static DbUtil instance = new DbUtil();
    private Connection conn;
    private DbUtil() {
        try {
            this.connectDataBase();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void connectDataBase() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        conn = DriverManager.getConnection("jdbc:h2:" + BDD_PATH, "sa", "");
    }

    private void closeDatabase() throws SQLException {
        conn.close();
    }

    public static DbUtil getInstance() {
        return instance;
    }

}
