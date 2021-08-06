package castle.util;

import java.nio.charset.StandardCharsets;

/**
 * 字符串转换
 *
 * @author ran
 */
public class UtilStrTo {

    /**
     * 16进制Str转byte[]
     *
     * @param hexStr 转换字符
     * @return String.str conversion-> byte[].str
     */
    public static byte[] HexStrToByteArray(String hexStr) {
        if (hexStr == null) {
            return null;
        }
        if (hexStr.length() == 0) {
            return new byte[0];
        }
        byte[] byteArray = new byte[hexStr.length() / 2];
        for (int i = 0; i < byteArray.length; i++) {
            String subStr = hexStr.substring(2 * i, 2 * i + 2);
            byteArray[i] = ((byte) Integer.parseInt(subStr, 16));
        }
        return byteArray;
    }

    /**
     * byte[]转16进制Str
     *
     * @param byteArray 转换字节数组
     * @return byte[].str conversion-> String. str
     */
    public static String ByteArrayToHexStr(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[byteArray.length * 2];
        for (int i = 0; i < byteArray.length; i++) {
            int temp = byteArray[i] & 0xFF;
            hexChars[i * 2] = hexArray[temp >>> 4];
            hexChars[i * 2 + 1] = hexArray[temp & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     * 16进制的Str转Str
     *
     * @param hexStr 字符串类型的byte数组转str
     * @return String.byte[].str conversion-> String. str
     */
    public static String HexStrToStr(String hexStr) {
        //能被16整除,肯定可以被2整除
        byte[] array = new byte[hexStr.length() / 2];
        try {
            for (int i = 0; i < array.length; i++) {
                array[i] = (byte) (0xff & Integer.parseInt(hexStr.substring(i * 2, i * 2 + 2), 16));
            }
            hexStr = new String(array, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return hexStr;
    }

    public static byte[] int2bytes(int num){
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++){
            b[i] = (byte)(num >>> (24 - i*8));
        }
        return b;
    }
}
