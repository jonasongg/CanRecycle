package com.example.canrecycle;

import android.os.Bundle;
import android.text.Html;
import android.transition.TransitionInflater;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import com.example.canrecycle.databinding.FragmentItemBinding;

public class ItemFragment extends Fragment {

    FragmentItemBinding binding;

    String itemName, itemMaterial, itemTreatment, itemDescription;
    Boolean recyclable;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        float dp_16_to_px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics());

        binding = FragmentItemBinding.inflate(inflater, container, false);
        String data = getArguments().getString("DATA");
        itemName = data.split("\\|", -1)[0];
        itemMaterial = data.split("\\|", -1)[1];
        recyclable = data.split("\\|", -1)[2].equals("Recyclable");
        itemTreatment = data.split("\\|", -1)[3];
        itemDescription = data.split("\\|", -1)[4];

        binding.itemNameTitle.setText(itemName);
        binding.materialBody.setText(itemMaterial);

        binding.treatmentBody.setText(itemTreatment);
        binding.descriptionBody.setText(itemDescription);

        if (itemDescription.isEmpty()) { //if description is EMPTY

            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(binding.nestedConstraint);
            constraintSet.connect(R.id.treatment_title, ConstraintSet.TOP, R.id.material_body, ConstraintSet.BOTTOM, (int) dp_16_to_px);
            constraintSet.applyTo(binding.nestedConstraint);

            binding.descriptionTitle.setVisibility(View.INVISIBLE);
            binding.descriptionBody.setVisibility(View.INVISIBLE);
        }
        if (itemTreatment.isEmpty()) { //if treatment is EMPTY
            binding.treatmentTitle.setVisibility(View.INVISIBLE);
            binding.treatmentBody.setVisibility(View.INVISIBLE);
        }

        if (recyclable) {
            //binding.recyclableBanner.setVisibility(View.VISIBLE);
            binding.nonrecyclableBanner.setVisibility(View.INVISIBLE);
        }
        else {
            //binding.nonrecyclableBanner.setVisibility(View.VISIBLE);
            binding.recyclableBanner.setVisibility(View.INVISIBLE);
        }

        TransitionInflater transitionInflater = TransitionInflater.from(getContext());
        //setExitTransition(transitionInflater.inflateTransition(R.transition.fade));
        setEnterTransition(transitionInflater.inflateTransition(R.transition.slide_right));

        return binding.getRoot();
    }
}
