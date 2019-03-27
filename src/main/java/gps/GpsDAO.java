package main.java.gps;

import main.java.accessPoints.AccessPoints;
import main.java.accessPoints.Coordinates;
import main.java.test.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sofia on 8/2/15.
 */
public class GpsDAO {
    public void insertGps(int id, String user, Double latitude, Double longtitude, Timestamp timestamp){
        // 3. Get Session object
        Session session = HibernateUtil.getSession();

        // 4. Starting Transaction
        Transaction transaction = session.beginTransaction();
        Gps gps = new Gps();

        gps.setId(id);
        gps.setUser(user);
        gps.setLatitude(latitude);
        gps.setLongtitude(longtitude);
        gps.setTimestamp(timestamp);

        session.save(gps);
        transaction.commit();

        session.close();
    }

    public List<Gps> getGPSLocation(String user, Timestamp startDate, Timestamp endDate){
        // 3. Get Session object
        Session session = HibernateUtil.getSession();

        String hql = "FROM Gps GP WHERE GP.user = :user_id AND GP.timestamp BETWEEN :startDate AND :endDate";
        Query query = session.createQuery(hql);
        query.setParameter("user_id",user);
        query.setParameter("startDate",startDate);
        query.setParameter("endDate", endDate);
        List<Gps> results = query.list();

        return results;
    }

    public List<Coordinates> getGPSroute (String user, Timestamp startDate, Timestamp endDate){
        // 3. Get Session object
        Session session = HibernateUtil.getSession();

        String hql = "FROM Gps GP WHERE GP.user = :user_id AND GP.timestamp BETWEEN :startDate AND :endDate";
        Query query = session.createQuery(hql);
        query.setParameter("user_id",user);
        query.setParameter("startDate",startDate);
        query.setParameter("endDate", endDate);
        List<Gps> results = query.list();
        List<Coordinates> route = new ArrayList<>();

        for (Gps location : results){
            Coordinates coordinates = new Coordinates(location.getLatitude(), location.getLongtitude());
            route.add(coordinates);
        }

        return route;
    }






    public List<String> getUsers(){
        List<String> users;

        // 3. Get Session object
        Session session = HibernateUtil.getSession();

        // 4. Create criteria for distinct values in column "users"
        Criteria criteria = session.createCriteria(AccessPoints.class);
        criteria.setProjection( Projections.distinct(Projections.property("user")) );
        users = criteria.list();

        session.close();

        return users;
    }
}
