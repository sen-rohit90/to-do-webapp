# to-do-webapp
A to-do Java application built in Spring Framework, Spring Boot and Hibernate

Step 01 : Creating Sprint Boot Application with Spring Initializr
- Selected Maven
- Selected Spring Boot version - 3.0.2
- Java version - 17
- Add dependencies
  a. Spring web b. Spring DevTools
  
- In eclipse, import the extracted folder / jar. Go to Import -> Existing Maven project -> Browse and Import

Step 02 : First Spring MVC Controls
 - Create SayHelloController.java, mark it as @Controller
 - Add in the below method

        @RequestMapping("say-hello")
        @ResponseBody
        public String sayHello() {
          return "Hello, what's up!";
        }

		    @RequestMapping("say-hello")
		    @ResponseBody
		    public String sayHello() {
		      return "Hello, what's up!";
		    }

  - @RequestMapping is used to map URL with the bean / method
  - @ResponseBody tells that the returning string is a response. Otherwise Spring MVC looks for a view with name "Hello, what's up" - so in 
  order that the Spring MVC understands that the returning string is not a view is by adding the annotation - @ResponseBody

Step 03 : Let's return html response
- Add in the below method
	    
		    @RequestMapping("say-hello-html")
		    @ResponseBody
		    public String sayHelloHtml() {

		      StringBuffer sb = new StringBuffer();
		      sb.append("<html>");
		      sb.append("<head>");
		      sb.append("<title> My first HTML page</title>");
		      sb.append("</head>");

		      sb.append("<body>");
		      sb.append("My first HTML body");
		      sb.append("</body>");
		      sb.append("</html>");

		      return sb.toString();

		    }
- Method is cumbersome. We should explore Views to better maintain UI code. 

Step 04 : Redirect to a JSP
- Add in the below method
        @RequestMapping("say-hello-jsp")
        public String sayHelloJsp() {
          return "sayHello";
        }

- Note that it returns a "sayHello" view. It is a jsp page under WEB-INF/jsp
- This setting is defined in application.properties file as below:
    spring.mvc.view.prefix=/WEB-INF/jsp/
    spring.mvc.view.suffix=.jsp

  This is how we are using the View Resolver

- The jsp file is a simple html file for now. 
- Note, we also need to add the below dependency in the pom.xml file

      <dependency>
        <groupId>org.apache.tomcat.embed</groupId>
        <artifactId>tomcat-embed-jasper</artifactId>
        <scope>provided</scope>
      </dependency>

Step 05: Adding a login controller
- Created a simple controller class named login
- Add the below method
        @RequestMapping("login")
        public String gotoLoginPage() {
          return "login";
        }
- Created a login.jsp page with a basic form element.

Step 06: Use of QueryParam, first look at model annotation
- To use query param, ie., a URL like http://localhost:8080/login?name=Rohit , we need to use @QueryParam in method argument
        @RequestMapping("login")
        public String gotoLoginPage(@RequestParam String name, ModelMap model) {
          model.put("name", name);
          return "login";
        }

- For the login.jsp, if the name argument has to be used, we need to use a "ModelMap"... this is very similar to identityModel.
- Just put the key, value in the modelMap object
- On the jsp page, access this variable with ${name} 

Step 07: Logging using slf4j
- Add details on application properties file
        logging.level.com.todo.todowebapp.login=debug

- And on class, create an instance of Logger class
        private Logger logger = LoggerFactory.getLogger(getClass());

- Use logger as per need. 

- Future mini project idea: use log4j2 as the logger

Step 08: Add the login page,etc
- Using the logic we have already seen above, let's create a simple login page
- With the help of RequestMapping, lets define the function of GET and POST methods; GET for login page and POST for submitting credentials

Step 09: Here on our project begins... Creating Todo and TodoService
- For ToDo class
    Things we will need... 
      private int id;
      private String username;
      private String description;
      private LocalDate targetData;
      private boolean done;

  Lets add getters, setters, toString and constructors for the same. 

- Initially we will create a static list of todos... we will use ToDoService class for it. Marked the ToDoService class as @Service
    Added as so:
      static {
        todos.add(new ToDo(1, "in28mins", "Learn AWS", LocalDate.now().plusYears(1), false));
        todos.add(new ToDo(1, "in28mins", "Learn DevOps", LocalDate.now().plusYears(2), false));
        todos.add(new ToDo(1, "in28mins", "Learn Full Stack", LocalDate.now().plusYears(3), false));

  	  }

