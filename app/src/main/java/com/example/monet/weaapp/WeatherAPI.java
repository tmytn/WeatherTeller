package com.example.monet.weaapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.io.*;

/**
 * Created by nton on 4/9/2018.
 */

public  class WeatherAPI extends AppCompatActivity {

    ProgressBar pb;
    public List<String> lbHeaderItems;
    public HashMap<String,String> lbListInfo;

    public static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?";
    public static String IMG_URL = "http://openweathermap.org/img/w/";
    public static String OKEY_API = "4ce86b3a58b577e6c1acc9555837655c";

    /*public  class MyWeatherService extends AsyncTask<URL,String,String>{

        @Override
        protected String doInBackground(URL... strings) {

            Log.w("myApp", "string[0] = " + strings[0]);
            HttpURLConnection conn = new HttpURLConnection(strings[0]) {
                @Override
                public void disconnect() {

                }

                @Override
                public boolean usingProxy() {
                    return false;
                }

                @Override
                public void connect() throws IOException {
                    publishProgress("Connecting to url");
                }
            };
            WeatherInfoEntity.Weather weather = new WeatherInfoEntity().new Weather();
            String data = ( (new WeatherAPI()).getWeatherData(strings[0].toString()));
            Log.w("myApp", "data = " + data);
            try {
                weather = JSONWeatherParser.getWeather(data);

                // Let's retrieve the icon
                //weather.iconData = ( (new WeatherAPI()).getImage(weather.currentCondition.getIcon()));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return "";

        }


        @Override
        protected void onPreExecute() {
            pb = new ProgressBar(WeatherAPI.this);

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            publishProgress("Completed");
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }


    } */

    public String getWeatherData(String location) {
        HttpURLConnection con = null ;
        InputStream is = null;

        try {
            con = (HttpURLConnection) ( new URL(location)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            // Let's read the response
            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            if (is == null){
                return  null;
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ( (line = br.readLine()) != null )
                buffer.append(line + "rn");

            is.close();
            con.disconnect();
            return buffer.toString();
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }

        return null;

    }

    public byte[] getImage(String code) {
        HttpURLConnection con = null ;
        InputStream is = null;
        try {
            con = (HttpURLConnection) ( new URL(IMG_URL + code + ".png")).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            // Let's read the response
            is = con.getInputStream();
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            while ( is.read(buffer) != -1)
                baos.write(buffer);

            return baos.toByteArray();
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }

        return null;

    }


}
