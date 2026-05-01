package io.babyredis.protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class RespDecoder {

    private static void checkPrefixMismatch(boolean expected, String message) {
        if (expected) {
            // TODO: Move BabyRedisException from Client to Protocol
            throw new RuntimeException(message);
        }
    }

    public static String decodeString(BufferedReader reader) throws IOException {
        String encoded = reader.readLine();
        char prefix = encoded.charAt(0);

        checkPrefixMismatch(prefix == '-', "Error message was given: " + encoded);

        checkPrefixMismatch(prefix != '+', "Prefix does not match method signature");
        return encoded.substring(1);
    }

    public static int decodeInteger(BufferedReader reader) throws IOException {
        String encoded = reader.readLine();

        char prefix = encoded.charAt(0);

        checkPrefixMismatch(prefix == '-', "Error message was given: " + encoded);

        checkPrefixMismatch(prefix != ':', "Prefix does not match method signature");

        return Integer.parseInt(encoded.substring(1));
    }


    public static String[] decodeArray(BufferedReader reader) throws IOException {
        String head = reader.readLine();

        char prefix = head.charAt(0);

        checkPrefixMismatch(prefix == '-', "Error message was given: " + head);
        checkPrefixMismatch(prefix != '*', "Prefix does not match method signature");

        int numItems = Integer.parseInt(head.substring(1));


        ArrayList<String> response = new ArrayList<String>();

        for (int i = 0; i < numItems; i++){
            response.add(decodeBulkString(reader));
        }

        return response.toArray(new String[0]);
    }

    public static String decodeBulkString(BufferedReader reader) throws IOException {

        String head = reader.readLine();

        char prefix = head.charAt(0);

        checkPrefixMismatch(prefix == '-', "Error message was given: " + head);
        checkPrefixMismatch(prefix != '$', "Prefix does not match method signature");

        int length = Integer.parseInt(head.substring(1));

        String value = reader.readLine();

        if(value.length() != length) throw new RuntimeException("String length mismatch");

        return value;
    }
}
