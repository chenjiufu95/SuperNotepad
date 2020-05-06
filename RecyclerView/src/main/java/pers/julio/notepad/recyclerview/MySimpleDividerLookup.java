package pers.julio.notepad.recyclerview;

import com.choices.divider.Divider;

import pers.julio.notepad.recyclerview.Base.MyBaseDecoration;


/**
 * @file FileName, Created by julio_chan on 2019/11/9.
 */
public class MySimpleDividerLookup implements MyBaseDecoration.DividerLookup {
    private final int SIZE;
    private final int COLOR;
    private final int START;
    private final int END;

    public MySimpleDividerLookup(int size, int color, int magin_start, int magin_end) {
        SIZE = size;
        COLOR = color;
        START = magin_start;
        END = magin_end;
    }

    @Override
    public Divider getHorizontalDivider(int pos) {
        return new Divider.Builder().size(SIZE).color(COLOR).margin(START, END).build();
    }

    @Override
    public Divider getVerticalDivider(int pos) {
        return null;
        //return new Divider.Builder().size(SIZE).color(COLOR).margin(START, END).build();
    }
}
