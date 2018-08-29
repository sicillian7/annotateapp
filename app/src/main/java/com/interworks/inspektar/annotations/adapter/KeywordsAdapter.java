package com.interworks.inspektar.annotations.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.interworks.inspektar.R;
import com.interworks.inspektar.base.DisplayableItem;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mk.com.interworks.domain.model.KeywordEntity;

public class KeywordsAdapter extends RecyclerView.Adapter<KeywordsAdapter.ViewHolder>  {

    public interface ActionListener{
        void onItemSelect(KeywordEntity item);
    }

    private List<KeywordEntity> items = new ArrayList<>();
    private ActionListener mListener;

    @NonNull
    @Override
    public KeywordsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_with_text, parent, false);
        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull KeywordsAdapter.ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        KeywordEntity kw = items.get(position);
        holder.tv.setText(kw.getName());
        holder.image.setImageResource(context.getResources().getIdentifier(kw.getResPath(),"drawable", context.getPackageName()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void newDataSetChanged(List<KeywordEntity> newItems){
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    public void setActionListener(ActionListener l){
        mListener = l;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.text)
        TextView tv;

        private WeakReference<KeywordsAdapter> weakAdapter;

        public ViewHolder(View itemView, KeywordsAdapter l) {
            super(itemView);
            ButterKnife.bind(itemView);
            weakAdapter = new WeakReference<>(l);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            KeywordsAdapter adapter = weakAdapter.get();
            if (adapter != null && adapter.mListener != null) {
                adapter.mListener.onItemSelect(adapter.items.get(this.getAdapterPosition()));
            }
        }
    }
}
