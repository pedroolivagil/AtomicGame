package cat.olivadevelop.atomicspacewar.actors;

import com.badlogic.gdx.graphics.Color;

import cat.olivadevelop.atomicspacewar.tools.GameActor;

import static cat.olivadevelop.atomicspacewar.tools.GameLogic.getPlayersTexture;

/**
 * Created by Oliva on 14/09/2016.
 */
public class Bullet extends GameActor {

    private final float dirY;
    private final float dirX;
    private float speed;

    public Bullet(Color color, float x, float y, float angle, float dirX, float dirY) {
        super(getPlayersTexture("bullet"));
        setPosition(x, y - getHeight()/2);
        setRotation(angle);
        setColor(color);
        speed = 600f;
        setScale(.5f);
        this.dirX = dirX;
        this.dirY = dirY;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setPosition(getX() + dirX * speed * delta, getY() + dirY * speed * delta);
    }
}
