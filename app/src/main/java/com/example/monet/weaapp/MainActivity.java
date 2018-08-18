package com.example.monet.weaapp;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.example.monet.weaapp.*;

import org.json.JSONException;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    public WeatherAPI wapi = new WeatherAPI();
    public WeatherInfoEntity wentity = new WeatherInfoEntity();
    public Button btnSearch;
    public EditText editText;
    public ExpandableListView expandableListView;
    public ExpandableListAdapter expandableListAdapter;
    public Object currentActivity;
    public TextView txtNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final String city = "London,UK";
        currentActivity = this;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        editText = (EditText)findViewById(R.id.editText);


        expandableListView = (ExpandableListView) findViewById(R.id.forecastList);

        txtNotification = (TextView) findViewById(R.id.txtNotify);

        btnSearch = (Button)findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = null;
                String editInfo = editText.getText().toString();
                if (editInfo.length() > 0) {
                    try {
                        if (editInfo.matches("^\\d{5}")) {
                            url = (WeatherAPI.BASE_URL + "zip=" + editInfo + ",us" + "&appid=" +
                                    WeatherAPI.OKEY_API + "&units=metric");
                            txtNotification.setText("");
                        } else if (editInfo.matches("^\\w+\\,(\\w){2}")) { //if (editInfo.matches("^\\w+\\,(\\w){2}"))
                            url = (WeatherAPI.BASE_URL + "q=" + editInfo + "&appid=" +
                                    WeatherAPI.OKEY_API + "&units=metric");
                            txtNotification.setText("");
                        }
                        else {
                            txtNotification.setText("Invalid input");
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    if (txtNotification.getText().length() ==0) {
                        JSONWeatherTask task = new JSONWeatherTask();
                        task.execute(new String[]{url});
                    }
                }else {
                    // show nothing
                    txtNotification.setText("Nothing to search");
                }
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class JSONWeatherTask extends AsyncTask<String, Void, WeatherInfoEntity.Weather> {

        @Override
        protected WeatherInfoEntity.Weather doInBackground(String... params) {
            WeatherInfoEntity.Weather weather = wentity.new Weather();
            String data = wapi.getWeatherData(params[0]);
            String str;

            try {
                weather = JSONWeatherParser.getWeather(data);

                // Let's retrieve the icon
                if (weather != null) {
                    //weather.iconData = (byte[]) wapi.getImage(weather.currentCondition.getIcon());
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return weather;

        }


        @Override
        protected void onPostExecute(WeatherInfoEntity.Weather weather) {
            super.onPostExecute(weather);

            if (weather.iconData != null && weather.iconData.length > 0) {
                //Bitmap img = BitmapFactory.decodeByteArray(weather.iconData, 0, weather.iconData.length);
               // imgView.setImageBitmap(img);
            }

            HashMap<String,List<String>> headerItems = new HashMap<String,List<String>>();
            List<String> list1 = new ArrayList<String>() ;
            list1.add((weather.location.city + "," + weather.location.country));
            list1.add(weather.currentCondition.getDescr());
            list1.add(Float.toString(weather.temperature.getTemp())+ " °C ");
            list1.add(Float.toString(weather.wind.getSpeed())+ " mps");
            list1.add(Double.toString(weather.location.latitude));
            list1.add(Double.toString(weather.location.longitude));

            headerItems.put("Today",list1);

            expandableListAdapter = new ExpandableListAdapter((Context) currentActivity,new ArrayList<String>(headerItems.keySet()),headerItems );
            expandableListView.setAdapter(expandableListAdapter);


        }

    }
}
