package com.codepath.simpletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codepath.simpletodo.model.ToDoItem;

import java.util.List;

public class ToDoAdapter extends ArrayAdapter<ToDoItem>{

    List<ToDoItem> todos;
    public ToDoAdapter(Context context, List<ToDoItem> todos) {
        super(context, 0, todos);
        this.todos = todos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ToDoItem todo = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_item, parent, false);
        }
        TextView tvTodoItem = (TextView) convertView.findViewById(R.id.tvTodoItem);
        tvTodoItem.setText(todo.name);
        return convertView;
    }

}
