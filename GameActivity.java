package com.goblinstudios.equationeaters;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.goblinstudios.equationeaters.OpenGLRendering.GameController;
import com.goblinstudios.equationeaters.OpenGLRendering.GameScreen;
import com.goblinstudios.equationeaters.OpenGLRendering.OpenGLView;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private OpenGLView openGLView;
    TextView firstTextView;
    EditText firstEditText;
    Button playButton;
    boolean internetConnection = false;
    GameController gController;
    public static GameModel gModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set content view
        setContentView(R.layout.activity_game);
        System.out.println("game assets device width: " + GameAssets.deviceWidth);
        //assign GUI elements
        openGLView = (OpenGLView) this.findViewById(R.id.gameGLView);

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

 //       gModel = new GameModel();

        gController = new GameController(openGLView);
        gController.modelContext = gModel;
        gController.setChar();
        openGLView.setOnTouchListener(gController);
        openGLView.gModel = gController.modelContext;
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("On Resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        openGLView.onPause();
        System.out.println("On Pause");
    }

    public void onClick(View v) {
        System.out.println("View Clicked: " + v.getId() + " --- " + playButton.getId());
        if (v.getId() == playButton.getId()) {
            loadGame();
        }
    }

    public void loadGame() {
        System.out.println("load game");
        GameAssets.gameState = GameAssets.GameState.gameScreen;
        OpenGLView.masterRenderer = new GameScreen(this, GameAssets.deviceWidth, GameAssets.deviceHeight);
        GameAssets.openGLView.setRenderer(OpenGLView.masterRenderer);
    }

}