- And create a method -> List<ToDo> findByUsername(String username) - to fetch list of all todos for a user. 
- Next we need to create the controller class... which is ToDoController and a jsp page to display our response.
    Using the concepts we have learnt above, let's makr our class @Controller and map our bean @RequestMapping with the jsp page. We create a ToDoController oonstructor to autowire it in.

        @RequestMapping("list-todos")
        public String listAllTodos(ModelMap model) {
          List todos = toDoService.findByUsername("in28mins");
          model.put("todos", todos);
          return "listTodos";

        }

- Create list-todos.jsp , like so:
        <html>
          <title>List ToDos Page</title>
          <head>
          </head>
          <body>
            <div>Welcome ${name} .</div>
            <div>Your to dos are :  ${todos} .</div>
          </body>
          </form>
        </html>

- Via SessionAttribute, persisted the variable values to multiple web pages (Session Scope). More on this later.

Step 10: Using JSTL 
- We see on the response html, the following - 

  Welcome .
  Your to dos are : [ToDo [id=1, username=in28mins, description=Learn AWS, targetData=2024-02-21, done=false], ToDo [id=1, username=in28mins, description=Learn DevOps, targetData=2025-02-21, done=false], ToDo [id=1, username=in28mins, description=Learn Full Stack, targetData=2026-02-21, done=false]] .

  The ${todos} has three rows with columns as id, username, description, targetDate, done. 

  Making use of JSTL for each method, lets display this into a table. 

- Add a header to import jstl uri
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

- Add a table with c:forEach jstl tag to scan through the rows and display on table like so:
    <table>
      <thead></thead>
      <tbody>
        <tr>
          <th>id</th>
          <th>Description</th>
          <th>Target Date</th>
          <th>is Done?</th>
        </tr>
        <c:forEach items="${todos}" var="todo">
          <tr>
            <td>${todo.id}</td>
            <td>${todo.description}</td>
            <td>${todo.targetDate}</td>
            <td>${todo.done}</td>
          </tr>
        </c:forEach>
      </tbody>
    </table>


Step 11: Adding css, js and jquery 
  - Making use of the pom.xml we will add the css, js, jquery min library
  - Remember css is added at the head as we want to load it at the start
      <head>
        <link href="webjars/bootstrap/5.1.3/css/bootstrap.min.css"
        rel="stylesheet">
      </head>

  - And the below towards end of </body> tag
      	<script src="webjars/bootstrap/5.1.3/js/bootstrap.min.js"></script>
	      <script src="webjars/jquery/3.6.0/js/jquery.min.js"></script>

  - Adding these will change how the response apprears. 
  - Now we want to add new todos... for the same, added a button on our todo list page. 
        <a href="add-todo" class="btn btn-success">Add Todo</a>

  - On clicking this, we want to send a GET request and display a new "Add todo" page... so adding a new bean in toDoController class
        @RequestMapping(value="add-todo", method = RequestMethod.GET)
        public String showNewTodoPage() {
          return "todo";
        }
  
  - Now we add a POST method to take description as input
        <form method="post">
          Description: <input type="text" name="description"> 
          <input type="Submit" class="btn btn-success">
        </form>

  - And to the controller class , add the below bean
        @RequestMapping(value="add-todo", method = RequestMethod.POST)
        public String addNewTodo(@RequestParam String description, ModelMap model) {
          String usernmae = (String)model.get("name");
          toDoService.addTodo(usernmae, description, LocalDate.now().plusYears(1), false);
          return "redirect:list-todos";
        }

    Using redirect: to re-use another bean mapped as "list-todos"

  - And to add a todo created a method in ToDoService class as:
        public void addTodo(String usernmae, String description, LocalDate targetDate, boolean done) {
          ToDo todo = new ToDo(++todosCount, usernmae, description, targetDate, done);
          todos.add(todo);
        }
  
