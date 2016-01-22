package lesson5_8.tank5.battlefield;


import java.awt.*;

public class BattleField implements Drawable {

    private int BF_WIDTH = 576;
    private int BF_HEIGHT = 576;

    private String[][] battleField = {
            {" ", " ", "B", "B", "B", "B", "B", "B", "B"},
            {"B", " ", "W", " ", " ", " ", "W", " ", "B"},
            {"B", "B", " ", " ", "B", " ", " ", "B", "B"},
            {"B", " ", " ", " ", " ", " ", "B", "R", "B"},
            {"B", " ", " ", " ", "W", " ", " ", " ", "B"},
            {"R", " ", " ", "B", " ", "B", "B", "B", "B"},
            {" ", "B", "W", "B", "B", "B", " ", "B", "B"},
            {" ", " ", " ", " ", " ", " ", " ", " ", "B"},
            {"B", " ", " ", " ", "E", " ", " ", " ", "B"}};

    private FieldObject[][] objBattleField = new FieldObject[9][9];


    public BattleField() {

        initObjectBattleField();
    }

    public void setBattleField(String[][] battleField) {
        this.battleField = battleField;
    }

    public FieldObject[][] getBattleField() {
        return objBattleField;
    }

    public int getBF_WIDTH() {
        return BF_WIDTH;
    }

    public int getBF_HEIGHT() {
        return BF_HEIGHT;
    }

    public FieldObject scanQuadrant(int v, int h) {

        return objBattleField[v][h];
    }

    public void updateQuadrant(int v, int h, FieldObject f) {

        objBattleField[v][h] = f;
    }

    public  int getDimensionY() {

        return battleField.length;
    }

    public  int getDimensionX() {

        return battleField[0].length;
    }

    public void draw (Graphics g) {

        for (int j = 0; j < this.getDimensionY(); j++) {
            for (int k = 0; k < this.getDimensionX(); k++) {
                String coordinates = getQuadrantXY(j + 1, k + 1);
                int separator = coordinates.indexOf("_");
                int y = Integer.parseInt(coordinates.substring(0, separator));
                int x = Integer.parseInt(coordinates.substring(separator + 1));

                if (this.scanQuadrant(j, k) != null) {

                    objBattleField[j][k].draw(g);
                }
            }
        }
    }

    public String getQuadrantXY(int v, int h) {

        return (v - 1) * 64 + "_" + (h - 1) * 64;
    }

    public void initObjectBattleField() {

        for (int i = 0; i < battleField.length; i ++) {
            for (int j = 0; j < battleField[i].length; j ++) {

                if (battleField[i][j].equalsIgnoreCase("B")) {
                    objBattleField[i][j] = new Brick(i * 64, j * 64);
                }

                else if (battleField[i][j].equalsIgnoreCase("R")) {
                    objBattleField[i][j] = new Rock(i * 64, j * 64);
                }

                else if (battleField[i][j].equalsIgnoreCase("W")) {
                    objBattleField[i][j] = new Water(i * 64, j * 64);
                }
                else if (battleField[i][j].equalsIgnoreCase("E")) {
                    objBattleField[i][j] = new Eagle(i * 64, j * 64);
                }
                else if (battleField[i][j].equalsIgnoreCase(" ")) {
                    objBattleField[i][j] = new ClearField(i * 64, j * 64);
                }
            }
        }
    }

    public void destroyObject(int v, int h) {

        objBattleField[v][h].destroy();
    }

    public String[][] getStringBattleField() {
        return battleField;
    }
}
