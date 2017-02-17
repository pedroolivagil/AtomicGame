package cat.olivadevelop.atomicspacewar.actors;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Group;

import cat.olivadevelop.atomicspacewar.screens.GameScreen;
import cat.olivadevelop.atomicspacewar.tools.GameLogic;
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
    private final LabelGame lblShield;
    private final Group bgLivesBar;
    private final Group bgShieldBar;
    private final Group livesBar;
    private final Group shieldBar;
    private ImageGame imgEnergy;
    private ImageGame imgShield;
    GeneralScreen screen;

    public HUD(GeneralScreen screen) {
        this.screen = screen;

        // panel de la izquierda
        imgEnergy = new ImageGame(new NinePatch(getUi("glassPanel_cornerTR"), 15, 15, 15, 15));
        imgEnergy.setWidth(350);
        imgEnergy.setHeight(60);
        imgEnergy.setPosition(20, 20);

        //panel de la derecha
        imgShield = new ImageGame(new NinePatch(getUi("glassPanel_cornerTL"), 15, 15, 15, 15));
        imgShield.setWidth(350);
        imgShield.setHeight(60);
        imgShield.setPosition(GameLogic.getScreenWidth() - imgShield.getWidth() - 20, 20);

        lblEnergy = new LabelGame(getString("lblEnergy"), .5f);
        lblEnergy.setPosition(32, 11);
        //lblEnergy.addAction(Actions.alpha(.5f));

        lblShield = new LabelGame(getString("lblShield"), .5f);
        lblShield.setPosition(getScreenWidth() - lblShield.getWidth() + 64, 11);
        //lblShield.addAction(Actions.alpha(.5f));

        bgLivesBar = new Group();
        for (int x = 0; x < GameScreen.player.getEnergyFixed(); x++) {
            ImageGame i = new ImageGame(getUi("square_shadow"));
            i.setX(x * 28);
            bgLivesBar.addActor(i);
        }
        bgLivesBar.setPosition(180, 37f);

        livesBar = new Group();
        livesBar.setPosition(180, 37f);

        bgShieldBar = new Group();
        for (int x = 0; x < GameScreen.player.getEnergyFixed(); x++) {
            ImageGame i = new ImageGame(getUi("square_shadow"));
            i.setX(x * 28);
            bgShieldBar.addActor(i);
        }
        bgShieldBar.setPosition(getScreenWidth() - 160 - 180, 37f);

        shieldBar = new Group();
        shieldBar.setPosition(getScreenWidth() - 160 - 180, 37f);

        addActor(imgEnergy);
        addActor(imgShield);
        addActor(lblEnergy);
        addActor(lblShield);
        addActor(bgLivesBar);
        addActor(bgShieldBar);
        addActor(livesBar);
        addActor(shieldBar);
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

        for (int x = 0; x < GameScreen.player.getCurrentEnergy(); x++) {
            ImageGame i = new ImageGame(getUi("squareGreen"));
            i.setX((GameScreen.player.getEnergyFixed() - x - 1) * 28);
            shieldBar.addActor(i);
        }

        imgEnergy.toFront();
        imgShield.toFront();

        lblEnergy.toFront();
        lblShield.toFront();

        bgLivesBar.toFront();
        bgShieldBar.toFront();

        livesBar.toFront();
        shieldBar.toFront();
    }
}
