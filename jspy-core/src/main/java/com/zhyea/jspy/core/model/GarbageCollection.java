package com.zhyea.jspy.core.model;

import com.zhyea.jspy.core.constants.GarbageCollectType;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class GarbageCollection {

    private GarbageCollectType type;


    private AtomicInteger count = new AtomicInteger(0);

    private AtomicLong usedTime = new AtomicLong(0L);

    public GarbageCollection(GarbageCollectType type) {
        this.type = type;
    }

    public void accumulate(int count, long usedTime) {
        this.count.addAndGet(count);
        this.usedTime.addAndGet(usedTime);
    }

}
