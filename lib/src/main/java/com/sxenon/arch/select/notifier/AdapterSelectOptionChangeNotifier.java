package com.sxenon.arch.select.notifier;

import com.sxenon.arch.adapter.IAdapter;
import com.sxenon.arch.select.ISelectOptionChangeNotifier;

import java.util.List;

public class AdapterSelectOptionChangeNotifier<T> implements ISelectOptionChangeNotifier<T> {
    private final IAdapter<T> adapter;

    public AdapterSelectOptionChangeNotifier(IAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public void notifySelectChange(List<Boolean> oldSelectedFlags, List<Boolean> newSelectedFlags) {
        adapter.notifyDataSetChanged();
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
        final int size = selectedFlags.size();
        boolean selected = false;

        for (int position = size - 1; position >= 0; position--) {
            if (selectedFlags.get(position)) {
                adapter.removeItem(position);
                selected = true;
            }
        }
        if (selected) {
            selectedFlags.remove(true);
            adapter.notifyDataSetChanged();
        }
    }

    public IAdapter<T> getAdapter() {
        return adapter;
    }

    @Override
    public int getItemCount() {
        return adapter.getItemCount();
    }

    @Override
    public void resetAllItems(List<T> values) {
        adapter.resetAllItems(values);
    }
}
