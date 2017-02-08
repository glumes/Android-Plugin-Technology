package hook_activity_manager_service;

import android.content.Context;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

/**
 * Created by zhaoying on 2017/1/23.
 */

public class ActivityManagerServiceHookUtil {




    public static void hookAms(){
        try {
            // 通过 Class 对象得到 JVM 中的这个类
            // Class.forName 方法得到 Class 对象
            Class<?> activityManagerNativeClass = Class.forName("android.app.ActivityManagerNative") ;
            // 得到 ActivityManagerNative 中的 gDefault 成员变量
            Field gDefaultField = activityManagerNativeClass.getDeclaredField("gDefault");
            // 设置访问权限
            gDefaultField.setAccessible(true);
            // 得到 gDefault 成员变量的变量值，此处为何为 null ？
            // 获得静态的 static 变量则为 null
            Object gDefault = gDefaultField.get(null);

            // Class.forName 方法得到 Class 对象
            Class<?> singletonClass = Class.forName("android.util.Singleton");
            // 得到 Singleton 中的 mInstance 变量
            Field mInstanceField = singletonClass.getDeclaredField("mInstance");
            // 设置访问权限
            mInstanceField.setAccessible(true);
            // 得到 gDefault 对象的成员变量 mInstance 的变量值
            Object iActivityManagerProxy = mInstanceField.get(gDefault);
            // 需要实现的接口
            Class<?> iActivityManagerInterface = Class.forName("android.app.IActivityManager");

            Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class<?>[]{ iActivityManagerInterface },new AmsInvocationHandler(iActivityManagerProxy));

            // 将 gDefault 对象的 mInstance 对象设置成我们的自己的 IActivityManager
            mInstanceField.set(gDefault,proxy);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
