package cat.olivadevelop.atomicspacewar.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import static cat.olivadevelop.atomicspacewar.tools.GameLogic.getUi;

/**
 * Created by Oliva on 14/09/2016.
 */
public class Notification extends Dialog {

    public enum NotificationType {
        SUCCESS, FAIL, INFO;
    }

    LabelGame label;

    public Notification(String text, NotificationType type, float x, float y) {
        super("", GameLogic.getSkin());
        setSize(700, 50);
        setPosition(x, y);
        label = new LabelGame(text);
        add(label).center();

        label.setColor(ColorGame.BLUE);
        setKeepWithinStage(false);
        setBackground(
                new NinePatchDrawable(
                        new NinePatch(getUi("glassPanel_cornerBL"), 20, 20, 20, 20)
                )
        );
        show();
    }

    public void show() {
        MoveToAction moveTo = new MoveToAction();
        moveTo.setPosition(getX(), getY() - getHeight());
        moveTo.setDuration(.2f);

        MoveToAction moveBack = new MoveToAction();
        moveBack.setPosition(getX(), Gdx.graphics.getHeight() + getHeight());
        moveBack.setDuration(.2f);

        SequenceAction action = new SequenceAction(moveTo, Actions.delay(4.0f), moveBack, Actions.removeActor());
        addAction(action);
    }
}