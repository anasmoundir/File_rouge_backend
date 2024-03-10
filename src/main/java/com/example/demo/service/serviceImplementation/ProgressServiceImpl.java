package com.example.demo.service.serviceImplementation;

import com.example.demo.exception.ProgressNotFoundException;
import com.example.demo.model.Progress;
import com.example.demo.repository.ProgressRepository;
import com.example.demo.service.interfaces.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class ProgressServiceImpl implements ProgressService {

    private final ProgressRepository progressRepository;

    @Autowired
    public ProgressServiceImpl(ProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    @Override
    public Progress createProgress(Progress progress) {
        return progressRepository.save(progress);
    }

    @Override
    public Progress getProgressById(Long id) {
        return progressRepository.findById(id)
                .orElseThrow(() -> new ProgressNotFoundException("Progress not found with id: " + id));
    }

    @Override
    public Progress updateProgress(Long id, Progress progress) {
        if (!progressRepository.existsById(id)) {
            throw new ProgressNotFoundException("Progress not found with id: " + id);
        }
        progress.setId(id);
        return progressRepository.save(progress);
    }

    @Override
    public void deleteProgress(Long id) {
        if (!progressRepository.existsById(id)) {
            throw new ProgressNotFoundException("Progress not found with id: " + id);
        }
        progressRepository.deleteById(id);
    }
}
