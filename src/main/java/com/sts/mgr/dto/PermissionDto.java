package com.sts.mgr.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionDto implements Serializable {

	private static final long serialVersionUID = -5588938192304896910L;

	private Integer id;
	private String userEmail;
	private PermissionGroupDto permissionGroup;
}
