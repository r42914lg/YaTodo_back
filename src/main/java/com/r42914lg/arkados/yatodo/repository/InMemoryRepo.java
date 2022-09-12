package com.r42914lg.arkados.yatodo.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.r42914lg.arkados.yatodo.restservice.TodoItem;

public class InMemoryRepo implements IRepo {
    private final HashMap<String,List<TodoItem>> data = new HashMap<String,List<TodoItem>>();
    
    @Override
    public List<TodoItem> getTodoItems(String userid) {
        if (!data.containsKey(userid))
            data.put(userid, new ArrayList<TodoItem>());
        return data.get(userid).stream()
            .collect(Collectors.toList());
    }

    @Override
    public void addTodoItems(List<TodoItem> items, String userid) {
        data.get(userid).addAll(items);
    }

    @Override
    public void clearUserItems(String userid) {
        data.get(userid).clear();
    }
}
