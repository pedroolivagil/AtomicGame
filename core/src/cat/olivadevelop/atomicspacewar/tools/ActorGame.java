package cat.olivadevelop.atomicspacewar.tools;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by OlivaDevelop on 26/05/2015.
 */
public class ActorGame extends Actor implements Disposable {

    public GeneralScreen screen;
    public float width = GameLogic.getScreenWidth();
    public float height = GameLogic.getScreenHeight();
    public TextureRegion texture;
    public ShapeRenderer shape;


    public ActorGame(GeneralScreen screen) {
        this.screen = screen;
        shape = new ShapeRenderer();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //batch.setColor(1, 1, 1, parentAlpha);
        super.draw(batch, parentAlpha);
    }

    @Override
    public void dispose() {
        shape.dispose();
    }
}
