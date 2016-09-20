package cat.olivadevelop.atomicspacewar.tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
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
    //public ShapeRenderer shape;
    private Vector2 previousPosition;
    public float[] area = new float[]{
            getX(), getY(),
            getX(), getY() + (getHeight() * getScaleY()),
            getX() + (getWidth() * getScaleX()), getY() + (getHeight() * getScaleY()),
            getX() + (getWidth() * getScaleX()), getY()
    };

    public GameActor(TextureRegion tRegion) {
        super(tRegion);
        setWidth(tRegion.getRegionWidth());
        setHeight(tRegion.getRegionHeight());
        setOrigin(getWidth() / 2, getHeight() / 2);
        polygon = new Polygon(area);
        polygon.setOrigin(getWidth() / 2, getHeight() / 2);
        //shape = new ShapeRenderer();
        previousPosition = new Vector2(getX(), getY());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        /*batch.end();
        shape.setProjectionMatrix(batch.getProjectionMatrix());
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(ColorGame.RED);
        shape.polygon(polygon.getTransformedVertices());
        shape.end();
        batch.begin();*/
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
    }

    public void kicked(int damage) {
        addAction(Actions.sequence(Actions.color(Color.RED, 0f), Actions.color(Color.WHITE, 1f)));
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
        // shape.dispose();
    }
}
