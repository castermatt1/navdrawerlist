package com.example.navdrawerlist;

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

public class FragmentUserAdd extends Fragment {

    private MainActivity mainActivity;
    private EditText firstNameInput;
    private EditText lastNameInput;
    private Button saveButton;
    private Button cancelButton;

    public FragmentUserAdd(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user, container, false);
        firstNameInput = view.findViewById(R.id.firstname);
        lastNameInput = view.findViewById(R.id.lastname);
        saveButton = view.findViewById(R.id.saveButton);
        cancelButton = view.findViewById(R.id.cancelButton);
        saveButtonOnclick();
        cancelButtonOnclick();
        firstNameInput.requestFocus(); // set cursor to firstname
        mainActivity.keyboard(MainActivity.SHOW_KEYBOARD);
        return view;
    }

    private void saveButtonOnclick() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewUser(
                        firstNameInput.getText().toString(),
                        lastNameInput.getText().toString());
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
