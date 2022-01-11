package com.example.fightcovid2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class VaccineAvailability extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    DrawerLayout drawerLayout;

    String baseURL = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByPin?";
    private EditText pinCode;
    private Button searchBtn;
    private ProgressBar loadPB;
    private RecyclerView centerRV;

    String areaPin, avlDate;

    ArrayList<CenterModel> centerModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_availability);

        drawerLayout = findViewById(R.id.drawer_layout);

        pinCode = findViewById(R.id.pinCode);
        searchBtn = findViewById(R.id.searchBtn);
        loadPB = findViewById(R.id.loadingPB);
        centerRV = findViewById(R.id.vaccineCenterRV);

        centerModels = new ArrayList<CenterModel>();

        onClickSetup();
    }

    private void onClickSetup() {
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPB.setVisibility(View.VISIBLE);
                DialogFragment df = new PickDate();
                df.show(getSupportFragmentManager(), "Pick Date");
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
        dateFormat.setTimeZone(calendar.getTimeZone());

        String d = dateFormat.format(calendar.getTime());
        setup(d);
    }

    private void setup(String d) {
        avlDate = d;
        centerModels.clear();

        areaPin = pinCode.getText().toString();
        if(areaPin.length() != 6){
            Toast.makeText(this, "Enter Valid Pin Code", Toast.LENGTH_SHORT).show();
        }
        String apiUrl = baseURL + "pincode=" + areaPin + "&date=" + avlDate;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray sessionArray = obj.getJSONArray("sessions");

                    if(sessionArray.length() == 0){
                        Toast.makeText(VaccineAvailability.this, "No Results Found", Toast.LENGTH_LONG).show();
                    }
                    for (int i = 0; i < sessionArray.length(); i++) {
                        JSONObject sesObj = sessionArray.getJSONObject(i);
                        CenterModel centerModel = new CenterModel();
                        centerModel.setCenterName(sesObj.getString("name"));
                        centerModel.setCenterAddress(sesObj.getString("address"));
                        centerModel.setCenterFromTime(sesObj.getString("from"));
                        centerModel.setCenterToTime(sesObj.getString("to"));
                        centerModel.setVaccineName(sesObj.getString("vaccine"));
                        centerModel.setFeeType(sesObj.getString("fee_type"));
                        centerModel.setVaccineAvailability(sesObj.getInt("available_capacity"));
                        centerModel.setAgeLimit(sesObj.getInt("min_age_limit"));
                        centerModels.add(centerModel);
                    }


                    CenterAdapter centerAdapter = new CenterAdapter(getApplicationContext(), centerModels);
                    centerRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    centerRV.setAdapter(centerAdapter);
                    loadPB.setVisibility(View.INVISIBLE);

                } catch (Exception e) {
                    loadPB.setVisibility(View.INVISIBLE);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadPB.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
        recreate();
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