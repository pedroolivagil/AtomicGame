package cat.olivadevelop.atomicspacewar;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cat.olivadevelop.atomicspacewar.actors.Player;
import cat.olivadevelop.atomicspacewar.screens.GameScreen;
import cat.olivadevelop.atomicspacewar.screens.MainMenuScreen;
import cat.olivadevelop.atomicspacewar.screens.SplashScreen;
import cat.olivadevelop.atomicspacewar.tools.GameLogic;
import cat.olivadevelop.atomicspacewar.tools.GeneralScreen;
import cat.olivadevelop.atomicspacewar.tools.PlayServices;
import cat.olivadevelop.atomicspacewar.tools.ToastAction;
import cat.olivadevelop.atomicspacewar.tools.Xbox;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import static cat.olivadevelop.atomicspacewar.tools.GameLogic.getPlayersTexture;
import static cat.olivadevelop.atomicspacewar.tools.GameLogic.load;

public class AtomicSpaceWarGame extends Game {

    private final float UPDATE_TIME = 1 / 60f;
    float timer;
    private PlayServices playServices;
    private ToastAction toast;
    private Xbox btnsPad;
    public GeneralScreen _splashScreen;
    public GeneralScreen _mainMenuScreen;
    public GeneralScreen _gameScreen;
    private Socket socket;
    public boolean initPlayer = false;
    public boolean playerNotification = false;

    public AtomicSpaceWarGame(PlayServices playServices, ToastAction toast, Xbox btnsPad) {
        this.playServices = playServices;
        this.toast = toast;
        this.btnsPad = btnsPad;
        GameLogic.setScreenHeight(720);
        GameLogic.setScreenWidth(1280);
    }

    @Override
    public void create() {
        load();
        connectSocket();
        configSocketEvent();
        btnsPad.init();
        _splashScreen = new SplashScreen(this);
        _mainMenuScreen = new MainMenuScreen(this);
        _gameScreen = new GameScreen(this, initPlayer, playerNotification);
        setScreen(_splashScreen);
    }

    @Override
    public void render() {
        super.render();
        updateServer(Gdx.graphics.getDeltaTime());
    }

    private void connectSocket() {
        try {
            socket = IO.socket("http://localhost:8080");
            //socket = IO.socket("hl219.dinaserver.com:17605");
            socket.connect();
            Gdx.app.log("Socket", "OK");
        } catch (Exception e) {
            Gdx.app.log("Socket", "Error-> " + e);
        }
    }

    private void configSocketEvent() {
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Gdx.app.log("socketIO", "Connected");
                initPlayer = true;
            }
        }).on("socketID", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    String id = data.getString("id");
                    Gdx.app.log("SocketIO", "My ID: " + id);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Gdx.app.log("SocketIO", "Error getting ID");
                }
            }
        }).on("newPlayer", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    String id = data.getString("id");
                    Gdx.app.log("SocketIO", "New Player connect: " + id);
                    //getToast().show(getString("newPlayerConn"));
                    playerNotification = true;
                    GameScreen.otherPlayers.put(id, new Player(getPlayersTexture("playerShip2_red")));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Gdx.app.log("SocketIO", "Error getting new playerID");
                }
            }
        }).on("playerDisconnected", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    String id = data.getString("id");
                    Gdx.app.log("SocketIO", "Player disconnect: " + id);
                    GameScreen.otherPlayers.get(id).remove();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Gdx.app.log("SocketIO", "Error getting new playerID");
                }
            }
        }).on("playerMoved", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                /* Movemos los actores de otros players cuando se detecte movimiento */
                JSONObject data = (JSONObject) args[0];
                try {
                    String playerID = data.getString("id");
                    Double x = data.getDouble("x");
                    Double y = data.getDouble("y");
                    Double angle = data.getDouble("angle");
                    if (GameScreen.otherPlayers.get(playerID) != null) {
                        GameScreen.otherPlayers.get(playerID).setPosition(x.floatValue(), y.floatValue());
                        GameScreen.otherPlayers.get(playerID).setRotation(angle.floatValue());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Gdx.app.log("SocketIO", "Error getting playerID; moved");
                }
            }
        }).on("getPlayers", new Emitter.Listener() {
            /* Posicionamos los actores de otros players */
            @Override
            public void call(Object... args) {
                JSONArray objects = (JSONArray) args[0];
                try {
                    for (int x = 0; x < objects.length(); x++) {
                        Player p = new Player(getPlayersTexture("playerShip2_red"));
                        p.setPosition(
                                ((Double) objects.getJSONObject(x).getDouble("x")).floatValue(),
                                ((Double) objects.getJSONObject(x).getDouble("y")).floatValue()
                        );
                        GameScreen.otherPlayers.put(objects.getJSONObject(x).getString("id"), p);
                    }
                } catch (JSONException e) {
                }
            }
        });
    }

    public void updateServer(float delta) {
        timer += delta;
        if (timer >= UPDATE_TIME && GameScreen.player != null && GameScreen.player.hasMoved()) {
            JSONObject data = new JSONObject();
            try {
                data.put("x", GameScreen.player.getX());
                data.put("y", GameScreen.player.getY());
                data.put("angle", GameScreen.player.getRotation());
                socket.emit("playerMoved", data);
            } catch (JSONException e) {
                Gdx.app.log("SocketIO", "Error sending update data");
            }
        }
    }

    @Override
    public void dispose() {
    }

    public PlayServices getPlayServices() {
        return playServices;
    }

    public ToastAction getToast() {
        return toast;
    }

    public Xbox getBtnsPad() {
        return btnsPad;
    }
}
