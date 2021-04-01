// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: user.proto

package me.shy.netty.netty.rpc.grpc.userGen;

/**
 * Protobuf type {@code me.shy.demo.grpc.Words}
 */
public  final class Words extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:me.shy.demo.grpc.Words)
    WordsOrBuilder {
private static final long serialVersionUID = 0L;
  // Use Words.newBuilder() to construct.
  private Words(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private Words() {
    whatSay_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new Words();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private Words(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
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
            java.lang.String s = input.readStringRequireUtf8();

            whatSay_ = s;
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
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return me.shy.netty.netty.rpc.grpc.userGen.UserProto.internal_static_me_shy_demo_grpc_Words_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return me.shy.netty.netty.rpc.grpc.userGen.UserProto.internal_static_me_shy_demo_grpc_Words_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            me.shy.netty.netty.rpc.grpc.userGen.Words.class, me.shy.netty.netty.rpc.grpc.userGen.Words.Builder.class);
  }

  public static final int WHAT_SAY_FIELD_NUMBER = 1;
  private volatile java.lang.Object whatSay_;
  /**
   * <code>string what_say = 1;</code>
   * @return The whatSay.
   */
  public java.lang.String getWhatSay() {
    java.lang.Object ref = whatSay_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs =
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      whatSay_ = s;
      return s;
    }
  }
  /**
   * <code>string what_say = 1;</code>
   * @return The bytes for whatSay.
   */
  public com.google.protobuf.ByteString
      getWhatSayBytes() {
    java.lang.Object ref = whatSay_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      whatSay_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
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
    if (!getWhatSayBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, whatSay_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getWhatSayBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, whatSay_);
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
    if (!(obj instanceof me.shy.netty.netty.rpc.grpc.userGen.Words)) {
      return super.equals(obj);
    }
    me.shy.netty.netty.rpc.grpc.userGen.Words other = (me.shy.netty.netty.rpc.grpc.userGen.Words) obj;

    if (!getWhatSay()
        .equals(other.getWhatSay())) return false;
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
    hash = (37 * hash) + WHAT_SAY_FIELD_NUMBER;
    hash = (53 * hash) + getWhatSay().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static me.shy.netty.netty.rpc.grpc.userGen.Words parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static me.shy.netty.netty.rpc.grpc.userGen.Words parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static me.shy.netty.netty.rpc.grpc.userGen.Words parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static me.shy.netty.netty.rpc.grpc.userGen.Words parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static me.shy.netty.netty.rpc.grpc.userGen.Words parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static me.shy.netty.netty.rpc.grpc.userGen.Words parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static me.shy.netty.netty.rpc.grpc.userGen.Words parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static me.shy.netty.netty.rpc.grpc.userGen.Words parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static me.shy.netty.netty.rpc.grpc.userGen.Words parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static me.shy.netty.netty.rpc.grpc.userGen.Words parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static me.shy.netty.netty.rpc.grpc.userGen.Words parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static me.shy.netty.netty.rpc.grpc.userGen.Words parseFrom(
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
  public static Builder newBuilder(me.shy.netty.netty.rpc.grpc.userGen.Words prototype) {
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
   * Protobuf type {@code me.shy.demo.grpc.Words}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:me.shy.demo.grpc.Words)
      me.shy.netty.netty.rpc.grpc.userGen.WordsOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return me.shy.netty.netty.rpc.grpc.userGen.UserProto.internal_static_me_shy_demo_grpc_Words_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return me.shy.netty.netty.rpc.grpc.userGen.UserProto.internal_static_me_shy_demo_grpc_Words_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              me.shy.netty.netty.rpc.grpc.userGen.Words.class, me.shy.netty.netty.rpc.grpc.userGen.Words.Builder.class);
    }

    // Construct using me.shy.netty.netty.rpc.grpc.userGen.Words.newBuilder()
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
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      whatSay_ = "";

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return me.shy.netty.netty.rpc.grpc.userGen.UserProto.internal_static_me_shy_demo_grpc_Words_descriptor;
    }

    @java.lang.Override
    public me.shy.netty.netty.rpc.grpc.userGen.Words getDefaultInstanceForType() {
      return me.shy.netty.netty.rpc.grpc.userGen.Words.getDefaultInstance();
    }

    @java.lang.Override
    public me.shy.netty.netty.rpc.grpc.userGen.Words build() {
      me.shy.netty.netty.rpc.grpc.userGen.Words result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public me.shy.netty.netty.rpc.grpc.userGen.Words buildPartial() {
      me.shy.netty.netty.rpc.grpc.userGen.Words result = new me.shy.netty.netty.rpc.grpc.userGen.Words(this);
      result.whatSay_ = whatSay_;
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
      if (other instanceof me.shy.netty.netty.rpc.grpc.userGen.Words) {
        return mergeFrom((me.shy.netty.netty.rpc.grpc.userGen.Words)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(me.shy.netty.netty.rpc.grpc.userGen.Words other) {
      if (other == me.shy.netty.netty.rpc.grpc.userGen.Words.getDefaultInstance()) return this;
      if (!other.getWhatSay().isEmpty()) {
        whatSay_ = other.whatSay_;
        onChanged();
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
      me.shy.netty.netty.rpc.grpc.userGen.Words parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (me.shy.netty.netty.rpc.grpc.userGen.Words) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object whatSay_ = "";
    /**
     * <code>string what_say = 1;</code>
     * @return The whatSay.
     */
    public java.lang.String getWhatSay() {
      java.lang.Object ref = whatSay_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        whatSay_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string what_say = 1;</code>
     * @return The bytes for whatSay.
     */
    public com.google.protobuf.ByteString
        getWhatSayBytes() {
      java.lang.Object ref = whatSay_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        whatSay_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string what_say = 1;</code>
     * @param value The whatSay to set.
     * @return This builder for chaining.
     */
    public Builder setWhatSay(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }

      whatSay_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string what_say = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearWhatSay() {

      whatSay_ = getDefaultInstance().getWhatSay();
      onChanged();
      return this;
    }
    /**
     * <code>string what_say = 1;</code>
     * @param value The bytes for whatSay to set.
     * @return This builder for chaining.
     */
    public Builder setWhatSayBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);

      whatSay_ = value;
      onChanged();
      return this;
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


    // @@protoc_insertion_point(builder_scope:me.shy.demo.grpc.Words)
  }

  // @@protoc_insertion_point(class_scope:me.shy.demo.grpc.Words)
  private static final me.shy.netty.netty.rpc.grpc.userGen.Words DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new me.shy.netty.netty.rpc.grpc.userGen.Words();
  }

  public static me.shy.netty.netty.rpc.grpc.userGen.Words getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Words>
      PARSER = new com.google.protobuf.AbstractParser<Words>() {
    @java.lang.Override
    public Words parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new Words(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<Words> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<Words> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public me.shy.netty.netty.rpc.grpc.userGen.Words getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
