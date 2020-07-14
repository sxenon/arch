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

import java.util.List;

/**
 * ISelectOptionStrategy
 * Created by Sui on 2017/8/29.
 */

public interface ISelectOptionStrategy<T> {
    void onOptionSelected(List<Boolean> selectedFlags, int position, IOptionChangeNotifier notifier);

    void onOptionUnSelected(List<Boolean> selectedFlags, int position, IOptionChangeNotifier notifier);

    void onAllOptionsReversed(List<Boolean> selectedFlags, IOptionChangeNotifier notifier);

    void onAllOptionsSelected(List<Boolean> selectedFlags, IOptionChangeNotifier notifier);

    void onAllOptionsUnSelected(List<Boolean> selectedFlags, IOptionChangeNotifier notifier);

    void onOptionRemoved(List<Boolean> selectedFlags, int position, IOptionChangeNotifier<T> notifier);

    void onSelectedOptionsRemoved(List<Boolean> selectedFlags, IOptionChangeNotifier<T> notifier);
}
