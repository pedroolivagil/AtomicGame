package cat.olivadevelop.atomicspacewar.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import cat.olivadevelop.atomicspacewar.AtomicSpaceWarGame;

import static cat.olivadevelop.atomicspacewar.tools.GameLogic.getApp_asset;
import static cat.olivadevelop.atomicspacewar.tools.GameLogic.getScreenHeight;
import static cat.olivadevelop.atomicspacewar.tools.GameLogic.getScreenWidth;
import static cat.olivadevelop.atomicspacewar.tools.GameLogic.getString;

/**
 * Created by OlivaDevelop on 26/05/2015.
 */
public class GeneralScreen implements Screen, ControllerListener, InputProcessor {

    private AtomicSpaceWarGame _game;
    private Stage _stage;
    private WindowGame wndwExit;
    private ImageGame black;
    private ButtonGame btnWndExitAccept;
    private ButtonGame btnWndExitCancel;

    public GeneralScreen(AtomicSpaceWarGame game) {
        this._game = game;
    }

    @Override
    public void show() {
        _stage = new Stage(new FitViewport(getScreenWidth(), getScreenHeight()));
        Gdx.input.setInputProcessor(getStage());
        Gdx.input.setCatchBackKey(true);
        getStage().addListener(new Listener() {
            @Override
            public void action(InputEvent event, int keycode) {
                if (keycode == Input.Keys.BACK || keycode == Input.Keys.ESCAPE) {
                    actionBackButton();
                }
                actionOtherButton(event, keycode);
            }
        });

        btnWndExitAccept = new ButtonGame(getString("yes"));
        btnWndExitCancel = new ButtonGame(getString("no"));

        wndwExit = new WindowGame(this, getString("windowExitTitle"));
        wndwExit.add(btnWndExitAccept).width(300).padLeft(25);
        wndwExit.add(btnWndExitCancel).width(300);

        black = new ImageGame(getApp_asset("black"));
        black.setPosition(0, 0);
        black.setWidth(getScreenWidth());
        black.setHeight(getScreenHeight());
        black.setVisible(false);

        btnWndExitAccept.addListener(new Listener() {
            @Override
            public void action() {
                Gdx.app.exit();
            }
        });

        btnWndExitCancel.addListener(new Listener() {
            @Override
            public void action() {
                cancel();
            }
        });

        getStage().addActor(black);
        getStage().addActor(wndwExit);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(22f / 255, 22f / 255, 22f / 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        getStage().act(delta);
        getStage().draw();
    }

    @Override
    public void resize(int width, int height) {
        getStage().getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        getStage().dispose();
    }

    public void actionBackButton() {

    }

    public void actionOtherButton(InputEvent event, int keycode) {

    }

    public Stage getStage() {
        return _stage;
    }

    public AtomicSpaceWarGame getGame() {
        return _game;
    }

    public void clearGroups() {
    }

    public void cancel() {
        wndwExit.setVisible(false);
        black.setVisible(false);
    }

    public void exitGame() {
        wndwExit.setVisible(true);
        black.setVisible(true);
        black.toFront();
        wndwExit.toFront();
    }

    /**
     * ControllerListener
     **/
    @Override
    public void connected(Controller controller) {
        for (Controller c : Controllers.getControllers()) {
            System.out.println(c.getName());
            Gdx.app.log("Controller", c.getName());
        }
    }

    @Override
    public void disconnected(Controller controller) {

    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        return false;
    }

    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value) {
        return false;
    }

    @Override
    public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
        return false;
    }

    /**
     * InputProcessor
     **/
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
