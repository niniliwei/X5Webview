##X5Webview的使用<br />
名称：BridgeWebView&nbsp;<br />
介绍：继承自腾讯X5Webview。<br />
使用：<br />
直接在xml定义 并且在界面中初始化<br />
如果需要设置Webchromechlient如下：<br />
&nbsp;BridgeWebChromeClient webChromeClient = new BridgeWebChromeClient(this);<br />
&nbsp;webView.setWebChromeClient(webChromeClient);<br />
&nbsp;需要实现FileChooserCallback接口 以及图片上传相关接口方法（如不用可以不写具体实现逻辑）<br />
&nbsp;设置webviewchient 如下：<br />
&nbsp;BridgeWebViewClient webviewClient = new BridgeWebViewClient(mContext,webView);<br />
&nbsp;webviewClient.registWebClientListener(this);<br />
&nbsp; webView.setWebViewClient(webviewClient);<br />
&nbsp; 需要实现BridgeWebViewClient.WebClientListener 接口以及回调方法。<br />
&nbsp; &nbsp; @Override<br />
&nbsp; &nbsp; public void setLoadFail() {<br />
&nbsp; &nbsp; &nbsp; &nbsp; //加载失败<br />
&nbsp; &nbsp; }<br />
<br />
<br />
&nbsp; &nbsp; @Override<br />
&nbsp; &nbsp; public void pageLoadFinished() {<br />
&nbsp; &nbsp; &nbsp; //加载完成<br />
&nbsp; &nbsp; }
