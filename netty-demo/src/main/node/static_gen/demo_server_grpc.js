/**
 * @Since: 2020/3/21 15:22
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 *
 **/

// 引入生成的service与message
var service = require('./gen/greeting_grpc_pb')
var message = require('./gen/greeting_pb')

// 引入grpc
var grpc = require('grpc')

// 实现业务逻辑处理方法
function sayHello(call, callback){
  var user = call.request.getUsername()
  console.log("Greeting from client: " + user)
  var resp = new message.GreetingResponse()
  resp.setGreetwords('Hello, ' + user)
  callback(null, resp)
}

// 入口函数
function main(address) {
  // 创建Server
  var server = new grpc.Server();
  // 绑定业务逻辑处理方法
  server.addService(service.GreetingServiceService, {sayHello: sayHello})
  // 绑定端口
  server.bind(address, grpc.ServerCredentials.createInsecure())
  console.log("Server started, listen on port " + address + " ...")
  // 启动服务器
  server.start()
}

// 调用入口函数
main('0.0.0.0:9999')