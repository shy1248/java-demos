package me.shy.netty.netty.rpc.grpc.userGen;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.28.0)",
    comments = "Source: user.proto")
public final class UserServiceGrpc {

  private UserServiceGrpc() {}

  public static final String SERVICE_NAME = "me.shy.demo.grpc.UserService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<me.shy.netty.netty.rpc.grpc.userGen.UserName,
      me.shy.netty.netty.rpc.grpc.userGen.User> getGetUserByNameMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetUserByName",
      requestType = me.shy.netty.netty.rpc.grpc.userGen.UserName.class,
      responseType = me.shy.netty.netty.rpc.grpc.userGen.User.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<me.shy.netty.netty.rpc.grpc.userGen.UserName,
      me.shy.netty.netty.rpc.grpc.userGen.User> getGetUserByNameMethod() {
    io.grpc.MethodDescriptor<me.shy.netty.netty.rpc.grpc.userGen.UserName, me.shy.netty.netty.rpc.grpc.userGen.User> getGetUserByNameMethod;
    if ((getGetUserByNameMethod = UserServiceGrpc.getGetUserByNameMethod) == null) {
      synchronized (UserServiceGrpc.class) {
        if ((getGetUserByNameMethod = UserServiceGrpc.getGetUserByNameMethod) == null) {
          UserServiceGrpc.getGetUserByNameMethod = getGetUserByNameMethod =
              io.grpc.MethodDescriptor.<me.shy.netty.netty.rpc.grpc.userGen.UserName, me.shy.netty.netty.rpc.grpc.userGen.User>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetUserByName"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  me.shy.netty.netty.rpc.grpc.userGen.UserName.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  me.shy.netty.netty.rpc.grpc.userGen.User.getDefaultInstance()))
              .setSchemaDescriptor(new UserServiceMethodDescriptorSupplier("GetUserByName"))
              .build();
        }
      }
    }
    return getGetUserByNameMethod;
  }

