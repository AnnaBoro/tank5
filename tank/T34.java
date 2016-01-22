package lesson5_8.tank5.tank;

import lesson5_8.tank5.actionfield.ActionField;
import lesson5_8.tank5.battlefield.Algo1;
import lesson5_8.tank5.battlefield.Algo2;
import lesson5_8.tank5.battlefield.BattleField;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class T34 extends AbstractTank {


    public T34(ActionField actionField, BattleField battleField) {

        super(actionField, battleField);
        IMAGE_NAME_UP = "t34_up.png";
        IMAGE_NAME_DOWN = "t34_down.png";
        IMAGE_NAME_LEFT = "t34_lft.png";
        IMAGE_NAME_RIGHT = "t34_rght.png";
        try {
            myTankImageUp = ImageIO.read(new File(IMAGE_NAME_UP));
            myTankImageDown = ImageIO.read(new File(IMAGE_NAME_DOWN));
            myTankImageLeft = ImageIO.read(new File(IMAGE_NAME_LEFT));
            myTankImageRight = ImageIO.read(new File(IMAGE_NAME_RIGHT));
        }
        catch (IOException e) {
            System.err.print("Can't find image ");
        }
        algo2 = new Algo2(battleField.getStringBattleField(), actionField, this);
    }

    public T34(ActionField actionField, BattleField battleField, int x, int y, Direction direction) {

        super(actionField, battleField, x, y, direction);
        IMAGE_NAME_UP = "t34_up.png";
        IMAGE_NAME_DOWN = "t34_down.png";
        IMAGE_NAME_LEFT = "t34_lft.png";
        IMAGE_NAME_RIGHT = "t34_rght.png";
        try {
            myTankImageUp = ImageIO.read(new File(IMAGE_NAME_UP));
            myTankImageDown = ImageIO.read(new File(IMAGE_NAME_DOWN));
            myTankImageLeft = ImageIO.read(new File(IMAGE_NAME_LEFT));
            myTankImageRight = ImageIO.read(new File(IMAGE_NAME_RIGHT));
        }
        catch (IOException e) {
            System.err.print("Can't find image ");
        }
        algo2 = new Algo2(battleField.getStringBattleField(), actionField,this);
    }

    public Object[] actions;

    private int step = 0;

    @Override
    public Action setUp() {

        if (actions == null) {
            List<int[]> path = algo2.movePath();
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

