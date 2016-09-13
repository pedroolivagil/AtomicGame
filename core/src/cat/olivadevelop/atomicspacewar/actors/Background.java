package cat.olivadevelop.atomicspacewar.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;

import cat.olivadevelop.atomicspacewar.tools.ActorGame;
import cat.olivadevelop.atomicspacewar.tools.GeneralScreen;

import static cat.olivadevelop.atomicspacewar.tools.GameLogic.getApp_asset;
import static cat.olivadevelop.atomicspacewar.tools.GameLogic.getScreenHeight;
import static cat.olivadevelop.atomicspacewar.tools.GameLogic.getScreenWidth;

/**
 * Created by Oliva on 10/09/2016.
 */
public class Background extends ActorGame {

    private boolean init;

    public Background(GeneralScreen screen) {
        super(screen);
        texture = getApp_asset("background");
        setWidth(getScreenWidth());
        setHeight(getScreenHeight());
        setPosition(0, 0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        // first line
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(texture, getX(), getY(), getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
}
