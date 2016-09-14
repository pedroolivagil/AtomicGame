package cat.olivadevelop.atomicspacewar.actors;

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
    private final GeneralScreen screen;
    public float dirX;
    public float dirY;
    private float speed;
    private Vector2 previousPosition;
    private Bullet bullet;

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
        setPosition(getX() + dirX * getSpeed() * delta, getY() + dirY * getSpeed() * delta);
        moveAction(delta);
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
}
