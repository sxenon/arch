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

package com.sxenon.arch.adapter;

import java.util.List;

/**
 * Interface for adapter
 * Created by Sui on 2016/11/20.
 */

public interface IAdapter<T> {
    int getItemCount();

    void addItemFromEnd(T value);

    void addItemFromStart(T value);

    void addItemsFromStart(List<T> values);

    void addItemsFromEnd(List<T> values);

    void addItem(int position, T value);

    void addItems(int position, List<T> values);

    void removeItems(List<T> values);

    void removeItem(int position);

    void removeItem(T value);

    void removeItems(int position, int count);

    List<T> getValues();

    T getValue(int position);

    void resetAllItems(List<T> values);

    void clearAllItems();

    void setItem(int position, T value);

    void invalidate(T oldValue, T newValue);

    void moveItem(int fromPosition, int toPosition);

    void notifyDataSetChanged();

    void resetAllData(List<T> values);

}