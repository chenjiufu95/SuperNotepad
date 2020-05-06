package pers.julio.notepad.recyclerview;

import com.choices.divider.Divider;

import java.util.ArrayList;

import pers.julio.notepad.recyclerview.Base.MyBaseDecoration;
import pers.julio.notepad.recyclerview.Bean.DividerBean;


/**
 * @file FileName, Created by julio_chan on 2019/11/27.
 */
public class MyConfigurableDividerLookup implements MyBaseDecoration.DividerLookup {
    private ArrayList<DividerBean> mHorizontal = new ArrayList<>();
    private ArrayList<DividerBean> mVertical = new ArrayList<>();

    public MyConfigurableDividerLookup(ArrayList<DividerBean> dividers) {
        this(dividers,null);
    }
    public MyConfigurableDividerLookup(ArrayList<DividerBean> h_Dividers, ArrayList<DividerBean> v_Dividers) {
        if (h_Dividers != null && h_Dividers.size() > 0) {
            mHorizontal.addAll(h_Dividers);
        }
        if (v_Dividers != null && v_Dividers.size() > 0) {
            mVertical.addAll(v_Dividers);
        }
    }

    @Override
    public Divider getHorizontalDivider(int pos) {
        if (pos < mHorizontal.size()){
            DividerBean divider = mHorizontal.get(pos);
            if(divider==null) return null;
            return new Divider.Builder().size(divider.SIZE).color(divider.COLOR).margin(divider.START, divider.END).build();
        }else return null;
    }

    @Override
    public Divider getVerticalDivider(int pos) {
        if (pos < mVertical.size()){
            DividerBean divider = mVertical.get(pos);
            if(divider==null) return null;
            return new Divider.Builder().size(divider.SIZE).color(divider.COLOR).margin(divider.START, divider.END).build();
        }else return null;
    }
}
