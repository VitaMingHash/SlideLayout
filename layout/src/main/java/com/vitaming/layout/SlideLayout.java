package com.vitaming.layout;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ming
 * 1/4/21
 * todo 间隔
 */
public class SlideLayout<T> extends ViewGroup {
    private BaseAdapter<T> baseAdapter;
    private int cumulateLayoutHeight = 0;
    private List<T> adapterList;

    /**
     * 自定义属性值
     */
    private int padding;

    /**
     * 显示的item的页数
     */
    private int page = 0;
    private float lastX = 0;
    private float lastY = 0;

    /**
     * layout格式
     * 0为ListView
     * 1位GridView
     */
    private int form = 0;

    /**
     * grid模式下一行显示的格式
     */
    private int numColumns = 3;
    /**
     * 默认移动距离
     */
    private final int DEFAULTMOVE = 70;

    /**
     * 添加item的position
     */
    private int addPosition = 0;

    /**
     * 显示的item的位置
     */
    private List<ItemPositionBean<T>> positionBeanList = new ArrayList<>();

    public SlideLayout(Context context) {
        super(context);
    }

    public SlideLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
        initClick();
        initAttr(attrs);
    }

    public void initAttr(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.SlideLayout);
            form = arr.getInt(R.styleable.SlideLayout_form, 0);
            numColumns = arr.getInt(R.styleable.SlideLayout_numColumns, 3);
            padding = arr.getInt(R.styleable.SlideLayout_padding, 0);
            arr.recycle();
        }
    }

    public void initView() {

    }

    /**
     * 初始化触摸事件
     * 暂定抬起位置为判断起点
     */
    private void initClick() {
        setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    lastX = event.getX();
                    lastY = event.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    if (Math.abs(event.getX() - lastX) < DEFAULTMOVE && Math.abs(event.getY() - lastY) < DEFAULTMOVE) {
                        ItemPositionBean<T> itemPositionBean = touchPointView(event.getY(), event.getX());
                        if (baseAdapter != null && itemPositionBean != null) {
                            baseAdapter.onItemClick(itemPositionBean.getView(), itemPositionBean.getT());
                        }
                    } else {
                        removeAllViews();
                        addPosition = 0;
                        if (event.getY() < lastY) {
                            if (cumulateLayoutHeight == 0) {
                                baseAdapter.nextPage(positionBeanList);
                            }
                        } else {
                            baseAdapter.lastPage();
                        }
                        setAdapter(baseAdapter);
                        positionBeanList.clear();
                    }
                    return false;
                default:
                    break;
            }
            return true;
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (form == 0) {
            int countPage = 0;
            int left;
            int right;
            int top;
            int bottom = 0;
            cumulateLayoutHeight = 0;
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                int childHeight = child.getMeasuredHeight();
                ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
                int height = lp.topMargin + lp.bottomMargin + childHeight + (ScreenUtil.dp2px(getContext(), padding));
                left = lp.leftMargin;
                right = left + getWidth();
                if (cumulateLayoutHeight + height <= getHeight()) {
                    top = cumulateLayoutHeight;
                    cumulateLayoutHeight += height;
                    bottom = cumulateLayoutHeight;
                } else {
                    cumulateLayoutHeight = 0;
                    countPage += 1;
                    break;
                }
                if (countPage == page) {
                    child.layout(left, top + getPaddingTop() + (countPage + 1) * (ScreenUtil.dp2px(getContext(), padding)), right, bottom);
                    positionBeanList.add(new ItemPositionBean(top + getPaddingTop() + (countPage + 1) * (ScreenUtil.dp2px(getContext(), padding)), left, bottom, right, child, addPosition, page, baseAdapter.getData().get(addPosition)));
                    addPosition += 1;
                }
            }
        } else if (form == 1 && numColumns != 0) {
            addPosition = 0;
            positionBeanList.clear();
            int itemWidth = (getWidth() - getPaddingLeft() - getPaddingRight() - (numColumns + 1) * (ScreenUtil.dp2px(getContext(), padding))) / numColumns;
            int lineNum = 0;
            int countPage = 0;
            int left;
            int right;
            int top = getPaddingTop();
            int gridLines = 1;
            cumulateLayoutHeight = 0;

            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                int childHeight = child.getMeasuredHeight();
                ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
                int height = childHeight;
                if (lineNum == numColumns) {
                    lineNum = 0;
                    left = getPaddingLeft();
                    top += height;
                    gridLines += 1;
                    cumulateLayoutHeight += childHeight;
                    if (cumulateLayoutHeight + childHeight + gridLines * (ScreenUtil.dp2px(getContext(), padding)) * 2 > getHeight()) {
                        cumulateLayoutHeight = 0;
                        countPage += 1;
                        break;
                    }
                }
                lp.width = itemWidth;
                left = lineNum * itemWidth + getPaddingLeft() + (lineNum + 1) * ScreenUtil.dp2px(getContext(), padding);
                right = left + itemWidth;
                child.layout(left, top + gridLines * (ScreenUtil.dp2px(getContext(), padding)), right, gridLines * height + gridLines * (ScreenUtil.dp2px(getContext(), padding)) * 2);
                child.requestLayout();
                lineNum += 1;
                positionBeanList.add(new ItemPositionBean(top + gridLines * (ScreenUtil.dp2px(getContext(), padding)), left, gridLines * height + gridLines * (ScreenUtil.dp2px(getContext(), padding)) * 2, right, child, addPosition, countPage, baseAdapter.getData().get(addPosition)));
                addPosition += 1;
            }
        }
    }

    /**
     * 设置adapter
     *
     * @param baseAdapter
     */
    public void setAdapter(BaseAdapter<T> baseAdapter) {
        this.baseAdapter = baseAdapter;
        adapterList = baseAdapter.getData();
        for (int i = 0; i < adapterList.size(); i++) {
            View view = LayoutInflater.from(getContext()).inflate(baseAdapter.getLayout(), this, false);
            baseAdapter.convert(new ViewHolder(view), adapterList.get(i), i);
            addView(view);
        }
    }

    /**
     * 获取点击的item
     *
     * @param y
     * @param x
     * @return 点击的ItemPositionBean
     */
    private @Nullable
    ItemPositionBean<T> touchPointView(float y, float x) {
        for (ItemPositionBean<T> itemPositionBean : positionBeanList) {
            if (itemPositionBean.getTop() <= y && itemPositionBean.getBottom() >= y && itemPositionBean.getLeft() <= x && itemPositionBean.getRight() >= x) {
                return itemPositionBean;
            }
        }
        return null;
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return p;
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof MarginLayoutParams;
    }
}
