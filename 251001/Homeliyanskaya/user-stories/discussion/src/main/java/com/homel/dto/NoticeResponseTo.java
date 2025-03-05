package com.homel.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class NoticeResponseTo {
    private Long id;
    private Long storyId;
    private String content;
}
