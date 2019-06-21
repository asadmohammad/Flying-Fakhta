package com.mohammadasad.flyingfakhta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndGameActivity extends AppCompatActivity {
    private Button startGameAgain;
    private TextView displayScore;
    private String score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        score = getIntent().getExtras().get("score").toString();


        startGameAgain = (Button) findViewById(R.id.playAgain);
        displayScore = (TextView) findViewById(R.id.scoreDisplay);

        startGameAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(EndGameActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });


        displayScore.setText("Score = " + score);
    }
}
