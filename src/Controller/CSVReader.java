package Controller;

import Model.Event;

import java.awt.*;
import java.io.*;
import java.util.*;

public class CSVReader implements FileStrategy {
    @Override
    public void execute(File file, TreeMap<Calendar, TreeSet<Event>> events) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
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
                Model.Event e = new Model.Event(c, name, color);

                if (events.containsKey(c)) {
                    events.get(c).add(e);
//                    for (Model.Event meh : events.get(d)) {
//                        System.out.println(meh.getName());
//                    }
                } else {
                    TreeSet<Model.Event> toAdd = new TreeSet<>();
                    toAdd.add(e);
                    events.put(c, toAdd);
                }
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
    }
}
