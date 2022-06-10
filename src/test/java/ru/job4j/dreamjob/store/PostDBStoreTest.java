package ru.job4j.dreamjob.store;

import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PostDBStoreTest {

    private Post firstPost = new Post(0,
            "Java junior job",
            "description",
            LocalDate.now(),
            true,
            new City());

    private Post secondPost = new Post(1,
            "Java middle job",
            "description2",
            LocalDate.now(),
            true,
            new City());

    private Post thirdPost = new Post(2,
            "Java senior job",
            "description",
            LocalDate.now(),
            true,
            new City());

    @Test
    public void whenCreatePost() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        store.add(firstPost);
        Post postInDb = store.findById(firstPost.getId());
        assertThat(postInDb.getName(), is(firstPost.getName()));
    }

    @Test
    public void whenAllPostsFound() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        store.deleteAll();
        store.add(firstPost);
        store.add(secondPost);
        store.add(thirdPost);
        assertThat(store.findAll(), is(List.of(firstPost, secondPost, thirdPost)));
    }

    @Test
    public void whenPostUpdated() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        store.add(firstPost);
        assertThat(store.findById(firstPost.getId()).getName(), is("Java junior job"));
        Post post = new Post(firstPost.getId(),
                "updated name",
                "description",
                LocalDate.now(),
                true,
                new City());
        store.update(post);
        assertThat(store.findById(post.getId()).getName(), is("updated name"));
    }

    @Test
    public void whenPostsFoundByIds() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        store.add(firstPost);
        store.add(secondPost);
        store.add(thirdPost);
        assertThat(store.findById(firstPost.getId()), is(firstPost));
        assertThat(store.findById(secondPost.getId()), is(secondPost));
        assertThat(store.findById(thirdPost.getId()), is(thirdPost));
        assertNull(store.findById(100));
    }

    @Test
    public void whenPostDeleted() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        store.deleteAll();
        store.add(firstPost);
        store.add(secondPost);
        store.add(thirdPost);
        assertThat(store.findAll(), is(List.of(firstPost, secondPost, thirdPost)));
        assertTrue(store.deletePost(secondPost.getId()));
        assertThat(store.findAll(), is(List.of(firstPost, thirdPost)));
    }

    @Test
    public void whenAllDeleted() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        store.add(firstPost);
        store.add(secondPost);
        store.add(thirdPost);
        store.deleteAll();
        assertThat(store.findAll(), is(List.of()));
    }
}