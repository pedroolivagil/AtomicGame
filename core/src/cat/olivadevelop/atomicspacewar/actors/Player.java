package cat.olivadevelop.atomicspacewar.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Timer;

import org.json.JSONException;
import org.json.JSONObject;

import cat.olivadevelop.atomicspacewar.screens.GameScreen;
import cat.olivadevelop.atomicspacewar.tools.ColorGame;
import cat.olivadevelop.atomicspacewar.tools.GameActor;
import cat.olivadevelop.atomicspacewar.tools.GeneralScreen;

import static cat.olivadevelop.atomicspacewar.tools.GameLogic.MARGIN;

/**
 * Created by Oliva on 12/09/2016.
 */
public class Player extends GameActor {

    public final float SPEED_DEFAULT = 150;
    public final float SPEED_FAST = 300;
    private final GeneralScreen screen;
    public float dirX;
    public float dirY;
    private float speed;
    private Bullet bullet;
    private boolean moving;
    private boolean canShoot;
    private Fire motorFire;
    private boolean fireInStage;

    public Player(GeneralScreen screen, TextureRegion tRegion) {
        super(tRegion);
        this.screen = screen;
        dirX = 0;
        dirY = 0;
        setEnergyFixed(6);
        setCurrentEnergy(3);
        setWidth(tRegion.getRegionWidth());
        setHeight(tRegion.getRegionHeight());
        setSpeed(SPEED_DEFAULT);
        appear();
        canShoot = true;
        motorFire = new Fire(screen, getX(), getY(), 0);
        fireInStage = false;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!fireInStage && this.screen != null && this.screen.getStage() != null && getMotorFire() != null) {
            this.screen.getStage().addActor(getMotorFire());
            fireInStage = true;
        }
        fireMove();
        polygon.setPosition(getX(), getY());
        move(delta);
    }

    private void fireMove() {
        motorFire.setRotation(this.getRotation());
        motorFire.setPosition(getX(),getY());
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
        //setPosition(MathUtils.random(MARGIN, tiledMapW - (MARGIN * 2)), MathUtils.random(MARGIN, tiledMapH - (MARGIN * 2)));
        setPosition(MARGIN + 100, MARGIN + 100);
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
            bullet = new Bullet(screen, ColorGame.RED, getX() + getWidth() / 2, getY() + getHeight() / 2, getRotation(), dirX, dirY);
            screen.getStage().addActor(bullet);
            toFront();
            runTimer();
            //}
            JSONObject dataBullet = new JSONObject();
            try {
                Gdx.app.log("Player Shoot", "");
                dataBullet.put("x", GameScreen.player.getX());
                dataBullet.put("y", GameScreen.player.getY());
                dataBullet.put("color", ColorGame.GREEN_POINTS);
                dataBullet.put("angle", GameScreen.player.getRotation());
                dataBullet.put("dirX", GameScreen.player.dirX);
                dataBullet.put("dirY", GameScreen.player.dirY);
                screen.getGame().getSocket().emit("playerShoot", dataBullet);
            } catch (JSONException e) {
                e.printStackTrace();
            }
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

    public Fire getMotorFire() {
        return motorFire;
    }
}
