package io.babyredis.protocol;

public class RespEncoder {

    public static String encodeString(String value){
        return String.format("+%s\r\n", value);
    }

    public static String encodeInteger(int value){
        return String.format(":%d\r\n", value);
    }

    public static String encodeArray(String... values){
        int items  = values.length;

        StringBuilder builder = new StringBuilder();
        builder.append(String.format("*%d\r\n", items));

        for (String val : values){
            builder.append(encodeBulkString(val));
        }

        return builder.toString();
    }

    public static String encodeBulkString(String string ){
        int length = string.length();

        return String.format("$%d\r\n%s\r\n", length, string);
    }

    public static String encodeError(String error){
        return String.format("-%s\r\n", error);
    }
}
