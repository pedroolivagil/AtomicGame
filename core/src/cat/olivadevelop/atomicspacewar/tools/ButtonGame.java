package cat.olivadevelop.atomicspacewar.tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;

import static cat.olivadevelop.atomicspacewar.tools.GameLogic.getSkinXL;
import static cat.olivadevelop.atomicspacewar.tools.GameLogic.getUi;

/**
 * Created by OlivaDevelop on 26/05/2015.
 */
public class ButtonGame extends Group {
    private Label label;
    private Image image;

    public ButtonGame(String text) {
        create(text);
        setScale(.7f);
    }

    public ButtonGame(String text, float scale) {
        create(text);
        setScale(scale);
    }

    private void create(String text) {
        label = new Label(text.toUpperCase(), getSkinXL());
        image = new Image(new NinePatchDrawable(new NinePatch(getUi("glassPanel_projection"), 5, 5, 10, 10)));
        image.setWidth(label.getWidth() + (label.getWidth() * .1f));
        image.setHeight(label.getHeight());
        setWidth(350);
        setHeight(image.getHeight());
        addActors(new Actor[]{label, image});
    }

    public void addActors(Actor[] actor) {
        if (hasChildren()) {
            clearChildren();
        }
        for (Actor anActor : actor) {
            addActor(anActor);
        }
    }

    public float getScale() {
        return super.getScaleX();
    }

    @Override
    public void setColor(Color color) {
        label.setColor(color);
    }

    @Override
    public void setWidth(float width) {
        super.setWidth(width * getScale());
        image.setWidth(width + (width * .1f));
        label.setX(image.getWidth() / 2 - label.getWidth() / 2);
    }

    public ButtonGame center() {
        label.setAlignment(Align.center);
        image.setAlign(Align.center);
        return this;
    }
}
