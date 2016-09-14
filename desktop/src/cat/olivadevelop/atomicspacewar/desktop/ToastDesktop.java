package cat.olivadevelop.atomicspacewar.desktop;


import com.badlogic.gdx.Gdx;

import cat.olivadevelop.atomicspacewar.tools.ToastAction;

/**
 * Created by onion on 06/03/2016.
 */
public class ToastDesktop implements ToastAction {


    @Override
    public void show(CharSequence text) {
        Gdx.app.log("PC Toast",text.toString());

    }
}
