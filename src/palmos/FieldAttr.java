package palmos;

public class FieldAttr {
  public static final short usable        = 0x0001;
  public static final short visible       = 0x0002;
  public static final short editable      = 0x0004;
  public static final short singleLine    = 0x0008;
  public static final short hasFocus      = 0x0010;
  public static final short dynamicSize   = 0x0020;
  public static final short insPtVisible  = 0x0040;
  public static final short dirty         = 0x0080;
  public static final short underlined    = 0x0030;
  public static final short justification = 0x00c0;

  public short attributes;
}
