package com.lijiayi.springbootpromedemo.factory;

import com.lijiayi.springbootpromedemo.event.LongEvent;
import com.lmax.disruptor.EventFactory;

public class LongEventFactory implements EventFactory<LongEvent> {
    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
