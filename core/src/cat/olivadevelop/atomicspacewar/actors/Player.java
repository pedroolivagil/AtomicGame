package cat.olivadevelop.atomicspacewar.actors;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;

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
    boolean canShoot;

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
        canShoot = true;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        polygon.setPosition(getX(), getY());
    }

    public boolean hasMoved() {
        //detect movement of other players
        if (previousPosition.x != getX() || previousPosition.y != getY()) {
            previousPosition.x = getX();
            previousPosition.y = getY();
            return true;
        }
        return false;
    }

    public void move(float delta) {
        if ((dirX == (float) 0.000015258789 && dirY == (float) -0.007827878)) {
            dirX = 0.0f;
            dirY = 0.0f;
        }
        setPosition(getX() + dirX * getSpeed() * delta, getY() + dirY * getSpeed() * delta);
        if (!(dirX == 0.0 && dirY == 0.0)) {
            moveAction(delta);
        }
        //Gdx.app.log("Actor speed + direction", "Rot: " + getRotation() + "; RadiansX;" + dirX + "; RadiansY;" + dirY);
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
        if (canShoot) {
            //if (Math.abs(dirX * getSpeed() * Gdx.graphics.getDeltaTime()) > 0.7f || Math.abs(dirY * getSpeed() * Gdx.graphics.getDeltaTime()) > 0.7f) {
            //if () {
                canShoot = false;
                bullet = new Bullet(ColorGame.RED, getX() + getWidth() / 2, getY() + getHeight() / 2, getRotation(), dirX, dirY);
                screen.getStage().addActor(bullet);
                toFront();
                runTimer();
            //}
        }
    }

    public void runTimer() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                canShoot = true;
            }
        }, .2f);
    }
}
