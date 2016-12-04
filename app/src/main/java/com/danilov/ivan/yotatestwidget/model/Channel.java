package com.danilov.ivan.yotatestwidget.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "channel", strict = false)
public class Channel {

    @ElementList(inline = true, name = "item")
    private List<Feed> feeds;

    public List<Feed> getFeeds() {
        return feeds;
    }
}
