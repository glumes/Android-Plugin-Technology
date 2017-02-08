package hook_activity_thread;

import android.os.Handler;

import java.lang.reflect.Field;

import timber.log.Timber;

/**
 * Created by zhaoying on 2017/2/8.
 */

public class ActivityThreadHookUtil {

    private static final String TAG = ActivityThreadHookUtil.class.getSimpleName() ;

    // 反射去找内存里的某个类，一定要去找单例的那个类，否则创建出来的是自己的，不是系统里的
    // ActivityThread 内的 sCurrentActivityThread 就是个单例形式，static 变量
    public  void hookActivityThreadHandler(){
        try {
            Timber.d("hook ActivityThread Handler");
            Class<?> activityThread = Class.forName("android.app.ActivityThread");

            Field currentActivityThreadField = activityThread.getDeclaredField("sCurrentActivityThread");

            currentActivityThreadField.setAccessible(true);

            Object mActivityThread = currentActivityThreadField.get(null); // 静态方法拿到类就行了

            // 访问 Handler
            Field handlerFiled = activityThread.getDeclaredField("mH");

            handlerFiled.setAccessible(true);
            // ActivityThread 的 mH Handler
            Handler mHandler = (Handler) handlerFiled.get(mActivityThread);

            // 得到 handler 的 callback
            Field callbackField = Handler.class.getDeclaredField("mCallback");

            callbackField.setAccessible(true);

            // 把 ActivityThread 中的 mHandler 的 callback 替换成自定义的 callback
            callbackField.set(mHandler,new ActivityThreadHandlerCallback(mHandler));

        }catch (Exception e){
            Timber.e("error msg is %s",e.getMessage());
        }
    }




}
