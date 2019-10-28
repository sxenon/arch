package com.sxenon.arch.select;

import java.util.List;

interface IOptionChangeNotifier<T> {
    void onOptionAppended(T data);
    void onOptionRemoved(int position);
    void onSelectedOptionsRemoved(List<Boolean> selectedFlags);
}
