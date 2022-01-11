package com.example.fightcovid2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CenterAdapter extends RecyclerView.Adapter<CenterAdapter.ViewHolder>{

    private Context context;
    private ArrayList<CenterModel> centerModelArrayList;

    public CenterAdapter(Context context, ArrayList<CenterModel> centerModelArrayList) {
        this.context = context;
        this.centerModelArrayList = centerModelArrayList;
    }

    @NonNull
    @NotNull
    @Override
    public CenterAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vaccine_center_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CenterAdapter.ViewHolder holder, int position) {
        CenterModel center = centerModelArrayList.get(position);
        holder.centerName.setText(center.getCenterName());
        holder.centerAddress.setText(center.getCenterAddress());
        holder.centerTime.setText("From: " + center.getCenterFromTime() + " To: " + center.getCenterToTime());
        holder.vaccineName.setText(center.getVaccineName());
        holder.vaccineFees.setText(center.getFeeType());
        int limit = center.getAgeLimit();
        holder.ageLimit.setText("Age Limit: " + String.valueOf(limit));
        int avail = center.getVaccineAvailability();
        holder.vaccineAvail.setText("Availability: " + String.valueOf(avail));
    }

    @Override
    public int getItemCount() {
        return centerModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView centerName, centerAddress, centerTime, vaccineName, vaccineFees, ageLimit, vaccineAvail;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            centerName =  itemView.findViewById(R.id.centerName);
            centerAddress =  itemView.findViewById(R.id.centerLocation);
            centerTime =  itemView.findViewById(R.id.time);
            vaccineName =  itemView.findViewById(R.id.vaccineName);
            vaccineFees =  itemView.findViewById(R.id.vaccineFees);
            ageLimit =  itemView.findViewById(R.id.ageLimit);
            vaccineAvail =  itemView.findViewById(R.id.vaccineAvail);
        }
    }
}
