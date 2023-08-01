package com.lijiayi.springbootpromedemo.consumer;

import com.lijiayi.springbootpromedemo.event.LongEvent;
import com.lmax.disruptor.EventHandler;

public class LongEventHandler implements EventHandler<LongEvent> {

    @Override
    public void onEvent(LongEvent longEvent, long l, boolean b) throws Exception {
        System.out.println("消费者:"+longEvent.getValue());
    }
}
