package org.chobit.jspy.service.mapper;

import org.apache.ibatis.annotations.*;
import org.chobit.jspy.service.entity.ThreadStat;

import java.util.Date;

@Mapper
public interface ThreadStatMapper {


    @Insert({"insert into thread_stat (app_code, ip, current, peak, total_started, daemon, event_time)",
            "values",
            "(#{appCode}, #{ip}, #{current}, #{peak}, #{totalStarted}, #{daemon}, #{eventTime})"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ThreadStat stat);


    @Select("select * from thread_stat where app_code=#{appCode} and event_time>#{time} order by id desc limit 1")
    ThreadStat getLatest(@Param("appCode") String appCode, @Param("time") Date time);

}
