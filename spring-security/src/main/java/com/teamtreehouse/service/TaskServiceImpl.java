package com.teamtreehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teamtreehouse.dao.TaskDao;
import com.teamtreehouse.model.Task;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskDao taskDao;
	
	@Override
	public Iterable<Task> findAll() {
		return taskDao.findAll();
	}

	@Override
	public Task findOne(Long id) {
		return taskDao.findOne(id);
	}

	@Override
	public void toggleComplete(Long id) {
		Task task=taskDao.findOne(id);
		task.setComplete(!task.isComplete());
		taskDao.save(task);
	}

	@Override
	public void save(Task task) {
		taskDao.save(task);
	}

}
