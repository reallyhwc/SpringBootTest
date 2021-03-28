package com.xuhu.springboodemo.core.JDKUtil;

import java.util.Random;

/**
 * @author xuhu
 * @date 2021-03-28 15:11
 */
public class LocalStringUtils {

    public static String genRandomNum(int len) {
        int i;
        int count = 0;
        char[] str = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        int maxNum = str.length;
        StringBuilder pwd = new StringBuilder("");
        Random r = new Random();
        while (count < len) {
            i = Math.abs(r.nextInt(maxNum));
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }
}
