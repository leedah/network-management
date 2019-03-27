package main.java.battery;

import main.java.accessPoints.AccessPoints;
import main.java.test.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by sofia on 8/2/15.
 */
public class BatteryDAO {
    public void insertBattery(int id, String user, Integer level, Integer plugged, Timestamp timestamp){
        // 3. Get Session object
        Session session = HibernateUtil.getSession();

        // 4. Starting Transaction
        Transaction transaction = session.beginTransaction();
        Battery battery = new Battery();

        battery.setId(id);
        battery.setUser(user);
        battery.setLevel(level);
        battery.setPlugged(plugged);
        battery.setTimestamp(timestamp);

        session.save(battery);
        transaction.commit();

        session.close();
    }

    public List<Battery> getBattery(String user, Timestamp startDate, Timestamp endDate){
        // 3. Get Session object
        Session session = HibernateUtil.getSession();

        String hql = "FROM Battery B WHERE B.user = :user_id AND B.timestamp BETWEEN :startDate AND :endDate ORDER BY timestamp ASC";
        Query query = session.createQuery(hql);
        query.setParameter("user_id",user);
        query.setParameter("startDate",startDate);
        query.setParameter("endDate", endDate);
        List<Battery> results = query.list();

//        for (Battery battery: results)
//            System.out.println(battery.getTimestamp());

        return results;
    }

    public List<String> getAllTimestamps(){
        // 3. Get Session object
        Session session = HibernateUtil.getSession();

        String hql = "SELECT DISTINCT date_format(timestamp, '%Y-%m-%d %H') FROM Battery ORDER BY timestamp ASC";
        Query query = session.createQuery(hql);
        List<String> results = query.list();

        return results;
    }

    public int getLowBatteryUsers(String timestamp){
        // 3. Get Session object
        Session session = HibernateUtil.getSession();

        String hql = "SELECT COUNT(DISTINCT user) FROM Battery B WHERE date_format(timestamp, '%Y-%m-%d %H') = :timestamp and B.level < :level";
        Query query = session.createQuery(hql);
        query.setParameter("timestamp",timestamp);
        query.setParameter("level",15);
        int results =  ((Number) query.uniqueResult()).intValue();

        session.close();

        return results;

    }

    public int getLowBatteryHour(String timestamp){
        // 3. Get Session object
        Session session = HibernateUtil.getSession();

        String hql = "SELECT COUNT(DISTINCT user) FROM Battery B WHERE date_format(timestamp, '%H') = :timestamp and B.level < :level";
        Query query = session.createQuery(hql);
        query.setParameter("timestamp",timestamp);
        query.setParameter("level",15);
        int results =  ((Number) query.uniqueResult()).intValue();

        session.close();

        return results;

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
