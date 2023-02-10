package com.todo.todowebapp.todo;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("name")
public class ToDoController {

	private ToDoService toDoService;

	public ToDoController(ToDoService toDoService) {
		super();
		this.toDoService = toDoService;
	}

	// list-todos
	@RequestMapping("list-todos")
	public String listAllTodos(ModelMap model) {
		List todos = toDoService.findByUsername("in28mins");
		model.put("todos", todos);
		return "listTodos";

	}
}
