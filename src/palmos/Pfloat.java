package palmos;

// This is a class that provides an interface to the PalmOS floating point
// library routines. This side (the Java side) should be complete, but the
// assembler side in native-Pfloat.asm is not complete. Attempting to use
// this class right now won't work.

public class Pfloat extends Number {
  // data fields must be in this order to match PalmOS FloatType
  private int man;
  private short exp;
  private byte sign;

  public static native int init(); // FplInit

  public static native void free(); // FplFree

  public Pfloat()
  {
  }

  public Pfloat(int x)
  {
    initInt(x);
  }

  public Pfloat(String s)
  {
    initString(s);
  }

  public Pfloat(Pfloat f)
  {
    man = f.man;
    exp = f.exp;
    sign = f.sign;
  }

  private native void initInt(int x); // FplLongToFloat

  private native void initString(String s); // FplAtoF

  public native String toString(); // FplFToA

  public native int base10Info(Integer mantissa, Integer exponent, Integer sign); // FplBase10Info

  public native int intValue(); // FplFloatToLong

  public long longValue()
  {
    return intValue();
  }

  public float floatValue()
  {
    return intValue();
  }

  public double doubleValue()
  {
    return intValue();
  }

  public native Pfloat add(Pfloat a, Pfloat b); // FplAdd

  public native Pfloat sub(Pfloat a, Pfloat b); // FplSub

  public native Pfloat mul(Pfloat a, Pfloat b); // FplMul

  public native Pfloat div(Pfloat a, Pfloat b); // FplDiv
}
