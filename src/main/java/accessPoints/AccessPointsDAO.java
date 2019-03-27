package main.java.accessPoints;

import main.java.test.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.exception.ConstraintViolationException;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by sofia on 8/2/15.
 */
//Data access object - for hibernate only
public class AccessPointsDAO {

    public void insertAccessPoint(int id, String user, String ssid, String bssid, Integer rssi, Integer frequency, Timestamp timestamp) {
        // 3. Get Session object
        Session session = HibernateUtil.getSession();

        // 4. Starting Transaction
        Transaction transaction = session.beginTransaction();
        AccessPoints accessPoints = new AccessPoints();

        accessPoints.setId(id);
        accessPoints.setUser(user);
        accessPoints.setSsid(ssid);
        accessPoints.setBssid(bssid);
        accessPoints.setRssi(rssi);
        accessPoints.setFrequency(frequency);
        accessPoints.setTimestamp(timestamp);

        session.save(accessPoints);
        try {
            transaction.commit();
        }catch (ConstraintViolationException e){
            //for duplicates
            System.out.println(e.getConstraintName());
        }

        session.close();
    }

    public List<AccessPoints> getAccessPoints(String user, Timestamp startDate, Timestamp endDate){
        // 3. Get Session object
        Session session = HibernateUtil.getSession();

        String hql = "FROM AccessPoints A WHERE A.user = :user_id AND A.timestamp BETWEEN :startDate AND :endDate";
        Query query = session.createQuery(hql);
        query.setParameter("user_id",user);
        query.setParameter("startDate",startDate);
        query.setParameter("endDate", endDate);
        List<AccessPoints> results = query.list();

        session.close();
        return results;
    }

    public List<String> getUsers(){
        List<String> users;

        // 3. Get Session object
        Session session = HibernateUtil.getSession();

        // 4. Create criteria for distinct values in column "users"
        Criteria criteria = session.createCriteria(AccessPoints.class);
        criteria.setProjection( Projections.distinct( Projections.property( "user" ) ) );
        users = criteria.list();

        session.close();

        return users;
    }



}
