package cat.olivadevelop.atomicspacewar.actors;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import cat.olivadevelop.atomicspacewar.tools.GameActor;

/**
 * Created by Oliva on 12/09/2016.
 */
public class Player extends GameActor {

    public float dirX;
    public float dirY;
    public final float SPEED_DEFAULT = 200;
    private float speed;
    private Vector2 previousPosition;

    public Player(TextureRegion tRegion) {
        super(tRegion);
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
        move(delta);
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

    public void moveAction(float delta){
        //addAction(Actions.rotateTo(calcDegree(getX() + dirX * getSpeed() * delta, getY() + dirY * getSpeed() * delta), 0f));
        setRotation(calcDegree(getX() + dirX * getSpeed() * delta, getY() + dirY * getSpeed() * delta));
    }

    private float calcDegree(float newX, float newY) {
        double finalDeg = Math.atan2(newY - getY(), newX - getX());
        finalDeg = (finalDeg * MathUtils.radiansToDegrees);
        double degrees = finalDeg - 90;
        return (float) degrees;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }

    public void appear() {
        //setPosition(MathUtils.random(0, tiledMapW), MathUtils.random(0, tiledMapH));
        setPosition(100, 100);
    }

    public Vector2 getPreviousPosition() {
        return previousPosition;
    }
}
