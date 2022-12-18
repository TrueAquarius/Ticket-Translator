package com.tickettranslate.azure;

public class RequestBody {
    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public RequestBody(String text) {
        Text = text;
    }

    private String Text;

    
    @Override
    public String toString() {
        //return "[{\"Text\": \"I would really like to drive your car around the block a few times!\"}]"
        return "[{" +
                "\"Text\": \"" + Text + '\"' +
                "}]";
    }
}
