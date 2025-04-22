package com.example.distcomp.service;
import com.example.distcomp.dto.WriterRequestTo;
import com.example.distcomp.dto.WriterResponseTo;
import com.example.distcomp.exception.ServiceException;
import com.example.distcomp.mapper.WriterMapper;
import com.example.distcomp.model.Writer;
import com.example.distcomp.repository.WriterRepository;
import com.example.distcomp.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WriterService {
    private final WriterRepository writerRepository;
    private final WriterMapper writerMapper;

    public WriterResponseTo createWriter(WriterRequestTo request) {
        validateWriterRequest(request);
        try{
            return writerMapper.toResponse(writerRepository.save(writerMapper.toEntity(request)));
        }
        catch (DataIntegrityViolationException e) {
            throw new ServiceException("Data integrity violation", 403);
        }
    }

    public List<WriterResponseTo> getAllWriters() {
        return writerMapper.listToResponse(writerRepository.findAll());
    }

    public WriterResponseTo getWriterById(Long id) {
        Writer writer = writerRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Writer not found with id: " + id, 404));
        return writerMapper.toResponse(writer);
    }

    public WriterResponseTo updateWriter(WriterRequestTo request) {
        validateWriterRequest(request);
        Writer entity = writerMapper.toEntity(request);
        if (!writerRepository.existsById(entity.getId())) {
            throw new ServiceException("Writer not found with id: " + entity.getId(), 404);
        }
        try{
            return writerMapper.toResponse(writerRepository.save(entity));
        }
        catch (DataIntegrityViolationException e) {
            throw new ServiceException("Data integrity violation", 403);
        }
    }

    public void deleteWriter(Long id) {
        if (!writerRepository.existsById(id)) {
            throw new ServiceException("Writer not found with id: " + id, 404);
        }
        writerRepository.deleteById(id);
    }

    private void validateWriterRequest(WriterRequestTo request) {
        ValidationUtils.validateNotNull(request, "Writer");
        ValidationUtils.validateString(request.getLogin(), "Login", 2, 64);
        ValidationUtils.validateString(request.getPassword(), "Password", 8, 128);
        ValidationUtils.validateString(request.getFirstname(), "First name", 2, 64);
        ValidationUtils.validateString(request.getLastname(), "Last name", 2, 64);
    }
}
