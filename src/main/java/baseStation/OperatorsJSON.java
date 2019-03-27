package main.java.baseStation;

import org.json.simple.JSONValue;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sofia on 10/3/15.
 */
public class OperatorsJSON {

    public static String getOperatorsJSON(Map<String, Integer> operators2users){

        Map OperatorsObj = new LinkedHashMap();
        String batteryJsonToString;

        OperatorsObj.putAll(operators2users);
        batteryJsonToString = JSONValue.toJSONString(OperatorsObj);
        System.out.print(batteryJsonToString);
        return batteryJsonToString;

    }

    public static Map<String, Integer> getOperators(){

        /* get a list of all operators*/
        BaseStationsDAO baseStationsDAO = new BaseStationsDAO();
        List<String> operatorsList = baseStationsDAO.getBaseStationsOperators();

        for (String operator: operatorsList)
            System.out.println(": "+operator);

        /* for each operator, find how many user it has*/
        Map<String, Integer> operator2users = new HashMap<>();

        /* combine operators with similar names*/
        int cosmote = 0;
        int vodafone = 0;

        for (String operator : operatorsList){

            //users
            int userNumber = baseStationsDAO.getUsersFromOperators(operator);
            System.out.println(operator+" has "+userNumber);

            if (operator.toLowerCase().contains("COSMOT".toLowerCase()))
                cosmote += userNumber;
            else if (operator.toLowerCase().contains("vodafone".toLowerCase()))
                vodafone += userNumber;
            else
                operator2users.put(operator, userNumber);
        }

        if (cosmote != 0)
            operator2users.put("COSMOTE", cosmote);
        if (vodafone != 0)
            operator2users.put("VODAFONE", vodafone);

        for (String operator : operator2users.keySet())
            System.out.println(operator+"   "+operator2users.get(operator));

        return operator2users;
    }
}
