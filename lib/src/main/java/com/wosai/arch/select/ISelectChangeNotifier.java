package com.wosai.arch.select;

import java.util.List;

interface ISelectChangeNotifier {
    void notifySelectChange(List<Boolean> oldSelectedFlags, List<Boolean> newSelectedFlags);
}
