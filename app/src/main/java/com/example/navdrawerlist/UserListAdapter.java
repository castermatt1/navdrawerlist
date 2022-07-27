package com.example.navdrawerlist;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.navdrawerlist.db.User;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> {

    private MainActivity mainActivity;
    private static List<User> userList;

    public UserListAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setUserList(List<User> userList) {
        UserListAdapter.userList = userList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mainActivity).inflate(R.layout.recycler_row, parent, false);
        return new MyViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.MyViewHolder holder, int position) {
        holder.tvFirstName.setText(this.userList.get(position).firstName);
        holder.tvLastName.setText(this.userList.get(position).lastName);
    }

    @Override
    public int getItemCount() {
        return this.userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvFirstName;
        TextView tvLastName;
        Button btDelete;
        Button btEdit;
        User user;

        public MyViewHolder(View view, UserListAdapter userListAdapter) {
            super(view);
            tvFirstName = view.findViewById(R.id.tvFirstName);
            tvLastName = view.findViewById(R.id.tvLastName);
            btDelete = view.findViewById(R.id.btDeleteUser);
            btDelete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    user = new User();
                    int ID = userList.get(getAbsoluteAdapterPosition()).uid;
                    user.uid = ID;
                    MainActivity.db.userDao().delete(user);
                    userList = MainActivity.db.userDao().getAllUsers();
                    userListAdapter.setUserList(userList);
                }
            });

            btEdit = view.findViewById(R.id.btEdit);
            btEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user = new User();
                    int userID = userList.get(getAbsoluteAdapterPosition()).uid;
                    User user = MainActivity.db.userDao().getUser(userID);

                    MainActivity.intent.putExtra(User.class.getName(), user);

                    mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new FragmentUserUpdate(mainActivity)).commit();
                }
            });

        }

        @Override
        public void onClick(View view) {
        }
    }
}
