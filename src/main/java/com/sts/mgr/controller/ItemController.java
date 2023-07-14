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
import com.sts.mgr.service.ItemService;
import com.sts.mgr.utils.exceptions.UnAuthorizedUserException;

@RestController
@RequestMapping("/item")
public class ItemController {

	@Autowired
	@Qualifier("itemServiceImpl")
	private ItemService itemService;

	@PostMapping("/space")
	public ResponseEntity<JsonResponseDto> createSpace(@RequestBody JsonRequestDto jsonRequest) {
		try {
			JsonResponseDto responseResult = itemService.createSpace(jsonRequest);
			return ResponseEntity.ok(responseResult);
		} catch (RuntimeException e) {
			JsonResponseDto errorResponse = new JsonResponseDto();
			errorResponse.setStatus(500);
			errorResponse.setMessage(e.getMessage());
			return ResponseEntity.internalServerError().body(errorResponse);
		} catch (UnAuthorizedUserException e) {
			JsonResponseDto errorResponse = JsonResponseDto.unAuthorized(e.getMessage());
			return ResponseEntity.internalServerError().body(errorResponse);
		}
	}

	@PostMapping("/folder")
	public ResponseEntity<JsonResponseDto> createFolder(@RequestBody JsonRequestDto jsonRequest) {
		try {
			JsonResponseDto responseResult = itemService.createFolder(jsonRequest);
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

	@PostMapping("/file")
	public ResponseEntity<JsonResponseDto> createFile(@RequestBody JsonRequestDto jsonRequest) {
		try {
			JsonResponseDto responseResult = itemService.createFile(jsonRequest);
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
}
