package com.example.clima;

public class Common {

    public static String API_KEY = "&appid=c634170f97178d48c1e5d219d164bccb";
    public static String url = "http://api.openweathermap.org/data/2.5/weather?";

    public static String apiRequets(String lat , String lng){
        return url + String.format("lat=%s&lon=%s",lat,lng) + API_KEY;
    }
    public static String getIcon(String icon){
        return String.format("http://openweathermap.org/img/w/%s.png",icon);
    }
    public static String apifromcity(String name){
        return url + String.format("q=%s",name) + API_KEY;
    }
}
