package com.example.myapplication;

import java.util.ArrayList;

public class Filter {
    String[] art;
    String[] bundesland;
    String[] berusfeld;

    public String[] getArt() {
        return art;
    }

    public void setArt(String[] art) {
        this.art = art;
    }

    public String[] getBundesland() {
        return bundesland;
    }

    public void setBundesland(String[] bundesland) {
        this.bundesland = bundesland;
    }

    public String[] getBerusfeld() {
        return berusfeld;
    }

    public void setBerusfeld(String[] berusfeld) {
        this.berusfeld = berusfeld;
    }

    public String createUrl(){
      String url = "https://www.wikway.de/companies/offers-json?password=ain1018";
      if (art!=null) {
          for (String str : art) {
              if (str != null) {
                  str.replaceAll(" ", "+");
                  url += "&art=" + str;
              }
          }
      }
       if(bundesland!=null) {
           for (String str : bundesland) {
               if (str != null) {
                   str.replaceAll(" ", "+");
                   url += "&bundesland=" + str;
               }
           }
       }
       if (berusfeld!=null) {
           for (String str : berusfeld) {
               if (str != null) {
                   str.replaceAll(" ", "+");
                   url += "&berusfeld=" + str;
               }
           }
       }
        return url;
    }

}
