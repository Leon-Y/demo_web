package com.example.web.io;


import java.io.*;
import java.util.Collections;
import java.util.LinkedList;

/**
 * @Auther: 36560
 * @Date: 2019/8/7 :21:18
 * @Description:
 */
public class ReaderClass {
    public static LinkedList<String> fileReader(String fileName) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        java.lang.String s;
        LinkedList<String> strings = new LinkedList<>();
        while ((s=bufferedReader.readLine())!=null){
            strings.add(s);
        }
        bufferedReader.close();
        return strings;
    }
    public static String fileReaderString(String fileName) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        String s;
        StringBuilder stringBuilder = new StringBuilder();
        while ((s=bufferedReader.readLine())!= null){
            stringBuilder.append(s+"\n");
        }
        return stringBuilder.toString();
    }
    public static void main(String[] args) {
        ReaderClass readerClass = new ReaderClass();
        try {
            LinkedList<String> strings = readerClass.fileReader("pom.xml");
            Collections.reverse(strings);
            for (String string:strings){
                System.out.println(string+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static class MemeryInput{
        public static void main(String[] args) {
            try {
                StringReader stringReader = new StringReader(fileReaderString("pom.xml"));
                byte[] bytes = fileReaderString("pom.xml").getBytes();
                int c;
                while((c=stringReader.read())!=-1){
                    System.out.print((char) c);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    static class formatMemeryInput{
        public static void main(String[] args) {
            try {
                DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(fileReaderString("pom.xml").getBytes()));
                while (true){
                    System.out.print((char) dataInputStream.readByte());
                }
            } catch (EOFException e){
                System.err.println("end");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    static class BinaryFile{
        public static byte[] read(File file) throws IOException {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            try {
                byte[] bytes = new byte[bufferedInputStream.available()];
                bufferedInputStream.read(bytes);
                return bytes;
            }finally {
                bufferedInputStream.close();
            }
        }
        public static byte[] read(String fileName) throws IOException {
            return read(new File(fileName));
        }

        public static void main(String[] args) {
            try {
                byte[] read = read("pom.xml");
                for (int i =0;i<read.length;i++){
                    System.out.print((char)read[i]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        static class Echo{
            public static void main(String[] args) throws IOException {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                String s;
                while ((s=bufferedReader.readLine())!=null){
                    System.out.println(
                            s
                    );
                }
            }
        }
        static class ChangeSystemout{
            public static void main(String[] args) {
                PrintWriter out = new PrintWriter(System.out,true);
                out.println("hello world");
            }
        }
        static class Redirecting{
            public static void main(String[] args) throws IOException {
                InputStream in = System.in;
                BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("pom.xml"));
                PrintStream out = System.out;
                PrintStream printStream = new PrintStream(new BufferedOutputStream(new FileOutputStream("test.txt")));
                System.setIn(bufferedInputStream);
                System.setOut(printStream);
                System.setErr(printStream);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                String s;
                while ((s=bufferedReader.readLine())!=null){
                    System.out.println(s);
                }
                printStream.close();
                System.setOut(out);
            }
        }
    }
}
