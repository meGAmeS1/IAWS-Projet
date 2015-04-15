package UGmont;

import UGmont.database.DbUtil;
import UGmont.database.HibernateUtil;
import org.hibernate.Session;

/**
 * Created by flemoal on 15/04/15.
 */
public class MainTrash {
    public static void main(String[] args) {
        DbUtil.getInstance().initializeBase();
        Session session = HibernateUtil.getSessionFactory().openSession();
    }
}
