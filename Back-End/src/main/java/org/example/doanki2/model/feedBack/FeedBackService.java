package org.example.doanki2.model.feedBack;

import org.example.doanki2.entity.Feed_Back;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class FeedBackService {
    @Autowired
    private FeedBackRepository feedBackRepository;

    public ResponseEntity<List<Feed_Back>> getAll(){
        List<Feed_Back> getAll = feedBackRepository.findAll();
        return ResponseEntity.ok().body(getAll);
    }
    public ResponseEntity<List<Feed_Back>> getById(int id){
        List<Feed_Back> getAll = feedBackRepository.findByProductId(id);
        if (getAll.isEmpty()){
            throw new IllegalArgumentException("id feed back not found");
        }
        return ResponseEntity.ok().body(getAll);
    }
    public ResponseEntity<Feed_Back> post (Feed_Back feedBack){
       Feed_Back save = feedBackRepository.save(feedBack);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(save.getId()).toUri();
        return ResponseEntity.created(location).body(save);
    }
    public ResponseEntity<Feed_Back> put(int id, Feed_Back feedBack){
        Optional<Feed_Back> optional = feedBackRepository.findById(id);
        if (!optional.isPresent()){
            throw new IllegalArgumentException("id feed back not found");
        }
        feedBack.setId(optional.get().getId());
        feedBackRepository.save(feedBack);

        return ResponseEntity.ok().body(feedBack);
    }
    public ResponseEntity<Feed_Back> Delete(int id) {
        Optional<Feed_Back> optional = feedBackRepository.findById(id);
        if (!optional.isPresent()) {
            throw new IllegalArgumentException("id feed back not found");
        }
        feedBackRepository.deleteById(optional.get().getId());
        return ResponseEntity.noContent().build();
    }
}
