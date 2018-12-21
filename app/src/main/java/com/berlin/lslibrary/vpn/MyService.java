package com.berlin.lslibrary.vpn;

import android.annotation.TargetApi;
import android.os.Build;
import android.service.quicksettings.TileService;

@TargetApi(Build.VERSION_CODES.N)
public class MyService extends TileService {
    @Override
    public void onClick() {
        super.onClick();
    }
}
