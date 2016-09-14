package cat.olivadevelop.atomicspacewar.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.I18NBundle;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/**
 * Created by OlivaDevelop on 26/05/2015.
 */
public abstract class GameLogic implements Disposable {

    public final static String prefsName = "localPreferences";
    public final static String COOKIE_KEY = "5d77314492c1e10daa3a2366ca1b7103578d856c3a5c89e30b879ad4";
    // Volume Sounds
    public static final float VOLUME_10 = 1.0f;
    public static final float VOLUME_7 = .7f;
    public static final float VOLUME_5 = .5f;
    public static final int tiledMapH = 12800;
    public static final int tiledMapW = 12800;
    private static Preferences prefs;
    // Bundle
    private static I18NBundle bundle;
    // Variables
    private static boolean showADS;
    private static int screenWidth;
    private static int screenHeight;
    // Skin
    private static Skin skinS;
    private static Skin skinL;
    private static Skin skinXL;
    // Textures
    private static TextureAtlas app_asset;
    private static TextureAtlas ui;
    private static TextureAtlas players;
    private static TiledMap tiledMap;
    private static OrthogonalTiledMapRenderer tiledMapRenderer;
    // Sounds
    // Enviroments

    public static void loadUI() {
        // Textures
        ui = new TextureAtlas("textures/ui.atlas");
        players = new TextureAtlas("textures/enemy_ship.atlas");
        // Sounds
        // Preferencias
        if (getPrefs().getBoolean("localReg", true)) {
            getPrefs().putBoolean("localReg", true);
        }
        if (getPrefs().getString("user", "").equals("")) {
            getPrefs().putString("user", "");
        }
        tiledMap = new TmxMapLoader().load("map/space_map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        getPrefs().flush();
    }

    public static void load() {
        // Preferencias
        prefs = Gdx.app.getPreferences(GameLogic.prefsName);
        // Bundle
        bundle = I18NBundle.createBundle(Gdx.files.internal("i18n/MyBundle"), Locale.getDefault());
        // Skin
        skinS = new Skin(Gdx.files.internal("skin/S/uiskin.json"));
        skinL = new Skin(Gdx.files.internal("skin/L/uiskin.json"));
        skinXL = new Skin(Gdx.files.internal("skin/XL/uiskin.json"));
        app_asset = new TextureAtlas("textures/app.atlas");


        ColorGame.initColorGame();
    }

    public static OrthogonalTiledMapRenderer getTiledMapRenderer() {
        return tiledMapRenderer;
    }

    public static boolean isShowADS() {
        return showADS;
    }

    public static void setShowADS(boolean showADS) {
        GameLogic.showADS = showADS;
        if (showADS) {
            GameLogic.setScreenHeight(1180);
        } else {
            GameLogic.setScreenHeight(1280);
        }
    }

    public static String getNumberFormated(BigInteger num) {
        String n = num.toString();
        StringBuffer sb = new StringBuffer();
        for (int z = n.length(); z > 0; z--) {
            if (z % 3 == 0 && z != n.length()) {
                sb.append(".");
            }
            sb.append(n.charAt(n.length() - z));
        }
        return sb.toString();
    }

    public static String getNumberFormated(double num) {
        String n = String.valueOf(num);
        StringBuffer sb = new StringBuffer();
        for (int z = n.length(); z > 0; z--) {
            if (z % 3 == 0 && z != n.length()) {
                sb.append(".");
            }
            sb.append(n.charAt(n.length() - z));
        }
        return sb.toString();
    }

    public static String getNumberFormated(float num) {
        String n = String.valueOf(num);
        StringBuffer sb = new StringBuffer();
        for (int z = n.length(); z > 0; z--) {
            if (z % 3 == 0 && z != n.length()) {
                sb.append(".");
            }
            sb.append(n.charAt(n.length() - z));
        }
        return sb.toString();
    }

    public static String getNumberFormated(int num) {
        String n = String.valueOf(num);
        StringBuffer sb = new StringBuffer();
        for (int z = n.length(); z > 0; z--) {
            if (z % 3 == 0 && z != n.length()) {
                sb.append(".");
            }
            sb.append(n.charAt(n.length() - z));
        }
        return sb.toString();
    }

    public static String zeroFill(int val) {
        String str = "";
        if (val < 10) {
            str += "0";
        }
        return str + "" + val;
    }

    public static String getFormatedTime(int seconds) {
        int hor, min, seg;
        hor = seconds / 3600;
        min = (seconds - (3600 * hor)) / 60;
        seg = seconds - ((hor * 3600) + (min * 60));
        if (seconds > 3599) {
            return zeroFill(hor) + ":" + zeroFill(min) + ":" + zeroFill(seg);
        } else {
            return zeroFill(min) + ":" + zeroFill(seg);
        }
    }

    public static float resizeImg(float anchoOriginal, float altoOriginal, float anchoDeseado) {
        return (anchoDeseado * altoOriginal) / anchoOriginal;
    }

    public static boolean isAudioOn() {
        return getPrefs().getBoolean("audio", true);
    }

    public static void setAudioOn(boolean b) {
        getPrefs().putBoolean("audio", b);
        getPrefs().flush();
    }

    public static void setTheme(String theme) {
        getPrefs().putString("theme", theme);
        getPrefs().flush();
    }

    // Bundle
    public static String getString(String key) {
        return bundle.format(key);
    }

    // Textures

    public static Skin getSkinS() {
        return skinS;
    }

    public static Skin getSkinL() {
        return skinL;
    }

    public static Skin getSkinXL() {
        return skinXL;
    }

    public static TextureRegion getUi(String region) {
        return ui.findRegion(region);
    }

    public static TextureRegion getApp_asset(String region) {
        return app_asset.findRegion(region);
    }

    public static TextureRegion getPlayersTexture(String region) {
        return players.findRegion(region);
    }

    public static TextureRegion[] getSprites(int cols, int rows, Texture t) {
        TextureRegion[][] tmp = TextureRegion.split(t, t.getWidth() / cols, t.getHeight() / rows);
        TextureRegion[] Frames = new TextureRegion[cols * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Frames[index++] = tmp[i][j];
            }
        }
        return Frames;
    }

    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String encrypt(String passwd) {
        String pass = COOKIE_KEY + "" + passwd;
        return getMD5(pass);
    }

