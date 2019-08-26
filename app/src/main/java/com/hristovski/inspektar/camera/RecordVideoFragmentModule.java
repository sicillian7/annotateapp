package com.hristovski.inspektar.camera;

import android.hardware.Camera;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.hristovski.inspektar.R;
import com.hristovski.inspektar.annotations.adapter.KeywordsAdapter;
import com.hristovski.inspektar.annotations.view.AnnotationGridDialog;
import com.hristovski.inspektar.annotations.view.KeywordItemDecoration;
import com.hristovski.inspektar.annotations.view.KeywordsDialog;
import com.hristovski.inspektar.base.ViewModelFactory;
import com.hristovski.inspektar.camera.utils.AutoFitTextureView;
import com.hristovski.inspektar.camera.utils.CameraManager;
import com.hristovski.inspektar.camera.utils.CameraService;
import com.hristovski.inspektar.camera.utils.CameraServiceApi2;
import com.hristovski.inspektar.camera.utils.OrientationManager;
import com.hristovski.inspektar.camera.view.CameraActivity;
import com.hristovski.inspektar.camera.view.CameraPreview;
import com.hristovski.inspektar.camera.view.SaveVideoDialog;
import com.hristovski.inspektar.di.modules.DomainModule;
import com.hristovski.inspektar.di.scopes.PerActivity;

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
