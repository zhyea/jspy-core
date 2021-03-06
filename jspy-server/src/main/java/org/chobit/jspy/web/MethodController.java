package org.chobit.jspy.web;


import org.chobit.jspy.charts.ChartKit;
import org.chobit.jspy.charts.ChartModel;
import org.chobit.jspy.model.ChartParam;
import org.chobit.jspy.service.MethodService;
import org.chobit.jspy.service.entity.HistogramEntity;
import org.chobit.jspy.service.entity.MethodEntity;
import org.chobit.jspy.tools.LowerCaseKeyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/method")
public class MethodController {

    @Autowired
    private MethodService methodService;


    @PostMapping("/find-by-params")
    public ChartModel findByParams(@SessionAttribute("appCode") String appCode,
                                   @SessionAttribute("methodName") String methodName,
                                   @RequestBody ChartParam param) {
        List<LowerCaseKeyMap> m = methodService.findForChart(appCode, param, methodName);
        return ChartKit.fill(param.getTarget(), m, HistogramEntity.class);
    }
    

    @GetMapping("/all-methods")
    public List<MethodEntity> allMethods(@SessionAttribute("appCode") String appCode) {
        return methodService.findMethods(appCode);
    }
}
