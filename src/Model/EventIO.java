package Model;

import java.io.*;
import java.util.*;
import java.util.Calendar;
import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class EventIO {
    PrintWriter out;
    BufferedReader in;

    //method 1 - only saves CSV files
    public void exportCSV(File csv, TreeMap<Date, TreeSet<Event>> events){
        try{
            out = new PrintWriter(new FileWriter(csv));

            for (Date d : events.keySet()) {
                Calendar c = Calendar.getInstance();
                c.setTime(d);
                int month = c.get(Calendar.MONTH) + 1;
                int year = c.get(Calendar.YEAR);
                int day = c.get(Calendar.DAY_OF_MONTH);
                int hour = c.get(Calendar.HOUR);
                int minutes = c.get(Calendar.MINUTE);
                int seconds = c.get(Calendar.SECOND);

                for (Event event : events.get(d)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(month + "/" + day + "/" + year + "/" + hour + ":" + minutes + ":" + seconds);
                    sb.append(",");
                    sb.append(event.getName());
                    sb.append(",");
                    sb.append(Integer.toString(event.getColor().getRGB()));
                    out.println(sb.toString());
                }
            }
            System.out.println("Events successfully saved.");

        } catch (Exception e) {
            System.out.println("Failed to create CSV file.");
            e.printStackTrace();
        } finally {
            out.close();
        }

    }

    //method 2 - saves only PSV files
    public void exportPSV(File psv, TreeMap<Date, TreeSet<Event>> events ) {
        try {
            out = new PrintWriter(new FileWriter(psv));

            for (Date d : events.keySet()) {
                Calendar c = Calendar.getInstance();
                c.setTime(d);
                int month = c.get(Calendar.MONTH) + 1;
                int year = c.get(Calendar.YEAR);
                int day = c.get(Calendar.DAY_OF_MONTH);
                for (Event event : events.get(d)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(event.getName());
                    sb.append("|");
                    sb.append(month + "/" + day + "/" + year);
                    sb.append("|");
                    sb.append(Integer.toString(event.getColor().getRGB()));
                    out.println(sb.toString());
                }
            }
            System.out.println("Events successfully saved.");

        } catch (Exception e) {
            System.out.println("Failed to create CSV file.");
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    public ArrayList<Event> importCSV(File csv) {
        ArrayList<Event> events = new ArrayList<>();
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(csv),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                String[] fields = line.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
                String[] dateFormat = fields[0].split("/");
                int month = Integer.parseInt(dateFormat[0].trim());
                int year = Integer.parseInt(dateFormat[2].trim());
                int day = Integer.parseInt(dateFormat[1].trim());
                Calendar c = Calendar.getInstance();
                c.set(year, month - 1, day, 0, 0, 0);
                c.set(Calendar.MILLISECOND, 0);
                Date date = c.getTime();
                String name = fields[1].trim();
                Color color;
                String clr = fields[2].trim();
                if (clr.equals("red")) {
                    color = new Color(255, 0, 0);
                } else if (clr.equals("blue")) {
                    color = new Color(0, 0, 255);
                } else if (clr.equals("green")) {
                    color = new Color(0, 255, 0);
                } else {
                    color = new Color(Integer.parseInt(clr));
                }
                Event e = new Event(date, name, color);
                events.add(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return events;
    }

    public ArrayList<Event> importPSV(File psv) {
        ArrayList<Event> events = new ArrayList<>();
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(psv),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                String[] fields = line.split("\\|");
                for (int i = 0; i < fields.length; i++)
                    System.out.println(fields[i]);
                String[] dateFormat = fields[1].split("/");
                int month = Integer.parseInt(dateFormat[0].trim());
                int year = Integer.parseInt(dateFormat[2].trim());
                int day = Integer.parseInt(dateFormat[1].trim());
                Calendar c = Calendar.getInstance();
                c.set(year, month - 1, day, 0, 0, 0);
                c.set(Calendar.MILLISECOND, 0);
                Date date = c.getTime();
                String name = fields[0].trim();
                Color color;
                String clr = fields[2].trim();
                if (clr.equals("red")) {
                    color = new Color(255, 0, 0);
                } else if (clr.equals("blue")) {
                    color = new Color(0, 0, 255);
                } else if (clr.equals("green")) {
                    color = new Color(0, 255, 0);
                } else {
                    color = new Color(Integer.parseInt(clr));
                }
                Event e = new Event(date, name, color);
                events.add(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return events;
    }
}
