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
import android.widget.ArrayAdapter;

public abstract class BaseListBox {
    private Activity activity;

    public BaseListBox(Activity activity) {
        this.activity = activity;
    }

    public void show() {
        show(new ArrayAdapter<String>(activity, android.R.layout.select_dialog_singlechoice), new AlertDialog.Builder(activity));
    }

    public abstract void show(ArrayAdapter<String> arrayAdapter, AlertDialog.Builder builder);
}
