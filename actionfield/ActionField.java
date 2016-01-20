package lesson5_8.tank5.actionfield;


import lesson5_8.tank5.battlefield.BattleField;
import lesson5_8.tank5.battlefield.Brick;
import lesson5_8.tank5.battlefield.ClearField;
import lesson5_8.tank5.battlefield.Eagle;
import lesson5_8.tank5.tank.*;
import lesson5_8.tank5.tank.Action;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class ActionField extends JPanel{

    private boolean COLORDED_MODE = false;

    private BattleField bf;
    private AbstractTank defender;
    private AbstractTank agressor;
    private AbstractTank defender2;
    private int[][] randomArr = {{64, 64}, {64, 448}, {448, 64}};
    private int randomPosition = -1;

    public ActionField() throws Exception {

        bf = new BattleField();
        int[] xy = randomArr[getRandomNum()];
        int y2 = xy[1];
        int x2 = xy[0];
        agressor = new Tiger(this, bf, x2, y2, Direction.DOWN);

        defender = new T34(this, bf);
        defender2 = new BT7(this, bf, 320, 512, Direction.UP);
        initFrame();
    }

    public void runTheGame() throws Exception {

//        while (!bf.getBattleField()[8][4].isDestroyed() && bf.getBattleField()[8][4] instanceof Eagle) {
//            processAction(defender.setUp(), defender);
//        }
        while (!bf.getBattleField()[8][4].isDestroyed() && bf.getBattleField()[8][4] instanceof Eagle) {
            processAction(defender2.setUp(), defender2);
        }
//        while (!bf.getBattleField()[8][4].isDestroyed() && bf.getBattleField()[8][4] instanceof Eagle) {
//            processAction(agressor.setUp(), agressor);
//        }
    }

    public void processAction(Action a, AbstractTank t) throws InterruptedException {

        if (a == Action.MOVE) {
            processMove(t);
        }

        else if (a == Action.FIRE) {
//            processFire(t);
            t.fire();
        }
    }

    private boolean processInterception(AbstractTank tank) throws InterruptedException {

        if (isOnTheField(tank.getBullet())) {

            if (removeBrick(false, tank)) {
                tank.getBullet().destroy();
            }

            else if (removeTank()) {
                agressor.destroy();
                tank.getBullet().destroy();
                repaint();
                Thread.sleep(3000);
                int[] xy = randomArr[getRandomNum()];
                agressor.updateX(xy[0]);
                agressor.updateY(xy[1]);
                repaint();
                ((Tiger)agressor).setArmor(1);
            }

            else if (tank instanceof Tiger && removeEagle()) {
                tank.getBullet().destroy();
            }
            return false;
        }
        return true;
    }

    public boolean isOnTheField(Bullet bullet) {

        if (bullet != null && (bullet.getX() > 0 &&  bullet.getX() < 575)
                && (bullet.getY() > 0 &&  bullet.getY() < 575)) {
            return true;
        }
        return false;
    }

    public boolean removeBrick(boolean removeType, AbstractTank tank) {

        String quadrant;

        if (removeType) {
            quadrant = getQuadrant(tank.getX(), tank.getY());
        }
        else quadrant = getQuadrant(tank.getBullet().getX(), tank.getBullet().getY());

        int i = Integer.parseInt(quadrant.substring(0, quadrant.indexOf("_")));
        int j = Integer.parseInt(quadrant.substring(quadrant.indexOf("_") + 1, quadrant.length()));

        if (bf.scanQuadrant(i, j) instanceof Brick) {
            bf.updateQuadrant(i, j, new ClearField(i, j));
            repaint();
            return true;
        }
        return false;
    }

    public boolean removeEagle() {

        String quadrant = getQuadrant(agressor.getBullet().getX(), agressor.getBullet().getY());

        int i = Integer.parseInt(quadrant.substring(0, quadrant.indexOf("_")));
        int j = Integer.parseInt(quadrant.substring(quadrant.indexOf("_") + 1, quadrant.length()));

        if (bf.scanQuadrant(i, j) instanceof Eagle) {
            bf.updateQuadrant(i, j, new ClearField(i, j));
            bf.getBattleField()[8][4].destroy();
            return true;
        }
        return false;
    }

    public boolean removeTank() throws InterruptedException {

        Bullet bullet1 = defender.getBullet();

        if (bullet1 != null) {
            String quadrant = getQuadrant(bullet1.getX(), bullet1.getY());
            String quadrant2 = getQuadrant(agressor.getX(), agressor.getY());

            if (quadrant.equalsIgnoreCase(quadrant2)) {
                if (((Tiger)agressor).getArmor() == 1) {
                    ((Tiger) agressor).setArmor(((Tiger) agressor).getArmor() - 1);
                    bullet1.destroy();
                    repaint();
                    defender.fire();
                    return false;
                } else return true;
            }
        }
        return false;
    }

    public String getQuadrant(int v, int h) {

        int x = v / 64;
        int	y = h / 64;

        return y + "_" + x;
    }

    public void initFrame() throws Exception {
        JFrame frame = new JFrame("BATTLE FIELD, Lesson 5");
        frame.setLocation(750, 150);
        frame.setMinimumSize(new Dimension(bf.getBF_WIDTH(), bf.getBF_HEIGHT() + 22));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        int i = 0;
        Color cc;
        for (int v = 0; v < 9; v++) {
            for (int h = 0; h < 9; h++) {
                if (COLORDED_MODE) {
                    if (i % 2 == 0) {
                        cc = new Color(252, 241, 177);
                    } else {
                        cc = new Color(233, 243, 255);
                    }
                } else {
                    cc = new Color(180, 180, 180);
                }
                i++;
                g.setColor(cc);
                g.fillRect(h * 64, v * 64, 64, 64);
            }
        }

        bf.draw(g);
        defender.draw(g);
        agressor.draw(g);
        defender2.draw(g);
        if (defender.getBullet() != null) {
            defender.getBullet().draw(g);
        }
        if (agressor.getBullet() != null) {
            agressor.getBullet().draw(g);
        }
    }

    public void processMove(AbstractTank tank) throws InterruptedException {

        for (int i = 0; i < 64; i++) {

            if (tank.getDirection().getId() == 1) {

                if (tank.getY() !=0) {
                    tank.updateY(-1);
                }
                else System.out.println("Wrong direction");
            }
            else if (tank.getDirection().getId() == 2) {

                if (tank.getY() != 512) {
                    tank.updateY(1);
                }
                else System.out.println("Wrong direction");
            }
            else if (tank.getDirection().getId() == 3) {

                if (tank.getX() != 0) {
                    tank.updateX(- 1);
                }
                else System.out.println("Wrong direction");
            }
            else if (tank.getDirection().getId() == 4) {

                if (tank.getX() != 512) {
                    tank.updateX(1);
                }
                else System.out.println("Wrong direction");
            }
            repaint();
            Thread.sleep(tank.getSpeed()/2);

        }
        this.removeBrick(true, tank);
    }

    public void processTurn(AbstractTank tank) {

        repaint();
    }

    public void processFire(AbstractTank tank) throws InterruptedException {

        while (isOnTheField(tank.getBullet())) {
            for (int i = 0; i < 64; ) {

                if (tank.getDirection().getId() == 1) {
                    tank.getBullet().updateY(-1);
                }
                else if (tank.getDirection().getId() == 2) {
                    tank.getBullet().updateY(1);
                }
                else if (tank.getDirection().getId() == 3) {
                    tank.getBullet().updateX(-1);
                }
                else if (tank.getDirection().getId() == 4) {
                    tank.getBullet().updateX(1);
                }
                processInterception(tank);
                repaint();
                Thread.sleep(tank.getBullet().getSpeed());
                break;
            }
        }
    }

    public int getRandomNum() {

        Random random = new Random();
        int randomInt = random.nextInt(3);
        if (randomPosition == randomInt) {
            return getRandomNum();
        }
        randomPosition = randomInt;
        return randomInt;
    }

    public AbstractTank getTank() {
        return defender;
    }

    public AbstractTank getAgressor() {
        return agressor;
    }
}
