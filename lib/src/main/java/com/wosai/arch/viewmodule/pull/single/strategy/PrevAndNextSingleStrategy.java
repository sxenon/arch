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

package com.wosai.arch.viewmodule.pull.single.strategy;

import com.wosai.arch.viewmodule.pull.IPullStrategy;
import com.wosai.arch.viewmodule.pull.IPullViewModule;

/**
 * PrevAndNext implement for IPullStrategy
 * Created by Sui on 2017/8/6.
 */

public class PrevAndNextSingleStrategy<R> extends BaseSingleStrategy<R> {
    private final int mInitPage;
    private EventListener mEventListener;

    public PrevAndNextSingleStrategy(int initPage) {
        super();
        mInitPage = initPage;
    }

    private void onInitialize() {
        if ( mEventListener !=null){
            mEventListener.onInitialize();
        }
    }

    private void onNoPrevData(){
        if ( mEventListener !=null){
            mEventListener.onNoPrevData();
        }
    }

    private void onNoNextData() {
        if ( mEventListener !=null){
            mEventListener.onNoNextData();
        }
    }

    private void onNextDataFetched(R data){
        if ( mEventListener !=null){
            //noinspection unchecked
            mEventListener.onNextData(data);
        }
    }

    private void onPrevDataFetched(R data){
        if ( mEventListener !=null){
            //noinspection unchecked
            mEventListener.onPrevData(data);
        }
    }

    @Override
    public void onSingle(IPullViewModule pullViewHolder, R data, PageInfo pageInfo) {
        pageInfo.currentPage = pageInfo.tempPage;
        if ( IPullStrategy.PULL_ACTION_UP==pullViewHolder.getPullAction()){
            onNextDataFetched(data);
        }else {
            onPrevDataFetched(data);
        }
    }

    @Override
    public void onEmpty(IPullViewModule pullViewModule, PageInfo pageInfo) {
        pageInfo.tempPage = pageInfo.currentPage;
        if (pageInfo.currentPage == -1) {
            pullViewModule.onEmpty();
        } else {
            onNoNextData();
        }
    }

    @Override
    public void onPullDown(PageInfo pageInfo) {
        switch (pageInfo.currentPage) {
            case -1:
                onInitialize();
                pageInfo.tempPage = mInitPage;
                break;
            case 0:
                onNoPrevData();
                break;
            default:
                pageInfo.tempPage = pageInfo.currentPage - 1;
                break;
        }
    }

    @Override
    public void onPullUp(PageInfo pageInfo) {
        pageInfo.tempPage = pageInfo.currentPage + 1;
    }

    public void setFillEventListener(EventListener eventListener) {
        this.mEventListener = eventListener;
    }

    public interface EventListener<R>{
        void onNextData(R data);
        void onPrevData(R data);
        void onNoPrevData();
        void onNoNextData();
        void onInitialize();
    }

    public static class SimpleEventListener<R> implements EventListener<R> {

        @Override
        public void onNextData(R data) {

        }

        @Override
        public void onPrevData(R data) {

        }

        @Override
        public void onNoPrevData() {

        }

        @Override
        public void onNoNextData() {

        }

        @Override
        public void onInitialize() {

        }
    }
}
