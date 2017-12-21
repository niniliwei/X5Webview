package com.uama.weight.x5webview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.widget.Toast;


/**
 * webview图片点击时间监听
 *
 * @author HEZ
 */
public class JavascriptInterface {
    private Context context;

    public JavascriptInterface(Context context) {
        this.context = context;
    }

    @android.webkit.JavascriptInterface
    public void openImage(String[] imgUrls, int position) {

        Toast.makeText(context,imgUrls[position],Toast.LENGTH_LONG).show();
    }
}
