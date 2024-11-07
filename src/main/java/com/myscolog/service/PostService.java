package com.myscolog.service;


import com.myscolog.Repository.PostRepository;
import com.myscolog.domain.Post;
import com.myscolog.request.PostCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreate postCreate) {

       Post post = new Post(postCreate.getTitle(), postCreate.getContent());
       postRepository.save(post);
    }
}
