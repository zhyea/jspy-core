package org.chobit.jspy.service;

import org.chobit.jspy.core.model.GcRecord;
import org.chobit.jspy.model.GcOverview;
import org.chobit.jspy.model.Histogram;
import org.chobit.jspy.model.QueryParam;
import org.chobit.jspy.service.beans.GcStat;
import org.chobit.jspy.service.beans.HistogramEntity;
import org.chobit.jspy.service.mapper.GcStatMapper;
import org.chobit.jspy.service.mapper.HistogramMapper;
import org.chobit.jspy.tools.LowerCaseKeyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

import static org.chobit.jspy.constants.HistogramType.GC;

@Service
public class GcService {


    @Autowired
    private GcStatMapper gcMapper;
    @Autowired
    private HistogramMapper histogramMapper;


    /**
     * 查询报表数据
     */
    public List<LowerCaseKeyMap> findByQueryParam(String appCode, QueryParam param) {
        return histogramMapper
                .findForChart(appCode, GC.id, param.getTarget(), param.getStartTime(), param.getEndTime());
    }


    /**
     * 写入GC记录
     */
    public boolean insert(String appCode, String ip, GcOverview overview) {
        insertGcHistogram(appCode, ip, overview.getMajorHistogram());
        insertGcHistogram(appCode, ip, overview.getMinorHistogram());
        insertGcRecords(appCode, ip, overview.getGcRecords());

        return true;
    }


    private boolean insertGcHistogram(String appCode, String ip, Histogram histogram) {
        if (null == histogram) {
            return true;
        }
        return histogramMapper.insert(new HistogramEntity(appCode, ip, GC, histogram)) > 0;
    }


    private boolean insertGcRecords(String appCode, String ip, List<GcRecord> gcRecords) {
        if (null == gcRecords || gcRecords.isEmpty()) {
            return true;
        }

        List<GcStat> gcStats = new LinkedList<>();
        for (GcRecord record : gcRecords) {
            GcStat stat = new GcStat();
            stat.setAppCode(appCode);
            stat.setIp(ip);

            stat.setGcId(record.getGcId());
            stat.setType(record.getType().name());
            stat.setAction(record.getAction());
            stat.setName(record.getName());
            stat.setCause(record.getCause());

            stat.setStartTime(record.getStartTime());
            stat.setDuration(record.getDuration());

            stat.setUsageBefore(record.getUsageBefore());
            stat.setUsageAfter(record.getUsageAfter());

            stat.setEventTime(record.getRecordTime());

            stat.setMajorGcCount(record.getMajorGcCount());
            stat.setMinorGcCount(record.getMinorGcCount());

            gcStats.add(stat);
        }
        return gcMapper.batchInsert(gcStats) == gcStats.size();
    }

}
