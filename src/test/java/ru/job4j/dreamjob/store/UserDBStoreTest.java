package ru.job4j.dreamjob.store;

import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.User;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserDBStoreTest {

    @Test
    public void whenUserAddedSuccessful() {
        UserDBStore store = new UserDBStore(new Main().loadPool());
        store.deleteAll();
        String email = "user1@mail.dude";
        User user = new User(1, email, "123456");
        assertThat(store.add(user), is(Optional.of(user)));
        assertThat(store.findByEmail(email).getEmail(), is(user.getEmail()));
    }

    @Test
    public void whereSecondUserCanNotBeAdded() {
        UserDBStore store = new UserDBStore(new Main().loadPool());
        store.deleteAll();
        String email = "user2@mail.dude";
        User user = new User(1, email, "123456");
        User userLooser = new User(2, email, "qwerty");
        assertThat(store.add(user), is(Optional.of(user)));
        assertThat(store.add(userLooser), is(Optional.empty()));
        assertThat(store.findByEmail(email), is(user));
    }

    @Test
    public void whenUserDeletedWhenOtherUserWithSameEmailCanBeAdded() {
        UserDBStore store = new UserDBStore(new Main().loadPool());
        store.deleteAll();
        String email = "user2@mail.dude";
        String password = "123456";
        User user = new User(1, email, password);
        User otherUser = new User(2, email, "qwerty");
        assertThat(store.add(user), is(Optional.of(user)));
        assertThat(store.findByEmail(email), is(user));
        assertThat(store.add(otherUser), is(Optional.empty()));
        assertTrue(store.deleteUser(email, password));
        assertNull(store.findByEmail(email));
        assertThat(store.add(otherUser), is(Optional.of(otherUser)));
        assertThat(store.findByEmail(email), is(otherUser));
    }
}
