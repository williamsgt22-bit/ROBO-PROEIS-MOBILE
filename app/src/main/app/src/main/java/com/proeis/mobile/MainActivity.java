package com.proeis.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;
import android.graphics.Color;

public class MainActivity extends Activity {

    private WebView webView;
    private TextView status;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(16, 16, 16, 16);

        TextView titulo = new TextView(this);
        titulo.setText("ROBÔ PROEIS");
        titulo.setTextSize(24);
        titulo.setTextColor(Color.BLACK);
        layout.addView(titulo);

        status = new TextView(this);
        status.setText("Abra o portal e faça seu login.");
        layout.addView(status);

        Button abrir = new Button(this);
        abrir.setText("ABRIR PORTAL PROEIS");
        layout.addView(abrir);

        Button verificar = new Button(this);
        verificar.setText("VERIFICAR VAGA");
        layout.addView(verificar);

        webView = new WebView(this);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        layout.addView(webView,
            new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, 1));

        setContentView(layout);

        abrir.setOnClickListener(v -> {
            webView.loadUrl("https://www.proeis.rj.gov.br/");
            status.setText("Portal aberto. Faça seu login.");
        });

        verificar.setOnClickListener(v -> {
            status.setText("Verificando a página...");
            webView.evaluateJavascript(
                "(function(){return document.body.innerText;})()",
                resultado -> status.setText(
                    resultado == null ? "Não foi possível verificar." :
                    "Página consultada. Confira a vaga no portal."
                )
            );
        });

        webView.loadUrl("https://www.proeis.rj.gov.br/");
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
