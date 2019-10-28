/*
 * Copyright (c) 2017 sxenon
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

package com.wosai.arch.adapter.rv;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wosai.arch.adapter.IAdapterViewHolder;
import com.wosai.arch.viewmodule.IViewModule;

/**
 * ViewHolder for RecyclerView
 * Created by Sui on 2016/12/29.
 */

public abstract class WosaiRecyclerViewViewHolder<T> extends RecyclerView.ViewHolder implements IAdapterViewHolder<T> {
    private final WosaiRecyclerViewAdapter adapter;
    private final IViewModule adapterContainer;
    private int height, width;

    public WosaiRecyclerViewViewHolder(View itemView, WosaiRecyclerViewAdapter adapter) {
        super(itemView);
        this.adapter = adapter;
        this.adapterContainer = adapter.getContainer();

        height = itemView.getLayoutParams().height;
        width = itemView.getLayoutParams().width;

    }

    @SuppressWarnings("unchecked")
    public WosaiRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    @Override
    public Context getContext() {
        return adapterContainer.getContext();
    }

    public void setVisibility(boolean isVisible) {
        RecyclerView.LayoutParams param = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        if (isVisible) {
            param.height = this.height;
            param.width = this.width;
            itemView.setVisibility(View.VISIBLE);
        } else {
            itemView.setVisibility(View.GONE);
            param.height = 0;
            param.width = 0;
        }
        itemView.setLayoutParams(param);
    }
}
