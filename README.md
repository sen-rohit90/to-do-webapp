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
  - @RequestMapping is used to map URL with the bean / method
  - @ResponseBody tells that the returning string is a response. Otherwise Spring MVC looks for a view with name "Hello, what's up" - so in 
  order that the Spring MVC understands that the returning string is not a view is by adding the annotation - @ResponseBody
