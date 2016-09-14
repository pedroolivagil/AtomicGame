package cat.olivadevelop.atomicspacewar.actors;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import cat.olivadevelop.atomicspacewar.tools.GameLogic;

import static cat.olivadevelop.atomicspacewar.tools.GameLogic.getPlayersTexture;

/**
 * Created by Oliva on 14/09/2016.
 */
public class Bot extends Player {

    private int newX;
    private int newY;

    public Bot() {
        super(getPlayersTexture("playerShip1_green"));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        move(delta);
    }

    @Override
    public void move(float delta) {
        if (getActions().size == 0) { // si no hay acciones
            newX = MathUtils.random(100, GameLogic.getScreenWidth() - 100);
            newY = MathUtils.random(100, GameLogic.getScreenHeight() - 100);
            addAction(
                    Actions.rotateTo(calcDegree(newX, newY), 0f)
            );
            setPosition(getX() + dirX * getSpeed() * delta, getY() + dirY * getSpeed() * delta);
        }
    }
}
