package com.example.adminpanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import Fragments.AddFragment;
import Fragments.DonationFragment;
import Fragments.EmergencyFragment;
import Fragments.NeedHelpFragment;
import Fragments.ProjectFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;




public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment selectorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_projects:
                        selectorFragment = new ProjectFragment();
                         break;
                    case R.id.nav_donate:
                        selectorFragment = new DonationFragment();
                        break;
                    case R.id.nav_add:
                        selectorFragment = new AddFragment();
                        break;
                    case R.id.nav_emergency:
                        selectorFragment = new EmergencyFragment();
                        break;
                    case R.id.nav_needhelp:
                        selectorFragment = new NeedHelpFragment();
                }
                if (selectorFragment != null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container , selectorFragment).commit();
                }
                return true;
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container , new ProjectFragment()).commit();
    }
}