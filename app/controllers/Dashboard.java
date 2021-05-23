package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Member;
import models.Reading;
import models.Station;
import play.Logger;
import play.mvc.Controller;


public class Dashboard extends Controller {

    public static void index(Long id) {
        Logger.info("Rendering Dashboard");
        Member member = Accounts.getLoggedInMember();
        List<Station> stations = member.stations;

        for (Station station : stations) {
            List<Reading> readings = station.readings;
            if (readings.size() > 0) {
                Reading reading = station.readings.get(station.readings.size() - 1);
                station.weatherCode = Station.latestCode(reading.code);
                station.tempC = reading.temperature;
                station.tempF = ((reading.temperature * 9 / 5) + (32));
                station.windBeaufort = Station.latestWindToBeaufort(reading.windSpeed);
                station.windChill = (Math.round((13.13 + (0.6215 * reading.temperature) - (11.37 * (Math.pow(reading.windSpeed, 0.16))) +
                        ((0.3965 * reading.temperature) * (Math.pow(reading.windSpeed, 0.16)))) * 100)) / 100.0;
                station.windDirectionName = Station.windDirectionText(reading.windDirection);
                station.pressureNow = reading.pressure;
                station.minTempC = StationCtrl.getMinTemp(station.readings);
                station.maxTempC = StationCtrl.getMaxTemp(station.readings);
                station.minWindSpeed = StationCtrl.getMinWindSpeed(station.readings);
                station.maxWindSpeed = StationCtrl.getMaxWindSpeed(station.readings);
                station.minPressure = StationCtrl.getMinPressure(station.readings);
                station.maxPressure = StationCtrl.getMaxPressure(station.readings);
                station.latitudeRound = (Math.round((station.latitude) * 1000.0)) / 1000.0;
                station.longitudeRound = (Math.round((station.longitude) * 1000.0)) / 1000.0;
            }
        }
        render("dashboard.html", "latestDataCards.html", stations, member);
    }

    public static void addStation(String name, double latitude, double longitude) {
        Member member = Accounts.getLoggedInMember();
        Station station = new Station(name, latitude, longitude);
        member.stations.add(station);
        member.save();
        Logger.info("Adding a new Station called " + name);
        redirect("/dashboard");
    }

    public static void deleteStation(Long id) {
        Member member = Accounts.getLoggedInMember();
        Station station = Station.findById(id);
        member.stations.remove(station);
        member.save();
        Logger.info("Deleting " + station.name);
        redirect("/dashboard");
    }
}
