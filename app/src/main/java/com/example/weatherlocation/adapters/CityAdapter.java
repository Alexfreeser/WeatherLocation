package com.example.weatherlocation.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weatherlocation.R;
import com.example.weatherlocation.pojo.CityList;

import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    private List<CityList> cities;

    public List<CityList> getCities() {
        return cities;
    }

    public void setCities(List<CityList> cities) {
        this.cities = cities;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.city_item, viewGroup, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder cityViewHolder, int i) {
        CityList city = cities.get(i);
        cityViewHolder.textViewCity.setText(city.getName());
        cityViewHolder.textViewWeather.setText(String.format("%s", city.getWeather()));
        cityViewHolder.textViewTemp.setText(String.format("%s", city.getTemp()));
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    class CityViewHolder extends RecyclerView.ViewHolder {

        TextView textViewCity;
        TextView textViewWeather;
        TextView textViewTemp;

        public CityViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCity = itemView.findViewById(R.id.textViewCity);
            textViewWeather = itemView.findViewById(R.id.textViewWeather);
            textViewTemp = itemView.findViewById(R.id.textViewTemp);
        }
    }
}
