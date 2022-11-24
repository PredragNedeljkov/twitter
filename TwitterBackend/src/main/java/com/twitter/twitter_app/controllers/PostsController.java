package com.twitter.twitter_app.controllers;

import com.twitter.twitter_app.models.Post;
import com.twitter.twitter_app.models.UserInReaction;
import com.twitter.twitter_app.payload.request.ReactionRequest;
import com.twitter.twitter_app.payload.request.SavePost;
import com.twitter.twitter_app.payload.response.PostDto;
import com.twitter.twitter_app.payload.response.ReactionResponse;
import com.twitter.twitter_app.repository.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController()
@CrossOrigin(origins = "*")
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    PostsRepository postsRepository;

    @GetMapping("user/{userId}")
    ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable String userId) {
        List<Post> posts = postsRepository.findPostsByUserId(userId).orElse(Collections.emptyList());

        List<PostDto> dtos = new ArrayList<>();
        posts.forEach(post -> {
            PostDto dto = new PostDto();
            dto.setId(post.getId());
            dto.setContent(post.getContent());
            dto.setTimestamp(post.getTimestamp());
            dto.setUserName(post.getUserName());
            dto.setUserLastName(post.getUserLastName());
            dto.setLikes(post.getLikes() != null ? post.getLikes() : new ArrayList<>());
            dto.setImagePath(post.getImage());
            dtos.add(dto);
        });

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping("")
    ResponseEntity<?> addNewPost(@RequestBody SavePost requestBody) {
        Post newPost = new Post();
        newPost.setContent(requestBody.getContent());
        newPost.setUserId(requestBody.getUserId());
        newPost.setTimestamp(LocalDateTime.now());
        newPost.setUserName(requestBody.getUserName());
        newPost.setUserLastName(requestBody.getUserLastName());
        newPost.setImage(requestBody.getImage());
        newPost.setLikes(new ArrayList<>());

        postsRepository.save(newPost);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/like")
    ResponseEntity<?> likePost(@RequestBody ReactionRequest requestBody) {
        Optional<Post> optional = postsRepository.findById(requestBody.getPostId());

        if (optional.isPresent()) {
            Post post = optional.get();
            UserInReaction userInReaction = new UserInReaction(requestBody.getUserId(), requestBody.getUserName(), requestBody.getUserLastName());

            if (post.getLikes().contains(userInReaction)) {
                post.setLikes(post.getLikes().stream().filter(like -> !like.getUserId().equals(requestBody.getUserId())).collect(Collectors.toList()));
            } else {
                post.getLikes().add(userInReaction);
            }

            postsRepository.save(post);

            ReactionResponse dto = new ReactionResponse(post.getLikes());

            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}