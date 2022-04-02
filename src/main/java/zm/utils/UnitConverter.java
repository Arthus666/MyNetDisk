package zm.utils;

import java.text.DecimalFormat;

/**
 * @author Arthus
 */
public class UnitConverter {

    public static String convert(long size) {

        long GB = 1024 * 1024 * 1024;
        long MB = 1024 * 1024;
        long KB = 1024;

        //格式化小数
        DecimalFormat df = new DecimalFormat("0.00");

        String resultSize = "";

        if (size / GB >= 1) {
            //如果当前Byte的值大于等于1GB
            resultSize = df.format(size / (double) GB) + " GB";
        } else if (size / MB >= 1) {
            //如果当前Byte的值大于等于1MB
            resultSize = df.format(size / (double) MB) + " MB";
        } else if (size / KB >= 1) {
            //如果当前Byte的值大于等于1KB
            resultSize = df.format(size / (double) KB) + " KB";
        } else {
            //如果当前Byte的值大于等于1MB
            resultSize = size + " MB";
        }
        return resultSize;
    }
}
