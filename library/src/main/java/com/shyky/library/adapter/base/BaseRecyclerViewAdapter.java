package com.shyky.library.adapter.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.text.Spanned;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用的RecyclerView Adapter
 *
 * @param <ENTITY> 泛型参数，数据源集合中的实体
 * @author Copyright(C)2011-2017 Shyky Studio.
 * @version 1.7
 * @email sj1510706@163.com
 * @date 2016/3/31
 * @since 1.0
 */
public abstract class BaseRecyclerViewAdapter<ENTITY> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.GenericRecyclerViewHolder> {
    /**
     * 默认布局类型
     */
    public static final int VIEW_TYPE_NORMAL = 10;
    /**
     * 头部布局类型
     */
    public static final int VIEW_TYPE_HEADER = 11;
    /**
     * 底部布局类型
     */
    public static final int VIEW_TYPE_FOOTER = 12;
    /**
     * 应用程序上下文
     */
    protected Context context;
    /**
     * 布局加载器
     */
    private final LayoutInflater layoutInflater;
    /**
     * 数据源
     */
    private List<ENTITY> data;
    private final List<View> headerViews;
    private final List<View> footerViews;
    /**
     * 保存Item view type类型集合
     */
//    private List<Integer> viewTypes;
    /**
     * Item点击事件回调
     */
    private OnItemClickListener onItemClickListener;
    /**
     * Item View里的某个view的点击事件回调
     */
    private OnItemViewClickListener onItemViewClickListener;
    /**
     * Item长按事件回调
     */
    private OnItemLongClickListener onItemLongClickListener;
    /**
     * Item View里的某个view的长按事件回调
     */
    private OnItemViewLongClickListener onItemViewLongClickListener;
    /**
     * 当前的item view布局类型
     */
//    private int viewType;

    /**
     * Item点击事件监听器
     */
    public interface OnItemClickListener {
        /**
         * 当Item View点击回调方法
         *
         * @param view      被点击的item view
         * @param viewResId item view控件资源ID
         * @param position  item的位置
         */
        void onItemClick(@NonNull View view, @IdRes int viewResId, int position);
    }

    /**
     * Item View中的view点击事件监听器
     */
    public interface OnItemViewClickListener {
        /**
         * 当Item View点击回调方法
         *
         * @param view     被点击的view控件
         * @param position item的位置
         */
        void onClick(@NonNull View view, @IdRes int viewResId, int position);
    }

    /**
     * Item View长按事件监听器
     */
    public interface OnItemLongClickListener {
        boolean onItemLongClick(@NonNull View view, @IdRes int viewResId, int position);
    }

    /**
     * Item View长按事件监听器
     */
    public interface OnItemViewLongClickListener {
        boolean onLongClick(@NonNull View view, @IdRes int viewResId, int position);
    }

    /**
     * 构造方法
     *
     * @param context 应用程序上下文
     */
    protected BaseRecyclerViewAdapter(@NonNull Context context) {
        this(context, null);
    }

    /**
     * 构造方法
     *
     * @param context 应用程序上下文
     * @param data    数据源
     */
    protected BaseRecyclerViewAdapter(@NonNull Context context, @NonNull List<ENTITY> data) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        if (data == null) {
            this.data = new ArrayList<>();
        } else {
            this.data = data;
        }
        headerViews = new ArrayList<>();
        footerViews = new ArrayList<>();

