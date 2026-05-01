package io.babyredis.protocol;

import io.babyredis.error.BabyRedisException;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

public class RespDecoderTest {

    @Test
    public void decodeStringReturnsCorrect() throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader("+OK\r\n"));

        String testResponse = RespDecoder.decodeString(reader);

        assertEquals("OK", testResponse);
    }
    @Test
    public void decodeIntegerReturnsCorrect() throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader(":46\r\n"));

        int testResponse = RespDecoder.decodeInteger(reader);

        assertEquals(46, testResponse);
    }
    @Test
    public void decodeBulkStringReturnsCorrect() throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader("$11\r\nhello world\r\n"));
        String testResponse = RespDecoder.decodeBulkString(reader);
        assertEquals("hello world", testResponse);


    }
    @Test
    public void decodeArrayReturnsCorrect() throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader("*2\r\n$5\r\nLorem\r\n$5\r\nIpsum\r\n"));
        String[] testResponse = RespDecoder.decodeArray(reader);

        assertEquals(2, testResponse.length);
        assertEquals("Lorem", testResponse[0]);
        assertEquals("Ipsum", testResponse[1]);

    }

    @Test
    public void errorMessagesThrows() throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader("-ERR NOT FOUND"));

        assertThrows(BabyRedisException.class, () -> {
            RespDecoder.decodeString(reader);
        });

    }
    @Test
    public void prefixMismatchThrows(){
        BufferedReader reader = new BufferedReader(new StringReader(":42"));

        assertThrows(BabyRedisException.class, () -> {
            RespDecoder.decodeString(reader);
        });

    }

}
