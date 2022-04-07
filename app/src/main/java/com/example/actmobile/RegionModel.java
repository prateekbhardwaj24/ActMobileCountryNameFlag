package com.example.actmobile;

public class RegionModel {
    String name;
    String flagUrl;

    public RegionModel(String name, String flag){
        this.name = name;
        this.flagUrl = flag;
    }


    public String getName() {
        return name;
    }

    public String getFlagUrl() {
        return flagUrl;
    }


    public void setName(String name) {
        this.name = name;
    }


}
