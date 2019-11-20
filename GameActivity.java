package com.goblinstudios.equationeaters;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
    public static EditText levelText;
    public static EditText scoreText;
    public static EditText lunchNumberText;
    public static EditText lunchNumberNum;
    public static EditText gameEntryText;
    public static EditText gameEntryNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GameAssets.setActivityContext(this);

        //set content view
        setContentView(R.layout.activity_game);

        levelText = (EditText) this.findViewById(R.id.levelText);
        scoreText = (EditText) this.findViewById(R.id.scoreText);
        lunchNumberText = (EditText) this.findViewById(R.id.lunchNumberText);
        lunchNumberNum = (EditText) this.findViewById(R.id.lunchNumberNum);
        gameEntryText = (EditText) this.findViewById(R.id.gameEntryText);
        gameEntryNum = (EditText) this.findViewById(R.id.startingNum);

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

        lunchNumberNum.setText(GameScreen.targetSum + "");
        GameScreen.closeGameOverGUI();

    }

    @Override
    protected void onResume() {
        super.onResume();
        openGLView.onResume();
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

    public void updateButton(int resourceID, String text, boolean visibility) {
        Button changingButton = (Button) findViewById(resourceID);
        changingButton.setText(text);
        if (visibility == true) {
            changingButton.setVisibility(VISIBLE);
        } else {
            changingButton.setVisibility(INVISIBLE);
        }
    }

    public void updateImage(int resourceID, boolean visibility, int resourceImage) {
        ImageView changingView = (ImageView) findViewById(resourceID);
        if (visibility == true) {
            changingView.setVisibility(VISIBLE);
        } else {
            changingView.setVisibility(INVISIBLE);
        }
        //changingView.setBackgroundResource(resourceImage);
    }

    public void updateText(int resourceID, String text, boolean visibility) {
        EditText changingText = (EditText) findViewById(resourceID);
        System.out.println("setting text : " + text);
        changingText.setText(text);
        if (visibility == true) {
            changingText.setVisibility(VISIBLE);
        } else {
            changingText.setVisibility(INVISIBLE);
        }
    }

    public void updateProgressBar(int resourceID, boolean visibility) {
        ProgressBar progressBar = (ProgressBar) findViewById(resourceID);
        if (visibility == true) {
            progressBar.setVisibility(VISIBLE);
        } else {
            progressBar.setVisibility(INVISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}