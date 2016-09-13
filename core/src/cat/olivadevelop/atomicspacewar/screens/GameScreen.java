package cat.olivadevelop.atomicspacewar.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

import cat.olivadevelop.atomicspacewar.AtomicSpaceWarGame;
import cat.olivadevelop.atomicspacewar.actors.Player;
import cat.olivadevelop.atomicspacewar.tools.ColorGame;
import cat.olivadevelop.atomicspacewar.tools.GeneralScreen;
import cat.olivadevelop.atomicspacewar.tools.IntersectorGame;

import static cat.olivadevelop.atomicspacewar.tools.GameLogic.getPlayersTexture;
import static cat.olivadevelop.atomicspacewar.tools.GameLogic.getTiledMapRenderer;
import static cat.olivadevelop.atomicspacewar.tools.GameLogic.tiledMapH;
import static cat.olivadevelop.atomicspacewar.tools.GameLogic.tiledMapW;

/**
 * Created by Oliva on 12/09/2016.
 */
public class GameScreen extends GeneralScreen {

    private boolean initPlayer;
    private Rectangle[] boundsRectangle;
    private Controller pad = null;
    private ShapeRenderer shape;
    public static Player player;
    public static HashMap<String, Player> otherPlayers = new HashMap<String, Player>();

    public GameScreen(AtomicSpaceWarGame game, boolean initPlayer) {
        super(game);
        this.initPlayer = initPlayer;
        boundsRectangle = new Rectangle[]{
                new Rectangle(0, 0, 1, tiledMapH),
                new Rectangle(0, tiledMapH, tiledMapW, 1),
                new Rectangle(tiledMapW, tiledMapH, 1, -tiledMapH),
                new Rectangle(tiledMapW, 0, -tiledMapW, 1)
        };
        shape = new ShapeRenderer();
        player = new Player(getPlayersTexture("playerShip3_blue"));
    }


    @Override
    public void show() {
        super.show();
        Controllers.addListener(this);
        Gdx.input.setInputProcessor(this);
        checkGamePad();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(32f / 255, 39f / 255, 50f / 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        getStage().getCamera().position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);

        getTiledMapRenderer().setView(getStage().getCamera().combined, 0, 0, tiledMapW, tiledMapH);
        getTiledMapRenderer().render();
        // Bounds Rectangle

        shape.setProjectionMatrix(getStage().getBatch().getProjectionMatrix());
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(ColorGame.DARK_BLUE);
        shape.rect(boundsRectangle[0].getX(), boundsRectangle[0].getY(), boundsRectangle[0].getWidth(), boundsRectangle[0].getHeight());
        shape.rect(boundsRectangle[1].getX(), boundsRectangle[1].getY(), boundsRectangle[1].getWidth(), boundsRectangle[1].getHeight());
        shape.rect(boundsRectangle[2].getX(), boundsRectangle[2].getY(), boundsRectangle[2].getWidth(), boundsRectangle[2].getHeight());
        shape.rect(boundsRectangle[3].getX(), boundsRectangle[3].getY(), boundsRectangle[3].getWidth(), boundsRectangle[3].getHeight());
        shape.end();
        // important for actors on top

        for (HashMap.Entry<String, Player> entry : otherPlayers.entrySet()) {
            getStage().addActor(entry.getValue());
        }

        if (initPlayer) {
            getStage().addActor(player);
            initPlayer = false;
        }

        getStage().draw();
        // Overlaps
        overlapsAll();
    }

    private void checkGamePad() {
        if (pad != null) return;
        for (Controller c : Controllers.getControllers()) {
            System.out.println(c.getName());
            Gdx.app.log("Controller", c.getName());
        }
        Array<Controller> controllers = Controllers.getControllers();
        if (controllers.size == 0) {
            getGame().getToast().show("You not have a controller");
        } else {
            getGame().getToast().show("You have a controller");
            for (Controller c : controllers) {
                if (c.getName().toLowerCase().contains("xbox") && c.getName().contains("360") ||
                        (c.getName().toLowerCase().contains("gamesir"))) {
                    setPad(c);
                }
            }
        }
    }

    private void overlapsAll() {
        for (Rectangle r : boundsRectangle) {
            if (IntersectorGame.overlaps(player.polygon, r)) {
                player.appear();
            }
        }
    }

    public void setPad(Controller c) {
        pad = c;
        pad.addListener(this);
    }

    @Override
    public void connected(Controller controller) {
        super.connected(controller);
        checkGamePad();
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        if (buttonCode == getGame().getBtnsPad().getKeys("BUTTON_LB")) {
            //jump
            player.setSpeed(500);

        }
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        if (buttonCode == getGame().getBtnsPad().getKeys("BUTTON_A")) {
            //jump
            player.dirX = 0;
            player.dirY = 0;
        }
        if (buttonCode == getGame().getBtnsPad().getKeys("BUTTON_LB")) {
            //jump
            player.setSpeed(player.SPEED_DEFAULT);
        }

        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        if (axisCode == getGame().getBtnsPad().getKeys("AXIS_LEFT_X")) {
            player.dirX = value;
        } else if (axisCode == getGame().getBtnsPad().getKeys("AXIS_LEFT_Y")) {
            player.dirY = -value;
        }
        Gdx.app.log("Player rotation", player.getRotation() + "");

        return false;
    }
}
