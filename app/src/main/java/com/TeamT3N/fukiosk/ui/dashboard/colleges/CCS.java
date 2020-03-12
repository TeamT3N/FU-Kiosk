package com.TeamT3N.fukiosk.ui.dashboard.colleges;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.TeamT3N.fukiosk.R;
import com.TeamT3N.fukiosk.adapter.FeedListAdapter;
import com.TeamT3N.fukiosk.app.AppController;
import com.TeamT3N.fukiosk.data.FeedItem;
import com.TeamT3N.fukiosk.ui.home.HomeFragment;
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class CCS extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_c_s);



    }

    public class MainActivity extends Activity {
        private final String TAG = HomeFragment.class.getSimpleName();
        private ListView listView;
        private FeedListAdapter listAdapter;
        private List<FeedItem> feedItems;
        private String URL_FEED = "https://api.androidhive.info/feed/feed.json";
        @SuppressLint("NewApi")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_c_c_s);

            listView = (ListView) findViewById(R.id.list);

            feedItems = new ArrayList<FeedItem>();

            listAdapter = new FeedListAdapter(this, feedItems);
            listView.setAdapter(listAdapter);

            // These two lines not needed,
            // just to get the look of facebook (changing background color & hiding the icon)
          getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3b5998")));
         getActionBar().setIcon(
                    new ColorDrawable(getResources().getColor(android.R.color.transparent)));

            // We first check for cached request
            Cache cache = AppController.getInstance().getRequestQueue().getCache();
            Cache.Entry entry = cache.get(URL_FEED);
            if (entry != null) {
                // fetch the data from cache
                try {
                    String data = new String(entry.data, "UTF-8");
                    try {
                        parseJsonFeed(new JSONObject(data));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            } else {
                // making fresh volley request and getting json
                JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                        URL_FEED, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        VolleyLog.d(TAG, "Response: " + response.toString());
                        if (response != null) {
                            parseJsonFeed(response);
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                    }
                });

                // Adding request to volley request queue
                AppController.getInstance().addToRequestQueue(jsonReq);
            }

        }

        /**
         * Parsing json reponse and passing the data to feed view list adapter
         */
        private void parseJsonFeed(JSONObject response) {
            try {
                JSONArray feedArray = response.getJSONArray("feed");

                for (int i = 0; i < feedArray.length(); i++) {
                    JSONObject feedObj = (JSONObject) feedArray.get(i);

                    FeedItem item = new FeedItem();
                    item.setId(feedObj.getInt("id"));
                    item.setName(feedObj.getString("name"));

                    // Image might be null sometimes
                    String image = feedObj.isNull("image") ? null : feedObj
                            .getString("image");
                    item.setImge(image);
                    item.setStatus(feedObj.getString("status"));
                    item.setProfilePic(feedObj.getString("profilePic"));
                    item.setTimeStamp(feedObj.getString("timeStamp"));

                    // url might be null sometimes
                    String feedUrl = feedObj.isNull("url") ? null : feedObj
                            .getString("url");
                    item.setUrl(feedUrl);

                    feedItems.add(item);
                }

                // notify data changes to list adapater
                listAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }
}


