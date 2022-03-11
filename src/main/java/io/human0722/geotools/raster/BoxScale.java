package io.human0722.geotools.raster;

import jj2000.j2k.io.BEBufferedRandomAccessFile;
import jj2000.j2k.io.RandomAccessIO;

import java.io.*;

/**
 * @author xueliang
 * @description 尝试解决 Geotools 直接读取 .jp2 文件报错： File too long, 转换后无法获得 Envelop,不再报 File too long.
 * @date 2022-03-02 9:27
 */
public class BoxScale {
    public static void main(String[] args) throws IOException {
        RandomAccessIO in = new BEBufferedRandomAccessFile("C:/Users/puhou/Desktop/lab/jp2_java_lab/aaa.jp2", "r");
        DataOutputStream out = new DataOutputStream(new FileOutputStream(new File("C:/Users/puhou/Desktop/lab/jp2_java_lab/_aaa.jp2")));
        boolean done = false;
        while (!done) {
            try {
                int boxLength = in.readInt();
                if (boxLength == 1) {
                    //convert large box
                    int boxType = in.readInt();//skip box type
                    long actualBoxLength = in.readLong();//the box is actually small
                    if (actualBoxLength > Integer.MAX_VALUE) {
                        throw new RuntimeException("Unable to fix large box, size exceeds int");
                    }
                    out.writeInt((int) actualBoxLength - 8);
                    out.writeInt(boxType);
                    copyBytes(in, out, (int) actualBoxLength - 16);
                } else {
                    //copy other stuff
                    out.writeInt(boxLength);
                    copyBytes(in, out, boxLength != 0 ? boxLength - 4 : 0);
                }
            } catch (EOFException e) {
                done = true;
            }
        }
        out.close();
        in.close();
    }

    private static void copyBytes(RandomAccessIO in, DataOutputStream out, int length) throws IOException {
        if (length != 0) {
            //copying set amount
            byte[] bytes = new byte[length];
            in.readFully(bytes, 0, bytes.length);
            out.write(bytes, 0, bytes.length);
        } else {
            //copying to the end of file
            byte[] bytes = new byte[10240];
            int lastPos = 0;
            try {
                while (true) {
                    lastPos = in.getPos();
                    in.readFully(bytes, 0, bytes.length);
                    out.write(bytes, 0, bytes.length);
                }
            } catch (EOFException e) {
                out.write(bytes, 0, in.length() - lastPos);
            }
        }
    }
}
