package com.codepath.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class EditItemActivity extends Activity {
    EditText etEditItem;
    private int currentPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        currentPosition = getIntent().getIntExtra("index", 0);
        String text = getIntent().getStringExtra("item_text");
        etEditItem = (EditText) findViewById(R.id.etEditItem);
        etEditItem.setText(text);
        etEditItem.setSelection(etEditItem.getText().length());
        etEditItem.hasFocus();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
        return true;
    }

    public void onSave(View view) {
        Intent data = new Intent();
        data.putExtra("item_text", etEditItem.getText().toString());
        data.putExtra("index", currentPosition);
        setResult(RESULT_OK, data);
        finish();
    }
}
