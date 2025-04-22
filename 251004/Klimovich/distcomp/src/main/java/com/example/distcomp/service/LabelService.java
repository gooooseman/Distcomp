package com.example.distcomp.service;

import com.example.distcomp.dto.LabelRequestTo;
import com.example.distcomp.dto.LabelResponseTo;
import com.example.distcomp.exception.ServiceException;
import com.example.distcomp.mapper.LabelMapper;
import com.example.distcomp.model.Label;
import com.example.distcomp.repository.LabelRepository;
import com.example.distcomp.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LabelService {
    private final LabelRepository labelRepository;
    private final LabelMapper labelMapper;

    public LabelResponseTo createLabel(LabelRequestTo request) {
        validateLabelRequest(request);
        return labelMapper.toResponse(labelRepository.save(labelMapper.toEntity(request)));
    }

    public List<LabelResponseTo> getAllLabels() {
        return labelMapper.listToResponse(labelRepository.findAll());
    }

    public LabelResponseTo getLabelById(Long id) {
        Label label = labelRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Label not found with id: " + id, 404));
        return labelMapper.toResponse(label);
    }

    public LabelResponseTo updateLabel(LabelRequestTo request) {
        validateLabelRequest(request);
        Label entity = labelMapper.toEntity(request);
        if (!labelRepository.existsById(entity.getId())) {
            throw new ServiceException("Label not found with id: " + entity.getId(), 404);
        }
        return labelMapper.toResponse(labelRepository.save(entity));
    }

    public void deleteLabel(Long id) {
        if (!labelRepository.existsById(id)) {
            throw new ServiceException("Label not found with id: " + id, 404);
        }
        labelRepository.deleteById(id);
    }

    private void validateLabelRequest(LabelRequestTo request) {
        ValidationUtils.validateNotNull(request, "Label");
        ValidationUtils.validateString(request.getName(), "Name", 2, 32);
    }
}
