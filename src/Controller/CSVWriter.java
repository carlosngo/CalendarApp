package Controller;

import Model.Event;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeMap;
import java.util.TreeSet;

public class CSVWriter implements FileStrategy {
    @Override
    public void execute(File file, TreeMap<Calendar, TreeSet<Event>> events) {
        PrintWriter out = null;
        try{
            out = new PrintWriter(new FileWriter(file));
            for (Calendar c : events.keySet()) {
                int month = c.get(Calendar.MONTH) + 1;
                int year = c.get(Calendar.YEAR);
                int day = c.get(Calendar.DAY_OF_MONTH);
                int hour = c.get(Calendar.HOUR);
                int minutes = c.get(Calendar.MINUTE);
                int seconds = c.get(Calendar.SECOND);
                for (Model.Event event : events.get(c)) {
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
}
