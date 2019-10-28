package com.sxenon.arch.select.notifier.rv;

import android.support.v7.util.DiffUtil;

import com.sxenon.arch.adapter.rv.RecyclerViewAdapter;
import com.sxenon.arch.select.ISelectOptionChangeNotifier;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterSelectOptionChangeNotifier<T> implements ISelectOptionChangeNotifier<T> {
    private final RecyclerViewAdapter<T> adapter;

    public RecyclerViewAdapterSelectOptionChangeNotifier(RecyclerViewAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getItemCount() {
        return adapter.getItemCount();
    }

    @Override
    public void resetAllItems(List<T> values) {
        adapter.resetAllItems(values);
    }

    @Override
    public void onOptionAppended(T data) {
        adapter.addItemFromEnd(data);
    }

    @Override
    public void onOptionRemoved(int position) {
        adapter.removeItem(position);
    }

    @Override
    public void onSelectedOptionsRemoved(List<Boolean> selectedFlags) {
        int size = selectedFlags.size();
        List<Integer> beforeRemoved = new ArrayList<>();
        for (int position = 0; position < size; position++) {
            beforeRemoved.add(position);
        }

        boolean selected = false;
        List<Integer> afterRemoved = new ArrayList<>(beforeRemoved);
        for (int position = size - 1; position >= 0; position--) {
            if (selectedFlags.get(position)) {
                afterRemoved.remove(position);
                adapter.removeItem(position);
                selected=true;
            }
        }

        if (selected){
            selectedFlags.remove(true);

            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new RemoveDiffCallBack(beforeRemoved, afterRemoved), false);
            result.dispatchUpdatesTo(adapter);
        }
    }

    @Override
    public void notifySelectChange(List<Boolean> oldSelectedFlags, List<Boolean> newSelectedFlags) {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new SelectDiffCallBack(oldSelectedFlags, newSelectedFlags), false);
        result.dispatchUpdatesTo(adapter);
    }
}
