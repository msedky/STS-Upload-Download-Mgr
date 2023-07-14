package com.sts.mgr.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto implements Serializable {

	private static final long serialVersionUID = 3481206480749390284L;

	private Integer id;
	private String type;
	private String name;
	private PermissionGroupDto permissionGroup;
}
