package cat.olivadevelop.atomicspacewar.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
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
    public Polygon bulletPolygon;

    public Bullet(GeneralScreen screen, Color color, float x, float y, float angle, float dirX, float dirY) {
        super(getPlayersTexture("bullet"));
        setPosition(x, y - getHeight() / 2);
        setRotation(angle);
        setColor(color);
        speed = 600f;
        setScale(.5f);
        this.dirX = dirX;
        this.dirY = dirY;
        timerRemove();
        area = new float[]{
                getX(), getY(),
                getX(), getY() + (getHeight() * getScaleY()),
                getX() + (getWidth() * getScaleX()), getY() + (getHeight() * getScaleY()),
                getX() + (getWidth() * getScaleX()), getY()
        };
        bulletPolygon = new Polygon(area);
        bulletPolygon.setPosition(getX(),getY());
        bulletPolygon.setRotation(angle);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (isSpeedCorrect(dirX, dirY, speed, delta)) {
            remove();
        }
        //Gdx.app.log("SPEED", "X->" + (dirX * speed * delta) + "; Y->" + (dirY * speed * delta));
        //Gdx.app.log("SPEED", "Total->" + ((dirX * speed * delta) + (dirY * speed * delta)));
        setPosition(getX() + dirX * speed * delta, getY() + dirY * speed * delta);
        movePolygon();
    }

    public void movePolygon() {
        bulletPolygon.setPosition(getX(), getY());
    }

    public boolean isSpeedCorrect(float x, float y, float s, float delta) {
        return (x * s * delta) == 0.0 && (y * s * delta) == 0.0;
    }

    public float getSpeed() {
        return speed;
    }

    public void timerRemove() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                Gdx.app.log("Remove Bullet", "True");
                addAction(
                        Actions.parallel(
                                Actions.alpha(0f, .3f),
                                Actions.delay(1, Actions.removeActor())
                        )
                );
            }
        }, 10f);
    }
}
