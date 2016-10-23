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
    public String email;
    public String activeStatus;
    public Integer uId_;
    public Boolean active;

    public static User createFromObject(JSONObject r){
        try {
            User user = new User();
            user.userId = r.getString("userId");
            user.fullName = r.getString("fullname");
            user.email = r.getString("email");
            user.activeStatus = r.getString("active");

            user.uId_ = r.getInt("userId");
            user.active = r.getInt("active") == 1;
            return user;
        }
        catch (JSONException e){
            return null;
        }

    }
}
