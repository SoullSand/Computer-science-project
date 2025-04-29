package UserStats;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.computerscienceproject.FBModule;
import com.example.computerscienceproject.R;

import UserAuth.User;

public class StatsActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvUsername, tvEasyRecord, tvMediumRecord, tvHardRecord;
    Button btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        tvUsername = findViewById(R.id.tvUsername);
        tvEasyRecord = findViewById(R.id.tvEasyRecord);
        tvMediumRecord = findViewById(R.id.tvMediumRecord);
        tvHardRecord = findViewById(R.id.tvHardRecord);

        btnReturn = findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(this);
    }

    public void SetStatsText(User user) {
        tvUsername.setText("Username: " + user.getName());

        if (user.getEasyRecord() > 999) {tvEasyRecord.setText("Easy record: none");}
        else {tvEasyRecord.setText("Easy record: " + user.getEasyRecord());}

        if (user.getMediumRecord() > 999) {tvMediumRecord.setText("Medium record: none");}
        else {tvMediumRecord.setText("Medium record: " + user.getMediumRecord());}

        if (user.getHardRecord() > 999) {tvHardRecord.setText("Hard record: none");}
        else {tvHardRecord.setText("Hard record: " + user.getHardRecord());}
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}