package Controller;

import Model.*;
import java.io.*;
import java.util.*;
public interface FileStrategy {
    public void execute(File file, ArrayList<Event> events);
}
