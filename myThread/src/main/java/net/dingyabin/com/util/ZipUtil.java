package net.dingyabin.com.util;

import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrDing
 * Date: 2017/3/31.
 * Time:23:46
 */
public class ZipUtil {


    public static List<File> getFile(File file) {
        List<File> list=null;
        if (file == null || list == null) {
            list=new ArrayList<>();
        }
        if (!file.isDirectory()) {
            list.add(file);
            return list;
        }
        File[] files = file.listFiles();
        if (files!=null){
            for (File perFile : files) {
                list.addAll(getFile(perFile));
            }
        }
        return list;
    }




    /**
     * 把文件压缩成zip格式
     * @param files         需要压缩的文件
     * @param zipFile 压缩后的zip文件路径   ,如"D:/test/aa.zip";
     */
    public static void compressFilesZip(File files, File zipFile) {
        ZipArchiveOutputStream zaos = null;
        try {
            List<File> list = getFile(files);
            checkIfExistOrCreate(zipFile);
            zaos = new ZipArchiveOutputStream(zipFile);
            zaos.setUseZip64(Zip64Mode.AsNeeded);
            writeZip(list,zaos);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            close(zaos);
        }
    }



    private static void writeZip(List<File> fileList, ZipArchiveOutputStream zaos) throws IOException {
        //将每个文件用ZipArchiveEntry封装
        //再用ZipArchiveOutputStream写到压缩文件中
        for (File file : fileList) {
            if (file != null) {
                ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(file, file.getName());
                zaos.putArchiveEntry(zipArchiveEntry);
                if (file.isDirectory()) {
                    continue;
                }
                writeFile(zaos, file);
                zaos.closeArchiveEntry();
            }
        }
        zaos.finish();
    }




    private static void writeFile(OutputStream zaos, File file) {
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[1024 ];
            int len;
            while((len = is.read(buffer)) != -1) {
                zaos.write(buffer, 0, len);//把缓冲区的字节写入到ZipArchiveEntry
            }
        }catch(Exception e) {
            throw new RuntimeException(e);
        }finally {
            close(is);
        }
    }


    /**
     * 校验文件是否存在，不存在则新建
     *
     * @param file
     * @throws Exception
     */
    private static void checkIfExistOrCreate(File file) throws Exception {
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.mkdirs();
            }
            file.createNewFile();
        }
    }



    /**
     * 关流
     * @param obj
     */
    private static  void close(Closeable obj){
        try {
            if (obj!=null) obj.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public static void main(String[] args) {
        File src=new File("C:\\Users\\MrDing\\Desktop\\知识分享");
        File zip=new File("C:\\Users\\MrDing\\Desktop\\知识分享\\aaa.zip");
        compressFilesZip(src,zip);
        System.out.println("ok");
    }
}
