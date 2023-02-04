package com.example.canrecycle;

import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import com.example.canrecycle.databinding.FragmentSummaryBinding;

public class SummaryFragment extends Fragment {

    private FragmentSummaryBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSummaryBinding.inflate(inflater, container, false);

        TransitionInflater transitionInflater = TransitionInflater.from(getContext());
        //setExitTransition(transitionInflater.inflateTransition(R.transition.fade));
        setEnterTransition(transitionInflater.inflateTransition(R.transition.slide_right));

        binding.paperContainer.setOnClickListener(view -> iconSelected(view, 0));
        binding.plasticContainer.setOnClickListener(view -> iconSelected(view, 1));
        binding.glassContainer.setOnClickListener(view -> iconSelected(view, 2));
        binding.metalContainer.setOnClickListener(view -> iconSelected(view, 3));
        binding.othersContainer.setOnClickListener(view -> iconSelected(view, 4));

        return binding.getRoot();

    }

    private void iconSelected(View view, int iconSelected) {
        ((MainActivity)getActivity()).iconClicked(view, iconSelected);
    }
}
