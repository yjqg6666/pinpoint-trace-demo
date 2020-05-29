package com.pinpoint.demo.trace.init;

import com.navercorp.pinpoint.interaction.trace.decision.TraceDecisionEnum;
import com.navercorp.pinpoint.interaction.trace.decision.TraceDecisionMaker;
import com.navercorp.pinpoint.interaction.trace.decision.TraceDecisionMakerHolder;
import com.navercorp.pinpoint.interaction.util.InstanceUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;

/**
 * @author yjqg6666
 */
@Component
public class AppTraceDecisionMaker implements TraceDecisionMaker {

    @Override
    public TraceDecisionEnum shouldTrace(Object request) {
        if (!InstanceUtils.isServletRequest(request)) {
            return TraceDecisionEnum.RateTrace;
        }
        try {
            Method checkMethod = request.getClass().getDeclaredMethod("getServletPath");
            Object result = checkMethod.invoke(request);
            if (result instanceof String) {
                String reqPath = (String) result;
                String[] dirs = reqPath.split("/");
                String firstDir = dirs.length >= 2 ? dirs[1] : "";
                switch (firstDir) {
                    case "api1":
                        return TraceDecisionEnum.ForceTrace;
                    case "api2":
                        return TraceDecisionEnum.NoTrace;
                    default:
                        return TraceDecisionEnum.RateTrace;
                }
            }
        } catch (Exception e) {
            return TraceDecisionEnum.RateTrace;
        }
        return TraceDecisionEnum.RateTrace;
    }

    @PostConstruct
    public void setMaker() {
        TraceDecisionMakerHolder.setTraceDecisionMaker(this);
    }

}
