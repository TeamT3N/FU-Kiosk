package com.TeamT3N.fukiosk;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second2);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    public static class ImageDownload {

        Bitmap bmImg;
        void downloadfile(String fileurl, ImageView img)
        {
            URL myfileurl =null;
            try
            {
                myfileurl= new URL(fileurl);

            }
            catch (MalformedURLException e)
            {

                e.printStackTrace();
            }

            try
            {
                HttpURLConnection conn= (HttpURLConnection)myfileurl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                int length = conn.getContentLength();
                int[] bitmapData =new int[length];
                byte[] bitmapData2 =new byte[length];
                InputStream is = conn.getInputStream();
                BitmapFactory.Options options = new BitmapFactory.Options();

                bmImg = BitmapFactory.decodeStream(is,null,options);

                img.setImageBitmap(bmImg);

                //dialog.dismiss();
            }
            catch(IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
    //          Toast.makeText(PhotoRating.this, "Connection Problem. Try Again.", Toast.LENGTH_SHORT).show();
            }


        }


    }
}
