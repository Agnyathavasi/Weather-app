package com.example.clima;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clima.Helper.Helper;
import com.example.clima.Model.OpenWeatherMap;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class Splash_screen extends AppCompatActivity {

    ImageButton mlocation, mcity;
    Calendar calendar = Calendar.getInstance();
    ImageView micon;
    double latitude, longitude;
    TextView mcityname, mcitydesc, mtemp, mintemp, maxtemp, sunrise, sunset, windspeed, humidity,dateday;
    private FusedLocationProviderClient client;
    TextView mpressure,mfeelslike;
    String date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime()),daydecoded;
    int days = 0;
    int i = 0;
    public OpenWeatherMap openWeatherMap = new OpenWeatherMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();
        dateday = findViewById(R.id.dateday);
        date = date.substring(0,date.length()-6);
        calendar = Calendar.getInstance();
        System.out.println(days);

        Intent inte = getIntent();
        String cname =  inte.getStringExtra(Search.EXTRA);

        dateday.setText(date);

        mcityname = findViewById(R.id.cityname);
        mcitydesc = findViewById(R.id.citydesc);
        micon = findViewById(R.id.cityicon);
        mtemp = findViewById(R.id.citytemp);
        maxtemp = findViewById(R.id.maxtemp);
        mintemp = findViewById(R.id.mintemp);
        mfeelslike = findViewById(R.id.feelslike);
        mpressure = findViewById(R.id.pressure);
        sunrise = findViewById(R.id.sunrise);
        sunset = findViewById(R.id.sunset);
        windspeed = findViewById(R.id.windspeed);
        humidity = findViewById(R.id.humidity);
        mcity = findViewById(R.id.btncity);
        mlocation = findViewById(R.id.btnlocation);
        client = LocationServices.getFusedLocationProviderClient(this);
        new Getweather().execute(Common.apifromcity("Bengaluru"));
        if (cname!= null){
            new Getweather().execute(Common.apifromcity(cname));
        }


        mlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(Splash_screen.this,
                        ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermission();
                    Toast.makeText(getApplicationContext(),
                            "Please enable location Permissions for this app",Toast.LENGTH_LONG).show();
                    return;
                }

                client.getLastLocation().addOnSuccessListener(Splash_screen.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            System.out.println("LAT : " + latitude + "  LONG : " + longitude);
                            new Getweather().execute(Common.apiRequets(String.valueOf(latitude),String.valueOf(longitude)));
                        }
                        else {
                            Toast.makeText(getApplicationContext(),
                                    "Your Location is not turned ON",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        mcity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opensearchmenu();
                Splash_screen.this.finish();
            }
        });
    }

    void opensearchmenu(){
        Intent in = new Intent(this,Search.class);
        startActivity(in);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }

    String getWeatherIcon(int condition) {
        if (condition < 300) {
            return "🌩";
        } else if (condition < 400) {
            return "🌧";
        } else if (condition < 600) {
            return "☔️";
        } else if (condition < 700) {
            return "☃️";
        } else if (condition < 800) {
            return "🌫";
        } else if (condition == 800) {
            return "☀️";
        } else if (condition <= 804) {
            return "☁️";
        } else {
            return "🤷‍";
        }
    }

    private class Getweather extends AsyncTask<String, Void, String>{

        ProgressDialog progressDialog = new ProgressDialog(Splash_screen.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Please Wait...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String stream = null;
            String urlstr = strings[0];
            Helper http = new Helper();
            stream = http.getHttpdata(urlstr);
            return stream;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.contains("Error: Not found city")){
                progressDialog.dismiss();
                return;
            }

            Gson gson = new Gson();
            Type mtype = new TypeToken<OpenWeatherMap>(){}.getType();
            openWeatherMap = gson.fromJson(s,mtype);
            progressDialog.dismiss();
            DecimalFormat cur = new DecimalFormat("##.##");
            mcityname.setText(String.format("%s,%s",openWeatherMap.getName(),openWeatherMap.getSys().getCountry()));
            mcitydesc.setText(String.format("%s",openWeatherMap.getWeather().get(0).getDescription()));
            int temper = (int)(openWeatherMap.getMain().getTemp()-273.15);
            mtemp.setText(String.format((String.format(temper+"°"))));
            maxtemp.setText(String.format(cur.format(openWeatherMap.getMain().getMax()-273.15)+"°"));
            mintemp.setText(String.format(cur.format(openWeatherMap.getMain().getMin()-273.15)+"°"));
            Double feelsliketemp = (openWeatherMap.getMain().getFeelslike()-273.15);
            mfeelslike.setText(String.format(cur.format(feelsliketemp)+"°"));
            mpressure.setText(String.format((openWeatherMap.getMain().getPressure())/1000 +"atm"));
            char c = '%';

            humidity.setText(String.format(""+openWeatherMap.getMain().getHumidity())+ c);
            long unixSeconds = (long) openWeatherMap.getSys().getSunrise();

            TimeZone timeZone = TimeZone.getTimeZone(openWeatherMap.getTimezone().toString());
            long timezoneoffset = openWeatherMap.getTimezone();
            timezoneoffset = timezoneoffset / 60;
            System.out.println(timezoneoffset);
            int hrs = (int)timezoneoffset / 60;
            int mins = (int)timezoneoffset % 60;
            Date date = new java.util.Date(unixSeconds*1000L);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            String timezone = "";
            if (hrs < 0 && mins!=0){
                timezone = ("GMT"+hrs+mins);
            }
            else if (hrs < 0)
            {
                timezone = ("GMT"+hrs);
            }
            else if (hrs > 0 && mins==0)
            {
                timezone = ("GMT+"+hrs);
            }
            else if (hrs > 0)
            {
                timezone = ("GMT+"+hrs+mins);
            }
            sdf.setTimeZone(TimeZone.getTimeZone(timezone));
            System.out.println("GMT"+hrs+mins);
            String formattedDate = sdf.format(date);
            sunrise.setText(String.format(formattedDate + ""));
            long unixSeconds2 = (long) openWeatherMap.getSys().getSunset();
            Date date2 = new java.util.Date(unixSeconds2*1000L);
            String formattedDate2 = sdf.format(date2);

            sunset.setText(String.format(formattedDate2 + ""));

            windspeed.setText(String.format(openWeatherMap.getWind().getSpeed()+"m/s"));

            Picasso.get().load(Common.getIcon(openWeatherMap.getWeather().get(0).getIcon())).into(micon);

        }
    }
}
