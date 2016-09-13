package cat.olivadevelop.atomicspacewar.tools;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by Oliva on 15/05/2015.
 */
public class Listener extends InputListener {

    public void action() {
    }

    public void action(InputEvent event, int keycode) {
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        return true;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        action();
        super.touchUp(event, x, y, pointer, button);
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        return true;
    }

    @Override
    public boolean keyUp(InputEvent event, int keycode) {
        action(event, keycode);
        return super.keyUp(event, keycode);
    }
}
