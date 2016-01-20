package lesson5_8.tank5.battlefield;


import lesson5_8.tank5.tank.AbstractTank;

import java.util.ArrayList;
import java.util.List;

public class Algo3 extends Algo{

    public Algo3(String[][] battleField, AbstractTank tank) {
        super(battleField, tank);
    }

    public void initObjField(String[][] ff) {

        for (int i = 0; i < ff.length; i++) {
            for (int j = 0; j < ff[i].length; j++) {
                if (ff[i][j].equalsIgnoreCase("E")) {
                    startIndexY = i;
                    startIndexX = j - 1;
                    endIndexY = i;
                    endIndexX = j + 1;
                    field[i][j] = new ObjAlgo();
                    field[i][j].setNum(-2);
                    field[i][j].setIsMark(true);
                    field[i][startIndexX].setNum(d);
                    field[i][startIndexX].setIsMark(true);
                } else if (ff[i][j].equalsIgnoreCase("B")) {
                    field[i][j] = new ObjAlgo();
                    field[i][j].setNum(-2);
                    field[i][j].setIsMark(true);
                } else if (ff[i][j].equalsIgnoreCase(" ")) {
                    field[i][j] = new ObjAlgo();
                    field[i][j].setNum(-1);
                }
            }
        }

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {

                System.out.print(field[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void makeWave() {

        boolean stop;
//        int dx[] = {1,0,-1,0};
//        int dy[] = {0,1,0,-1};

        do {
            stop = true;
            for (int i = 0; i < field.length; i++) {
                for (int j = 0; j < field[i].length; j++) {
                    if (field[i][j].getNum() == d) {
                        if (i < (field.length - 1) && !field[i + 1][j].isMark()) {

                            field[i + 1][j].setNum(d + 1);
                            field[i + 1][j].setIsMark(true);
                            stop = false;
                        }
                        if (i != 0 && !field[i - 1][j].isMark()) {

                            field[i - 1][j].setNum(d + 1);
                            field[i - 1][j].setIsMark(true);
                            stop = false;
                        }
                        if (j < (field[i].length - 1) && !field[i][j + 1].isMark()) {

                            field[i][j + 1].setNum(d + 1);
                            field[i][j + 1].setIsMark(true);
                            stop = false;
                        }
                        if (j != 0 && !field[i][j - 1].isMark()) {

                            field[i][j - 1].setNum(d + 1);
                            field[i][j - 1].setIsMark(true);
                            stop = false;
                        }
                    }
                }
            }
            d++;

        } while (!field[endIndexY][endIndexX].isMark() && !stop);

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {

                System.out.print(field[i][j] + " ");
            }
            System.out.println();
        }
    }

    public List getPath() {

        List <int[]> path = new ArrayList<int[]>();
        if (field[endIndexY][endIndexX].getNum() == -1) {
            System.out.println("You can't go");
            return path;
        }
        ObjAlgo len = field[endIndexY][endIndexX];

        lengthPath = len.getNum();
        pathX = new int[lengthPath + 1];
        pathY = new int[lengthPath + 1];
        int x, y;
        x = endIndexX;
        y = endIndexY;
        d = lengthPath;
        while (d > 0) {

            pathY [d] = y;
            pathX [d] = x;
            d--;

            if ((x + 1) < field[0].length && field[y][x + 1].getNum() == d) {
                x = x + 1;
            }
            if ((x - 1) >= 0 && field[y][x - 1].getNum() == d) {
                x = x - 1;
            }
            if ((y - 1) >= 0 && field[y - 1][x].getNum() == d) {
                y = y - 1;
            }
            if ((y + 1) < field.length && field[y + 1][x].getNum() == d) {
                y = y + 1;
            }

            pathY[0] = startIndexY;
            pathX[0] = startIndexX;
        }
        for(int i = lengthPath; i >= 0; i --) {
            path.add(new int[]{pathY[i], pathX[i]});
            System.out.println("__" + pathY[i] + "__" + pathX[i] + "__");
        }

        return path;
    }
}
