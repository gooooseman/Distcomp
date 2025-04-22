package com.example.distcomp.service;

import com.example.distcomp.dto.IssueRequestTo;
import com.example.distcomp.dto.IssueResponseTo;
import com.example.distcomp.exception.ServiceException;
import com.example.distcomp.mapper.IssueMapper;
import com.example.distcomp.model.Issue;
import com.example.distcomp.repository.IssueRepository;
import com.example.distcomp.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class IssueService {
    private final IssueRepository issueRepository;

    private final IssueMapper issueMapper;

    public IssueResponseTo createIssue(IssueRequestTo request) {
        validateIssueRequest(request);
        return issueMapper.toResponse(issueRepository.save(issueMapper.toEntity(request)));
    }

    public List<IssueResponseTo> getAllIssues() {
        return issueMapper.listToResponse(issueRepository.findAll());
    }

    public IssueResponseTo getIssueById(Long id) {
        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Issue not found with id: " + id, 404));
        return issueMapper.toResponse(issue);
    }

    public IssueResponseTo updateIssue(IssueRequestTo request) {
        validateIssueRequest(request);
        Issue entity = issueMapper.toEntity(request);
        if (!issueRepository.existsById(entity.getId())) {
            throw new ServiceException("Issue not found with id: " + entity.getId(), 404);
        }
        return issueMapper.toResponse(issueRepository.save(entity));
    }

    public void deleteIssue(Long id) {
        if (!issueRepository.existsById(id)) {
            throw new ServiceException("Issue not found with id: " + id, 404);
        }
        issueRepository.deleteById(id);
    }
    private void validateIssueRequest(IssueRequestTo request) {
        ValidationUtils.validateNotNull(request, "Issue");
        ValidationUtils.validateString(request.getTitle(), "Title", 2, 64);
        ValidationUtils.validateString(request.getContent(), "Content", 4, 2048);
        ValidationUtils.validateNotNegative(request.getWriterId(), "Writer ID");
    }
}
