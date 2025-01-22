package com.example.computerscienceproject;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CustomDialog extends Dialog implements View.OnClickListener {

    private Button btnYes, btnNo;
    private TextView tvWinLose;

    private Context context;

    public CustomDialog(Context context, String winOrLose) {
        super(context);

        this.context = context;
        setContentView(R.layout.activity_custom_dialog);

        tvWinLose = findViewById(R.id.tvWinLose);
        tvWinLose.setText("You " + winOrLose);

        btnYes = findViewById(R.id.btnYes);
        btnNo = findViewById(R.id.btnNo);
        btnYes.setOnClickListener(this);
        btnNo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        ((GameActivity) context).stopOrStartTimer();
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