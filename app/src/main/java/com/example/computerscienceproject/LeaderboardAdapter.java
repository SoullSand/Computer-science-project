package com.example.computerscienceproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.UserViewHolder> {

    //this context we will use to inflate the layout
    private Context context;

    //storing all the records in a list
    private List<User> recordsList;

    public LeaderboardAdapter(Context context, List<User> recordsList) {
        this.context = context;
        this.recordsList = recordsList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_layout, null);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        // read eachtime from arraylist object and write to listview Item
        //getting the record of the specified position
        User user = recordsList.get(position);
        holder.tvName.setText("#" + (position+1) + " Name: " + user.getName());
        if (user.getEasyRecord() > 999)
        {
            holder.tvRecord.setText("Record: none");
        }
        else
        {
            holder.tvRecord.setText("Record: " + user.getEasyRecord());
        }

    }

    @Override
    public int getItemCount() {
        return recordsList.size();
    }


    public class UserViewHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvRecord;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            // TODO:
            tvName = itemView.findViewById(R.id.tvName);
            tvRecord = itemView.findViewById(R.id.tvScore);
        }
    }
}
