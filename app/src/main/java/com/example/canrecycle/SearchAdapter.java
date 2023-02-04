package com.example.canrecycle;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canrecycle.databinding.SearchRowBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    OnItemClickListener listener;

    public void setOnItemClickListener (OnItemClickListener listener){
        this.listener = listener;
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {

        TextView itemName, itemTreatmentShort;
        ImageView itemImage, recyclableImage;
        View divider;

        public SearchViewHolder(@NonNull SearchRowBinding itemViewBinding) {
            super(itemViewBinding.getRoot());

            itemName = itemViewBinding.searchItemName;
            itemTreatmentShort = itemViewBinding.itemTreatmentShort;
            itemImage = itemViewBinding.itemImage;
            recyclableImage = itemViewBinding.recyclableImage;
            divider = itemViewBinding.dividerSearch;

            itemViewBinding.getRoot().setOnClickListener(view -> listener.onItemClick(view, itemList[getAbsoluteAdapterPosition()]));
        }
    }

    String[] itemList, storedItemList;
    Drawable recyclableImage,  nonRecyclableImage;

    public SearchAdapter(String[] itemList, Drawable recyclableImage, Drawable nonRecyclableImage) {
        this.itemList = new String[0];
        this.storedItemList = itemList;
        this.recyclableImage = recyclableImage;
        this.nonRecyclableImage = nonRecyclableImage;
    }

    public void setQuery(String query) {
        if (!query.isEmpty()) {
            List<String> filteredItemList = new ArrayList<>();
            List<String> filteredItemListPri1 = new ArrayList<>();
            List<String> filteredItemListPri2 = new ArrayList<>();
            List<String> filteredItemListPri3 = new ArrayList<>();
            List<String> filteredItemListPri4 = new ArrayList<>();
            for (String s : storedItemList) {
                if (s.split("\\|", -1)[0].toLowerCase().startsWith(query))
                    filteredItemListPri1.add(s);
                else if (s.split("\\|", -1)[0].toLowerCase().contains(query))
                    filteredItemListPri2.add(s);
                else if (s.split("\\|", -1)[4].toLowerCase().contains(query))
                    filteredItemListPri3.add(s);
                else if (s.toLowerCase().contains(query))
                    filteredItemListPri4.add(s);
            }

            filteredItemList.addAll(filteredItemListPri1);
            filteredItemList.addAll(filteredItemListPri2);
            filteredItemList.addAll(filteredItemListPri3);
            filteredItemList.addAll(filteredItemListPri4);

            itemList = new String[filteredItemList.size()];
            itemList = filteredItemList.toArray(itemList);
        }
        else itemList = new String[0];

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SearchRowBinding binding = SearchRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SearchViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.itemName.setText(itemList[position].split("\\|", -1)[0]);

        boolean recyclable = itemList[position].split("\\|", -1)[2].equals("Recyclable");
        if (recyclable)
            holder.recyclableImage.setImageDrawable(recyclableImage);
        else
            holder.recyclableImage.setImageDrawable(nonRecyclableImage);

        String itemTreatment = itemList[position].split("\\|", -1)[3];

        if (!itemTreatment.isEmpty()) {
            holder.divider.setVisibility(View.VISIBLE);
            holder.itemTreatmentShort.setText(itemTreatment.split("[^A-Za-z]")[0].toLowerCase());
        }
        else {
            holder.divider.setVisibility(View.INVISIBLE);
            holder.itemTreatmentShort.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return itemList.length;
    }
}
