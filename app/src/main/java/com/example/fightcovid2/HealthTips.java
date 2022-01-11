package com.example.fightcovid2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

public class HealthTips extends AppCompatActivity {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_tips);

        drawerLayout =  findViewById(R.id.drawer_layout);
    }

    public void clickMenu(View view) {
        MainActivity.openDrawer(drawerLayout);
    }

    public void clickLogo(View view) {
        MainActivity.closeDrawer(drawerLayout);
    }

    public void clickHome(View view) {
        MainActivity.redirectActivity(this, MainActivity.class);
    }

    public void clickTrackCountries(View view){
        MainActivity.redirectActivity(this, CountryListActivity.class);
    }

    public void clickTips(View view) {
        recreate();
    }

    public void clickNews(View view){
        MainActivity.redirectActivity(this, CovidNews.class);
    }

    public void clickVaccine(View view){
        MainActivity.redirectActivity(this, VaccineAvailability.class);
    }

    public void clickAbout(View view) {

        MainActivity.redirectActivity(this, About.class);
    }

    public void clickKnow(View view){
        MainActivity.redirectActivity(this, KnowAboutVaccine.class);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.closeDrawer(drawerLayout);
    }
}