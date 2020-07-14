/*
 * Copyright (c) 2017  sxenon
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sxenon.arch.select;

import com.sxenon.arch.select.strategy.ISelectStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * SelectOptionStrategyImpl
 * Created by Sui on 2017/8/29.
 */

public class SelectOptionStrategyImpl<T> implements ISelectOptionStrategy<T> {
    private final ISelectStrategy innerSelectStrategy;

    public SelectOptionStrategyImpl(ISelectStrategy innerSelectStrategy) {
        this.innerSelectStrategy = innerSelectStrategy;
    }

    @Override
    public void onOptionSelected(List<Boolean> selectedFlags, int position, IOptionChangeNotifier notifier) {
        if (selectedFlags.get(position)) {
            return;
        }
        List<Boolean> oldSelectedFlags = new ArrayList<>(selectedFlags);
        innerSelectStrategy.onOptionSelected(selectedFlags, position);
        notifier.notifySelectChange(oldSelectedFlags,selectedFlags);
    }

    @Override
    public void onOptionUnSelected(List<Boolean> selectedFlags, int position, IOptionChangeNotifier notifier) {
        if (!selectedFlags.get(position)) {
            return;
        }
        List<Boolean> oldSelectedFlags = new ArrayList<>(selectedFlags);
        innerSelectStrategy.onOptionUnSelected(selectedFlags, position);
        notifier.notifySelectChange(oldSelectedFlags,selectedFlags);
    }

    @Override
    public void onAllOptionsReversed(List<Boolean> selectedFlags, IOptionChangeNotifier notifier) {
        List<Boolean> oldSelectedFlags = new ArrayList<>(selectedFlags);
        innerSelectStrategy.onAllOptionsReversed(selectedFlags);
        notifier.notifySelectChange(oldSelectedFlags,selectedFlags);
    }

    @Override
    public void onAllOptionsSelected(List<Boolean> selectedFlags, IOptionChangeNotifier notifier) {
        List<Boolean> oldSelectedFlags = new ArrayList<>(selectedFlags);
        innerSelectStrategy.onAllOptionsSelected(selectedFlags);
        notifier.notifySelectChange(oldSelectedFlags,selectedFlags);
    }

    @Override
    public void onAllOptionsUnSelected(List<Boolean> selectedFlags, IOptionChangeNotifier notifier) {
        List<Boolean> oldSelectedFlags = new ArrayList<>(selectedFlags);
        innerSelectStrategy.onAllOptionsUnSelected(selectedFlags);
        notifier.notifySelectChange(oldSelectedFlags,selectedFlags);
    }

    @Override
    public void onOptionRemoved(List<Boolean> selectedFlags, int position, IOptionChangeNotifier<T> notifier) {
        selectedFlags.remove(position);
        notifier.onOptionRemoved(position);
    }

    @Override
    public void onSelectedOptionsRemoved(final List<Boolean> selectedFlags, IOptionChangeNotifier<T> notifier) {
        notifier.onSelectedOptionsRemoved(selectedFlags);
    }

    public ISelectStrategy getInnerSelectStrategy() {
        return innerSelectStrategy;
    }
}
