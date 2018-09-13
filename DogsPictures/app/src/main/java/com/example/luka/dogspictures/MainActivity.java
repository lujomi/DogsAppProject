package com.example.luka.dogspictures;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText txtCount;
    public static final String KEY_IMAGES_SAVED = "key_images_saved";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCount = findViewById(R.id.inputEnterNumber);

        Button btnRandom = findViewById(R.id.buttonGetRandomPictures);
        btnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(txtCount.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Molimo unesite broj slika", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(MainActivity.this, ImagesActivity.class);
                    i.putExtra("is_saved", false);
                    i.putExtra("count", Integer.parseInt(txtCount.getText().toString()));
                    startActivity(i);
                }
            }
        });

        final Button btnSaved = findViewById(R.id.buttonGetSavedPictures);
        btnSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imagesJson = getSavedImages();
                if (TextUtils.isEmpty(imagesJson))
                    Toast.makeText(MainActivity.this, "Nema spremljenih slika", Toast.LENGTH_SHORT).show();
                else {
                    Intent i = new Intent(MainActivity.this, ImagesActivity.class);
                    i.putExtra("is_saved", true);
                    i.putExtra("list", imagesJson);

                    startActivity(i);
                }
            }
        });
    }

    public String getSavedImages() {
        SharedPreferences pref = getSharedPreferences("shared", MODE_PRIVATE);
        return pref.getString(KEY_IMAGES_SAVED, "");
    }
}
