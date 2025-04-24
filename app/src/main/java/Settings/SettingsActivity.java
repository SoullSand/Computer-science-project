package Settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.computerscienceproject.R;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    RadioGroup radioGroup;
    RadioButton rbSelected;
    RadioButton rbEasy, rbMedium, rbHard;
    Button btnReturn;
    TextToSpeech tts;

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

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                tts.setLanguage(Locale.US);
            }
        });

        Intent intent = getIntent();
        Difficulties difficulty = Difficulties.valueOf(intent.getStringExtra("Difficulty"));
        String selectedDifficulty = String.valueOf(difficulty);
        setDefaultCheckButton(selectedDifficulty);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        rbSelected = findViewById(checkedId);
        Intent intent = new Intent();
        /* tts.speak says the text out loud,
        first parameter is for adding a text to the queue of said text
         instead of replacing the whole queue. */
        if (rbSelected == rbEasy) {
            intent.putExtra("Difficulty", Difficulties.EASY.toString());
            tts.speak("Difficulty easy", TextToSpeech.QUEUE_FLUSH, null, null);
        } else if (rbSelected == rbMedium) {
            intent.putExtra("Difficulty", Difficulties.MEDIUM.toString());
            tts.speak("Difficulty medium", TextToSpeech.QUEUE_FLUSH, null, null);
        } else if (rbSelected == rbHard) {
            intent.putExtra("Difficulty", Difficulties.HARD.toString());
            tts.speak("Difficulty hard", TextToSpeech.QUEUE_FLUSH, null, null);
        }
        setResult(RESULT_OK, intent);
    }

    @Override
    public void onClick(View v) {
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