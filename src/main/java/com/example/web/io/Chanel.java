package com.example.web.io;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;

/**
 * @Auther: 36560
 * @Date: 2019/8/8 :21:12
 * @Description:
 */
public class Chanel {
    private static final int BSIZE = 1024;
    public static void main(String[] args) throws IOException {
        FileChannel channel = new FileOutputStream("text.xml").getChannel();
        channel.write(ByteBuffer.wrap("some text ".getBytes()));
        channel.close();
        FileChannel rw = new RandomAccessFile("text.xml", "rw").getChannel();
        rw.position(rw.size());
        rw.write(ByteBuffer.wrap("some more".getBytes()));
        rw.close();
        FileChannel fc = new FileInputStream("text.xml").getChannel();
        ByteBuffer allocate = ByteBuffer.allocate(BSIZE);
        fc.read(allocate);
        allocate.flip();
        while (allocate.hasRemaining()){
            System.out.print((char) allocate.get());
        }
    }
    static class UsingBuffers{
        public static void SymmetricScramble(CharBuffer charBuffer){
            charBuffer.rewind();
            while (charBuffer.hasRemaining() && charBuffer.remaining()>2){
                charBuffer.mark();
                char c1=charBuffer.get();
                char c2 =charBuffer.get();
                charBuffer.reset();
                charBuffer.put(c2).put(c1);
            }
        }

        public static void main(String[] args) {
            char[] chars = "use".toCharArray();
            ByteBuffer allocate = ByteBuffer.allocate(chars.length * 2);
            CharBuffer charBuffer = allocate.asCharBuffer();
            charBuffer.put(chars);
            UsingBuffers.SymmetricScramble(charBuffer);
            charBuffer.rewind();
            while (charBuffer.hasRemaining()){
                System.out.print(charBuffer.get());
            }
        }
    }
}
