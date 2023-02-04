package com.example.canrecycle;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canrecycle.databinding.FragmentListBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ListFragment extends Fragment {
    private FragmentListBinding binding;

    private boolean isAscending = true;
    private MenuItem clickedItem;

    @Override
    public View onCreateView (@NonNull LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater, container, false);

        RecyclerView recyclerView = binding.recyclerViewList;

        ListAdapter listAdapter = new ListAdapter(getResources().getStringArray(R.array.item_list));
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        listAdapter.setOnItemClickListener((view, data) -> ((MainActivity)getActivity()).itemRowClicked(data));

        itemNamePressed(listAdapter);

        if (clickedItem != null) {
            itemTypeMenuPressed(listAdapter, clickedItem);
        }

        binding.view2.setOnClickListener(view -> {
            isAscending = !isAscending;
            itemNamePressed(listAdapter);
        });

        binding.view3.setOnClickListener(view -> {
            PopupMenu popup = new PopupMenu(getContext(), view);
            popup.getMenuInflater().inflate(R.menu.filter_material_menu, popup.getMenu());
            popup.getMenu().setGroupDividerEnabled(true);

            popup.setOnMenuItemClickListener(clickedItem -> itemTypeMenuPressed(listAdapter, clickedItem));

            popup.show();
        });

        TransitionInflater transitionInflater = TransitionInflater.from(getContext());
        //setExitTransition(transitionInflater.inflateTransition(R.transition.fade));
        setEnterTransition(transitionInflater.inflateTransition(R.transition.slide_right));

        return binding.getRoot();
    }

    private void itemNamePressed(ListAdapter listAdapter) {
        binding.imageView.setScaleY(isAscending ? 1f : -1f);
        listAdapter.itemNamePressed(isAscending);
    }

    private boolean itemTypeMenuPressed(ListAdapter listAdapter, MenuItem clickedItem) {
        this.clickedItem = clickedItem;

        listAdapter.itemTypePressed(clickedItem);

        ObjectAnimator animator = ObjectAnimator.ofFloat(binding.filteredMaterialText, "translationX", 135f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(binding.itemTypeHeader, "translationX", -60f);
        ObjectAnimator animatorRev = ObjectAnimator.ofFloat(binding.filteredMaterialText, "translationX", 0f);
        ObjectAnimator animator2Rev = ObjectAnimator.ofFloat(binding.itemTypeHeader, "translationX", 0f);
        int duration = 500;
        animator.setDuration(duration);
        animator2.setDuration(duration);
        animatorRev.setDuration(duration);
        animator2Rev.setDuration(duration);

        TypedValue value = new TypedValue();
        getContext().getTheme().resolveAttribute(com.google.android.material.R.attr.colorPrimaryVariant, value, true);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                binding.itemTypeHeader.setBackgroundColor(Color.TRANSPARENT);
            }
        });
        animatorRev.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                binding.itemTypeHeader.setBackgroundColor(Color.TRANSPARENT);
                binding.filteredMaterialText.setText("");
                binding.imageView2.setVisibility(View.VISIBLE);
            }
        });

        if (!clickedItem.toString().equals("None")) {
            binding.filteredMaterialText.setText(": " + clickedItem);
            binding.imageView2.setVisibility(View.INVISIBLE);

            binding.itemTypeHeader.setBackgroundColor(value.data);

            animator.start();
            animator2.start();
        }
        else {
            binding.itemTypeHeader.setBackgroundColor(value.data);

            animatorRev.start();
            animator2Rev.start();
        }
        return true;
    }

    /*@Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(null, "list fragment saved");
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
