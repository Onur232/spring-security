package com.teamtreehouse.service;

import com.teamtreehouse.model.Task;

public interface TaskService {
	
	public Iterable<Task> findAll();
	public Task findOne(Long id);
	public void toggleComplete(Long id);
	public void save(Task task);

}
