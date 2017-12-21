package com.uama.weight.uama_webview;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by bruce on 10/28/15.
 */
public class BridgeWebViewClient extends WebViewClient {
    private BridgeWebView webView;
    private Context context;
    public WebClientListener listener;

    public BridgeWebViewClient(Context context, BridgeWebView webView) {
        this.webView = webView;
        this.context = context;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        try {
            url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (url.contains("tel:")) {
            showDialogs(url);
            return true;
        } else if (url != null && url.contains("image://?")) {
            // 点击图片查看大图
                String temp = url.replace("image://?", "");
                String[] params = temp.split("&");

                // 图片集
                String[] imgUrl = null;
                int index = 0;
                for (String param : params) {
                    if (param.contains("url=")) {
                        param = param.replace("url=", "");
                        imgUrl = param.split(",");
                    } else if (param.contains("currentIndex=")) {
                        param = param.replace("currentIndex=", "");
                        try {
                            index = Integer.parseInt(param);
                        } catch (NumberFormatException e) {
                            index = 0;
                        }
                    }
                }

                if (imgUrl != null && imgUrl.length > 0) {
                    List<String> list = Arrays.asList(imgUrl);
                    listener.imageClick(list,index);
                }
                return true;
            }else {
            if (url.startsWith(BridgeUtil.YY_RETURN_DATA)) { // 如果是返回数据
                webView.handlerReturnData(url);
                return true;
            } else if (url.startsWith(BridgeUtil.YY_OVERRIDE_SCHEMA)) { //
                webView.flushMessageQueue();
                return true;
            } else if (url.startsWith("http:") || url.startsWith("https:")) {
                webView.loadUrl(url);
                return false;
            } else {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    context.startActivity(intent);
                } catch (Exception e) {
                    // 防止没有安装应用
                    e.printStackTrace();
                }
                return true;
            }
        }
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);

        if (BridgeWebView.toLoadJs != null) {
            BridgeUtil.webViewLoadLocalJs(view, BridgeWebView.toLoadJs);
        }

        //
        if (webView.getStartupMessage() != null) {
            for (Message m : webView.getStartupMessage()) {
                webView.dispatchMessage(m);
            }
            webView.setStartupMessage(null);
        }
        if (listener != null) {
            listener.pageLoadFinished();
        }
    }

    @Override
    public void onReceivedError(WebView webView, com.tencent.smtt.export.external.interfaces.WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
        super.onReceivedError(webView, webResourceRequest, webResourceError);
        if (listener != null) {
            listener.setLoadFail();
        }
    }

    /**
     * 点击webview电话连接打电话
     *
     * @param url 超链接
     */
    private void showDialogs(final String url) {
        String telNum = url.substring(url.indexOf(":", 1), url.length());
        new AlertDialog.Builder(context)
                .setTitle("提示")
                .setMessage("确认拨打 " + telNum)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            Uri uri = Uri.parse(url);
                            Intent it = new Intent(Intent.ACTION_CALL, uri);
                            context.startActivity(it);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }

    public interface WebClientListener {
        void setLoadFail();

        void pageLoadFinished();

        void imageClick(List<String> imgs, int position);
    }

    public void registWebClientListener(WebClientListener listener) {
        removeWebClientListener();
        this.listener = listener;
    }

    public void removeWebClientListener() {
        if (listener != null) {
            listener = null;
        }
    }
}