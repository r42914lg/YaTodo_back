package com.r42914lg.arkados.yatodo.repository;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.r42914lg.arkados.yatodo.jpa.DbTodoItem;
import com.r42914lg.arkados.yatodo.jpa.TodoItemRepoJPA;
import com.r42914lg.arkados.yatodo.restservice.TodoItem;

@Component
public class DbRepo implements IRepo {

    @Autowired
	TodoItemRepoJPA todoItemRepoJPA;

    @Override
    public List<TodoItem> getTodoItems(String userid) {
        List<DbTodoItem> dbList = todoItemRepoJPA.findByUserid(userid);
        
        List<TodoItem> domainList = dbList.stream()
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

        return domainList;
    }

    @Override
    public void addTodoItems(List<TodoItem> items, String userid) {
        List<DbTodoItem> domainList = items.stream()
        .map(item -> new DbTodoItem(
            userid,
            item.getLocalid(), 
            item.getText(), 
            item.getImportance(), 
            item.getDone(), 
            item.getCreated(), 
            item.getDeadline(), 
            item.getChanged()))
        .collect(Collectors.toList());

        todoItemRepoJPA.saveAll(domainList);
    }

    @Override
    @Transactional
    public void clearUserItems(String userid) {
        Long deletedCount = todoItemRepoJPA.deleteByUserid(userid);
        System.out.println("Num of items deleted: " + deletedCount);
    }
}
