package com.shyky.demo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;

import com.shyky.library.adapter.ChoiceModeAdapter;

import static com.shyky.library.adapter.ChoiceModeAdapter.CHOICE_MODE_NONE;

/**
 * Multiple choice RecyclerView
 *
 * @author Shyky
 * @version 1.1
 * @date 2017/1/21
 * @since 1.0
 */
public class MultipleChoiceRecyclerView extends RecyclerView {
    /**
     * Controls if/how the user may choose/check items in the list
     */
    private int choiceMode = CHOICE_MODE_NONE;
    private Adapter internalAdapter;

    public MultipleChoiceRecyclerView(Context context) {
        this(context, null);
    }

    public MultipleChoiceRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultipleChoiceRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
//        final TypedArray typedArray = context.obtainStyledAttributes(attrs,
//                R.styleable.MultipleChoiceRecyclerView, 0, defStyle);
//
//        setChoiceMode(typedArray.getInt(R.styleable.MultipleChoiceRecyclerView_android_choiceMode, CHOICE_MODE_NONE));
    }

    public void setOnItemClickListener(@NonNull ChoiceModeAdapter.OnItemClickListener listener) {
        if (internalAdapter instanceof ChoiceModeAdapter) {
            ((ChoiceModeAdapter) internalAdapter).setOnItemClickListener(listener);
        }
    }

    public void setChoiceMode(int choiceMode) {
        this.choiceMode = choiceMode;
        if (internalAdapter instanceof ChoiceModeAdapter) {
            ((ChoiceModeAdapter) internalAdapter).setChoiceMode(this.choiceMode);
        }
    }

    public void setCheckedItemPositions(int... positions) {
        if (internalAdapter instanceof ChoiceModeAdapter) {
            ((ChoiceModeAdapter) internalAdapter).setDefaultCheckedItemPositions(positions);
        }
    }

    public SparseBooleanArray getCheckedItemPositions() {
        if (internalAdapter instanceof ChoiceModeAdapter) {
            return ((ChoiceModeAdapter) internalAdapter).getCheckedItemPositions();
        }
        return null;
    }

    public void check(int position) {
        if (internalAdapter instanceof ChoiceModeAdapter) {
            ((ChoiceModeAdapter) internalAdapter).check(position);
        }
    }
//    @Override
//    public void setAdapter(Adapter adapter) {
//        internalAdapter = adapter;
//        if (choiceMode != CHOICE_MODE_NONE) {
//            if (internalAdapter instanceof ChoiceModeAdapter) {
//                internalAdapter = new ArrayChoiceModeAdapter<>(getContext(), android.R.layout.simple_list_item_multiple_choice, adapter);
//            }
//        }
//        super.setAdapter(internalAdapter);
//    }
}