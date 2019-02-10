package Controller;

import Model.Event;

import java.io.*;
import java.util.*;
import java.util.Calendar;
import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class EventIO {
    public static FileStrategy fs;

    public static void setStrategy(FileStrategy fs) {
        EventIO.fs = fs;
    }

    public static void executeStrategy(File file, ArrayList<Event> events) {
        fs.execute(file, events);
    }
}
