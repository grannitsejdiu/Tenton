package com.example.admin.tenton.dummy;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;
/**
 * Created by Admin on 10/13/2016.
 */

public class User implements Serializable {
    public String fullName;
    public Integer userId;
    public Boolean active;

    public static User createFromObject(JSONObject r){
        try {
            User user = new User();
            user.fullName = r.getString("fullname").toString();
            user.userId = r.getInt("userId");
            user.active = r.getInt("active") == 1 ? true : false;
            return user;
        }
        catch (JSONException e){
            return null;
        }

    }
}
