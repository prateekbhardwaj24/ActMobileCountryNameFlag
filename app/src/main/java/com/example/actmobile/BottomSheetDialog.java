package com.example.actmobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.actmobile.databinding.RegionBottomSheetLayoutBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class BottomSheetDialog extends BottomSheetDialogFragment {
    private ArrayList<RegionModel> regionArrList;
    private RegionBottomSheetAdapter regionBottomSheetAdapter;
    private RegionBottomSheetLayoutBinding bottomSheetLayoutBinding;
    private int selectedPosition;


    public BottomSheetDialog(ArrayList<RegionModel> list, int selectedPosition) {
        this.regionArrList = list;
        this.selectedPosition = selectedPosition;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        bottomSheetLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.region_bottom_sheet_layout, container, false);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        bottomSheetLayoutBinding.regionListRv.setLayoutManager(linearLayoutManager);
        regionBottomSheetAdapter = new RegionBottomSheetAdapter(regionArrList, getContext(), selectedPosition);
        bottomSheetLayoutBinding.regionListRv.setAdapter(regionBottomSheetAdapter);
        regionBottomSheetAdapter.setOnItemClickListener(new RegionBottomSheetAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                ((MainActivity) requireActivity()).updatingViews(position);
            }

        });

        return bottomSheetLayoutBinding.getRoot();
    }


}