package com.example.canrecycle;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canrecycle.databinding.ListRowBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    OnItemClickListener listener;

    public void setOnItemClickListener (OnItemClickListener listener){
        this.listener = listener;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        TextView itemName, itemType;

        public ListViewHolder(@NonNull ListRowBinding itemViewBinding) {

            super(itemViewBinding.getRoot());

            itemName = itemViewBinding.itemName;
            itemType = itemViewBinding.itemType;

            itemViewBinding.getRoot().setOnClickListener(view -> listener.onItemClick(view, itemList[getAbsoluteAdapterPosition()]));
        }
    }

    String[] itemList, storedItemList;

    public ListAdapter(String[] itemList) {
        this.itemList = itemList;
        this.storedItemList = itemList;
    }

    @NonNull
    @Override
    public ListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListRowBinding binding = ListRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ListViewHolder holder, int position) {
        holder.itemName.setText(itemList[position].split("\\|", -1)[0]);
        holder.itemType.setText(itemList[position].split("\\|", -1)[1]);
    }

    @Override
    public int getItemCount() {
        return itemList.length;
    }

    public void itemNamePressed(boolean ascending) {
        if (ascending) {
            Arrays.sort(itemList);
        }
        else {
            Arrays.sort(itemList, Collections.reverseOrder());
        }

        ascending = !ascending;
        notifyDataSetChanged();
    }

    public void itemTypePressed(MenuItem clickedItem) {
        if (!clickedItem.toString().equals("None")) {
            List<String> filteredItemList = new ArrayList<>();
            for (String s : storedItemList) {
                if (s.split("\\|", -1)[1].equals(clickedItem.toString())) {
                    filteredItemList.add(s);
                }
            }

            itemList = new String[filteredItemList.size()];
            itemList = filteredItemList.toArray(itemList);
        }
        else {
            itemList = new String[storedItemList.length];
            itemList = storedItemList;
        }

        notifyDataSetChanged();
    }
}
