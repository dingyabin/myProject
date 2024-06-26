package net.dingyabin.upload.model;

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
        if (++curIndex == maxCount) {
            curIndex = 0;
        }
        bitSet.set(curIndex, result);
    }


    public synchronized int successCount() {
        return bitSet.cardinality();
    }


}
