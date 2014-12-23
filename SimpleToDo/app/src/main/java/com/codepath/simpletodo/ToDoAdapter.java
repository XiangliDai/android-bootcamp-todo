package com.codepath.simpletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ToDoAdapter extends ArrayAdapter<String>{

    List<String> todos;
    public ToDoAdapter(Context context, List<String> todos) {
        super(context, 0, todos);
        this.todos = todos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String todoStr = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_item, parent, false);
        }
        TextView tvTodoItem = (TextView) convertView.findViewById(R.id.tvTodoItem);
        tvTodoItem.setText(todoStr);
        return convertView;
    }

}
