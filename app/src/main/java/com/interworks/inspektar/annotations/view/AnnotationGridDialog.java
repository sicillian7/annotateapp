package com.interworks.inspektar.annotations.view;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import com.interworks.inspektar.R;
import com.interworks.inspektar.annotations.adapter.KeywordsAdapter;
import com.interworks.inspektar.base.IWPopupWindow;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import butterknife.BindView;
import mk.com.interworks.domain.model.KeywordEntity;

public abstract class AnnotationGridDialog extends IWPopupWindow implements
        AdapterView.OnItemClickListener, View.OnClickListener {

    @BindView(R.id.keywordsGrid)
    private RecyclerView keywordsGridView;

    @Inject
    KeywordsAdapter mKeywordsAdapter;

    private float posX, posY;
    private View parent;

    public AnnotationGridDialog(View parent) {
        super(parent.getContext(), R.layout.annotation_view_switcher);
        this.parent = parent;
        initView();
    }

    protected void initView() {

        Resources res = context.getResources();
        int width = res.getDimensionPixelSize(R.dimen.grid_col_width)
                * res.getInteger(R.integer.num_of_columns)
                + res.getDimensionPixelSize(R.dimen.grid_margin) + 150;
        setWidth(width);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        mKeywordsAdapter.setActionListener(new OnKeywordsPopUpActionListener(this));

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
