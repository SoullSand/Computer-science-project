package com.example.computerscienceproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SettingsActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    RadioGroup radioGroup;
    RadioButton rbSelected;
    RadioButton rbEasy, rbMedium, rbHard;
    Button btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(this);

        rbEasy = findViewById(R.id.radioButtonEasy);
        rbMedium = findViewById(R.id.radioButtonMedium);
        rbHard = findViewById(R.id.radioButtonHard);

        btnReturn = findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(this);

        Intent intent = getIntent();
        Difficulties difficulty = Difficulties.valueOf(intent.getStringExtra("Difficulty"));
        String selectedDifficulty = String.valueOf(difficulty);
        setDefaultCheckButton(selectedDifficulty);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        rbSelected = findViewById(checkedId);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        if (rbSelected == rbEasy) {
            intent.putExtra("Difficulty", Difficulties.EASY.toString());
        } else if (rbSelected == rbMedium) {
            intent.putExtra("Difficulty", Difficulties.MEDIUM.toString());
        } else if (rbSelected == rbHard) {
            intent.putExtra("Difficulty", Difficulties.HARD.toString());
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    private void setDefaultCheckButton(String str) {
        if (str.equals(Difficulties.EASY.toString())) {
            rbEasy.setChecked(true);
        } else if (str.equals(Difficulties.MEDIUM.toString())) {
            rbMedium.setChecked(true);
        } else if (str.equals(Difficulties.HARD.toString())) {
            rbHard.setChecked(true);
        }
    }
}