package com.example.fightcovid2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.fightcovid2.covid19api.RetrofitAPI;

public class CovidNews extends AppCompatActivity {

    public static final String API_KEY = "9c57845c931a4a159655d2dfebf24491";
    DrawerLayout drawerLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView newsRV;
    private ProgressBar loadingPB;

    private ArrayList<Articles> articlesArrayList;
    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_news);

        drawerLayout = findViewById(R.id.drawer_layout);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        newsRV = findViewById(R.id.newsRV);
        loadingPB = findViewById(R.id.loadPB);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recreate();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        articlesArrayList = new ArrayList<>();
        newsAdapter = new NewsAdapter(articlesArrayList, this);
        newsRV.setLayoutManager(new LinearLayoutManager(this));
        newsRV.setAdapter(newsAdapter);

        getNews();
        newsAdapter.notifyDataSetChanged();
    }

    private void getNews() {
        loadingPB.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String url = "https://newsapi.org/v2/everything/?q=omicron&language=en&pageSize=100&apiKey=" + API_KEY;
        String BASE_URL = "https://newsapi.org/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<NewsModel> call;
        call = retrofitAPI.getNews(url);
        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                NewsModel newsModel = response.body();
                loadingPB.setVisibility(View.GONE);
                ArrayList<Articles> articles = newsModel.getArticles();
                for(int i=0; i<articles.size(); i++){
                    articlesArrayList.add(new Articles(articles.get(i).getTitle(), articles.get(i).getDescription(),
                            articles.get(i).getUrlToImage(), articles.get(i).getUrl(), articles.get(i).getContent()));
                }
                newsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                loadingPB.setVisibility(View.INVISIBLE);
                Toast.makeText(CovidNews.this, "Failed to Load News", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // navigation activities
    public void clickMenu(View view) {
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void clickLogo(View view) {
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void clickHome(View view) {
        redirectActivity(this, MainActivity.class);
    }

    public void clickTrackCountries(View view){
        redirectActivity(this, CountryListActivity.class);
    }

    public void clickVaccine(View view){
        redirectActivity(this, VaccineAvailability.class);
    }

    public void clickNews(View view){
        recreate();
    }

    public void clickKnow(View view) {
        redirectActivity(this, KnowAboutVaccine.class);
    }

    public static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    public void clickTips(View view) {
        redirectActivity(this, HealthTips.class);
    }

    public void clickAbout(View view){
        redirectActivity(this, About.class);
    }
    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
}