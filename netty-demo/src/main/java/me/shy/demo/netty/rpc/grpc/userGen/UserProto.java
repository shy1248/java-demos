// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: user.proto

package me.shy.demo.netty.rpc.grpc.userGen;

public final class UserProto {
  private UserProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_me_shy_demo_grpc_User_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_me_shy_demo_grpc_User_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_me_shy_demo_grpc_UserName_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_me_shy_demo_grpc_UserName_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_me_shy_demo_grpc_UserAge_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_me_shy_demo_grpc_UserAge_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_me_shy_demo_grpc_UserList_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_me_shy_demo_grpc_UserList_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_me_shy_demo_grpc_Words_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_me_shy_demo_grpc_Words_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\nuser.proto\022\020me.shy.demo.grpc\"3\n\004User\022\014" +
      "\n\004name\030\001 \001(\t\022\013\n\003age\030\002 \001(\005\022\020\n\010location\030\003 " +
      "\001(\t\"\030\n\010UserName\022\014\n\004name\030\001 \001(\t\"\026\n\007UserAge" +
      "\022\013\n\003age\030\001 \001(\005\"0\n\010UserList\022$\n\004user\030\001 \003(\0132" +
      "\026.me.shy.demo.grpc.User\"\031\n\005Words\022\020\n\010what" +
      "_say\030\001 \001(\t2\257\002\n\013UserService\022E\n\rGetUserByN" +
      "ame\022\032.me.shy.demo.grpc.UserName\032\026.me.shy" +
      ".demo.grpc.User\"\000\022F\n\rGetUsersByAge\022\031.me." +
      "shy.demo.grpc.UserAge\032\026.me.shy.demo.grpc" +
      ".User\"\0000\001\022Q\n\024GetPackingUsersByAge\022\031.me.s" +
      "hy.demo.grpc.UserAge\032\032.me.shy.demo.grpc." +
      "UserList\"\000(\001\022>\n\004Talk\022\027.me.shy.demo.grpc." +
      "Words\032\027.me.shy.demo.grpc.Words\"\000(\0010\001B1\n\"" +
      "me.shy.demo.netty.rpc.grpc.userGenB\tUser" +
      "ProtoP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_me_shy_demo_grpc_User_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_me_shy_demo_grpc_User_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_me_shy_demo_grpc_User_descriptor,
        new java.lang.String[] { "Name", "Age", "Location", });
    internal_static_me_shy_demo_grpc_UserName_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_me_shy_demo_grpc_UserName_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_me_shy_demo_grpc_UserName_descriptor,
        new java.lang.String[] { "Name", });
    internal_static_me_shy_demo_grpc_UserAge_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_me_shy_demo_grpc_UserAge_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_me_shy_demo_grpc_UserAge_descriptor,
        new java.lang.String[] { "Age", });
    internal_static_me_shy_demo_grpc_UserList_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_me_shy_demo_grpc_UserList_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_me_shy_demo_grpc_UserList_descriptor,
        new java.lang.String[] { "User", });
    internal_static_me_shy_demo_grpc_Words_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_me_shy_demo_grpc_Words_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_me_shy_demo_grpc_Words_descriptor,
        new java.lang.String[] { "WhatSay", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}