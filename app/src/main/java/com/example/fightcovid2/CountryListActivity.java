package com.example.fightcovid2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.fightcovid2.covid19api.APIUtilities;
import com.example.fightcovid2.covid19api.CountryData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryListActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private SearchView searchCountry;
    private SearchManager searchManager;
    private RecyclerView recyclerView;
    private List<CountryData> countryList;
    private CountryAdapter countryAdapter;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_list);

        drawerLayout = findViewById(R.id.drawer_layout);

        searchCountry = (SearchView) findViewById(R.id.searchCountry);
        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        searchCountry.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        recyclerView = findViewById(R.id.countriesRV);
        countryList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        countryAdapter = new CountryAdapter(this, countryList);
        recyclerView.setAdapter(countryAdapter);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading Countries...");
        dialog.setCancelable(false);
        dialog.show();


        APIUtilities.getAPIInterface().getCountryData().enqueue(new Callback<List<CountryData>>() {
            @Override
            public void onResponse(Call<List<CountryData>> call, Response<List<CountryData>> response) {
                countryList.addAll(response.body());

                countryAdapter.notifyDataSetChanged();

                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<CountryData>> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(CountryListActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        searchCountry.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (countryAdapter != null)
                    filter(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (countryAdapter != null)
                    filter(newText);
                return false;
            }
        });
    }

    private void filter(String text) {
        List<CountryData> listFilter = new ArrayList<>();
        for (CountryData items: countryList){
            if (items.getCountry().toLowerCase().contains(text.toLowerCase())){
                listFilter.add(items);
            }
        }

        countryAdapter.filterCountryList(listFilter);
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
        recreate();
    }

    public void clickVaccine(View view){
        redirectActivity(this, VaccineAvailability.class);
    }

    public void clickNews(View view){
        redirectActivity(this, CovidNews.class);
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