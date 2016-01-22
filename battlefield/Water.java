package lesson5_8.tank5.battlefield;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

public class Water extends FieldObject {

    private final static String IMAGE_NAME = "water.png";
    private Image myImage;

    public Water(int y, int x) {

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

        Graphics2D g2d = (Graphics2D) g.create();
        Composite compositeOriginal = g2d.getComposite();
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);
        g2d.setComposite(alphaComposite);
        g.drawImage(myImage, x, y, new ImageObserver() {
            @Override
            public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                return false;
            }
        });
        g2d.setComposite(compositeOriginal);
    }
}