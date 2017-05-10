package com.iqoid.moovrika;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by iulian.lungu on 4/24/2017.
 */

public class MyWebChromeClient extends WebChromeClient {
    private MainActivity mainActivity;
    private View customView;
    private CustomViewCallback callback;
    private boolean isFullScreen = false;

    public MyWebChromeClient(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result){
        Toast.makeText(mainActivity, message, Toast.LENGTH_LONG).show();
        result.confirm();
        return true;
    }

    @Override
    public void onShowCustomView(View view, CustomViewCallback callback) {
        RelativeLayout fullScreen = this.mainActivity.getFullScreenLayout();
        if (fullScreen == null) return;

        this.customView = view;
        this.callback = callback;
        this.isFullScreen = true;

        fullScreen.setVisibility(View.VISIBLE);
        fullScreen.addView(view, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        this.mainActivity.toggleFullscreen(this.isFullScreen);
    }

    @Override
    public void onHideCustomView() {
        this.customView = null;
        this.isFullScreen = false;

        RelativeLayout fullScreen = this.mainActivity.getFullScreenLayout();
        if (fullScreen != null) {
            fullScreen.setVisibility(View.INVISIBLE);
            fullScreen.removeAllViews();
        }

        if (this.callback != null) {
            callback.onCustomViewHidden();
        }

        this.mainActivity.toggleFullscreen(this.isFullScreen);
    }

    public boolean exitFullScreen() {
        if (this.isFullScreen) {
            onHideCustomView();
            return true;
        } else {
            return false;
        }
    }

}