  private static volatile io.grpc.MethodDescriptor<me.shy.netty.netty.rpc.grpc.userGen.UserAge,
      me.shy.netty.netty.rpc.grpc.userGen.User> getGetUsersByAgeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetUsersByAge",
      requestType = me.shy.netty.netty.rpc.grpc.userGen.UserAge.class,
      responseType = me.shy.netty.netty.rpc.grpc.userGen.User.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<me.shy.netty.netty.rpc.grpc.userGen.UserAge,
      me.shy.netty.netty.rpc.grpc.userGen.User> getGetUsersByAgeMethod() {
    io.grpc.MethodDescriptor<me.shy.netty.netty.rpc.grpc.userGen.UserAge, me.shy.netty.netty.rpc.grpc.userGen.User> getGetUsersByAgeMethod;
    if ((getGetUsersByAgeMethod = UserServiceGrpc.getGetUsersByAgeMethod) == null) {
      synchronized (UserServiceGrpc.class) {
        if ((getGetUsersByAgeMethod = UserServiceGrpc.getGetUsersByAgeMethod) == null) {
          UserServiceGrpc.getGetUsersByAgeMethod = getGetUsersByAgeMethod =
              io.grpc.MethodDescriptor.<me.shy.netty.netty.rpc.grpc.userGen.UserAge, me.shy.netty.netty.rpc.grpc.userGen.User>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetUsersByAge"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  me.shy.netty.netty.rpc.grpc.userGen.UserAge.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  me.shy.netty.netty.rpc.grpc.userGen.User.getDefaultInstance()))
              .setSchemaDescriptor(new UserServiceMethodDescriptorSupplier("GetUsersByAge"))
              .build();
        }
      }
    }
    return getGetUsersByAgeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<me.shy.netty.netty.rpc.grpc.userGen.UserAge,
      me.shy.netty.netty.rpc.grpc.userGen.UserList> getGetPackingUsersByAgeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetPackingUsersByAge",
      requestType = me.shy.netty.netty.rpc.grpc.userGen.UserAge.class,
      responseType = me.shy.netty.netty.rpc.grpc.userGen.UserList.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<me.shy.netty.netty.rpc.grpc.userGen.UserAge,
      me.shy.netty.netty.rpc.grpc.userGen.UserList> getGetPackingUsersByAgeMethod() {
    io.grpc.MethodDescriptor<me.shy.netty.netty.rpc.grpc.userGen.UserAge, me.shy.netty.netty.rpc.grpc.userGen.UserList> getGetPackingUsersByAgeMethod;
    if ((getGetPackingUsersByAgeMethod = UserServiceGrpc.getGetPackingUsersByAgeMethod) == null) {
      synchronized (UserServiceGrpc.class) {
        if ((getGetPackingUsersByAgeMethod = UserServiceGrpc.getGetPackingUsersByAgeMethod) == null) {
          UserServiceGrpc.getGetPackingUsersByAgeMethod = getGetPackingUsersByAgeMethod =
              io.grpc.MethodDescriptor.<me.shy.netty.netty.rpc.grpc.userGen.UserAge, me.shy.netty.netty.rpc.grpc.userGen.UserList>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetPackingUsersByAge"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  me.shy.netty.netty.rpc.grpc.userGen.UserAge.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  me.shy.netty.netty.rpc.grpc.userGen.UserList.getDefaultInstance()))
              .setSchemaDescriptor(new UserServiceMethodDescriptorSupplier("GetPackingUsersByAge"))
              .build();
        }
      }
    }
    return getGetPackingUsersByAgeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<me.shy.netty.netty.rpc.grpc.userGen.Words,
      me.shy.netty.netty.rpc.grpc.userGen.Words> getTalkMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Talk",
      requestType = me.shy.netty.netty.rpc.grpc.userGen.Words.class,
      responseType = me.shy.netty.netty.rpc.grpc.userGen.Words.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<me.shy.netty.netty.rpc.grpc.userGen.Words,
      me.shy.netty.netty.rpc.grpc.userGen.Words> getTalkMethod() {
    io.grpc.MethodDescriptor<me.shy.netty.netty.rpc.grpc.userGen.Words, me.shy.netty.netty.rpc.grpc.userGen.Words> getTalkMethod;
    if ((getTalkMethod = UserServiceGrpc.getTalkMethod) == null) {
      synchronized (UserServiceGrpc.class) {
        if ((getTalkMethod = UserServiceGrpc.getTalkMethod) == null) {
          UserServiceGrpc.getTalkMethod = getTalkMethod =
              io.grpc.MethodDescriptor.<me.shy.netty.netty.rpc.grpc.userGen.Words, me.shy.netty.netty.rpc.grpc.userGen.Words>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Talk"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  me.shy.netty.netty.rpc.grpc.userGen.Words.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  me.shy.netty.netty.rpc.grpc.userGen.Words.getDefaultInstance()))
              .setSchemaDescriptor(new UserServiceMethodDescriptorSupplier("Talk"))
              .build();
        }
      }
    }
    return getTalkMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static UserServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UserServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UserServiceStub>() {
        @java.lang.Override
        public UserServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UserServiceStub(channel, callOptions);
        }
      };
    return UserServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static UserServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UserServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UserServiceBlockingStub>() {
        @java.lang.Override
        public UserServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UserServiceBlockingStub(channel, callOptions);
        }
      };
    return UserServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static UserServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UserServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UserServiceFutureStub>() {
        @java.lang.Override
        public UserServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UserServiceFutureStub(channel, callOptions);
        }
      };
    return UserServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class UserServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * 简单请求
     * </pre>
     */
    public void getUserByName(me.shy.netty.netty.rpc.grpc.userGen.UserName request,
        io.grpc.stub.StreamObserver<me.shy.netty.netty.rpc.grpc.userGen.User> responseObserver) {
      asyncUnimplementedUnaryCall(getGetUserByNameMethod(), responseObserver);
    }

    /**
     * <pre>
     * 流式响应
     * </pre>
     */
    public void getUsersByAge(me.shy.netty.netty.rpc.grpc.userGen.UserAge request,
        io.grpc.stub.StreamObserver<me.shy.netty.netty.rpc.grpc.userGen.User> responseObserver) {
      asyncUnimplementedUnaryCall(getGetUsersByAgeMethod(), responseObserver);
    }

    /**
     * <pre>
     * 流式请求
     * </pre>
     */
    public io.grpc.stub.StreamObserver<me.shy.netty.netty.rpc.grpc.userGen.UserAge> getPackingUsersByAge(
        io.grpc.stub.StreamObserver<me.shy.netty.netty.rpc.grpc.userGen.UserList> responseObserver) {
      return asyncUnimplementedStreamingCall(getGetPackingUsersByAgeMethod(), responseObserver);
    }

