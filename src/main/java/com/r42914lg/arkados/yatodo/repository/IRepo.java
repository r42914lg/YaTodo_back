package com.r42914lg.arkados.yatodo.repository;

import java.util.List;

import com.r42914lg.arkados.yatodo.restservice.TodoItem;

public interface IRepo {
    public List<TodoItem> getTodoItems(String userid);
    public void addTodoItems(List<TodoItem> items, String userid, String deviceid);
    public void clearUserItems(String userid);
}
