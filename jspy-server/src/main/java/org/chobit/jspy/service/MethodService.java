package org.chobit.jspy.service;


import com.github.benmanes.caffeine.cache.LoadingCache;
import org.chobit.jspy.model.Histogram;
import org.chobit.jspy.model.MethodHistogram;
import org.chobit.jspy.model.ChartParam;
import org.chobit.jspy.model.page.Page;
import org.chobit.jspy.model.page.PageResult;
import org.chobit.jspy.service.entity.HistogramEntity;
import org.chobit.jspy.service.entity.MethodEntity;
import org.chobit.jspy.service.mapper.HistogramMapper;
import org.chobit.jspy.service.mapper.MethodMapper;
import org.chobit.jspy.tools.LowerCaseKeyMap;
import org.chobit.jspy.utils.SysTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.chobit.jspy.constants.HistogramType.METHOD;
import static org.chobit.jspy.tools.CacheBuilder.build;

@Service
@CacheConfig(cacheNames = "method")
public class MethodService {

    @Autowired
    private HistogramMapper histogramMapper;
    @Autowired
    private MethodMapper methodMapper;


    private LoadingCache<String, List<MethodEntity>> methodNames = build(this::findMethods0);

    /**
     * 分页查询
     */
    public PageResult<HistogramEntity> findInPage(String appCode, String methodName, Page page) {
        long total = histogramMapper.countByMethod(appCode, METHOD.id, methodName);
        List<HistogramEntity> rows = histogramMapper.findInPage(appCode, METHOD.id, methodName, page);
        return new PageResult<>(total, rows);
    }


    /**
     * 查询报表数据
     */
    public List<LowerCaseKeyMap> findForChart(String appCode, ChartParam param, String methodName) {
        return histogramMapper
                .findForChart(appCode, METHOD.id, methodName, param.getStartTime(), param.getEndTime());
    }

    /**
     * 根据ID获取方法记录
     */
    public MethodEntity get(int id) {
        return methodMapper.get(id);
    }

    public List<MethodEntity> findMethods(String appCode) {
        return methodNames.get(appCode);
    }

    /**
     * 查找方法信息
     */
    private List<MethodEntity> findMethods0(String appCode) {
        return methodMapper.findByAppCode(appCode);
    }


    /**
     * 写入方法统计数据
     */
    public boolean insertHistograms(String appCode, String ip, MethodHistogram mh) {
        if (mh.isEmpty()) {
            return true;
        }

        List<HistogramEntity> list = new LinkedList<>();

        for (Histogram h : mh.getHistograms()) {
            long failedCount = mh.failedCount(h.getName());
            list.add(new HistogramEntity(appCode, ip, METHOD, h, failedCount));
        }
        boolean allInsert = mh.size() == histogramMapper.batchInsert(list);

        for (HistogramEntity e : list) {
            insertOrUpdate(appCode, e.getName());
        }

        return allInsert;
    }


    /**
     * 写入或更新方法信息
     */
    private void insertOrUpdate(String appCode, String methodName) {
        MethodEntity entity = methodMapper.findByName(appCode, methodName);
        if (null == entity) {
            entity = new MethodEntity();
            entity.setAppCode(appCode);
            entity.setName(methodName);
        }

        Date date = new Date(SysTime.millis() - TimeUnit.DAYS.toMillis(1));
        Map<String, BigDecimal> counts = histogramMapper.sumByTime(appCode, methodName, date);
        BigDecimal total = counts.getOrDefault("TOTAL", BigDecimal.ZERO);
        BigDecimal failed = counts.getOrDefault("FAILED", BigDecimal.ZERO);

        entity.setRecentCount(total.longValue());
        entity.setRecentFailed(failed.longValue());

        if (0 >= entity.getId()) {
            methodMapper.insert(entity);
        } else {
            methodMapper.update(entity);
        }
    }

}
