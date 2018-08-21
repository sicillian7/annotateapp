package com.interworks.inspektar.di.components;


import android.content.Context;

import com.interworks.inspektar.base.BaseActivity;
import com.interworks.inspektar.di.modules.ApplicationModule;
import com.interworks.inspektar.di.modules.PersistenceModule;
import com.interworks.inspektar.di.scopes.ApplicationScope;

import dagger.Component;
import mk.com.interworks.domain.executor.PostExecutionThread;
import mk.com.interworks.domain.executor.ThreadExecutor;

@ApplicationScope
@Component(modules = {ApplicationModule.class, PersistenceModule.class})
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);
    Context context();
    ThreadExecutor threadExecutor();
    PostExecutionThread postExecutionThread();
}
