package com.sxenon.arch.select;

import java.util.ArrayList;
import java.util.List;

public class SelectOptionWrap<T>{
    private List<Boolean> selectedFlags;
    private final ISelectOptionStrategy<T> selectStrategy;
    private final IOptionChangeNotifier<T> notifier;

    public SelectOptionWrap(ISelectOptionStrategy<T> selectStrategy, IOptionChangeNotifier<T> notifier,List<Boolean> selectedFlags) {
        this.selectStrategy = selectStrategy;
        this.notifier = notifier;
        this.selectedFlags = selectedFlags;
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
