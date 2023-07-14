package com.sts.mgr.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionGroupDto implements Serializable {

	private static final long serialVersionUID = -5633073022360821650L;

	private Integer id;
	private String groupName;
}
