package com.me.spinner.views;

import android.content.Context;

import java.util.List;


/**
 * @author dysen
 * dy.sen@qq.com     1/28/21 13:08 PM
 *
 * Info：
 */
public class NiceSpinnerAdapter<T> extends NiceSpinnerBaseAdapter {

    private final List<T> items;

    NiceSpinnerAdapter(Context context, List<T> items, int textColor, int backgroundSelector,
                       SpinnerTextFormatter spinnerTextFormatter) {
        super(context, textColor, backgroundSelector, spinnerTextFormatter);
        this.items = items;
    }

    @Override public int getCount() {
        return items.size() - 1;
    }

    @Override public T getItem(int position) {
        if (position >= selectedIndex) {
            return items.get(position + 1);
        } else {
            return items.get(position);
        }
    }

    @Override public T getItemInDataset(int position) {
        return items.get(position);
    }
}