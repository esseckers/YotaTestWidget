package com.danilov.ivan.yotatestwidget.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Ivan Danilov on 29.11.2016.
 * Email - i.danilov@globus-ltd.com
 */

@Root(name = "item")
public class Feed {

    @Element(name = "title")
    private String title;
    @Element(name = "link")
    private String link;
    @Element(name = "description")
    private String description;

    @Element(required = false)
    private String category;
    @Element(required = false)
    private String comments;
    @Element(required = false)
    private String pubDate;

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }
}
