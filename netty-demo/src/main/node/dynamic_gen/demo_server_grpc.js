/**
 * @Since: 2020/3/21 11:55
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 *
 **/

// 定义proto文件位置
var PROTO_FILE = __dirname + '/../../grpc/greeting.proto'
// 导入grpc包
var grpc = require('grpc')
// 加载proto文件
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

// 实现业务逻辑处理方法
function sayHello(call, callback) {
  console.log("Greeting from client: " + call.request.username)
  callback(null, {greetWords: 'Hello, ' + call.request.username})
}

// 入口函数
function main(address) {
  // 创建服务器
  var server = new grpc.Server()
  // 绑定业务逻辑处理方法
  server.addService(grpcService.GreetingService.service, {
    sayHello: sayHello
  })
  // 绑定端口
  server.bind(address, grpc.ServerCredentials.createInsecure())
  console.log("Server started, listen on port " + address + " ...")
  // 启动服务器
  server.start()
}

// 调用入口函数
main('0.0.0.0:9999')