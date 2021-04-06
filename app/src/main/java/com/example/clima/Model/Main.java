package com.example.clima.Model;

public class Main {
    private double temp,humidity,temp_min,temp_max,feels_like,pressure;

    public Main(double temp, double humidity, double temp_min, double temp_max,double feels_like, double pressure) {
        this.temp = temp;
        this.humidity = humidity;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.feels_like = feels_like;
        this.pressure = pressure;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getTemp() {
        return temp;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getMin() {
        return temp_min;
    }

    public void setMin(double min) {
        this.temp_min = min;
    }

    public double getMax() {
        return temp_max;
    }

    public void setMax(double max) {
        this.temp_max = max;
    }

    public double getFeelslike() {
        return feels_like;
    }

    public void setFeelslike(double feelslike) {
        this.feels_like = feelslike;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }
}
