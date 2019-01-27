package Controller;

import Model.Event;
import View.*;

import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CalendarController {
    private CalendarWindow cv;
    private AddEventWindow aev;
    private ViewDateWindow dv;
    private TreeMap<Calendar, TreeSet<Model.Event>> events;
    private EventIO io;

    public CalendarController() {
        events = new TreeMap<>();
        io = new EventIO();
        cv = new CalendarWindow(this);
    }

    public void openAddEventWindow() {
        aev = new AddEventWindow(this);
    }

    public void openAddEventWindow(Calendar d) {
        aev = new AddEventWindow(this, d);
    }

    public void openViewDateWindow(Calendar d, Point p) {
        dv = new ViewDateWindow(this, d, getEventNames(d), getEventColors(d), p);
    }

    public void importEvents() {
        JFileChooser chooser = new JFileChooser();
        chooser.addChoosableFileFilter(new FileNameExtensionFilter(
                "PSV / CSV File", "csv", "psv"));
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            String filename = file.getName();
            String extension = filename.substring(filename.lastIndexOf('.'), filename.length());
            if (extension.equals(".csv")) {
                ArrayList<Model.Event> list = io.importCSV(file);
                for (Model.Event ev : list) {
                    Calendar d = ev.getDate();
                    if (events.containsKey(d)) {
                        events.get(d).add(ev);
                        for (Model.Event meh : events.get(d)) {
                            System.out.println(meh.getName());
                        }
                    } else {
                        TreeSet<Model.Event> toAdd = new TreeSet<>();
                        toAdd.add(ev);
                        events.put(d, toAdd);
                    }
                }
            } else if (extension.equals(".psv")) {
                ArrayList<Model.Event> list = io.importPSV(file);
                for (Model.Event ev : list) {
                    Calendar d = ev.getDate();
                    if (events.containsKey(d)) {
                        events.get(d).add(ev);
                    } else {
                        TreeSet<Model.Event> toAdd = new TreeSet<>();
                        toAdd.add(ev);
                        events.put(d, toAdd);
                    }
                }
            }
        }
    }
    public void exportEvents() {
        JFileChooser chooser = new JFileChooser();
        chooser.addChoosableFileFilter(new FileNameExtensionFilter(
                "PSV / CSV File", "csv", "psv"));
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            String filename = file.getName();
            String extension = filename.substring(filename.lastIndexOf('.'), filename.length());
            if (extension.equals(".csv")) {
                io.exportCSV(file, events);
            } else if (extension.equals(".psv")) {
                io.exportPSV(file, events);
            }
        }
    }

    public ArrayList<String> getEventNames(Calendar d) {
        ArrayList<String> names = new ArrayList<>();
        if (events.containsKey(d)) {
            for (Model.Event e : events.get(d)) {
                names.add(e.getName());
            }
        }
        return names;
    }

    public ArrayList<Color> getEventColors(Calendar d) {
        ArrayList<Color> colors = new ArrayList<>();
        if (events.containsKey(d)) {
            for (Model.Event e : events.get(d)) {
                colors.add(e.getColor());
            }
        }
        return colors;
    }

    public void addEvent(Calendar d, Event e) {
        if (!events.containsKey(d)) {
            TreeSet<Model.Event> set = new TreeSet<>();
            set.add(e);
            events.put(d, set);
        } else {
            events.get(d).add(e);
        }
    }

    public void addEvent(Calendar c, String name, Color color, int interval) {
        Model.Event ev = new Model.Event(c, name, color);
        addEvent(c, ev);
        switch (interval) {
            case Event.WEEKLY_EVENT:
                while (c.get(Calendar.YEAR) <= cv.yearBound + 100) {
                    c.add(Calendar.WEEK_OF_YEAR, 1);
                    Model.Event repeatedEvent = new Model.Event(c, name, color);
                    addEvent(c, repeatedEvent);
                }
                break;
            case Event.BIWEEKLY_EVENT:
                while (c.get(Calendar.YEAR) <= cv.yearBound + 100) {
                    c.add(Calendar.WEEK_OF_YEAR, 2);
                    Model.Event repeatedEvent = new Model.Event(c, name, color);
                    addEvent(c, repeatedEvent);
                }
                break;
            case Event.YEARLY_EVENT:
                while (c.get(Calendar.YEAR) <= cv.yearBound + 100) {
                    c.add(Calendar.YEAR, 1);
                    Date newDate = c.getTime();
                    Model.Event repeatedEvent = new Model.Event(c, name, color);
                    addEvent(c, repeatedEvent);
                }
                break;
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
