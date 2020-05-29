package com.pinpoint.demo.trace.controller;

import com.navercorp.pinpoint.interaction.trace.export.TraceInfoHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 * @author yjqg6666
 */
@RestController
public class DemoController {

    /**
     * should enable profiler.export.trace.info=true
     * @param id
     * @return
     */
    @GetMapping(value = "/trace/{id:[0-9]+}")
    public String config(@PathVariable("id") Long id) {
        System.out.println(TraceInfoHolder.getTraceInfo());
        System.out.println(TraceInfoHolder.getSpanId());
        System.out.println(TraceInfoHolder.toInfoString());
        return id.toString() + "@" + System.currentTimeMillis() + " " + TraceInfoHolder.getTraceInfo();
    }

    @GetMapping("/api1/{id:[0-9]+}")
    public String apiOne(@PathVariable("id") Long id) {
        return "api1@" + id + " " + TraceInfoHolder.getTransactionId();
    }

    @GetMapping("/api2/{id:[0-9]+}")
    public String apiTwo(@PathVariable("id") Long id) {
        return "api2@" + id;
    }

}
