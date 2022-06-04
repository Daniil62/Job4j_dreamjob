package ru.job4j.dreamjob.service;

import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.store.PostDBStore;

import java.util.Collection;

@Service
public class PostService {

    private final PostDBStore store;
    private final CityService service;

    public PostService(PostDBStore store, CityService service) {
        this.store = store;
        this.service = service;
    }

    public Collection<Post> findAll() {
        Collection<Post> result = store.findAll();
        result.forEach(p -> p.setCity(service.findById(p.getCity().getId())));
        return result;
    }

    public void add(Post post) {
        store.add(post);
    }

    public Post findById(int id) {
        Post post = store.findById(id);
        post.setCity(service.findById(post.getCity().getId()));
        return post;
    }

    public void update(Post post) {
        store.update(post);
    }
}
