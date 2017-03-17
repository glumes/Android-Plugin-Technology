package com.glumes.load_plugin_resource;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.RawRes;
import android.support.annotation.StringRes;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;

import org.w3c.dom.Text;

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
        createPluginResource();
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
     * 返回插件资源的 Id
     * @param resName 资源的名称
     * @param resType 资源的类型
     * @return
     */
    public int getResourceId(String resName,String resType){
       return mPluginResource.getIdentifier(resName,resType,mPluginPackageName);
    }

    /**
     * 加载插件资源的颜色
     * @param id 对应于插件包中的 id
     * @return
     */
    public int getPluginColor(@ColorRes int id){
        return mPluginResource.getColor(id);
    }


    /**
     * 加载插件资源的颜色列表
     * @param id 对应于插件包中的 id
     * @return
     */
    public ColorStateList getPluginColorStateList(@ColorRes int id){
        return mPluginResource.getColorStateList(id);
    }


    /**
     * 加载插件资源的尺寸
     * @param id 对应于插件包中的 id
     * @return
     */
    public float getPluginDimension(@DimenRes int id){
        return mPluginResource.getDimension(id);
    }

    /**
     * 加载插件资源的 Drawable
     * @param id 对应于插件包中的 id
     * @return
     */
    public Drawable getPluginDrawable(@DrawableRes int id){
        return mPluginResource.getDrawable(id);
    }

    public int[] getPluginIntArray(@ArrayRes int id){
        return mPluginResource.getIntArray(id);
    }

    public String[] getPluginStringArray(@ArrayRes int id){
        return mPluginResource.getStringArray(id);
    }


    /**
     * 加载插件中的布局，并且根据布局返回 View
     * @param id
     * @return
     */
    public View getPluginLayoutView(@LayoutRes int id){
        XmlResourceParser xmlResourceParser = mPluginResource.getLayout(id);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(xmlResourceParser,null);
    }


    public Movie getPluginMovie(@RawRes int id){
        return mPluginResource.getMovie(id);
    }

    public String getPluginString(@StringRes int id){
        return mPluginResource.getString(id);
    }


    public CharSequence getPluginCharSequence(@StringRes int id){
        return mPluginResource.getText(id);
    }



}
