package com.beardedbrothers.sentrybayherald.controller;

import com.beardedbrothers.sentrybayherald.dto.PostDto;
import com.beardedbrothers.sentrybayherald.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PreAuthorize("hasAuthority('ROLE_MODERATOR') or hasAuthority('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostDto postDto) {
        postService.createPost(postDto);
        return new ResponseEntity<>(HttpStatus.CREATED); // Use CREATED status code for successful resource creation
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> showAllPosts() {
        List<PostDto> posts = postService.showAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getSinglePost(@PathVariable Long id) {
        PostDto postDto = postService.readSinglePost(id);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }
}
