package com.example.demo.service.interfaces;

import com.example.demo.model.Progress;

public interface ProgressService {
    Progress createProgress(Progress progress);

    Progress getProgressById(Long id);

    Progress updateProgress(Long id, Progress progress);

    void deleteProgress(Long id);
}
