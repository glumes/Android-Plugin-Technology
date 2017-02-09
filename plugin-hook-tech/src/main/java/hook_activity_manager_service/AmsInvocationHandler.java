package hook_activity_manager_service;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by zhaoying on 2017/1/23.
 */

public class AmsInvocationHandler implements InvocationHandler {

    private Object iActivityManagerObject;

    private static final String TAG = AmsInvocationHandler.class.getSimpleName() ;

    public AmsInvocationHandler(Object object) {
        this.iActivityManagerObject = object;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        Log.d(TAG,"hook ActivityManagerService");
        return method.invoke(iActivityManagerObject,objects);
    }
}
