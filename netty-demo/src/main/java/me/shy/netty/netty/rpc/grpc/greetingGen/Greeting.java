// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: greeting.proto

package me.shy.netty.netty.rpc.grpc.greetingGen;

public final class Greeting {
  private Greeting() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_me_shy_demo_grpc_GreetingRequest_descriptor;
  static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_me_shy_demo_grpc_GreetingRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_me_shy_demo_grpc_GreetingResponse_descriptor;
  static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_me_shy_demo_grpc_GreetingResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\016greeting.proto\022\020me.shy.demo.grpc\"#\n\017Gr" +
      "eetingRequest\022\020\n\010username\030\001 \001(\t\"&\n\020Greet" +
      "ingResponse\022\022\n\ngreetWords\030\001 \001(\t2f\n\017Greet" +
      "ingService\022S\n\010SayHello\022!.me.shy.demo.grp" +
      "c.GreetingRequest\032\".me.shy.demo.grpc.Gre" +
      "etingResponse\"\000B4\n&me.shy.netty.netty.rpc" +
      ".grpc.greetingGenB\010GreetingP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_me_shy_demo_grpc_GreetingRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_me_shy_demo_grpc_GreetingRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_me_shy_demo_grpc_GreetingRequest_descriptor,
        new java.lang.String[] { "Username", });
    internal_static_me_shy_demo_grpc_GreetingResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_me_shy_demo_grpc_GreetingResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_me_shy_demo_grpc_GreetingResponse_descriptor,
        new java.lang.String[] { "GreetWords", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
