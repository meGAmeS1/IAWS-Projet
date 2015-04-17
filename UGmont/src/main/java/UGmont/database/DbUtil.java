package UGmont.database;

import org.h2.tools.RunScript;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by flemoal on 01/04/15.
 */
public class DbUtil {
    public final static String BDD_URL = String.format("jdbc:h2:mem:%s", "ugmont_db");


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
        conn = DriverManager.getConnection(BDD_URL, "sa", "");
    }

    public void closeDatabase() throws SQLException {
        conn.close();
    }

    public static DbUtil getInstance() {
        return instance;
    }

    public void initializeBase() {
        try {
            BufferedReader file = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/scriptBdd.sql")));
            RunScript.execute(conn, file);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
