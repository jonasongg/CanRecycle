package com.example.canrecycle;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.transition.TransitionInflater;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import com.example.canrecycle.databinding.FragmentCheckBinding;

public class CheckFragment extends Fragment {
    private FragmentCheckBinding binding;

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = FragmentCheckBinding.inflate(inflater, container, false);

        TypedValue value = new TypedValue();
        getContext().getTheme().resolveAttribute(com.google.android.material.R.attr.colorPrimary, value, true);
        ForegroundColorSpan green = new ForegroundColorSpan(value.data);
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);

        SpannableString title1 = new SpannableString(binding.checkTitle1.getText());
        title1.setSpan(green, 8, title1.length(), 0);
        binding.checkTitle1.setText(title1);

        SpannableString title2 = new SpannableString(binding.checkTitle2.getText());
        title2.setSpan(green, 6, title2.length(), 0);
        binding.checkTitle2.setText(title2);

        SpannableString body1 = new SpannableString(binding.checkBody1.getText());
        body1.setSpan(boldSpan, 8, 11, 0);
        binding.checkBody1.setText(body1);

        SpannableString body3 = new SpannableString(binding.checkBody3.getText());
        body3.setSpan(boldSpan, 0, 7, 0);
        binding.checkBody3.setText(body3);

        binding.button.setOnClickListener(view -> ((MainActivity)getActivity()).summaryClicked());

        TransitionInflater transitionInflater = TransitionInflater.from(getContext());
        //setExitTransition(transitionInflater.inflateTransition(R.transition.fade));
        setEnterTransition(transitionInflater.inflateTransition(R.transition.slide_left));

        return binding.getRoot();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
