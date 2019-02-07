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
    private CalendarWindow cv;
    private AddEventWindow aev;
    private ViewDateWindow dv;
    private ArrayList<Event> events;
    private EventIO io;
    private NotificationController no;

    public CalendarController() {
        events = new ArrayList<>();
        io = new EventIO();
        loadEvents();
        cv = new CalendarWindow(this);
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
        no.addSubject(new FBWindow());
//        new Thread(new NotificationHandler(this, new FBWindow())).start();
    }

    public void openSMSWindow() {
        no.addSubject(new SMSWindow());
//        new Thread(new NotificationHandler(this, new SMSWindow())).start();
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
                io.setStrategy(new CSVReader());
                io.executeStrategy(file, events);
            } else if (extension.equals(".psv")) {
                io.setStrategy(new PSVReader());
                io.executeStrategy(file, events);
            }
        }
    }

    public void saveEvents() {
        try {
            URL resource = getClass().getClassLoader().getResource("events.csv");
            File csv = Paths.get(resource.toURI()).toFile();
            io.setStrategy(new CSVWriter());
            io.executeStrategy(csv, events);
//            io.exportCSV(csv, events);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void loadEvents() {
        try {
            URL resource = getClass().getClassLoader().getResource("events.csv");
            File csv = Paths.get(resource.toURI()).toFile();
            io.setStrategy(new CSVReader());
            io.executeStrategy(csv, events);
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
                io.setStrategy(new CSVWriter());
                io.executeStrategy(file, events);
            } else if (extension.equals(".psv")) {
                io.setStrategy(new PSVWriter());
                io.executeStrategy(file, events);
            }
        }
    }

    public TreeSet<Event> getEvents(Calendar d) {
        TreeSet<Event> set = new TreeSet<>();
        for (Event e : events) {
            if (repeatsAtDate(e.getDate(), e.getInterval(), d))
                set.add(e);
        }
        return set;
    }

    public ArrayList<String> getEventNames(Calendar d) {
        ArrayList<String> names = new ArrayList<>();
        for (Event e : events) {
            if (repeatsAtDate(e.getDate(), e.getInterval(), d))
                names.add(e.getName());
        }
        return names;
    }

    public ArrayList<Color> getEventTextColors(Calendar d) {
        ArrayList<Color> colors = new ArrayList<>();
        for (Event e : events) {
            if (repeatsAtDate(e.getDate(), e.getInterval(), d))
                colors.add(e.getTextColor());
        }
        return colors;
    }

    public ArrayList<Color> getEventBackgroundColors(Calendar d) {
        ArrayList<Color> colors = new ArrayList<>();
        for (Event e : events) {
            if (repeatsAtDate(e.getDate(), e.getInterval(), d))
                colors.add(e.getBackgroundColor());
        }
        return colors;
    }

    public ArrayList<Integer> getEventIntervals (Calendar d) {
        ArrayList<Integer> intervals = new ArrayList<>();
        for (Event e : events) {
            if (repeatsAtDate(e.getDate(), e.getInterval(), d))
                intervals.add(e.getInterval());
        }
        return intervals;
    }

    public void addEvent(Calendar d, String name, Color color, int interval) {
        events.add(new Event(d, name, color, interval));
        cv.refreshCalendar(cv.monthToday, cv.yearToday);
        no.update();
    }

    public void addHoliday(Calendar d, String name, Color color) {
        events.add(new Holiday(d, name, color));
        cv.refreshCalendar(cv.monthToday, cv.yearToday);
        no.update();
    }

    public void removeEvent(Calendar d, String name, Color c) {

        for (int i = 0; i < events.size(); i++) {
            Event e = events.get(i);
            if (repeatsAtDate(e.getDate(), e.getInterval(), d) && e.getName().equals(name) &&
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

    private boolean repeatsAtDate(Calendar startDate, int interval, Calendar query) {
        if (startDate.compareTo(query) > 0) return false;
        switch (interval) {
            case Event.ONE_TIME_EVENT:
                return startDate.compareTo(query) == 0;
            case Event.DAILY_EVENT:
                return startDate.compareTo(query) <= 0;
            case Event.WEEKLY_EVENT:
                return startDate.get(Calendar.DAY_OF_WEEK) == query.get(Calendar.DAY_OF_WEEK);
            case Event.MONTHLY_EVENT:
                return startDate.get(Calendar.DAY_OF_MONTH) == query.get(Calendar.DAY_OF_MONTH);
            case Event.YEARLY_EVENT:
                return startDate.get(Calendar.MONTH) == query.get(Calendar.MONTH) &&
                        startDate.get(Calendar.DAY_OF_MONTH) == query.get(Calendar.DAY_OF_MONTH);
        }
        return false;
    }

    public boolean isViewDateWindowOpen() { return dv != null; }

    public boolean isAddEventWindowOpen() { return aev != null; }
}
