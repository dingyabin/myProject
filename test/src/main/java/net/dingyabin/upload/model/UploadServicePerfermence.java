package net.dingyabin.upload.model;

import org.apache.commons.lang3.RandomUtils;

import java.util.BitSet;

/**
 * @author 丁亚宾
 * Date: 2024/6/27.
 * Time:3:10
 */
public class UploadServicePerfermence {

    private final int maxCount;

    private int curIndex;

    private final BitSet bitSet;


    public UploadServicePerfermence(int maxCount) {
        this.maxCount = maxCount;
        this.bitSet = new BitSet(maxCount);
        bitSet.set(0, maxCount, true);
    }


    public synchronized void setResult(boolean result) {
        bitSet.set(curIndex, result);
        if (++curIndex == maxCount) {
            curIndex = 0;
        }
    }


    public synchronized int successCount() {
        return bitSet.cardinality();
    }


    @Override
    public String toString() {
        return "UploadServicePerfermence{" +
                "maxCount=" + maxCount +
                ", curIndex=" + curIndex +
                ", bitSet=" + bitSet +
                '}';
    }

    public static void main(String[] args) {
        UploadServicePerfermence uploadServicePerfermence = new UploadServicePerfermence(20);
        for (int i = 0; i < 100; i++) {
            boolean nextBoolean = RandomUtils.nextBoolean();
            uploadServicePerfermence.setResult(nextBoolean);
            System.out.println(uploadServicePerfermence + "  " + nextBoolean + "  "  + uploadServicePerfermence.successCount());
        }
    }


}
