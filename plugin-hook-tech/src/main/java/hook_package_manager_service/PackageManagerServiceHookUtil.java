package hook_package_manager_service;

import android.content.Context;
import android.content.pm.PackageManager;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

import timber.log.Timber;

/**
 * Created by zhaoying on 2017/2/7.
 */

public class PackageManagerServiceHookUtil {

    public static void hookPms(Context context){

        try {
            Timber.d("start hook pms");
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            // 得到 currentActivityThread 变量
            Field currentActivityThreadField = activityThread.getDeclaredField("sCurrentActivityThread");
            currentActivityThreadField.setAccessible(true);
            Object mActivityThread = currentActivityThreadField.get(null);

            // 得到 mActivityThread 的 sPackageManager 变量
            Field sPackageManagerField = activityThread.getDeclaredField("sPackageManager");
            sPackageManagerField.setAccessible(true);
            Object sPackageManager = sPackageManagerField.get(mActivityThread);

            // 需要实现的接口
            Class<?> iPackageManagerInterface = Class.forName("android.content.pm.IPackageManager");
            // 生成代理的 PackageManager
            Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class<?>[]{iPackageManagerInterface},new PmsInvocationHandler(sPackageManager));

            // 替换掉 ActivityThread 的 PackageManager
            sPackageManagerField.set(mActivityThread,proxy);

            // 替换掉 ApplicationPackageManager 里面的 mPM 对象
            PackageManager pm = context.getPackageManager() ;
            Field mPmField = pm.getClass().getDeclaredField("mPM");
            mPmField.setAccessible(true);
            mPmField.set(pm,proxy);

        }catch (Exception e){
            Timber.e("error msg is %s",e.getMessage());
        }
    }
}
