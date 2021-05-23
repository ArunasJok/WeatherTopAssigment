package controllers;

import java.util.Date;
import java.util.List;

import models.Reading;
import models.Station;
import play.Logger;
import play.mvc.Controller;


public class StationCtrl extends Controller {
    public static void index(Long id) {
        Logger.info("Rendering StationCtrl");
        Station station = Station.findById(id);
        List<Station> stations = Station.findAll();
        for (Station stationTwo : stations) {
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
                Logger.info("Station id = " + id);
                station.minTempC = getMinTemp(station.readings);
                station.maxTempC = getMaxTemp(station.readings);
                station.minWindSpeed = getMinWindSpeed(station.readings);
                station.maxWindSpeed = getMaxWindSpeed(station.readings);
                station.minPressure = getMinPressure(station.readings);
                station.maxPressure = getMaxPressure(station.readings);
                station.latitudeRound = (Math.round((station.latitude) * 1000.0)) / 1000.0;
                station.longitudeRound = (Math.round((station.longitude) * 1000.0)) / 1000.0;
            }
            render("station.html", station);
        }
    }

    public static double getMinTemp(List<Reading> readings) {
        Reading minTemp = null;
        if (readings.size() > 0) {
            minTemp = readings.get(0);
            for (Reading reading : readings) {
                if (reading.temperature < minTemp.temperature) {
                    minTemp = reading;
                }
            }
        }
        return minTemp.temperature;
    }

    public static double getMaxTemp(List<Reading> readings) {
        Reading maxTemp = null;
        if (readings.size() > 0) {
            maxTemp = readings.get(0);
            for (Reading reading : readings) {
                if (reading.temperature > maxTemp.temperature) {
                    maxTemp = reading;
                }
            }
        }
        return maxTemp.temperature;
    }

    public static double getMinWindSpeed(List<Reading> readings) {
        Reading minWind = null;
        if (readings.size() > 0) {
            minWind = readings.get(0);
            for (Reading reading : readings) {
                if (reading.windSpeed < minWind.windSpeed) {
                    minWind = reading;
                }
            }
        }
        return minWind.windSpeed;
    }

    public static double getMaxWindSpeed(List<Reading> readings) {
        Reading maxWind = null;
        if (readings.size() > 0) {
            maxWind = readings.get(0);
            for (Reading reading : readings) {
                if (reading.windSpeed > maxWind.windSpeed) {
                    maxWind = reading;
                }
            }
        }
        return maxWind.windSpeed;
    }

    public static int getMinPressure(List<Reading> readings) {
        Reading minPressure = null;
        if (readings.size() > 0) {
            minPressure = readings.get(0);
            for (Reading reading : readings) {
                if (reading.pressure < minPressure.pressure) {
                    minPressure = reading;
                }
            }
        }
        return minPressure.pressure;
    }

    public static int getMaxPressure(List<Reading> readings) {
        Reading maxPressure = null;
        if (readings.size() > 0) {
            maxPressure = readings.get(0);
            for (Reading reading : readings) {
                if (reading.pressure > maxPressure.pressure) {
                    maxPressure = reading;
                }
            }
        }
        return maxPressure.pressure;
    }


    public static void addData(Long id, int code, double temperature, double windSpeed, int pressure, int windDirection) {
        Date date = new Date();
        Reading reading = new Reading(date, code, temperature, windSpeed, pressure, windDirection);
        Station station = Station.findById(id);
        station.readings.add(reading);
        station.save();
        Logger.info("Redirecting Station ");
        redirect("/stations/" + id);
    }

    public static void deleteReading(Long id, Long readingid) {
        Station station = Station.findById(id);
        Reading reading = Reading.findById(readingid);
        Logger.info("Removing " + reading);
        station.readings.remove(reading);
        station.save();
        reading.delete();
        Logger.info("Redirecting Station ");
        redirect("/stations/" + id);
    }

}
