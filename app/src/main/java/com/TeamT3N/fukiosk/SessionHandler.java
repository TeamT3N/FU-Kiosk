package com.TeamT3N.fukiosk;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

/**
 * Created by Abhi on 20 Jan 2018 020.
 */

public class SessionHandler {
    private static final String PREF_NAME = "UserSession";
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
    private static final String KEY_EXPIRES = "expires";

    private static final String KEY_EMPTY = "";
    private Context mContext;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;

    public SessionHandler(Context mContext) {
        this.mContext = mContext;
        mPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.mEditor = mPreferences.edit();
    }

    /**
     * Logs in the user by saving user details and setting session
     *
     * @param uid

     */
    public void loginStudent(String uid, String lname,String fname, String mname,String status,String year, String image, String colid,String col,String prog, String cur) {
        mEditor.putString(KEY_ID, uid);
        mEditor.putString(KEY_LAST_NAME, lname);
        mEditor.putString(KEY_FIRST_NAME, fname);
        mEditor.putString(KEY_MIDDLE_NAME, mname);
        mEditor.putString(KEY_STATUS, status);
        mEditor.putString(KEY_YEAR, year);
        mEditor.putString(KEY_IMAGE, image);
        mEditor.putString(KEY_COLLEGE_ID, colid);
        mEditor.putString(KEY_COLLEGE, col);
        mEditor.putString(KEY_PROGRAM, prog);
        mEditor.putString(KEY_CURRICULUM, cur);

        Date date = new Date();

        //Set user session for next 7 days
        long millis = date.getTime() + (7 * 24 * 60 * 60 * 1000);
        mEditor.putLong(KEY_EXPIRES, millis);
        mEditor.commit();


    }



    /**
     * Checks whether user is logged in
     *
     * @return
     */
    public boolean isLoggedIn() {
        Date currentDate = new Date();

        long millis = mPreferences.getLong(KEY_EXPIRES, 0);

        /* If shared preferences does not have a value
         then user is not logged in
         */
        if (millis == 0) {
            return false;
        }
        Date expiryDate = new Date(millis);

        /* Check if session is expired by comparing
        current date and Session expiry date
        */
        return currentDate.before(expiryDate);
    }

    /**
     * Fetches and returns user details
     *
     * @return user details
     */



    public Student getStudentDetails() {
        if (!isLoggedIn()) {
            return null;
        }

        Student student = new Student();
        student.setStudentID(mPreferences.getString(KEY_ID, KEY_EMPTY));
        student.setLastName(mPreferences.getString(KEY_LAST_NAME, KEY_EMPTY));
        student.setFirstName(mPreferences.getString(KEY_FIRST_NAME, KEY_EMPTY));
        student.setMiddleName(mPreferences.getString(KEY_MIDDLE_NAME, KEY_EMPTY));
        student.setStatus(mPreferences.getString(KEY_STATUS, KEY_EMPTY));
        student.setYear(mPreferences.getString(KEY_YEAR, KEY_EMPTY));
        student.setImage(mPreferences.getString(KEY_IMAGE, KEY_EMPTY));
        student.setYear(mPreferences.getString(KEY_YEAR, KEY_EMPTY));
        student.setCollegeID(mPreferences.getString(KEY_COLLEGE_ID, KEY_EMPTY));
        student.setCollege(mPreferences.getString(KEY_COLLEGE, KEY_EMPTY));
        student.setProgram(mPreferences.getString(KEY_PROGRAM, KEY_EMPTY));
        student.setCurriculum(mPreferences.getString(KEY_CURRICULUM, KEY_EMPTY));
        return student;
    }
    /**
     * Logs out user by clearing the session
     */

    public void logoutStudent(){
        mEditor.clear();
        mEditor.commit();
    }

}
