/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.*;
import java.util.*;
/**
 *
 * @author User
 */
public class Event implements Comparable<Event> {
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
}
