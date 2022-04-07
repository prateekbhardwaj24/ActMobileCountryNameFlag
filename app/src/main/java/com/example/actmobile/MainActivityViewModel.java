package com.example.actmobile;


import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivityViewModel extends BaseObservable {

    private RegionModel model;

    public MainActivityViewModel(RegionModel model) {
        this.model = model;
    }

    @Bindable
    public String getRegionName() {
        return model.getName();
    }

    public void setRegionName(String email) {
        model.setName(email);
        notifyPropertyChanged(BR.regionName);
    }

    public MutableLiveData<ArrayList<RegionModel>> getREgionListWithImages(MainActivity mainActivity) {
        MutableLiveData<ArrayList<RegionModel>> regionArrList = new MutableLiveData<>();
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(mainActivity.getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);


        requestQueue.start();


        String url = "https://api.printful.com/countries";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //  Log.d("sixef", String.valueOf("d->"+response));
                        try {
                            JSONArray regionJsonArray = response.getJSONArray("result");
                            ArrayList<RegionModel> list = new ArrayList<>();
                            for (int i = 0; i < regionJsonArray.length(); i++) {
                                JSONObject regionJsonObject = regionJsonArray.getJSONObject(i);
                                list.add(new RegionModel(
                                        regionJsonObject.getString("name"),
                                        "https://countryflagsapi.com/" + "png/" + regionJsonObject.getString("code")
                                ));

                            }
                            regionArrList.postValue(list);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("sixef", "ddddd->" + error.getLocalizedMessage());
                    }
                });

        requestQueue.add(jsonObjectRequest);
        return regionArrList;

    }

}
