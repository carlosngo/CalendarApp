package Controller;

import Model.Event;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TreeMap;
import java.util.TreeSet;

public class CSVWriter implements FileStrategy {
    @Override
    public void execute(File file, ArrayList<Event> events) {
        PrintWriter out = null;
        try{
            out = new PrintWriter(new FileWriter(file));
            for (Event event : events) {
                Calendar c = event.getDate();
                int month = c.get(Calendar.MONTH) + 1;
                int year = c.get(Calendar.YEAR);
                int day = c.get(Calendar.DAY_OF_MONTH);
                int hour = c.get(Calendar.HOUR);
                int minutes = c.get(Calendar.MINUTE);
                int seconds = c.get(Calendar.SECOND);
                StringBuilder sb = new StringBuilder();
                sb.append(month + "/" + day + "/" + year + "/" + hour + ":" + minutes + ":" + seconds);
                sb.append(",");
                sb.append(event.getName());
                sb.append(",");
                sb.append(Integer.toString(event.getTextColor().getRGB()));
                sb.append(",");
                sb.append(Integer.toString(event.getBackgroundColor().getRGB()));
                sb.append(",");
                sb.append(event.getInterval());
                out.println(sb.toString());
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
