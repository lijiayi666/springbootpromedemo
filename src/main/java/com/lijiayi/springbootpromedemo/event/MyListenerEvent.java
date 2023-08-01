package com.lijiayi.springbootpromedemo.event;

import org.springframework.context.ApplicationEvent;

public class MyListenerEvent extends ApplicationEvent {

    private final String name;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     * @param name
     */
    public MyListenerEvent(Object source, String name) {
        super(source);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
