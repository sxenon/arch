/*
 * Copyright (c) 2018  sxenon
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

package com.wosai.arch.viewmodule.pull.list.strategy.adapter;

import com.wosai.arch.adapter.IWosaiAdapter;

import java.util.List;

/**
 * Default Strategy for ListDataFill
 * Created by Sui on 2017/1/12.
 */

public class DefaultAdapterStrategy<R> implements IAdapterStrategy<R> {
    @Override
    public void onMoreData(IWosaiAdapter<R> adapter, List<R> data) {
        adapter.addItemsFromEnd(data);
    }

    @Override
    public void onNewData(IWosaiAdapter<R> adapter, List<R> data) {
        adapter.addItemsFromStart(data);
    }

    @Override
    public void onInitData(IWosaiAdapter<R> adapter, List<R> data) {
        adapter.resetAllItems(data);
    }
}
