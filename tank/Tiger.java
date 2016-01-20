package lesson5_8.tank5.tank;


import lesson5_8.tank5.actionfield.ActionField;
import lesson5_8.tank5.battlefield.Algo1;
import lesson5_8.tank5.battlefield.BattleField;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class Tiger extends AbstractTank {

    private int armor;

    public Tiger(ActionField actionField, BattleField battleField, int x, int y, Direction direction) {
        super(actionField, battleField, x, y, direction);
        IMAGE_NAME_UP = "tiger_up.png";
        IMAGE_NAME_DOWN = "tiger_down.png";
        IMAGE_NAME_LEFT = "tiger_left.png";
        IMAGE_NAME_RIGHT = "tiger_right.png";
        try {
            myTankImageUp = ImageIO.read(new File(IMAGE_NAME_UP));
            myTankImageDown = ImageIO.read(new File(IMAGE_NAME_DOWN));
            myTankImageLeft = ImageIO.read(new File(IMAGE_NAME_LEFT));
            myTankImageRight = ImageIO.read(new File(IMAGE_NAME_RIGHT));
        }
        catch (IOException e) {
            System.err.print("Can't find image ");
        }
        armor = 1;
        algo = new Algo1(battleField.getStringBattleField(), this);
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getArmor() {
        return armor;
    }

    public Object[] actions;

    private int step = 0;

    @Override
    public Action setUp() {

        if (actions == null) {
            List<int[]> path = algo.movePath();
            actions = new Object[path.size() - 1];

            for (int i = 0; i < path.size() - 1; i++) {
                if (path.get(i)[0] > path.get(i + 1)[0]) {
                    actions[i] = Direction.UP;
                }
                else if (path.get(i)[0] < path.get(i + 1)[0]) {
                    actions[i] = Direction.DOWN;
                }
                else if (path.get(i)[1] > path.get(i + 1)[1]) {
                    actions[i] = Direction.LEFT;
                }
                else {
                    actions[i] = Direction.RIGHT;
                }
            }
            Object[] actions2 = new Object[actions.length * 2];
            for (int i = 0, j = 0; i < actions.length && j < actions2.length - 1; i++) {
                actions2[j] = actions[i];
                if (j == actions2.length - 2) {
                    actions2[j + 1] = Action.FIRE;
                }
                else {
                    actions2[j + 1] = Action.MOVE;
                }
                j = j + 2;

            }
            actions = actions2;
            for (Object o : actions) {
                System.out.println(o);
            }
        }

        if (!(actions[step] instanceof Action)) {

            turn((Direction) actions[step++]);
        }
        return (Action) actions[step++];
    }

    @Override
    public int getMovePath() {
        return 0;
    }
}
