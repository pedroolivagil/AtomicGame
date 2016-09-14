package cat.olivadevelop.atomicspacewar.tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

import static cat.olivadevelop.atomicspacewar.tools.GameLogic.getSkinL;

/**
 * Created by Oliva on 30/08/2015.
 */
public class LabelGame extends Label {

    public LabelGame(CharSequence text) {
        super(text, getSkinL());
        setFontScale(1f);
    }

    public LabelGame(CharSequence text, Skin skin) {
        super(text, skin);
        setFontScale(1f);
    }

    /*public LabelGame(CharSequence text, Skin skin, float scale) {
        super(text, skin);
        setFontScale(scale);
    }*/

    public LabelGame(CharSequence text, float scale) {
        super(text, getSkinL());
        setFontScale(scale);
    }

    public LabelGame(CharSequence text, float scale, Color color) {
        super(text, getSkinL());
        setFontScale(scale);
        setColor(color);
    }

    public LabelGame center() {
        setAlignment(Align.center);
        return this;
    }

    /*public LabelGame setPositionXY(float x, float y) {
        setPosition(x, y);
        return this;
    }*/
}
