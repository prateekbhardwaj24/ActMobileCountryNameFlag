package com.example.actmobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RegionBottomSheetAdapter extends RecyclerView.Adapter<RegionBottomSheetAdapter.MyViewHolder> {
    private ArrayList<RegionModel> regionList;
    private Context context;
    public static ClickListener clickListener;
    private int selectedPosition;

    public RegionBottomSheetAdapter(ArrayList<RegionModel> list, Context context, int selectedPosition) {
        this.regionList = list;
        this.context = context;
        this.selectedPosition = selectedPosition;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.region_list_viewitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        RegionModel model = regionList.get(position);
        holder.regionName.setText(model.getName());
        Glide.with(context).load(model.getFlagUrl()).into(holder.countryImage);

        if (selectedPosition == position) {
            holder.regionBtn.setChecked(true);
        } else {
            holder.regionBtn.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return regionList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView regionName;
        public ImageView countryImage;
        public RadioButton regionBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            regionName = itemView.findViewById(R.id.regionNameView);
            countryImage = itemView.findViewById(R.id.flagImageView);
            regionBtn = itemView.findViewById(R.id.radioView);
            itemView.setOnClickListener(this);
            regionBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        RegionBottomSheetAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }
}
