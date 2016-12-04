package com.danilov.ivan.yotatestwidget.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by Ivan Danilov on 04.12.2016.
 * Email - i.danilov@globus-ltd.com
 */

@Root(name = "channel", strict = false)
public class Channel {

    @ElementList(inline = true, name = "item")
    private List<Feed> feeds;

    public List<Feed> getFeeds() {
        return feeds;
    }
}
