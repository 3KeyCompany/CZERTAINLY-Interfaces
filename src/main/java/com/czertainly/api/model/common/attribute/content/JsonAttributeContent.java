package com.czertainly.api.model.common.attribute.content;

public class JsonAttributeContent extends BaseAttributeContent<String> {

    private Object data;

    public JsonAttributeContent() { }

    public JsonAttributeContent(String value, Object data) {
        super(value);
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
