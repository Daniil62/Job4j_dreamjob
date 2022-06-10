package ru.job4j.dreamjob.store;

import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CandidateDBStoreTest {

    private Candidate firstCandidate = new Candidate(
            0,
            "Ivan",
            "Middle C++ dev.");
    private Candidate secondCandidate = new Candidate(
            1,
            "Alexandr",
            "Senior C++ dev.");
    private Candidate thirdCandidate = new Candidate(
            2,
            "Andrey",
            "Senior C# dev.");

    @Test
    public void whenCandidateCreated() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        store.add(firstCandidate);
        assertThat(store.findById(firstCandidate.getId()).getName(), is(firstCandidate.getName()));
    }

    @Test
    public void whenAllCandidatesFound() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        store.deleteAll();
        store.add(firstCandidate);
        store.add(secondCandidate);
        store.add(thirdCandidate);
        assertThat(store.findAll(), is(List.of(firstCandidate, secondCandidate, thirdCandidate)));
    }

    @Test
    public void whenCandidateUpdated() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        store.add(firstCandidate);
        assertThat(store.findById(firstCandidate.getId()).getDescription(), is("Middle C++ dev."));
        store.update(new Candidate(firstCandidate.getId(), "Ivan", "Senior C++ dev."));
        assertThat(store.findById(firstCandidate.getId()).getDescription(), is("Senior C++ dev."));
    }

    @Test
    public void whenCandidateFoundById() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        store.add(firstCandidate);
        store.add(secondCandidate);
        store.add(thirdCandidate);
        assertThat(store.findById(firstCandidate.getId()), is(firstCandidate));
        assertThat(store.findById(secondCandidate.getId()), is(secondCandidate));
        assertThat(store.findById(thirdCandidate.getId()), is(thirdCandidate));
        assertNull(store.findById(100));
    }

    @Test
    public void whenCandidateDeleted() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        store.deleteAll();
        store.add(firstCandidate);
        store.add(secondCandidate);
        store.add(thirdCandidate);
        assertThat(store.findAll(), is(List.of(firstCandidate, secondCandidate, thirdCandidate)));
        assertTrue(store.deleteCandidate(secondCandidate.getId()));
        assertThat(store.findAll(), is(List.of(firstCandidate, thirdCandidate)));
    }

    @Test
    public void whenAllDeleted() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        store.add(firstCandidate);
        store.add(secondCandidate);
        store.add(thirdCandidate);
        store.deleteAll();
        assertThat(store.findAll(), is(List.of()));
    }
}
