package com.teamtreehouse.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.teamtreehouse.model.Task;

@Repository
public interface TaskDao extends CrudRepository<Task, Long>{

	Task findOne(Long id);
	
	
	
	//tüm task'lar değil sadece login olmuş user'ın task'ları görüntülenmek isteniyor.aşağıdaki query'de principal'ın
	//görüntülenmesi için evaluation context extension configure edilmelidir authentication data'nın expose edilmesi için.
	//Security config class'ında bu işlem yapılmıştır.
	
	@Query("select t from Task t where t.user.id=:#{principal.id}")
	List<Task> findAll();

}
