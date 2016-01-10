package lesson5_8.tank5.tank;

import lesson5_8.tank5.actionfield.ActionField;
import lesson5_8.tank5.battlefield.BattleField;
import lesson5_8.tank5.battlefield.Brick;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

public abstract class AbstractTank implements Tank {


    private Direction direction;

    private int x = 128;
    private int y = 512;

    private int speed = 10;
    private boolean destroyed;

    protected Color tankColor;
    protected Color towerColor;
    private final static String IMAGE_NAME_UP = "t34_up.png";
    private final static String IMAGE_NAME_DOWN = "t34_down.png";
    private final static String IMAGE_NAME_LEFT = "t34_lft.png";
    private final static String IMAGE_NAME_RIGHT = "t34_rght.png";
    private Image myTankImage1;
    private Image myTankImage2;
    private Image myTankImage3;
    private Image myTankImage4;

//    нет actionField
    private ActionField actionField;
    private BattleField battleField;

    public AbstractTank() {

        try {
            myTankImage1 = ImageIO.read(new File(IMAGE_NAME_UP));
            myTankImage2 = ImageIO.read(new File(IMAGE_NAME_DOWN));
            myTankImage3 = ImageIO.read(new File(IMAGE_NAME_LEFT));
            myTankImage4 = ImageIO.read(new File(IMAGE_NAME_RIGHT));
        }
        catch (IOException e) {
            System.err.print("Can't find image ");
        }
    }

    public AbstractTank(ActionField actionField, BattleField battleField) {
        this(actionField, battleField, 128, 128, Direction.UP);
        towerColor = new Color(70, 70, 70);
        tankColor = new Color(0, 255, 0);
        try {
            myTankImage1 = ImageIO.read(new File(IMAGE_NAME_UP));
            myTankImage2 = ImageIO.read(new File(IMAGE_NAME_DOWN));
            myTankImage3 = ImageIO.read(new File(IMAGE_NAME_LEFT));
            myTankImage4 = ImageIO.read(new File(IMAGE_NAME_RIGHT));
        }
        catch (IOException e) {
            System.err.print("Can't find image ");
        }
    }

    public AbstractTank(ActionField actionField, BattleField battleField, int x, int y, Direction direction) {

        this.actionField = actionField;
        this.battleField = battleField;
        this.x = x;
        this.y = y;
        this.direction = direction;
        towerColor = new Color(70, 70, 70);
        tankColor = new Color(0, 255, 0);
        try {
            myTankImage1 = ImageIO.read(new File(IMAGE_NAME_UP));
            myTankImage2 = ImageIO.read(new File(IMAGE_NAME_DOWN));
            myTankImage3 = ImageIO.read(new File(IMAGE_NAME_LEFT));
            myTankImage4 = ImageIO.read(new File(IMAGE_NAME_RIGHT));
        }
        catch (IOException e) {
            System.err.print("Can't find image ");
        }
    }

