package com.example.fightcovid2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends AppCompatActivity {

    String title, desc, url, imageUrl, content;
    private TextView titleTV, subDescTV, contentTV;
    private ImageView newsIV;
    private Button readNewsBtn, shareNewsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        titleTV = findViewById(R.id.titleTV);
        subDescTV = findViewById(R.id.subtitleTV);
        contentTV = findViewById(R.id.contentTV);
        newsIV = findViewById(R.id.newsIV);
        readNewsBtn = findViewById(R.id.readNewsBtn);
        shareNewsBtn = findViewById(R.id.shareNewsBtn);

        title = getIntent().getStringExtra("title");
        desc = getIntent().getStringExtra("desc");
        url = getIntent().getStringExtra("url");
        imageUrl = getIntent().getStringExtra("image");
        content = getIntent().getStringExtra("content");

        titleTV.setText(title);
        subDescTV.setText(desc);
        contentTV.setText(content);
        Picasso.get().load(imageUrl).into(newsIV);
        readNewsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        shareNewsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, title);
                    String body = title + "\n" + url + "\n";
                    intent.putExtra(Intent.EXTRA_TEXT, body);
                    startActivity(Intent.createChooser(intent, "Share with: "));
                }
                catch (Exception e){
                    Toast.makeText(NewsDetailActivity.this, "Sorry. \nCan't be Share", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}