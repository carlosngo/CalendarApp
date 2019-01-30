package Controller;

import Model.Event;

import java.io.*;
import java.util.*;
import java.util.Calendar;
import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class EventIO {
    FileStrategy fs;

    public void setStrategy(FileStrategy fs) {
        this.fs = fs;
    }

    public void executeStrategy(File file, TreeMap<Calendar, TreeSet<Event>> events) {
        fs.execute(file, events);
    }
}
