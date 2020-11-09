/**
 * @Since: 2020/3/21 15:23
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


function main(address) {
  // 创建客户端
  var client = new service.GreetingServiceClient(address,
      grpc.credentials.createInsecure())
  // 发起请求
  var names = new Array("Tom", "Jerry", "Mickey", "Charly", "Jiony")
  for (var i = 0; i < 10; i++) {
    var username = names[Math.floor(Math.random() * names.length)]
    var request = new message.GreetingRequest()
    request.setUsername(username)
    client.sayHello(request, function (error, response) {
      console.log('Greeting: ' + response.getGreetwords())
    })
  }
}

main('0.0.0.0:9999')

