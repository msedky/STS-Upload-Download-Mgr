package com.sts.mgr.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JsonResponseDto implements Serializable {

	private static final long serialVersionUID = 9070220864573842682L;

	private int status;

	private String message;

	private Object result;

	public static JsonResponseDto success(Object result) {
		JsonResponseDto jsonResponseDto = new JsonResponseDto(200, "Success", result);
		return jsonResponseDto;
	}

	public static JsonResponseDto success(String message, Object result) {
		JsonResponseDto jsonResponseDto = new JsonResponseDto(200, message, result);
		return jsonResponseDto;
	}

	public static JsonResponseDto unAuthorized() {
		JsonResponseDto jsonResponseDto = new JsonResponseDto(401, "UnAuthorized", null);
		return jsonResponseDto;
	}

	public static JsonResponseDto unAuthorized(String message) {
		JsonResponseDto jsonResponseDto = new JsonResponseDto(401, message, null);
		return jsonResponseDto;
	}
}
