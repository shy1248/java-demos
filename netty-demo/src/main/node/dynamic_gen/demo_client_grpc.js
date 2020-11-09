/**
 * @Since: 2020/3/21 11:17
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: a demo application for node.js grpc client with dynamic
 * generated.
 *
 **/

// 定义proto文件路径
var PROTO_FILE = __dirname + '/../../grpc/greeting.proto'
// 引入grpc包
var grpc = require('grpc')

// 动态加载proto文件
// var grpcService = grpc.load(PROTO_FILE).me.shy.demo.grpc
var protoLoader = require('@grpc/proto-loader')
var pkgDefinition = protoLoader.loadSync(PROTO_FILE, {
  keepCase: true,
  longs: String,
  enums: String,
  defaults: true,
  oneofs: true
})
var grpcService = grpc.loadPackageDefinition(pkgDefinition).me.shy.demo.grpc

// 入口函数
function main(address) {
  // 创建grpc客户端，此处使用不安全的连接方式，类似Java中的usePlainText的方式
  var client = new grpcService.GreetingService(address,
      grpc.credentials.createInsecure())

  // 发起请求
  var names = new Array("Tom", "Jerry", "Mickey", "Charly", "Jiony")
  for (var i = 0; i < 10; i++) {
    var user = names[Math.floor(Math.random() * names.length)]
    client.sayHello({username: user}, function (error, resp) {
      console.log('Greeting: ' + resp.greetWords)
    })
  }
}

// 调用入口函数
main('localhost:9999')