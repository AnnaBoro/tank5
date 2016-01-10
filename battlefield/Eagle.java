package lesson5_8.tank5.battlefield;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;


public class Eagle extends FieldObject {

    private final static String IMAGE_NAME = "eagle.png";
    private Image myImage;

    public Eagle(int y, int x) {

        super(y, x);
        try {
            myImage = ImageIO.read(new File(IMAGE_NAME));
        }
        catch (IOException e) {
            System.err.print("Can't find image " + IMAGE_NAME);
        }
    }

    @Override
    public void draw(Graphics g) {

        g.drawImage(myImage, x, y, new ImageObserver() {
            @Override
            public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                return false;
            }
        });
    }
}