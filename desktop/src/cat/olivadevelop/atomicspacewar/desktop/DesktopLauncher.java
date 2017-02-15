package cat.olivadevelop.atomicspacewar.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import cat.olivadevelop.atomicspacewar.AtomicSpaceWarGame;
import cat.olivadevelop.atomicspacewar.tools.GameLogic;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.height = (int) GameLogic.resizeImg(1280, 720, 800);
        config.width = 800;
        config.y = 20;
        //config.x = 2100;
        config.addIcon("logo.png", Files.FileType.Internal);
        config.title = "OlivaDevelop Box Games - Atomic SpaceWar";
        config.useGL30 = false;
        new LwjglApplication(new AtomicSpaceWarGame(new Play(), new ToastDesktop(), new XboxDesktop()), config);
    }
}
