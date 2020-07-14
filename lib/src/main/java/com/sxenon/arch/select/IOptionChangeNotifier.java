package com.sxenon.arch.select;

import java.util.List;

public interface IOptionChangeNotifier<T> {
    void onOptionRemoved(int position);
    void onSelectedOptionsRemoved(List<Boolean> selectedFlags);
    void notifySelectChange(List<Boolean> oldSelectedFlags, List<Boolean> newSelectedFlags);
}
