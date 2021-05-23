package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Reading;
import models.Station;
import play.Logger;
import play.mvc.Controller;

public class Admin extends Controller {
    public static void index() {
        Logger.info("Rendering Admin");
        List<Reading> readings = Reading.findAll();
        render("admin.html", readings);
    }
}
