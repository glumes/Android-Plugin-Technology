package com.glumes.load_plugin_resource;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

import java.io.File;
import java.lang.reflect.Method;

import timber.log.Timber;

/**
 * Created by zhaoying on 2017/2/9.
 */


/**
 * 插件资源管理器
 */
public class PluginResourceManager {


    private Context mContext ;

    private String mPluginPath  ;

    private String mPluginPackageName ;

    private Resources mPluginResource ;

    public PluginResourceManager(Context mContext, String pluginPath) {
        this.mPluginPath = pluginPath;
        this.mContext = mContext;
    }

    /**
     * 构造插件资源 Resource
     */
    public void createPluginResource(){

        try {
            // 得到插件包的包名
            PackageManager packageManager = mContext.getPackageManager() ;
            PackageInfo info = packageManager.getPackageArchiveInfo(mPluginPath,PackageManager.GET_ACTIVITIES);
            mPluginPackageName = info.packageName ;
            Timber.d("package name is %s",mPluginPackageName);
            // 构造 AssetManager 类
            AssetManager assetManager = AssetManager.class.newInstance();
            // 反射调用 addAssetPath 方法
            Method addAssetPathMehtod = assetManager.getClass().getDeclaredMethod("addAssetPath",String.class);
            addAssetPathMehtod.invoke(assetManager,mPluginPath);
            // 构造插件的 Resource
            Resources superRes = mContext.getResources();
            mPluginResource = new Resources(assetManager,superRes.getDisplayMetrics(),superRes.getConfiguration());
        }catch (Exception e){
            e.printStackTrace();
        }
    }




    /**
     * 通过反射创建资源管理器
     * @param apkPath apk 文件路径
     * @return
     */
    public static AssetManager getPluginAssetManager(@NonNull String apkPath){
        try {
            // 构造 AssetManager 类
            AssetManager assetManager = AssetManager.class.newInstance();

            // 反射调用 addAssetPath 方法
            Method addAssetPathMehtod = assetManager.getClass().getDeclaredMethod("addAssetPath",String.class);

            addAssetPathMehtod.invoke(assetManager,apkPath);

            return assetManager ;

        } catch (Exception e) {
            e.printStackTrace();
            Timber.e("error msg is %s",e.getMessage());
        }
        return null ;
    }


    public static
}
