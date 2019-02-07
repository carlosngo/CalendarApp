package Controller;

import Model.Event;
import Model.Holiday;

import java.awt.*;
import java.io.*;
import java.util.*;

public class CSVReader implements FileStrategy {
    @Override
    public void execute(File file, ArrayList<Event> events) {
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
                Color textColor;
                String txtClr = fields[2].trim();
                if (txtClr.equals("red")) {
                    textColor = new Color(255, 0, 0);
                } else if (txtClr.equals("blue")) {
                    textColor = new Color(0, 0, 255);
                } else if (txtClr.equals("green")) {
                    textColor = new Color(0, 255, 0);
                } else {
                    textColor = new Color(Integer.parseInt(txtClr));
                }
                Event e;
                if (fields.length > 3) {
                    int backClrRGB = Integer.parseInt(fields[3].trim());
                    if (backClrRGB == Holiday.COLOR_CODE.getRGB()) {
                        e = new Holiday(c, name, textColor);
                    } else {
                        int interval = Integer.parseInt(fields[4].trim());
                        e = new Event(c, name, textColor, interval);
                    }
                } else {
                    e = new Event(c, name, textColor);
                }

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
    }
}
