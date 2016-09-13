package cat.olivadevelop.atomicspacewar.tools;

import com.badlogic.gdx.controllers.PovDirection;

import java.util.HashMap;

/**
 * Created by Oliva on 12/09/2016.
 */

public interface Xbox {
    /*
    //OLD
    public static final int BUTTON_A = 0;
    public static final int BUTTON_B = 1;
    public static final int BUTTON_X = 2;
    public static final int BUTTON_Y = 3;
    public static final int BUTTON_LB = 4;
    public static final int BUTTON_RB = 5;
    public static final int BUTTON_BACK = 6;
    public static final int BUTTON_START = 7;
    public static final int BUTTON_LS = 8; //Left Stick pressed down
    public static final int BUTTON_RS = 9; //Right Stick pressed down

    public static final int POV = 0;

    public static final int AXIS_LY = 0; //-1 is up | +1 is down
    public static final int AXIS_LX = 1; //-1 is left | +1 is right
    public static final int AXIS_RY = 2; //-1 is up | +1 is down
    public static final int AXIS_RX = 3; //-1 is left | +1 is right
    public static final int AXIS_TRIGGER = 4; //LT and RT are on the same Axis! LT > 0 | RT < 0
    //END OLD

    * It seems there are different versions of gamepads with different ID Strings.
    * Therefore its IMO a better bet to check for:
    * if (controller.getName().toLowerCase().contains("xbox") &&
                  controller.getName().contains("360"))
    *
    * Controller (Gamepad for Xbox 360)
      Controller (XBOX 360 For Windows)
      Controller (Xbox 360 Wireless Receiver for Windows)
      Controller (Xbox wireless receiver for windows)
      XBOX 360 For Windows (Controller)
      Xbox 360 Wireless Receiver
      Xbox Receiver for Windows (Wireless Controller)
      Xbox wireless receiver for windows (Controller)
    */
    //public static final String ID = "XBOX 360 For Windows (Controller)";
    int BUTTON_X = 2;
    int BUTTON_Y = 3;
    int BUTTON_A = 0;
    int BUTTON_B = 1;

    int BUTTON_BACK = 6;
    int BUTTON_START = 7;

    PovDirection BUTTON_DPAD_UP = PovDirection.north;
    PovDirection BUTTON_DPAD_DOWN = PovDirection.south;
    PovDirection BUTTON_DPAD_RIGHT = PovDirection.east;
    PovDirection BUTTON_DPAD_LEFT = PovDirection.west;

    int BUTTON_LB = 4;
    int BUTTON_L3 = 8;
    int BUTTON_RB = 5;
    int BUTTON_R3 = 9;

    int AXIS_LEFT_X = 1; //-1 is left | +1 is right
    int AXIS_LEFT_Y = 0; //-1 is up | +1 is down
    int AXIS_LEFT_TRIGGER = 4; //value 0 to 1f

    int AXIS_RIGHT_X = 3; //-1 is left | +1 is right
    int AXIS_RIGHT_Y = 2; //-1 is up | +1 is down
    int AXIS_RIGHT_TRIGGER = 4; //value 0 to -1f
    HashMap<String, Integer> keys = new HashMap<String, Integer>();

    public void init();

    public int getKeys(String key);
}
