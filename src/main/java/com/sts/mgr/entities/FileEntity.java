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
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(schema = "public", name = "files")
public class FileEntity implements Serializable {

	private static final long serialVersionUID = -733994301721901588L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FILE_GEN_SEQ")
	@SequenceGenerator(name = "FILE_GEN_SEQ", sequenceName = "file_seq", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@Column(name = "\"binary\"")
	private byte[] binary;

	@Transient
	private String base64Binary;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "item_id")
	private ItemEntity itemEntity;
}
