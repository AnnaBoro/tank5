package lesson5_8.tank5.tank;


import lesson5_8.tank5.actionfield.ActionField;
import lesson5_8.tank5.battlefield.BattleField;

import java.awt.*;

public class BT7 extends AbstractTank {

    private int speed;

    public BT7() {
    }

    public BT7(ActionField actionField, BattleField battleField) {
        this(actionField, battleField, 128, 512, Direction.UP);
        tankColor = new Color(255, 0, 0);
        towerColor = new Color(0, 255, 0);
    }

    public BT7(ActionField actionField, BattleField battleField, int x, int y, Direction direction) {
        super(actionField, battleField, x, y, direction);
        speed = super.getSpeed() / 2;
        tankColor = new Color(255, 0, 0);
        towerColor = new Color(0, 255, 0);
    }

    @Override
    public Action setUp() {
        return null;
    }

    @Override
    public int getMovePath() {
        return 0;
    }
}
