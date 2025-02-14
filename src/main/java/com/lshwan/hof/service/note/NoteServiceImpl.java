package com.lshwan.hof.service.note;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lshwan.hof.domain.entity.note.Note;
import com.lshwan.hof.repository.note.NoteRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NoteServiceImpl implements NoteService{
  @Autowired
  private NoteRepository repository;
  @Override
  @Transactional
  public Long add(Note note) {
    return repository.save(note).getNno();
  }

  @Override
  public Note findBy(Long nno) {
    return repository.findById(nno).orElse(null);
  }

  @Override
  public List<Note> findList() {
    return repository.findAll();
  }

  @Override
  @Transactional
  public Long modify(Note note) {
    return repository.save(note).getNno();
  }

  @Override
  @Transactional
  public boolean remove(Long nno) {
    if (repository.existsById(nno)) {
      repository.deleteById(nno);
      return true;
    }
    return false;
  }
  
}
