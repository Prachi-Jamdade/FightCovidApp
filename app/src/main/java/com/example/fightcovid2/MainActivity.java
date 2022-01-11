package com.example.fightcovid2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fightcovid2.covid19api.APIUtilities;
import com.example.fightcovid2.covid19api.CountryData;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView totalConfirm, totalActive, totalRecovered, totalDeaths, totalTests;
    private TextView todayConfirm, todayRecovered, todayDeaths;
    private TextView updateDate;
    private PieChart pieChart;
    private TextView country;

    private List<CountryData> country_data;
    String countryName = "India";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recreate();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        country_data = new ArrayList<>();

        if (getIntent().getStringExtra("country") != null)
            countryName = getIntent().getStringExtra("country");


        initialize();

        TextView cname = country;
        cname.setText(countryName);

        cname.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, CountryListActivity.class)));

        APIUtilities.getAPIInterface().getCountryData()
                .enqueue(new Callback<List<CountryData>>() {
                    @Override
                    public void onResponse(Call<List<CountryData>> call, Response<List<CountryData>> response) {
                        country_data.addAll(response.body());

                        for(int i=0; i<country_data.size(); i++){
                            if (country_data.get(i).getCountry().equals(countryName)){
                                int confirm = Integer.parseInt(country_data.get(i).getCases());
                                int active = Integer.parseInt(country_data.get(i).getActive());
                                int recovered = Integer.parseInt(country_data.get(i).getRecovered());
                                int deaths = Integer.parseInt(country_data.get(i).getDeaths());

                                totalActive.setText(NumberFormat.getInstance().format(active));
                                totalConfirm.setText(NumberFormat.getInstance().format(confirm));
                                totalRecovered.setText(NumberFormat.getInstance().format(recovered));
                                totalDeaths.setText(NumberFormat.getInstance().format(deaths));
                                totalTests.setText(NumberFormat.getInstance().format(Integer.parseInt(country_data.get(i).getTests())));

                                todayDeaths.setText(NumberFormat.getInstance().format(Integer.parseInt(country_data.get(i).getTodayDeaths())));
                                todayRecovered.setText(NumberFormat.getInstance().format(Integer.parseInt(country_data.get(i).getTodayRecovered())));
                                todayConfirm.setText(NumberFormat.getInstance().format(Integer.parseInt(country_data.get(i).getTodayCases())));

                                setDate(country_data.get(i).getUpdated());
                                pieChart.addPieSlice(new PieModel("Confirm", confirm, getResources().getColor(R.color.yellow)));
                                pieChart.addPieSlice(new PieModel("Active", active, getResources().getColor(R.color.blue_pie)));
                                pieChart.addPieSlice(new PieModel("Recovered", recovered, getResources().getColor(R.color.green_pie)));
                                pieChart.addPieSlice(new PieModel("Death", deaths, getResources().getColor(R.color.red_pie)));
                                pieChart.startAnimation();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<CountryData>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error! Something went wrong" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });
    }

    private void setDate(String updated) {
        DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss");
        long ms = Long.parseLong(updated);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ms);
        updateDate.setText("Updated at " + dateFormat.format(calendar.getTime()));
    }

    private void initialize(){
        country = (TextView) findViewById(R.id.country);
        updateDate = (TextView) findViewById(R.id.updateDate);
        pieChart = (PieChart) findViewById(R.id.pieChart);
        todayConfirm = (TextView) findViewById(R.id.todayConfirm);
        totalConfirm = (TextView) findViewById(R.id.totalConfirm);
        totalActive = (TextView) findViewById(R.id.totalActive);
        totalRecovered = (TextView) findViewById(R.id.totalRecovered);
        todayRecovered = (TextView) findViewById(R.id.todayRecovered);
        totalDeaths = (TextView) findViewById(R.id.totalDeaths);
        todayDeaths = (TextView) findViewById(R.id.todayDeaths);
        totalTests = (TextView) findViewById(R.id.totalTests);
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
        recreate();
    }

    public void clickTrackCountries(View view){
        redirectActivity(this, CountryListActivity.class);
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