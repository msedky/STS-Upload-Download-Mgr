package com.sts.mgr.service.impls;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sts.mgr.dto.FileMetaDataDto;
import com.sts.mgr.dto.ItemDto;
import com.sts.mgr.entities.PermissionEntity;
import com.sts.mgr.repoistory.FileRepository;
import com.sts.mgr.repoistory.PermissionRepository;
import com.sts.mgr.request.JsonRequestDto;
import com.sts.mgr.response.JsonResponseDto;
import com.sts.mgr.service.FileService;
import com.sts.mgr.utils.exceptions.UnAuthorizedUserException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("fileServiceImpl")
public class FileServiceImpl implements FileService {

	@Autowired
	private FileRepository fileRepository;

	@Autowired
	private PermissionRepository permissionRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public JsonResponseDto getFileMetadata(JsonRequestDto jsonRequest) throws UnAuthorizedUserException {
		/* start validation */
		PermissionEntity permissionEntity = permissionRepository.findById(jsonRequest.getUserId()).orElse(null);
		if (permissionEntity == null) {
			throw new RuntimeException("User with id '" + jsonRequest.getUserId() + "' is not existed");
		}
		ItemDto itemDto = objectMapper.convertValue(jsonRequest.getObject(), ItemDto.class);
		boolean doesUserHaveAccessToFile = fileRepository.doesUserHaveAccessToFile(jsonRequest.getUserId(),
				itemDto.getName());
		if (!doesUserHaveAccessToFile) {
			throw new UnAuthorizedUserException(
					"User with id '" + jsonRequest.getUserId() + " ' is not authorized to access this file");

		}
		/* end validation */

		/* getting file Metadata */
		Path path = Paths.get(itemDto.getName());
		try {
			BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
			File file = new File(itemDto.getName());

			FileMetaDataDto fileMetaDataDto = new FileMetaDataDto();
			fileMetaDataDto.setName(file.getName());
			fileMetaDataDto.setLocation(file.getParent());
			fileMetaDataDto.setSize(attr.size());
			fileMetaDataDto.setCreationTime(attr.creationTime().toString());
			fileMetaDataDto.setLastAccessTime(attr.lastAccessTime().toString());
			fileMetaDataDto.setLastModifiedTime(attr.lastModifiedTime().toString());
			fileMetaDataDto.setIsRegularFile(attr.isRegularFile());
			fileMetaDataDto.setIsDirectory(attr.isDirectory());
			fileMetaDataDto.setIsSymbolicLink(attr.isSymbolicLink());
			fileMetaDataDto.setIsOther(attr.isOther());

			fileMetaDataDto.setIsHidden(file.isHidden());
			fileMetaDataDto.setCanRead(file.canRead());
			fileMetaDataDto.setCanWrite(file.canWrite());

			return JsonResponseDto.success(fileMetaDataDto);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void downloadPDFResource(JsonRequestDto jsonRequest, HttpServletRequest request,
			HttpServletResponse response) throws UnAuthorizedUserException {
		/* start validation */
		PermissionEntity permissionEntity = permissionRepository.findById(jsonRequest.getUserId()).orElse(null);
		if (permissionEntity == null) {
			throw new RuntimeException("User with id '" + jsonRequest.getUserId() + "' is not existed");
		}
		ItemDto itemDto = objectMapper.convertValue(jsonRequest.getObject(), ItemDto.class);
		boolean doesUserHaveAccessToFile = fileRepository.doesUserHaveAccessToFile(jsonRequest.getUserId(),
				itemDto.getName());
		if (!doesUserHaveAccessToFile) {
			throw new UnAuthorizedUserException(
					"User with id '" + jsonRequest.getUserId() + " ' is not authorized to access this file");

		}
		/* end validation */

		File file = new File(itemDto.getName());
		if (file.exists()) {

			// get the mimetype
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				// unknown mimetype so set the mimetype to application/octet-stream
				mimeType = "application/octet-stream";
			}

			response.setContentType(mimeType);

			/**
			 * In a regular HTTP response, the Content-Disposition response header is a
			 * header indicating if the content is expected to be displayed inline in the
			 * browser, that is, as a Web page or as part of a Web page, or as an
			 * attachment, that is downloaded and saved locally.
			 * 
			 */

			/**
			 * Here we have mentioned it to show inline
			 */
			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));

			// Here we have mentioned it to show as attachment
			// response.setHeader("Content-Disposition", String.format("attachment;
			// filename=\"" + file.getName() + "\""));

			response.setContentLength((int) file.length());

			try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
				FileCopyUtils.copy(inputStream, response.getOutputStream());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}
	}

}
