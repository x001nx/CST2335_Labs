package com.example.delle6330.androidlabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Arrays;

public class WeatherForecast extends Activity {
    public ProgressBar progressBar;
    public TextView currentTempTV;
    public TextView minTempTV;
    public TextView maxTempTV;
    public TextView windSpeedTV;
    public ImageView weatherIcon;
    Bitmap image;
    String currentTemp = "N/A";
    String maxTemp = "N/A";
    String minTemp = "N/A";
    String windSpeed = "N/A";
    String gradC = Character.toString((char) 0x00B0)+"C";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        currentTempTV = findViewById(R.id.currentTemp);
        maxTempTV = findViewById(R.id.maxTemp);
        minTempTV = findViewById(R.id.minTemp);
        windSpeedTV = findViewById(R.id.windSpeed);
        weatherIcon = findViewById(R.id.weatherIcon);

        currentTempTV.setText("Current Temperature: " + currentTemp + gradC);
        minTempTV.setText("Minimal Temperature: " + minTemp + gradC);
        maxTempTV.setText("Maximal Temperature: " + maxTemp + gradC);
        windSpeedTV.setText("Wind Speed: " + windSpeed);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(100);


        ForecastQuery fq = new ForecastQuery();
        fq.execute();
    }

    public class ForecastQuery extends AsyncTask<String, Integer, String> {

        String result = "Not Available";
        String urlString="http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";
        String urlIcon = "";
        String iconName = "";


        @Override
        protected String doInBackground(String... strings){
            Log.i("***** WeatherForecast", "doInBackground");
            URL url;
            HttpURLConnection conn = null;
            InputStream in = null;

            //HTTPUrlConnection - START
            try {
                url = new URL(urlString);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
//                conn.setDoInput(true);
//                // Starts the query
                conn.connect();
                //Input Stream from HttpURLConnection
                in = conn.getInputStream();
            } catch (MalformedURLException e) {
            }
            catch (IOException e) {
            }
            //HTTPUrlConnection - END

            //XmlPullParser - START
            try {
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in, null);

                while (parser.next() != XmlPullParser.END_DOCUMENT) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }
                    //Get Tag name
                    String name = parser.getName();
                    // Starts by looking for the temperature
                    if (name.equals("temperature")) {
                        currentTemp = parser.getAttributeValue(null, "value");
                        Thread.sleep(700);
//                        Log.i("****** currentTemp ", currentTemp);
                        publishProgress(25);


                        minTemp = parser.getAttributeValue(null, "min");
                        Thread.sleep(700);
//                        Log.i("****** minTemp ", minTemp);
                        publishProgress(50);


                        maxTemp = parser.getAttributeValue(null, "max");
//                        Log.i("****** maxTemp ", maxTemp);
                        Thread.sleep(700);
                        publishProgress(75);

                    }
                    //look for speed tag
                    if (name.equals("speed")) {
                        windSpeed = parser.getAttributeValue(null, "value");
                        Thread.sleep(700);
                        publishProgress(100);


                    }
                    if (name.equals("weather")) {
                        iconName = parser.getAttributeValue(null, "icon");
                        urlIcon = "http://openweathermap.org/img/w/" + iconName + ".png";
                        Log.i("****** Icon", "Name " + iconName + " url" + urlIcon);
                        if(fileExistance(iconName + ".png")){
                            Log.i("****** fileExists()",  String.valueOf(fileExistance(iconName +".png")) + " "  + iconName);
                            image = BitmapFactory.decodeFile("/data/user/0/com.example.delle6330.androidlabs/files/" + iconName +".png");
                        }
                        else {
                            image  = getImage(new URL(urlIcon));
                            FileOutputStream outputStream = openFileOutput( iconName + ".png", Context.MODE_PRIVATE);
                            image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                            outputStream.flush();
                            outputStream.close();
                        }
                    }
                }
//            Log.i("***** WeatherForecast", "current " + currentTemp + " min " + minTemp + " max " + maxTemp);

            } catch (Exception e){
                Log.i("Crash", e.getMessage());
            }
            try{
                in.close();
            } catch (IOException e) {
            }

            return result;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressBar.setVisibility(View.INVISIBLE);
            weatherIcon.setImageBitmap(image);
        }

        protected void onProgressUpdate(Integer... progressValue)
        {
            progressBar.setVisibility(View.VISIBLE);
            if (progressBar.getProgress() == 25){
                currentTempTV.setText("Current Temperature: " + currentTemp + gradC);
            }
            else if (progressBar.getProgress() == 50){
                minTempTV.setText("Minimal Temperature: " + minTemp + gradC);
            }
            else if (progressBar.getProgress() == 75){
                maxTempTV.setText("Maximal Temperature: " + maxTemp + gradC);
                windSpeedTV.setText("Wind Speed: " + windSpeed + "m/s");
            }

            progressBar.setProgress(progressValue[0]);

        }

        public Bitmap getImage(URL url) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            Log.i("****** file path ", String.valueOf(getBaseContext().getFileStreamPath(fname)));
            return file.exists();
        }

    }
}
