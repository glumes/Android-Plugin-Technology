package hook_package_manager_service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import timber.log.Timber;

/**
 * Created by zhaoying on 2017/2/7.
 */

public class PmsInvocationHandler implements InvocationHandler {

    private Object iPackageManagerObject ;
    private static final String TAG = PmsInvocationHandler.class.getSimpleName() ;

    public PmsInvocationHandler(Object iPackageManagerObject) {
        this.iPackageManagerObject = iPackageManagerObject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Timber.d("hook PackageManagerService");
        return method.invoke(iPackageManagerObject,args);
    }
}
