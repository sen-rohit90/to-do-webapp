package com.todo.todowebapp.todo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.validation.Valid;

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
		String username = "in28mins";
		List todos = toDoService.findByUsername(username);
		model.put("todos", todos);
		return "listTodos";

	}
	
	@RequestMapping(value="add-todo", method = RequestMethod.GET)
	public String showNewTodoPage(ModelMap model) {
		String usernmae = (String)model.get("name");
		ToDo todo = new ToDo(0, usernmae, "", LocalDate.now().plusYears(1), false);
		model.addAttribute("todo", todo);
		return "todo";
	}
	
	@RequestMapping(value="add-todo", method = RequestMethod.POST)
	public String addNewTodo(ModelMap model, @Valid @ModelAttribute("todo") ToDo todo, BindingResult result) {
		
		if (result.hasErrors()) {
			return "todo";
		}
		String usernmae = (String)model.get("name");
		toDoService.addTodo(usernmae, todo.getDescription(), todo.getTargetDate(), false);
		return "redirect:list-todos";
	}
	
	@RequestMapping("delete-todo")
	public String deleteTodo(@RequestParam int id) {
		
		toDoService.deleteTodo(id);
		return "redirect:list-todos";

	}
	
	@RequestMapping(value="update-todo", method = RequestMethod.GET)
	public String showUpdateTodoPage(@RequestParam int id, ModelMap model) {
		ToDo todo = toDoService.findById(id);
		model.addAttribute("todo", todo);
		return "todo";

	}
	
	@RequestMapping(value="update-todo", method = RequestMethod.POST)
	public String updateTodo(ModelMap model, @Valid @ModelAttribute("todo") ToDo todo, BindingResult result) {
		
		if (result.hasErrors()) {
			return "todo";
		}
		String usernmae = (String)model.get("name");	
		todo.setUsername(usernmae);
		
		toDoService.updateTodo(todo);
		return "redirect:list-todos";
	}
	
	
}
