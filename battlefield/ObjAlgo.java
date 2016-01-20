package lesson5_8.tank5.battlefield;



public class ObjAlgo {

        private int num;
        private boolean isMark = false;

        public ObjAlgo() {
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public boolean isMark() {
            return isMark;
        }

        public void setIsMark(boolean isMark) {
            this.isMark = isMark;
        }

        @Override
        public String toString() {
            return getNum() + " " ;
        }
}
