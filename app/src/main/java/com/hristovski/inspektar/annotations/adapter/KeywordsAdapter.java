package com.hristovski.inspektar.annotations.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hristovski.inspektar.R;
import com.hristovski.inspektar.databinding.ItemImageWithTextBinding;

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
    private LayoutInflater inflater;

    @NonNull
    @Override
    public KeywordsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }

        ItemImageWithTextBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_image_with_text, parent, false);
        return new ViewHolder(binding, this);
    }

    @Override
    public void onBindViewHolder(@NonNull KeywordsAdapter.ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        KeywordEntity kw = items.get(position);
        holder.binding.text.setText(kw.getName());
        holder.binding.image.setImageResource(context.getResources().getIdentifier(kw.getResPath(),"drawable", context.getPackageName()));
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

        private final ItemImageWithTextBinding binding;

        private WeakReference<KeywordsAdapter> weakAdapter;

        public ViewHolder(ItemImageWithTextBinding binding, KeywordsAdapter l) {
            super(binding.getRoot());
            this.binding = binding;
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
