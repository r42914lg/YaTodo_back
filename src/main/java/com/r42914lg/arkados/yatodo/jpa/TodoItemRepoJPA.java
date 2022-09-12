package com.r42914lg.arkados.yatodo.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoItemRepoJPA extends JpaRepository<DbTodoItem, Long> {
    List<DbTodoItem> findByUserid(String userid);
    Long deleteByUserid(String userid);
  }