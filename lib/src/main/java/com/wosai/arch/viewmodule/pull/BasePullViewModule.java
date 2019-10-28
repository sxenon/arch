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

package com.wosai.arch.viewmodule.pull;

import android.content.Context;
import android.view.View;

import com.wosai.arch.Event;
import com.wosai.arch.util.CommonUtils;

/**
 * Base implement for IPullViewModule
 * Created by Sui on 2017/8/4.
 */

public abstract class BasePullViewModule<PL extends IPullLayout,S extends IPullStrategy> implements IPullViewModule {
    private final IPullStrategy.PageInfo pageInfo = new IPullStrategy.PageInfo(-1, -1);
    private int mPullAction = IPullStrategy.PULL_ACTION_DOWN;

    private final S mPullStrategy;
    private final PL mPullLayout;
    private final Context mContext;

    private int mEventWhat = PullEventWhat.WHAT_UNINITIALIZED;
    private Throwable mError;

    private View mEmptyView;
    private View mExceptionView;

    private EventListener mEventListener;

    /**
     * Constructor
     *
     * @param context          上下文
     * @param pullLayout       刷新容器
     * @param pullStrategy 分页数据填充策略
     */
    public BasePullViewModule(Context context, PL pullLayout, S pullStrategy) {
        mPullLayout = pullLayout;
        mPullStrategy = pullStrategy;
        mContext = context;
    }

    public BasePullViewModule setExtraComponents(View emptyView, View exceptionView) {
        mEmptyView = emptyView;
        mExceptionView = exceptionView;

        CommonUtils.setViewVisibility(mEmptyView, View.GONE);
        CommonUtils.setViewVisibility(mExceptionView, View.GONE);
        return this;
    }

    public BasePullViewModule resetExtraComponents(View emptyView, View exceptionView) {
        mEmptyView = emptyView;
        mExceptionView = exceptionView;
        return this;
    }

    public void endAllAnim() {
       endPullingDownAnim();
       endPullingUpAnim();
    }

    public void endPullingUpAnim(){
        mPullLayout.endPullingUp();
    }

    public void endPullingDownAnim(){
        mPullLayout.endPullingDown();
    }

    /**
     * For subclass call,see demo
     */
    public final void onBeginPullingDown() {
        mPullStrategy.onPullDown(pageInfo);
        mPullAction = IPullStrategy.PULL_ACTION_DOWN;
    }

    /**
     * For subclass call,see demo
     */
    protected final void onBeginPullingUp() {
        mPullStrategy.onPullUp(pageInfo);
        mPullAction = IPullStrategy.PULL_ACTION_UP;
    }

    public void toInitialize() {
        beginPullingDown();
    }

    public void beginPullingDown() {
        mPullLayout.beginPullingDown();
    }

    public void beginPullingUp() {
        mPullLayout.beginPullingUp();
    }

    //Event start
    public Event getCurrentEvent() {
        Event event = new Event();
        event.what = mEventWhat;
        event.arg1 = pageInfo.currentPage;

        if (event.what == PullEventWhat.WHAT_EXCEPTION) {
            event.obj = mError;
        } else {
            event.obj = getData();
        }
        return event;
    }

    public void restoreEvent(Event savedEvent) {
        if (savedEvent == null) {
            toInitialize();
            return;
        }
        pageInfo.currentPage = pageInfo.tempPage = savedEvent.arg1;
        mEventWhat = savedEvent.what;
        switch (savedEvent.what) {
            case PullEventWhat.WHAT_EMPTY:
                onEmpty();
                break;
            case PullEventWhat.WHAT_EXCEPTION:
                onError((Throwable) savedEvent.obj);
                break;
            case PullEventWhat.WHAT_UNINITIALIZED:
                toInitialize();
                break;
            case PullEventWhat.WHAT_NON_EMPTY:
                restoreData(savedEvent.obj);
                break;
        }
    }
    //Event end

    //Implement start
    @Override
    public void onNonEmpty() {
        getPullLayout().setVisibility(View.VISIBLE);
        mEventWhat = PullEventWhat.WHAT_NON_EMPTY;
        CommonUtils.setViewVisibility(mEmptyView, View.GONE);
        CommonUtils.setViewVisibility(mExceptionView, View.GONE);
    }

    @Override
    public void onCancel() {
        endAllAnim();
        pageInfo.tempPage = pageInfo.currentPage;
        if ( mEventListener !=null){
            mEventListener.onCancel();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        endAllAnim();
        mEventWhat = PullEventWhat.WHAT_EXCEPTION;
        mError = throwable;
        CommonUtils.setViewVisibility(mEmptyView, View.GONE);
        CommonUtils.setViewVisibility(mExceptionView, View.VISIBLE);
        if ( mEventListener !=null){
            mEventListener.onError(throwable);
        }
    }

    @Override
    public void onEmpty() {
        mEventWhat = PullEventWhat.WHAT_EMPTY;
        pageInfo.currentPage = pageInfo.tempPage = -1;
        CommonUtils.setViewVisibility(mExceptionView, View.GONE);
        CommonUtils.setViewVisibility(mEmptyView, View.VISIBLE);
        if ( mEventListener !=null){
            mEventListener.onEmpty();
        }
    }
    //Implement end


    public void setEventListener(EventListener eventListener) {
        this.mEventListener = eventListener;
    }

    public interface EventListener {
        void onEmpty();
        void onError(Throwable throwable);
        void onCancel();
    }

    //Getter start
    public View getExceptionView() {
        return mExceptionView;
    }

    public View getEmptyView() {
        return mEmptyView;
    }

    public int getPullEventWhat() {
        return mEventWhat;
    }

    public PL getPullLayout() {
        return mPullLayout;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    public Throwable getError() {
        return mError;
    }

    public S getPullStrategy() {
        return mPullStrategy;
    }

    public abstract Object getData();
    //Getter end

    //Setter start
    public abstract void restoreData(Object data);

    public void setFillerEventWhat(int eventWhat) {
        mEventWhat = eventWhat;
    }

    public void setError(Throwable error) {
        mError = error;
    }

    public int getCurrentPageCount() {
        return pageInfo.currentPage;
    }

    public IPullStrategy.PageInfo getPageInfo() {
        return pageInfo;
    }

    /**
     * @return 上个动作是，上拉还是下拉的
     */
    public int getPullAction(){
        return mPullAction;
    }

    //Setter end
}
