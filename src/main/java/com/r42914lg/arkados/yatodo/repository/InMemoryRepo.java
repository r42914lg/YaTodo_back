package com.r42914lg.arkados.yatodo.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.r42914lg.arkados.yatodo.restservice.TodoItem;

public class InMemoryRepo implements IRepo {
    private final HashMap<String,List<InMemTodoItem>> data = new HashMap<String,List<InMemTodoItem>>();
    
    @Override
    public List<TodoItem> getTodoItems(String userid) {
        if (!data.containsKey(userid))
            data.put(userid, new ArrayList<InMemTodoItem>());

        return data.get(userid).stream()
            .map(item -> new TodoItem(
                item.getLocalid(), 
                item.getText(), 
                item.getImportance(), 
                item.getDone(), 
                item.getCreated(), 
                item.getDeadline(), 
                item.getChanged(),
                false))
            .collect(Collectors.toList());
    }

    @Override
    public void addTodoItems(List<TodoItem> items, String userid, String deviceid) {
        List<InMemTodoItem> inMemItems  = items.stream()
            .map(item -> new InMemTodoItem(
                userid,
                item.getLocalid(), 
                item.getText(), 
                item.getImportance(), 
                item.getDone(), 
                item.getCreated(), 
                item.getDeadline(), 
                item.getChanged(),
                deviceid))
            .collect(Collectors.toList());
        

        data.get(userid).addAll(inMemItems);
    }

    @Override
    public void clearUserItems(String userid) {
        data.get(userid).clear();
    }
}
