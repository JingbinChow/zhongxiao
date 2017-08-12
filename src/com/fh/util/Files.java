package com.fh.util;

/**
 * Created by Administrator on 2016/11/7.
 */



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class Files {

    public static boolean createNewFile(File f) throws IOException {
        if (null == f)
            return false;
        if (f.exists())
            return false;
        makeDir(f.getParentFile());
        return f.createNewFile();
    }


    public static boolean makeDir(File dir) throws IOException {
        if (null == dir)
            return false;
        if (dir.exists())
            return false;
        return dir.mkdirs();
    }



    public static boolean copyFile(File src, File target) throws IOException {
        if (src == null || target == null)
            return false;
        if (!src.exists())
            return false;
        if (!target.exists())
            if (!createNewFile(target))
                return false;
        InputStream ins = new BufferedInputStream(new FileInputStream(src));
        OutputStream ops = new BufferedOutputStream(new FileOutputStream(target));
        int b;
        while (-1 != (b = ins.read()))
            ops.write(b);

        ins.close();
        ops.close();
        return target.setLastModified(src.lastModified());
    }






}
