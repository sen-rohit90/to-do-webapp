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

Step 09: Creating Todo and TodoService
- Created tthe respective classes
- Via SessionAttribute, persisted the variable values to multiple web pages (Session Scope)