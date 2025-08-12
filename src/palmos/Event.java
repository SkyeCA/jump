package palmos;

public class Event {
  public static final short nilEvent              = 0;
  public static final short penDownEvent          = 1;
  public static final short penUpEvent            = 2;
  public static final short penMoveEvent          = 3;
  public static final short keyDownEvent          = 4;
  public static final short winEnterEvent         = 5;
  public static final short winExitEvent          = 6;
  public static final short ctlEnterEvent         = 7;
  public static final short ctlExitEvent          = 8;
  public static final short ctlSelectEvent        = 9;
  public static final short ctlRepeatEvent        = 10;
  public static final short lstEnterEvent         = 11;
  public static final short lstSelectEvent        = 12;
  public static final short lstExitEvent          = 13;
  public static final short popSelectEvent        = 14;
  public static final short fldEnterEvent         = 15;
  public static final short fldHeightChangedEvent = 16;
  public static final short fldChangedEvent       = 17;
  public static final short tblEnterEvent         = 18;
  public static final short tblSelectEvent        = 19;
  public static final short daySelectEvent        = 20;
  public static final short menuEvent             = 21;
  public static final short appStopEvent          = 22;
  public static final short frmLoadEvent          = 23;
  public static final short frmOpenEvent          = 24;
  public static final short frmGotoEvent          = 25;
  public static final short frmUpdateEvent        = 26;
  public static final short frmSaveEvent          = 27;
  public static final short frmCloseEvent         = 28;
  public static final short tblExitEvent          = 29;
  public static final short firstUserEvent        = 32767;
  
  public short eType;
  public boolean penDown;
  public short screenX;
  public short screenY;
  public short data1;
  public short data2;
  public short data3;
  public short data4;
  public short data5;
  public short data6;
  public short data7;
  public short data8;

  // penUp
  public final short start_x() { return data1; }
  public final short start_y() { return data2; }
  public final short end_x() { return data3; }
  public final short end_y() { return data4; }

  // keyDown
  public final char chr() { return (char)data1; }
  public final short keyCode() { return data2; }
  public final short modifiers() { return data3; }

  // winEnter, winExit
  public final int enterWindow() { return (data1 << 16) | (data2 & 0xFFFF); }
  public final int exitWindow() { return (data3 << 16) | (data4 & 0xFFFF); }

  // ctl
  public final short controlID() { return data1; }
  public final int pControl() { return (data2 << 16) | (data3 & 0xFFFF); }

  // ctlSelect
  public final boolean on() { return data4 != 0; }

  // ctlRepeat
  public final int time() { return (data4 << 16) | (data5 & 0xFFFF); }

  // fld
  public final short fieldID() { return data1; }
  public final int pField() { return (data2 << 16) | (data3 & 0xFFFF); }

  // fldHeightChanged
  public final short newHeight() { return data4; }
  public final short currentPos() { return data5; }

  // lst
  public final short listID() { return data1; }
  public final int pList() { return (data2 << 16) | (data3 & 0xFFFF); }

  // lstEnter, lstSelect
  public final short selection() { return data4; }

  // tbl
  public final short tableID() { return data1; }
  public final int pTable() { return (data2 << 16) | (data3 & 0xFFFF); }
  public final short row() { return data4; }
  public final short column() { return data5; }

  // frm
  public final short formID() { return data1; }

  // frmGoto
  public final short recordNum() { return data2; }
  public final short matchPos() { return data3; }
  public final short matchLen() { return data4; }
  public final short matchFieldNum() { return data5; }
  public final int matchCustom() { return (data6 << 16) | (data7 & 0xFFFF); }

  // frmUpdate
  public final short updateCode() { return data2; }

  // daySelect
  public final int pSelector() { return (data1 << 16) | (data2 & 0xFFFF); }
  public final short daySelect_selection() { return data3; }
  public final boolean useThisDate() { return data4 != 0; }

  // menu
  public final short itemID() { return data1; }

  // popSelect
  public final short popSelect_controlID() { return data1; }
  public final int popSelect_controlP() { return (data2 << 16) | (data3 & 0xFFFF); }
  public final short popSelect_listID() { return data4; }
  public final int popSelect_listP() { return (data5 << 16) | (data6 & 0xFFFF); }
  public final short popSelect_selection() { return data7; }
  public final short popSelect_priorSelection() { return data8; }
}
