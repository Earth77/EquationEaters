package com.goblinstudios.equationeaters;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    Button playButton;
    boolean internetConnection = false;

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

        //set Visibility of GUI elements
        firstTextView.setVisibility(VISIBLE);
        playButton.setVisibility(VISIBLE);
        playButton.setOnClickListener(this);

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
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
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

}
