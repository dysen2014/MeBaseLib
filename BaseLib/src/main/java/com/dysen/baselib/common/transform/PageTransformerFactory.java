package com.dysen.baselib.common.transform;

import androidx.viewpager2.widget.ViewPager2;


import com.zhpan.bannerview.transform.ScaleInTransformer;

import static com.dysen.baselib.common.transform.TransformerStyle.*;


public class PageTransformerFactory {

    public static ViewPager2.PageTransformer createPageTransformer(int transformerStyle) {
        ViewPager2.PageTransformer transformer = null;
        switch (transformerStyle) {
            case TransformerStyle.DEPTH:
                transformer = new DepthPageTransformer();
                break;
            case TransformerStyle.ROTATE:
                transformer = new RotateUpTransformer();
                break;
            case TransformerStyle.STACK:
                transformer = new StackTransformer();
                break;
            case TransformerStyle.ACCORDION:
                transformer = new AccordionTransformer();
                break;
            case TransformerStyle.SCALE_IN:
                transformer = new ScaleInTransformer(ScaleInTransformer.DEFAULT_MIN_SCALE);
                break;
        }
        return transformer;
    }
}
