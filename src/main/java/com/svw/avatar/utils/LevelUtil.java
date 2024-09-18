package com.svw.avatar.utils;


import com.svw.avatar.customize.views.Item;


public class LevelUtil {

    public static int CLASSIC_MODE_MAX_LEVEL = 15;//经典模式关卡数


    /**
     * 经典模式获取默认的树头坐标
     */
    public static int[][] getDefaultFencePosition(int verticalCount, int horizontalCount, int level) {
        int[][] data = new int[verticalCount][horizontalCount];
        switch (level) {
            case 1:
                data[1][2] = data[1][3] = data[1][4] = data[1][5] = data[2][1] = data[2][2] = data[2][6] = data[3][2] = data[5][2] = data[5][3] = data[5][4] = data[4][2]
                        = Item.STATE_SELECTED;
                break;

            case 2:
                data[2][2] = data[1][2] = data[0][1] = data[2][1] = data[1][1] = data[2][0] = data[2][6] = data[1][7] = data[2][7] = data[1][8] = data[0][7] = data[2][8] = data[0][7] = data[6][1] = data[6][2] = data[6][0] = data[8][1] = data[7][2] = data[7][1] = data[6][6] = data[6][7] = data[6][8] = data[7][7] = data[7][8] = data[8][7] = data[2][4] = data[4][2] = data[6][4] = data[4][6] = Item.STATE_SELECTED;
                break;

            case 3:
                data[1][7] = data[1][5] = data[3][3] = data[4][8] = data[5][8] = data[7][6] = data[6][3] = data[7][2] = data[7][3] = data[8][2] = Item.STATE_SELECTED;
                break;

            case 4:
                data[6][6] = data[7][7] = data[6][7] = data[1][6] = data[1][5] = data[6][1] = data[7][2] = data[6][2] = data[3][1] = data[3][2] = Item.STATE_SELECTED;
                break;

            case 5:
                data[1][5] = data[1][4] = data[5][5] = data[7][7] = data[8][7] = data[7][8] = data[7][6] = data[6][2] = data[5][8] = Item.STATE_SELECTED;
                break;

            case 6:
                data[0][5] = data[1][5] = data[2][4] = data[7][6] = data[7][5] = data[7][4] = data[8][3] = data[2][1] = data[3][1] = Item.STATE_SELECTED;
                break;

            case 7:
                data[4][6] = data[3][6] = data[4][5] = data[2][5] = data[1][6] = data[1][7] = data[6][5] = data[6][6] = data[6][7] = data[5][8] = Item.STATE_SELECTED;
                break;

            case 8:
                data[5][3] = data[5][2] = data[5][1] = data[6][1] = data[7][2] = data[7][3] = data[7][4] = data[7][5] = data[0][0] = data[1][1] = data[1][0] = data[1][1] = Item.STATE_SELECTED;
                break;

            case 9:
                data[8][0] = data[7][2] = data[6][0] = data[0][0] = data[1][2] = data[2][0] = data[0][8] = data[0][6] = data[2][7] = Item.STATE_SELECTED;
                break;

            case 10:
                data[2][1] = data[5][2] = data[7][2] = data[1][4]
                        = data[2][5] = data[3][5] = data[4][5] = data[6][5] = data[8][1] = data[8][5]
                        = Item.STATE_SELECTED;
                break;

            case 11:
                data[1][6] = data[2][6] = data[1][3] = data[1][5] = data[7][6] = data[6][6] = data[7][5] = data[3][2] = data[4][1] = data[4][2] = data[5][2] = data[4][6] = data[6][3] = data[5][5] = data[3][4] = Item.STATE_SELECTED;
                break;

            case 12:
                data[4][5] = data[4][6] = data[7][7] = data[6][7] = data[2][2] = data[3][2] = data[3][3] = data[6][6] = data[5][7] = data[6][1] = data[6][2] = data[8][4] = data[1][6] = data[0][4] = data[0][5] = data[1][7] = Item.STATE_SELECTED;
                break;

            case 13:
                data[4][1] = data[2][6] = data[1][3] = data[6][5] = data[6][4] = data[1][4] = data[3][7] = data[3][6] = data[4][7] = data[5][7] = data[4][6] = Item.STATE_SELECTED;
                break;
            case 14:
                data[4][2] = data[4][1] = data[4][0] = data[4][6] = data[6][4] = data[7][4] = data[7][5] = data[8][4] = data[0][4] = data[1][4] = data[1][5] = data[2][4] = data[1][8] = data[8][7] = data[6][7] = data[6][1] = data[2][1] = Item.STATE_SELECTED;

            case 15:
                data[3][4] = data[3][5] = data[5][5] = data[6][5] = Item.STATE_SELECTED;
                break;


            default:
                data[2][1] = data[2][2] = data[3][2] = data[4][2] = data[5][2] = data[6][2] = data[7][2] = data[1][4]
                        = data[2][5] = data[3][5] = data[4][5] = data[5][5] = data[6][5] = data[8][1] = data[8][2] = data[8][3] = data[8][4] = data[8][5]
                        = Item.STATE_SELECTED;
                break;
        }
        return data;
    }
}
