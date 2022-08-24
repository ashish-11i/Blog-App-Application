package com.codewithashish.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codewithashish.blog.entities.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer>{

}
