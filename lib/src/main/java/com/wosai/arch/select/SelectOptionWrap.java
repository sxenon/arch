package com.wosai.arch.select;

import java.util.ArrayList;
import java.util.List;

public class SelectOptionWrap<T>{
    private List<Boolean> selectedFlags;
    private final ISelectOptionStrategy<T> selectStrategy;
    private final ISelectOptionChangeNotifier<T> notifier;

    public SelectOptionWrap(ISelectOptionStrategy<T> selectStrategy, ISelectOptionChangeNotifier<T> adapter) {
        this.selectStrategy = selectStrategy;
        this.notifier = adapter;
        selectedFlags = new ArrayList<>(adapter.getItemCount());
    }

    public void resetAllItems(List<T> values) {
        notifier.resetAllItems(values);
        selectedFlags = new ArrayList<>(notifier.getItemCount());
    }

    public void appendOption(T data) {
        selectStrategy.onOptionAppended(selectedFlags, data, notifier);
    }

    public void removeOption(int position) {
        selectStrategy.onOptionRemoved(selectedFlags, position, notifier);
    }

    public void selectOption(int position) {
        selectStrategy.onOptionSelected(selectedFlags, position, notifier);
    }

    public void unSelectOption(int position) {
        selectStrategy.onOptionUnSelected(selectedFlags, position, notifier);
    }

    public void selectAllOptions() {
        selectStrategy.onAllOptionsSelected(selectedFlags, notifier);
    }

    public void unSelectAllOptions() {
        selectStrategy.onAllOptionsUnSelected(selectedFlags, notifier);
    }

    public void reverseAllOptions() {
        selectStrategy.onAllOptionsReversed(selectedFlags, notifier);
    }

    public void removeSelectedOptions() {
        selectStrategy.onSelectedOptionsRemoved(selectedFlags, notifier);
    }

    public List<Boolean> getSelectedFlags() {
        return selectedFlags;
    }
}
