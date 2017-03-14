package com.shyky.library.adapter;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;

/**
 * 实现单选、多选、切换单选到多选的Adapter
 *
 * @author Shyky
 * @version 1.1
 * @date 2017/1/21
 * @since 1.0
 */
public abstract class ChoiceModeAdapter extends RecyclerView.Adapter {
    /**
     * Normal list that does not indicate choices
     */
    public static final int CHOICE_MODE_NONE = -1;
    /**
     * The list allows up to one choice
     */
    public static final int CHOICE_MODE_SINGLE = 1;
    /**
     * The list allows multiple choices
     */
    public static final int CHOICE_MODE_MULTIPLE = 2;
    /**
     * The list allows multiple choices in a modal selection mode
     */
    public static final int CHOICE_MODE_MULTIPLE_MODAL = 3;
    private final LayoutInflater layoutInflater;
    //    private OnItemClickListener onItemClickListener;
    /**
     * Controls if/how the user may choose/check items in the list
     */
    private int choiceMode = CHOICE_MODE_NONE;
    private SparseBooleanArray checkStates;
    /**
     * Running state of which IDs are currently checked.
     * If there is a value for a given key, the checked state for that ID is true
     * and the value holds the last known position in the adapter for that id.
     */
//    private LongSparseArray<Integer> checkedIdStates;
    /**
     * 默认为-1，没有选择任何item
     */
    private int currentCheckedItemPosition;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position, long id);
    }

    public ChoiceModeAdapter(@NonNull Context context) {
        layoutInflater = LayoutInflater.from(context);
        // 默认没有选择任何item
        checkStates = new SparseBooleanArray(0);
        currentCheckedItemPosition = -1;
    }

    //    @Override
//    public InternalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return new InternalViewHolder(layoutInflater.inflate(R.layout.item_multiple_choice, parent, false));
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (adapter != null) {
//            return adapter.onCreateViewHolder(parent, viewType);
//        } else {
//            final View inflateView = layoutInflater.inflate(resource, parent, false);
//            if (choiceMode == CHOICE_MODE_SINGLE) {
//                return new SingleChoiceViewHolder(inflateView);
//            } else if (choiceMode == CHOICE_MODE_MULTIPLE) {
//                return new MultipleChoiceViewHolder(inflateView);
//            }
//        }
//        return null;
//    }

    @CallSuper
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final View rootView = holder.itemView;
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, position, getItemId(position));
                }
            }
        });

        if (choiceMode == CHOICE_MODE_SINGLE) {
//                invokeAdapterOnBindViewHolder(holder, position);
        } else if (choiceMode == CHOICE_MODE_SINGLE) {
            final RadioButton radioButton = findRadioButtonFromItemView(rootView);
            if (radioButton != null) {
                if (currentCheckedItemPosition == position) {
                    radioButton.setChecked(true);
                } else {
                    radioButton.setChecked(false);
                }
//                    invokeAdapterOnBindViewHolder(holder, position);
            }
        } else if (choiceMode == CHOICE_MODE_MULTIPLE) {
            final CheckBox checkBox = findCheckBoxFromItemView(rootView);
            if (checkBox != null) {
                if (checkStates.get(position)) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
//                    invokeAdapterOnBindViewHolder(holder, position);
            }
        } else if (choiceMode == CHOICE_MODE_MULTIPLE_MODAL) {

        }
    }

    public void setOnItemClickListener(@NonNull OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    //    private void invokeAdapterOnBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        if (adapter != null)
//            adapter.onBindViewHolder(holder, position);
//    }
    public View inflateItemView(@LayoutRes int resource, @NonNull ViewGroup parent) {
        return layoutInflater.inflate(resource, parent, false);
    }

    private RadioButton findRadioButtonFromItemView(View itemView) {
        if (itemView instanceof RadioButton)
            return ((RadioButton) itemView);
        else if (itemView instanceof ViewGroup) {
            return null;
        }
        return null;
    }

    private CheckBox findCheckBoxFromItemView(View itemView) {
        if (itemView instanceof CheckBox)
            return ((CheckBox) itemView);
        else if (itemView instanceof ViewGroup) {
            return null;
        }
        return null;
    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//    public void setOnItemClickListener(@NonNull OnItemClickListener listener) {
//        onItemClickListener = listener;
//    }

    /**
     * Defines the choice behavior for the List. By default, Lists do not have any choice behavior
     * ({@link #CHOICE_MODE_NONE}). By setting the choiceMode to {@link #CHOICE_MODE_SINGLE}, the
     * List allows up to one item to  be in a chosen state. By setting the choiceMode to
     * {@link #CHOICE_MODE_MULTIPLE}, the list allows any number of items to be chosen.
     *
     * @param choiceMode One of {@link #CHOICE_MODE_NONE}, {@link #CHOICE_MODE_SINGLE}, or
     *                   {@link #CHOICE_MODE_MULTIPLE}
     */
    public void setChoiceMode(int choiceMode) {
        this.choiceMode = choiceMode;
//        if (mChoiceActionMode != null) {
//            mChoiceActionMode.finish();
//            mChoiceActionMode = null;
//        }
        if (choiceMode != CHOICE_MODE_NONE) {
            if (checkStates == null) {
                checkStates = new SparseBooleanArray(0);
            }
        }
////            if (checkedIdStates == null && mAdapter != null && mAdapter.hasStableIds()) {
////                checkedIdStates = new LongSparseArray<Integer>();
////            }
//            // Modal multi-choice mode only has choices when the mode is active. Clear them.
////            if (choiceMode == CHOICE_MODE_MULTIPLE_MODAL) {
////                clearChoices();
////                setLongClickable(true);
////            }
//        }
    }

    public void setDefaultCheckedItemPosition(int position) {
        currentCheckedItemPosition = position;
    }

    public int getCheckedItemPosition() {
        return currentCheckedItemPosition;
    }

    public void setDefaultCheckedItemPositions(int... positions) {
        for (int item : positions) {
            checkStates.put(item, true);
        }
    }

    public void check(int position) {
        // 如果当前item已选中了，则从集合中删除，否则选择该item
        if (checkStates.get(position)) {
            checkStates.delete(position);
        } else
            checkStates.put(position, true);
        notifyDataSetChanged();
    }

    public void checks(int position) {
        setDefaultCheckedItemPosition(position);
        notifyDataSetChanged();
    }

    public SparseBooleanArray getCheckedItemPositions() {
        return checkStates;
    }
}