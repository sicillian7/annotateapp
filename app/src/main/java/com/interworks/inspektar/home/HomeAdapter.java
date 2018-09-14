package com.interworks.inspektar.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.interworks.inspektar.R;
import com.interworks.inspektar.utils.ImageUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import static java.security.AccessController.getContext;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {


    private List<VideoListTestData> mTestData;


    public HomeAdapter(List<VideoListTestData> videoList)
    {
        mTestData = videoList;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View view = inflater.inflate(R.layout.item_video, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {

        Context context = holder.itemView.getContext();

        VideoListTestData testData = mTestData.get(position);


        ViewHolder viewHolder = holder;
        viewHolder.setIsRecyclable(false);
        viewHolder.tvVideoTitle.setText(testData.getTitle());
        viewHolder.tvVideoDate.setText(testData.getDate());
        viewHolder.tvVideoHour.setText(testData.getHour());
        viewHolder.ivVideoImage.setImageResource(testData.getImage());
        viewHolder.ivPlayBtn.setImageResource(R.drawable.ic_btn_rec);


//        ImageView ivVideo = holder.ivVideoImage;
//        ivVideo.setImageResource(testData.getImage());
//
//        /*Picasso.get()
//                .load(testData.getImage())
//                .transform(new ImageUtils(150, 0))
//                .into(ivVideo);*/
//
//
//        TextView tvTitle = holder.tvVideoTitle;
//        tvTitle.setText(testData.getTitle());
//
//        TextView tvDate = holder.tvVideoDate;
//        tvDate.setText(testData.getDate());
//
//        TextView tvHour = holder.tvVideoHour;
//        tvHour.setText(testData.getHour());
//
//        ImageView ivPlay = holder.ivPlayBtn;
//        ivPlay.setImageResource(R.drawable.ic_btn_rec);

    }

    @Override
    public int getItemCount() {
        return mTestData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivVideoImage;
        public ImageView ivPlayBtn;
        public TextView tvVideoTitle;
        public TextView tvVideoDate;
        public TextView tvVideoHour;

        public ViewHolder(View itemView) {
            super(itemView);

            ivPlayBtn = itemView.findViewById(R.id.ivPlayBtn);
            ivVideoImage = itemView.findViewById(R.id.ivVideoImage);
            tvVideoTitle = itemView.findViewById(R.id.tvVideoTitle);
            tvVideoDate = itemView.findViewById(R.id.tvVideoDate);
            tvVideoHour = itemView.findViewById(R.id.tvVideoHour);


        }
    }
}
