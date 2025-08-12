package palmos;

public class SerSettings {
  public static final int FlagStopBitsM    = 0x00000001;
  public static final int FlagStopBits1    = 0x00000000;
  public static final int FlagStopBits2    = 0x00000001;
  public static final int FlagParityOnM    = 0x00000002;
  public static final int FlagParityEvenM  = 0x00000004;
  public static final int FlagXonXoffM     = 0x00000008;
  public static final int FlagRTSAutoM     = 0x00000010;
  public static final int FlagCTSAutoM     = 0x00000020;
  public static final int FlagBitsPerCharM = 0x000000c0;
  public static final int FlagBitsPerChar5 = 0x00000000;
  public static final int FlagBitsPerChar6 = 0x00000040;
  public static final int FlagBitsPerChar7 = 0x00000080;
  public static final int FlagBitsPerChar8 = 0x000000c0;

  public SerSettings() {}
  public int baudRate;
  public int flags;
  public int ctsTimeout;
}
