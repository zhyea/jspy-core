package org.chobit.jspy.service.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.chobit.jspy.service.entity.GcStat;

import java.util.List;

@Mapper
public interface GcStatMapper {


    /**
     * 批量写入数据
     */
    @Insert({
            "<script>",
            "insert into gc_stat(app_code, ip, gc_id, `type`, action, `name`, cause, start_time, duration,",
            "usage_before, usage_after, event_time, major_gc_count, minor_gc_count)",
            "values",
            "<foreach collection='gcStats' item='item' separator=','>",
            "(#{item.appCode}, #{item.ip}, #{item.gcId}, #{item.type}, #{item.action}, #{item.name}, #{item.cause}, #{item.startTime}, #{item.duration},",
            " #{item.usageBefore}, #{item.usageAfter}, #{item.eventTime}, #{item.majorGcCount}, #{item.minorGcCount})",
            "</foreach>",
            "</script>"
    })
    int batchInsert(@Param("gcStats") List<GcStat> gcStats);


}
