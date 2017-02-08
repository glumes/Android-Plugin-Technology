package hook_activity_thread;

import android.os.Handler;
import android.os.Message;
import android.util.Log;


/**
 * Created by zhaoying on 2017/2/8.
 */

public class ActivityThreadHandlerCallback implements Handler.Callback {

    private static final String TAG = ActivityThreadHandlerCallback.class.getSimpleName() ;
    Handler handler ;

    public ActivityThreadHandlerCallback(Handler handler) {
        super();
        this.handler = handler;
    }

    @Override
    public boolean handleMessage(Message msg) {

//        Timber.d("hook_activity_thread_handler_callback");

        Log.d(TAG,"hook callback");
        /**
         *
         */
        handler.handleMessage(msg);// 传入的 Handler 继续处理消息 Msg
        return true;
    }
}
