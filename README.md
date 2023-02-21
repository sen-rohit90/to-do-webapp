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
  