Step 12: Adding springboot starter validation
  - Spring boot offers validation support to check for various form field values. Added by adding the entry to pom. 
      <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
      </dependency>

  - In order to use form validation, imported as below
      <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

    on the todo.jsp page.

  - We want to easily link the form fields with java bean - it is made possible by using the form tag along with modelAttribute tag. modelAttribute tag is 
    put in here as todo. Using path=<name of the bean> , we can map the object.

      <form:form method="post" modelAttribute="todo"> 
          Description: <input type="text" name="description" required="required" path="description"> 
        <form:input type="hidden" path="id"/> 
        <form:input type="hidden" path="done"/> 
        <input type="Submit" class="btn btn-success">
      </form:form>

  - We pass in the todo attribute to modelMap in our showNewTodoPage method
        @RequestMapping(value="add-todo", method = RequestMethod.GET) 
        public String showNewTodoPage(ModelMap model) {
          String usernmae = (String)model.get("name");
          ToDo todo = new ToDo(0, usernmae, "", LocalDate.now().plusYears(1), false);
          model.put("todo", todo);
          return "todo";
        } 

Step 13: Validation using command bean
  - On our toDo class, we can mark variables as Size to check for validation
        @Size(min=10, message="Enter atleast 10 characters")
	      private String description;

  - And on the toDo jsp we can add form:errors tag to link this
        <form:form method="post" modelAttribute="todo">
			Description: <form:input type="text" name="description"
				required="required" path="description" />
			<form:errors path="description" cssClass="text-warning"/>
			<form:input type="hidden" path="id" />
			<form:input type="hidden" path="done" />
			<input type="Submit" class="btn btn-success">
		  </form:form>

  - And on our getter method, we will use @Valid and @ModelAttribute to display the message as the form values is being input
        @RequestMapping(value="add-todo", method = RequestMethod.POST)
        public String addNewTodo(ModelMap model, @Valid @ModelAttribute("todo") ToDo todo, BindingResult result) {
          
          if (result.hasErrors()) {
            return "todo";
          }
          String usernmae = (String)model.get("name");
          toDoService.addTodo(usernmae, todo.getDescription(), LocalDate.now().plusYears(1), false);
          return "redirect:list-todos";
        }

    result will store any errors we have defined with @Size 

  - Now let's add delete button; add deleteTodo method in toDoService bean
        public void deleteTodo(int id) {
          Predicate<? super ToDo> predicate = todo -> todo.getId() == id;
          todos.removeIf(predicate);
        }

        if the id incoming is equal to todo.getId(), only then delete items from the list. 

  - Add a new bean to the controller
        @RequestMapping("delete-todo")
        public String deleteTodo(@RequestParam int id) {
          
          toDoService.deleteTodo(id);
          return "redirect:list-todos";

        }

  - And the below jsp code on listTodos.jsp
        <c:forEach items="${todos}" var="todo">
					<tr>
						<td>${todo.id}</td>
						<td>${todo.description}</td>
						<td>${todo.targetDate}</td>
						<td>${todo.done}</td>
						<td> <a href="delete-todo?id=${todo.id}" class="btn btn-warning">DELETE</a> </td>
					</tr>
				</c:forEach>

  Step 14: Adding update feature
  - On todo controller, added a new method for Update function
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

    No new concepts here. 

  - Also added another method for GET
          @RequestMapping(value="update-todo", method = RequestMethod.GET)
          public String showUpdateTodoPage(@RequestParam int id, ModelMap model) {
            ToDo todo = toDoService.findById(id);
            model.addAttribute("todo", todo);
            return "todo";

          }

  - And on todoService class added the below
          public void updateTodo(@Valid ToDo todo) {
            // TODO Auto-generated method stub
            deleteTodo(todo.getId());
            todos.add(todo);
          }
    First deleting it and then adding new one with updated value

  - On todo.jsp, using datepicker min css,js added the option of datepicker like so
      - Link reference as below:
          <link
            href="webjars/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.standalone.min.css"
            rel="stylesheet">
          
      - Fieldsets added
                <fieldset class="mb-3">
                  <form:label path="description">Description</form:label>
                  <form:input type="text" path="description" required="required" />
                  <form:errors path="description" cssClass="text-warning" />
                </fieldset>
                <fieldset class="mb-3">
                  <form:label path="targetDate">Target Date</form:label>
                  <form:input type="text" path="targetDate" required="required" />
                  <form:errors path="targetDate" cssClass="text-warning" />
                </fieldset>

                
        - added js files and js code    
            <script
              src="webjars/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>
            <script type="text/javascript">
              $('#targetDate').datepicker({
                format : 'yyyy-mm-dd'
              });
            </script>

    - On inspecting the page source, found an error in import of jquery file, updated it correctly 
          <script src="webjars/jquery/3.6.0/jquery.min.js "></script>

