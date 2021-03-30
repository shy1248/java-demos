// 生成对应语言的代码：thrift --gen java/py/perl... student.thrift
// 指定命名空间，对于java，生成的代码以此作为包名
// 可以使用include包含其它的thrift文件
namespace java me.shy.demo.netty.rpc.thrift.gen
// 定义python语言的namespace，需要下载thrift源码包，然后进入lib/py中使用 python setup.py install 来安装python的依赖的库
namespace py student_gen

// 重定义类型
// thrift支持的类型有：byte，i16，i32，i64，double，bool，binary，string
// 容器类型有：list<t>，set<t>，map<t1,t2>
typedef i16 shot
typedef i32 int
typedef i64 long

// 定义结构体，用于数据封装，相当于Java中的bean
// 结构体中除了不能有service以外，其它类型都可以有，包括exception
struct Student {
    // 字段编号1，必须，i64类型，字段名为id
    1: required long id,
    // 字段编号2，必须，string类型，字段名为name
    2: required string name,
    // 字段编号3，可选，i32类型，字段名为age
    3: optional int age,
    // 字段编号4，可选，Sex枚举类型，字段名为gender
    4: optional Sex gender
}

// 枚举类型定义
enum Sex {
    MALE,
    FEMALE
}

// 定义异常类型
exception DataException {
    1: optional int code,
    2: optional string reason
}

// 定义service，相当于Java中的接口
service StudentService {
    // 返回值 方法名(编号:可选还是必须 类型 参数名) throws(编号:类型 名)
    Student getStudentByName(1: required string name) throws(1:DataException e),
    void save(1: required Student student) throws(1: DataException e)
}