    /**
     * Separa la frase en palabras.
     *
     * @param s La cadena a separar.
     * @return Cadena en partes.
     */
    public static String[] split(String s) {
        int cp = 0; // Cantidad de palabras

        // Recorremos en busca de espacios
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') { // Si es un espacio
                cp++; // Aumentamos en uno la cantidad de palabras
            }
        }

        // "Este blog es genial" tiene 3 espacios y 3 + 1 palabras
        String[] partes = new String[cp + 1];
        for (int i = 0; i < partes.length; i++) {
            partes[i] = ""; // Se inicializa en "" en lugar de null (defecto)
        }

        int ind = 0; // Creamos un índice para las palabras
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') { // Si hay un espacio
                ind++; // Pasamos a la siguiente palabra
                continue; // Próximo i
            }
            partes[ind] += s.charAt(i); // Sino, agregamos el carácter a la palabra actual
        }
        return partes; // Devolvemos las partes
    }

    public static int getScreenWidth() {
        return screenWidth;
    }

    public static void setScreenWidth(int screenWidth) {
        GameLogic.screenWidth = screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    public static void setScreenHeight(int screenHeight) {
        GameLogic.screenHeight = screenHeight;
    }

    public static Preferences getPrefs() {
        return prefs;
    }

    @Override
    public void dispose() {
        skinL.dispose();
        skinXL.dispose();
        skinS.dispose();

        ui.dispose();
        app_asset.dispose();
        players.dispose();
        tiledMapRenderer.dispose();
        tiledMap.dispose();
    }
}
