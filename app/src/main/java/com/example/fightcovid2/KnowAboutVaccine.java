package com.example.fightcovid2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static com.example.fightcovid2.MainActivity.redirectActivity;

public class KnowAboutVaccine extends AppCompatActivity {

    DrawerLayout drawerLayout;
    TextView txt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_know_about_vaccine);

        drawerLayout = findViewById(R.id.drawer_layout);
    }

    public void clickMenu(View view) {
        MainActivity.openDrawer(drawerLayout);
    }

    public void clickLogo(View view) {
        MainActivity.closeDrawer(drawerLayout);
    }

    public void clickHome(View view) {
        redirectActivity(this, MainActivity.class);
    }

    public void clickTrackCountries(View view){
        redirectActivity(this, CountryListActivity.class);
    }

    public void clickNews(View view){
        redirectActivity(this, CovidNews.class);
    }

    public void clickKnow(View view) {
        recreate();
    }

    public void clickVaccine(View view){
        redirectActivity(this, VaccineAvailability.class);
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
        MainActivity.closeDrawer(drawerLayout);
    }
}