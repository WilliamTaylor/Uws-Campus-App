/**
 *
 * Copyright 2015 : William Taylor : wi11berto@yahoo.co.uk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.uws.campus_app.core.modals;

import android.app.Activity;
import android.app.AlertDialog;

public abstract class BaseInputBox {
    private AlertDialog.Builder builder;
    private OnInputEntered handler;
    private Activity activity;

    public BaseInputBox(Activity activity) {
        this.builder = new AlertDialog.Builder(activity);
        this.activity = activity;
    }

    public BaseInputBox onInputEntered(OnInputEntered handler) {
        if(handler != null) {
           this.handler = handler;
        } else {
            throw new IllegalArgumentException("Handler cannot be null");
        }

        return this;
    }

    public void show() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                show(builder, activity, handler);
            }
        });
    }

    public abstract void show(final AlertDialog.Builder builder, final Activity activity, final OnInputEntered handler);
}
