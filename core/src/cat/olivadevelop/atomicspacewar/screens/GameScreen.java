package cat.olivadevelop.atomicspacewar.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

import cat.olivadevelop.atomicspacewar.AtomicSpaceWarGame;
import cat.olivadevelop.atomicspacewar.actors.Bullet;
import cat.olivadevelop.atomicspacewar.actors.HUD;
import cat.olivadevelop.atomicspacewar.actors.Player;
import cat.olivadevelop.atomicspacewar.tools.GeneralScreen;
import cat.olivadevelop.atomicspacewar.tools.IntersectorGame;
import cat.olivadevelop.atomicspacewar.tools.Notification;

import static cat.olivadevelop.atomicspacewar.tools.GameLogic.BORDER_WIDTH;
import static cat.olivadevelop.atomicspacewar.tools.GameLogic.MARGIN;
import static cat.olivadevelop.atomicspacewar.tools.GameLogic.getNotificationManager;
import static cat.olivadevelop.atomicspacewar.tools.GameLogic.getPlayersTexture;
import static cat.olivadevelop.atomicspacewar.tools.GameLogic.getString;
import static cat.olivadevelop.atomicspacewar.tools.GameLogic.getTiledMapRenderer;
import static cat.olivadevelop.atomicspacewar.tools.GameLogic.tiledMapH;
import static cat.olivadevelop.atomicspacewar.tools.GameLogic.tiledMapW;

/**
 * Created by Oliva on 12/09/2016.
 */
public class GameScreen extends GeneralScreen {

    public static Player player;
    public static HashMap<String, Player> otherPlayers = new HashMap<String, Player>();
    public static HashMap<String, HashMap<String, Bullet>> otherBulletPlayers = new HashMap<String, HashMap<String, Bullet>>();
    private HUD hud;
    private boolean initPlayer;
    private Rectangle[] boundsRectangle;
    private Controller pad = null;
    private ShapeRenderer shape;

    public GameScreen(AtomicSpaceWarGame game, boolean initPlayer) {
        super(game);
        this.initPlayer = initPlayer;
        boundsRectangle = new Rectangle[]{
                new Rectangle(0 + MARGIN, 0 + MARGIN, BORDER_WIDTH, tiledMapH - (MARGIN * 2)),                    // left
                new Rectangle(0 + MARGIN, tiledMapH - MARGIN, tiledMapW - (MARGIN * 2), BORDER_WIDTH),            // top
                new Rectangle(tiledMapW - MARGIN, tiledMapH - MARGIN, BORDER_WIDTH, -tiledMapH + (MARGIN * 2)),   // right
                new Rectangle(tiledMapW - MARGIN, 0 + MARGIN, -tiledMapW + (MARGIN * 2), BORDER_WIDTH)            // bottom
        };
        shape = new ShapeRenderer();
        player = new Player(this, getPlayersTexture("playerShip3_blue"));
        getNotificationManager().setScreen(this);
    }


    @Override
    public void show() {
        super.show();
        Controllers.addListener(this);
        Gdx.input.setInputProcessor(this);
        checkGamePad();
        getNotificationManager().newAlert(
                getString("welcome") + ", " + GameScreen.otherPlayers.size() + " " + getString("quantPlayersCon"),
                Notification.NotificationType.INFO,
                5
        );
        hud = new HUD(this);
        getStage().addActor(hud);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        //Gdx.gl.glClearColor(32f / 255, 39f / 255, 50f / 255, 1);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        getStage().getCamera().position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);

        getTiledMapRenderer().setView(getStage().getCamera().combined, 0, 0, tiledMapW, tiledMapH);
        getTiledMapRenderer().render();

        // Bounds Rectangle
        shape.setProjectionMatrix(getStage().getBatch().getProjectionMatrix());
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.YELLOW);
        shape.rect(boundsRectangle[0].getX(), boundsRectangle[0].getY(), boundsRectangle[0].getWidth(), boundsRectangle[0].getHeight());
        shape.rect(boundsRectangle[1].getX(), boundsRectangle[1].getY(), boundsRectangle[1].getWidth(), boundsRectangle[1].getHeight());
        shape.rect(boundsRectangle[2].getX(), boundsRectangle[2].getY(), boundsRectangle[2].getWidth(), boundsRectangle[2].getHeight());
        shape.rect(boundsRectangle[3].getX(), boundsRectangle[3].getY(), boundsRectangle[3].getWidth(), boundsRectangle[3].getHeight());
        shape.end();

        // important for actors on top
        for (HashMap.Entry<String, Player> entry : otherPlayers.entrySet()) {
            getStage().addActor(entry.getValue());
            entry.getValue().toFront();

            /*shape.setProjectionMatrix(getStage().getBatch().getProjectionMatrix());
            shape.begin(ShapeRenderer.ShapeType.Line);
            shape.setColor(ColorGame.RED);
            shape.polygon(entry.getValue().polygon.getTransformedVertices());
            shape.end();*/
        }
        // bullets enemies
        for (HashMap.Entry<String, HashMap<String, Bullet>> entry : otherBulletPlayers.entrySet()) {
            HashMap<String, Bullet> hmap = entry.getValue();
            for (HashMap.Entry<String, Bullet> x : hmap.entrySet()) {
                getStage().addActor(x.getValue());
                //x.getValue().polygon.setPosition(x.getValue().getX(), x.getValue().getY());
                x.getValue().bulletPolygon.setPosition(x.getValue().getX(), x.getValue().getY());
                x.getValue().timerRemove();

               /* shape.setProjectionMatrix(getStage().getBatch().getProjectionMatrix());
                shape.begin(ShapeRenderer.ShapeType.Line);
                shape.setColor(ColorGame.YELLOW);
                shape.polygon(x.getValue().bulletPolygon.getTransformedVertices());
                shape.end();*/
            }
        }
        if (initPlayer) {
            getStage().addActor(player);
            initPlayer = false;
        }

        getStage().draw();
        // Overlaps
        overlapsAll();
        hud.toFront();
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

        // bullets enemies overlaps player
        for (HashMap.Entry<String, HashMap<String, Bullet>> entry : otherBulletPlayers.entrySet()) {
            HashMap<String, Bullet> hmap = entry.getValue();
            for (HashMap.Entry<String, Bullet> x : hmap.entrySet()) {
                if (IntersectorGame.overlaps(player.polygon, x.getValue().bulletPolygon)) {
                    player.kicked(1);
                    Gdx.app.log("Player", "Kicked");
                }
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
            player.setSpeed(player.SPEED_FAST);
        }
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        if (buttonCode == getGame().getBtnsPad().getKeys("BUTTON_A")) {
            //if (player.isMoving()) {
            player.shoot();
            //}
        }
        if (buttonCode == getGame().getBtnsPad().getKeys("BUTTON_LB")) {
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
        return false;
    }
}
