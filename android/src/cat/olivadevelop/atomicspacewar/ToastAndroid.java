package cat.olivadevelop.atomicspacewar;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import cat.olivadevelop.atomicspacewar.tools.ToastAction;

/**
 * Created by onion on 06/03/2016.
 */
public class ToastAndroid implements ToastAction {

    Handler handler;
    Context context;

    public ToastAndroid(Context context) {
        this.handler = new Handler();
        this.context = context;
    }

    @Override
    public void show(final CharSequence text) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, text, Toast.LENGTH_LONG).show();
            }
        });
    }
}
