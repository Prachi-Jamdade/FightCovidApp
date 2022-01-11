package com.example.fightcovid2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fightcovid2.covid19api.CountryData;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder>{
    private Context context;
    private List<CountryData> countryFlagList;

    public CountryAdapter(Context context, List<CountryData> countryFlagList) {
        this.context = context;
        this.countryFlagList = countryFlagList;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.countries_item, parent, false);

        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull CountryAdapter.CountryViewHolder holder, int position) {
        CountryData data = countryFlagList.get(position);
        holder.countryCases.setText(NumberFormat.getInstance().format(Integer.parseInt(data.getCases())));
        holder.countryName.setText(data.getCountry());
        holder.srno.setText(String.valueOf(position+1));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("country", data.getCountry());
            context.startActivity(intent);
        });

        Map<String, String> flag = data.getCountryInfo();
        Glide.with(context).load(flag.get("flag")).into(holder.countryFlag);
    }

    public void filterCountryList(List<CountryData> filterList){
        countryFlagList = filterList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return countryFlagList.size();
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder{
        private TextView srno, countryName, countryCases;
        private ImageView countryFlag;

        public CountryViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);

            srno = itemView.findViewById(R.id.srno);
            countryName = itemView.findViewById(R.id.countryName);
            countryCases = itemView.findViewById(R.id.countryCases);
            countryFlag = itemView.findViewById(R.id.countryFlag);
        }
    }
}
