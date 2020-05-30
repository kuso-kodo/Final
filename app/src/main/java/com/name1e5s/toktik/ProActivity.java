package com.name1e5s.toktik;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import com.bumptech.glide.util.ViewPreloadSizeProvider;
import com.name1e5s.toktik.model.Config;
import com.name1e5s.toktik.model.Feed;
import com.name1e5s.toktik.model.GetFeedInterface;
import com.name1e5s.toktik.view.ProFeedAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProFeedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro);
        setView();
        loadData();
    }

    private void setView() {
        recyclerView = findViewById(R.id.proRecyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(recyclerView);
        adapter = new ProFeedAdapter(this);
        ListPreloader.PreloadSizeProvider<Feed> sizeProvider = new ViewPreloadSizeProvider(recyclerView);
        RecyclerViewPreloader<Feed> preloader = new RecyclerViewPreloader<Feed>(Glide.with(this), adapter, sizeProvider, 20);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(preloader);
    }

    private void loadData() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        OkHttpClient client = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        GetFeedInterface getFeedInterface = retrofit.create(GetFeedInterface.class);
        Call<List<Feed>> call = getFeedInterface.getFeed();
        call.enqueue(new Callback<List<Feed>>() {
            @Override
            public void onResponse(@NotNull Call<List<Feed>> call, @NotNull Response<List<Feed>> response) {
                List<Feed> feedList = response.body();
                if (feedList == null) {
                    return;
                }
                adapter.setmList(feedList);
            }

            @Override
            public void onFailure(@NotNull Call<List<Feed>> call, @NotNull Throwable t) {
                Log.e("Fuck", "Fuck");
            }
        });
    }
}
