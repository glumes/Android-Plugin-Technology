package com.glumes.load_plugin_resource;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.File;
import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dalvik.system.DexClassLoader;
import timber.log.Timber;

public class LoadPluginResourceActivity extends AppCompatActivity {


    @BindView(R.id.img)
    ImageView mImage;
    @BindView(R.id.loadPluginResource)
    Button mLoadPluginResource;
    @BindView(R.id.activity_main)
    RelativeLayout mActivityMain;

    String apkPath = Environment.getExternalStorageDirectory() + File.separator + "load-plugin-class-debug.apk";

    String packageName = "com.glumes.load_plugin_class";

    String resourceName = "abc_btn_check_material";

    Context mContext ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this ;
    }

    @OnClick({R.id.img, R.id.loadPluginResource})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img:

                break;
            case R.id.loadPluginResource:
                try {
                    AssetManager assetManager = PluginResources.getPluginAssetManager(apkPath);
                    Resources resources = PluginResources.getPluginResources(mContext.getResources(),assetManager);
                    // 加载了一个 ClassLoader 去反射
                    DexClassLoader dexClassLoader = new DexClassLoader(apkPath,this.getDir("Dex",MODE_PRIVATE).getAbsolutePath(),
                            null,getClassLoader());


                    Class<?> loadClass = dexClassLoader.loadClass(packageName + ".R$drawable");
                    Field[] declaredFields = loadClass.getDeclaredFields();

                    for (Field field : declaredFields){
                        if (field.getName().equals(resourceName)){
                            int drawableId = field.getInt(R.drawable.class);
                            // 通过 id 加载插件的内容
                            Drawable drawable = resources.getDrawable(drawableId);

                            mImage.setImageDrawable(drawable);

                            Timber.e("id is %d",drawableId);
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    Timber.e("error msg is %s",e.getMessage());
                }
                break;
        }
    }
}
