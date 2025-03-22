package UserStats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import Settings.Difficulties;
import com.example.computerscienceproject.FBModule;
import com.example.computerscienceproject.R;

import java.util.ArrayList;

import UserAuth.User;

public class LeaderboardActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnReturn, btnEasy, btnMedium, btnHard;
    RecyclerView rvEasy;
    LeaderboardAdapter leaderboardAdapter;
    FBModule fbModule;
    ArrayList<User> allUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        rvEasy = findViewById(R.id.rvEasy);
        rvEasy.setLayoutManager(new LinearLayoutManager(this));

        btnReturn = findViewById(R.id.btnReturn);
        btnEasy = findViewById(R.id.btnEasy);
        btnMedium = findViewById(R.id.btnMedium);
        btnHard = findViewById(R.id.btnHard);

        btnReturn.setOnClickListener(this);
        btnEasy.setOnClickListener(this);
        btnMedium.setOnClickListener(this);
        btnHard.setOnClickListener(this);

        fbModule = new FBModule(this);;
        allUsers = new ArrayList<User>();

        leaderboardAdapter = new LeaderboardAdapter(this, allUsers);
        rvEasy.setAdapter(leaderboardAdapter);
    }
    public void AddUserToList(User user)
    {
        allUsers.add(user);
        leaderboardAdapter.notifyDataSetChanged();
    }
    public void ResetUserList()
    {
        allUsers.clear();
    }


    @Override
    public void onClick(View v) {
        allUsers.clear();
        if (v == btnEasy)
        {
            fbModule.GetUsersFromFB(Difficulties.EASY);
        }
        if (v == btnMedium)
        {
            fbModule.GetUsersFromFB(Difficulties.MEDIUM);
        }
        if (v == btnHard)
        {
            fbModule.GetUsersFromFB(Difficulties.HARD);
        }
        if (v == btnReturn)
        {
            finish();
        }
    }
}