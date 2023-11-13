package com.beardedbrothers.sentrybayherald.repository;

import com.beardedbrothers.sentrybayherald.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}