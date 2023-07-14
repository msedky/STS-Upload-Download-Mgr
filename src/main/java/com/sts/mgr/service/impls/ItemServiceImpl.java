package com.sts.mgr.service.impls;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sts.mgr.dto.FileDto;
import com.sts.mgr.dto.ItemDto;
import com.sts.mgr.dto.PermissionGroupDto;
import com.sts.mgr.entities.FileEntity;
import com.sts.mgr.entities.ItemEntity;
import com.sts.mgr.entities.PermissionEntity;
import com.sts.mgr.repoistory.FileRepository;
import com.sts.mgr.repoistory.ItemRepository;
import com.sts.mgr.repoistory.PermissionRepository;
import com.sts.mgr.request.JsonRequestDto;
import com.sts.mgr.response.JsonResponseDto;
import com.sts.mgr.service.ItemService;
import com.sts.mgr.utils.exceptions.UnAuthorizedUserException;

@Component("itemServiceImpl")
public class ItemServiceImpl implements ItemService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemServiceImpl.class);

	@Autowired
	private FileRepository fileRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private PermissionRepository permissionRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Value("${base-path}")
	private String basePath;

	@Value("${space.default-name}")
	private String spaceDefaultName;

	@Value("${folder.default-name}")
	private String folderDefaultName;

	@Value("${file.default-name}")
	private String fileDefaultName;

	@Override
	public JsonResponseDto createSpace(JsonRequestDto jsonRequest) throws UnAuthorizedUserException {
		/* start validation */
		PermissionEntity permissionEntity = permissionRepository.findById(jsonRequest.getUserId()).orElse(null);
		if (permissionEntity == null) {
			throw new RuntimeException("User with id '" + jsonRequest.getUserId() + "' is not existed");
		}
		if (!permissionEntity.getPermissionLevel().equalsIgnoreCase("EDIT")
				&& !permissionEntity.getPermissionGroupEntity().equals("VIEW")) {
			throw new UnAuthorizedUserException(
					"User with id '" + jsonRequest.getUserId() + " ' is not authorized to create space");
		}
		/* end validation */

		/* creating the folder */
		String filePath = basePath + spaceDefaultName + "/";
		Path path = Paths.get(filePath);
		if (Files.exists(path)) {
			throw new RuntimeException("Space already existed");
		}
		try {
			Files.createDirectories(path);
			LOGGER.info("space [" + filePath + "] has been created");
		} catch (IOException e) {
			LOGGER.error("IO Exception is thrown when attempt to create space", e);
			throw new RuntimeException(e);
		}

		/* saving the itemEntity */
		ItemEntity itemEntity = new ItemEntity();
		itemEntity.setType("Space");
		itemEntity.setPermissionGroupEntity(permissionEntity.getPermissionGroupEntity());
		itemEntity.setName(filePath);
		itemRepository.save(itemEntity);

		ItemDto itemDto = new ItemDto();
		BeanUtils.copyProperties(itemEntity, itemDto);

		PermissionGroupDto permissionGroupDto = new PermissionGroupDto();
		BeanUtils.copyProperties(permissionEntity.getPermissionGroupEntity(), permissionGroupDto);
		itemDto.setPermissionGroup(permissionGroupDto);

		return JsonResponseDto.success(itemDto);

	}

	@Override
	public JsonResponseDto createFolder(JsonRequestDto jsonRequest) throws UnAuthorizedUserException {
		/* start validation */
		PermissionEntity permissionEntity = permissionRepository.findById(jsonRequest.getUserId()).orElse(null);
		if (permissionEntity == null) {
			throw new RuntimeException("User with id '" + jsonRequest.getUserId() + "' is not existed");
		}
		if (!permissionEntity.getPermissionLevel().equalsIgnoreCase("EDIT")) {
			throw new UnAuthorizedUserException(
					"User with id '" + jsonRequest.getUserId() + " ' is not authorized to create folder");
		}
		/* end validation */

		/* creating the folder */
		String filePath = basePath + spaceDefaultName + "/" + folderDefaultName + "/";
		Path path = Paths.get(filePath);
		if (Files.exists(path)) {
			throw new RuntimeException("Folder already existed");
		}
		try {
			Files.createDirectories(path);
			LOGGER.info("folder [" + filePath + "] has been created");
		} catch (IOException e) {
			LOGGER.error("IO Exception is thrown when attempt to create folder", e);
			throw new RuntimeException(e);
		}

		/* saving the itemEntity */
		ItemEntity itemEntity = new ItemEntity();
		itemEntity.setType("Folder");
		itemEntity.setPermissionGroupEntity(permissionEntity.getPermissionGroupEntity());
		itemEntity.setName(filePath);
		itemRepository.save(itemEntity);

		ItemDto itemDto = new ItemDto();
		BeanUtils.copyProperties(itemEntity, itemDto);

		PermissionGroupDto permissionGroupDto = new PermissionGroupDto();
		BeanUtils.copyProperties(permissionEntity.getPermissionGroupEntity(), permissionGroupDto);
		itemDto.setPermissionGroup(permissionGroupDto);

		return JsonResponseDto.success(itemDto);
	}

	@Override
	public JsonResponseDto createFile(JsonRequestDto jsonRequest) throws UnAuthorizedUserException {
		/* start validation */
		PermissionEntity permissionEntity = permissionRepository.findById(jsonRequest.getUserId()).orElse(null);
		if (permissionEntity == null) {
			throw new RuntimeException("User with id '" + jsonRequest.getUserId() + "' is not existed");
		}
		if (!permissionEntity.getPermissionLevel().equalsIgnoreCase("EDIT")) {
			throw new UnAuthorizedUserException(
					"User with id '" + jsonRequest.getUserId() + " ' is not authorized to create file");
		}
		/* end validation */

		/* started creating the file */
		String filePath = basePath + spaceDefaultName + "/" + folderDefaultName + "/" + fileDefaultName;
		Path path = Paths.get(filePath);
		if (Files.exists(path)) {
			throw new RuntimeException("File already existed");
		}

		FileDto fileDto = objectMapper.convertValue(jsonRequest.getObject(), FileDto.class);

		byte[] decodedBytes = Base64.getDecoder().decode(fileDto.getBase64Binary());
		fileDto.setBinary(decodedBytes);

		File file = new File(filePath);

		try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
			fileOutputStream.write(fileDto.getBinary());
			fileOutputStream.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		LOGGER.info("file [" + filePath + "] has been created");
		/* ended creating the file */

		/* start saving file info to db */
		ItemEntity itemEntity = new ItemEntity();
		itemEntity.setType("File");
		itemEntity.setName(filePath);
		itemEntity.setPermissionGroupEntity(permissionEntity.getPermissionGroupEntity());
		itemRepository.save(itemEntity);

		FileEntity fileEntity = new FileEntity();
		BeanUtils.copyProperties(fileDto, fileEntity);
		fileEntity.setItemEntity(itemEntity);

		fileRepository.save(fileEntity);
		/* end saving file info to db */

		BeanUtils.copyProperties(fileEntity, fileDto);
		ItemDto itemDto = new ItemDto();
		BeanUtils.copyProperties(fileEntity.getItemEntity(), itemDto);
		fileDto.setItem(itemDto);
		PermissionGroupDto permissionGroupDto = new PermissionGroupDto();
		BeanUtils.copyProperties(fileEntity.getItemEntity().getPermissionGroupEntity(), permissionGroupDto);
		itemDto.setPermissionGroup(permissionGroupDto);
		return JsonResponseDto.success(fileDto);
	}

}
