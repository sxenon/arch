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

package com.wosai.arch.viewmodule.pull.list;

import com.wosai.arch.adapter.IWosaiAdapter;
import com.wosai.arch.response.IListResponseHandler;
import com.wosai.arch.viewmodule.pull.IPullViewModule;

/**
 * FillerViewHolder for list data
 * Created by Sui on 2017/8/19.
 */

public interface IListViewModule<R> extends IListResponseHandler<R>, IPullViewModule {
    void setAdapter(IWosaiAdapter<R> adapter);
}
