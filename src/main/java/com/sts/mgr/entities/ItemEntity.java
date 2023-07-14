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
@Table(schema = "public", name = "item")
public class ItemEntity implements Serializable {

	private static final long serialVersionUID = -9015036999586872720L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEM_GEN_SEQ")
	@SequenceGenerator(name = "ITEM_GEN_SEQ", sequenceName = "item_seq", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@Column(name = "type")
	private String type;

	@Column(name = "name")
	private String name;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "permission_group_id")
	private PermissionGroupEntity permissionGroupEntity;
}
