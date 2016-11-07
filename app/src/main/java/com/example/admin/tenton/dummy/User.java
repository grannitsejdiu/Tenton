package com.example.admin.tenton.dummy;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;
/**
 * Created by Admin on 10/13/2016.
 */

public class User implements Serializable {

    public String fullName;
    public String userId;
    //public String email;
    public Boolean status;
    public String calculatedMins;
    public String totalMinutes;


    public static User currentUser = null;

    public static User createFromObject(JSONObject r){
        try {
            User user = new User();
            user.userId = r.getString("userId");
            user.fullName = r.getString("fullname");
            //user.email = r.getString("email");

            user.calculatedMins = r.getString("calculatedMinutes");

            if (!r.isNull("totalMinutes")) {
                user.totalMinutes = r.getString("totalMinutes");
            }

            user.status = r.getInt("status") == 1;
            return user;
        }
        catch (JSONException e){
            return null;
        }

    }
}
