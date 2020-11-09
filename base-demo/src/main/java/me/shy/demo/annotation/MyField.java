/**
 * @Date        : 2020-10-18 20:54:40
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description :
 *
 *  * 什么是注解：
 * Java注解又称Java标注，是JDK5.0版本开始支持加入源代码的特殊语法元数据。
 * Java语言中的类、方法、变量、参数和包等都可以被标注。和Javadoc不同，Java标注可以通过反射获取标注内容。
 * 在编译器生成类文件时，标注可以被嵌入到字节码中。Java虚拟机可以保留标注内容，在运行时可以获取到标注内容。
 * 当然它也支持自定义Java标注。
 *
 * JDK1.5版本内置了三种标准的注解：
 * @Override，表示当前的方法定义将覆盖超类中的方法。
 * @Deprecated，使用了注解为它的元素编译器将发出警告，因为注解@Deprecated是不赞成使用的代码，被弃用的代码。
 * @SuppressWarnings,关闭不当编辑器警告信息。
 *
 * 元注解：
 * 元注解位于 java.lang.annotation 包中，可以使用这些注解来定义自己的注解。
 * 注解的处理类为 java.lang.reflect.AnnotationElement 接口。注解的处理是通过反射的方式进行的，与反射相关的
 * 类，如 Class、Method 和 Field 等都实现了 AnnotationElement 接口。因此，只要我们通过反射拿到 Class、Method、Field类，
 * 就能够通过 getAnnotation(Class<T>) 拿到我们想要的注解并取值。
 *
 * @Target：描述了注解修饰的范围，取值在 java.lang.annotation.ElementType 枚举类中定义。常用的包括：
 *      METHOD: 用于描述方法；
 *      PACKAGE: 用于描述包；
 *      PARAMETER: 用于描述方法参数；
 *      TYPE: 用于描述类、接口或enum类型
 *
 * @Retention: 表示注解保留时间长短。取值在java.lang.annotation.RetentionPolicy中，取值为：
 *      SOURCE：在源文件中有效，编译过程中会被忽略；
 *      CLASS：随源文件一起编译在class文件中，运行时忽略；
 *      RUNTIME：在运行时有效；
 *
 * 只有定义为 RetentionPolicy.RUNTIME 时，我们才能通过注解反射获取到注解。
 *
 * 如下自定义注解，用在字段上，并且可以通过反射获取到，功能是用来描述字段的长度和作用。
 */
package me.shy.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MyField {
    String desc();

    int length();
}
