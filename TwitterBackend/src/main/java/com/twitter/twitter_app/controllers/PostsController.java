package com.twitter.twitter_app.controllers;

import com.twitter.twitter_app.models.Post;
import com.twitter.twitter_app.models.User;
import com.twitter.twitter_app.models.UserInReaction;
import com.twitter.twitter_app.payload.request.ReactionRequest;
import com.twitter.twitter_app.payload.request.SavePost;
import com.twitter.twitter_app.payload.response.PostDto;
import com.twitter.twitter_app.payload.response.ReactionResponse;
import com.twitter.twitter_app.payload.response.UserAndPostsResponse;
import com.twitter.twitter_app.repository.PostsRepository;
import com.twitter.twitter_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Autowired
    UserRepository userRepository;

    @GetMapping("user/{userId}")
    @PreAuthorize("hasRole('REGULAR_USER') or hasRole('BUSINESS_USER')")
    ResponseEntity<UserAndPostsResponse> getPostsByUser(@PathVariable String userId) {
        var optUser = userRepository.findById(userId);
        if (!optUser.isPresent()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        User u = optUser.get();
        var dto = new UserAndPostsResponse();
        dto.setUserName(u.getName());
        dto.setUserLastName(u.getLastName());

        List<Post> posts = postsRepository.findPostsByUserId(userId).orElse(Collections.emptyList());
        List<PostDto> postDtos = new ArrayList<>();
        posts.forEach(post -> {
            PostDto postDto = new PostDto();
            postDto.setId(post.getId());
            postDto.setContent(post.getContent());
            postDto.setTimestamp(post.getTimestamp());
            postDto.setUserName(post.getUserName());
            postDto.setUserLastName(post.getUserLastName());
            postDto.setLikes(post.getLikes() != null ? post.getLikes() : new ArrayList<>());
            postDto.setImagePath(post.getImage());
            postDtos.add(postDto);
        });

        dto.setPosts(postDtos);
        return new ResponseEntity<>(dto, HttpStatus.OK);

    }

    @PostMapping("")
    @PreAuthorize("hasRole('REGULAR_USER') or hasRole('BUSINESS_USER')")
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
    @PreAuthorize("hasRole('REGULAR_USER') or hasRole('BUSINESS_USER')")
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