package com.example.weatherlocation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.weatherlocation.adapters.CityAdapter;
import com.example.weatherlocation.api.ApiFactory;
import com.example.weatherlocation.api.ApiService;
import com.example.weatherlocation.pojo.CityList;
import com.example.weatherlocation.pojo.SearchParams;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CitiesFragment extends Fragment {

    private static final String API_KEY = "8eda8cc564c4bb530445526ee56cb222";
    private static final String UNITS = "metric";
    private static final double BASE_LAT = 47.2313;
    private static final double BASE_LON = 39.7233;
    private static final int COUNT = 20;

    RecyclerView recyclerViewCities;
    CityAdapter adapter;

    Disposable disposable;
    CompositeDisposable compositeDisposable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.cities_fragment, container, false);
        recyclerViewCities = view.findViewById(R.id.recyclerViewCities);
        adapter = new CityAdapter();
        adapter.setCities(new ArrayList<CityList>());
        recyclerViewCities.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerViewCities.setAdapter(adapter);
        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();
        compositeDisposable = new CompositeDisposable();
        disposable = apiService.getCities(BASE_LAT, BASE_LON, COUNT, API_KEY, UNITS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SearchParams>() {
                    @Override
                    public void accept(SearchParams searchParams) throws Exception {
                        adapter.setCities(searchParams.getList());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(view.getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        compositeDisposable.add(disposable);
        return view;
    }

    @Override
    public void onDestroyView() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
        super.onDestroyView();
    }
}
