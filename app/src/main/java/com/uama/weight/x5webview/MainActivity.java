package com.uama.weight.x5webview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.uama.weight.uama_webview.BridgeHandler;
import com.uama.weight.uama_webview.BridgeWebView;
import com.uama.weight.uama_webview.BridgeWebViewClient;
import com.uama.weight.uama_webview.CallBackFunction;

import java.util.List;

public class MainActivity extends AppCompatActivity implements BridgeWebViewClient.WebClientListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BridgeWebView webView = (BridgeWebView) findViewById(R.id.webview);
        BridgeWebViewClient webViewClient =   new BridgeWebViewClient(this, webView);
        webViewClient.registWebClientListener(this);
        webView.setWebViewClient(webViewClient);
        webView.loadUrl("https://www.bejson.com/");
        // 注册桥方法
        webView.registerHandler("_app_home_h5_card", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                    Log.i("heerzhen", data);
            }
        });
    }

    @Override
    public void setLoadFail() {
//        Toast.makeText(this,"我是失败",Toast.LENGTH_LONG).show();
    }

    @Override
    public void pageLoadFinished() {
//        Toast.makeText(this,"我是结束",Toast.LENGTH_LONG).show();
    }

    @Override
    public void webviewImageClick(List<String> imgs, int position) {
        //图片点击
        Toast.makeText(this,imgs.get(position),Toast.LENGTH_LONG).show();
    }
}
