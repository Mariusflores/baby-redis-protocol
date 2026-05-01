package io.babyredis.protocol;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RespEncoderTest {

    @Test
    public void encodeStringTest(){
        String testString = RespEncoder.encodeString("hello");

        assertEquals("+hello\r\n", testString);
    }
    @Test
    public void encodeIntegerTest(){
        String testString = RespEncoder.encodeInteger(42);

        assertEquals(":42\r\n", testString);
    }
    @Test
    public void encodeArrayTest(){
        String testString = RespEncoder.encodeArray("Lorem", "Ipsum");

        assertEquals("*2\r\n$5\r\nLorem\r\n$5\r\nIpsum\r\n", testString);
    }
    @Test

    public void encodeBulkStringTest(){
        String testString = RespEncoder.encodeBulkString("hello world");

        assertEquals("$11\r\nhello world\r\n", testString);
    }

    @Test
    public void encodeErrorTest(){
        String testString = RespEncoder.encodeError("ERR NOT FOUND");

        assertEquals("-ERR NOT FOUND\r\n", testString);
    }

}
