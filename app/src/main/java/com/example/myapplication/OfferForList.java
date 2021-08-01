package com.example.myapplication;

import android.graphics.Bitmap;

public class OfferForList {
    private String name;
    private String discription;
    private Bitmap logo;
    private String mainInfo1;
    private String mainInfo2;
    private String mainInfo3;
    private String mainInfo4;
    private String mainInfo5;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    private boolean fav;

    public String getMainInfo5() {
        return mainInfo5;
    }

    public void setMainInfo5(String mainInfo5) {
        this.mainInfo5 = mainInfo5;
    }

    private String logoUrl;
    private String compName;

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getMainInfo1() {
        return mainInfo1;
    }

    public void setMainInfo1(String mainInfo1) {
        this.mainInfo1 = mainInfo1;
    }

    public String getMainInfo2() {
        return mainInfo2;
    }

    public void setMainInfo2(String mainInfo2) {
        this.mainInfo2 = mainInfo2;
    }

    public String getMainInfo3() {
        return mainInfo3;
    }

    public void setMainInfo3(String mainInfo3) {
        this.mainInfo3 = mainInfo3;
    }

    public String getMainInfo4() {
        return mainInfo4;
    }

    public void setMainInfo4(String mainInfo4) {
        this.mainInfo4 = mainInfo4;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public Bitmap getLogo() {
        return logo;
    }

    public void setLogo(Bitmap logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
