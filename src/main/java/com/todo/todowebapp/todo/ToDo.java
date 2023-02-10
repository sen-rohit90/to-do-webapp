package com.todo.todowebapp.todo;

import java.time.LocalDate;

public class ToDo {

	private int id;
	private String username;
	private String description;
	private LocalDate targetData;
	private boolean done;

	public ToDo(int id, String username, String description, LocalDate targetData, boolean done) {
		super();
		this.id = id;
		this.username = username;
		this.description = description;
		this.targetData = targetData;
		this.done = done;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getTargetData() {
		return targetData;
	}

	public void setTargetData(LocalDate targetData) {
		this.targetData = targetData;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	@Override
	public String toString() {
		return "ToDo [id=" + id + ", username=" + username + ", description=" + description + ", targetData="
				+ targetData + ", done=" + done + "]";
	}

}
