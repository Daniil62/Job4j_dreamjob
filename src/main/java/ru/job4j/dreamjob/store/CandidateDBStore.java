package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class CandidateDBStore {

    private final BasicDataSource pool;
    private static Logger log = Logger.getLogger(CandidateDBStore.class.getName());

    public CandidateDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Candidate> findAll() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM candidate")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(new Candidate(it.getInt(Fields.ID),
                            it.getString(Fields.NAME),
                            it.getString(Fields.DESC),
                            it.getDate(Fields.CREATED).toLocalDate(),
                            it.getBytes(Fields.PHOTO)));
                }
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "error: ", e);
        }
        return candidates;
    }

    public void add(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("INSERT INTO candidate(" +
                             "name, description, created, photo) VALUES (?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setDate(3, Date.valueOf(candidate.getCreated()));
            ps.setBytes(4, candidate.getPhoto());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "error: ", e);
        }
    }

    public Candidate findById(int id) {
        Candidate result = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM candidate WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    result =  new Candidate(it.getInt(Fields.ID),
                            it.getString(Fields.NAME),
                            it.getString(Fields.DESC),
                            it.getDate(Fields.CREATED).toLocalDate(),
                            it.getBytes(Fields.PHOTO));
                }
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "error: ", e);
        }
        return result;
    }

    public void update(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("UPDATE candidate " +
                             "SET name = ?, description = ?, created = ?, photo = ? WHERE id = ?",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setDate(3, Date.valueOf(candidate.getCreated()));
            ps.setBytes(4, candidate.getPhoto());
            ps.setInt(5, candidate.getId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "error: ", e);
        }
    }

    private static class Fields {

        private static final String ID = "id";
        private static final String NAME = "name";
        private static final String DESC = "description";
        private static final String CREATED = "created";
        private static final String PHOTO = "photo";
    }
}
