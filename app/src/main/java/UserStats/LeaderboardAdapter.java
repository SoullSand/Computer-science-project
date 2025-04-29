package UserStats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.computerscienceproject.R;
import com.google.firebase.database.Query;

import java.util.List;

import UserAuth.User;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.UserViewHolder> {

    //this context we will use to inflate the layout
    private Context context;

    //storing all the records in a list
    private List<User> recordsList;

    public LeaderboardAdapter(Context context, List<User> recordsList) {
        this.context = context;
        this.recordsList = recordsList;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvRecord;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvRecord = itemView.findViewById(R.id.tvScore);
        }
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        //inflating and returning our view holder
        // in other words it creates a new line in the leaderboards to type a user into
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_leaderboard_adapter, null);
        return new UserViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        // read each user from arraylist and write the stats in the leaderboard lines
        User user = recordsList.get(position);
        holder.tvName.setText("#" + (position+1) + "   " + user.getName());
        SetRecordText(holder, user);
    }
    private void SetRecordText(UserViewHolder holder, User user)
    {
        if (user.getEasyRecord() > 999)
        {
            holder.tvRecord.setText("Easy: none");
        }
        else
        {
            holder.tvRecord.setText("Easy: ");
            holder.tvRecord.append(String.valueOf(user.getEasyRecord()));

        }
        if (user.getMediumRecord() > 999)
        {
            holder.tvRecord.append("   Medium: none");
        }
        else
        {
            holder.tvRecord.append("   Medium: ");
            holder.tvRecord.append(String.valueOf(user.getMediumRecord()));

        }
        if (user.getHardRecord() > 999)
        {
            holder.tvRecord.append("   Hard: none");
        }
        else
        {
            holder.tvRecord.append("   Hard: ");
            holder.tvRecord.append(String.valueOf(user.getHardRecord()));

        }
    }

    @Override
    public int getItemCount() {
        return recordsList.size();
    }
}
