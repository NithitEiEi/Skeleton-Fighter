package constant;


import java.io.File;

public class Constant {

    
    public static class MapConstant{
        
        public static final int finalChapter = 2;
        private static final int axe1 = 7;
        private static final int axe2 = 3;
        private static final File chapter0 = new File("res/map/mountain.png");
        private static final File chapter1 = new File("res/map/forest.png");
        private static final File chapter2 = new File("res/map/castle.png");

        public static final int[] randomAxe = {axe1,axe2};
        public static final File[] chapter = {chapter0, chapter1, chapter2};
        public static final String[][] enemyList = 
        {
            {"villager","villager","slime","slime"}, 
            {"villager","villager","knight","villager","knight"},
            {"villager", "knight","knight","knight","slime","knight","knight"}
        };


        public static int getYLocation(int chapter){
            switch (chapter) {
                case 0:
                    return 400;
                case 1, 2:
                    return 350;
            }
            return 400;
        }
    }

    public static class ActionConstant{
        // for player and other
        public static final int ALEFT = 0;
        public static final int ARIGHT = 1;
        public static final int ATOP = 2;
        public static final int GSIDE = 3; 
        public static final int GTOP = 4;
        public static final int IDLE = 5;
        public static final int WALK = 6;
        public static final int LAYDOWN = 7;
        // for knight
        public static final int PIERCE = 3;
        public static final int DEAD = 4;
        //for villager
        public static final int VILLDEAD = 3;
        public static final int VILLIDLE = 4;
        public static final int VILLWALK = 5;
        //for slime
        public static final int SLIMEATK = 0;
        public static final int SLIMEWALK = 1;
        public static final int SLIMEDEAD = 2;

        
        public static int getSpriteFrames(int value){
            if(value == ALEFT || value == ARIGHT || value == ATOP) {
                return 8; // 8 frames
            } else if(value == GSIDE || value == GTOP) {
                return 6; // 6 frames
            } else if(value == IDLE) {
                return 4; // 4 frames
            } else if(value == WALK) {
                return 9; // 9 frames
            } else {
                return 1;
            }
        }

        public static int getSlimeFrame(int value){
            if(value == SLIMEATK) {
                return 9;
            } else if(value == SLIMEDEAD) {
                return 8;
            } else if(value == SLIMEWALK) {
                return 4;
            }
             else {
                return 1;
            }
        }

        public static int getKnightFrame(int value){
            if(value == ALEFT || value == ARIGHT || value == ATOP) {
                return 8; // 8 frames
            } else if(value == PIERCE || value == WALK || value == DEAD) {
                return 9;
            } else if(value == IDLE || value == LAYDOWN) {
                return 4;
            }
             else {
                return 1;
            }
        }

        public static int getVillagerFrames(int value){
            if(value == ALEFT || value == ARIGHT || value == ATOP) {
                return 10;
            } else if(value == VILLWALK || value == VILLDEAD) {
                return 9;
            } else if(value == VILLIDLE) {
                return 4;
            }
             else {
                return 1;
            }
        }
    }

}
