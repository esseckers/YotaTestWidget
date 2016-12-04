package com.danilov.ivan.yotatestwidget.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.danilov.ivan.yotatestwidget.R;
import com.danilov.ivan.yotatestwidget.Utils;
import com.danilov.ivan.yotatestwidget.widget.UpdateWidgetService;

public class DialogActivity extends Activity {

    private EditText inputRssLink;
    private TextView subscribe;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_activity);
        assignView();
        initView();
    }

    private void initView() {
        inputRssLink.setText("http://www.feedforall.com");
        subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputLink = inputRssLink.getText().toString();
                if (inputLink.isEmpty()) {
                    Toast.makeText(DialogActivity.this, "Link cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    Intent serviceIntent = new Intent(DialogActivity.this, UpdateWidgetService.class);
                    serviceIntent.putExtra(Utils.EXTRA_KEY, "http://www.feedforall.com");
                    serviceIntent.setAction(Utils.EXTRA_KEY);
                    // serviceIntent.putExtra(Utils.EXTRA_KEY, "inputLink");
                    startService(serviceIntent);
                    finish();
                }
            }
        });
    }

    private void assignView() {
        inputRssLink = (EditText) findViewById(R.id.input_rss_uri);
        subscribe = (TextView) findViewById(R.id.subscribe);
    }
}
