package com.example.computerscienceproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnReset, btnFlag, btnClick, btnMove, btnReturn;
    private TextView tvFlags, tvTimer;
    private BoardGame boardGame;
    public Handler handler;
    private TimerThread timer;
    private int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);



        LinearLayout canvasLayout = findViewById(R.id.canvasLL);

        Intent intent = getIntent();
        Difficulties difficulty = Difficulties.valueOf(intent.getStringExtra("Difficulty"));

        tvFlags = findViewById(R.id.tvFlags);
        tvTimer = findViewById(R.id.tvTimer);

        boardGame = new BoardGame(this, difficulty);
        canvasLayout.addView(boardGame);

        btnReset = findViewById(R.id.btnReset);
        btnFlag = findViewById(R.id.btnFlag);
        btnClick = findViewById(R.id.btnClick);
        btnMove = findViewById(R.id.btnMove);
        btnReturn = findViewById(R.id.btnReturn);

        btnReset.setOnClickListener(this);
        btnFlag.setOnClickListener(this);
        btnClick.setOnClickListener(this);
        btnMove.setOnClickListener(this);
        btnReturn.setOnClickListener(this);

        updateTimerView();

        timer = new TimerThread(this);
        timer.start();
    }

    public void resetGame() {
        boardGame.restart();
        time = 0;
    }

    public void updateFlagCountView(int amount) {
        tvFlags.setText("Flags: " + amount);
    }

    @Override
    public void onClick(View v) {
        if (btnReset == v) {
            resetGame();
        }
        if (v == btnFlag) {
            boardGame.setSelectedButton(GameButtons.FLAG);
        }
        if (v == btnClick) {
            boardGame.setSelectedButton(GameButtons.CLICK);
        }
        if (v == btnMove) {
            boardGame.setSelectedButton(GameButtons.MOVE);
        }
        if (v == btnReturn) {
            finish();
        }
    }

    public void updateTimerView() {
        time = 0;
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                time++;
                if (time < 10)
                {
                    tvTimer.setText("00" + String.valueOf(time));
                }
                else if (time < 100)
                {
                    tvTimer.setText("0" + String.valueOf(time));
                }
                else if (time < 999) {
                    tvTimer.setText(String.valueOf(time));
                }
                else if (time > 999) {
                    tvTimer.setText("999");
                }
                return false;
            }
        });
    }
    public void resetTimer()
    {
        time = 0;
    }
    public void stopOrStartTimer()
    {
        timer.stopOrStartTimer();
    }

    public int getTime(){return time;}
}