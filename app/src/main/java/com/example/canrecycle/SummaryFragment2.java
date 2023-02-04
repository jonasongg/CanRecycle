package com.example.canrecycle;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.media.Image;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canrecycle.databinding.FragmentSummary2Binding;
import com.example.canrecycle.databinding.FragmentSummaryBinding;

public class SummaryFragment2 extends Fragment {

    private ImageView[] buttons = new ImageView[5];
    private ImageView previouslySelected;

    private FragmentSummary2Binding binding;
    private float[] colorMatrix = {
            0.33f, 0.33f, 0.33f, 0, 0,
            0.33f, 0.33f, 0.33f, 0, 0,
            0.33f, 0.33f, 0.33f, 0, 0,
            0, 0, 0, 0.4f, 0
    };

    private ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);

    private int iconSelected = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSummary2Binding.inflate(inflater, container, false);

        iconSelected = getArguments().getInt("ICONSELECTED");

        previouslySelected = binding.paperIconSmall;

        buttons = new ImageView[]{
                binding.paperIconSmall,
                binding.plasticIconSmall,
                binding.glassIconSmall,
                binding.metalIconSmall,
                binding.othersIconSmall,
        };

        String[] materials = new String[] {
                getString(R.string.paper),
                getString(R.string.plastic),
                getString(R.string.glass),
                getString(R.string.metal),
                getString(R.string.others),
        };

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setColorFilter(filter);
            int finalI = i;
            buttons[i].setOnClickListener(view -> iconSelected(finalI));
        }

        binding.paperIconSmall.clearColorFilter();


        RecyclerView recyclerView = binding.headerRecyclerview;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        HeaderAdapter headerAdapter = new HeaderAdapter(materials);
        recyclerView.setAdapter(headerAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        float margin18dp = (float)(((ViewGroup.MarginLayoutParams)binding.plasticSummary.getLayoutParams()).topMargin);

        binding.bodyScrollview.setOnScrollChangeListener((view, i, i1, i2, i3) -> {
            Pair pair = getFirstVisibleItem((ConstraintLayout)binding.bodyScrollview.getChildAt(0), binding.bodyScrollview, margin18dp);
            //Log.d(null, Integer.toString(pair.child));

            int buttonIndex = (pair.child + 1) / 2;

            if (pair.child % 2 != 0) {
                ((LinearLayoutManager)recyclerView.getLayoutManager()).scrollToPositionWithOffset(buttonIndex, (int)pair.offset);
            }
            else {
                ((LinearLayoutManager)recyclerView.getLayoutManager()).scrollToPositionWithOffset(pair.child / 2, 0);
            }

            if (buttons[(pair.child + 1) / 2] != previouslySelected) {
                setColour((pair.child + 1) / 2);
            }
        });


        TransitionInflater transitionInflater = TransitionInflater.from(requireContext());
        //setExitTransition(transitionInflater.inflateTransition(R.transition.fade));
        setEnterTransition(transitionInflater.inflateTransition(R.transition.slide_right));

        return binding.getRoot();

    }

    @Override
    public void onStart() {
        super.onStart();

        iconSelected(iconSelected);
    }

    private void iconSelected(int iconIndex) {
        View viewToScrollTo = ((ConstraintLayout) binding.bodyScrollview.getChildAt(0)).getChildAt(iconIndex * 2 + 2);
        binding.bodyScrollview.post(() -> binding.bodyScrollview.smoothScrollTo(0,
                (int)viewToScrollTo.getY()
                        //- ((ViewGroup.MarginLayoutParams)viewToScrollTo.getLayoutParams()).topMargin
                ));
    }

    private void setColour(int iconIndex) {
        ImageView view = buttons[iconIndex];

        previouslySelected.setColorFilter(filter);
        view.clearColorFilter();
        previouslySelected = view;
    }

    private Pair getFirstVisibleItem(ConstraintLayout constraintLayout, ScrollView scrollView, float margin) {
        for (int i = 2; i < constraintLayout.getChildCount(); i++) {
            View view = constraintLayout.getChildAt(i);
            if (view.getY() + view.getHeight() + margin >= scrollView.getScrollY()) {
                return new Pair(i - 2, view.getHeight() - scrollView.getScrollY() + view.getY() + margin);
            }
        }
        return new Pair(0, 0f);
    }

    private class Pair {
        int child;
        float offset;

        Pair(int child, float offset) {
            this.child = child;
            this.offset = offset;
        }
    }
}
