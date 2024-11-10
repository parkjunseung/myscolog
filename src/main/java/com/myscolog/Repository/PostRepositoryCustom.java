package com.myscolog.Repository;

import com.myscolog.domain.Post;
import com.myscolog.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
