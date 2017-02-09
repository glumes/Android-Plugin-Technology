package com.glumes.load_plugin_resource;

import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.io.File;
import java.lang.reflect.Method;

import timber.log.Timber;

/**
 * Created by zhaoying on 2017/2/9.
 */

public class PluginResources extends Resources {


    /**
     * @param assets
     * @param metrics
     * @param config
     * @deprecated
     */
    public PluginResources(AssetManager assets, DisplayMetrics metrics, Configuration config) {
        super(assets, metrics, config);
    }

    // 加载插件的 apk 来返回插件的 Resources
    public static PluginResources getPluginResources(Resources resources,AssetManager assetManager){
        return new PluginResources(assetManager,resources.getDisplayMetrics(),resources.getConfiguration());
    }


    public static AssetManager getPluginAssetManager(String apkPath){
        try {
            Class<?> forName = Class.forName("android.content.res.AssetManager");
//
            Method addAssetPathMehtod = forName.getDeclaredMethod("addAssetPath",String.class);
//
            AssetManager assetManager = AssetManager.class.newInstance();

            addAssetPathMehtod.invoke(assetManager,apkPath);

//            AssetManager.class.getDeclaredMethod("addAssetPath", String.class).invoke(
//                    assetManager, apkPath);

            return assetManager ;

        } catch (Exception e) {
            e.printStackTrace();
            Timber.e("error msg is %s",e.getMessage());
        }
        return null ;
    }

}