        final int layoutResId = getHeaderItemLayoutResId();
        if (layoutResId > 0)
            addHeaderItem(layoutResId);
        final int[] layoutResIds = getHeaderItemLayoutResIds();
        if (layoutResIds != null && layoutResIds.length > 0) {
            for (int resId : layoutResIds) {
                addHeaderItem(resId);
            }
        }
        //
        final int layoutResId2 = getFooterItemLayoutResId();
        if (layoutResId2 > 0)
            addFooterItem(layoutResId2);
        final int[] layoutResIds2 = getFooterItemLayoutResIds();
        if (layoutResIds2 != null && layoutResIds2.length > 0) {
            for (int resId : layoutResIds2) {
                addFooterItem(resId);
            }
        }
    }

    /**
     * 获取Item布局资源文件ID
     *
     * @param viewType item布局类型
     * @return 资源文件ID
     */
    public abstract int getItemLayoutResId(int viewType);

    /**
     * 获取Item布局资源文件ID
     *
     * @return 资源文件ID数组
     */
    @LayoutRes
    public int getHeaderItemLayoutResId() {
        return 0;
    }

    /**
     * 获取Item布局资源文件ID
     *
     * @return 资源文件ID数组
     */
    @LayoutRes
    public int[] getHeaderItemLayoutResIds() {
        return null;
    }

    /**
     * 获取Item布局资源文件ID
     *
     * @return 资源文件ID数组
     */
    public View getHeaderItemView() {
        return null;
    }

    /**
     * 获取Item布局资源文件ID
     *
     * @return 资源文件ID数组
     */
    public View[] getHeaderItemViews() {
        return null;
    }

    /**
     * 获取Item布局资源文件ID
     *
     * @return 资源文件ID数组
     */
    @LayoutRes
    public int getFooterItemLayoutResId() {
        return 0;
    }

    /**
     * 获取Item布局资源文件ID
     *
     * @return 资源文件ID数组
     */
    public int[] getFooterItemLayoutResIds() {
        return null;
    }

    /**
     * 获取Item布局资源文件ID
     *
     * @return 资源文件ID数组
     */
    public View getFooterItemView() {
        return null;
    }

    /**
     * 获取Item布局资源文件ID
     *
     * @return 资源文件ID数组
     */
    public View[] getFooterItemViews() {
        return null;
    }

    /**
     * 绑定Item view及设置显示数据
     *
     * @param viewHolder
     * @param viewType
     * @param position
     */
    public abstract void bindViewHolder(@NonNull GenericRecyclerViewHolder viewHolder, int viewType, int position);

    /**
     * 获取item view的布局参数，用于调整item view的布局
     *
     * @param layoutParams 布局参数
     * @param viewType     item view布局类型
     * @return 调整后的布局参数
     */
    public ViewGroup.LayoutParams getItemViewLayoutParams(ViewGroup.LayoutParams layoutParams, int viewType) {
        return null;
    }

    /**
     * 获取要点击的item view里的view的资源ID
     *
     * @return view的资源ID数组
     */
    public int[] getClickItemViewResId() {
        return null;
    }

    /**
     * 获取要点击的item view里的view的资源ID
     *
     * @return view的资源ID数组
     */
    public int[] getLongClickItemViewResId() {
        return null;
    }

    protected void onItemClick(View view, int viewResId, int position) {

    }

    /**
     * 内置一个通用的ViewHolder用于包装各个item view控件
     */
    public final class GenericRecyclerViewHolder extends RecyclerView.ViewHolder implements OnClickListener, OnLongClickListener {
        /**
         * 保存ViewHolder中的各个View
         */
        private SparseArray<View> views;

        /**
         * 构造方法
         *
         * @param itemView item view布局
         */
        public GenericRecyclerViewHolder(View itemView) {
            super(itemView);
            views = new SparseArray<>();
//            this.itemView = itemView;
            itemView.setTag(this);
//            rootView.setTag(position);
            // 监听item点击事件
            itemView.setOnClickListener(this);
            // 监听item长按事件
            itemView.setOnLongClickListener(this);

            // 处理item view中的view的点击事件
            final int[] clickViewResIds = getClickItemViewResId();
            if (clickViewResIds != null) {
                final ClickListener clickListener = new ClickListener(getAdapterPosition());
                for (int resId : clickViewResIds)
                    setOnClickListener(getItemViewType(), resId, clickListener);
            }

            // 处理item view中的view的长按事件
            final int[] longClickViewResIds = getLongClickItemViewResId();
            if (longClickViewResIds != null) {
                final LongClickListener longClickListener = new LongClickListener(getAdapterPosition());
                for (int resId : longClickViewResIds)
                    setOnLongClickListener(getItemViewType(), resId, longClickListener);
            }
        }

        public <VIEW extends View> VIEW getView(int viewType, int viewId) {
            // 通过viewType+viewId区分View
            View view = views.get(viewType + viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                views.put(viewType + viewId, view);
            }
            return (VIEW) view;
        }

        public GenericRecyclerViewHolder setVisibility(int viewType, int viewResId, int visibility) {
            final View view = getView(viewType, viewResId);
            if (view != null)
                view.setVisibility(visibility);
            else
                throw new NullPointerException("找不到id:" + viewResId + "的控件，请检查是否在item view中定义了或有无设置控件ID");
            return this;
        }

        public GenericRecyclerViewHolder setBackgroundColor(int viewType, int viewResId, int color) {
            final View view = getView(viewType, viewResId);
            if (view != null)
                view.setBackgroundColor(color);
            return this;
        }

        public GenericRecyclerViewHolder setBackgroundResource(int viewType, int viewResId, int resId) {
            final View view = getView(viewType, viewResId);
            if (view != null)
                view.setBackgroundResource(resId);
            return this;
        }

        public GenericRecyclerViewHolder setTag(int viewType, @IdRes int viewResId, int key, Object tag) {
            final View view = getView(viewType, viewResId);
            if (view != null)
                view.setTag(key, tag);
            return this;
        }

        public GenericRecyclerViewHolder setLayoutParams(int viewType, @IdRes int viewResId, @NonNull RadioGroup.LayoutParams params) {
            final View view = getView(viewType, viewResId);
            if (view != null)
                view.setLayoutParams(params);
            return this;
        }

        public GenericRecyclerViewHolder setLayoutParams(int viewType, @IdRes int viewResId, @NonNull LinearLayout.LayoutParams params) {
            final View view = getView(viewType, viewResId);
            if (view != null)
                view.setLayoutParams(params);
            return this;
        }

        public GenericRecyclerViewHolder setLayoutParams(int viewType, @IdRes int viewResId, @NonNull RelativeLayout.LayoutParams params) {
            final View view = getView(viewType, viewResId);
            if (view != null)
                view.setLayoutParams(params);
            return this;
        }

        public GenericRecyclerViewHolder setLayoutParams(int viewType, @IdRes int viewResId, @NonNull FrameLayout.LayoutParams params) {
            final View view = getView(viewType, viewResId);
            if (view != null)
                view.setLayoutParams(params);
            return this;
        }

        public GenericRecyclerViewHolder setTag(int viewType, @IdRes int viewResId, @NonNull Object tag) {
            final View view = getView(viewType, viewResId);
            if (view != null)
                view.setTag(tag);
            return this;
        }

        public GenericRecyclerViewHolder setText(@NonNull TextView textView, @NonNull String text) {
            if (textView != null)
                textView.setText(text);
            return this;
        }

        public GenericRecyclerViewHolder setText(@NonNull TextView textView, @StringRes int resId) {
            if (textView != null)
                textView.setText(resId);
            return this;
        }

        public GenericRecyclerViewHolder setText(int viewType, @IdRes int viewResId, byte value) {
            return setText(viewType, viewResId, String.valueOf(value));
        }

        public GenericRecyclerViewHolder setText(int viewType, @IdRes int viewResId, boolean value) {
            return setText(viewType, viewResId, String.valueOf(value));
        }

        public GenericRecyclerViewHolder setText(int viewType, @IdRes int viewResId, short value) {
            return setText(viewType, viewResId, String.valueOf(value));
        }

        public GenericRecyclerViewHolder setText(int viewType, @IdRes int viewResId, long value) {
            return setText(viewType, viewResId, String.valueOf(value));
        }

        public GenericRecyclerViewHolder setText(int viewType, @IdRes int viewResId, float value) {
            return setText(viewType, viewResId, String.valueOf(value));
        }

        public GenericRecyclerViewHolder setText(int viewType, @IdRes int viewResId, double value) {
            return setText(viewType, viewResId, String.valueOf(value));
        }

        public GenericRecyclerViewHolder setText(int viewType, @IdRes int viewResId, @NonNull String text) {
            final TextView textView = getView(viewType, viewResId);
            if (textView != null)
                textView.setText(text);
            else {
                // do nothing
                // throw new NullPointerException("找不到id = " + viewResId + "的控件，请检查是否在item view中定义了或有无设置控件ID");
            }
            return this;
        }

        public GenericRecyclerViewHolder setText(int viewType, @IdRes int viewResId, int resIdOrIntValue) {
            final TextView textView = getView(viewType, viewResId);
            if (textView != null) {
                // 运行时找不到会抛出异常
                try {
                    if (context.getResources().getResourceName(resIdOrIntValue) != null)
                        textView.setText(resIdOrIntValue);
                    else
                        textView.setText(String.valueOf(resIdOrIntValue));
                } catch (Exception e) {
                    textView.setText(String.valueOf(resIdOrIntValue));
                }
            }
            return this;
        }

        public GenericRecyclerViewHolder setText(int viewType, @IdRes int viewResId, @NonNull Spanned html) {
            final TextView textView = getView(viewType, viewResId);
            if (textView != null)
                textView.setText(html);
            return this;
        }

        /**
         * 设置TextView的显示文本及Flags文本效果
         *
         * @param viewType  Item的布局类型
         * @param viewResId 要设置的TextView控件的View ID
         * @param resId     string字符串资源ID
         * @param flags     TextView的Flags，从Paint这个类中取
         * @return BaseRecyclerViewHolder实例对象
         */
        public GenericRecyclerViewHolder setText(int viewType, @IdRes int viewResId, @StringRes int resId, int flags) {
            return setText(viewType, viewResId, context.getString(resId), flags);
        }

        /**
         * 设置TextView的显示文本及Flags文本效果
         *
         * @param viewType  Item的布局类型
         * @param viewResId 要设置的TextView控件的View ID
         * @param text      要显示文本
         * @param flags     TextView的Flags，从Paint这个类中取
         * @return BaseRecyclerViewHolder实例对象
         */
        public GenericRecyclerViewHolder setText(int viewType, @IdRes int viewResId, @NonNull String text, int flags) {
            final TextView textView = getView(viewType, viewResId);
            if (textView != null) {
                // textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); // 下划线
                textView.getPaint().setAntiAlias(true); // 抗锯齿
                // Paint.STRIKE_~_TEXT_FLAG 中划线
                textView.getPaint().setFlags(flags | Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
                textView.setText(text);
            }
            return this;
        }

        public GenericRecyclerViewHolder setTextColor(int viewType, @IdRes int viewResId, int color) {
            final TextView textView = getView(viewType, viewResId);
            if (textView != null)
                textView.setTextColor(color);
            return this;
        }

        public GenericRecyclerViewHolder setImageResource(int viewType, @IdRes int viewResId, @DrawableRes int drawableResId) {
            final ImageView imageView = getView(viewType, viewResId);
            if (imageView != null)
                imageView.setImageResource(drawableResId);
            return this;
        }

        public GenericRecyclerViewHolder setImageDrawable(int viewType, @IdRes int viewResId, @NonNull Drawable drawable) {
            final ImageView imageView = getView(viewType, viewResId);
            if (imageView != null)
                imageView.setImageDrawable(drawable);
            return this;
        }

        public GenericRecyclerViewHolder setImageDrawable(int viewType, int viewId, int resId) {
            final ImageView imageView = getView(viewType, viewId);
            if (imageView != null)
                imageView.setImageResource(resId);
            return this;
        }

        public GenericRecyclerViewHolder setImageBitmap(int viewType, int viewId, Bitmap bitmap) {
            final ImageView imageView = getView(viewType, viewId);
            if (imageView != null)
                imageView.setImageBitmap(bitmap);
            return this;
        }

        public GenericRecyclerViewHolder setProgress(int viewType, int viewId, int progress) {
            final ProgressBar progressBar = getView(viewType, viewId);
            if (progressBar != null)
                progressBar.setProgress(progress);
            return this;
        }

        public GenericRecyclerViewHolder setMax(int viewType, int viewId, int max) {
            final ProgressBar progressBar = getView(viewType, viewId);
            if (progressBar != null)
                progressBar.setMax(max);
            return this;
        }

        public GenericRecyclerViewHolder setChecked(int viewType, @IdRes int viewResId, boolean checked) {
            final View view = getView(viewType, viewResId);
            if (view != null) {
                if (view instanceof CheckBox) {
                    final CheckBox checkBox = (CheckBox) view;
                    if (checkBox != null)
                        checkBox.setChecked(checked);
                } else if (view instanceof RadioButton) {
                    final RadioButton radioButton = (RadioButton) view;
                    if (radioButton != null)
                        radioButton.setChecked(checked);
                }
            }
            return this;
        }

        public final GenericRecyclerViewHolder setRating(int viewType, int viewResId, float rating) {
            final RatingBar ratingBar = getView(viewType, viewResId);
            if (ratingBar != null)
                ratingBar.setRating(rating);
            return this;
        }

        public final GenericRecyclerViewHolder setOnClickListener(int viewType, int viewResId, OnClickListener onClickListener) {
            final View view = getView(viewType, viewResId);
            if (view != null)
                view.setOnClickListener(onClickListener);
            return this;
        }

        public final GenericRecyclerViewHolder setOnLongClickListener(int viewType, int viewResId, OnLongClickListener onLongClickListener) {
            final View view = getView(viewType, viewResId);
            if (view != null)
                view.setOnLongClickListener(onLongClickListener);
            return this;
        }

        public final GenericRecyclerViewHolder setOnCheckedChangeListener(int viewType, int viewResId, CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
            final CheckBox checkBox = getView(viewType, viewResId);
            if (checkBox != null)
                checkBox.setOnCheckedChangeListener(onCheckedChangeListener);
            return this;
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, v.getId(), getAdapterPosition() /*(int) v.getTag()*/);
                onItemClick(v, v.getId(), getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (onItemLongClickListener != null) {
                onItemLongClickListener.onItemLongClick(v, v.getId(), getAdapterPosition() /*(int) v.getTag()*/);
            }
            return false;
        }
    }

    public final void setOnItemClickListener(@NonNull OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public final void setOnItemViewClickListener(@NonNull OnItemViewClickListener onItemViewClickListener) {
        this.onItemViewClickListener = onItemViewClickListener;
    }

    public final void setOnItemLongClickListener(@NonNull OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public final void setOnItemViewLongClickListener(@NonNull OnItemViewLongClickListener onItemViewLongClickListener) {
        this.onItemViewLongClickListener = onItemViewLongClickListener;
    }

    /**
     * 设置数据源
     *
     * @param data 数据源集合
     */
    public final void setData(@NonNull List<ENTITY> data) {
        this.data = data;
    }

    /**
     * 获取指定位置的Item数据源
     *
     * @param position Item的位置
     * @return 如果位置正确返回数据源中的实体，否则返回null
     */
    public ENTITY getItem(int position) {
//        int viewTypeCount = viewTypes.size();
//        // 如果viewTypeCount大于1说明还有别的item view
//        if (viewTypeCount > 1)
//            position = (position - viewTypeCount) + 1; // 取出正确的索引，防止越界
        if (data == null || position < 0 || position >= data.size())
            return null;
//        return data.get(position);


        // Header (negative positions will throw an IndexOutOfBoundsException)
        int numHeaders = getHeaderItemCount();
        if (position < numHeaders) {

            return null;
//            headerView = headerViews.get(position);
        }

        // Adapter
        final int adjPosition = position - numHeaders;
        int adapterCount = data.size();
        if (adjPosition < adapterCount) {
//                adapter.onBindViewHolder(holder, adjPosition);

            return data.get(adjPosition);
//            bindViewHolder(holder, getItemViewType(position), );
        }


        // Footer (off-limits positions will throw an IndexOutOfBoundsException)
//        footerView = footerViews.get(adjPosition - adapterCount);


        Log.d("Test", "position = " + position);
        Log.d("Test", "getHeaderItemCount = " + getHeaderItemCount());
//        return data.get(position - getHeaderItemCount());
        return null;
    }

//    /**
//     * 获取Item View type的个数
//     *
//     * @return item viewType的个数
//     */
//    public final int getItemViewTypeCount() {
//        return viewTypes.size();
//    }

    //    @Override
//    public RecyclerView.ViewHolder onCreatseViewHolder(ViewGroup parent, int viewType) {
//        this.viewType = viewType;
//        // 保存不同的Item view布局类型，需要去重判断，这个方法会多次调用
//        if (!viewTypes.contains(viewType))
//            viewTypes.add(viewType);
//        View inflateView;
//        final int layoutResId = getItemLayoutResId(viewType);
//        // 使用全局的Context对象会造成UI显示不正常问题
//        if (layoutResId > 0) {
//            inflateView = layoutInflater.inflate(layoutResId, parent, false);
//            // 用于控制瀑布流布局时，有些Item View需要横向显示，需要子类去根据需求设置参数，让Item View横向全屏显示
//            final ViewGroup.LayoutParams layoutParams = getItemViewLayoutParams(inflateView.getLayoutParams(), viewType);
//            if (layoutParams != null)
//                inflateView.setLayoutParams(layoutParams);
//            return new BaseRecyclerViewHolder(inflateView);
//        }
//        return null;
//    }

    //    @Override
//    public final void onBindVisewHolder(RecyclerView.ViewHolder holder, int position) {
//        final View rootView = ((BaseRecyclerViewHolder) holder).getRootView();
//        if (rootView != null) {
//            rootView.setTag(position);
//            rootView.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (onItemClickListener != null) {
//                        onItemClickListener.onItemClick(v, v.getId(), (int) v.getTag());
//                    }
//                }
//            });
//            rootView.setOnLongClickListener(new OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    if (onItemLongClickListener != null)
//                        onItemLongClickListener.onItemLongClick(v, v.getId(), (int) v.getTag());
//                    return false;
//                }
//            });
//
//            // 处理item view中的view的点击事件
//            final int[] clickViewResIds = getClickItemViewResId();
//            if (clickViewResIds != null) {
//                final ClickListener clickListener = new ClickListener(position);
//                for (int resId : clickViewResIds)
//                    ((BaseRecyclerViewHolder) holder).setOnClickListener(viewType, resId, clickListener);
//            }
//
//            // 处理item view中的view的长按事件
//            final int[] longClickViewResIds = getLongClickItemViewResId();
//            if (longClickViewResIds != null) {
//                final LongClickListener longClickListener = new LongClickListener(position);
//                for (int resId : longClickViewResIds)
//                    ((BaseRecyclerViewHolder) holder).setOnLongClickListener(viewType, resId, longClickListener);
//            }
//        }
//        bindViewHolder((BaseRecyclerViewHolder) holder, getItemViewType(position), position);
//    }

    private class ClickListener implements OnClickListener {
        private int position;

        public ClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            onItemViewClickListener.onClick(view, view.getId(), position);
        }
    }

    private class LongClickListener implements OnLongClickListener {
        private int position;

        public LongClickListener(int position) {
            this.position = position;
        }

        @Override
        public boolean onLongClick(View v) {
            onItemViewLongClickListener.onLongClick(v, v.getId(), position);
            return false;
        }
    }

//    /**
//     * 获取数据源大小
//     *
//     * @return 数据源大小
//     */
//    public final int getItemDataCount() {
//        return data == null ? 0 : data.size();
//    }

//    /**
//     * 获取数据源里的数据
//     *
//     * @param position 位置
//     * @return 位置正确返回实体，否则返回null
//     */
//    public final ENTITY getDataItem(int position) {
//        if (position >= 0 && position < getItemDataCount())
//            return data.get(position);
//        return null;
//    }

    /**
     * 数据源是否为空
     *
     * @return 数据源为null或集合为空返回true，否则返回false
     */
    public final boolean isEmpty() {
        return data == null ? true : data.isEmpty();
    }

    /**
     * @return true if this adapter doesn't contain any data.  This is used to determine
     * whether the empty view should be displayed.  A typical implementation will return
     * getCount() == 0 but since getCount() includes the headers and footers, specialized
     * adapters might want a different behavior.
     */
    public final void add(@NonNull ENTITY entity) {
        if (!isEmpty() && entity != null) {
            data.add(entity);
            notifyDataSetChanged();
        }
    }

    /**
     * 获取数据源集合
     *
     * @return 数据源集合
     */
    public final List<ENTITY> getData() {
        return data;
    }

    /**
     * 设置指定位置的item实体
     *
     * @param position
     * @param entity
     */
    public final void set(int position, @NonNull ENTITY entity) {
        if (position >= 0 && position < getItemCount() /*getItemDataCount()*/ && entity != null) {
            data.set(position, entity);
            notifyItemChanged(position);
        }
    }

    public final void addAll(@NonNull List<ENTITY> list) {
        if (data != null && list != null) {
            data.addAll(list);
            notifyDataSetChanged();
        }
    }

    public final void insert(int index, @NonNull ENTITY entity) {
        if (index >= 0 && index < data.size() && entity != null) {
            data.add(index, entity);
            notifyItemInserted(index);
        }
    }

    public final void remove(@NonNull ENTITY entity) {
        if (!isEmpty() && entity != null) {
            data.remove(entity);
            notifyDataSetChanged();
        }
    }

    public final void remove(int position) {
        if (position >= 0 && position < data.size()) {
            data.remove(position);
            notifyItemRemoved(position);
        }
    }

    public final void clear() {
        if (data != null) {
            data.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemViewType(int position) {
        // Header
        int numHeaders = getHeaderItemCount();
        if (position < numHeaders) {
            return VIEW_TYPE_HEADER;
        }

        // data item(Adapter)
        final int adjPosition = position - numHeaders;
        final int adapterCount = getItemCount();
        if (adjPosition < adapterCount) {
            return VIEW_TYPE_NORMAL; //super.getItemViewType(position);
        }

        // Footer
        return VIEW_TYPE_FOOTER;
    }

    @Override
    public final BaseRecyclerViewAdapter.GenericRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        this.viewType = viewType;
//        // 保存不同的Item view布局类型，需要去重判断，这个方法会多次调用
//        if (!viewTypes.contains(viewType))
//            viewTypes.add(viewType);
//        View inflateView = null;
//        int layoutResId = getItemLayoutResId(viewType);

        switch (viewType) {
            case VIEW_TYPE_HEADER:
            case VIEW_TYPE_FOOTER:
                final FrameLayout frameLayout = new FrameLayout(context);
                frameLayout.setLayoutParams(
                        new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT));

//                //
//                final int resId = getHeaderItemLayoutResId();
//                if (resId != 0)
//                    addHeaderItem(resId);
//                final int[] resIds = getHeaderItemLayoutResIds();
//                if (resIds != null) {
//                    for (int id : resIds) {
//                        addHeaderItem(id);
//                    }
//                }
//                //
//
//                //
//                final int footerResId = getFooterItemLayoutResId();
//                if (footerResId != 0)
//                   addFooterItem(footerResId);
//                final int[] footerResIds = getFooterItemLayoutResIds();
//                if (footerResIds != null) {
//                    for (int id : footerResIds) {
//                        addFooterItem(id);
//                    }
//                }
//                //

                return new GenericRecyclerViewHolder(frameLayout);
            case VIEW_TYPE_NORMAL:
                final int layoutResId = getItemLayoutResId(viewType);
                if (layoutResId > 0) {
                    final View inflateView = layoutInflater.inflate(layoutResId, parent, false);
                    // 用于控制瀑布流布局时，有些Item View需要横向显示，需要子类去根据需求设置参数，让Item View横向全屏显示
                    final ViewGroup.LayoutParams layoutParams = getItemViewLayoutParams(inflateView.getLayoutParams(), viewType);
                    if (layoutParams != null)
                        inflateView.setLayoutParams(layoutParams);
                    return new GenericRecyclerViewHolder(inflateView);
                } else
                    throw new IllegalArgumentException("未指定item布局文件");
        }
        return null;

//        if (viewType == VIEW_TYPE_HEADER) {
//            inflateView = new FrameLayout(context);
//            inflateView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.MATCH_PARENT));
//        } else if (viewType == VIEW_TYPE_FOOTER) {
//            inflateView = new FrameLayout(context);
//            inflateView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.MATCH_PARENT));
//        }
////        switch (viewType) {
////            case VIEW_TYPE_HEADER:
////                final int resId = getHeaderItemLayoutResId();
////                if (resId != 0)
////                    layoutResId = resId;
//////            case VIEW_TYPE_FOOTER:
//////                return new HeaderFooterViewAdapter.FooterViewHolder(footerView);
//////            default:
//////                if (adapter != null)
//////                    return adapter.onCreateViewHolder(parent, viewType);
////        }
//
//
//        if (layoutResId > 0) {
//            inflateView = layoutInflater.inflate(layoutResId, parent, false);
//            // 用于控制瀑布流布局时，有些Item View需要横向显示，需要子类去根据需求设置参数，让Item View横向全屏显示
//            final ViewGroup.LayoutParams layoutParams = getItemViewLayoutParams(inflateView.getLayoutParams(), viewType);
//            if (layoutParams != null)
//                inflateView.setLayoutParams(layoutParams);
////            return new GenericRecyclerViewHolder(inflateView);
//        }
////        return null;
//        return new GenericRecyclerViewHolder(inflateView);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewAdapter.GenericRecyclerViewHolder holder, int position) {

        // Header (negative positions will throw an IndexOutOfBoundsException)
        final int numHeaders = getHeaderItemCount();

        //check what type of view our position is
        if (position < headerViews.size()) {
            final View view = headerViews.get(position);
            //add our view to a header view and display it
            prepareHeaderFooter(holder, view);
        } else if (position >= numHeaders + getDataSize()) {
            final View view = footerViews.get(position - getDataSize() - numHeaders);
            //add oru view to a footer view and display it
            prepareHeaderFooter(holder, view);
        } else {
            //it's one of our items, display as required
//            prepareGeneric((GenericViewHolder) vh, position - headers.size());

            bindViewHolder(holder, getItemViewType(position), position - numHeaders);
        }


//        // data item(Adapter)
//        final int adjPosition = position - numHeaders;
//        final int adapterCount = getItemCount();
//        if (adjPosition < adapterCount) {
//
////            if (position < numHeaders) {
//            final View view = headerViews.get(position);
//            //getHeaderItem(position);//headers.get(position);
//            //empty out our FrameLayout and replace with our header/footer
//            ((FrameLayout) holder.itemView).removeAllViews();
//            ((FrameLayout) holder.itemView).addView(view);
//
////            headerView = headerViews.get(position);
//        } else if (adjPosition < adapterCount) {
//            bindViewHolder(holder, getItemViewType(position), position - numHeaders);
//
////            bindViewHolder(holder, getItemViewType(position), position);
//
//
//        } else {
//            final View view = footerViews.get(position - getDataSize() - numHeaders);
//
//            ((FrameLayout) holder.itemView).removeAllViews();
//            ((FrameLayout) holder.itemView).addView(view);
//        }
//
//        // Adapter
//        final int adjPosition = position - numHeaders;
//        int adapterCount = data.size();
//        if (adjPosition < adapterCount) {
////                adapter.onBindViewHolder(holder, adjPosition);
//
//            bindViewHolder(holder, getItemViewType(position), adjPosition);
//            return;
//        }


        // Footer (off-limits positions will throw an IndexOutOfBoundsException)
//        footerView = footerViews.get(adjPosition - adapterCount);
//
//        final View view = footerViews.get(adjPosition - adapterCount);
//
//        ((FrameLayout) holder.itemView).removeAllViews();
//        ((FrameLayout) holder.itemView).addView(view);
        //add oru view to a footer view and display it
//        prepareHeaderFooter((HeaderFooterViewHolder) vh, v);


//        bindViewHolder(holder, getItemViewType(position), position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
//        final int count = viewTypes.size();
        // count为1说明只有列表Item，否则需要减去1，因为列表类型也算进去了
        // 解决count为0时不会显示item view问题
//        return ((count == 1 || count == 0) ? 0 : count - 1) + (data == null || data.isEmpty() ? 0 : data.size());

        return getHeaderItemCount() + getDataSize() + getFooterItemCount();
    }

    private void prepareHeaderFooter(GenericRecyclerViewHolder viewHolder, View view) {
        //empty out our FrameLayout and replace with our header/footer
        ((FrameLayout) viewHolder.itemView).removeAllViews();
        ((FrameLayout) viewHolder.itemView).addView(view);
    }

    public final int getDataSize() {
        return data == null ? 0 : data.size();
    }

    public final int getHeaderItemCount() {
//        final int layoutResId = getHeaderItemLayoutResId();
//        if (layoutResId != 0)
//            return 1;
//        final int[] layoutResIds = getHeaderItemLayoutResIds();
//        if (layoutResIds != null) {
//            for (int resId : layoutResIds) {
//                addHeaderItem(resId);
//            }
//        }
        return headerViews.size();
    }

    public final boolean hasHeaderItem() {
        return getHeaderItemCount() > 0;
    }

    public final View getHeaderItem(int position) {
        if (position >= 0 && position < getHeaderItemCount()) {
            return headerViews.get(position);
        }
        return null;
    }

    public final void addHeaderItem(@LayoutRes int resId) {
        addHeaderItem(layoutInflater.inflate(resId, null, false));
    }

    public final void addHeaderItem(int position, @LayoutRes int resId) {
        addHeaderItem(position, layoutInflater.inflate(resId, null, false));
    }

    public final void addHeaderItem(@NonNull View view) {
        headerViews.add(view);
    }

    public final void addHeaderItem(int position, @NonNull View view) {
        headerViews.add(position, view);
    }

    public final boolean removeHeaderView(@NonNull View view) {
        if (hasHeaderItem()) {
            return headerViews.remove(view);
        }
        return false;
    }

    public final boolean removeHeaderView(int position, @NonNull View view) {
        if (hasHeaderItem() && position >= 0 && position < getHeaderItemCount()) {
            return headerViews.remove(view);
        }
        return false;
    }

    public final int getFooterItemCount() {
//        final int layoutResId = getFooterItemLayoutResId();
//        if (layoutResId != 0)
//            return 1;
//        final int[] layoutResIds = getFooterItemLayoutResIds();
//        if (layoutResIds != null) {
//            for (int resId : layoutResIds) {
//                addFooterItem(resId);
//            }
//        }
        return footerViews.size();
    }

    public final boolean hasFooterItem() {
        return getFooterItemCount() > 0;
    }

    public final View getFooterItem(int position) {
        if (position >= 0 && position < getFooterItemCount()) {
            return footerViews.get(position);
        }
        return null;
    }

    public final void addFooterItem(@LayoutRes int resId) {
        addFooterItem(layoutInflater.inflate(resId, null, false));
    }

    public final void addFooterItem(int position, @LayoutRes int resId) {
        addFooterItem(position, layoutInflater.inflate(resId, null, false));
    }

    public final void addFooterItem(@NonNull View view) {
        footerViews.add(view);
    }

    public final void addFooterItem(int position, @NonNull View view) {
        footerViews.add(position, view);
    }

    public boolean removeFooterItem(@NonNull View view) {
        if (view != null && hasFooterItem()) {
            return footerViews.remove(view);
        }
        return false;
    }

    public boolean removeFooterItem(int position, @NonNull View view) {
        if (view != null && hasFooterItem() && position >= 0 && position < getHeaderItemCount()) {
            return footerViews.remove(view);
        }
        return false;
    }

//    void ss() {
//        ListView ss = null;
//        ss. ()
//    }
}