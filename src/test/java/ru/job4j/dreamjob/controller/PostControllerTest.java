package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class PostControllerTest {

    @Test
    public void whenPosts() {
        List<Post> posts = Arrays.asList(
                new Post(1, "New post"),
                new Post(2, "New post")
        );
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        when(postService.findAll()).thenReturn(posts);
        PostController postController = new PostController(postService, mock(CityService.class));
        String page = postController.posts(model, mock(HttpSession.class));
        verify(model).addAttribute("posts", posts);
        assertThat(page, is("posts"));
    }

    @Test
    public void whenAddPost() {
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        PostController postController = new PostController(
                mock(PostService.class), mock(CityService.class));
        assertThat(postController.addPost(model, session), is("addPost"));
    }

    @Test
    public void whenCreatePost() {
        Post post = new Post(1, "New_post", "test",
                LocalDate.now(), true, new City(1));
        PostService postService = mock(PostService.class);
        PostController postController = new PostController(postService, mock(CityService.class));
        String page = postController.createPost(post);
        verify(postService).add(post);
        assertThat(page, is("redirect:/posts"));
    }

    @Test
    public void whenFormUpdatePost() {
        PostService postService = mock(PostService.class);
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        PostController postController = new PostController(postService, mock(CityService.class));
        when(postService.findById(1)).thenReturn(new Post(1, "New_post",
                "test", LocalDate.now(), true, new City(1)));
        assertThat(postController.formUpdatePost(model, 1, session), is("updatePost"));
    }

    @Test
    public void whenUpdatePost() {
        PostController postController = new PostController(mock(PostService.class), mock(CityService.class));
        assertThat(postController.updatePost(new Post(1, "New_post", "test",
                        LocalDate.now(), true, new City(1))),
                is("redirect:/posts"));
    }
}
