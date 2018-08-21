package com.androidex.fastble.utils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Byte2HexUtil implements Serializable {

    /**
     * 字节转十六进制为相应的字符串显示
     *
     * @param data
     * @return
     */
    public static String byte2Hex(byte data[]) {
        if (data != null && data.length > 0) {
            StringBuilder sb = new StringBuilder(data.length);
            for (byte tmp : data) {
                sb.append(String.format("%02X", tmp));
            }
            return sb.toString();
        }
        return "no data";
    }

    /**
     * 两个字节的方法
     *
     * @param n
     */
    public static byte[] int2bytes(int n) {
        int temp1 = 0, temp2 = 0;
        byte[] hex = new byte[2];
        if (n < 256) {
            hex[1] = (byte) n;
        } else {
            temp1 = n & 0xff;
            hex[1] = (byte) temp1;
            temp2 = n >> 8;
            hex[0] = (byte) temp2;
        }
        return hex;
    }

    public static String int2HexStr(int i) {
        String str = Integer.toHexString(i);
        return str.length() >= 2 ? str : 0 + str;
    }

    /**
     * 将字符串ASCII转成16进制
     *
     * @param str
     * @return
     */
    public static String convertStringToHex(String str){

        char[] chars = str.toCharArray();

        StringBuffer hex = new StringBuffer();
        for(int i = 0; i < chars.length; i++){
            hex.append(Integer.toHexString((int)chars[i]));
        }

        return hex.toString();
    }

    /**
     *  /**
     * 将16进制ASCII转成字符串
     *
     * @param hex
     * @return
     */
    public static String convertHexToString(String hex){
        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        for( int i=0; i<hex.length()-1; i+=2 ){
            String output = hex.substring(i, (i + 2));
            int decimal = Integer.parseInt(output, 16);
            sb.append((char)decimal);
            temp.append(decimal);
        }
        return sb.toString();
    }

    /**
     * 十六进制字符串转化成字节数组
     *
     * @param str
     * @return
     */
    public static byte[] hex2Bytes(String str) {
        if (str == null || str.trim().equals("")) {
            return new byte[0];
        }
        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }
        return bytes;
    }

    /**
     * 字符串转字节数组
     */
    public static byte[] str2Bytes(String s) {
        byte[] r = s.getBytes();
        return r;
    }

    /**
     * 将获取到的经纬度字节转化为原始经纬度
     * 北纬25°03.7148′   0x01 0x7E 0x09 0x5C ---> 25037148 --->2503.7148
     * 东经121°38.7451′  0x07 0x3C 0x39 0xBB --->121387451 --->12138.7451
     * 高程116.7          0x04 0x8F  ---> 1167 --->116.7
     *
     * @param b
     * @return
     */
    public static Double byte2Double(byte[] b) {
        double d = 0.0;
        int i = Integer.parseInt(Byte2HexUtil.byte2Hex(b), 16);
        if (b.length == 4) {
            d = (double) i / 10000;
        } else if (b.length == 2) {
            d = (double) i / 10;
        }
        return d;

    }

    /**
     * 字节数组转16进制字符串
     */
    public static String bytes2HexString(byte[] b) {
        String r = "";
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            r += hex.toUpperCase();
        }
        return r;
    }

    /**
     * 字符串转16进制字符串
     */
    public static String str2HexStr(String s) throws Exception{
        String r = bytes2HexString(str2Bytes(s));
        return r;
    }

    /**
     * 将十六进制字符数组转换为字节数组
     *
     * @param data 十六进制char[]
     * @return byte[]
     * @throws RuntimeException 如果源十六进制字符数组是一个奇怪的长度，将抛出运行时异常
     */
    public static byte[] decodeHex(char[] data) {
        int len = data.length;
        try {
            if ((len & 0x01) != 0) {
                throw new RuntimeException("Odd number of characters.");
            } else {
                byte[] out = new byte[len >> 1];
                // two characters form the hex value.
                for (int i = 0, j = 0; j < len; i++) {
                    int f = toDigit(data[j], j) << 4;
                    j++;
                    f = f | toDigit(data[j], j);
                    j++;
                    out[i] = (byte) (f & 0xFF);
                }
                return out;
            }
        } catch (RuntimeException e) {


        }
        return new byte[0x00];
    }

    /**
     * 将十六进制字符转换成一个整数
     *
     * @param ch    十六进制char
     * @param index 十六进制字符在字符数组中的位置
     * @return 一个整数
     * @throws RuntimeException 当ch不是一个合法的十六进制字符时，抛出运行时异常
     */
    protected static int toDigit(char ch, int index) {
        int digit = Character.digit(ch, 16);
//        BleLog.i(index+"=index");
        if (digit == -1) {
            throw new RuntimeException("Illegal hexadecimal character " + ch
                    + " at index " + index);
        }
        return digit;
    }

    /**
     * 二维数组转换为一维数组
     *
     * @param data
     * @return
     */
    public static byte[] twoArray2OneArray(byte[][] data) {
        if (data != null) {
            int dLength = data.length;
            //获取总元素个数
            int l = 0;
            for (byte[] b : data) {
                l += b.length;
            }
            //new一个一维数组，length为总元素个数
            byte[] k = new byte[l];
            //已复制的个数
            int g = 0;
            //复制二维数组每一个子数组到新的一维数组
            for (byte[] b : data) {
                System.arraycopy(b, 0, k, g, b.length);
                g += b.length;
            }
            return k;
        }
        return null;
    }

    /*
     * 字节转10进制
     */
    public static int byte2Int(byte b) {
        int r = (int) b;
        return r;
    }

    /**
     * 日期转换成字符串
     *
     * @param date
     * @return str
     */
    public static String DateToStr(Date date) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(date);
        return str;
    }

    /**
     * 字符串转换成日期
     *
     * @param str
     * @return date
     */
    public static Date StrToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 时间戳转化为Sting
     *
     * @param time
     * @return
     */
    public static String longTimeToStr(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = format.format(time);
        return d;
    }

    /**
     * 时间戳转化为Date
     *
     * @param time
     * @return
     */
    public static Date dateToStr(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = format.format(time);
        Date date = null;
        try {
            date = format.parse(d);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 时间戳转化为年月日时分秒数组
     *
     * @param time
     * @return
     */
    public static int[] longTimeToByteArray(long time) {
        String d = longTimeToStr(time);
        String spStr[] = d.split(" ", -1);
        String[] t1 = spStr[0].split("-", -1);
        String[] t2 = spStr[1].split(":", -1);
        int[] times = new int[6];
        times[0] = Integer.parseInt(t1[0].split("0")[1]);
        times[1] = Integer.parseInt(t1[1]);
        times[2] = Integer.parseInt(t1[2]);
        times[3] = Integer.parseInt(t2[0]);
        times[4] = Integer.parseInt(t2[1]);
        times[5] = Integer.parseInt(t2[2]);
        return times;
    }

    /**
     * 保留n位小数
     *
     * @param data 数据
     * @param num  保留位数
     * @return
     */
    public static String retainNPositionDecimals(double data, int num) {
        BigDecimal bd = new BigDecimal(String.valueOf(data));
        bd = bd.setScale(num, BigDecimal.ROUND_HALF_UP);
        return bd.toString();
    }
}
