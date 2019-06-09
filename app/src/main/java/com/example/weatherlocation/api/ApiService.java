package com.example.weatherlocation.api;

import com.example.weatherlocation.pojo.SearchParams;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("find")
    Observable<SearchParams> getCities(
            @Query("lat") Double lat,
            @Query("lon") Double lon,
            @Query("count") int count,
            @Query("appid") String appid,
            @Query("units") String units);
}
