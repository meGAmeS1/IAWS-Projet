package UGmontBack.back;

import UGmontBack.database.HibernateUtil;
import UGmontBack.model.Salle;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class BackSearchRoom {

    public List<Salle> getSalles(int nbplaces, boolean require3d) {
        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder.append("from Salle ");
        queryBuilder.append("where nbPlaces >= :nbPlaces ");
        if (require3d) {
            queryBuilder.append("and support3d = true");
        }

        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery(queryBuilder.toString());
        query.setParameter("nbPlaces", nbplaces);

        List<Salle> salles = query.list();

        session.close();

        return salles;
    }
}
