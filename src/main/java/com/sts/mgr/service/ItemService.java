package com.sts.mgr.service;

import org.springframework.stereotype.Service;

import com.sts.mgr.request.JsonRequestDto;
import com.sts.mgr.response.JsonResponseDto;
import com.sts.mgr.utils.exceptions.UnAuthorizedUserException;

import jakarta.transaction.Transactional;

@Service
@Transactional
public interface ItemService {
	public JsonResponseDto createSpace(JsonRequestDto jsonRequest) throws UnAuthorizedUserException;

	public JsonResponseDto createFolder(JsonRequestDto jsonRequest) throws UnAuthorizedUserException;

	public JsonResponseDto createFile(JsonRequestDto jsonRequest) throws UnAuthorizedUserException;
}
