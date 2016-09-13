package cat.olivadevelop.atomicspacewar.tools;

import com.badlogic.gdx.utils.async.AsyncExecutor;

/**
 * Created by Oliva on 28/12/2015.
 */
public class AsyncGame extends AsyncExecutor {
    public static final int MAX_OCURRENCES = 30;

    public AsyncGame(GeneralScreen screen) {
        super(MAX_OCURRENCES);
    }

    public AsyncGame() {
        super(MAX_OCURRENCES);
    }
}
