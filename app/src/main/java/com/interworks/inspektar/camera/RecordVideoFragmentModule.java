package com.interworks.inspektar.camera;

import android.hardware.Camera;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.interworks.inspektar.R;
import com.interworks.inspektar.annotations.adapter.KeywordsAdapter;
import com.interworks.inspektar.annotations.view.AnnotationGridDialog;
import com.interworks.inspektar.annotations.view.KeywordItemDecoration;
import com.interworks.inspektar.annotations.view.KeywordsDialog;
import com.interworks.inspektar.base.ViewModelFactory;
import com.interworks.inspektar.camera.utils.AutoFitTextureView;
import com.interworks.inspektar.camera.utils.CameraManager;
import com.interworks.inspektar.camera.utils.CameraService;
import com.interworks.inspektar.camera.utils.CameraServiceApi2;
import com.interworks.inspektar.camera.utils.OrientationManager;
import com.interworks.inspektar.camera.view.CameraActivity;
import com.interworks.inspektar.camera.view.CameraPreview;
import com.interworks.inspektar.camera.view.SaveVideoDialog;
import com.interworks.inspektar.di.modules.DomainModule;
import com.interworks.inspektar.di.scopes.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import mk.com.interworks.domain.model.VideoEntity;

@Module(includes = {DomainModule.class})
public class RecordVideoFragmentModule {

    @Provides
    RecyclerView.LayoutManager providesLayoutManager(CameraActivity c){
        GridLayoutManager manager = new GridLayoutManager(c,3);
        return new GridLayoutManager(c, 3);
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
    KeywordsDialog providesKeywordsDialog(CameraActivity c, RecyclerView.LayoutManager layoutManager, KeywordsAdapter adapter, KeywordItemDecoration decoration){
        return new KeywordsDialog(c, adapter,layoutManager,decoration);
    }

    @Provides
    KeywordItemDecoration providesKeywordItemDecoration(CameraActivity c){
        return new KeywordItemDecoration((int) c.getResources().getDimension(R.dimen.material_component_cards_action_button_row_padding));
    }


    @Provides
    SaveVideoDialog providesSaveVideoDialog(CameraActivity c){
        return new SaveVideoDialog(c);
    }


    @Provides
    CameraContract providesCameraService(CameraActivity act, OrientationManager orientationManager){
//        CameraService service = new CameraService(act);
//        service.setup(cam, camPreview, orientationManager);
//        return service;
        return new CameraServiceApi2<>(act);
    }
}
