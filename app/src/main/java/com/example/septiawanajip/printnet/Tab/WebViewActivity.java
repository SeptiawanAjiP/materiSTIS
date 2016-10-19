package com.example.septiawanajip.printnet.Tab;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.septiawanajip.printnet.R;

/**
 * Created by Septiawan Aji P on 10/15/2016.
 */
public class WebViewActivity extends Activity {
    private WebView pdfWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web);
        pdfWebView = (WebView)findViewById(R.id.materi);
        pdfWebView.getSettings().setJavaScriptEnabled(true);
//        String pdf = "http://www.adobe.com/devnet/acrobat/pdfs/pdf_open_parameters.pdf";
        String pdf = getIntent().getStringExtra("filePath");
        pdfWebView.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);

    }
}
