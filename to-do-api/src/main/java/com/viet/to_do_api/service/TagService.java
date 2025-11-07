package com.viet.to_do_api.service;

import com.viet.to_do_api.dto.task.TagDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface TagService {
    TagDto createNewTag(TagDto tagDto);
    boolean checkExistsTaskByTitle(String title);
    List<TagDto> getAllTags(Authentication authentication);
}
