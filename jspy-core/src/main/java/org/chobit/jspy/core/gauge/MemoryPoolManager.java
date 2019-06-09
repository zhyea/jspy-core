package org.chobit.jspy.core.gauge;


import org.chobit.jspy.core.info.Net;
import org.chobit.jspy.core.model.MemoryPool;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryType;
import java.lang.management.MemoryUsage;
import java.util.LinkedList;
import java.util.List;

public abstract class MemoryPoolManager {

    public static List<MemoryPoolMXBean> poolMXBeans = ManagementFactory.getMemoryPoolMXBeans();


    public static List<MemoryPool> memoryPools() {
        List<MemoryPool> result = new LinkedList<>();
        for (MemoryPoolMXBean mxBean : poolMXBeans) {
            String name = mxBean.getName();
            String[] managers = mxBean.getMemoryManagerNames();
            MemoryType type = mxBean.getType();
            MemoryUsage usage = mxBean.getUsage();
            MemoryUsage peakUsage = mxBean.getPeakUsage();

            MemoryPool pool = new MemoryPool(name, type, managers, Net.LOCAL_HOST_IP.value(), usage, peakUsage);
            result.add(pool);
        }
        return result;
    }

}