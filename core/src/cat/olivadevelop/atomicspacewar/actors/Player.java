package cat.olivadevelop.atomicspacewar.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import cat.olivadevelop.atomicspacewar.tools.ColorGame;
import cat.olivadevelop.atomicspacewar.tools.GameActor;
import cat.olivadevelop.atomicspacewar.tools.GeneralScreen;

/**
 * Created by Oliva on 12/09/2016.
 */
public class Player extends GameActor {

    public final float SPEED_DEFAULT = 200;
    public final float SPEED_FAST = 400;
    private final GeneralScreen screen;
    public float dirX;
    public float dirY;
    private float speed;
    private Vector2 previousPosition;
    private Bullet bullet;
    boolean moving;

    public Player(GeneralScreen screen, TextureRegion tRegion) {
        super(tRegion);
        this.screen = screen;
        dirX = 0;
        dirY = 0;
        setWidth(tRegion.getRegionWidth());
        setHeight(tRegion.getRegionHeight());
        setSpeed(SPEED_DEFAULT);
        appear();
        previousPosition = new Vector2(getX(), getY());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        polygon.setPosition(getX(), getY());
    }

    public boolean hasMoved() {
        if (previousPosition.x != getX() || previousPosition.y != getY()) {
            previousPosition.x = getX();
            previousPosition.y = getY();
            return true;
        }
        return false;
    }

    public void move(float delta) {
        Gdx.app.log("dirX", dirX + "");
        Gdx.app.log("dirY", dirY + "");
        if ((dirX == (float) 0.000015258789 && dirY == (float) -0.007827878) ||
                (dirX == (float) 0.0 && dirY == (float) -0.007827878)) {
            dirX = 0;
            dirY = 0;
        }
        setPosition(getX() + dirX * getSpeed() * delta, getY() + dirY * getSpeed() * delta);
        if (dirX != 0.0 && dirY != 0.0) {
            moveAction(delta);
            moving = false;
        }
        moving = true;
    }

    public void moveAction(float delta) {
        setRotation(calcDegree(getX() + dirX * getSpeed() * delta, getY() + dirY * getSpeed() * delta));
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void appear() {
        //setPosition(MathUtils.random(0, tiledMapW), MathUtils.random(0, tiledMapH));
        setPosition(100, 100);
        alive = true;
        moving = false;
    }

    @Override
    public void death() {
        super.death();
    }

    @Override
    public void shoot() {
        super.shoot();
        bullet = new Bullet(ColorGame.RED, getX() + getWidth() / 2, getY() + getHeight() / 2, getRotation(), dirX, dirY);
        screen.getStage().addActor(bullet);
        toFront();
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }
}
