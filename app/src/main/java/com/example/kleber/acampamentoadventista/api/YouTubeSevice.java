package com.example.kleber.acampamentoadventista.api;

import com.example.kleber.acampamentoadventista.modelos.youtube.Resultado;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YouTubeSevice {

    /*

    https://www.googleapis.com/youtube/v3/
    search
    ?part=snippet
    &order=date
    &maxResults=20
    &key=AIzaSyAYis0uGB6CuPlgdY23tgodeDlahmtVACo
    &channelId=UC_OaSsAydgSIjUtjYn9qLog

    https://www.googleapis.com/youtube/v3/search?part=snippet&order=date&maxResults=20&key=AIzaSyDWg9KAPB1QFIWFMKIix7jyYt2DayNtaaQ&channelId=UC_OaSsAydgSIjUtjYn9qLog

    * */

    @GET("search")
    Call<Resultado> recuperarVideos(
            @Query("part") String part,
            @Query("order") String order,
            @Query("maxResults") String maxResults,
            @Query("key") String key,
            @Query("channelId") String channelId
            );
}
