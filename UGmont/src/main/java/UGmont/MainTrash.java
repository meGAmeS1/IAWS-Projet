package UGmont;

import UGmont.database.DbUtil;
import UGmont.database.HibernateUtil;
import UGmont.model.Salle;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by flemoal on 15/04/15.
 */
public class MainTrash {
    public static void main(String[] args) {
        DbUtil.getInstance().initializeBase();
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("from Salle");

        List<Salle> salles = query.list();
        session.close();

        for (Salle salle : salles) {
            System.out.println(salle);
        }
    }
}
