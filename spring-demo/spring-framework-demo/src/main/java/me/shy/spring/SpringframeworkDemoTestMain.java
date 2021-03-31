package me.shy.spring;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import me.shy.spring.aop.AOPDemo;
import me.shy.spring.ioc.InjectionDemoBean;
import me.shy.spring.ioc.People;
import me.shy.spring.ioc.Student;
import me.shy.spring.mybatis.pojo.User;
import me.shy.spring.mybatis.service.UserService;
import me.shy.spring.mybatis.service.impl.UserServiceImpl;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: Spring Framework
 *
 * 整个spring framework主要包含三个技术：
 * 1.Ioc/DI
 * 2.AOP
 * 3.声明式事务
 *
 * 一.Ioc/DI
 * Ioc => Invertion of control => 控制反转
 * DI  => Dependencies Injection => 依赖注入
 *
 * spring framework中，Ioc与DI是一个意思，具体是指对象不再由程序员自己通过new关键字来创建，
 * 而是交由spring框架来进行管理，我们直接用就行。
 *
 * Ioc/DI使用方式：
 * 1.导入Spring framework相关的jar包，如bean，core，context等；
 * 2.在classpath下创建xml配置文件，路径和名称随便取，一般就取作applicationContext.xml，这样做
 *   是为了让我们记住，在spring框架中，AppliactionContext接口就是用来管理这些bean（即Java对象）
 *   的容器；
 * 3.在applicationContext.xml配置文件中使用bean标签来告诉spring框架管理我们得类，详见ApplicationContext.xml
 *   配置说明；
 * 4.使用new ClassPathXmlApplicationContext("配置文件路径")来创建ApplicationContext对象；
 * 5.通过ApplicationContext对象的getBean(id, class)就能获取到该id对应类得实例对象；
 *
 * spring框架创建bean对象有三种方式：
 * 1.构造函数创建
 *   默认情况下，spring在实例化这些bean对象时使用得是无参得构造函数。但是可以通过配置来调用有参构造函数。
 * 2.实例工厂（需要先创建工厂才能生产）
 * 3.静态工厂（不需要创建工厂就能创建）
 *
 *
 * 二.AOP
 *   AOP => Aspect Oriented Programming => 面向切面编程
 *   正常程序执行流程都是纵向的，面向切面编程的思想是在某些方法的前面或者后面添加一些通知（前置通知、后置通知或者环绕通知）形成
 *   一个横切面的过程。面向切面编程可以在不改变原有方法的基础上扩充逻辑，具有很好的扩展性。底层实现采用了Java的动态代理模式。
 *
 *   1.常用概念
 *     原有功能（方法）：切点，Point
 *     前置通知：在切点前执行的功能，before advice
 *     后置通知：在切点后执行的功能，after advice
 *     环绕通知：就是前置与后置通知的结合体，around advice
 *     异常通知：切点执行抛出异常，throws advice
 *     切面：所以上面的总称，aspect
 *     织入：把切面嵌入原有功能的过程
 *
 *   2.spring提供了2中实现AOP的方式：
 *     默认spring AOP都是基于JDK的方式实现的。除了基于注解的AspectJ方式需要指定cglib方式。
 *     Schema-Based：
 *     每个通知都需要实现相应的接口或类（前置通知需要实现MethodBefore接口，后置通知实现AfterRuturning接口）；
 *     配置方式是在<aop:config>的子标签<aop:advisor>中配置；
 *     AspectJ：
 *     每个通知不需要实现相应的接口或类，配置方式是在<aop:config>的子标签<aop:aspect>中配置。
 *
 *   3.spring注解使用：在配置文件中配置:
 *         <context:component-scan base-package="me.shy.demo.framework.*"></context:component-scan>
 *         <!-- proxy-target-class为true时表示使用cglib的动态代理实现，为false时表示使用JDK的动态代理技术实现，使用注解时必须开启-->
 *         <aop:aspectj-autoproxy proxy-target-class="true"></aop:aspectj-autoproxy>
 *
 * 三.声明式式事务
 *    编程式事务：由程序员手动控制的事务；
 *    声明式事务：事务控制代码已经由框架（如spring）写好，程序员只需要声明出哪些方法需要有事务以及如何控制事务。
 *    spring声明式事务都是针对服务层实现类的，事务管理器基于通知的（aop/advice）；
 *
 * 四.spring中的常用注解
 *    @Complent：创建类对象，相当于配置文件中的<bean>标签；
 *    @Service：与@Complent功能相同，建议写在ServiceImpl类上；
 *    @Repository：与@Complent功能相同，建议写在数据访问层类上；
 *    @Controller：与@Complent功能相同，建议写在控制器类上；
 *    @Resource：注入对象，是JDK提供的，默认按照byName策略注入，如果没有名称对象，就按照ByType注入，不需要Seter/Getter方法；
 *    @Aotuwired：注入对象，是spring的注解，默认按照byType策略注入，不需要Seter/Getter方法；
 *    @Value：获取properties文件中的值并赋值给类的属性；
 *    @Pointcut：定义切点；
 *    @Before：定义前置通知；
 *    @After：定义后置通知，无论是否抛出异常都会执行；
 *    @AfterReturning：定义后置通知，只有正常执行才会执行；
 *    @AfterThrowing：定义异常通知；
 *    @Around：定义环绕通知；
 *
 */
public class SpringframeworkDemoTestMain {
    public static void main(String[] args) throws InterruptedException {
        // 实例化ApplicationContext
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        System.out.println("*****************************************************************");

        // iocTest(context);
        // aopTest(context);
        txTest(context);

        System.out.println("*****************************************************************");
        // 获取context管理的所有bean的id
        final String[] beans = context.getBeanDefinitionNames();
        for (String bean : beans) {
            System.out.println("Found bean: " + bean);
        }
        // 获取context管理的所有bean的数量
        int beansCount = context.getBeanDefinitionCount();
        System.out.println("Total managed beans is: " + beansCount);
    }

    public static void txTest(ApplicationContext context) {
        // User tim = new User(20, "Tim", 40);
        User exceptUser = new User(21, "exceptionTest", 1000000000);
        UserService userService = context.getBean("userService", UserServiceImpl.class);
        // userService.addUser(tim);
        userService.addUser(exceptUser);
    }

    public static void aopTest(ApplicationContext context) {
        AOPDemo aopDemo = context.getBean("aopDemo", AOPDemo.class);
        try {
            aopDemo.demo(1, "schema-based");
        } catch (Exception e) {

        }
    }

    public static void iocTest(ApplicationContext context) throws InterruptedException {
        // 默认bean创建
        // 获取bean，只给定一个id参数，返回object，必须强转
        People tony = (People)context.getBean("people");
        // 获取bean，给定id和要返回的类型
        People jerry = context.getBean("people", People.class);
        TimeUnit.SECONDS.sleep(1);
        System.out.println(tony);
        System.out.println(jerry);

        // 有参构造函数创建bean
        Student tom = context.getBean("stu", Student.class);
        System.out.println(tom);

        // 实例工厂创建bean
        People jiony = context.getBean("peopByInstanceFactory", People.class);
        System.out.println(jiony);

        // 静态工厂创建bean
        People mark = context.getBean("peopByStaticFactory", People.class);
        System.out.println(mark);

        // bean属性/依赖注入
        InjectionDemoBean demoBean = context.getBean("injectDemo", InjectionDemoBean.class);
        System.out.println(demoBean);

        // mybatis
        UserService userService = context.getBean("userService", UserServiceImpl.class);
        List<User> users = userService.getAll();
        for (User user : users) {
            System.out.println(user);
        }
    }
}
