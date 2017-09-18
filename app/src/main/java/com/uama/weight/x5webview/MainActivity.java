package com.uama.weight.x5webview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.uama.weight.uama_webview.BridgeWebView;
import com.uama.weight.uama_webview.BridgeWebViewClient;

public class MainActivity extends AppCompatActivity implements BridgeWebViewClient.WebClientListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BridgeWebView webView = (BridgeWebView) findViewById(R.id.webview);
        BridgeWebViewClient webViewClient =   new BridgeWebViewClient(this, webView);
        webViewClient.registWebClientListener(this);
        webView.setWebViewClient(webViewClient);
        webView.loadUrl("http://go.tujia.com/4091?code=ymexc2asPgxZKwaPevSCKgkT7iscN14qhXELcGSLiuPfhdxxv1JvgcroSSRqlt9UmSdx6zzb%2BdyidTXErrDYkjGxcaP1fYyHlYUCU6XPvEisoycLeZnqlbkCYgtndqR9DNAzd9jcijHxK6m7%2FA5gmIUpxnMdpy2ftCco%2B4CpV09Pkuehq9687A%3D%3D");
    }

    @Override
    public void setLoadFail() {
        Toast.makeText(this,"我是失败",Toast.LENGTH_LONG).show();
    }

    @Override
    public void pageLoadFinished() {
        Toast.makeText(this,"我是结束",Toast.LENGTH_LONG).show();
    }
}
