package com.hexin.android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.widget.TextView;

import demo.clb.myapplication.R;


/**
 *
 * TextView的setCompoundDrawables()可以在其四周设置图片，但是有个众所周知的问题，即无法设置drawable大小。
 * 这就导致在实际的使用中有很大的局限性，必须用代码去控制，就略显麻烦了。
 *
 * 这个自定义控件虽然简单，也非常不起眼，但是用处还真不少：
 * 1.解决了主要矛盾，无法在布局里设置TextView图片大小问题，使用更加简单。
 *   除了设置图片大小，其它的TextView可以的事情这个一样也都可以
 *
 * 2.图片加文字的简单组合非常常见，原本为了适配图片大小不得不用一个xxxLayout＋ImageView+TextView
 *   才能搞定的事，现在用一个控件即可搞定。
 *   在方便、高效使用的同时，也有效的减少了布局层级。
 *
 * 原理真的简单到只有两句话：
 * 1.通过Drawable的setBound()设置显示区域，也就是图片大小
 * 2.通过TextView的setCompoundDrawables()设置要显示的图片
 *
 */

public class DrawableTextView extends TextView {

    public static final int LEFT_DRAWABLE = 0;
    public static final int TOP_DRAWABLE = 1;
    public static final int RIGHT_DRAWABLE = 2;
    public static final int BOTTOM_DRAWABLE = 3;

    public DrawableTextView(Context context) {
        super(context);
    }

    public DrawableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DrawableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DrawableTextView);
        int width = ta.getDimensionPixelOffset(R.styleable.DrawableTextView_drawable_width, -1);
        int height = ta.getDimensionPixelOffset(R.styleable.DrawableTextView_drawable_height, -1);

        SizeWrap sizeWrap = new SizeWrap();
        Drawable leftDrawable = ta.getDrawable(R.styleable.DrawableTextView_left_drawable);
        if (leftDrawable != null) {
            int lwidth = ta.getDimensionPixelOffset(R.styleable.DrawableTextView_leftdrawable_width, -1);
            int lheight = ta.getDimensionPixelOffset(R.styleable.DrawableTextView_lefttdrawable_height, -1);
            if (sizeWrap.checkWidthAndHeight(leftDrawable, width, height, lwidth, lheight)) {
                leftDrawable.setBounds(0, 0, sizeWrap.width, sizeWrap.height);
            } else {
                throw new IllegalArgumentException("error left drawable size setting");
            }
        }

        Drawable rightDrawable = ta.getDrawable(R.styleable.DrawableTextView_right_drawable);
        if (rightDrawable != null) {
            int rwidth = ta.getDimensionPixelOffset(R.styleable.DrawableTextView_rightdrawable_width, -1);
            int rheight = ta.getDimensionPixelOffset(R.styleable.DrawableTextView_rightdrawable_height, -1);
            if (sizeWrap.checkWidthAndHeight(leftDrawable, width,height, rwidth, rheight)) {
                rightDrawable.setBounds(0, 0, sizeWrap.width, sizeWrap.height);
            } else {
                throw new IllegalArgumentException("error right drawable size setting");
            }
        }

        Drawable topDrawable = ta.getDrawable(R.styleable.DrawableTextView_top_drawable);
        if (topDrawable != null) {
            int twidth = ta.getDimensionPixelOffset(R.styleable.DrawableTextView_topdrawable_width, -1);
            int theight = ta.getDimensionPixelOffset(R.styleable.DrawableTextView_topdrawable_height, -1);
            if (sizeWrap.checkWidthAndHeight(leftDrawable, width,height, twidth, theight)) {
                topDrawable.setBounds(0, 0, sizeWrap.width, sizeWrap.height);
            } else {
                throw new IllegalArgumentException("error top drawable size setting");
            }
        }

        Drawable bottomDrawable = ta.getDrawable(R.styleable.DrawableTextView_bottom_drawable);
        if (bottomDrawable != null) {
            int bwidth = ta.getDimensionPixelOffset(R.styleable.DrawableTextView_bottomdrawable_width, -1);
            int bheight = ta.getDimensionPixelOffset(R.styleable.DrawableTextView_bottomdrawable_height, -1);
            if (sizeWrap.checkWidthAndHeight(leftDrawable, width, height, bwidth, bheight)) {
                bottomDrawable.setBounds(0, 0, sizeWrap.width, sizeWrap.height);
            } else {
                throw new IllegalArgumentException("error bottom drawable size setting");
            }
        }

        this.setCompoundDrawables(leftDrawable, topDrawable, rightDrawable, bottomDrawable);
        ta.recycle();
        ta = null;
    }

    public void setDrawable(int drawableDirect, @DrawableRes int drawableRes) {
        Drawable newDr = getResources().getDrawable(drawableRes);
        setDrawable(drawableDirect, newDr);
    }

    public void setBitmap(int drawableDirect, Bitmap bitmap) {
        if (bitmap == null) {
            setDrawable(drawableDirect, null);
        } else {
            Drawable drawable = new BitmapDrawable(getResources(), bitmap);
            setDrawable(drawableDirect, drawable);
        }
    }

    public void setDrawable(int drawableDirect, Drawable drawable) {
        if (drawableDirect < LEFT_DRAWABLE || drawableDirect > BOTTOM_DRAWABLE) {
            throw new IllegalArgumentException("drawableDirect is wrong");
        }

        Drawable[] drawables = getCompoundDrawables();
        Drawable oldDr = drawables[drawableDirect];
        Drawable newDr = drawable;
        if (oldDr == null) {
            if (newDr != null) {
                if (newDr.getBounds().isEmpty()) {  //如果drawable没设置大小，会无法显示，这种情况下填充默认大小
                    newDr.setBounds(0, 0, newDr.getIntrinsicWidth(), newDr.getIntrinsicHeight());
                }
                drawables[drawableDirect] = newDr;
                setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
            }
        } else {
            Rect rect = oldDr.getBounds();
            if (newDr != null) {
                newDr.setBounds(rect);
            }
            drawables[drawableDirect] = newDr; //newDr为null,代表从原来有图片设置为无图片
            setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
        }
    }


    /**
     *
     */
    public static class SizeWrap {
        int width;
        int height;

        public boolean checkWidthAndHeight(Drawable drawable, int globalWidth, int globalHeight, int localWidth, int localHeight) {
            width = 0;
            height = 0;

            //局部的大小设置均正常的情况
            if (localWidth > 0 && localHeight > 0) {
                width = localWidth;
                height = localHeight;
                return true;
            }

            //局部大小没设置时，看全局的大小是否正确设置
            if (localWidth == -1 && localHeight == -1) {
                if (globalWidth > 0 && globalHeight > 0) {
                    width = globalWidth;
                    height = globalHeight;
                    return true;
                } else if (globalWidth == -1 && globalHeight == -1) { //没有设置自定义属性，采用图片的默认大小
                    width = drawable.getIntrinsicWidth();
                    height = drawable.getIntrinsicHeight();
                    return true;
                }
            }
            return false;
        }
    }
}