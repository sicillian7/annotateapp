package com.interworks.inspektar.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.interworks.inspektar.InspektARApplication;
import com.interworks.inspektar.di.components.ApplicationComponent;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {

    private ProgressDialog mDialog;
    protected Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        unbinder = ButterKnife.bind(this);
        onViewReady(savedInstanceState, getIntent());
        this.getApplicationComponent().inject(this);
    }


    protected void addFragment(int containerViewId, Fragment fragment) {
        final FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commit();
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((InspektARApplication) getApplication()).getApplicationComponent();
    }

    protected abstract int getContentView();

    @CallSuper
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        // to be used by child classes
        resolveDaggerDependencies();
    }

    protected void resolveDaggerDependencies() {
    }

    protected void showDialog(String message, boolean isVisible){
        if(isVisible){
            if(mDialog == null){
                mDialog = new ProgressDialog(this);
                mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mDialog.setCancelable(true);
            }
            mDialog.setMessage(message);
            mDialog.show();
        }else{
            if(mDialog != null && mDialog.isShowing()){
                mDialog.dismiss();
            }
        }
    }

//    protected ActivityModule getActivityModule() {
//        return new ActivityModule(this);
//    }
}
