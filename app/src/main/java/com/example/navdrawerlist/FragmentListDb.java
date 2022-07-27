package com.example.navdrawerlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.navdrawerlist.db.User;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class FragmentListDb extends Fragment {
    private static UserListAdapter userListAdapter;
    private MainActivity mainActivity;
    private View view;

    public FragmentListDb(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_list_db, container, false);

//        editUser();

        addNewUser();

        initRecyclerView();

        loadUserList();

        return view;
    }

    private void addNewUser(){
        Button addNewUserButton = view.findViewById(R.id.addNewUserButton);
        addNewUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentUserAdd(mainActivity)).commit();
            }
        });
    }

//    private void editUser(){
//        Button updateUserButton = view.findViewById(R.id.addNewUserButton);
//        updateUserButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new FragmentUserUpdate(mainActivity)).commit();
//            }
//        });
//    }

    private void initRecyclerView() {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mainActivity, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        userListAdapter = new UserListAdapter(mainActivity);
        recyclerView.setAdapter(userListAdapter);
    }

    public static void loadUserList() {
        List<User> userList = MainActivity.db.userDao().getAllUsers();
        FragmentListDb.userListAdapter.setUserList(userList);
    }
}