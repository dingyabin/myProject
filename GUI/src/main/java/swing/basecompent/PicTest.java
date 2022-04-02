package swing.basecompent;

import org.apache.commons.io.IOUtils;
import sun.awt.FontConfiguration;
import sun.font.FontManager;
import sun.font.FontManagerFactory;
import sun.font.SunFontManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.*;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.spi.IIORegistry;
import javax.imageio.stream.ImageInputStream;

/**
 * @author 丁亚宾
 * Date: 2021/11/19.
 * Time:22:54
 */
public class PicTest {

    static {
        FontManager instance = FontManagerFactory.getInstance();
        SunFontManager instance1 = (SunFontManager) instance;
        instance1.loadFontFiles();
        ImageIO.setUseCache(false);
        GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            //ImageInputStream imageInputStream = ImageIO.createImageInputStream(new ByteArrayInputStream(IOUtils.toByteArray(new FileInputStream("C:\\Users\\丁亚宾\\Desktop\\2222.jpg"))));
            //Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(imageInputStream);

            ImageIO.read(new ByteArrayInputStream(IOUtils.toByteArray(new FileInputStream("C:\\Users\\丁亚宾\\Desktop\\2222.jpg"))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        try {
            // 读取原图片信息
            String srcImgPath="C:\\Users\\丁亚宾\\Desktop\\1111.jpg";
            String waterMarkContent="这是水印";
            String outImgPath="C:\\Users\\丁亚宾\\Desktop\\2222.jpg";

            byte[] bytes = IOUtils.toByteArray(new FileInputStream(srcImgPath));
//
//            byte[] bytes111 = IOUtils.toByteArray(new FileInputStream("/Users/didi/Downloads/111.png"));
//            BufferedImage srcImg111 = ImageIO.read(new ByteArrayInputStream(bytes111));
//
//            byte[] bytes222 = IOUtils.toByteArray(new FileInputStream("/Users/didi/Downloads/222.png"));
//            BufferedImage srcImg222 = ImageIO.read(new ByteArrayInputStream(bytes222));

            //ImageIO.read(new ByteArrayInputStream(bytes2));

            long start = System.currentTimeMillis();
            BufferedImage srcImg = ImageIO.read(new ByteArrayInputStream(bytes));
            System.out.println("2-----"+(System.currentTimeMillis()-start));


            int srcImgWidth = srcImg.getWidth(null);
            int srcImgHeight = srcImg.getHeight(null);


            // 加水印
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_3BYTE_BGR);



            long start2 = System.currentTimeMillis();
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg,0,0,null);
            System.out.println("3-----"+(System.currentTimeMillis()-start2));


            g.setColor(Color.red);
            int x = 0;
            int y = 20;


            long start3 = System.currentTimeMillis();
            g.drawString(waterMarkContent, x, y);
            g.dispose();
            System.out.println("4-----"+(System.currentTimeMillis()-start3));


            long start4 = System.currentTimeMillis();
            ByteArrayOutputStream outputStream1 = new ByteArrayOutputStream();
            ImageIO.write(bufImg, "jpg", outputStream1);
            byte[] data = outputStream1.toByteArray();
            System.out.println("5-----"+(System.currentTimeMillis()-start4));



//            long start4 = System.currentTimeMillis();
//            WritableRaster raster = bufImg.getRaster();
//            DataBufferByte dataBuffer = (DataBufferByte)raster.getDataBuffer();
//            byte[] data = dataBuffer.getData();
//            System.out.println("5-----"+(System.currentTimeMillis()-start4));


            System.out.println("6-----"+(System.currentTimeMillis()-start));


            FileOutputStream outputStream = new FileOutputStream(outImgPath);
            IOUtils.write(data, outputStream);
            outputStream.flush();
            outputStream.close();

//            // 输出图片
//            FileOutputStream outImgStream = new FileOutputStream(outImgPath);
//            ImageIO.write(bufImg, "jpg", outImgStream);
//            outImgStream.flush();
//            outImgStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
