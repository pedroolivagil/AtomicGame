package cat.olivadevelop.atomicspacewar.desktop;

import cat.olivadevelop.atomicspacewar.tools.Xbox;

/**
 * Created by Oliva on 12/09/2016.
 */
public class XboxDesktop implements Xbox {

    @Override
    public void init() {
        keys.put("BUTTON_X", 2);
        keys.put("BUTTON_Y", 3);
        keys.put("BUTTON_A", 0);
        keys.put("BUTTON_B", 1);
        keys.put("BUTTON_DPAD_UP", 0);
        keys.put("BUTTON_DPAD_DOWN", 0);
        keys.put("BUTTON_DPAD_RIGHT", 0);
        keys.put("BUTTON_DPAD_LEFT", 0);
        keys.put("BUTTON_LB", 4);
        keys.put("BUTTON_L3", 8);
        keys.put("BUTTON_RB", 5);
        keys.put("BUTTON_R3", 9);
        keys.put("AXIS_LEFT_X", 1);
        keys.put("AXIS_LEFT_Y", 0);
        keys.put("AXIS_LEFT_TRIGGER", 4);
        keys.put("AXIS_RIGHT_X", 3);
        keys.put("AXIS_RIGHT_Y", 2);
        keys.put("AXIS_RIGHT_TRIGGER", 4);
        keys.put("BUTTON_START", 7);
        keys.put("BUTTON_SELECT", 6);
    }

    @Override
    public int getKeys(String key) {
        return keys.get(key);
    }
}
