package com.uws.campus_app.core.tasks;

import java.util.List;

import com.google.android.gms.maps.model.PolylineOptions;

public interface PathTaskFinished {
    void onFinished(List<PolylineOptions> lines);
}
