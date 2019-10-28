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

package com.sxenon.arch.viewmodule.pull;

/**
 * 分页数据填充策略
 * Created by Sui on 2017/8/6.
 */

public interface IPullStrategy {
    int PULL_ACTION_DOWN = 1;
    int PULL_ACTION_UP = 2;

    void onPullDown(PageInfo pageInfo);

    void onPullUp(PageInfo pageInfo);

    class PageInfo {
        public int currentPage;
        public int tempPage;

        public PageInfo(int currentPage, int tempPage) {
            this.currentPage = currentPage;
            this.tempPage = tempPage;
        }

        public boolean isIdle(){
            return tempPage == currentPage;
        }

        public void rollBackToIdle(){
            tempPage = currentPage;
        }
    }
}
