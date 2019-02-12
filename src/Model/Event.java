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
    private Calendar date;
    private String name;
    private Color textColor;
    private Color backgroundColor;
    private EventInterval interval;

    public Event(Calendar date, String name, Color textColor) {
        this.date = date;
        this.name = name;
        this.textColor = textColor;
        interval = new NoInterval();
        backgroundColor = Color.WHITE;
    }

    public Event(Calendar date, String name, Color textColor, EventInterval interval) {
        this(date, name, textColor);
        this.interval = interval;
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

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color color) {
        this.textColor = color;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
    }

    public EventInterval getInterval() { return interval; }

    public void setInterval(EventInterval interval) { this.interval = interval; }


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
