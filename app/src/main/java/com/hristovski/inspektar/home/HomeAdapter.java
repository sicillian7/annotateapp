package com.hristovski.inspektar.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hristovski.inspektar.R;
import com.hristovski.inspektar.utils.ImageUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import static java.security.AccessController.getContext;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperAdapter {


    private List<VideoListTestData> mTestData;
    private Context context;


    public HomeAdapter(List<VideoListTestData> videoList) {
        mTestData = videoList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view;

        switch (viewType) {
            case R.layout.item_video:
                view = inflater.inflate(R.layout.item_video, parent, false);
                return new VideosHolder(view);
            case R.layout.item_folder:
                view = inflater.inflate(R.layout.item_folder, parent, false);
                return new FolderHolder(view);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        VideoListTestData testData = mTestData.get(position);

        if (testData != null) {

            if (holder instanceof VideosHolder) {
                ((VideosHolder) holder).tvVideoTitle.setText(testData.getTitle());
                ((VideosHolder) holder).tvVideoDate.setText(testData.getDate());
                ((VideosHolder) holder).tvVideoHour.setText(testData.getHour());
                ((VideosHolder) holder).ivVideoImage.setImageResource(testData.getImage());
                ((VideosHolder) holder).ivPlayBtn.setImageResource(R.drawable.ic_btn_rec);
            } else if (holder instanceof FolderHolder) {
                ((FolderHolder) holder).ivFolder.setImageResource(R.drawable.ic_folder_black_24dp);
                ((FolderHolder) holder).tvFolderTitle.setText(testData.getTitle());
                ((FolderHolder) holder).ivArrow.setImageResource(R.drawable.ic_keyboard_arrow_right_black_24dp);
            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (mTestData != null) {
            VideoListTestData object = mTestData.get(position);
            if (object != null) {
                if (object.getFolder()) {
                    return R.layout.item_folder;
                    //return 1;
                } else
                    return R.layout.item_video;
                //return 2;
            }
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        if (mTestData == null)
            return 0;
        return mTestData.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {

        VideoListTestData targetUser = mTestData.get(fromPosition);
        VideoListTestData user = new VideoListTestData(targetUser);
        mTestData.remove(fromPosition);
        mTestData.add(toPosition, user);
        notifyItemMoved(fromPosition, toPosition);


        /*VideoListTestData prev = mTestData.remove(fromPosition);
        mTestData.add(toPosition > fromPosition ? toPosition - 1 : toPosition, prev);
        notifyItemMoved(fromPosition, toPosition);*/
    }

    @Override
    public void onItemDismiss(int position) {
        /*mTestData.remove(position);
        notifyItemRemoved(position);*/
    }

    public class VideosHolder extends RecyclerView.ViewHolder {

        public ImageView ivVideoImage;
        public ImageView ivPlayBtn;
        public TextView tvVideoTitle;
        public TextView tvVideoDate;
        public TextView tvVideoHour;

        public VideosHolder(View itemView) {
            super(itemView);

            ivPlayBtn = itemView.findViewById(R.id.ivPlayBtn);
            ivVideoImage = itemView.findViewById(R.id.ivVideoImage);
            tvVideoTitle = itemView.findViewById(R.id.tvVideoTitle);
            tvVideoDate = itemView.findViewById(R.id.tvVideoDate);
            tvVideoHour = itemView.findViewById(R.id.tvVideoHour);
        }
    }

    public class FolderHolder extends RecyclerView.ViewHolder {

        public ImageView ivFolder;
        public ImageView ivArrow;
        public TextView tvFolderTitle;

        public FolderHolder(View itemView) {
            super(itemView);

            ivFolder = itemView.findViewById(R.id.ivFolder);
            ivArrow = itemView.findViewById(R.id.ivArrow);
            tvFolderTitle = itemView.findViewById(R.id.tvFolderTitle);
        }
    }
}
