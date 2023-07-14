package com.sts.mgr.request;

import java.io.Serializable;

import lombok.Data;

@Data
public class JsonRequestDto implements Serializable {

	private static final long serialVersionUID = 7127317262960177287L;

	private Integer userId;

	private Object object;
}