    @Override
    public void draw(Graphics g) {

        if (!destroyed) {

            if (this.getDirection().getId() == 1) {
                g.drawImage(myTankImage1, this.getX(), this.getY(), new ImageObserver() {
                    @Override
                    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                        return false;
                    }
                });
            } else if (this.getDirection().getId() == 2) {
                g.drawImage(myTankImage2, this.getX(), this.getY(), new ImageObserver() {
                    @Override
                    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                        return false;
                    }
                });
            } else if (this.getDirection().getId() == 3) {
                g.drawImage(myTankImage3, this.getX(), this.getY(), new ImageObserver() {
                    @Override
                    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                        return false;
                    }
                });
            } else {
                g.drawImage(myTankImage4, this.getX(), this.getY(), new ImageObserver() {
                    @Override
                    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                        return false;
                    }
                });
            }
        }
    }

    public void destroy() {

        x = -100;
        y = -100;
        destroyed = true;
    }

    public void turn(Direction direction) {

        this.direction = direction;
        actionField.processTurn(this);
    }

    public void move() throws InterruptedException {

        actionField.processMove(this);
    }

    public Bullet fire() throws InterruptedException {

        Bullet bullet = new Bullet(x + 25, y + 25, direction);
        actionField.processFire(bullet);
        return bullet;
    }

    public void clean() throws InterruptedException {

        turn(Direction.LEFT);
        while (isEmptyX()) {
            fire();
        }

        moveToQuadrant(getY(), 0);
        turn(Direction.UP);
        while (isEmptyY()) {
            fire();
        }

        moveToQuadrant(0, 0);
        turn(Direction.RIGHT);
        while (isEmptyX()) {
            fire();
        }

        for (int i = 0; i < battleField.getBattleField().length; i++) {
            moveToQuadrant(0, i * 64);
            turn(Direction.DOWN);
            while (isEmptyY()) {
                fire();
            }
        }
    }

    public boolean isEmptyY() {

        int index = getX() / 64;
        int firstPoint = 0;
        int endPoint = getY() / 64;

        if (getDirection() == Direction.DOWN) {
            firstPoint = getY() / 64;
            endPoint = battleField.getBattleField()[index].length;
        }

        for (int i = firstPoint; i < endPoint; i++) {

            if (battleField.getBattleField()[i][index] instanceof Brick) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmptyX() {

        int index = getY() / 64;
        int firstPoint = 0;
        int endPoint = getX() / 64;

        if (getDirection() == Direction.RIGHT) {
            firstPoint = getX() / 64;
            endPoint = battleField.getBattleField()[index].length;
        }

        for (int i = firstPoint; i < endPoint; i++) {

            if (battleField.getBattleField()[index][i] instanceof Brick) {
                return true;
            }
        }
        return false;
    }

    public int[] getRandomQuadrant() {

        int[] randomNumbers = getRandomNumbers();
        for (int i = 0; i < randomNumbers.length; i++) {

            if (randomNumbers[i] > 8) {
                randomNumbers[i] = randomNumbers[i] - 1;
            }
        }
        return randomNumbers;
    }

    public int[] getRandomNumbers() {

        String randNum = String.valueOf(System.currentTimeMillis());
        String randNum1 = randNum.substring(randNum.length()-1);
        String randNum2 = randNum.substring(randNum.length()-2, randNum.length()-1);
        int randNumInt1 = Integer.parseInt(randNum1);
        int randNumInt2 = Integer.parseInt(randNum2);
        int[] randomNumbers = {randNumInt1, randNumInt2};
        return randomNumbers;
    }

    public Direction getRandomDirection(){

        int[] randomNumbers = getRandomNumbers();
        int randNumInt1 = randomNumbers[0];
        int randNumInt2 = randomNumbers[1];

        if (randNumInt1 > randNumInt2) {

            if (randNumInt1 % 2 == 0) {
                direction = Direction.DOWN;
            }
            else {
                direction = Direction.UP;
            }
        }
        else {

            if (randNumInt2 % 2 == 0) {
                direction = Direction.LEFT;
            }
            else {
                direction = Direction.RIGHT;
            }
        }
        return direction;
    }

    public void moveRandom() throws InterruptedException {

        while (true) {
            turn(getRandomDirection());
            move();
        }
    }

    public void moveToQuadrant(int v, int h) throws InterruptedException {

        String quadrant = actionField.getQuadrant(v, h);
        int lineIndex = quadrant.indexOf("_");
        int tankXNew = 64 * Integer.parseInt(quadrant.substring(0, lineIndex));
        int tankYNew = 64 * Integer.parseInt(quadrant.substring(lineIndex+1));

        if ((tankXNew - getX()) > 0) {
            int steps = (tankXNew - getX()) / 64;
            for (int step = 0; step < steps; step++) {
                turn(Direction.RIGHT);
                if ((battleField.getBattleField()[getY() / 64][getX() / 64 + 1] instanceof Brick) ||
                        (actionField.getTank().getY() / 64 == actionField.getAgressor().getY() / 64) &&
                                ((actionField.getTank().getX() / 64 + 1) == actionField.getAgressor().getX() / 64)) {
                    fire();
                }
                move();
            }
        } else if ((tankXNew - getX()) < 0) {
            int steps = Math.abs((tankXNew - getX()) / 64);
            for (int step = 0; step < steps; step++) {
                turn(Direction.LEFT);
                if ((battleField.getBattleField()[getY() / 64][getX() / 64 - 1] instanceof Brick) ||
                        (actionField.getTank().getY() / 64 == actionField.getAgressor().getY() / 64) &&
                                ((actionField.getTank().getX() / 64 - 1) == actionField.getAgressor().getX() / 64)) {
                    fire();
                }
                move();
            }
        }

        if ((tankYNew - getY()) > 0) {
            int steps = (tankYNew - getY()) / 64;
            for (int step = 0; step < steps; step++) {
                turn(Direction.DOWN);
                if ((battleField.getBattleField()[getY() / 64 + 1][getX()/64] instanceof Brick) ||
                        ((actionField.getTank().getY() / 64 + 1) == actionField.getAgressor().getY() / 64) &&
                                (actionField.getTank().getX() / 64 == actionField.getAgressor().getX() / 64)) {
                    fire();
                }
                move();
            }
        } else if ((tankYNew - getY()) < 0) {
            int steps = Math.abs((getY() - tankYNew) / 64);
            for (int step = 0; step < steps; step++) {
                turn(Direction.UP);
                if ((battleField.getBattleField()[getY() / 64 - 1][getX()/64] instanceof Brick)  ||
                        ((actionField.getTank().getY() / 64 - 1) == actionField.getAgressor().getY() / 64) &&
                                (actionField.getTank().getX()/64 == actionField.getAgressor().getX() / 64)) {
                    fire();
                }
                move();
            }
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void updateX(int i) {

        if (x < 0) {
            x = 0;
        }
        x += i;
    }

    public void updateY(int i) {

        if (y < 0) {
            y = 0;
        }
        y += i;
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }
}
