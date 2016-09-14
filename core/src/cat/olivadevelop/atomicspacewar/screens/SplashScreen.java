package cat.olivadevelop.atomicspacewar.screens;

import com.badlogic.gdx.utils.Timer;

import cat.olivadevelop.atomicspacewar.AtomicSpaceWarGame;
import cat.olivadevelop.atomicspacewar.tools.GameLogic;
import cat.olivadevelop.atomicspacewar.tools.GeneralScreen;
import cat.olivadevelop.atomicspacewar.tools.ImageGame;

import static cat.olivadevelop.atomicspacewar.tools.GameLogic.getApp_asset;
import static cat.olivadevelop.atomicspacewar.tools.GameLogic.loadUI;

/**
 * Created by Oliva on 10/09/2016.
 */
public class SplashScreen extends GeneralScreen {

    public SplashScreen(AtomicSpaceWarGame game) {
        super(game);
        loadUI();
    }

    @Override
    public void show() {
        super.show();
        ImageGame logo = new ImageGame(getApp_asset("logo"));
        logo.setPosition(GameLogic.getScreenWidth() / 2 - logo.getWidth() / 2, GameLogic.getScreenHeight() / 2 - logo.getHeight() / 2);
        getStage().addActor(logo);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                getGame().setScreen(getGame()._mainMenuScreen);
            }
        }, .5f);
    }
}
