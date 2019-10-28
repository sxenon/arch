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

package com.sxenon.arch.viewmodule.pull.list.strategy;

import com.sxenon.arch.adapter.IAdapter;
import com.sxenon.arch.viewmodule.pull.IPullViewModule;
import com.sxenon.arch.viewmodule.pull.list.strategy.adapter.IAdapterStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * RefreshAndMoreListStrategy
 * Created by Sui on 2017/8/6.
 */

public class RefreshAndMoreListStrategy<R> extends BaseListStrategy<R> {

    private final List<EventListener<R>> mEventListeners = new ArrayList<>();

    public RefreshAndMoreListStrategy() {
        super();
    }

    public RefreshAndMoreListStrategy(IAdapterStrategy<R> adapterStrategy) {
        super(adapterStrategy);
    }

    private void onFullMoreData(IAdapter<R> adapter, List<R> data) {
        getAdapterStrategy().onMoreData(adapter, data);
        for (EventListener<R> eventListener:mEventListeners){
            eventListener.onFullMoreData(data);
        }
    }

    private void onPartialMoreData(IAdapter<R> adapter, List<R> data){
        getAdapterStrategy().onMoreData(adapter, data);
        for (EventListener<R> eventListener:mEventListeners){
            eventListener.onPartialMoreData(data);
        }
    }

    private void onInitData(IAdapter<R> adapter, List<R> data) {
        getAdapterStrategy().onInitData(adapter, data);
        for (EventListener<R> eventListener:mEventListeners){
            eventListener.onInitData(data);
        }
    }

    private void onNoMoreData() {
        for (EventListener<R> eventListener:mEventListeners){
            eventListener.onNoMoreData();
        }
    }

    private void onCanMoreData() {
        for (EventListener<R> eventListener:mEventListeners){
            eventListener.onCanMoreData();
        }
    }

    private void onInitialize() {
        for (EventListener<R> eventListener:mEventListeners){
            eventListener.onInitialize();
        }
    }

    private void onNoData(){
        for (EventListener<R> eventListener:mEventListeners){
            eventListener.onNoData();
        }
    }

    @Override
    public void onPullDown(PageInfo pageInfo) {
        if (pageInfo.currentPage == -1){
            onInitialize();
        }
        pageInfo.tempPage = 0;
    }

    @Override
    public void onPullUp(PageInfo pageInfo) {
        pageInfo.tempPage = pageInfo.currentPage + 1;
    }

    @Override
    public void onPartialList(IPullViewModule pullViewModule, List<R> data, IAdapter<R> adapter, PageInfo pageInfo, int action) {
        pageInfo.currentPage = pageInfo.tempPage;
        if (pageInfo.tempPage == 0) {
            onInitData(adapter, data);
        } else {
            onPartialMoreData(adapter,data);
        }
        onNoMoreData();
    }

    @Override
    public void onFullList(IPullViewModule pullViewModule, List<R> data, IAdapter<R> adapter, PageInfo pageInfo, int action) {
        pageInfo.currentPage = pageInfo.tempPage;
        if (pageInfo.tempPage == 0) {
            onInitData(adapter, data);
        }else {
            onFullMoreData(adapter, data);
        }
        onCanMoreData();
    }

    @Override
    public void onEmptyList(IPullViewModule pullViewModule, PageInfo pageInfo, IAdapter<R> adapter, int action) {
        pageInfo.tempPage = pageInfo.currentPage;
        if (action == PULL_ACTION_DOWN) {
            pullViewModule.onEmpty();
            onNoData();
        }else {
            onNoMoreData();
        }
    }

    @Override
    public void onError(IPullViewModule pullViewModule, Throwable throwable, IAdapter<R> adapter, PageInfo pageInfo) {
        adapter.clearAllItems();
        pageInfo.currentPage = pageInfo.tempPage = -1;
    }

    public void addEventListener(EventListener<R> eventListener) {
        mEventListeners.add(eventListener);
    }

    public interface EventListener<R>{
        void onFullMoreData(List<R> data);
        void onPartialMoreData(List<R> data);
        void onInitData(List<R> data);
        void onNoMoreData();
        void onCanMoreData();
        void onInitialize();
        void onNoData();
    }

    public static class SimpleEventListener<R> implements EventListener<R> {

        @Override
        public void onFullMoreData(List<R> data) {

        }

        @Override
        public void onPartialMoreData(List<R> data) {

        }

        @Override
        public void onInitData(List<R> data) {

        }

        @Override
        public void onNoMoreData() {

        }

        @Override
        public void onCanMoreData() {

        }

        @Override
        public void onNoData() {

        }

        @Override
        public void onInitialize() {

        }
    }
}
