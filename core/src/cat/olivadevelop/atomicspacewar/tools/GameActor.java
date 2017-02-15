package cat.olivadevelop.atomicspacewar.tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Disposable;


/**
 * Created by Oliva on 15/04/2015.
 */
public class GameActor extends ImageGame implements Disposable {

    public boolean alive;
    public Polygon polygon;
    private Vector2 previousPosition;
    private int energyFixed;
    private int currentEnergy;
    private TextureRegion tRegion;
    public float[] area = new float[]{
            getX(), getY(),
            getX(), getY() + (getHeight() * getScaleY()),
            getX() + (getWidth() * getScaleX()), getY() + (getHeight() * getScaleY()),
            getX() + (getWidth() * getScaleX()), getY()
    };

    public GameActor(TextureRegion tRegion) {
        super(tRegion);
        this.tRegion = tRegion;
        setWidth(tRegion.getRegionWidth());
        setHeight(tRegion.getRegionHeight());
        setOrigin(getWidth() / 2, getHeight() / 2);
        polygon = new Polygon(area);
        polygon.setOrigin(getWidth() / 2, getHeight() / 2);
        previousPosition = new Vector2(getX(), getY());
        energyFixed = 1;
        currentEnergy = energyFixed;
    }

    public void setEnergyFixed(int energyFixed) {
        this.energyFixed = energyFixed;
    }

    public int getEnergyFixed() {
        return energyFixed;
    }

    public int getCurrentEnergy() {
        return currentEnergy;
    }

    public void setCurrentEnergy(int currentEnergy) {
        this.currentEnergy = currentEnergy;
    }

    @Override
    public void setScale(float scaleXY) {
        super.setScale(scaleXY);
        polygon.setScale(scaleXY, scaleXY);
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

    public void shoot() {
    }

    public void death() {
        alive = false;
        clearActions();
        drop();
        remove();
    }

    public void kicked(int damage) {
        setCurrentEnergy(getCurrentEnergy() - damage);
        addAction(Actions.sequence(Actions.color(Color.RED, 0f), Actions.color(Color.WHITE, 1f)));
        if (getCurrentEnergy() <= 0) {
            death();
        }
    }

    public void drop() {
    }

    protected float calcDegree(float newX, float newY) {
        double finalDeg = Math.atan2(newY - getY(), newX - getX());
        finalDeg = (finalDeg * MathUtils.radiansToDegrees);
        double degrees = finalDeg - 90;
        return (float) degrees;
    }

    @Override
    public void dispose() {
    }
}
