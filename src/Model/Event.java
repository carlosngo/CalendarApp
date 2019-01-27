/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.*;
import java.util.*;
import java.util.Calendar;

/**
 *
 * @author User
 */
public class Event implements Comparable<Event> {
    public static final int ONE_TIME_EVENT = 0;
    public static final int WEEKLY_EVENT = 1;
    public static final int BIWEEKLY_EVENT = 2;
    public static final int YEARLY_EVENT = 3;
    private Date date;
    private String name;
    private Color color;

    public Event(Date date, String name, Color color) {
        this.date = date;
        this.name = name;
        this.color = color;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Event)) return false;
        Event e = (Event) obj;
        return date.compareTo(e.getDate()) == 0 && name.equals(e.getName());
    }

    @Override
    public int compareTo(Event e) {
        int compDate = date.compareTo(e.getDate());
        if (compDate != 0) {
            return compDate;
        } else {
            return name.compareTo(e.getName());
        }
    }

//    public static void main(String[] args) {
//        int month = 1;
//        int year = 2018;
//        int day = 20;
//        java.util.Calendar c = Calendar.getInstance();
//        c.set(year, month - 1, day, 0, 0, 0);
//        Date d1 = c.getTime();
//        Date d2 = c.getTime();
//        TreeMap<Date, TreeSet<Event>> calendar = new TreeMap<>();
//        TreeSet<Event> events = new TreeSet<>();
//        Event e1 = new Event(d1, "CCS Day", new Color(255, 0, 0));
//        Event e2 = new Event(d2, "CCS Day", new Color(255, 0, 0));
//        events.add(e1);
//        calendar.put(d1, events);
//        System.out.println("Does the calendar have the key? " + calendar.containsKey(d2));
////        events.
//        events.add(e2);
//        for (Event e : events) {
//            System.out.println(e.toString());
//        }
//        System.out.println("Is d1 equal to d2? " + d1.compareTo(d2));
//        System.out.println(e1.compareTo(e2));
//
//    }
}
