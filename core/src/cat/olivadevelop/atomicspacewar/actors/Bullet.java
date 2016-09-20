package cat.olivadevelop.atomicspacewar.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;

import cat.olivadevelop.atomicspacewar.tools.GameActor;
import cat.olivadevelop.atomicspacewar.tools.GeneralScreen;

import static cat.olivadevelop.atomicspacewar.tools.GameLogic.getPlayersTexture;

/**
 * Created by Oliva on 14/09/2016.
 */
public class Bullet extends GameActor {

    private final float dirY;
    private final float dirX;
    private float speed;

    public Bullet(GeneralScreen screen, Color color, float x, float y, float angle, float dirX, float dirY) {
        super(getPlayersTexture("bullet"));
        setPosition(x, y - getHeight() / 2);
        setRotation(angle);
        setColor(color);
        speed = 600f;
        setScale(.5f);
        this.dirX = dirX;
        this.dirY = dirY;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                remove();
            }
        }, 10f);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if ((dirX * speed * delta) == 0.0 && (dirY * speed * delta) == 0.0) {
            remove();
        }
        //Gdx.app.log("SPEED", "X->" + (dirX * speed * delta) + "; Y->" + (dirY * speed * delta));
        //Gdx.app.log("SPEED", "Total->" + ((dirX * speed * delta) + (dirY * speed * delta)));
        setPosition(getX() + dirX * speed * delta, getY() + dirY * speed * delta);
    }
}
