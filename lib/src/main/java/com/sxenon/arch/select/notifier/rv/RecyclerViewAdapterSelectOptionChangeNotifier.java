package com.sxenon.arch.select.notifier.rv;

import android.support.v7.util.DiffUtil;

import com.sxenon.arch.adapter.rv.RecyclerViewAdapter;
import com.sxenon.arch.select.IOptionChangeNotifier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RecyclerViewAdapterSelectOptionChangeNotifier<T> implements IOptionChangeNotifier<T> {
    private final RecyclerViewAdapter<T> adapter;

    public RecyclerViewAdapterSelectOptionChangeNotifier(RecyclerViewAdapter<T> adapter) {
        this.adapter = adapter;
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
                selected=true;
            }
        }

        if (selected){
            Iterator<Boolean> iterator = selectedFlags.iterator();
            while (iterator.hasNext()){
                if (iterator.next()){
                    iterator.remove();
                }
            }
            //TODO
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
