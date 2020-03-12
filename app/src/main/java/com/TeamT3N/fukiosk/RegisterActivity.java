package com.TeamT3N.fukiosk;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

public class RegisterActivity extends AppCompatActivity {
    private static final String KEY_STATS = "stats";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_STUDENT_ID = "student_id";
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
    private static final String KEY_USERTYPE = "usertype";
    private EditText etUsername;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private String id;
    private String password;
    private String confirmPassword;

    private ProgressDialog pDialog;
    private String ip = globalconfig.ipaddress;
    private String register_url =ip +"student/register.php";
    private String getstudent_url =ip +"student/getstudentinfo.php";
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
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);


        Button login = findViewById(R.id.btnRegisterLogin);
        Button register = findViewById(R.id.btnRegister);

        //Launch Login screen when Login Button is clicked
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve the data entered in the edit texts
                id = etUsername.getText().toString().toLowerCase().trim();
                password = etPassword.getText().toString().trim();
                confirmPassword = etConfirmPassword.getText().toString().trim();
                if (validateInputs()) {
                    registerUser();
                }

            }
        });

    }

    /**
     * Display Progress bar while registering
     */
    private void displayLoader() {
        pDialog = new ProgressDialog(RegisterActivity.this);
        pDialog.setMessage("Signing Up.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    /**
     * Launch Dashboard Activity on Successful Sign Up
     */
    private void loadSecond() {
        Intent intent = new Intent (getApplicationContext(), SecondActivity.class);
        startActivity(intent);
        finish();


    }

    private void registerUser() {
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
                (Request.Method.POST, register_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            //Check if user got registered successfully
                            if (response.getInt(KEY_STATS) == 0) {
                                //Set the user session

// if (response.getString(KEY_USERTYPE )==  1){ admmin activity}
// if (response.getString(KEY_USERTYPE )==  2){ faculty activity}
// if (response.getString(KEY_USERTYPE ) ==  3){ student activity}




                                Toast.makeText(getApplicationContext(),
                                        response.getString(KEY_USERTYPE) , Toast.LENGTH_SHORT).show();


                            }else if(response.getInt(KEY_STATS) == 1){
                                //Display error message if username is already existsing
                                etUsername.setError("Username already taken!");
                                etUsername.requestFocus();


                            }
                            else if(response.getInt(KEY_STATS) == 2){
                                //Display error message if username is already existsing
                                etUsername.setError("ID number not recognize!  !");
                                etUsername.requestFocus();


                            }
                            else{
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
                                error.getMessage(), Toast.LENGTH_SHORT).show();

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
      /*  int intid = Integer.parseInt(id);
        int digit = (int)(Math.log10(intid)+1);
         if (digit <6 || digit > 8 )
         {
             etUsername.setError("invalid id");
             etUsername.requestFocus();
             return false;
         }*/

        if (KEY_EMPTY.equals(id)) {
            etUsername.setError("ID number cannot be empty");
            etUsername.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(password)) {
            etPassword.setError("Password cannot be empty");
            etPassword.requestFocus();
            return false;
        }

        if (KEY_EMPTY.equals(confirmPassword)) {
            etConfirmPassword.setError("Confirm Password cannot be empty");
            etConfirmPassword.requestFocus();
            return false;
        }
        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Password and Confirm Password does not match");
            etConfirmPassword.requestFocus();
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
