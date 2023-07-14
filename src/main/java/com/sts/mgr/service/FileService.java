package com.sts.mgr.service;

import org.springframework.stereotype.Service;

import com.sts.mgr.request.JsonRequestDto;
import com.sts.mgr.response.JsonResponseDto;
import com.sts.mgr.utils.exceptions.UnAuthorizedUserException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

@Service
@Transactional
public interface FileService {
	public JsonResponseDto getFileMetadata(JsonRequestDto jsonRequest) throws UnAuthorizedUserException;

	void downloadPDFResource(JsonRequestDto jsonRequest, HttpServletRequest request, HttpServletResponse response)
			throws UnAuthorizedUserException;
}
