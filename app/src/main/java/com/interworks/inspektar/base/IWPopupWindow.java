package com.interworks.inspektar.base;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class IWPopupWindow extends PopupWindow {

    protected Context context;
    protected Unbinder unbinder;

    public IWPopupWindow(Context context, int resourceId) {

        super(context);
        this.setContext(context);
        initView(resourceId);
        setListeners();
        this.setFocusable(true);
    }

    protected abstract void setListeners();

    protected abstract void getUIRefs();

    protected void initView(int resourceId) {

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(resourceId, null);
        unbinder = ButterKnife.bind(this,contentView);
        this.setContentView(contentView);
    }

    public void show(View parent, float posX, float posY) {

        setWindowLayoutMode(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        showAtLocation(parent, Gravity.NO_GRAVITY, (int) posX, (int) posY);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void unbindViews(){
        unbinder.unbind();
    }
}