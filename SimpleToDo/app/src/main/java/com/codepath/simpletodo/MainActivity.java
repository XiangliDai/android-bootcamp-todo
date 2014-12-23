package com.codepath.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends Activity {
    ArrayList<String> items;
    ToDoAdapter itemsAdapter;
    ListView lvItems;
    private final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView)findViewById(R.id.lvItems);
        lvItems.setFadingEdgeLength(6);
        lvItems.setSmoothScrollbarEnabled(true);
        lvItems.setDivider(getResources().getDrawable(R.color.bright_foreground_material_light));
        lvItems.setDividerHeight(1);

        readFile();

        if(items == null)
            items = new ArrayList<>();
        itemsAdapter = new ToDoAdapter(this, items);
        lvItems.setAdapter(itemsAdapter);

        setupListViewListener();

    }


    private void setupListViewListener(){
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeFile();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                launchEditActivity(position);
            }
        });
    }

    private void launchEditActivity(int position) {
        Intent i = new Intent(this, EditItemActivity.class);
        i.putExtra("index", position);
        i.putExtra("item_text", items.get(position));
        startActivityForResult(i, REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String itemText = data.getExtras().getString("item_text");
            int index = data.getExtras().getInt("index");
            items.set(index,itemText);
            itemsAdapter.notifyDataSetChanged();
            writeFile();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onAddItem(View view) {
        EditText eNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = eNewItem.getText().toString();
        items.add(itemText);
        itemsAdapter.notifyDataSetChanged();
        writeFile();
        eNewItem.setText("");
    }

    private void readFile(){
        File fileDirs = getFilesDir();
       File todoFile = new File(fileDirs, "todo.txt");
        try{
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void writeFile(){
        File fileDirs = getFilesDir();
        File todoFile = new File(fileDirs, "todo.txt");
        try{
            FileUtils.writeLines(todoFile, items);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
