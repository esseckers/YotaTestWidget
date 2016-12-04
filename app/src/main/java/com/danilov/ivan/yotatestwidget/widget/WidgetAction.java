package com.danilov.ivan.yotatestwidget.widget;

public enum WidgetAction {

    ACTION_SUBSCRIBE("com.danilov.ivan.yotatestwidget.widget.ACTION_SUBSCRIBE"),
    ACTION_NEXT("com.danilov.ivan.yotatestwidget.widget.ACTION_NEXT"),
    ACTION_BACK("com.danilov.ivan.yotatestwidget.widget.ACTION_BACK"),
    ACTION_NONE("");

    WidgetAction(String action) {
        this.action = action;
    }

    private String action;

    public String getActionKey() {
        return action;
    }

    public static WidgetAction getAction(String action) {
        WidgetAction result = ACTION_NONE;
        for (WidgetAction widgetAction : WidgetAction.values()) {
            if (widgetAction.getActionKey().equals(action)) {
                result = widgetAction;
                break;
            }
        }
        return result;
    }
}
