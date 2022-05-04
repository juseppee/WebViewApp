package com.example.webviewapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refreshLayout = findViewById(R.id.swiperefresh);
        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.setWebViewClient(new WebViewClient());
        Button btnFetch = findViewById(R.id.downloadBtn);

        btnFetch.setOnClickListener(v -> {
            if (btnFetch.getText().equals("Загрузка"))

                btnFetch.setText("На главную");
            new PageLoader(findViewById(R.id.downloadBtn), findViewById(R.id.linearLayout), webView).start();
        });
        refreshLayout.setOnRefreshListener(
                () -> {
                    refreshLayout.setRefreshing(false);
                    if (btnFetch.getText().equals("Загрузка"))
                        Toast.makeText(MainActivity.this.getApplicationContext(),
                                "Обновлять нечего", Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(MainActivity.this.getApplicationContext(),
                                "Обновление...", Toast.LENGTH_SHORT).show();
                        webView.reload();
                    }
                }
        );
    }

    public void back(View v) {
        webView.goBack();
    }

    public void forward(View v) {
        webView.goForward();
    }

    public void refresh(View v){
        Toast.makeText(MainActivity.this.getApplicationContext(),
                "Обновляю...", Toast.LENGTH_SHORT).show();
        webView.reload();
    }

    public void zoomOut(View v){
        webView.zoomOut();
        Toast.makeText(MainActivity.this.getApplicationContext(),
                "Отдаляю...", Toast.LENGTH_SHORT).show();
    }

    public void zoomIn(View v){
        webView.zoomIn();
        Toast.makeText(MainActivity.this.getApplicationContext(),
                "Приближаю...", Toast.LENGTH_SHORT).show();
    }

    public void copyUrl(View v){
        String url = webView.getUrl();
        ClipboardManager clipboard = (ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("", url);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(MainActivity.this.getApplicationContext(),
                "URL " + url + " скопирован в буфер обмена", Toast.LENGTH_SHORT).show();
    }

    public void clearCache(View v){
        webView.clearCache(true);
        Toast.makeText(MainActivity.this.getApplicationContext(), "Очищаю кэш", Toast.LENGTH_SHORT).show();
    }
}