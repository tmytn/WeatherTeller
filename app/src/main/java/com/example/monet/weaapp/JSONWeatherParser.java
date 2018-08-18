package com.example.monet.weaapp;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.monet.weaapp.WeatherInfoEntity.*;

public class JSONWeatherParser {
    public static Weather getWeather(String data) throws JSONException  {
        Weather weather = new WeatherInfoEntity().new Weather();

        JSONObject jObj = new JSONObject(data);

        Weather.Location loc = weather.new Location();

        JSONObject coordObj = getObject("coord", jObj);
        loc.latitude = (getDouble("lat", coordObj));
        loc.longitude = (getDouble("lon", coordObj));

        JSONObject sysObj = getObject("sys",jObj);
        loc.country = (getString("country", sysObj));
        //loc.sunrise = (getInt("sunrise", sysObj));
        //loc.sunset = (getInt("sunset", sysObj));
        loc.city = (getString("name", jObj));
        weather.location = loc;

        // We get weather info (This is an array)
        JSONArray jArr = jObj.getJSONArray("weather");

        JSONObject JSONWeather = jArr.getJSONObject(0);
        weather.currentCondition.setWeatherId(getInt("id", JSONWeather));
        weather.currentCondition.setDescr(getString("description", JSONWeather));
        weather.currentCondition.setCondition(getString("main", JSONWeather));
        weather.currentCondition.setIcon(getString("icon", JSONWeather));

        JSONObject mainObj = getObject("main", jObj);
        weather.currentCondition.setHumidity(getInt("humidity", mainObj));
        weather.currentCondition.setPressure(getInt("pressure", mainObj));
        weather.temperature.setMaxTemp(getDouble("temp_max", mainObj));
        weather.temperature.setMinTemp(getDouble("temp_min", mainObj));
        weather.temperature.setTemp(getDouble("temp", mainObj));

        // Wind
        JSONObject wObj = getObject("wind", jObj);
        weather.wind.setSpeed(getDouble("speed", wObj));
        weather.wind.setDeg(getDouble("deg", wObj));

        // Clouds
        JSONObject cObj = getObject("clouds", jObj);
        weather.clouds.setPerc(getInt("all", cObj));

        // We download the icon to show
        //*/

        return weather;
    }


    private static JSONObject getObject(String tagName, JSONObject jObj)  throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    private static float  getDouble(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    private static int  getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }
}
