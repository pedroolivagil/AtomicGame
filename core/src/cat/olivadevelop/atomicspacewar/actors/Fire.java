package cat.olivadevelop.atomicspacewar.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import cat.olivadevelop.atomicspacewar.tools.ActorGame;
import cat.olivadevelop.atomicspacewar.tools.ColorGame;
import cat.olivadevelop.atomicspacewar.tools.GameLogic;
import cat.olivadevelop.atomicspacewar.tools.GeneralScreen;

/**
 * Created by Oliva on 17/02/2017.
 */

public class Fire extends ActorGame {


    private final Animation animation;
    private float elapsedTime;
    private Texture texture;

    public Fire(GeneralScreen screen, float x, float y, float rotation) {
        super(screen);
        TextureRegion[][] tmp = new TextureRegion[][]{
                {
                        GameLogic.getUi("fire14"),
                        GameLogic.getUi("fire15")
                }
        };
        animation = new Animation(1 / 10f, getSprites(2, 1, tmp));
        setPosition(x, y);
        setRotation(rotation);
        setHeight(31);
        setWidth(14);
        setOrigin(getWidth() / 2, getHeight() / 2);
        setScale(1);
        setName("fireShip");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(ColorGame.WHITE);
        super.draw(batch, parentAlpha);
        elapsedTime += Gdx.graphics.getDeltaTime();
        batch.draw(animation.getKeyFrame(elapsedTime, true), getX() - getWidth() / 2, getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    private TextureRegion[] getSprites(int cols, int rows, TextureRegion[][] tmp) {
        TextureRegion[] Frames = new TextureRegion[cols * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Frames[index++] = tmp[i][j];
            }
        }
        return Frames;
    }
}