package lesson5_8.tank5.tank;

import lesson5_8.tank5.actionfield.ActionField;
import lesson5_8.tank5.battlefield.BattleField;

import java.awt.*;

public class T34 extends AbstractTank {

    public T34(ActionField actionField, BattleField battleField) {

        super(actionField, battleField);
        tankColor = new Color(0, 255, 0);
        towerColor = new Color(255, 0, 0);
    }

    public T34(ActionField actionField, BattleField battleField, int x, int y, Direction direction) {
        super(actionField, battleField, x, y, direction);
        tankColor = new Color(0, 255, 0);
        towerColor = new Color(255, 0, 0);
    }

    public Object[] actions = new Object[]{
            Direction.DOWN,
            Action.FIRE,
            Action.MOVE,
            Action.MOVE,
            Action.MOVE,
            Action.MOVE,
            Action.MOVE,
            Action.MOVE,
            Direction.RIGHT,
            Action.FIRE,
            Action.FIRE
    };

    private int step = 0;

    @Override
    public Action setUp() {

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

