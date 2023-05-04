package com.example.myweather;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {
Context context;
ArrayList<WeatherModal> weatherModalArrayList;

public WeatherAdapter(Context context, List<WeatherModal> weatherModalList) {
        this.context = context;
        this.weatherModalArrayList = (ArrayList<WeatherModal>) weatherModalList;
    }

@NonNull
    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.weather_rv,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherAdapter.ViewHolder holder, int position) {
WeatherModal weatherModal=weatherModalArrayList.get(position);
        Picasso.get().load("https:".concat(weatherModal.getIcon())).into(holder.rv_condition);
        holder.rv_wind.setText(weatherModal.getWindSpeed().concat("Km/hr"));
        holder.rv_temp.setText(weatherModal.getTemperature().concat("°C"));
        SimpleDateFormat input=new SimpleDateFormat("yyyy-MM-dd hh:mm");
        SimpleDateFormat output=new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
        try{
            Date t=input.parse(weatherModal.getTime());
            holder.rv_date.setText(output.format(t));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
    return weatherModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView rv_temp,rv_date,rv_wind;
        ImageView rv_condition;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
          rv_temp=  itemView.findViewById(R.id.rv_temp);
            rv_date=  itemView.findViewById(R.id.rv_time);
            rv_wind=itemView.findViewById(R.id.rv_windspeed);
            rv_condition=itemView.findViewById(R.id.rv_icon);


        }
    }
}
