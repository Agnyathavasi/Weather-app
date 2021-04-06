package com.example.clima.Model;

import java.util.List;

public class OpenWeatherMap {
    private Coord coord;
    private Main main;
    private Sys sys;
    private Wind wind;
    private List<Weather> weather;
    private String name;
    private Long timezone;

    public OpenWeatherMap() {
    }

    public OpenWeatherMap(Coord coord, Main main, Sys sys, Wind wind, List<Weather> weather, String name, Long timezone) {
        this.coord = coord;
        this.main = main;
        this.sys = sys;
        this.wind = wind;
        this.weather = weather;
        this.name = name;
        this.timezone = timezone;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTimezone() {
        return timezone;
    }

    public void setTimezone(Long timezone) {
        this.timezone = timezone;
    }
}
