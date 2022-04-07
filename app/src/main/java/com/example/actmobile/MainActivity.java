package com.example.actmobile;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.example.actmobile.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ArrayList<RegionModel> regionArrList = new ArrayList<>();
    private ProgressDialog progressDialog;
    private ActivityMainBinding activityMainBinding;
    private BottomSheetDialog bottomSheet;
    private int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setMainScreenViewBinding(new MainActivityViewModel(new RegionModel("", "")));
        activityMainBinding.executePendingBindings();
        activityMainBinding.dropDownView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBottonSheet();
            }
        });
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading region");
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        activityMainBinding.getMainScreenViewBinding().getREgionListWithImages(this).observe(this, new Observer<ArrayList<RegionModel>>() {
            @Override
            public void onChanged(ArrayList<RegionModel> regionModels) {
                regionArrList = regionModels;
                progressDialog.dismiss();
            }
        });
    }

    private void openBottonSheet() {
        bottomSheet = new BottomSheetDialog(regionArrList, selectedPosition);
        bottomSheet.show(getSupportFragmentManager(),
                "ModalBottomSheet");
    }

    public void updatingViews(int position) {
        Glide.with(this).load(regionArrList.get(position).getFlagUrl()).into(activityMainBinding.flagImageV);
        selectedPosition = position;
        activityMainBinding.setMainScreenViewBinding(new MainActivityViewModel(new RegionModel(regionArrList.get(position).getName(), "")));
        bottomSheet.dismiss();
    }


}
