package com.sxenon.arch.select.notifier;

import com.sxenon.arch.adapter.IAdapter;
import com.sxenon.arch.select.IOptionChangeNotifier;

import java.util.Iterator;
import java.util.List;

public class AdapterSelectOptionChangeNotifier<T> implements IOptionChangeNotifier<T> {
    private final IAdapter<T> adapter;

    public AdapterSelectOptionChangeNotifier(IAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public void notifySelectChange(List<Boolean> oldSelectedFlags, List<Boolean> newSelectedFlags) {
        adapter.notifyDataSetChanged();
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
            Iterator<Boolean> iterator = selectedFlags.iterator();
            while (iterator.hasNext()) {
                if (iterator.next()) {
                    iterator.remove();
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    public IAdapter<T> getAdapter() {
        return adapter;
    }

}
