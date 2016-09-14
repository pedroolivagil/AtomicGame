package cat.olivadevelop.atomicspacewar.tools;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;

import cat.olivadevelop.atomicspacewar.screens.GameScreen;

import static cat.olivadevelop.atomicspacewar.tools.GameLogic.getScreenHeight;
import static cat.olivadevelop.atomicspacewar.tools.GameLogic.getSkinS;
import static cat.olivadevelop.atomicspacewar.tools.GameLogic.getUi;

/**
 * Created by Oliva on 14/09/2016.
 */
public class Notification extends Dialog {

    LabelGame label;
    private float time;

    public Notification(String text, NotificationType type, float time) {
        super("", getSkinS());
        this.time = time;
        setSize(700, 50);
        setKeepWithinStage(false);
        setBackground(
                new NinePatchDrawable(
                        new NinePatch(getUi("glassPanel_projection"), 10, 10, 10, 10)
                )
        );
        addAction(Actions.alpha(0f, 0f));
        label = new LabelGame(text, getSkinS());
        switch (type) {
            case INFO:
                label.setColor(ColorGame.WHITE);
                break;
            case SUCCESS:
                label.setColor(ColorGame.GREEN_POINTS);
                break;
            case FAIL:
                label.setColor(ColorGame.RED);
                break;
        }
        clearChildren();
        add(label).width(label.getWidth()).height(label.getHeight());
        show();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setPosition(
                GameScreen.player.getX() + GameScreen.player.getWidth() / 2 - getWidth() / 2,
                GameScreen.player.getY() + (getScreenHeight() / 2) + GameScreen.player.getHeight() - (getHeight() * 2)
        );
    }

    public void show() {
        AlphaAction alphaAppear = new AlphaAction();
        alphaAppear.setAlpha(1f);
        alphaAppear.setDuration(.2f);

        AlphaAction alphaRemove = new AlphaAction();
        alphaRemove.setAlpha(0f);
        alphaRemove.setDuration(.2f);

        SequenceAction action = new SequenceAction(alphaAppear, Actions.delay(time), alphaRemove, Actions.removeActor());
        addAction(action);
    }

    public enum NotificationType {
        SUCCESS, FAIL, INFO;
    }
}