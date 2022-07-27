package com.example.navdrawerlist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.navdrawerlist.db.AppDatabase;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static AppDatabase db;
    public Bundle saveInstanceState;
    public static Intent intent;
    public static final boolean SHOW_KEYBOARD = false;
    public static final boolean HIDE_KEYBOARD = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.saveInstanceState = savedInstanceState;

        intent = new Intent(this, MainActivity.class);

        /* at first only one time connect to db */
        db = AppDatabase.getDbInstance(this.getApplicationContext());

        MainViewParts.attachAll(this);

        /* click Home when app started */
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new FragmentHome()).commit();
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBackPressed() {
        if (MainViewParts.drawer.isDrawerOpen(GravityCompat.START)) {
            MainViewParts.drawer.closeDrawer(GravityCompat.START);
        } else if (this.findViewById(R.layout.fragment_user) != null) { // go back to list users NOT WORK !!!
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new FragmentListDb(this)).commit();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return MainViewParts.onNavigationItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 100) {
            FragmentListDb.loadUserList();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void keyboard(boolean show) {
        InputMethodManager imm;
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        if (show) {
            imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } else {
            imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }

}