    /**
     * <pre>
     * 双向流
     * </pre>
     */
    public io.grpc.stub.StreamObserver<me.shy.netty.netty.rpc.grpc.userGen.Words> talk(
        io.grpc.stub.StreamObserver<me.shy.netty.netty.rpc.grpc.userGen.Words> responseObserver) {
      return asyncUnimplementedStreamingCall(getTalkMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetUserByNameMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                me.shy.netty.netty.rpc.grpc.userGen.UserName,
                me.shy.netty.netty.rpc.grpc.userGen.User>(
                  this, METHODID_GET_USER_BY_NAME)))
          .addMethod(
            getGetUsersByAgeMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                me.shy.netty.netty.rpc.grpc.userGen.UserAge,
                me.shy.netty.netty.rpc.grpc.userGen.User>(
                  this, METHODID_GET_USERS_BY_AGE)))
          .addMethod(
            getGetPackingUsersByAgeMethod(),
            asyncClientStreamingCall(
              new MethodHandlers<
                me.shy.netty.netty.rpc.grpc.userGen.UserAge,
                me.shy.netty.netty.rpc.grpc.userGen.UserList>(
                  this, METHODID_GET_PACKING_USERS_BY_AGE)))
          .addMethod(
            getTalkMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                me.shy.netty.netty.rpc.grpc.userGen.Words,
                me.shy.netty.netty.rpc.grpc.userGen.Words>(
                  this, METHODID_TALK)))
          .build();
    }
  }

  /**
   */
  public static final class UserServiceStub extends io.grpc.stub.AbstractAsyncStub<UserServiceStub> {
    private UserServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UserServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 简单请求
     * </pre>
     */
    public void getUserByName(me.shy.netty.netty.rpc.grpc.userGen.UserName request,
        io.grpc.stub.StreamObserver<me.shy.netty.netty.rpc.grpc.userGen.User> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetUserByNameMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 流式响应
     * </pre>
     */
    public void getUsersByAge(me.shy.netty.netty.rpc.grpc.userGen.UserAge request,
        io.grpc.stub.StreamObserver<me.shy.netty.netty.rpc.grpc.userGen.User> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getGetUsersByAgeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 流式请求
     * </pre>
     */
    public io.grpc.stub.StreamObserver<me.shy.netty.netty.rpc.grpc.userGen.UserAge> getPackingUsersByAge(
        io.grpc.stub.StreamObserver<me.shy.netty.netty.rpc.grpc.userGen.UserList> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(getGetPackingUsersByAgeMethod(), getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     * 双向流
     * </pre>
     */
    public io.grpc.stub.StreamObserver<me.shy.netty.netty.rpc.grpc.userGen.Words> talk(
        io.grpc.stub.StreamObserver<me.shy.netty.netty.rpc.grpc.userGen.Words> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getTalkMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class UserServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<UserServiceBlockingStub> {
    private UserServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UserServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 简单请求
     * </pre>
     */
    public me.shy.netty.netty.rpc.grpc.userGen.User getUserByName(me.shy.netty.netty.rpc.grpc.userGen.UserName request) {
      return blockingUnaryCall(
          getChannel(), getGetUserByNameMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 流式响应
     * </pre>
     */
    public java.util.Iterator<me.shy.netty.netty.rpc.grpc.userGen.User> getUsersByAge(
        me.shy.netty.netty.rpc.grpc.userGen.UserAge request) {
      return blockingServerStreamingCall(
          getChannel(), getGetUsersByAgeMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class UserServiceFutureStub extends io.grpc.stub.AbstractFutureStub<UserServiceFutureStub> {
    private UserServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UserServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 简单请求
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<me.shy.netty.netty.rpc.grpc.userGen.User> getUserByName(
        me.shy.netty.netty.rpc.grpc.userGen.UserName request) {
      return futureUnaryCall(
          getChannel().newCall(getGetUserByNameMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_USER_BY_NAME = 0;
  private static final int METHODID_GET_USERS_BY_AGE = 1;
  private static final int METHODID_GET_PACKING_USERS_BY_AGE = 2;
  private static final int METHODID_TALK = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final UserServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(UserServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_USER_BY_NAME:
          serviceImpl.getUserByName((me.shy.netty.netty.rpc.grpc.userGen.UserName) request,
              (io.grpc.stub.StreamObserver<me.shy.netty.netty.rpc.grpc.userGen.User>) responseObserver);
          break;
        case METHODID_GET_USERS_BY_AGE:
          serviceImpl.getUsersByAge((me.shy.netty.netty.rpc.grpc.userGen.UserAge) request,
              (io.grpc.stub.StreamObserver<me.shy.netty.netty.rpc.grpc.userGen.User>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_PACKING_USERS_BY_AGE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.getPackingUsersByAge(
              (io.grpc.stub.StreamObserver<me.shy.netty.netty.rpc.grpc.userGen.UserList>) responseObserver);
        case METHODID_TALK:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.talk(
              (io.grpc.stub.StreamObserver<me.shy.netty.netty.rpc.grpc.userGen.Words>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class UserServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    UserServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return me.shy.netty.netty.rpc.grpc.userGen.UserProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("UserService");
    }
  }

  private static final class UserServiceFileDescriptorSupplier
      extends UserServiceBaseDescriptorSupplier {
    UserServiceFileDescriptorSupplier() {}
  }

  private static final class UserServiceMethodDescriptorSupplier
      extends UserServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    UserServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (UserServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new UserServiceFileDescriptorSupplier())
              .addMethod(getGetUserByNameMethod())
              .addMethod(getGetUsersByAgeMethod())
              .addMethod(getGetPackingUsersByAgeMethod())
              .addMethod(getTalkMethod())
              .build();
        }
      }
    }
    return result;
  }
}
