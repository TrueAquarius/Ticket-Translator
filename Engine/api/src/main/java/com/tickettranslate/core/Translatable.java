package com.tickettranslate.core;

public class Translatable {
    private static String TRANSLATION_START = "=== START TRANSLATION =====================";
    private static String TRANSLATION_END = "=== END TRANSLATION =====================";
    private static String NEWLINE = "\r\n";

    private String text = null;
    private String translation = null;

    private  Translatable next = null;
    private int index = 0;

    private Translatable(String text, int index)
    {
        this(text);
        this.index = index;
    }
    public Translatable(String text)
    {
        int start = text.indexOf(TRANSLATION_START);
        if(start<0)
        {
            this.text = text;
            return;
        }

        this.text = text.substring(0, start-1).trim();
        int end = text.indexOf(TRANSLATION_END);

        if(end<0)
        {
            this.translation = text.substring(start + TRANSLATION_START.length()).trim();
            return;
        }

        this.translation = text.substring(start + TRANSLATION_START.length(), end-1).trim();

        String rest = text.substring(end + TRANSLATION_END.length()).trim();

        if(rest.length() >0)
        {
            next = new Translatable(rest, index+1);
        }
    }

    public String toString()
    {
        String s = text + ((translation!=null) ? NEWLINE + NEWLINE + TRANSLATION_START + NEWLINE + translation + NEWLINE + TRANSLATION_END + NEWLINE : "");
        return s + (next==null? "" : next.toString());
    }

    public String getText(int index)
    {
        if(this.index == index)
        {
            return this.text;
        }

        if(this.next==null) return null;

        return next.getText(index);
    }

    public String getTranslation(int index)
    {
        if(this.index == index)
        {
            return this.translation;
        }

        if(this.next==null) return null;

        return next.getTranslation(index);
    }

    public void setTranslation(int index, String translation)
    {
        if(this.index == index)
        {
            this.translation = translation;
            return;
        }

        if(this.next==null) return;

        next.setTranslation(index, translation);
    }

    public int getCount()
    {
        return next == null ? index + 1 : next.getCount();
    }
}

