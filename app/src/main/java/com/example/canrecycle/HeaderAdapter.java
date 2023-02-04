package com.example.canrecycle;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canrecycle.databinding.SummaryHeaderRowBinding;

public class HeaderAdapter extends RecyclerView.Adapter<HeaderAdapter.HeaderViewHolder> {

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView headerTextView;
        View rootView;

        public HeaderViewHolder(@NonNull SummaryHeaderRowBinding itemViewBinding) {
            super(itemViewBinding.getRoot());

            headerTextView = itemViewBinding.headerTextview;
            rootView = itemViewBinding.getRoot();
        }
    }

    String[] materialNames;
    TypedValue value = new TypedValue();

    public HeaderAdapter(String[] materialNames) {
        this.materialNames = materialNames;
    }

    @NonNull
    @Override
    public HeaderAdapter.HeaderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SummaryHeaderRowBinding binding = SummaryHeaderRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        parent.getContext().getTheme().resolveAttribute(com.google.android.material.R.attr.colorPrimaryVariant, value, true);

        return new HeaderViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull HeaderAdapter.HeaderViewHolder holder, int position) {
        holder.headerTextView.setText(materialNames[position]);
        if (position % 2 == 1) {
            holder.rootView.setBackgroundColor(value.data);
        }
    }

    @Override
    public int getItemCount() {
        return materialNames.length;
    }
}
