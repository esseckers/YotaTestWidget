package com.danilov.ivan.yotatestwidget.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Ivan Danilov on 04.12.2016.
 * Email - i.danilov@globus-ltd.com
 */
@Root(name = "channel", strict = false)
public class XmlResponse {
    @Element(name = "channel")
    private Channel channel;

    public Channel getChannel() {
        return channel;
    }
}
