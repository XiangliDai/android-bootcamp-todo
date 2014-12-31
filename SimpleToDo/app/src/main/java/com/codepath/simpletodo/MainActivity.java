package com.codepath.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.activeandroid.query.Select;
import com.codepath.simpletodo.model.ToDoItem;

import java.util.ArrayList;

public class MainActivity extends Activity {
    ArrayList<ToDoItem> items;
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

        readFromDB();

        if(items == null)
            items = new ArrayList<>();
        itemsAdapter = new ToDoAdapter(this, items);
        lvItems.setAdapter(itemsAdapter);

        setupListViewListener();

    }

    private void readFromDB() {
        items = new Select()
                .from(ToDoItem.class)
                .orderBy("name ASC").execute();
    }


    private void setupListViewListener(){
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ToDoItem todo = items.get(position);
                items.remove(position);
                todo.delete();
                itemsAdapter.notifyDataSetChanged();
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
        i.putExtra("item_text", items.get(position).name);
        startActivityForResult(i, REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String itemText = data.getExtras().getString("item_text");
            int index = data.getExtras().getInt("index");
            ToDoItem todo = items.get(index);
            todo.name = itemText;
            todo.save();
            items.set(index, todo);
            itemsAdapter.notifyDataSetChanged();

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
        ToDoItem todo = new ToDoItem(itemText);
        todo.save();
        items.add(todo);
        itemsAdapter.notifyDataSetChanged();
        eNewItem.setText("");
    }

}
