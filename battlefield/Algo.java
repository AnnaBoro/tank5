package lesson5_8.tank5.battlefield;


import lesson5_8.tank5.tank.AbstractTank;

import java.util.List;

public abstract class Algo {

    protected String[][] battleField;
    protected ObjAlgo[][] field;
    protected int endIndexX;
    protected int endIndexY;
    protected int startIndexX;
    protected int startIndexY;

    protected int d = 0;
    protected int lengthPath;
    protected int [] pathX;
    protected int [] pathY;

    public Algo(String[][] battleField, AbstractTank tank) {
        this.battleField = battleField;
        field = new ObjAlgo[battleField.length][battleField[0].length];
    }

    protected Algo() {
    }

    public List<int[]> movePath () {

        initObjField(battleField);
        makeWave();
        return getPath();
    }

    public abstract void initObjField(String[][] ff);

    public abstract void makeWave();

    public abstract List getPath();
}
