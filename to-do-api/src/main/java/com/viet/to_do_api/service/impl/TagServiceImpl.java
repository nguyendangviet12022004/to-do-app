package com.viet.to_do_api.service.impl;

import com.viet.to_do_api.exception.task.ExistsException;
import org.springframework.stereotype.Service;

import com.viet.to_do_api.dto.task.TagDto;
import com.viet.to_do_api.entity.task.Tag;
import com.viet.to_do_api.mapper.task.TagMapper;
import com.viet.to_do_api.repository.TagRepository;
import com.viet.to_do_api.service.TagService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    public TagDto createNewTag(TagDto tagDto) {

        if(checkExistsTaskByTitle(tagDto.getTitle())) {
            throw new ExistsException("Title already exists");
        }

        Tag tag = tagMapper.toEntity(tagDto);
        tag = tagRepository.save(tag);
        return tagMapper.toDto(tag);
    }

    @Override
    public boolean checkExistsTaskByTitle(String title) {
        return this.tagRepository.existsByTitle(title);
    }
}
