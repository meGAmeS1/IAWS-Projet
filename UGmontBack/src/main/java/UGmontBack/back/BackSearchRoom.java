package UGmontBack.back;

import UGmontBack.database.HibernateUtil;
import UGmontBack.model.Salle;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class BackSearchRoom {

    public List<Salle> getSalles(String nbplaces, String require3d) {
        if (nbplaces == null) {
            throw new IllegalArgumentException("Parameter \"nbplaces\" is required");
        }

        int places = 0;

        try {
            places = Integer.parseInt(nbplaces);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Parameter \"nbplaces\" should be a number");
        }

        if (places < 0) {
            throw new IllegalArgumentException("Parameter \"nbplaces\" should be higher than zero");
        }

        boolean avec3d = Boolean.parseBoolean(require3d);

        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder.append("from Salle ");
        queryBuilder.append("where nbPlaces >= :nbPlaces ");
        if (avec3d) {
            queryBuilder.append("and support3d = true");
        }

        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery(queryBuilder.toString());
        query.setParameter("nbPlaces", places);

        List<Salle> salles = query.list();

        session.close();

        return salles;
    }
}
