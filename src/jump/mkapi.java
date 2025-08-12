import java.io.*;

class paraminfo {
  public String type;
  public String name;
}

class mkapi {
  public static void main(String[] args) throws IOException
  {
    DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream("palmos.api")));
    PrintStream jout = new PrintStream(new BufferedOutputStream(new FileOutputStream("palmos\\Palm.java")));
    PrintStream aout = new PrintStream(new BufferedOutputStream(new FileOutputStream("native-palmos.asm")));
    jout.println("package palmos;");
    jout.println();
    jout.println("public class Palm {");
    while (true) {
      String s = in.readLine();
      if (s == null) {
        break;
      }
      if (s.length() == 0 || s.charAt(0) == '/') {
        continue;
      }
      if (s.length() > 7 && s.substring(0, 7).equals("public ")) {
        while (true) {
          jout.println(s);
          if (s.charAt(0) == '}') {
            break;
          }
          s = in.readLine();
        }
        continue;
      }
      strtok st = new strtok(s, "(), ");
      String rettype = st.next();
      if (rettype.equals("DmOpenRef")
       || rettype.equals("FieldPtr")
       || rettype.equals("FontPtr")
       || rettype.equals("FormPtr")
       || rettype.equals("MenuBarPtr")
       || rettype.equals("VoidHand")
       || rettype.equals("VoidPtr")
       || rettype.equals("WinHandle")
       || rettype.equals("WinPtr")) {
        rettype = "Handle";
      }
      if (rettype.equals("Err")
       || rettype.equals("FontID")
       || rettype.equals("FormObjectKind")
       || rettype.equals("LocalIDKind")
       || rettype.equals("UnderlineModeType")) {
        rettype = "short";
      }
      String name = st.next();
      boolean lib = false;
      if (name.charAt(0) == '&') {
        name = name.substring(1);
        lib = true;
      }
      paraminfo[] params = new paraminfo[20];
      int pcount = 0;
      while (true) {
        params[pcount] = new paraminfo();
        params[pcount].type = st.next();
        if (params[pcount].type.equals("BitmapPtr")
         || params[pcount].type.equals("ControlPtr")
         || params[pcount].type.equals("DmOpenRef")
         || params[pcount].type.equals("FieldPtr")
         || params[pcount].type.equals("FormPtr")
         || params[pcount].type.equals("ListPtr")
         || params[pcount].type.equals("MenuBarPtr")
         || params[pcount].type.equals("TablePtr")
         || params[pcount].type.equals("UnderlineModeType")
         || params[pcount].type.equals("VoidHand")
         || params[pcount].type.equals("voidptr")
         || params[pcount].type.equals("WinHandle")) {
          params[pcount].type = "Handle";
        }
        if (params[pcount].type.equals("ClipboardFormatType")
         //|| params[pcount].type.equals("CustomPatternType")
         || params[pcount].type.equals("DateFormatType")
         || params[pcount].type.equals("DirectionType")
         || params[pcount].type.equals("FontID")
         || params[pcount].type.equals("ScrOperation")
         || params[pcount].type.equals("SndSysBeepType")
         || params[pcount].type.equals("TableItemStyleType")
         || params[pcount].type.equals("TimeFormatType")
         || params[pcount].type.equals("WindowFormatType")) {
          params[pcount].type = "byte";
        }
        if (params[pcount].type.equals("DateType")
         || params[pcount].type.equals("FrameType")) {
          params[pcount].type = "short";
        }
        if (params[pcount].type.equals("LocalID")) {
          params[pcount].type = "int";
        }
        if (params[pcount].type.charAt(0) == ';') {
          break;
        }
        params[pcount].name = st.next();
        pcount++;
      }
      jout.print("  public native static ");
           if (rettype.equals("Boolean")) jout.print("boolean");
      else if (rettype.equals("Byte"   )) jout.print("byte");
      else if (rettype.equals("CharPtr")) jout.print("String");
      else if (rettype.equals("DWord"  )) jout.print("int");
      else if (rettype.equals("Handle" )) jout.print("int");
      else if (rettype.equals("int"    )) jout.print("int");
      else if (rettype.equals("Int"    )) jout.print("int");
      else if (rettype.equals("LocalID")) jout.print("int");
      else if (rettype.equals("short"  )) jout.print("int");
      else if (rettype.equals("UInt"   )) jout.print("int");
      else if (rettype.equals("ULong"  )) jout.print("int");
      else if (rettype.equals("void"   )) jout.print("void");
      else if (rettype.equals("Word"   )) jout.print("int");
      else {
        System.out.println("Unknown return type (java): " + rettype);
        System.exit(1);
      }
      jout.print(" " + name + "(");
      for (int i = 0; i < pcount; i++) {
        if (i > 0) {
          jout.print(", ");
        }
             if (params[i].type.equals("Boolean"   )) jout.print("boolean");
        else if (params[i].type.equals("Boolean*"  )) jout.print("Boolean");
        else if (params[i].type.equals("BooleanPtr")) jout.print("Boolean");
        else if (params[i].type.equals("byte"      )) jout.print("int");
        else if (params[i].type.equals("Byte"      )) jout.print("int");
        else if (params[i].type.equals("char"      )) jout.print("char");
        else if (params[i].type.equals("CharBuf"   )) jout.print("StringBuffer");
        else if (params[i].type.equals("CharPtr"   )) jout.print("String");
        else if (params[i].type.equals("DatePtr"   )) jout.print("Date");
        else if (params[i].type.equals("DateTimePtr")) jout.print("DateTime");
        else if (params[i].type.equals("DaySelectorPtr")) jout.print("DaySelector");
        else if (params[i].type.equals("DmOpenRef*")) jout.print("Integer");
        else if (params[i].type.equals("DmSearchStatePtr")) jout.print("DmSearchState");
        else if (params[i].type.equals("DWord"     )) jout.print("int");
        else if (params[i].type.equals("DWord*"    )) jout.print("Integer");
        else if (params[i].type.equals("DWordPtr"  )) jout.print("Integer");
        else if (params[i].type.equals("EventPtr"  )) jout.print("Event");
        else if (params[i].type.equals("FieldAttrPtr")) jout.print("FieldAttr");
        else if (params[i].type.equals("FindParamsPtr")) jout.print("FindParams");
        else if (params[i].type.equals("GrfMatchInfoPtr")) jout.print("GrfMatchInfo");
        else if (params[i].type.equals("Handle"    )) jout.print("int");
        else if (params[i].type.equals("Handle*"   )) jout.print("Integer");
        else if (params[i].type.equals("int"       )) jout.print("int");
        else if (params[i].type.equals("Int"       )) jout.print("int");
        else if (params[i].type.equals("int*"      )) jout.print("Short");
        else if (params[i].type.equals("Int*"      )) jout.print("Short");
        else if (params[i].type.equals("IntPtr"    )) jout.print("Short");
        else if (params[i].type.equals("LocalID*"  )) jout.print("Integer");
        else if (params[i].type.equals("Long"      )) jout.print("int");
        else if (params[i].type.equals("PointType*")) jout.print("PointType");
        else if (params[i].type.equals("Ptr"       )) jout.print("Object");
        else if (params[i].type.equals("RectanglePtr")) jout.print("Rectangle");
        else if (params[i].type.equals("SerSettingsPtr")) jout.print("SerSettings");
        else if (params[i].type.equals("short"     )) jout.print("int");
        else if (params[i].type.equals("shortPtr"  )) jout.print("Short");
        else if (params[i].type.equals("SlkPktHeaderPtr")) jout.print("SlkPktHeader");
        else if (params[i].type.equals("SlkSocketListenPtr")) jout.print("SlkSocketListenPtr");
        else if (params[i].type.equals("SlkWriteDataPtr")) jout.print("SlkWriteData");
        else if (params[i].type.equals("SndCommandPtr")) jout.print("SndCommand");
        else if (params[i].type.equals("SWord"     )) jout.print("int");
        else if (params[i].type.equals("SWord*"    )) jout.print("Short");
        else if (params[i].type.equals("SWordPtr"  )) jout.print("Short");
        else if (params[i].type.equals("SysBatteryKind*")) jout.print("Short");
        else if (params[i].type.equals("SystemPreferencesPtr")) jout.print("SystemPreferences");
        else if (params[i].type.equals("UBytePtr"  )) jout.print("Byte");
        else if (params[i].type.equals("UInt"      )) jout.print("int");
        else if (params[i].type.equals("ULong"     )) jout.print("int");
        else if (params[i].type.equals("ULongPtr"  )) jout.print("Integer");
        else if (params[i].type.equals("void*"     )) jout.print("Object");
        else if (params[i].type.equals("VoidHand*" )) jout.print("Integer");
        else if (params[i].type.equals("VoidPtr"   )) jout.print("Object");
        else if (params[i].type.equals("UIntPtr"   )) jout.print("Short");
        else if (params[i].type.equals("Word"      )) jout.print("int");
        else if (params[i].type.equals("WordPtr"   )) jout.print("Short");
        else {
          System.out.println("Unknown parameter type (java): " + params[i].type);
          System.exit(1);
        }
        jout.print(" " + params[i].name);
      }
      jout.println(");");

      aout.println("palmos$Palm__"+name+":");
      aout.println("\tlink\ta6,#0");
      int psize = 0;
      for (int i = 0; i < pcount; i++) {
        if (params[pcount-i-1].type.equals("Boolean*")
         || params[pcount-i-1].type.equals("BooleanPtr")
         || params[pcount-i-1].type.equals("DatePtr")
         || params[pcount-i-1].type.equals("DateTimePtr")
         || params[pcount-i-1].type.equals("DaySelectorPtr")
         || params[pcount-i-1].type.equals("DmOpenRef*")
         || params[pcount-i-1].type.equals("DmSearchStatePtr")
         || params[pcount-i-1].type.equals("DWord")
         || params[pcount-i-1].type.equals("DWord*")
         || params[pcount-i-1].type.equals("DWordPtr")
         || params[pcount-i-1].type.equals("EventPtr")
         || params[pcount-i-1].type.equals("FieldAttrPtr")
         || params[pcount-i-1].type.equals("FindParamsPtr")
         || params[pcount-i-1].type.equals("GrfMatchInfoPtr")
         || params[pcount-i-1].type.equals("Handle")
         || params[pcount-i-1].type.equals("Handle*")
         || params[pcount-i-1].type.equals("int")
         || params[pcount-i-1].type.equals("int*")
         || params[pcount-i-1].type.equals("Int*")
         || params[pcount-i-1].type.equals("IntPtr")
         || params[pcount-i-1].type.equals("LocalID*")
         || params[pcount-i-1].type.equals("Long")
         || params[pcount-i-1].type.equals("PointType*")
         || params[pcount-i-1].type.equals("RectanglePtr")
         || params[pcount-i-1].type.equals("SerSettingsPtr")
         || params[pcount-i-1].type.equals("shortPtr")
         || params[pcount-i-1].type.equals("SlkPktHeaderPtr")
         || params[pcount-i-1].type.equals("SlkSocketListenPtr")
         || params[pcount-i-1].type.equals("SlkWriteDataPtr")
         || params[pcount-i-1].type.equals("SndCommandPtr")
         || params[pcount-i-1].type.equals("SWord*")
         || params[pcount-i-1].type.equals("SWordPtr")
         || params[pcount-i-1].type.equals("SysBatteryKind*")
         || params[pcount-i-1].type.equals("SystemPreferencesPtr")
         || params[pcount-i-1].type.equals("VoidHand*")
         || params[pcount-i-1].type.equals("UBytePtr")
         || params[pcount-i-1].type.equals("UIntPtr")
         || params[pcount-i-1].type.equals("ULong")
         || params[pcount-i-1].type.equals("ULongPtr")
         || params[pcount-i-1].type.equals("WordPtr")) {
          aout.println("\tmove.l\t"+(8+4*i)+"(a6),-(a7)");
          psize += 4;
        } else if (params[pcount-i-1].type.equals("Boolean")
                || params[pcount-i-1].type.equals("char"   )
                || params[pcount-i-1].type.equals("Int"    )
                || params[pcount-i-1].type.equals("short"  )
                || params[pcount-i-1].type.equals("SWord"  )
                || params[pcount-i-1].type.equals("UInt"   )
                || params[pcount-i-1].type.equals("Word"   )) {
          aout.println("\tmove.w\t"+(8+4*i+2)+"(a6),-(a7)");
          psize += 2;
        } else if (params[pcount-i-1].type.equals("byte") ||
                   params[pcount-i-1].type.equals("Byte")) {
          aout.println("\tmove.b\t"+(8+4*i+3)+"(a6),-(a7)");
          psize += 2;
        } else if (params[pcount-i-1].type.equals("CharBuf")) {
          aout.println("\tmove.l\t"+(8+4*i)+"(a6),a0");
          aout.println("\tmove.l\t(a0),a0");
          aout.println("\tmove.l\tArray.data(a0),-(a7)");
          psize += 4;
        } else if (params[pcount-i-1].type.equals("CharPtr")) {
          aout.println("\tmove.l\t"+(8+4*i)+"(a6),a0");
          aout.println("\tmove.l\t(a0),a0");
          aout.println("\tmove.l\tArray.data(a0),-(a7)");
          psize += 4;
        } else if (params[pcount-i-1].type.equals("void*")
                || params[pcount-i-1].type.equals("VoidPtr")
                || params[pcount-i-1].type.equals("Ptr")) {
          aout.println("\tmove.l\t"+(8+4*i)+"(a6),-(a7)");
          aout.println("\tjsr\tmakevoidptr(pc)");
          psize += 4;
        } else {
          System.out.println("Unknown parameter type (asm): "+params[pcount-i-1].type);
          System.exit(1);
        }
      }
      aout.println("\ttrap\t#15");
      if (lib) {
        aout.println("\tdc.w\tsysLibTrap" + name);
      } else {
        aout.println("\tdc.w\tsysTrap" + name);
      }
      if (psize > 0) {
        if (psize > 8) {
          aout.println("\tlea\t"+psize+"(a7),a7");
        } else {
          aout.println("\taddq.l\t#"+psize+",a7");
        }
      }
      for (int i = 0; i < pcount; i++) {
        if (params[pcount-i-1].type.equals("CharBuf")) {
          aout.println("\tmove.l\td0,d2");
          aout.println("\tmove.l\t"+(8+4*i)+"(a6),a2");
          aout.println("\tmove.l\t(a2),a0");
          aout.println("\tsystrap\tStrLen(Array.data(a0).l)");
          aout.println("\tmove.w\td0,4+2(a2)");
          aout.println("\tmove.l\td2,d0");
        }
      }
      if (rettype.equals("Boolean")) {
        aout.println("\tand.l\t#$ffff,d0");
      } else if (rettype.equals("Byte")) {
        aout.println("\tand.l\t#$ff,d0");
      } else if (rettype.equals("CharPtr")) {
        aout.println("\tjsr\tCharPtr_to_String(pc)");
      } else if (rettype.equals("DWord")) {
        // nothing
      } else if (rettype.equals("Handle")) {
        aout.println("\tmove.l\ta0,d0");
      } else if (rettype.equals("Int")) {
        aout.println("\tand.l\t#$ffff,d0");
      } else if (rettype.equals("LocalID")) {
        // nothing
      } else if (rettype.equals("short")) {
        aout.println("\tand.l\t#$ffff,d0");
        aout.println("\text.l\td0");
      } else if (rettype.equals("ULong")) {
        // nothing
      } else if (rettype.equals("void")) {
        // nothing
      } else if (rettype.equals("UInt")) {
        aout.println("\tand.l\t#$ffff,d0");
      } else if (rettype.equals("Word")) {
        aout.println("\tand.l\t#$ffff,d0");
      } else {
        System.out.println("Unknown return type (asm): " + rettype);
        System.exit(1);
      }
      aout.println("\tunlk\ta6");
      aout.println("\trts");
      aout.println();
      System.out.println("  "+name);
    }
    jout.println("}");
    jout.close();
    aout.close();
  }
}
