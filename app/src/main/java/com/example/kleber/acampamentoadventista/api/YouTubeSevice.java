//package com.example.kleber.acampamentoadventista.api;
//
//import com.example.kleber.acampamentoadventista.modelos.youtube.ResultYouTubeRequest;
//
//import retrofit2.Call;
//import retrofit2.http.GET;
//import retrofit2.http.Query;
//
//public interface YouTubeSevice {
//
//    /*
//
//    https://www.googleapis.com/youtube/v3/
//    search
//    ?part=snippet
//    &order=date
//    &maxResults=20
//    &key=AIzaSyAYis0uGB6CuPlgdY23tgodeDlahmtVACo
//    &channelId=UC_OaSsAydgSIjUtjYn9qLog
//
//    https://www.googleapis.com/youtube/v3/search?part=snippet&order=date&maxResults=20&key=AIzaSyDWg9KAPB1QFIWFMKIix7jyYt2DayNtaaQ&channelId=UC_OaSsAydgSIjUtjYn9qLog
//
//
//    //PLAYLIST: Ivan saraiva
//
//    https://www.googleapis.com/youtube/v3/
//    playlistItems
//    ?part=snippet
//    &maxResults=10
//    &key=AIzaSyDWg9KAPB1QFIWFMKIix7jyYt2DayNtaaQ
//    &playlistId=PLy7ET69DnMyry95gyAnuiDMVPQ7w0xD5G
//
//    https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&order=date&maxResults=10&key=AIzaSyDWg9KAPB1QFIWFMKIix7jyYt2DayNtaaQ&playlistId=PLy7ET69DnMyry95gyAnuiDMVPQ7w0xD5G
//
//    * */
//
//    @GET("playlistItems")
//    Call<ResultYouTubeRequest> recuperarVideos(
//            @Query("part") String part,
//            @Query("order") String order,
//            @Query("maxResults") String maxResults,
//            @Query("key") String key,
//            @Query("playlistId") String playlistId
//    );
//}
