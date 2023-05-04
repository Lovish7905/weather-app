package com.example.myweather;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
//import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
//import android.widget.AbsListView;
import android.widget.EditText;
//import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
public class HomePage extends AppCompatActivity {
    ProgressBar pg;
    TextView city, temperature, condition, signout;
    EditText searchbar;
    ImageView  conditionimg, background;
    RelativeLayout layout1;
    RecyclerView bottom;
    ImageView img;
    ArrayList<WeatherModal> weatherModalArrayList;
    WeatherAdapter weatherAdapter;
    LocationManager locationManager;
    int PERMISSIONCODE = 1;
    FirebaseAuth fauth;
    String finalCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        fauth = FirebaseAuth.getInstance();
        signout = (TextView) findViewById(R.id.signout);
        city = (TextView) findViewById(R.id.city);
        temperature = (TextView) findViewById(R.id.temp);
        condition = (TextView) findViewById(R.id.condition);
        searchbar = (EditText) findViewById(R.id.searchcity);
        pg = (ProgressBar) findViewById(R.id.pg);
        layout1 = (RelativeLayout) findViewById(R.id.layout1);
        bottom = (RecyclerView) findViewById(R.id.weather);
        background = (ImageView) findViewById(R.id.backIv);
        img = (ImageView) findViewById(R.id.img3);
        conditionimg = (ImageView) findViewById(R.id.tempimg);
        bottom.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        weatherModalArrayList = new ArrayList<>();
        weatherAdapter=new WeatherAdapter(this,weatherModalArrayList);
        bottom.setAdapter(weatherAdapter);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

       // if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

       //     ActivityCompat.requestPermissions(HomePage.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONCODE);
       // }
    //    Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
   //finalCity=getName(location.getLongitude(),location.getLatitude());
finalCity="Delhi";
   getInfo(finalCity);


        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fauth.signOut();
                startActivity(new Intent(HomePage.this, MainActivity2.class));
                finish();
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pg.setVisibility(View.VISIBLE);

                String city1= searchbar.getText().toString().trim();
                if (city1.isEmpty()) {
                    Toast.makeText(HomePage.this, "Enter Valid City", Toast.LENGTH_SHORT).show();
                } else {
                    city.setText(city1);
                    getInfo(city1);
                }
            }
        });
    }

    public String getName(double longitude, double latitude) {
        String city1 = "City Not Found";
        Geocoder gc = new Geocoder(getBaseContext(), Locale.getDefault());
        try {
            List<Address> addressList = gc.getFromLocation(latitude, longitude, 10);
            for (Address ad : addressList) {
                if (ad != null) {
                    String city = ad.getLocality();
                    if (city != null && city.equals("")) {
                        city1 = city;
                    }
                } else {
                    Toast.makeText(HomePage.this, "User City Not Found", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String city11 = city1;
        return city11;
    }
    void getInfo(String city){
        pg.setVisibility(View.VISIBLE);
        RequestQueue requestQueue1 = Volley.newRequestQueue(HomePage.this);
        String url = "http://api.weatherapi.com/v1/forecast.json?key=e5d76d98b1ff469c8b0124545211011&q=" + city+ "&days=1&aqi=no&alerts=no";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String temperature1 = response.getJSONObject("current").getString("temp_c");
                    int day = response.getJSONObject("current").getInt("is_day");
                    String condition = response.getJSONObject("current").getJSONObject("condition").getString("text");
                    String conditionImg = response.getJSONObject("current").getJSONObject("condition").getString("icon");
                    Picasso.get().load("https:".concat(conditionImg)).into(conditionimg);
                    if (day == 1) {
                        Picasso.get().load("https://images.unsplash.com/photo-1539920951450-2b2d59cff66d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1888&q=80").into(background);

                    } else {
                        Picasso.get().load("https://images.unsplash.com/photo-1507400492013-162706c8c05e?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1918&q=80").into(background);
                    }

                    temperature.setText(temperature1 + "°C");
                    JSONObject obj = response.getJSONObject("forecast");
                    JSONObject jsonObj = obj.getJSONArray("forecastday").getJSONObject(0);
                    JSONArray jsonArray = jsonObj.getJSONArray("hour");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject hourObj = jsonArray.getJSONObject(i);
                        String time = hourObj.getString("time");
                        String temper = hourObj.getString("temp_c");
                        String img = hourObj.getJSONObject("condition").getString("icon");
                        String wind = hourObj.getString("wind_kph");
                        weatherModalArrayList.add(new WeatherModal(time, temper, img, wind));
                    }
                    weatherAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomePage.this, "Some error occured", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue1.add(jsonObjectRequest);
    }

    //@Override
    //public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    //    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    //    if(requestCode==PERMISSIONCODE){
      //      if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
      //          Toast.makeText(this,"PERMISSION GRANTED",Toast.LENGTH_SHORT).show();
      //      }
       //     else{
        //        Toast.makeText(this,"PERMISSION NOT GRANTED",Toast.LENGTH_SHORT).show();
        //        finish();
       //     }
        //}
   // }
}

