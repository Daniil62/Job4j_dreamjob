package ru.job4j.dreamjob.store;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class CandidateStore {

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private final AtomicInteger ids;

    public CandidateStore() {
        candidates.put(1, new Candidate(1, "Junior Java dev.", "Experience: 1 year."));
        candidates.put(2, new Candidate(2, "Middle Java dev.", "Experience: 3 years."));
        candidates.put(3, new Candidate(3, "Senior Java dev.", "Experience: 5 years."));
        ids = new AtomicInteger(candidates.size());
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public void add(Candidate candidate) {
        candidate.setId(ids.incrementAndGet());
        candidates.put(candidate.getId(), candidate);
    }

    public Candidate findById(int id) {
        return candidates.get(id);
    }

    public void update(Candidate candidate) {
        candidates.replace(candidate.getId(), candidate);
    }
}
