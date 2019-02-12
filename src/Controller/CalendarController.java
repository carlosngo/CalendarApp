package Controller;

import Model.*;
import Model.Event;
import View.*;

import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CalendarController {
    private CalendarProgram cv;
    private AddEventWindow aev;
    private ViewDateWindow dv;
    private ArrayList<Event> events;
    private NotificationController no;

    public CalendarController() {
        events = new ArrayList<>();
        loadEvents();
        cv = new CalendarProgram(this);
        no = new NotificationController(this);
        openFBWindow();
        openSMSWindow();

    }

    public void openAddEventWindow() {
        aev = new AddEventWindow(this);
    }

    public void openAddEventWindow(Calendar d) {
        aev = new AddEventWindow(this, d);
    }

    public void openAddEventWindow(Calendar d, String name, Color c, Color bckClr) {
        aev = new AddEventWindow(this, d, name, c, bckClr);
    }

    public void openViewDateWindow(Calendar d, Point p) {
        dv = new ViewDateWindow(this, d, getEventNames(d), getEventTextColors(d), getEventBackgroundColors(d), p);
    }

    public void openFBWindow() {
        no.addSubject(new FBView());
//        new Thread(new NotificationHandler(this, new FBView())).start();
    }

    public void openSMSWindow() {
        no.addSubject(new SMSView());
//        new Thread(new NotificationHandler(this, new SMSView())).start();
    }

    public void importEvents() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Import Events");
        chooser.addChoosableFileFilter(new FileNameExtensionFilter(
                "PSV / CSV File", "csv", "psv"));
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            String filename = file.getName();
            String extension = filename.substring(filename.lastIndexOf('.'), filename.length());
            if (extension.equals(".csv")) {
                EventIO.setStrategy(new CSVReader());
                EventIO.executeStrategy(file, events);
            } else if (extension.equals(".psv")) {
                EventIO.setStrategy(new PSVReader());
                EventIO.executeStrategy(file, events);
            }
        }
    }

    public void saveEvents() {
        try {
            URL resource = getClass().getClassLoader().getResource("events.csv");
            File csv = Paths.get(resource.toURI()).toFile();
            EventIO.setStrategy(new CSVWriter());
            EventIO.executeStrategy(csv, events);
//            io.exportCSV(csv, events);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void loadEvents() {
        try {
            URL resource = getClass().getClassLoader().getResource("events.csv");
            File csv = Paths.get(resource.toURI()).toFile();
            EventIO.setStrategy(new CSVReader());
            EventIO.executeStrategy(csv, events);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void exportEvents() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Export Events");
        chooser.addChoosableFileFilter(new FileNameExtensionFilter(
                "PSV / CSV File", "csv", "psv"));
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            String filename = file.getName();
            String extension = filename.substring(filename.lastIndexOf('.'), filename.length());
            if (extension.equals(".csv")) {
                EventIO.setStrategy(new CSVWriter());
                EventIO.executeStrategy(file, events);
            } else if (extension.equals(".psv")) {
                EventIO.setStrategy(new PSVWriter());
                EventIO.executeStrategy(file, events);
            }
        }
    }

    public TreeSet<Event> getEvents(Calendar d) {
        TreeSet<Event> set = new TreeSet<>();
        for (Event e : events) {
            if (e.getInterval().repeatsAtDate(e.getDate(), d))
                set.add(e);
        }
        return set;
    }

    public ArrayList<String> getEventNames(Calendar d) {
        ArrayList<String> names = new ArrayList<>();
        for (Event e : events) {
            if (e.getInterval().repeatsAtDate(e.getDate(), d))
                names.add(e.getName());
        }
        return names;
    }

    public ArrayList<Color> getEventTextColors(Calendar d) {
        ArrayList<Color> colors = new ArrayList<>();
        for (Event e : events) {
            if (e.getInterval().repeatsAtDate(e.getDate(), d))
                colors.add(e.getTextColor());
        }
        return colors;
    }

    public ArrayList<Color> getEventBackgroundColors(Calendar d) {
        ArrayList<Color> colors = new ArrayList<>();
        for (Event e : events) {
            if (e.getInterval().repeatsAtDate(e.getDate(), d))
                colors.add(e.getBackgroundColor());
        }
        return colors;
    }

    public void addEvent(Calendar d, String name, Color color, EventInterval interval) {
        events.add(new Event(d, name, color, interval));
        cv.refreshCalendar(cv.monthToday, cv.yearToday);
        no.update();
    }

    public void addHoliday(Calendar d, String name, Color color) {
        events.add(new Holiday(d, name, color));
        cv.refreshCalendar(cv.monthToday, cv.yearToday);
        no.update();
    }

    public void addBirthday(Calendar d, String name, Color color) {
        events.add(new Birthday(d, name, color));
        cv.refreshCalendar(cv.monthToday, cv.yearToday);
        no.update();
    }

    public void addAnniversary(Calendar d, String name, Color color) {
        events.add(new Anniversary(d, name, color));
        cv.refreshCalendar(cv.monthToday, cv.yearToday);
        no.update();
    }

    public void addUtilityBillPayment(Calendar d, String name, Color color) {
        events.add(new UtilityBillPayment(d, name, color));
        cv.refreshCalendar(cv.monthToday, cv.yearToday);
        no.update();
    }

    public void removeEvent(Calendar d, String name, Color c) {

        for (int i = 0; i < events.size(); i++) {
            Event e = events.get(i);
            if (e.getInterval().repeatsAtDate(e.getDate(), d) && e.getName().equals(name) &&
                    e.getTextColor().getRGB() == c.getRGB()) events.remove(i);
        }

        cv.refreshCalendar(cv.monthToday, cv.yearToday);
    }

    public void closeViewDateWindow() {
        dv.dispose();
        dv = null;
    }

    public void closeAddEventWindow() {
        aev.dispose();
        aev = null;
    }

    public boolean isViewDateWindowOpen() { return dv != null; }

    public boolean isAddEventWindowOpen() { return aev != null; }
}
