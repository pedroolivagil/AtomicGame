package cat.olivadevelop.atomicspacewar.screens;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Timer;

import cat.olivadevelop.atomicspacewar.AtomicSpaceWarGame;
import cat.olivadevelop.atomicspacewar.actors.Background;
import cat.olivadevelop.atomicspacewar.tools.ButtonGame;
import cat.olivadevelop.atomicspacewar.tools.GeneralScreen;
import cat.olivadevelop.atomicspacewar.tools.ImageGame;
import cat.olivadevelop.atomicspacewar.tools.Listener;

import static cat.olivadevelop.atomicspacewar.tools.GameLogic.getApp_asset;
import static cat.olivadevelop.atomicspacewar.tools.GameLogic.getScreenHeight;
import static cat.olivadevelop.atomicspacewar.tools.GameLogic.getScreenWidth;
import static cat.olivadevelop.atomicspacewar.tools.GameLogic.getString;

/**
 * Created by Oliva on 10/09/2016.
 */
public class MainMenuScreen extends GeneralScreen {

    private Background bg_app;
    private ImageGame title;
    private ImageGame p_rock;
    private ImageGame p_ring;
    private ImageGame p_cloudy4;
    private ImageGame p_cloudy7;
    private ButtonGame btnStart;
    private ButtonGame btnExit;

    public MainMenuScreen(AtomicSpaceWarGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        title = new ImageGame(getApp_asset("title"));
        title.setPosition(70, getScreenHeight() / 2 - title.getHeight() / 2);
        title.rotateBy(18);
        bg_app = new Background(this);

        p_cloudy7 = new ImageGame(getApp_asset("cloudy7"));
        p_cloudy7.setPosition(900, 35);

        p_cloudy4 = new ImageGame(getApp_asset("cloudy4"));
        p_cloudy4.setPosition(400, 300);

        p_rock = new ImageGame(getApp_asset("rock1"));
        p_rock.setPosition(0, 0);

        p_ring = new ImageGame(getApp_asset("rings5"));
        p_ring.setPosition(900, 500);

        btnStart = new ButtonGame(getString("mainMenuStart"), .9f);
        btnExit = new ButtonGame(getString("mainMenuExit"), .9f);

        actions();

        /** import to stage*/
        getStage().addActor(bg_app);
        getStage().addActor(p_rock);
        getStage().addActor(p_ring);
        getStage().addActor(p_cloudy7);
        getStage().addActor(p_cloudy4);
        getStage().addActor(title);
        getStage().addActor(btnStart);
        getStage().addActor(btnExit);

        /** Delete at compile **/
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                getGame().setScreen(getGame()._gameScreen);
            }
        }, .5f);
    }

    public Action planetAppear(float scale, float timedelay) {
        return Actions.sequence(
                Actions.scaleTo(0f, 0f, 0f),
                Actions.delay(timedelay, Actions.scaleTo(scale + .1f, scale + .1f, 1f, Interpolation.swingOut))
        );
    }

    public Action otherAppear(float scale, float timedelay) {
        return Actions.sequence(
                Actions.scaleTo(4f, 4f, 0f),
                Actions.delay(timedelay, Actions.scaleTo(scale + .1f, scale + .1f, 1f, Interpolation.swingOut))
        );
    }

    public Action btn_appear(float x, float y, float delay) {
        return Actions.parallel(
                Actions.moveTo(getScreenWidth() * 2, y, 0),
                Actions.delay(delay, Actions.moveTo(x, y, .5f, Interpolation.swingOut)
                )
        );
    }

    public void actions() {
        p_cloudy7.addAction(planetAppear(.5f, .1f));
        p_cloudy4.addAction(planetAppear(.7f, 0));
        p_rock.addAction(planetAppear(.15f, .2f));
        p_ring.addAction(planetAppear(.2f, .3f));
        title.addAction(otherAppear(1f, .2f));

        btnStart.addAction(btn_appear(860, (getScreenHeight() / 2 - btnStart.getHeight() / 2), 1.5f));
        btnExit.addAction(btn_appear(860, ((getScreenHeight() / 2) - (btnExit.getHeight() / 2) - 100), 2f));

        title.addAction(
                Actions.forever(
                        Actions.sequence(
                                Actions.delay(5),
                                Actions.rotateTo(25, .1f),
                                Actions.rotateTo(11, .2f),
                                Actions.rotateTo(18, .1f),
                                Actions.delay(5)
                        )
                )
        );

        btnExit.addListener(new Listener() {
            @Override
            public void action() {
                exitGame();
            }
        });
        btnStart.addListener(new Listener() {
            @Override
            public void action() {
                getGame().setScreen(getGame()._gameScreen);
            }
        });
    }
}
