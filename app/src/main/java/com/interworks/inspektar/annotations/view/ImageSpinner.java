package com.interworks.inspektar.annotations.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.interworks.inspektar.R;
import com.interworks.inspektar.base.DisplayableItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ImageSpinner extends LinearLayout {

    @BindView(R.id.imgIcon)
    ImageView mImage;
    @BindView(R.id.txtMainItem)
    TextView mMainItem;
    @BindView(R.id.txtSubItem)
    TextView mSubItem;
    @BindView(R.id.btnRadio)
    CheckBox btnRadio;
    protected DisplayableItem mModel;
    Unbinder unbinder;

    public ImageSpinner(Context context) {
        super(context);
        initView();
    }

    public ImageSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ImageSpinner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    protected void initView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.img_keyword, this,
                true);
        unbinder = ButterKnife.bind(v,this);
    }

    public DisplayableItem getModel() {
        return mModel;
    }

    public void setModel(DisplayableItem model) {
        if (mModel != null && mModel.equals(model))
            return;

        this.mModel = model;
        visualizeModel();
    }

    private void visualizeModel() {
        mMainItem.setText(mModel.getMainItem());
        mSubItem.setText(mModel.getSubItem());

        if(mModel.getSubItem()== null || mModel.getSubItem().equals(""))
            mSubItem.setVisibility(View.GONE);

        try {
            if (mModel.getImageResourceId() == 0){
                mImage.setImageDrawable(ContextCompat.getDrawable(getContext(), mModel.getImageResourceId()));
            }else
                mImage.setImageResource(mModel.getImageResourceId());

            mImage.setVisibility(VISIBLE);
        } catch (NullPointerException e) {
            mImage.setVisibility(INVISIBLE);
        }
    }

    public void setEditMode(boolean isInEditMode){
        if(isInEditMode){
            btnRadio.setVisibility(View.VISIBLE);
        }else {
            btnRadio.setVisibility(View.GONE);
        }
    }

}

