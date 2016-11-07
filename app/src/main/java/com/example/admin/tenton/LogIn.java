package com.example.admin.tenton;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.tenton.dummy.PeopleContent;
import com.example.admin.tenton.dummy.User;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;

public class LogIn extends AppCompatActivity {

    private EditText mUserEmail;
    private EditText mPassword;
    private Button btnLogIn;

    private ProgressDialog pDialog;
    private String m_RequestUrl = "http://in.tenton.co/api/?controller=users&action=check";
    View recyclerView;
    private String m_errorTag = "ERROR";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        final String username = sharedPref.getString("username", "");
        final String password = sharedPref.getString("password", "");

        if (username.equals("") & password.equals("")) {
            setContentView(R.layout.activity_log_in);
       }
        else {

            //region Else for opening PeopleListActivity and passing the User.currentUser value
            Intent intent = new Intent(getApplicationContext(), PeopleListActivity.class);
            startActivity(intent);
            StringRequest jRequest = new StringRequest(Request.Method.POST, m_RequestUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String res) {

                            try {
                                JSONObject r = new JSONObject(res);

                                JSONArray arr = r.getJSONArray("data");
                                if (arr.length() == 1) {

                                    User u = User.createFromObject(arr.getJSONObject(0));

                                    if (u != null) {
                                        Intent intent = new Intent(getApplicationContext(), PeopleListActivity.class);
                                        User.currentUser = u;
                                        //Perjcellja e vleres u User tek acitiviteti tjeter
                                        intent.putExtra("User", u);
                                       // startActivity(intent);
//                                            Toast.makeText(getApplicationContext(),
//                                                    u.fullName + ", Welcome to Tenton App!", Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Cannot parse User", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(),
                                        e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError e) {
                    e.printStackTrace();
                }
            }) {
                @Override
                public Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", username);
                    params.put("password", password);
                    return params;
                }
            };
            RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
            mRequestQueue.add(jRequest);

            //endregion
        }

        mUserEmail = (EditText) findViewById(R.id.userName);
        mPassword = (EditText) findViewById(R.id.passWord);
        btnLogIn = (Button) findViewById(R.id.btnLogIn);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                pDialog = new ProgressDialog(LogIn.this);
                pDialog.setTitle("Tentonizers");
                pDialog.setMessage("Please wait...");
                pDialog.setCancelable(false);
                pDialog.show();

                StringRequest jRequest = new StringRequest(Request.Method.POST, m_RequestUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String res) {

                                try {
                                    JSONObject r = new JSONObject(res);

                                    JSONArray arr = r.getJSONArray("data");
                                    if (arr.length() == 1) {

                                        User u = User.createFromObject(arr.getJSONObject(0));

                                        if (u != null) {
                                            Intent intent = new Intent(getApplicationContext(), PeopleListActivity.class);
                                            User.currentUser = u;

                                            SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

                                            SharedPreferences.Editor editor = sharedPref.edit();
                                            editor.putString("username", mUserEmail.getText().toString());
                                            editor.putString("password", mPassword.getText().toString());
                                            editor.apply();

                                            String uname = sharedPref.getString("username", "");
                                            String pass = sharedPref.getString("password", "");

                                            Toast.makeText(getApplicationContext(), "Welcome " +
                                                    uname.toUpperCase() + "  " + pass.toUpperCase(), Toast.LENGTH_LONG).show();

                                            //Perjcellja e vleres u User tek acitiviteti tjeter
                                            intent.putExtra("User", u);
                                            startActivity(intent);
//                                            Toast.makeText(getApplicationContext(),
//                                                    u.fullName + ", Welcome to Tenton App!", Toast.LENGTH_SHORT).show();


                                        } else {
                                            Toast.makeText(getApplicationContext(), "Cannot parse User", Toast.LENGTH_SHORT).show();
                                        }
                                    } else if ((mUserEmail.getText().toString().trim().length() == 0)
                                            || (mPassword.getText().toString().trim().length() == 0)) {
                                        Toast.makeText(getApplicationContext(), "Please input Username and Password",
                                                Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(),
                                                "Invalid email or password.", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    Toast.makeText(getApplicationContext(),
                                            e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                if (pDialog.isShowing()) {
                                    pDialog.dismiss();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError e) {
                        e.printStackTrace();
                    }
                }) {
                    @Override
                    public Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("email", mUserEmail.getText().toString());
                        params.put("password", mPassword.getText().toString());
                        return params;
                    }
                };

                RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());

                mRequestQueue.add(jRequest);

            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("LogIn Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
