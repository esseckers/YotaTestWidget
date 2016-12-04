package com.danilov.ivan.yotatestwidget.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "channel", strict = false)
public class XmlResponse {
    @Element(name = "channel")
    private Channel channel;

    public Channel getChannel() {
        return channel;
    }
}
