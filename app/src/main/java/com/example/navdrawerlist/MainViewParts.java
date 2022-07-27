package com.example.navdrawerlist;

import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

/* prepare view parts of the layout activity_main.xml */
public class MainViewParts {

    public static DrawerLayout drawer;
    private static MainActivity mainActivity;

    public static void attachAll(MainActivity mainActivity) {
        MainViewParts.mainActivity = mainActivity;
        addNavigationDrawer();
        addBottomNavigationView();
    }

    private static void addBottomNavigationView() {
        /* manage bottom navigation */
        BottomNavigationView bottomNav = mainActivity.findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    private static BottomNavigationView.OnNavigationItemSelectedListener navListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new FragmentHome();
                    break;
                case R.id.nav_favorite:
                    selectedFragment = new FragmentFavorites();
                    break;
                case R.id.nav_search:
                    selectedFragment = new FragmentSearch();
                    break;
            }
            mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    selectedFragment).commit();
            return true; // item is selected
        }
    };

    private static void addNavigationDrawer() {
        Toolbar toolbar = mainActivity.findViewById(R.id.toolbar);
        mainActivity.setSupportActionBar(toolbar);

        drawer = mainActivity.findViewById(R.id.drawer_layout);
        NavigationView navigationView = mainActivity.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(mainActivity);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(mainActivity, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (mainActivity.saveInstanceState == null) {
            mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new FragmentHome()).commit();
            /*show first view on app start*/
            navigationView.setCheckedItem(R.id.nav_message);
        }
    }

    public static boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_message:
                mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentMessage()).commit();
                break;
            case R.id.nav_chat:
                mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentChat()).commit();
                break;
            case R.id.nav_profile:
                mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentProfile()).commit();
                break;
            case R.id.nav_list:
                mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentList()).commit();
                break;
            case R.id.nav_listdb:
                mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentListDb(mainActivity)).commit();
                break;
            case R.id.nav_share:
                Toast.makeText(mainActivity, "Share", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_send:
                Toast.makeText(mainActivity, "Send", Toast.LENGTH_SHORT).show();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
