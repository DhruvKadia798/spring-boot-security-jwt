package com.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.models.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
