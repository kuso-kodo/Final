package com.name1e5s.toktik.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetFeedInterface {
    @GET(Config.API_URL)
    Call<List<Feed>> getFeed();
}
