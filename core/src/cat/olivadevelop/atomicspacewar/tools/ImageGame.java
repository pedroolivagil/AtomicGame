package cat.olivadevelop.atomicspacewar.tools;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by OlivaDevelop on 26/05/2015.
 */
public class ImageGame extends Image {

    public ImageGame(NinePatch patch) {
        super(patch);
        centerOrigin();
    }

    public ImageGame(TextureRegion region) {
        super(region);
        setWidth(region.getRegionWidth());
        setHeight(region.getRegionHeight());
        centerOrigin();
    }

    public ImageGame(TextureRegion region, float x, float y, float w, float h) {
        super(region);
        setX(x);
        setY(y);
        setWidth(w);
        setHeight(h);
        centerOrigin();
    }

    private void centerOrigin() {
        setOrigin(getWidth() / 2, getHeight() / 2);
    }
}
