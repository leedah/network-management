package main.java.baseStation;

import main.java.accessPoints.AccessPoints;
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
public class BaseStationsDAO {
    public void insertBaseStation(int id, String user, String operator, Integer mcc, Integer mnc, Integer lac, Integer cid, Double latitude, Double longtitude, Timestamp timestamp){
        // 3. Get Session object
        Session session = HibernateUtil.getSession();

        // 4. Starting Transaction
        Transaction transaction = session.beginTransaction();
        BaseStations baseStations = new BaseStations();

        baseStations.setId(id);
        baseStations.setUser(user);
        baseStations.setOperator(operator);
        baseStations.setMcc(mcc);
        baseStations.setMnc(mnc);
        baseStations.setLac(lac);
        baseStations.setCid(cid);
        baseStations.setLatitude(latitude);
        baseStations.setLongtitude(longtitude);
        baseStations.setTimestamp(timestamp);

        session.save(baseStations);
        //transaction.commit();

        try {
            transaction.commit();
        }catch (ConstraintViolationException e){
            //for duplicates
            System.out.println(e.getConstraintName());
        }

        session.close();
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

    public List<BaseStations> getBaseStationsGeo(String user, Timestamp startDate, Timestamp endDate){
        // 3. Get Session object
        Session session = HibernateUtil.getSession();

        String hql = "FROM BaseStations BS WHERE BS.user = :user_id AND BS.timestamp BETWEEN :startDate AND :endDate AND BS.latitude is not NULL AND BS.longtitude is not NULL";
        Query query = session.createQuery(hql);
        query.setParameter("user_id",user);
        query.setParameter("startDate",startDate);
        query.setParameter("endDate", endDate);
        List<BaseStations> results = query.list();

        return results;
    }

    public List<String> getBaseStationsOperators(){
        // 3. Get Session object
        Session session = HibernateUtil.getSession();

        String hql = "SELECT operator FROM BaseStations BS group by BS.operator";
        Query query = session.createQuery(hql);
        List<String> results = query.list();

        return results;
    }

    public int getUsersFromOperators(String operator){
        // 3. Get Session object
        Session session = HibernateUtil.getSession();

        String hql = "select count(distinct user) from BaseStations BS where BS.operator= :operator_";
        Query query = session.createQuery(hql);
        query.setParameter("operator_",operator);
        int results =  ((Number) query.uniqueResult()).intValue();

        return results;
    }
}
