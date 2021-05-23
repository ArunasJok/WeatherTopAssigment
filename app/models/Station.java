package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import controllers.StationCtrl;
import play.Logger;
import play.db.jpa.Model;


@Entity
public class Station extends Model {


    public String name;
    public String weatherCode;
    public double tempC;
    public double tempF;
    public double maxTempC;
    public double minTempC;
    public double windBeaufort;
    public double windChill;
    public double minWindSpeed;
    public double maxWindSpeed;
    public double latitude;
    public double latitudeRound;
    public double longitudeRound;
    public double longitude;
    public int pressureNow;
    public int minPressure;
    public int maxPressure;
    public String windDirectionName;


    @OneToMany(cascade = CascadeType.ALL)
    public List<Reading> readings = new ArrayList<Reading>();

    public Station(String name, double longitude, double latitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;

    }


    public static String latestCode(int code) {

        switch (code) {
            case 100:
                return "Clear";
            case 200:
                return "Partial Cloudy";
            case 300:
                return "Cloudy";
            case 400:
                return "Light Showers";
            case 500:
                return "Heavy Showers";
            case 600:
                return "Rain";
            case 700:
                return "Snow";
            case 800:
                return "Thunder";
            default:
                return "Error";
        }
    }

    public static double latestWindToBeaufort(double windSpeed) {

        if (windSpeed <= 1) {
            return 0;
        } else if (windSpeed > 1 && windSpeed <= 5) {
            return 1;
        } else if (windSpeed > 5 && windSpeed <= 11) {
            return 2;
        } else if (windSpeed > 11 && windSpeed <= 19) {
            return 3;
        } else if (windSpeed > 19 && windSpeed <= 28) {
            return 4;
        } else if (windSpeed > 28 && windSpeed <= 38) {
            return 5;
        } else if (windSpeed > 38 && windSpeed <= 49) {
            return 6;
        } else if (windSpeed > 49 && windSpeed <= 61) {
            return 7;
        } else if (windSpeed > 62 && windSpeed <= 74) {
            return 8;
        } else if (windSpeed > 74 && windSpeed <= 88) {
            return 9;
        } else if (windSpeed > 88 && windSpeed <= 102) {
            return 10;
        } else
            return 11;
    }


    public static String windDirectionText(int windDirection) {
        if (windDirection > 348.75 && windDirection <= 11.25) {
            return "North";
        } else if (windDirection > 11.25 && windDirection <= 33.75) {
            return "North North-east";
        } else if (windDirection > 33.75 && windDirection <= 56.25) {
            return "North-east";
        } else if (windDirection > 56.25 && windDirection <= 78.75) {
            return "East North-east";
        } else if (windDirection > 78.75 && windDirection <= 101.25) {
            return "East";
        } else if (windDirection > 101.25 && windDirection <= 123.75) {
            return "East South-east";
        } else if (windDirection > 123.75 && windDirection <= 146.25) {
            return "South-east";
        } else if (windDirection > 146.25 && windDirection <= 168.75) {
            return "South South-east";
        } else if (windDirection > 168.75 && windDirection <= 191.25) {
            return "South";
        } else if (windDirection > 191.25 && windDirection <= 213.75) {
            return "South South-west";
        } else if (windDirection > 213.75 && windDirection <= 236.25) {
            return "South-west";
        } else if (windDirection > 236.25 && windDirection <= 258.75) {
            return "West South-west";
        } else if (windDirection > 258.75 && windDirection <= 281.25) {
            return "West";
        } else if (windDirection > 281.25 && windDirection <= 303.75) {
            return "West North-west";
        } else if (windDirection > 303.75 && windDirection <= 326.25) {
            return "North-west";
        } else
            return "North North-west";
    }


}





