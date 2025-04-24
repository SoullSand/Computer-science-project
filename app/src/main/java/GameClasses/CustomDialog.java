package GameClasses;

import android.app.Dialog;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.computerscienceproject.R;

public class CustomDialog extends Dialog implements View.OnClickListener {

    private Button btnYes, btnNo;
    private TextView tvWinLose, tvTimer;
    private Context context;

    public CustomDialog(Context context, String winOrLose) {
        super(context);

        this.context = context;
        setContentView(R.layout.activity_custom_dialog);

        tvTimer = findViewById(R.id.tvTimer);
        tvWinLose = findViewById(R.id.tvWinLose);
        tvWinLose.setText("You " + winOrLose);

        btnYes = findViewById(R.id.btnYes);
        btnNo = findViewById(R.id.btnNo);
        btnYes.setOnClickListener(this);
        btnNo.setOnClickListener(this);

        new CountDownTimer(15000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvTimer.setText("seconds remaining: " + millisUntilFinished / 1000);
                // logic to set the EditText could go here
            }
            public void onFinish() {
                ((GameActivity) context).finish();
            }

        }.start();
    }

    @Override
    public void onClick(View view) {
        ((GameActivity) context).timer.stopTimer();
        if (btnYes == view) {
            ((GameActivity) context).resetGame();
            super.dismiss();
        } else if (btnNo == view) {
            ((GameActivity) context).finish();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        ((GameActivity) context).finish();
    }
}