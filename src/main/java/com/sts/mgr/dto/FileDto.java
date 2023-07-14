package com.sts.mgr.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDto implements Serializable {
	private static final long serialVersionUID = -2922636602168233357L;

	private Integer id;

	private byte[] binary;

	private String base64Binary;

	private ItemDto item;
}
