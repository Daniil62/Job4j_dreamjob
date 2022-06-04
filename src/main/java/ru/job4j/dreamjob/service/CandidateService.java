package ru.job4j.dreamjob.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.CandidateDBStore;

import java.io.IOException;
import java.util.Collection;

@Service
public class CandidateService {

    private final CandidateDBStore store;
    private static final int MAX_PERMITTED_SIZE = 1048576;

    public CandidateService(CandidateDBStore store) {
        this.store = store;
    }

    public Collection<Candidate> findAll() {
        return store.findAll();
    }

    public void add(Candidate candidate) {
        store.add(candidate);
    }

    public Candidate findById(int id) {
        return store.findById(id);
    }

    public void update(Candidate candidate) {
        store.update(candidate);
    }

    public void setPhoto(Candidate candidate, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            byte[] photo = file.getBytes();
            if (photo.length <= MAX_PERMITTED_SIZE) {
                candidate.setPhoto(photo);
            }
        }
    }
}