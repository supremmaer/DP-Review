
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Folder;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Integer> {

	//select f from Actor a join a.folders f where a.id = 379 and f.name = 'Root';

	@Query("select f from Actor a join a.folders f where a.id = ?1")
	Collection<Folder> findByActorID(int id);

	@Query("select f from Actor a join a.folders f where a.id = ?1 and f.name = ?2")
	Folder findFolderByActorIDAndName(int id, String name);

	@Query("select f from Folder f where f.name = ?1")
	Collection<Folder> findByName(String name);

	@Query("select f from Folder f join f.messages m where m.id=?1 and m member of f.messages")
	Collection<Folder> findByMessageID(int id);

	@Query("select f from Folder f where f.id = ?1")
	Folder findById(Integer folderId);
}
