package com.example.web.io;

import java.io.*;
import java.nio.Buffer;
import java.util.Enumeration;
import java.util.zip.*;

/**
 * @Auther: 36560
 * @Date: 2019/8/10 :8:28
 * @Description:
 */
public class compress {
    public static void main(String args[]) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("pom.xml"));
        BufferedOutputStream out = new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream("text.gz")));
        System.out.println("writing file");
        int c;
        while((c=in.read())!=-1){
            out.write(c);
        }
        in.close();
        out.close();
        System.out.println("reading file");
        BufferedReader in2 = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream("text.gz"))));
        String s;
        while ((s=in2.readLine())!=null){
            System.out.println(s);
        }
    }
    static class ZipCompress{
        public static void main(String[] args) throws IOException {
            String[] files = new String[]{"pom.xml","text.gz"};
            FileOutputStream f = new FileOutputStream("test.zip ");
            CheckedOutputStream checkedOutputStream = new CheckedOutputStream(f, new Adler32());
            ZipOutputStream zip = new ZipOutputStream(checkedOutputStream);
            BufferedOutputStream out = new BufferedOutputStream(zip);
            zip.setComment("a common test of java Zipping");
            for (String string:files){
                BufferedReader in = new BufferedReader(new FileReader(string));
                zip.putNextEntry(new ZipEntry(string));
                int c;
                while ((c=in.read())!=-1){
                    out.write(c);
                }
                in.close();
                out.flush();
            }
            out.close();
            //test result
            System.out.println("check sum :"+checkedOutputStream.getChecksum().getValue());
            System.out.println("reading file");
            FileInputStream fileInputStream = new FileInputStream("test.zip");
            CheckedInputStream checkedInputStream = new CheckedInputStream(fileInputStream,new Adler32());
            ZipInputStream zipin = new ZipInputStream(checkedInputStream);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(zipin);
            ZipEntry zipEntry;
            while ((zipEntry=zipin.getNextEntry())!=null){
                System.out.println("reading file:"+zipEntry);
                int x;
                while ((x = bufferedInputStream.read())!=-1){
                    System.out.write(x);
                }
            }
            if (files.length==1){
                System.out.println("checksum:"+checkedInputStream.getChecksum().getValue());
            }
            bufferedInputStream.close();
            ZipFile zipFile = new ZipFile("test.zip");
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()){
                ZipEntry zipEntry1 = entries.nextElement();
                System.out.println("reading zip in the other way:"+zipEntry1);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(zipFile.getInputStream(zipEntry1)));
                String s;
                while ((s=bufferedReader.readLine())!=null){
                    System.out.println(s);
                }
                bufferedReader.close();
            }

        }
    }
}
