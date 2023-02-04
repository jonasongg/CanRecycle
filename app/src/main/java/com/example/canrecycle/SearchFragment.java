package com.example.canrecycle;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canrecycle.databinding.FragmentSearchBinding;

import java.sql.ClientInfoStatus;

public class SearchFragment extends Fragment {
    private FragmentSearchBinding binding;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);

        float dp_24_to_px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics());

        ConstraintSet constraintSet = new ConstraintSet();
        ConstraintSet constraintSetMoved = new ConstraintSet();
        constraintSet.clone(getContext(), R.layout.fragment_search);
        constraintSetMoved.clone(getContext(), R.layout.fragment_search);
        constraintSetMoved.connect(R.id.title_fragment_search, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, (int) dp_24_to_px);

        RecyclerView recyclerView = binding.recyclerViewSearch;

        SearchAdapter searchAdapter = new SearchAdapter(
                getResources().getStringArray(R.array.item_list),
                ResourcesCompat.getDrawable(getResources(), R.drawable._recyclable, null),
                ResourcesCompat.getDrawable(getResources(), R.drawable._nonrecyclable, null));
        recyclerView.setAdapter(searchAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        searchAdapter.setOnItemClickListener((view, data) -> ((MainActivity)getActivity()).itemRowClicked(data));

        AutoTransition transition = new AutoTransition();
        transition.setDuration(200);

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                TransitionManager.beginDelayedTransition(binding.getRoot(), transition);
                if (!s.isEmpty()) constraintSetMoved.applyTo(binding.getRoot());
                else constraintSet.applyTo(binding.getRoot());

                searchAdapter.setQuery(s);

                return true;
            }
        });

        /*binding.recyclerViewSearch.setOnTouchListener((view, motionEvent) -> {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

            return false;
        });*/

        TransitionInflater transitionInflater = TransitionInflater.from(getContext());
        //setExitTransition(transitionInflater.inflateTransition(R.transition.fade));

        MainActivity activity = (MainActivity) getActivity();
        if (activity.isFromLeft)
            setEnterTransition(transitionInflater.inflateTransition(R.transition.slide_left));
        else
            setEnterTransition(transitionInflater.inflateTransition(R.transition.slide_right));

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
