package com.goblinstudios.equationeaters;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.goblinstudios.equationeaters.OpenGLRendering.GameScreen;
import com.goblinstudios.equationeaters.OpenGLRendering.OpenGLView;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private OpenGLView openGLView;
    TextView firstTextView;
    EditText firstEditText;
    Button playButton, themeOneBut, themeTwoBut, themeThreeBut, themeFourBut, themeFiveBut;
    boolean internetConnection = false;
    CheckBox randomChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set content view
        setContentView(R.layout.activity_main);
        System.out.println("game assets device width: " + GameAssets.deviceWidth);
        //assign GUI elements
        openGLView = (OpenGLView) this.findViewById(R.id.openGLView);
        firstTextView = (TextView)findViewById(R.id.firstTextView);
        playButton = (Button)findViewById(R.id.playButton);

        themeOneBut = (Button)findViewById(R.id.themeButtonOne);
        themeOneBut.setOnClickListener(this);
        themeTwoBut = (Button)findViewById(R.id.themeButtonTwo);
        themeTwoBut.setOnClickListener(this);
        themeThreeBut = (Button) findViewById(R.id.themeButtonThree);
        themeThreeBut.setOnClickListener(this);
        themeFourBut = (Button) findViewById(R.id.themeButtonFour);
        themeFourBut.setOnClickListener(this);
        themeFiveBut = (Button) findViewById(R.id.themeButtonFive);
        themeFiveBut.setOnClickListener(this);

        randomChecked = (CheckBox) findViewById(R.id.checkBox);
        randomChecked.setOnClickListener(this);

        //set Visibility of GUI elements
        firstTextView.setVisibility(VISIBLE);
        playButton.setVisibility(VISIBLE);
        playButton.setOnClickListener(this);

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        System.out.println("Current Android Version : " + android.os.Build.VERSION.SDK_INT);
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
        if (v.getId() == themeOneBut.getId()) {
            GameAssets.themeSelected = 1;
            clearButtons();
            themeOneBut.setBackgroundResource(R.drawable.dragoncoloricon);
            randomChecked.setChecked(false);
        }
        if (v.getId() == themeTwoBut.getId()) {
            GameAssets.themeSelected = 2;
            clearButtons();
            themeTwoBut.setBackgroundResource(R.drawable.krakencoloricon);
            randomChecked.setChecked(false);
        }
        if (v.getId() == themeThreeBut.getId()) {
            GameAssets.themeSelected = 3;
            clearButtons();
            themeThreeBut.setBackgroundResource(R.drawable.pegasuscoloricon);
            randomChecked.setChecked(false);
        }
        if (v.getId() == themeFourBut.getId()) {
            clearButtons();
            GameAssets.themeSelected = 4;
            randomChecked.setChecked(false);
        }
        if (v.getId() == themeFiveBut.getId()) {
            clearButtons();
            GameAssets.themeSelected = 5;
            randomChecked.setChecked(false);
        }
        if (v.getId() == randomChecked.getId()) {
            if (randomChecked.isChecked()) {
                clearButtons();
            }
        }



    }

    public void clearButtons() {
        themeOneBut.setBackgroundResource(R.drawable.dragonicon);
        themeTwoBut.setBackgroundResource(R.drawable.krakenicon);
        themeThreeBut.setBackgroundResource(R.drawable.pegasusicon);
        themeFourBut.setBackgroundResource(R.drawable.lockedicon);
        themeFiveBut.setBackgroundResource(R.drawable.lockedicon);
    }

    public void loadGame() {
        System.out.println("load game");
        GameAssets.gameState = GameAssets.GameState.gameScreen;
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
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
