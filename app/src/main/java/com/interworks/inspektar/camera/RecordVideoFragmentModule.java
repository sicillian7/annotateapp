package com.interworks.inspektar.camera;

import android.hardware.Camera;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.interworks.inspektar.R;
import com.interworks.inspektar.annotations.adapter.KeywordsAdapter;
import com.interworks.inspektar.annotations.view.AnnotationGridDialog;
import com.interworks.inspektar.base.ViewModelFactory;
import com.interworks.inspektar.camera.utils.AutoFitTextureView;
import com.interworks.inspektar.camera.utils.CameraManager;
import com.interworks.inspektar.camera.utils.CameraService;
import com.interworks.inspektar.camera.utils.CameraServiceApi2;
import com.interworks.inspektar.camera.utils.OrientationManager;
import com.interworks.inspektar.camera.view.CameraActivity;
import com.interworks.inspektar.camera.view.CameraPreview;
import com.interworks.inspektar.di.modules.DomainModule;
import com.interworks.inspektar.di.scopes.PerActivity;

import dagger.Module;
import dagger.Provides;

@Module(includes = {DomainModule.class})
public class RecordVideoFragmentModule {

    @Provides
    RecyclerView.LayoutManager providesLayoutManager(CameraActivity c){
        return new LinearLayoutManager(c);
    }

    @Provides
    Camera providesCamera(){
        return Camera.open();
    }

    @Provides
    CameraPreview providesCameraPreview(CameraActivity act, Camera camera){
        return new CameraPreview(act, camera);
    }

    @Provides
    OrientationManager providesOrientationMannager(CameraActivity act){
        return  new OrientationManager(act);
    }

    @Provides
    KeywordsAdapter providesKeywordsAdapter(){
        return new KeywordsAdapter();
    }

    @Provides
    AnnotationGridDialog providesKeywordsDialog(CameraActivity c, RecyclerView.LayoutManager layoutManager, KeywordsAdapter adapter){
        return new AnnotationGridDialog(c.findViewById(R.id.container ),adapter, layoutManager);
    }

    @Provides
    CameraContract providesCameraService(CameraActivity act, Camera cam, CameraPreview camPreview, OrientationManager orientationManager){
//        CameraService service = new CameraService(act);
//        service.setup(cam, camPreview, orientationManager);
//        return service;
        return new CameraServiceApi2<>(act);
    }
}
