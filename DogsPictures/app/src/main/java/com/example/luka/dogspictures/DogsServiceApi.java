package com.example.luka.dogspictures;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface DogsServiceApi {
    @GET("image/random/{count}")
    Call<DogResponse> getRandomImagesForCount(
            @Path("count") int count
    );
}
