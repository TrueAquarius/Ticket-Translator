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

        return "[{" +
                "\"Text\": \"" + Text + '\"' +
                "}]";
    }
}
