package com.sxenon.arch.select;

import java.util.List;

public interface ISelectOptionChangeNotifier<T> extends IOptionChangeNotifier<T>,ISelectChangeNotifier{
    int getItemCount();
    void resetAllItems(List<T> values);
}
