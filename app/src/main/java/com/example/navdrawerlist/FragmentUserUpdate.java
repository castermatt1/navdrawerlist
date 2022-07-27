package com.example.navdrawerlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.navdrawerlist.db.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentUserUpdate extends Fragment {

    private MainActivity mainActivity;
    private EditText firstname;
    private EditText lastname;
    private Button saveButton;
    private Button cancelButton;
    private Intent intent;
    private User user;

    public FragmentUserUpdate(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user, container, false);
        firstname = view.findViewById(R.id.firstname);
        lastname = view.findViewById(R.id.lastname);
        saveButton = view.findViewById(R.id.saveButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        /*get selected user from recycle view*/
        intent = MainActivity.intent;
        user = (User) intent.getSerializableExtra(User.class.getName());
        firstname.setText(user.firstName);
        lastname.setText(user.lastName);

        updateButtonOnclick();
        cancelButtonOnclick();
        firstname.requestFocus(); // set cursor to firstname
        mainActivity.keyboard(MainActivity.SHOW_KEYBOARD);
        return view;
    }

    private void updateButtonOnclick() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.firstName = firstname.getText().toString();
                user.lastName = lastname.getText().toString();
                MainActivity.db.userDao().update(user);

                FragmentListDb.loadUserList();
                backToUserList();
            }
        });
    }

    private void cancelButtonOnclick() {
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToUserList();
            }
        });
    }

    private void backToUserList() {
        mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new FragmentListDb(mainActivity)).commit();
        mainActivity.keyboard(MainActivity.HIDE_KEYBOARD);
    }

    private void saveNewUser(String firstName, String lastName) {
        User user = new User();
        user.firstName = firstName;
        user.lastName = lastName;
        MainActivity.db.userDao().insertUser(user);
    }

}
