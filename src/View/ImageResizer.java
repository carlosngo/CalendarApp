package View;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageResizer {
    public static BufferedImage resizeImage(File img, int width, int height) {
        try{
            BufferedImage rawHolder = ImageIO.read(img);
            Image raw = rawHolder.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = resized.createGraphics();
            g2d.drawImage(raw, 0, 0, null);
            g2d.dispose();
            return resized;
        }
        catch(IOException e){
            System.out.println("File not found.");
            return null;
        }
    }

}
