package com.example.canrecycle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.ClipData;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;

import com.example.canrecycle.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    public boolean isFromLeft;

    FragmentManager manager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, CheckFragment.class, null)
                    .commit();
        }

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.check_page) {
                selectedFragment = new CheckFragment();
                isFromLeft = false;
            }
            if (item.getItemId() == R.id.search_page) {
                selectedFragment = new SearchFragment();
            }
            if (item.getItemId() == R.id.list_page) {
                selectedFragment = new ListFragment();
                isFromLeft = true;
            }

            /*String selectedFragmentName = selectedFragment.getClass().getName();
            Fragment foundFragment = manager.findFragmentByTag(selectedFragmentName);

            if (foundFragment == null) {
                manager.beginTransaction()
                        .replace(R.id.fragment_container_view, selectedFragment, selectedFragmentName)
                        .addToBackStack(selectedFragmentName)
                        .commit();
            }
            else {
                if (!foundFragment.isAdded())
                    manager.beginTransaction()
                            .replace(R.id.fragment_container_view, foundFragment, selectedFragmentName)
                            .commit();
            }*/

            manager.beginTransaction()
                    .replace(R.id.fragment_container_view, selectedFragment)
                    .commit();

            return true;
        });
    }

    public void itemRowClicked(String data) {
        Bundle bundle = new Bundle();
        bundle.putString("DATA", data);

        Fragment fragment = new ItemFragment();
        fragment.setArguments(bundle);

        manager.beginTransaction()
                .replace(R.id.fragment_container_view, fragment)
                .commit();
    }

    public void summaryClicked() {
        manager.beginTransaction()
                .replace(R.id.fragment_container_view, new SummaryFragment())
                .commit();
    }

    public void iconClicked(View view, int iconSelected) {
        Bundle bundle = new Bundle();
        bundle.putInt("ICONSELECTED", iconSelected);

        Fragment fragment = new SummaryFragment2();
        fragment.setArguments(bundle);

        manager.beginTransaction()
                .replace(R.id.fragment_container_view, fragment)
                .commit();
    }

/*
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);

        if (currentFragment instanceof CheckFragment) {
            binding.bottomNavigation.setSelectedItemId(R.id.check_page);
        }
        if (currentFragment instanceof SearchFragment) {
            binding.bottomNavigation.setSelectedItemId(R.id.search_page);
        }
        if (currentFragment instanceof ListFragment) {
            binding.bottomNavigation.setSelectedItemId(R.id.list_page);
        }
    }*/
    /*
    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);

        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
            //additional code
        }
        else {
            getSupportFragmentManager().popBackStack();
            if (currentFragment instanceof CheckFragment) {
                binding.bottomNavigation.setSelectedItemId(R.id.check_page);
            }
            if (currentFragment instanceof SearchFragment) {
                binding.bottomNavigation.setSelectedItemId(R.id.search_page);
            }
            if (currentFragment instanceof ListFragment) {
                binding.bottomNavigation.setSelectedItemId(R.id.list_page);
            }
        }

    }*/
}