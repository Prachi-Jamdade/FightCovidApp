package com.example.fightcovid2.covid19api;

import com.example.fightcovid2.NewsModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetrofitAPI {
    @GET
    Call<NewsModel> getNews(@Url String url);
}
