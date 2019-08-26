package com.hristovski.inspektar.annotations.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.hristovski.inspektar.R;
import com.hristovski.inspektar.annotations.adapter.KeywordsAdapter;
import com.hristovski.inspektar.annotations.viewModel.AnnotationViewModel;
import com.hristovski.inspektar.databinding.AnnotationViewSwitcherBinding;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import mk.com.interworks.domain.model.KeywordEntity;

public class KeywordsDialog extends Dialog {

    public interface OnActionListener{
        void onKeywordSelected(AnnotationViewModel vm);
    }

    KeywordItemDecoration mItemDecoration;
    AnnotationViewSwitcherBinding binding;
    KeywordsAdapter mKeywordsAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    private OnActionListener mListener;

    private float posX, posY;
    private AnnotationViewModel mViewModel;
    private Context mContext;

    public KeywordsDialog(@NonNull Context context, KeywordsAdapter adapter, RecyclerView.LayoutManager layoutManager, KeywordItemDecoration decoration) {
        super(context);
        mContext = context;
        this.mLayoutManager = layoutManager;
        this.mKeywordsAdapter = adapter;
        mItemDecoration = decoration;
        init();
    }

    public KeywordsDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected KeywordsDialog(@NonNull Context context, boolean cancelable, @Nullable DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private void init(){
        binding = (AnnotationViewSwitcherBinding)DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.annotation_view_switcher, null, false);
        setContentView(binding.getRoot());
        mKeywordsAdapter.setActionListener(new KeywordsDialog.OnKeywordsPopUpActionListener(this));
        binding.keywordsGrid.setLayoutManager(mLayoutManager);
        binding.keywordsGrid.addItemDecoration(mItemDecoration);
        binding.keywordsGrid.setAdapter(mKeywordsAdapter);
    }

    public void onKeywordSelected(KeywordEntity item){
        if (mListener != null) {
            mViewModel.setKeyword(item);
            mListener.onKeywordSelected(mViewModel);
        }
    }

    public void notifyDatasetChanged(List<KeywordEntity> items){
        mKeywordsAdapter.newDataSetChanged(items);
    }

    public void setViewModel(AnnotationViewModel mViewModel) {
        this.mViewModel = mViewModel;
    }

    public void setListener(OnActionListener mListener) {
        this.mListener = mListener;
    }

    public static class OnKeywordsPopUpActionListener implements KeywordsAdapter.ActionListener{

        private WeakReference<KeywordsDialog> weakDialog;

        public OnKeywordsPopUpActionListener(KeywordsDialog d) {
            weakDialog = new WeakReference<>(d);
        }

        @Override
        public void onItemSelect(KeywordEntity item) {
            KeywordsDialog d = weakDialog.get();
            if (d != null) {
                d.onKeywordSelected(item);
            }
        }
    }
}
