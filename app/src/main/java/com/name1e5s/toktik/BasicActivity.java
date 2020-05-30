package com.name1e5s.toktik;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.name1e5s.toktik.model.Config;
import com.name1e5s.toktik.model.Feed;
import com.name1e5s.toktik.model.GetFeedInterface;
import com.name1e5s.toktik.view.BasicFeedAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BasicActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BasicFeedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        setView();
        loadData();
    }

    private void setView() {
        recyclerView = findViewById(R.id.basicRecyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        adapter = new BasicFeedAdapter(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
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
