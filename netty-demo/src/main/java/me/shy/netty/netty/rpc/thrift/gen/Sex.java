/**
 * Autogenerated by Thrift Compiler (0.13.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package me.shy.netty.netty.rpc.thrift.gen;


@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.13.0)", date = "2020-03-19")
public enum Sex implements org.apache.thrift.TEnum {
  MALE(0),
  FEMALE(1);

  private final int value;

  private Sex(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  @org.apache.thrift.annotation.Nullable
  public static Sex findByValue(int value) {
    switch (value) {
      case 0:
        return MALE;
      case 1:
        return FEMALE;
      default:
        return null;
    }
  }
}