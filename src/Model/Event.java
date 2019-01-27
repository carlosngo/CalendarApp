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
    private Calendar date;
    private String name;
    private Color color;

    public Event(Calendar date, String name, Color color) {
        this.date = date;
        this.name = name;
        this.color = color;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
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
}
