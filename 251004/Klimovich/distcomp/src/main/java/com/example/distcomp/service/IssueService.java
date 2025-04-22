package com.example.distcomp.service;

import com.example.distcomp.dto.IssueRequestTo;
import com.example.distcomp.dto.IssueResponseTo;
import com.example.distcomp.exception.ServiceException;
import com.example.distcomp.mapper.IssueMapper;
import com.example.distcomp.model.Issue;
import com.example.distcomp.repository.IssueRepository;
import com.example.distcomp.repository.WriterRepository;
import com.example.distcomp.utils.ValidationUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;


@Service
@RequiredArgsConstructor
public class IssueService {
    private final IssueRepository issueRepository;
    private final WriterRepository writerRepository;
    private final IssueMapper issueMapper;

    public IssueResponseTo createIssue(IssueRequestTo request) {
        validateIssueRequest(request);
        Issue entity = issueMapper.toEntity(request);
        entity.setWriter(writerRepository.getReferenceById(request.getWriterId()));
        entity.setCreated(new Timestamp(System.currentTimeMillis()));
        entity.setModified(new Timestamp(System.currentTimeMillis()));
        try{
            return issueMapper.toResponse(issueRepository.save(entity));
        }
        catch (DataIntegrityViolationException e) {
            throw new ServiceException("Data integrity violation", 403);
        }
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
        Issue existingIssue = issueRepository.findById(request.getId())
                .orElseThrow(() -> new ServiceException("Issue not found with id: " + request.getId(), 404));
        entity.setWriter(writerRepository.getReferenceById(request.getWriterId()));
        entity.setCreated(existingIssue.getCreated());
        entity.setModified(new Timestamp(System.currentTimeMillis()));
        try{
            return issueMapper.toResponse(issueRepository.save(entity));
        }
        catch (DataIntegrityViolationException e) {
            throw new ServiceException("Data integrity violation", 403);
        }
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
