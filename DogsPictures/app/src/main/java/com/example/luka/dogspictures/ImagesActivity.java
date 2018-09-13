package com.example.luka.dogspictures;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImagesActivity extends AppCompatActivity {

    public static final String KEY_IMAGES_SAVED = "key_images_saved";
    private List<String> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        Bundle extras = getIntent().getExtras();
        if (extras.getBoolean("is_saved")) {
            String imagesJson = extras.getString("list");
            String[] img = new Gson().fromJson(imagesJson, String[].class);
            images = Arrays.asList(img);
            setAdapter(images, true);
        } else {
            int count = 1;
            if (extras != null) {
                count = extras.getInt("count");
            }

            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setMessage("Molimo pričekajte");
            dialog.show();

            final DogsServiceApi apiClient = DogsServiceAdapter.provideApiClient();

            apiClient.getRandomImagesForCount(count).enqueue(new Callback<DogResponse>() {
                @Override
                public void onResponse(Call<DogResponse> call, Response<DogResponse> response) {
                    dialog.dismiss();

                    if (response.isSuccessful()) {
                        images = response.body().getMessage();
                        setAdapter(images, false);
                    }
                }

                @Override
                public void onFailure(Call<DogResponse> call, Throwable t) {
                    Toast.makeText(ImagesActivity.this, "Pokušajte kasnije", Toast.LENGTH_SHORT).show();

                }
            });
        }

        Button btnSaveImages = findViewById(R.id.btnSaveImages);
        if (extras.getBoolean("is_saved")) {
            btnSaveImages.setVisibility(View.GONE);
        } else {

            btnSaveImages.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (images != null)
                        saveImages();
                    Toast.makeText(ImagesActivity.this, "Slike su spremljene", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    private void setAdapter(List<String> images, boolean isSaved) {
        RecyclerView recyclerView = findViewById(R.id.recycler);
        DogsAdapter adapter = new DogsAdapter(ImagesActivity.this, images, isSaved);
        recyclerView.setLayoutManager(new LinearLayoutManager(ImagesActivity.this));
        recyclerView.setAdapter(adapter);
    }

    private void saveImages() {
        SharedPreferences pref = getSharedPreferences("shared", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_IMAGES_SAVED, new Gson().toJson(images)).apply();
    }
}





