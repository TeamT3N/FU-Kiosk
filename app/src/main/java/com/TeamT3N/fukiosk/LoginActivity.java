package com.TeamT3N.fukiosk;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

public class LoginActivity extends AppCompatActivity {


    private static final String KEY_STATS = "stats";
    private static final String KEY_MESSAGE = "message";

    private static final String KEY_ID = "id";
    private static final String KEY_LAST_NAME = "last_name";
    private static final String KEY_MIDDLE_NAME = "middle_name";
    private static final String KEY_FIRST_NAME = "first_name";
    private static final String KEY_STATUS = "status";
    private static final String KEY_YEAR = "year";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_COLLEGE_ID = "college_id";
    private static final String KEY_COLLEGE = "college";
    private static final String KEY_PROGRAM = "program";
    private static final String KEY_CURRICULUM = "curriculum";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMPTY = "";

    private EditText etUsername;
    private EditText etPassword;
    private String id;
    private String password;
    private ProgressDialog pDialog;
    private String ip = globalconfig.ipaddress;
    private String login_url = ip +"student/login.php";
    private String getstudent_url = ip +"student/getstudentinfo.php";
    private SessionHandler session;


    //SourceLockedOrientationActivity
    @SuppressLint("SourceLockedOrientationActivity")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //screen orientation portrait
        setRequestedOrientation(SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_login);

        session = new SessionHandler(getApplicationContext());


        if(session.isLoggedIn()){
            loadSecond();
        }








        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etLoginUsername);
        etPassword = findViewById(R.id.etLoginPassword);


         
        Button register = findViewById(R.id.btnLoginRegister);
        Button login = findViewById(R.id.btnLogin);

        //Launch Registration screen when Register Button is clicked
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve the data entered in the edit texts
                id = etUsername.getText().toString().toLowerCase().trim();
                password = etPassword.getText().toString().trim();
                if (validateInputs()) {


                    login();



                }
            }
        });
    }

    /**
     * Launch Dashboard Activity on Successful Login
     */
    private void loadSecond() {
        Intent intent = new Intent (getApplicationContext(), SecondActivity.class);
        startActivity(intent);
        finish();

    }


    /**
     * Display Progress bar while Logging in
     */

    private void displayLoader() {
        pDialog = new ProgressDialog(LoginActivity.this);
        pDialog.setMessage("Logging In.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    private void login() {

        displayLoader();
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_ID, id);
            request.put(KEY_PASSWORD, password);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, login_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            //Check if user got logged in successfully

                            if (response.getInt(KEY_STATS) == 0) {
                                Toast.makeText(getApplicationContext(),
                                        "LOADING:  1/2", Toast.LENGTH_SHORT).show();
                                getstudentinfo();

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        //What you want to happen later


                                        loadSecond();




                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {

                                Student student = session.getStudentDetails();

                                         Toast.makeText(getApplicationContext(),
                                                         student.getLastName()+" "+ student.getFirstName()+" "+ student.getMiddleName(), Toast.LENGTH_SHORT).show();

                                            }
                                        }, 3000);





                                    }
                                }, 3000); //1500 = 1.5 seconds, time in milli before it happens.








                            }else{

                                Toast.makeText(getApplicationContext(),
                                        response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();

                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                "Log in failed. Please try again later.", Toast.LENGTH_SHORT).show();

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);







    }

    private void getstudentinfo() {


        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_ID, id);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, getstudent_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            String stats = response.getString(KEY_STATS);
                        String lastname=response.getString(KEY_LAST_NAME);
                        String middlename= response.getString(KEY_MIDDLE_NAME);
                            String firstname=response.getString(KEY_FIRST_NAME);
                              String status=response.getString(KEY_STATUS);
                            String year=response.getString(KEY_YEAR);
                            String image=response.getString(KEY_IMAGE);
                            String collegeid=response.getString(KEY_COLLEGE_ID);
                            String college=response.getString(KEY_COLLEGE);
                            String program=response.getString(KEY_PROGRAM);
                            String curriculum=response.getString(KEY_CURRICULUM);
                           Toast.makeText(getApplicationContext(),
                                   "LOADING:  2/2", Toast.LENGTH_SHORT).show();



                        session.loginStudent(id, lastname,firstname,middlename,status,year,image,collegeid,college,program,curriculum);




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);

    }

    /**
     * Validates inputs and shows error if any
     * @return
     */
    private boolean validateInputs() {
        /*int intid = Integer.parseInt(id);
        int digit = (int)(Math.log10(intid)+1);
        if (digit <6 || digit > 8 )
        {
            etUsername.setError("Invalid ID");
            etUsername.requestFocus();
            return false;
        } */

        if(KEY_EMPTY.equals(id)){
            etUsername.setError("ID number cannot be empty");
            etUsername.requestFocus();
            return false;
        }
        if(KEY_EMPTY.equals(password)){
            etPassword.setError("Password cannot be empty");
            etPassword.requestFocus();
            return false;
        }


        return true;


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}

