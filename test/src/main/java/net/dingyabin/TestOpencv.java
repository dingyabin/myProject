package net.dingyabin;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.Collections;

public class TestOpencv {

    static {
        nu.pattern.OpenCV.loadShared();
        System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
    }

    public static float[] extractColorHistogram(String imagePath) {
        Mat image = Imgcodecs.imread(imagePath);
        if (image.empty()) {
            throw new RuntimeException("无法加载图像: " + imagePath);
        }

        // 转为 HSV
        Mat hsv = new Mat();
        Imgproc.cvtColor(image, hsv, Imgproc.COLOR_BGR2HSV);
        /**
         *
         * 设置直方图参数
         * 表示：为多通道直方图的每个通道指定分箱数量
         * 50：H（色调）通道分成 32 个区间
         * 60：S（饱和度）通道分成 16 个区间
         * 最终直方图大小 = 32 × 16 = 512 维特征向量（可展平为一维）
         * 这是提取颜色特征的关键一步，直接影响“以图搜图”的精度和性能。
         */
        int hBins = 32, sBins = 16;
        Mat hist = new Mat();
        MatOfInt channels = new MatOfInt(0, 1);
        MatOfInt histSize = new MatOfInt(hBins, sBins);
        MatOfFloat ranges = new MatOfFloat(0f, 180f, 0f, 256f); // H: 0-180, S: 0-256

        Imgproc.calcHist(Collections.singletonList(hsv), channels, new Mat(), hist, histSize, ranges);

        // 转为一维 double 数组（特征向量）
        float[] histArray = new float[(int) hist.total()];
        hist.get(0, 0, histArray);

        // 归一化
        Core.normalize(hist, hist, 1, 0, Core.NORM_L1);

        hist.get(0, 0, histArray); // 重新获取归一化后的值
        return histArray;
    }


    public static double cosineSimilarity(float[] a, float[] b) {
        float dot = 0.0f, normA = 0.0f, normB = 0.0f;
        for (int i = 0; i < a.length; i++) {
            dot += a[i] * b[i];
            normA += a[i] * a[i];
            normB += b[i] * b[i];
        }
        return dot / (Math.sqrt(normA) * Math.sqrt(normB));
    }


    public static void main(String[] args) {
        float[] matOfKeyPoint = extractColorHistogram("E:\\444.jpg");
        float[] matOfKeyPoint2 = extractColorHistogram("E:\\333.jpg");
        System.out.println(cosineSimilarity(matOfKeyPoint,matOfKeyPoint2));

    }
}
