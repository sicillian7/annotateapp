package com.interworks.inspektar.annotations.view;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import com.interworks.inspektar.R;
import com.interworks.inspektar.annotations.adapter.KeywordsAdapter;
import com.interworks.inspektar.annotations.viewModel.AnnotationViewModel;
import com.interworks.inspektar.base.IWPopupWindow;
import com.interworks.inspektar.base.ViewModelFactory;
import com.interworks.inspektar.databinding.AnnotationViewSwitcherBinding;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import mk.com.interworks.domain.model.AnnotationEntity;
import mk.com.interworks.domain.model.KeywordEntity;

@Deprecated
public class AnnotationGridDialog extends IWPopupWindow implements
        AdapterView.OnItemClickListener, View.OnClickListener {

    public interface OnActionListener{
        void onKeywordSelected(AnnotationViewModel vm);
    }

    AnnotationViewSwitcherBinding binding;
    KeywordsAdapter mKeywordsAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    private OnActionListener mListener;

    private float posX, posY;
    private View parent;
    private AnnotationViewModel mViewModel;

    public AnnotationGridDialog(View parent, KeywordsAdapter adapter, RecyclerView.LayoutManager layoutManager) {
        super(parent.getContext(), R.layout.annotation_view_switcher);
        this.parent = parent;
        this.mLayoutManager = layoutManager;
        this.mKeywordsAdapter = adapter;
        initView();
    }

    protected void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
          //  binding = AnnotationViewSwitcherBinding.inflate(LayoutInflater.from(getContext()), R.layout.annotation_view_switcher, null, false);
            Resources res = context.getResources();
            int width = res.getDimensionPixelSize(R.dimen.grid_col_width)
                    * res.getInteger(R.integer.num_of_columns)
                    + res.getDimensionPixelSize(R.dimen.grid_margin) + 150;
            setWidth(width);
            //setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
            setHeight(200);
            setBackgroundDrawable(new ColorDrawable(Color.BLACK));
            mKeywordsAdapter.setActionListener(new OnKeywordsPopUpActionListener(this));
            binding.keywordsGrid.setLayoutManager(mLayoutManager);
            binding.keywordsGrid.addItemDecoration(new KeywordItemDecoration(10));
            binding.keywordsGrid.setAdapter(mKeywordsAdapter);
        }


        //VIEW MODEL SHOULD FETCH KEYWORDS FOR FAVORITE AND PASS TO ADAPTER
    }

    @Override
    protected void getUIRefs() {

    }


    @Override
    protected void setListeners() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void dismiss() {
        //unbindViews();
        super.dismiss();
    }

    @Override
    public void show(View parent, float posX, float posY) {
        showAtLocation(parent, Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
        setPosX(posX);
        setPosY(posY);
    }

    public void show(float posX, float posY) {
        showAtLocation(parent, Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
        setPosX(posX);
        setPosY(posY);
    }


    public void onKeywordSelected(KeywordEntity item){
        if (mListener != null) {
            mViewModel.getEntity().setKeywordId(item.getId());
            mListener.onKeywordSelected(mViewModel);
        }
    }

    public void notifyDatasetChanged(List<KeywordEntity> items){
        mKeywordsAdapter.newDataSetChanged(items);
    }

    public AnnotationViewModel getViewModel() {
        return mViewModel;
    }

    public void setViewModel(AnnotationViewModel mViewModel) {
        this.mViewModel = mViewModel;
    }

    public OnActionListener getListener() {
        return mListener;
    }

    public void setListener(OnActionListener mListener) {
        this.mListener = mListener;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public static class OnKeywordsPopUpActionListener implements KeywordsAdapter.ActionListener{

        private WeakReference<AnnotationGridDialog> weakDialog;

        public OnKeywordsPopUpActionListener(AnnotationGridDialog d) {
            weakDialog = new WeakReference<>(d);
        }

        @Override
        public void onItemSelect(KeywordEntity item) {
            AnnotationGridDialog d = weakDialog.get();
            if (d != null) {
                d.onKeywordSelected(item);
            }
        }
    }

}
