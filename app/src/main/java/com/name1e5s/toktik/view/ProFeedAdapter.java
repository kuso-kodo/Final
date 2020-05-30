package com.name1e5s.toktik.view;


import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.name1e5s.toktik.R;
import com.name1e5s.toktik.model.Feed;

import java.util.ArrayList;
import java.util.List;

public class ProFeedAdapter extends RecyclerView.Adapter<ProFeedAdapter.VH> implements ListPreloader.PreloadModelProvider<Feed> {
    private Context mContext;
    private List<Feed> mList;
    private RequestOptions requestOptions;

    public ProFeedAdapter(Context context) {
        this.mContext = context;
        this.mList = new ArrayList<>();
        this.requestOptions = new RequestOptions().frame(1);
    }

    public void setmList(List<Feed> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pro_feed_cell, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        if (mList.size() != 0) {
            Feed feed = mList.get(position);
            holder.descriptionTextView.setText(feed.getDescription());
            String text = "\uD83D\uDC68\u200D\uD83E\uDDB3：" + feed.getNickname() + " ♥ " + feed.getLikecount();
            holder.likeTextView.setText(text);
            Glide.with(mContext).load("empty")
                    .thumbnail(Glide.with(mContext)
                            .asDrawable()
                            .load(feed.getFeedurl())
                            .apply(requestOptions))
                    .into(holder.proImageView);

            holder.videoView.setVideoPath(feed.getFeedurl());
            holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            holder.proImageView.setVisibility(View.INVISIBLE);
                        }
                    }, 400);
                }
            });
            holder.videoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.pause) {
                        holder.pause = false;
                        holder.videoView.start();
                    } else {
                        holder.pause = true;
                        holder.videoView.pause();
                    }
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull VH holder) {
        super.onViewAttachedToWindow(holder);
        holder.videoView.seekTo(1);
        holder.videoView.start();
        holder.pause = false;
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull VH holder) {
        super.onViewDetachedFromWindow(holder);
        holder.proImageView.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @NonNull
    @Override
    public List<Feed> getPreloadItems(int position) {
        return mList.subList(position, position + 1);
    }

    @Nullable
    @Override
    public RequestBuilder getPreloadRequestBuilder(Feed item) {
        return Glide.with(mContext).load("empty")
                .thumbnail(Glide.with(mContext)
                        .asDrawable()
                        .load(item.getFeedurl())
                        .apply(requestOptions));
    }

    static class VH extends RecyclerView.ViewHolder {

        TextView descriptionTextView;
        TextView likeTextView;
        ImageView proImageView;
        VideoView videoView;
        Boolean pause;

        VH(View itemView) {
            super(itemView);
            descriptionTextView = itemView.findViewById(R.id.proDescriptionTextView);
            proImageView = itemView.findViewById(R.id.proImage);
            likeTextView = itemView.findViewById(R.id.proLikeTextView);
            videoView = itemView.findViewById(R.id.proVideo);
            itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }
}
