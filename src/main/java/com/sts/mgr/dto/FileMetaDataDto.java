package com.sts.mgr.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class FileMetaDataDto implements Serializable {

	private static final long serialVersionUID = 3667797898837264017L;

	private String name;
	private String location;
	private Long size;
	private String creationTime;
	private String lastModifiedTime;
	private String lastAccessTime;
	private Boolean isRegularFile;
	private Boolean isDirectory;
	private Boolean isSymbolicLink;
	private Boolean isOther;
	private Boolean isHidden;
	private Boolean canRead;
	private Boolean canWrite;
}
