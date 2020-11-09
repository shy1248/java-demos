// GENERATED CODE -- DO NOT EDIT!

'use strict';
var grpc = require('grpc');
var greeting_pb = require('./greeting_pb.js');

function serialize_me_shy_demo_grpc_GreetingRequest(arg) {
  if (!(arg instanceof greeting_pb.GreetingRequest)) {
    throw new Error('Expected argument of type me.shy.demo.grpc.GreetingRequest');
  }
  return new Buffer(arg.serializeBinary());
}

function deserialize_me_shy_demo_grpc_GreetingRequest(buffer_arg) {
  return greeting_pb.GreetingRequest.deserializeBinary(new Uint8Array(buffer_arg));
}

function serialize_me_shy_demo_grpc_GreetingResponse(arg) {
  if (!(arg instanceof greeting_pb.GreetingResponse)) {
    throw new Error('Expected argument of type me.shy.demo.grpc.GreetingResponse');
  }
  return new Buffer(arg.serializeBinary());
}

function deserialize_me_shy_demo_grpc_GreetingResponse(buffer_arg) {
  return greeting_pb.GreetingResponse.deserializeBinary(new Uint8Array(buffer_arg));
}


// grpc service中的方法参数和返回类型必须是一个已定义的message，因此哪怕只有一个字段也必须定义成一个message
var GreetingServiceService = exports.GreetingServiceService = {
  // 此处方法名为大写，自动生成代码后会转换为小写
  sayHello: {
    path: '/me.shy.demo.grpc.GreetingService/SayHello',
    requestStream: false,
    responseStream: false,
    requestType: greeting_pb.GreetingRequest,
    responseType: greeting_pb.GreetingResponse,
    requestSerialize: serialize_me_shy_demo_grpc_GreetingRequest,
    requestDeserialize: deserialize_me_shy_demo_grpc_GreetingRequest,
    responseSerialize: serialize_me_shy_demo_grpc_GreetingResponse,
    responseDeserialize: deserialize_me_shy_demo_grpc_GreetingResponse,
  },
};

exports.GreetingServiceClient = grpc.makeGenericClientConstructor(GreetingServiceService);
