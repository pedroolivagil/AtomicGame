package cat.olivadevelop.atomicspacewar.actors;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Group;

import cat.olivadevelop.atomicspacewar.screens.GameScreen;
import cat.olivadevelop.atomicspacewar.tools.GeneralScreen;
import cat.olivadevelop.atomicspacewar.tools.ImageGame;
import cat.olivadevelop.atomicspacewar.tools.LabelGame;

import static cat.olivadevelop.atomicspacewar.tools.GameLogic.getScreenHeight;
import static cat.olivadevelop.atomicspacewar.tools.GameLogic.getScreenWidth;
import static cat.olivadevelop.atomicspacewar.tools.GameLogic.getString;
import static cat.olivadevelop.atomicspacewar.tools.GameLogic.getUi;

/**
 * Created by Oliva on 24/09/2016.
 */
public class HUD extends Group {

    private final LabelGame lblEnergy;
    private final Group bgLivesBar;
    private final Group livesBar;
    private ImageGame imgEnergy;
    GeneralScreen screen;

    public HUD(GeneralScreen screen) {
        this.screen = screen;

        // panel de la izquierda
        imgEnergy = new ImageGame(new NinePatch(getUi("glassPanel_cornerTR"), 15, 15, 15, 15));
        imgEnergy.setWidth(350);
        imgEnergy.setHeight(60);
        imgEnergy.setPosition(20, 20);

        lblEnergy = new LabelGame(getString("lblEnergy"), .5f);
        lblEnergy.setPosition(32, 11);
        //lblEnergy.addAction(Actions.alpha(.5f));

        bgLivesBar = new Group();
        for (int x = 0; x < GameScreen.player.getEnergyFixed(); x++) {
            ImageGame i = new ImageGame(getUi("square_shadow"));
            i.setX(x * 28);
            bgLivesBar.addActor(i);
        }
        bgLivesBar.setPosition(180, 37f);

        livesBar = new Group();
        livesBar.setPosition(180, 37f);

        //panel de la derecha
        imgEnergy = new ImageGame(new NinePatch(getUi("glassPanel_cornerTR"), 15, 15, 15, 15));
        imgEnergy.setWidth(350);
        imgEnergy.setHeight(60);
        imgEnergy.setPosition(20, 20);

        addActor(imgEnergy);
        addActor(lblEnergy);
        addActor(bgLivesBar);
        addActor(livesBar);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setPosition(
                screen.getStage().getCamera().position.x - getScreenWidth() / 2,
                screen.getStage().getCamera().position.y - getScreenHeight() / 2
        );

        for (int x = 0; x < GameScreen.player.getCurrentEnergy(); x++) {
            ImageGame i = new ImageGame(getUi("squareRed"));
            i.setX(x * 28);
            livesBar.addActor(i);
        }

        imgEnergy.toFront();
        lblEnergy.toFront();
        bgLivesBar.toFront();
        livesBar.toFront();
    }
}
