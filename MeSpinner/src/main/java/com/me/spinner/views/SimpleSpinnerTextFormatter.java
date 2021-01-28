package com.me.spinner.views;

import android.text.Spannable;
import android.text.SpannableString;

/**
 * @author dysen
 * dy.sen@qq.com     1/28/21 13:08 PM
 *
 * Info：
 */
public class SimpleSpinnerTextFormatter implements SpinnerTextFormatter {

    @Override public Spannable format(String text) {
        return new SpannableString(text);
    }
}
