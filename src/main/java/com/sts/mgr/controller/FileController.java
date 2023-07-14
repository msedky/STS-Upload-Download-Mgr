package com.sts.mgr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sts.mgr.request.JsonRequestDto;
import com.sts.mgr.response.JsonResponseDto;
import com.sts.mgr.service.FileService;
import com.sts.mgr.utils.exceptions.UnAuthorizedUserException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/file")
public class FileController {

	@Autowired
	@Qualifier("fileServiceImpl")
	private FileService fileService;

	@PostMapping("/metadata")
	public ResponseEntity<JsonResponseDto> getFileMetaData(@RequestBody JsonRequestDto jsonRequest) {
		try {
			JsonResponseDto responseResult = fileService.getFileMetadata(jsonRequest);
			return ResponseEntity.ok(responseResult);
		} catch (RuntimeException e) {
			e.printStackTrace();
			JsonResponseDto errorResponse = new JsonResponseDto();
			errorResponse.setStatus(500);
			errorResponse.setMessage(e.getMessage());
			return ResponseEntity.internalServerError().body(errorResponse);
		} catch (UnAuthorizedUserException e) {
			JsonResponseDto errorResponse = JsonResponseDto.unAuthorized(e.getMessage());
			return ResponseEntity.internalServerError().body(errorResponse);
		}
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/downloadPDF")
	public ResponseEntity downloadPDFResource(@RequestBody JsonRequestDto jsonRequest, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			fileService.downloadPDFResource(jsonRequest, request, response);
			return ResponseEntity.ok().build();
		} catch (RuntimeException e) {
			e.printStackTrace();
			JsonResponseDto errorResponse = new JsonResponseDto();
			errorResponse.setStatus(500);
			errorResponse.setMessage(e.getMessage());
			return ResponseEntity.internalServerError().body(errorResponse);
		} catch (UnAuthorizedUserException e) {
			JsonResponseDto errorResponse = JsonResponseDto.unAuthorized(e.getMessage());
			return ResponseEntity.internalServerError().body(errorResponse);
		}
	}

}
