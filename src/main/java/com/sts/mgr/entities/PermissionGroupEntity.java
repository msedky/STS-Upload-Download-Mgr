package com.sts.mgr.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(schema = "public", name = "permission_groups")
public class PermissionGroupEntity implements Serializable {

	private static final long serialVersionUID = 5330518436124214963L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERMISSION_GROUP_GEN_SEQ")
	@SequenceGenerator(name = "PERMISSION_GROUP_GEN_SEQ", sequenceName = "permission_group_seq", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@Column(name = "group_name")
	private String groupName;

}