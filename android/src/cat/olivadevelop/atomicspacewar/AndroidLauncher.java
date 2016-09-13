package cat.olivadevelop.atomicspacewar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.multidex.MultiDex;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;

import cat.olivadevelop.atomicspacewar.tools.PlayServices;

public class AndroidLauncher extends AndroidApplication implements PlayServices {
    private GameHelper gameHelper;
    private final static int requestCode = 1;
    private final String INTERSTICIAL = "ca-app-pub-2083012446340886/6932313901";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new AtomicSpaceWarGame(this, new ToastAndroid(this), new XboxAndroid()), config);
        gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
        gameHelper.enableDebugLog(false);

        GameHelper.GameHelperListener gameHelperListener = new GameHelper.GameHelperListener() {
            @Override
            public void onSignInFailed() {
            }

            @Override
            public void onSignInSucceeded() {
            }
        };
        gameHelper.setup(gameHelperListener);
        MultiDex.install(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        gameHelper.onStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        gameHelper.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        gameHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void signIn() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    gameHelper.beginUserInitiatedSignIn();
                }
            });
        } catch (Exception e) {
            Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
        }
    }

    @Override
    public void signOut() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    gameHelper.signOut();
                }
            });
        } catch (Exception e) {
            Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
        }
    }

    @Override
    public void rateGame() {
        String str = "https://play.google.com/store/apps/details?id=cat.olivadevelop.universalwar.android";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));

    }

    @Override
    public void unlockAchievement() {
        Games.Achievements.unlock(gameHelper.getApiClient(),
                getString(R.string.achievement_there_is_no_fear));
    }

    @Override
    public void submitScore(int highScore) {
        if (isSignedIn() == true) {
            Games.Leaderboards.submitScore(gameHelper.getApiClient(),
                    getString(R.string.leaderboard_leaderboard_highest), highScore);
        }
    }

    @Override
    public void showAchievement() {
        if (isSignedIn() == true) {
            startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), requestCode);
        } else {
            signIn();
        }
    }

    @Override
    public void showScore() {
        if (isSignedIn() == true) {
            startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),
                    getString(R.string.leaderboard_leaderboard_highest)), requestCode);
        } else {
            signIn();
        }
    }

    @Override
    public boolean isSignedIn() {
        return gameHelper.isSignedIn();
    }
}
