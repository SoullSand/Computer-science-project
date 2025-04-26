package GameClasses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import Settings.Difficulties;
import com.example.computerscienceproject.R;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnReset, btnFlag, btnClick, btnMove, btnReturn;
    private TextView tvFlags, tvTimer;
    private BoardGame boardGame;
    public Handler handler;
    protected TimerThread timer;
    private int time;
    private GameButtons button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        button = GameButtons.CLICK;

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

        highlightSelectedButton();

        updateTimerView();

        timer = new TimerThread(this);
        timer.start();
        timer.startTimer();
    }

    public void resetGame() {
        boardGame.restart();
        time = 0;
        timer.startTimer();
    }

    public void updateFlagCountView(int amount) {
        tvFlags.setText("Bombs: " + amount);
    }

    @Override
    public void onClick(View v) {
        if (btnReset == v) {
            resetGame();
        }
        if (v == btnFlag) {
            setSelectedButton(GameButtons.FLAG);
        }
        if (v == btnClick) {
            setSelectedButton(GameButtons.CLICK);
        }
        if (v == btnMove) {
            setSelectedButton(GameButtons.MOVE);
        }
        if (v == btnReturn) {
            finish();
        }
        highlightSelectedButton();
    }
    private void setSelectedButton(GameButtons button)
    {
        this.button = button;
        boardGame.setSelectedButton(button);
    }
    private void highlightSelectedButton()
    {
        // 0x252222 color code of original button background
        btnFlag.setBackgroundColor(0x252222);
        btnClick.setBackgroundColor(0x252222);
        btnMove.setBackgroundColor(0x252222);
        if (button == GameButtons.FLAG)
        {
            btnFlag.setBackgroundColor(Color.DKGRAY);
        }
        else if (button == GameButtons.CLICK)
        {
            btnClick.setBackgroundColor(Color.DKGRAY);
        }
        else if (button == GameButtons.MOVE)
        {
            btnMove.setBackgroundColor(Color.DKGRAY);
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
    public int getTime(){return time;}
}