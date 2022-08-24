package com.codewithashish.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codewithashish.blog.entities.Comment;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
