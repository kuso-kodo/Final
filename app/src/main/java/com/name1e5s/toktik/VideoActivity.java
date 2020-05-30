package com.name1e5s.toktik;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class VideoActivity extends AppCompatActivity {
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        videoView = findViewById(R.id.video_view);
        setVideoView();
    }

    private void setVideoView() {
        Intent intent = getIntent();
        String text = intent.getStringExtra("url");

        videoView.setVideoPath(text);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                finish();
            }
        });
        videoView.setOnClickListener(new View.OnClickListener() {
            private boolean pause = false;

            @Override
            public void onClick(View v) {
                if (pause) {
                    pause = false;
                    videoView.start();
                } else {
                    pause = true;
                    videoView.pause();
                }
            }
        });
        videoView.start();
    }
}
