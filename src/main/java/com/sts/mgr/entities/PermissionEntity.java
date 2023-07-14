package com.sts.mgr.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(schema = "public", name = "permissions")
public class PermissionEntity implements Serializable {

	private static final long serialVersionUID = -1562226825676004259L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERMISSION_GEN_SEQ")
	@SequenceGenerator(name = "PERMISSION_GEN_SEQ", sequenceName = "permission_seq", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@Column(name = "user_email")
	private String userEmail;

	@Column(name = "permission_level")
	private String permissionLevel;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "group_id")
	private PermissionGroupEntity permissionGroupEntity;
}
