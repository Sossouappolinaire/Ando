package com.movisossou.app;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.JavascriptInterface;
import android.widget.Toast;
import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getBridge().getWebView().addJavascriptInterface(new Object() {
      @JavascriptInterface
      public void download(String url, String name) {
        try {
          String safe = name.replaceAll("[\\\\/:*?\"<>|]", "_");
          DownloadManager.Request r = new DownloadManager.Request(Uri.parse(url));
          r.setTitle(safe);
          r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
          r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "moviSoSsou/" + safe);
          ((DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE)).enqueue(r);
        } catch (final Exception e) {
          runOnUiThread(new Runnable() {
            public void run() {
              Toast.makeText(MainActivity.this, "Échec du téléchargement", Toast.LENGTH_LONG).show();
            }
          });
        }
      }
    }, "AndroidDL");
  }
}
