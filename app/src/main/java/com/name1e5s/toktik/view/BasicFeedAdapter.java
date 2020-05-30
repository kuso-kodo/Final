package com.name1e5s.toktik.view;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.name1e5s.toktik.R;
import com.name1e5s.toktik.VideoActivity;
import com.name1e5s.toktik.model.Feed;

import java.util.ArrayList;
import java.util.List;

public class BasicFeedAdapter extends RecyclerView.Adapter<BasicFeedAdapter.VH> {
    private static final int W = 875;
    private static final int H = 492;
    private int realW;
    private int realH;
    private Context mContext;
    private List<Feed> mList;

    public BasicFeedAdapter(Context context) {
        this.mContext = context;
        realW = context.getResources().getDisplayMetrics().widthPixels;
        realH = realW * H / W;
        this.mList = new ArrayList<>();
    }

    public void setmList(List<Feed> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.basic_feed_cell, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        if (mList.size() != 0) {
            Feed feed = mList.get(position);
            holder.descriptionTextView.setText(feed.getDescription());
            String text = "\uD83D\uDC68\u200D\uD83E\uDDB3：" + feed.getNickname() + " ♥ " + feed.getLikecount();
            holder.likeTextView.setText(text);
            Glide.with(mContext).load(feed.getAvatar()).into(holder.basicImageView);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, VideoActivity.class);
                    intent.putExtra("url", feed.getFeedurl());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onViewRecycled(VH holder) {
        if (holder != null) {
            Glide.with(mContext).clear(holder.basicImageView);
        }
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class VH extends RecyclerView.ViewHolder {

        public TextView descriptionTextView;
        public TextView likeTextView;
        public ImageView basicImageView;

        public VH(View itemView) {
            super(itemView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            basicImageView = itemView.findViewById(R.id.basicImage);
            likeTextView = itemView.findViewById(R.id.likeTextView);
            itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, realH));
        }
    }
}
