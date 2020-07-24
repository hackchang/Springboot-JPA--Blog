package com.haechang.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.haechang.blog.model.Board;
import com.haechang.blog.model.User;


public interface BoardRepository extends JpaRepository<Board, Integer>{

}

