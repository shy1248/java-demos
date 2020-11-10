// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: user.proto

package me.shy.demo.netty.rpc.grpc.userGen;

/**
 * Protobuf type {@code me.shy.demo.grpc.UserList}
 */
public  final class UserList extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:me.shy.demo.grpc.UserList)
    UserListOrBuilder {
private static final long serialVersionUID = 0L;
  // Use UserList.newBuilder() to construct.
  private UserList(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private UserList() {
    user_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new UserList();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private UserList(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            if (!((mutable_bitField0_ & 0x00000001) != 0)) {
              user_ = new java.util.ArrayList<me.shy.demo.netty.rpc.grpc.userGen.User>();
              mutable_bitField0_ |= 0x00000001;
            }
            user_.add(
                input.readMessage(me.shy.demo.netty.rpc.grpc.userGen.User.parser(), extensionRegistry));
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      if (((mutable_bitField0_ & 0x00000001) != 0)) {
        user_ = java.util.Collections.unmodifiableList(user_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return me.shy.demo.netty.rpc.grpc.userGen.UserProto.internal_static_me_shy_demo_grpc_UserList_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return me.shy.demo.netty.rpc.grpc.userGen.UserProto.internal_static_me_shy_demo_grpc_UserList_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            me.shy.demo.netty.rpc.grpc.userGen.UserList.class, me.shy.demo.netty.rpc.grpc.userGen.UserList.Builder.class);
  }

  public static final int USER_FIELD_NUMBER = 1;
  private java.util.List<me.shy.demo.netty.rpc.grpc.userGen.User> user_;
  /**
   * <code>repeated .me.shy.demo.grpc.User user = 1;</code>
   */
  public java.util.List<me.shy.demo.netty.rpc.grpc.userGen.User> getUserList() {
    return user_;
  }
  /**
   * <code>repeated .me.shy.demo.grpc.User user = 1;</code>
   */
  public java.util.List<? extends me.shy.demo.netty.rpc.grpc.userGen.UserOrBuilder> 
      getUserOrBuilderList() {
    return user_;
  }
  /**
   * <code>repeated .me.shy.demo.grpc.User user = 1;</code>
   */
  public int getUserCount() {
    return user_.size();
  }
  /**
   * <code>repeated .me.shy.demo.grpc.User user = 1;</code>
   */
  public me.shy.demo.netty.rpc.grpc.userGen.User getUser(int index) {
    return user_.get(index);
  }
  /**
   * <code>repeated .me.shy.demo.grpc.User user = 1;</code>
   */
  public me.shy.demo.netty.rpc.grpc.userGen.UserOrBuilder getUserOrBuilder(
      int index) {
    return user_.get(index);
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    for (int i = 0; i < user_.size(); i++) {
      output.writeMessage(1, user_.get(i));
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (int i = 0; i < user_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, user_.get(i));
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof me.shy.demo.netty.rpc.grpc.userGen.UserList)) {
      return super.equals(obj);
    }
    me.shy.demo.netty.rpc.grpc.userGen.UserList other = (me.shy.demo.netty.rpc.grpc.userGen.UserList) obj;

    if (!getUserList()
        .equals(other.getUserList())) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (getUserCount() > 0) {
      hash = (37 * hash) + USER_FIELD_NUMBER;
      hash = (53 * hash) + getUserList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static me.shy.demo.netty.rpc.grpc.userGen.UserList parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static me.shy.demo.netty.rpc.grpc.userGen.UserList parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static me.shy.demo.netty.rpc.grpc.userGen.UserList parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static me.shy.demo.netty.rpc.grpc.userGen.UserList parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static me.shy.demo.netty.rpc.grpc.userGen.UserList parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static me.shy.demo.netty.rpc.grpc.userGen.UserList parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static me.shy.demo.netty.rpc.grpc.userGen.UserList parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static me.shy.demo.netty.rpc.grpc.userGen.UserList parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static me.shy.demo.netty.rpc.grpc.userGen.UserList parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static me.shy.demo.netty.rpc.grpc.userGen.UserList parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static me.shy.demo.netty.rpc.grpc.userGen.UserList parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static me.shy.demo.netty.rpc.grpc.userGen.UserList parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(me.shy.demo.netty.rpc.grpc.userGen.UserList prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code me.shy.demo.grpc.UserList}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:me.shy.demo.grpc.UserList)
      me.shy.demo.netty.rpc.grpc.userGen.UserListOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return me.shy.demo.netty.rpc.grpc.userGen.UserProto.internal_static_me_shy_demo_grpc_UserList_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return me.shy.demo.netty.rpc.grpc.userGen.UserProto.internal_static_me_shy_demo_grpc_UserList_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              me.shy.demo.netty.rpc.grpc.userGen.UserList.class, me.shy.demo.netty.rpc.grpc.userGen.UserList.Builder.class);
    }

    // Construct using me.shy.demo.netty.rpc.grpc.userGen.UserList.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
        getUserFieldBuilder();
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (userBuilder_ == null) {
        user_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
      } else {
        userBuilder_.clear();
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return me.shy.demo.netty.rpc.grpc.userGen.UserProto.internal_static_me_shy_demo_grpc_UserList_descriptor;
    }

    @java.lang.Override
    public me.shy.demo.netty.rpc.grpc.userGen.UserList getDefaultInstanceForType() {
      return me.shy.demo.netty.rpc.grpc.userGen.UserList.getDefaultInstance();
    }

    @java.lang.Override
    public me.shy.demo.netty.rpc.grpc.userGen.UserList build() {
      me.shy.demo.netty.rpc.grpc.userGen.UserList result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public me.shy.demo.netty.rpc.grpc.userGen.UserList buildPartial() {
      me.shy.demo.netty.rpc.grpc.userGen.UserList result = new me.shy.demo.netty.rpc.grpc.userGen.UserList(this);
      int from_bitField0_ = bitField0_;
      if (userBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          user_ = java.util.Collections.unmodifiableList(user_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.user_ = user_;
      } else {
        result.user_ = userBuilder_.build();
      }
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof me.shy.demo.netty.rpc.grpc.userGen.UserList) {
        return mergeFrom((me.shy.demo.netty.rpc.grpc.userGen.UserList)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(me.shy.demo.netty.rpc.grpc.userGen.UserList other) {
      if (other == me.shy.demo.netty.rpc.grpc.userGen.UserList.getDefaultInstance()) return this;
      if (userBuilder_ == null) {
        if (!other.user_.isEmpty()) {
          if (user_.isEmpty()) {
            user_ = other.user_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureUserIsMutable();
            user_.addAll(other.user_);
          }
          onChanged();
        }
      } else {
        if (!other.user_.isEmpty()) {
          if (userBuilder_.isEmpty()) {
            userBuilder_.dispose();
            userBuilder_ = null;
            user_ = other.user_;
            bitField0_ = (bitField0_ & ~0x00000001);
            userBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getUserFieldBuilder() : null;
          } else {
            userBuilder_.addAllMessages(other.user_);
          }
        }
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      me.shy.demo.netty.rpc.grpc.userGen.UserList parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (me.shy.demo.netty.rpc.grpc.userGen.UserList) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private java.util.List<me.shy.demo.netty.rpc.grpc.userGen.User> user_ =
      java.util.Collections.emptyList();
    private void ensureUserIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        user_ = new java.util.ArrayList<me.shy.demo.netty.rpc.grpc.userGen.User>(user_);
        bitField0_ |= 0x00000001;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        me.shy.demo.netty.rpc.grpc.userGen.User, me.shy.demo.netty.rpc.grpc.userGen.User.Builder, me.shy.demo.netty.rpc.grpc.userGen.UserOrBuilder> userBuilder_;

    /**
     * <code>repeated .me.shy.demo.grpc.User user = 1;</code>
     */
    public java.util.List<me.shy.demo.netty.rpc.grpc.userGen.User> getUserList() {
      if (userBuilder_ == null) {
        return java.util.Collections.unmodifiableList(user_);
      } else {
        return userBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .me.shy.demo.grpc.User user = 1;</code>
     */
    public int getUserCount() {
      if (userBuilder_ == null) {
        return user_.size();
      } else {
        return userBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .me.shy.demo.grpc.User user = 1;</code>
     */
    public me.shy.demo.netty.rpc.grpc.userGen.User getUser(int index) {
      if (userBuilder_ == null) {
        return user_.get(index);
      } else {
        return userBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .me.shy.demo.grpc.User user = 1;</code>
     */
    public Builder setUser(
        int index, me.shy.demo.netty.rpc.grpc.userGen.User value) {
      if (userBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureUserIsMutable();
        user_.set(index, value);
        onChanged();
      } else {
        userBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .me.shy.demo.grpc.User user = 1;</code>
     */
    public Builder setUser(
        int index, me.shy.demo.netty.rpc.grpc.userGen.User.Builder builderForValue) {
      if (userBuilder_ == null) {
        ensureUserIsMutable();
        user_.set(index, builderForValue.build());
        onChanged();
      } else {
        userBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .me.shy.demo.grpc.User user = 1;</code>
     */
    public Builder addUser(me.shy.demo.netty.rpc.grpc.userGen.User value) {
      if (userBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureUserIsMutable();
        user_.add(value);
        onChanged();
      } else {
        userBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .me.shy.demo.grpc.User user = 1;</code>
     */
    public Builder addUser(
        int index, me.shy.demo.netty.rpc.grpc.userGen.User value) {
      if (userBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureUserIsMutable();
        user_.add(index, value);
        onChanged();
      } else {
        userBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .me.shy.demo.grpc.User user = 1;</code>
     */
    public Builder addUser(
        me.shy.demo.netty.rpc.grpc.userGen.User.Builder builderForValue) {
      if (userBuilder_ == null) {
        ensureUserIsMutable();
        user_.add(builderForValue.build());
        onChanged();
      } else {
        userBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .me.shy.demo.grpc.User user = 1;</code>
     */
    public Builder addUser(
        int index, me.shy.demo.netty.rpc.grpc.userGen.User.Builder builderForValue) {
      if (userBuilder_ == null) {
        ensureUserIsMutable();
        user_.add(index, builderForValue.build());
        onChanged();
      } else {
        userBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .me.shy.demo.grpc.User user = 1;</code>
     */
    public Builder addAllUser(
        java.lang.Iterable<? extends me.shy.demo.netty.rpc.grpc.userGen.User> values) {
      if (userBuilder_ == null) {
        ensureUserIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, user_);
        onChanged();
      } else {
        userBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .me.shy.demo.grpc.User user = 1;</code>
     */
    public Builder clearUser() {
      if (userBuilder_ == null) {
        user_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        userBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .me.shy.demo.grpc.User user = 1;</code>
     */
    public Builder removeUser(int index) {
      if (userBuilder_ == null) {
        ensureUserIsMutable();
        user_.remove(index);
        onChanged();
      } else {
        userBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .me.shy.demo.grpc.User user = 1;</code>
     */
    public me.shy.demo.netty.rpc.grpc.userGen.User.Builder getUserBuilder(
        int index) {
      return getUserFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .me.shy.demo.grpc.User user = 1;</code>
     */
    public me.shy.demo.netty.rpc.grpc.userGen.UserOrBuilder getUserOrBuilder(
        int index) {
      if (userBuilder_ == null) {
        return user_.get(index);  } else {
        return userBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .me.shy.demo.grpc.User user = 1;</code>
     */
    public java.util.List<? extends me.shy.demo.netty.rpc.grpc.userGen.UserOrBuilder> 
         getUserOrBuilderList() {
      if (userBuilder_ != null) {
        return userBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(user_);
      }
    }
    /**
     * <code>repeated .me.shy.demo.grpc.User user = 1;</code>
     */
    public me.shy.demo.netty.rpc.grpc.userGen.User.Builder addUserBuilder() {
      return getUserFieldBuilder().addBuilder(
          me.shy.demo.netty.rpc.grpc.userGen.User.getDefaultInstance());
    }
    /**
     * <code>repeated .me.shy.demo.grpc.User user = 1;</code>
     */
    public me.shy.demo.netty.rpc.grpc.userGen.User.Builder addUserBuilder(
        int index) {
      return getUserFieldBuilder().addBuilder(
          index, me.shy.demo.netty.rpc.grpc.userGen.User.getDefaultInstance());
    }
    /**
     * <code>repeated .me.shy.demo.grpc.User user = 1;</code>
     */
    public java.util.List<me.shy.demo.netty.rpc.grpc.userGen.User.Builder> 
         getUserBuilderList() {
      return getUserFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        me.shy.demo.netty.rpc.grpc.userGen.User, me.shy.demo.netty.rpc.grpc.userGen.User.Builder, me.shy.demo.netty.rpc.grpc.userGen.UserOrBuilder> 
        getUserFieldBuilder() {
      if (userBuilder_ == null) {
        userBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            me.shy.demo.netty.rpc.grpc.userGen.User, me.shy.demo.netty.rpc.grpc.userGen.User.Builder, me.shy.demo.netty.rpc.grpc.userGen.UserOrBuilder>(
                user_,
                ((bitField0_ & 0x00000001) != 0),
                getParentForChildren(),
                isClean());
        user_ = null;
      }
      return userBuilder_;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:me.shy.demo.grpc.UserList)
  }

  // @@protoc_insertion_point(class_scope:me.shy.demo.grpc.UserList)
  private static final me.shy.demo.netty.rpc.grpc.userGen.UserList DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new me.shy.demo.netty.rpc.grpc.userGen.UserList();
  }

  public static me.shy.demo.netty.rpc.grpc.userGen.UserList getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<UserList>
      PARSER = new com.google.protobuf.AbstractParser<UserList>() {
    @java.lang.Override
    public UserList parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new UserList(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<UserList> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<UserList> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public me.shy.demo.netty.rpc.grpc.userGen.UserList getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
