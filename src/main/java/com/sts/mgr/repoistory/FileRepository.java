package com.sts.mgr.repoistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sts.mgr.entities.FileEntity;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Integer> {

	@Query(nativeQuery = true, value = "SELECT EXISTS (SELECT f.id FROM public.files f LEFT JOIN public.item it on f.item_id = it.id LEFT JOIN public.permission_groups pg  ON it.permission_group_id = pg.id LEFT JOIN public.permissions p ON pg.id=p.group_id WHERE p.id = ?1 AND it.name =?2)")
	boolean doesUserHaveAccessToFile(Integer userId, String fileName);

}