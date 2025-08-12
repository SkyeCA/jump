/*
    Jump - Java post-compiler for PalmPilot
    Copyright (C) 1996,97  Greg Hewgill <gregh@lightspeed.net>

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/

import java.io.*;
import java.util.*;

/*

- Compile each <clinit> function in such a way that it follows this logic:
  if (this class not already initialized) {
    super.<clinit>();
    // class initialization code from classfile
  }
- Generate calls to <clinit> at startup for classes that have any static fields.
- Call the <clinit> function for a class before an object of that class is created.

int ResId(String res)
{
  int id = 0;
  int n = 4;
  for (int i = 0; i < res.length() && n > 0; i++, n--) {
    id = (id << 8) | (byte)res[i];
  }
  return id << (n*8);
}

*/

class AssertionFailedException extends RuntimeException {
  AssertionFailedException(String msg)
  {
    super(msg);
  }
}

class ASSERT {
  public static void check(boolean condition, String reason)
  {
    if (!condition) {
      fail(/*"Assertion failed: "+*/reason);
    }
  }

  public static void check(Object object, String reason)
  {
    check(object != null, reason);
  }

  public static void fail(String condition)
  {
    throw new AssertionFailedException(condition);
//    System.out.println(condition);
//    if (Jump.Verbose) {
//      new Throwable().printStackTrace();
//    }
//    System.exit(1);
  }
}

interface JVM {
  static final int ACC_PUBLIC       = 0x0001; // Class, Method, Variable
  static final int ACC_PRIVATE      = 0x0002; // Method, Variable
  static final int ACC_PROTECTED    = 0x0004; // Method, Variable
  static final int ACC_STATIC       = 0x0008; // Method, Variable
  static final int ACC_FINAL        = 0x0010; // Class, Method, Variable
  static final int ACC_SYNCHRONIZED = 0x0020; // Method
  static final int ACC_VOLATILE     = 0x0040; // Variable
  static final int ACC_TRANSIENT    = 0x0080; // Variable
  static final int ACC_NATIVE       = 0x0100; // Method
  static final int ACC_INTERFACE    = 0x0200; // Class
  static final int ACC_ABSTRACT     = 0x0400; // Class, Method

  static final int CONSTANT_Class              = 7;
  static final int CONSTANT_Fieldref           = 9;
  static final int CONSTANT_Methodref          = 10;
  static final int CONSTANT_InterfaceMethodref = 11;
  static final int CONSTANT_String             = 8;
  static final int CONSTANT_Integer            = 3;
  static final int CONSTANT_Float              = 4;
  static final int CONSTANT_Long               = 5;
  static final int CONSTANT_Double             = 6;
  static final int CONSTANT_NameAndType        = 12;
  static final int CONSTANT_Utf8               = 1;
  static final int CONSTANT_Unicode            = 2;

  static final int op_nop              = 0;
  static final int op_aconst_null      = 1;
  static final int op_iconst_m1        = 2;
  static final int op_iconst_0         = 3;
  static final int op_iconst_1         = 4;
  static final int op_iconst_2         = 5;
  static final int op_iconst_3         = 6;
  static final int op_iconst_4         = 7;
  static final int op_iconst_5         = 8;
  static final int op_lconst_0         = 9;
  static final int op_lconst_1         = 10;
  static final int op_fconst_0         = 11;
  static final int op_fconst_1         = 12;
  static final int op_fconst_2         = 13;
  static final int op_dconst_0         = 14;
  static final int op_dconst_1         = 15;
  static final int op_bipush           = 16;
  static final int op_sipush           = 17;
  static final int op_ldc1             = 18;
  static final int op_ldc2             = 19;
  static final int op_ldc2w            = 20;
  static final int op_iload            = 21;
  static final int op_lload            = 22;
  static final int op_fload            = 23;
  static final int op_dload            = 24;
  static final int op_aload            = 25;
  static final int op_iload_0          = 26;
  static final int op_iload_1          = 27;
  static final int op_iload_2          = 28;
  static final int op_iload_3          = 29;
  static final int op_lload_0          = 30;
  static final int op_lload_1          = 31;
  static final int op_lload_2          = 32;
  static final int op_lload_3          = 33;
  static final int op_fload_0          = 34;
  static final int op_fload_1          = 35;
  static final int op_fload_2          = 36;
  static final int op_fload_3          = 37;
  static final int op_dload_0          = 38;
  static final int op_dload_1          = 39;
  static final int op_dload_2          = 40;
  static final int op_dload_3          = 41;
  static final int op_aload_0          = 42;
  static final int op_aload_1          = 43;
  static final int op_aload_2          = 44;
  static final int op_aload_3          = 45;
  static final int op_iaload           = 46;
  static final int op_laload           = 47;
  static final int op_faload           = 48;
  static final int op_daload           = 49;
  static final int op_aaload           = 50;
  static final int op_baload           = 51;
  static final int op_caload           = 52;
  static final int op_saload           = 53;
  static final int op_istore           = 54;
  static final int op_lstore           = 55;
  static final int op_fstore           = 56;
  static final int op_dstore           = 57;
  static final int op_astore           = 58;
  static final int op_istore_0         = 59;
  static final int op_istore_1         = 60;
  static final int op_istore_2         = 61;
  static final int op_istore_3         = 62;
  static final int op_lstore_0         = 63;
  static final int op_lstore_1         = 64;
  static final int op_lstore_2         = 65;
  static final int op_lstore_3         = 66;
  static final int op_fstore_0         = 67;
  static final int op_fstore_1         = 68;
  static final int op_fstore_2         = 69;
  static final int op_fstore_3         = 70;
  static final int op_dstore_0         = 71;
  static final int op_dstore_1         = 72;
  static final int op_dstore_2         = 73;
  static final int op_dstore_3         = 74;
  static final int op_astore_0         = 75;
  static final int op_astore_1         = 76;
  static final int op_astore_2         = 77;
  static final int op_astore_3         = 78;
  static final int op_iastore          = 79;
  static final int op_lastore          = 80;
  static final int op_fastore          = 81;
  static final int op_dastore          = 82;
  static final int op_aastore          = 83;
  static final int op_bastore          = 84;
  static final int op_castore          = 85;
  static final int op_sastore          = 86;
  static final int op_pop              = 87;
  static final int op_pop2             = 88;
  static final int op_dup              = 89;
  static final int op_dup_x1           = 90;
  static final int op_dup_x2           = 91;
  static final int op_dup2             = 92;
  static final int op_dup2_x1          = 93;
  static final int op_dup2_x2          = 94;
  static final int op_swap             = 95;
  static final int op_iadd             = 96;
  static final int op_ladd             = 97;
  static final int op_fadd             = 98;
  static final int op_dadd             = 99;
  static final int op_isub             = 100;
  static final int op_lsub             = 101;
  static final int op_fsub             = 102;
  static final int op_dsub             = 103;
  static final int op_imul             = 104;
  static final int op_lmul             = 105;
  static final int op_fmul             = 106;
  static final int op_dmul             = 107;
  static final int op_idiv             = 108;
  static final int op_ldiv             = 109;
  static final int op_fdiv             = 110;
  static final int op_ddiv             = 111;
  static final int op_irem             = 112;
  static final int op_lrem             = 113;
  static final int op_frem             = 114;
  static final int op_drem             = 115;
  static final int op_ineg             = 116;
  static final int op_lneg             = 117;
  static final int op_fneg             = 118;
  static final int op_dneg             = 119;
  static final int op_ishl             = 120;
  static final int op_lshl             = 121;
  static final int op_ishr             = 122;
  static final int op_lshr             = 123;
  static final int op_iushr            = 124;
  static final int op_lushr            = 125;
  static final int op_iand             = 126;
  static final int op_land             = 127;
  static final int op_ior              = 128;
  static final int op_lor              = 129;
  static final int op_ixor             = 130;
  static final int op_lxor             = 131;
  static final int op_iinc             = 132;
  static final int op_i2l              = 133;
  static final int op_i2f              = 134;
  static final int op_i2d              = 135;
  static final int op_l2i              = 136;
  static final int op_l2f              = 137;
  static final int op_l2d              = 138;
  static final int op_f2i              = 139;
  static final int op_f2l              = 140;
  static final int op_f2d              = 141;
  static final int op_d2i              = 142;
  static final int op_d2l              = 143;
  static final int op_d2f              = 144;
  static final int op_int2byte         = 145;
  static final int op_int2char         = 146;
  static final int op_int2short        = 147;
  static final int op_lcmp             = 148;
  static final int op_fcmpl            = 149;
  static final int op_fcmpg            = 150;
  static final int op_dcmpl            = 151;
  static final int op_dcmpg            = 152;
  static final int op_ifeq             = 153;
  static final int op_ifne             = 154;
  static final int op_iflt             = 155;
  static final int op_ifge             = 156;
  static final int op_ifgt             = 157;
  static final int op_ifle             = 158;
  static final int op_if_icmpeq        = 159;
  static final int op_if_icmpne        = 160;
  static final int op_if_icmplt        = 161;
  static final int op_if_icmpge        = 162;
  static final int op_if_icmpgt        = 163;
  static final int op_if_icmple        = 164;
  static final int op_if_acmpeq        = 165;
  static final int op_if_acmpne        = 166;
  static final int op_goto             = 167;
  static final int op_jsr              = 168;
  static final int op_ret              = 169;
  static final int op_tableswitch      = 170;
  static final int op_lookupswitch     = 171;
  static final int op_ireturn          = 172;
  static final int op_lreturn          = 173;
  static final int op_freturn          = 174;
  static final int op_dreturn          = 175;
  static final int op_areturn          = 176;
  static final int op_return           = 177;
  static final int op_getstatic        = 178;
  static final int op_putstatic        = 179;
  static final int op_getfield         = 180;
  static final int op_putfield         = 181;
  static final int op_invokevirtual    = 182;
  static final int op_invokenonvirtual = 183;
  static final int op_invokestatic     = 184;
  static final int op_invokeinterface  = 185;
  static final int op_new              = 187;
  static final int op_newarray         = 188;
  static final int op_anewarray        = 189;
  static final int op_arraylength      = 190;
  static final int op_athrow           = 191;
  static final int op_checkcast        = 192;
  static final int op_instanceof       = 193;
  static final int op_monitorenter     = 194;
  static final int op_monitorexit      = 195;
  static final int op_wide             = 196;
  static final int op_multianewarray   = 197;
  static final int op_ifnull           = 198;
  static final int op_ifnonnull        = 199;
  static final int op_goto_w           = 200;
  static final int op_jsr_w            = 201;
  static final int op_breakpoint       = 202;
  static final int op_ret_w            = 209;

  static final byte BytecodeLength[] = {
    1, //op_nop
    1, //op_aconst_null
    1, //op_iconst_m1
    1, //op_iconst_0
    1, //op_iconst_1
    1, //op_iconst_2
    1, //op_iconst_3
    1, //op_iconst_4
    1, //op_iconst_5
    1, //op_lconst_0
    1, //op_lconst_1
    1, //op_fconst_0
    1, //op_fconst_1
    1, //op_fconst_2
    1, //op_dconst_0
    1, //op_dconst_1
    2, //op_bipush
    3, //op_sipush
    2, //op_ldc1
    3, //op_ldc2
    3, //op_ldc2w
    2, //op_iload
    2, //op_lload
    2, //op_fload
    2, //op_dload
    2, //op_aload
    1, //op_iload_0
    1, //op_iload_1
    1, //op_iload_2
    1, //op_iload_3
    1, //op_lload_0
    1, //op_lload_1
    1, //op_lload_2
    1, //op_lload_3
    1, //op_fload_0
    1, //op_fload_1
    1, //op_fload_2
    1, //op_fload_3
    1, //op_dload_0
    1, //op_dload_1
    1, //op_dload_2
    1, //op_dload_3
    1, //op_aload_0
    1, //op_aload_1
    1, //op_aload_2
    1, //op_aload_3
    1, //op_iaload
    1, //op_laload
    1, //op_faload
    1, //op_daload
    1, //op_aaload
    1, //op_baload
    1, //op_caload
    1, //op_saload
    2, //op_istore
    2, //op_lstore
    2, //op_fstore
    2, //op_dstore
    2, //op_astore
    1, //op_istore_0
    1, //op_istore_1
    1, //op_istore_2
    1, //op_istore_3
    1, //op_lstore_0
    1, //op_lstore_1
    1, //op_lstore_2
    1, //op_lstore_3
    1, //op_fstore_0
    1, //op_fstore_1
    1, //op_fstore_2
    1, //op_fstore_3
    1, //op_dstore_0
    1, //op_dstore_1
    1, //op_dstore_2
    1, //op_dstore_3
    1, //op_astore_0
    1, //op_astore_1
    1, //op_astore_2
    1, //op_astore_3
    1, //op_iastore
    1, //op_lastore
    1, //op_fastore
    1, //op_dastore
    1, //op_aastore
    1, //op_bastore
    1, //op_castore
    1, //op_sastore
    1, //op_pop
    1, //op_pop2
    1, //op_dup
    1, //op_dup_x1
    1, //op_dup_x2
    1, //op_dup2
    1, //op_dup2_x1
    1, //op_dup2_x2
    1, //op_swap
    1, //op_iadd
    1, //op_ladd
    1, //op_fadd
    1, //op_dadd
    1, //op_isub
    1, //op_lsub
    1, //op_fsub
    1, //op_dsub
    1, //op_imul
    1, //op_lmul
    1, //op_fmul
    1, //op_dmul
    1, //op_idiv
    1, //op_ldiv
    1, //op_fdiv
    1, //op_ddiv
    1, //op_irem
    1, //op_lrem
    1, //op_frem
    1, //op_drem
    1, //op_ineg
    1, //op_lneg
    1, //op_fneg
    1, //op_dneg
    1, //op_ishl
    1, //op_lshl
    1, //op_ishr
    1, //op_lshr
    1, //op_iushr
    1, //op_lushr
    1, //op_iand
    1, //op_land
    1, //op_ior
    1, //op_lor
    1, //op_ixor
    1, //op_lxor
    3, //op_iinc
    1, //op_i2l
    1, //op_i2f
    1, //op_i2d
    1, //op_l2i
    1, //op_l2f
    1, //op_l2d
    1, //op_f2i
    1, //op_f2l
    1, //op_f2d
    1, //op_d2i
    1, //op_d2l
    1, //op_d2f
    1, //op_int2byte
    1, //op_int2char
    1, //op_int2short
    1, //op_lcmp
    1, //op_fcmpl
    1, //op_fcmpg
    1, //op_dcmpl
    1, //op_dcmpg
    3, //op_ifeq
    3, //op_ifne
    3, //op_iflt
    3, //op_ifge
    3, //op_ifgt
    3, //op_ifle
    3, //op_if_icmpeq
    3, //op_if_icmpne
    3, //op_if_icmplt
    3, //op_if_icmpge
    3, //op_if_icmpgt
    3, //op_if_icmple
    3, //op_if_acmpeq
    3, //op_if_acmpne
    3, //op_goto
    3, //op_jsr
    2, //op_ret
    0, //op_tableswitch
    0, //op_lookupswitch
    1, //op_ireturn
    1, //op_lreturn
    1, //op_freturn
    1, //op_dreturn
    1, //op_areturn
    1, //op_return
    3, //op_getstatic
    3, //op_putstatic
    3, //op_getfield
    3, //op_putfield
    3, //op_invokevirtual
    3, //op_invokenonvirtual
    3, //op_invokestatic
    5, //op_invokeinterface
    0, //op_136
    3, //op_new
    2, //op_newarray
    3, //op_anewarray
    1, //op_arraylength
    1, //op_athrow
    3, //op_checkcast
    3, //op_instanceof
    1, //op_monitorenter
    1, //op_monitorexit
    1, //op_wide
    4, //op_multianewarray
    3, //op_ifnull
    3, //op_ifnonnull
    5, //op_goto_w
    5, //op_jsr_w
    1, //op_breakpoint
    0, //op_203
    0, //op_204
    0, //op_205
    0, //op_206
    0, //op_207
    0, //op_208
    3, //op_ret_w
  };
}

class TMethodStack implements Cloneable {
  private StringBuffer s = new StringBuffer();

  boolean equals(TMethodStack m)
  {
    return s.toString().equals(m.s.toString());
  }

  TMethodStack copy()
  {
    TMethodStack r = new TMethodStack();
    r.s = new StringBuffer(s.toString());
    return r;
  }

  char top()
  {
    return s.charAt(s.length()-1);
  }

  boolean touched()
  {
    return s.length() > 0;
  }

  void push(char type, int n)
  {
    if (s.length() == 0) {
      s.append('.');
    }
    switch (type) {
      case 'B':
      case 'C':
      case 'S':
      case 'Z':
        type = 'I';
        break;
      case '[':
        type = 'L';
        break;
    }
    while (n-- > 0) {
      s.append(type);
    }
  }

  void push(char type)
  {
    push(type, 1);
  }

  char pop(char type, int n)
  {
    switch (type) {
      case 'B':
      case 'C':
      case 'S':
      case 'Z':
        type = 'I';
        break;
      case '[':
        type = 'L';
        break;
    }
    char t = 0;
    while (n-- > 0) {
      t = top();
      if (type != 0) {
        ASSERT.check(t == type, "Internal error: Type mismatch on stack pop, expected="+type+", actual="+top());
      }
      s.setLength(s.length()-1);
    }
    return t;
  }

  char pop(char type)
  {
    return pop(type, 1);
  }

  char pop()
  {
    return pop((char)0, 1);
  }

  void clear()
  {
    s.setLength(0);
  }

  public String toString()
  {
    return s.toString();
  }
}

class TArgs {
  private Vector args = new Vector();
  private String ret;
  private int words;

  TArgs(String signature)
  {
    int i = 0;
    i++;
    words = 0;
    while (signature.charAt(i) != ')') {
      switch (signature.charAt(i)) {
        case 'B':
        case 'C':
        case 'F':
        case 'I':
        case 'S':
        case 'Z':
          args.addElement(new Character(signature.charAt(i)));
          words++;
          break;
        case 'D':
        case 'J':
          args.addElement(new Character(signature.charAt(i)));
          words += 2;
          break;
        case 'L': {
          words++;
          StringBuffer t = new StringBuffer();
          do {
            t.append(signature.charAt(i));
            i++;
          } while (signature.charAt(i) != ';');
          args.addElement(t);
          break;
        }
        case '[': {
          words++;
          StringBuffer t = new StringBuffer();
          while (signature.charAt(i) == '[') {
            t.append(signature.charAt(i));
            i++;
          }
          if (signature.charAt(i) == 'L') {
            do {
              t.append(signature.charAt(i));
              i++;
            } while (signature.charAt(i) != ';');
          } else {
            t.append(signature.charAt(i));
          }
          args.addElement(t);
          break;
        }
        default:
          ASSERT.fail("Unknown method signature format: "+signature);
          break;
      }
      i++;
    }
    i++;
    ret = signature.substring(i);
  }

  int Words()
  {
    return words;
  }

  int ArgCount()
  {
    return args.size();
  }

  String Arg(int i)
  {
    return args.elementAt(i).toString();
  }

  String Ret()
  {
    return ret;
  }
}

class ConstantPool implements JVM {
  cp_info[] table;

  ConstantPool(DataInput in) throws IOException
  {
    int count = in.readShort();
    table = new cp_info[count];
    table[0] = null;
    for (int i = 1; i < count; i++) {
      int tag = in.readByte();
      switch (tag) {
        case CONSTANT_Class:
          table[i] = new CONSTANT_Class_info(in);
          break;
        case CONSTANT_Fieldref:
          table[i] = new CONSTANT_Fieldref_info(in);
          break;
        case CONSTANT_Methodref:
          table[i] = new CONSTANT_Methodref_info(in);
          break;
        case CONSTANT_InterfaceMethodref:
          table[i] = new CONSTANT_InterfaceMethodref_info(in);
          break;
        case CONSTANT_String:
          table[i] = new CONSTANT_String_info(in);
          break;
        case CONSTANT_Integer:
          table[i] = new CONSTANT_Integer_info(in);
          break;
        case CONSTANT_Float:
          table[i] = new CONSTANT_Float_info(in);
          break;
        case CONSTANT_Long:
          table[i] = new CONSTANT_Long_info(in);
          i++;
          table[i] = null;
          break;
        case CONSTANT_Double:
          table[i] = new CONSTANT_Double_info(in);
          i++;
          table[i] = null;
          break;
        case CONSTANT_NameAndType:
          table[i] = new CONSTANT_NameAndType_info(in);
          break;
        case CONSTANT_Utf8:
          table[i] = new CONSTANT_Utf8_info(in);
          break;
        case CONSTANT_Unicode:
          table[i] = new CONSTANT_Unicode_info(in);
          break;
        default:
          ASSERT.fail("Unknown constant pool tag: " + tag);
          break;
      }
    }
    for (int i = 1; i < count; i++) {
      if (table[i] != null) {
        table[i].resolveStrings(this);
      }
    }
  }

  String getString(int index)
  {
    ASSERT.check(table[index], "Internal error: null table[index]");
    ASSERT.check(table[index].tag == CONSTANT_Utf8, "Internal error: table[index] not Utf8");
    return ((CONSTANT_Utf8_info)table[index]).string;
  }
}

abstract class cp_info {
  int tag;

  cp_info(int t)
  {
    tag = t;
  }

  void resolveStrings(ConstantPool pool)
  {
  }
}

class CONSTANT_Class_info extends cp_info implements JVM {
  private int name_index;
  String name;

  CONSTANT_Class_info(DataInput in) throws IOException
  {
    super(CONSTANT_Class);
    name_index = in.readShort();
  }

  void resolveStrings(ConstantPool pool)
  {
    name = pool.getString(name_index);
  }
}

class CONSTANT_NameAndType_info extends cp_info implements JVM {
  private int name_index;
  private int signature_index;
  String name;
  String signature;

  CONSTANT_NameAndType_info(DataInput in) throws IOException
  {
    super(CONSTANT_NameAndType);
    name_index = in.readShort();
    signature_index = in.readShort();
  }

  void resolveStrings(ConstantPool pool)
  {
    name = pool.getString(name_index);
    signature = pool.getString(signature_index);
  }
}

class CONSTANT_Fieldref_info extends cp_info implements JVM {
  private int class_index;
  private int name_and_type_index;
  String class_name;
  String name;
  String signature;

  CONSTANT_Fieldref_info(DataInput in) throws IOException
  {
    super(CONSTANT_Fieldref);
    class_index = in.readShort();
    name_and_type_index = in.readShort();
  }

  void resolveStrings(ConstantPool pool)
  {
    CONSTANT_Class_info classinfo = (CONSTANT_Class_info)pool.table[class_index];
    classinfo.resolveStrings(pool);
    class_name = classinfo.name;
    CONSTANT_NameAndType_info ntinfo = (CONSTANT_NameAndType_info)pool.table[name_and_type_index];
    ntinfo.resolveStrings(pool);
    name = ntinfo.name;
    signature = ntinfo.signature;
  }
}

class CONSTANT_Methodref_info extends cp_info implements JVM {
  private int class_index;
  private int name_and_type_index;
  String class_name;
  String name;
  String signature;

  CONSTANT_Methodref_info(DataInput in) throws IOException
  {
    super(CONSTANT_Methodref);
    class_index = in.readShort();
    name_and_type_index = in.readShort();
  }

  void resolveStrings(ConstantPool pool)
  {
    CONSTANT_Class_info classinfo = (CONSTANT_Class_info)pool.table[class_index];
    classinfo.resolveStrings(pool);
    class_name = classinfo.name;
    CONSTANT_NameAndType_info ntinfo = (CONSTANT_NameAndType_info)pool.table[name_and_type_index];
    ntinfo.resolveStrings(pool);
    name = ntinfo.name;
    signature = ntinfo.signature;
  }
}

class CONSTANT_InterfaceMethodref_info extends cp_info implements JVM {
  private int class_index;
  private int name_and_type_index;
  String class_name;
  String name;
  String signature;

  CONSTANT_InterfaceMethodref_info(DataInput in) throws IOException
  {
    super(CONSTANT_InterfaceMethodref);
    class_index = in.readShort();
    name_and_type_index = in.readShort();
  }

  void resolveStrings(ConstantPool pool)
  {
    CONSTANT_Class_info classinfo = (CONSTANT_Class_info)pool.table[class_index];
    classinfo.resolveStrings(pool);
    class_name = classinfo.name;
    CONSTANT_NameAndType_info ntinfo = (CONSTANT_NameAndType_info)pool.table[name_and_type_index];
    ntinfo.resolveStrings(pool);
    name = ntinfo.name;
    signature = ntinfo.signature;
  }
}

class CONSTANT_String_info extends cp_info implements JVM {
  private int string_index;
  String string;

  CONSTANT_String_info(DataInput in) throws IOException
  {
    super(CONSTANT_String);
    string_index = in.readShort();
  }

  void resolveStrings(ConstantPool pool)
  {
    string = pool.getString(string_index);
  }
}

class CONSTANT_Integer_info extends cp_info implements JVM {
  int bytes;

  CONSTANT_Integer_info(DataInput in) throws IOException
  {
    super(CONSTANT_Integer);
    bytes = in.readInt();
  }
}

class CONSTANT_Float_info extends cp_info implements JVM {
  float bytes;

  CONSTANT_Float_info(DataInput in) throws IOException
  {
    super(CONSTANT_Float);
    bytes = in.readFloat();
  }
}

class CONSTANT_Long_info extends cp_info implements JVM {
  long bytes;

  CONSTANT_Long_info(DataInput in) throws IOException
  {
    super(CONSTANT_Long);
    bytes = in.readLong();
  }
}

class CONSTANT_Double_info extends cp_info implements JVM {
  private int high_bytes;
  private int low_bytes;

  CONSTANT_Double_info(DataInput in) throws IOException
  {
    super(CONSTANT_Double);
    high_bytes = in.readInt();
    low_bytes = in.readInt();
  }
}

class CONSTANT_Utf8_info extends cp_info implements JVM {
  String string;

  CONSTANT_Utf8_info(DataInput in) throws IOException
  {
    super(CONSTANT_Utf8);
    string = in.readUTF();
  }
}

class CONSTANT_Unicode_info extends cp_info implements JVM {
  private String string;

  CONSTANT_Unicode_info(DataInput in) throws IOException
  {
    super(CONSTANT_Unicode);
    int len = in.readShort();
    StringBuffer buf = new StringBuffer(len);
    for (int i = 0; i < len; i++) {
      buf.setCharAt(i, in.readChar());
    }
    string = new String(buf);
  }
}

class attribute_table {
  private GenericAttribute_info[] table;

  attribute_table(ConstantPool pool, DataInput in) throws IOException
  {
    int count = in.readShort();
    table = new GenericAttribute_info[count];
    for (int i = 0; i < count; i++) {
      int attribute_name = in.readShort();
      int attribute_length = in.readInt();
      String name = pool.getString(attribute_name);
      if (name.equals("SourceFile")) {
        table[i] = new SourceFile_attribute(name, pool, in);
      } else if (name.equals("ConstantValue")) {
        table[i] = new ConstantValue_attribute(name, in);
      } else if (name.equals("Code")) {
        table[i] = new Code_attribute(name, pool, in);
      } else if (name.equals("Exceptions")) {
        table[i] = new Exceptions_attribute(name, in);
      } else if (name.equals("LineNumberTable")) {
        table[i] = new Unknown_attribute(name, attribute_length, in);
        //table[i] = new LineNumberTable_table(name, in);
      } else if (name.equals("LocalVariableTable")) {
        table[i] = new Unknown_attribute(name, attribute_length, in);
        //table[i] = new LocalVariableTable_attribute(name, in);
      } else {
        table[i] = new Unknown_attribute(name, attribute_length, in);
      }
    }
  }

  GenericAttribute_info get(String name)
  {
    for (int i = 0; i < table.length; i++) {
      if (name.equals(table[i].name)) {
        return table[i];
      }
    }
    return null;
  }
}

abstract class GenericAttribute_info {
  String name;

  GenericAttribute_info(String aname)
  {
    name = aname;
  }
}

class Unknown_attribute extends GenericAttribute_info {
  Unknown_attribute(String aname, int length, DataInput in) throws IOException
  {
    super(aname);
    in.skipBytes(length);
  }
}

class SourceFile_attribute extends GenericAttribute_info {
  private int sourcefile_index;
  private String sourcefile;

  SourceFile_attribute(String aname, ConstantPool pool, DataInput in) throws IOException
  {
    super(aname);
    sourcefile_index = in.readShort();
    sourcefile = pool.getString(sourcefile_index);
  }
}

class ConstantValue_attribute extends GenericAttribute_info {
  int constantvalue_index;

  ConstantValue_attribute(String aname, DataInput in) throws IOException
  {
    super(aname);
    constantvalue_index = in.readShort();
  }
}

class exception_table_entry {
  int start_pc;
  int end_pc;
  int handler_pc;
  int catch_type_index;
  Klass catch_type;
}

class Code_attribute extends GenericAttribute_info {
  private int max_stack;
  int max_locals;
  private int code_length;
  byte[] code;
  private int exception_table_length;
  exception_table_entry[] exception_table;
  private attribute_table attributes;

  Code_attribute(String aname, ConstantPool pool, DataInput in) throws IOException
  {
    super(aname);
    max_stack = in.readShort();
    max_locals = in.readShort();
    code_length = in.readInt();
    code = new byte[code_length];
    in.readFully(code);
    exception_table_length = in.readShort();
    exception_table = new exception_table_entry[exception_table_length];
    for (int i = 0; i < exception_table_length; i++) {
      exception_table[i] = new exception_table_entry();
      exception_table[i].start_pc = in.readShort();
      exception_table[i].end_pc = in.readShort();
      exception_table[i].handler_pc = in.readShort();
      exception_table[i].catch_type_index = in.readShort();
      if (exception_table[i].catch_type_index == 0) {
        exception_table[i].catch_type = Klass.Load("java/lang/Object");
      } else {
        exception_table[i].catch_type = Klass.Load(((CONSTANT_Class_info)pool.table[exception_table[i].catch_type_index]).name);
      }
    }
    attributes = new attribute_table(pool, in);
  }
}

class Exceptions_attribute extends GenericAttribute_info {
  private int number_of_exceptions;
  private int[] exception_index_table;

  Exceptions_attribute(String aname, DataInput in) throws IOException
  {
    super(aname);
    number_of_exceptions = in.readShort();
    exception_index_table = new int[number_of_exceptions];
    for (int i = 0; i < number_of_exceptions; i++) {
      exception_index_table[i] = in.readShort();
    }
  }
}

class field_info {
  int access_flags;
  private int name_index;
  private int signature_index;
  attribute_table attributes;
  String name;
  String signature;
  boolean Generate;
  int Offset;

  field_info()
  {
    access_flags = 0;
    name_index = 0;
    signature_index = 0;
    attributes = null;
    name = null;
    signature = null;
    Generate = false;
  }

  field_info(ConstantPool pool, DataInput in) throws IOException
  {
    access_flags = in.readShort();
    name_index = in.readShort();
    signature_index = in.readShort();
    attributes = new attribute_table(pool, in);
    name = pool.getString(name_index);
    signature = pool.getString(signature_index);
    Generate = false;
  }
}

class method_info implements JVM {
  int access_flags;
  private int name_index;
  private int signature_index;
  attribute_table attributes;
  String name;
  String signature;
  boolean Generate;
  int VtableIndex;
  BitSet Labels;
  TMethodStack[] LabelOriginStack;

  private int max_locals;
  int args;

  method_info(ConstantPool pool, DataInput in) throws IOException
  {
    access_flags = in.readShort();
    name_index = in.readShort();
    signature_index = in.readShort();
    attributes = new attribute_table(pool, in);
    name = pool.getString(name_index);
    signature = pool.getString(signature_index);
    Generate = false;
    VtableIndex = -1;
    Code_attribute code = (Code_attribute)attributes.get("Code");
    if (code != null) {
      Labels = new BitSet(code.code.length);
      LabelOriginStack = new TMethodStack[code.code.length];
      max_locals = code.max_locals;
      args = new TArgs(signature).Words();
      if ((access_flags & ACC_STATIC) == 0) {
        args++;
      }
    }
  }

  void CheckStack(int dest, TMethodStack stack)
  {
    if (LabelOriginStack[dest] == null || !LabelOriginStack[dest].touched()) {
      LabelOriginStack[dest] = stack.copy();
    }
    ASSERT.check(LabelOriginStack[dest].equals(stack), "Internal error: Stack mismatch, expected="+LabelOriginStack[dest]+", actual="+stack);
  }

  int local(int x)
  {
    ASSERT.check(x >= 0, "Internal error: Local variable < 0");
    ASSERT.check(x < max_locals, "Internal error: Local variable > max_locals");
    if (x < args) {
      return 4*(3+args-x-1);
    } else {
      return -4*(x-args+1);
    }
  }
}

class NativeLocation {
  String fn;
  long offset;
  NativeLocation(String afn, long aoffset)
  {
    fn = afn;
    offset = aoffset;
  }
}

class Klass implements JVM {
  int magic;
  int minor_version;
  int major_version;
  ConstantPool constant_pool;
  int access_flags;
  int this_class;
  int super_class;
  int interfaces_count;
  short[] interfaces;
  int fields_count;
  field_info[] fields;
  int methods_count;
  method_info[] methods;
  attribute_table attributes;

  private String ClassName;
  Klass SuperClass;
  int Index;
  private static int NextIndex;
  private int DataSize;
  private int VtableSize;

  private static Hashtable Classes = new Hashtable();
  static Vector ClassList = new Vector();
  static Vector Strings = new Vector();
  static boolean[] BytecodeUsed = new boolean[256];

  static final int ClassInfo_array          = 0x0001;
  static final int ClassInfo_arrayofobjects = 0x0002;

  Klass(String classname)
  {
    ClassName = classname;
    magic = 0;
    minor_version = 0;
    major_version = 0;
    constant_pool = null;
    access_flags = 0;
    this_class = 0;
    super_class = 0;
    interfaces_count = 0;
    interfaces = null;
    fields_count = 0;
    fields = null;
    methods_count = 0;
    methods = null;
    attributes = null;
    SuperClass = null;
  }

  void PostConstruct()
  {
    Index = NextIndex++;
    DataSize = 0;
    if (SuperClass != null) {
      DataSize = SuperClass.DataSize;
    }
    for (int i = 0; i < fields_count; i++) {
      if ((fields[i].access_flags & ACC_FINAL) == 0) {
        if (fields[i].signature.charAt(0) != 'B' && (DataSize & 1) != 0) {
          DataSize++;
        }
        fields[i].Offset = DataSize;
        switch (fields[i].signature.charAt(0)) {
          case 'B':
            DataSize += 1;
            break;
          case 'C':
          case 'S':
          case 'Z':
            DataSize += 2;
            break;
          case 'F':
          case 'I':
          case 'L':
          case '[':
            DataSize += 4;
            break;
          case 'D':
          case 'J':
            DataSize += 8;
            break;
          default:
            ASSERT.fail("Unknown field data type signature ("+ClassName+"."+fields[i].name+"): "+fields[i].signature);
            break;
        }
      } else {
        fields[i].Offset = -1;
      }
    }
    if ((DataSize & 1) != 0) {
      DataSize++;
    }
    VtableSize = 0;
    if (SuperClass != null) {
      VtableSize = SuperClass.VtableSize;
    }
    for (int i = 0; i < methods_count; i++) {
      method_info m = methods[i];
      if ((m.access_flags & ACC_STATIC) == 0
       && !m.name.equals("<init>")) {
        method_info sm;
        if (SuperClass != null && (sm = SuperClass.FindMethod(m.name, m.signature)) != null) {
          m.VtableIndex = sm.VtableIndex;
        } else {
          m.VtableIndex = VtableSize++;
        }
      }
    }
  }

  field_info FindField(String name)
  {
    for (int i = 0; i < fields_count; i++) {
      if (name.equals(fields[i].name)) {
        return fields[i];
      }
    }
    return null;
  }

  method_info FindMethod(String name, String signature)
  {
    Klass cls = this;
    while (cls != null) {
      for (int i = 0; i < cls.methods_count; i++) {
        if (name.equals(cls.methods[i].name) && signature.equals(cls.methods[i].signature)) {
          return cls.methods[i];
        }
      }
      cls = cls.SuperClass;
    }
    return null;
  }

  private static Hashtable Labels = new Hashtable();
  private static int NextLabel = 0;
  String Mangle(String name, String signature, boolean shorter)
  {
    StringBuffer result;
    if (signature != null) {
      result = new StringBuffer(ClassName + "__" + name + "__" + signature);
    } else {
      result = new StringBuffer(ClassName + "__" + name);
    }
    for (int i = 0; i < result.length(); i++) {
      if (!Character.isLetterOrDigit(result.charAt(i)) && result.charAt(i) != '_') {
        result.setCharAt(i, '$');
      }
    }
    if (shorter) {
      Integer label = (Integer)Labels.get(result.toString());
      if (label == null) {
        label = new Integer(NextLabel++);
        Labels.put(result.toString(), label);
      }
      result = new StringBuffer("M" + label);
    }
    return new String(result);
  }

  String Mangle(String name, String signature)
  {
    return Mangle(name, signature, true);
  }

  String Mangle(String name)
  {
    return Mangle(name, null, true);
  }

  boolean CheckGeneratedSuperClassMethods()
  {
    boolean r = false;
    for (int i = 0; i < methods_count; i++) {
      method_info m = methods[i];
      if (!m.Generate && (m.access_flags & ACC_STATIC) == 0) {
        if (SuperClass != null) {
          method_info sm = SuperClass.FindMethod(m.name, m.signature);
          if (sm != null && sm.Generate) {
            Preprocess(m);
            r = true;
          }
        }
      }
    }
    return r;
  }

  void GenerateClassInfo(PrintWriter asmout)
  {
    asmout.println("\tdc.w\t" + Klass.Load("java/lang/Class").Index);
    if (SuperClass != null) {
      asmout.println("\tdc.w\t" + SuperClass.Index + "\t; class " + ClassName + " extends " + SuperClass.ClassName);
    } else {
      asmout.println("\tdc.w\t-1\t; class " + ClassName);
    }
    asmout.println("\tdc.w\tObjectHeader_sizeof+" + DataSize);
    asmout.println("\tdc.w\t" + ((ClassName.charAt(0) == '[' ? ClassInfo_array : 0) | (ClassName.substring(0, 2).equals("[L") || ClassName.substring(0, 2).equals("[[") ? ClassInfo_arrayofobjects : 0)));
    asmout.println("\tdc.l\tclass" + Index + "__name");
    asmout.println("\tdc.l\tclass" + Index + "__vtable");
    int count = 0;
    for (int i = 0; i < methods_count; i++) {
      if (methods[i].VtableIndex >= 0 && methods[i].Generate) {
        count++;
      }
    }
    asmout.println("\tdc.w\t" + count);
    asmout.println();
  }

  void GenerateVtable(PrintWriter asmout)
  {
    asmout.println("class" + Index + "__name\tdc.b\t'" + ClassName + "',0");
    asmout.println("\talign\t2");
    asmout.println("class" + Index + "__vtable:");
    for (int i = 0; i < VtableSize; i++) {
      for (int j = 0; j < methods_count; j++) {
        method_info m = methods[j];
        if (m.VtableIndex == i) {
          if (m.Generate) {
            asmout.println("\tdc.w\t" + i);
            asmout.println("\tdc.l\t" + Mangle(m.name, m.signature) + "\t; " + ClassName + "." + m.name + m.signature);
          }
          break;
        }
      }
    }
    asmout.println();
  }

  int GenerateStatic(PrintWriter asmout, boolean objects)
  {
    int count = 0;
    for (int i = 0; i < fields_count; i++) {
      if ((fields[i].access_flags & ACC_STATIC) != 0
       && ((fields[i].access_flags & ACC_FINAL) == 0 || fields[i].attributes.get("ConstantValue") == null)
       && fields[i].Generate) {
        switch (fields[i].signature.charAt(0)) {
          case 'Z':
            if (!objects && asmout != null) {
              asmout.println(Mangle(fields[i].name) + "\tds.w\t1\t; " + ClassName + "." + fields[i].name);
            }
            break;
          case 'B':
            if (!objects && asmout != null) {
              asmout.println(Mangle(fields[i].name) + "\tds.b\t1\t; " + ClassName + "." + fields[i].name);
            }
            break;
          case 'S':
            if (!objects && asmout != null) {
              asmout.println(Mangle(fields[i].name) + "\tds.w\t1\t; " + ClassName + "." + fields[i].name);
            }
            break;
          case 'I':
          case 'F':
            if (!objects && asmout != null) {
              asmout.println(Mangle(fields[i].name) + "\tds.l\t1\t; " + ClassName + "." + fields[i].name);
            }
            break;
          case 'L':
          case '[':
            if (objects) {
              if (asmout != null) {
                asmout.println(Mangle(fields[i].name) + "\tds.l\t1\t; " + ClassName + "." + fields[i].name);
              }
              count++;
            }
            break;
          case 'J':
          case 'D':
            if (!objects && asmout != null) {
              asmout.println(Mangle(fields[i].name) + "\tds.l\t2\t; " + ClassName + "." + fields[i].name);
            }
            break;
          default:
            ASSERT.fail("Unknown static field data type signature ("+ClassName+"."+fields[i].name+"): "+fields[i].signature);
            break;
        }
      }
    }
    return count;
  }

  void GenerateCode(PrintWriter asmout)
  {
    asmout.println("*=========================================================*");
    asmout.println("* class " + ClassName + "                                                  ".substring(ClassName.length()) + "*");
    asmout.println("*=========================================================*");
    asmout.println();
    for (int i = 0; i < methods_count; i++) {
      method_info m = methods[i];
      if (m.Generate) {
        asmout.println("*---------------------------------------------------------");
        asmout.println("* method " + ClassName + "." + m.name + m.signature);
        asmout.println();
        asmout.println(Mangle(m.name, m.signature) + ":");
        if ((m.access_flags & ACC_NATIVE) == 0) {
          if (Jump.Verbose) {
            System.out.println("method " + ClassName + "." + m.name + m.signature);
          }
          Code_attribute code = (Code_attribute)m.attributes.get("Code");
          ASSERT.check(code, "No 'Code' attribute for method "+ClassName+"."+m.name+m.signature);
          if (code.exception_table.length > 0) {
            asmout.println("\tpea\t"+Mangle(m.name, m.signature)+"__exceptions(pc)");
          } else {
            asmout.println("\tclr.l\t-(a7)");
          }
          ASSERT.check(m.args <= code.max_locals, "Internal error: More args than locals");
          int localonly = code.max_locals - m.args;
          asmout.println("\tlink\ta6,#-" + 4*localonly);
          if (localonly > 0) {
            if (localonly <= 4) {
              for (int j = 0; j < localonly; j++) {
                asmout.println("\tclr.l\t-" + 4*(j+1) + "(a6)");
              }
            } else {
              asmout.println("\tsystrap\tMemSet(&-" + 4*localonly + "(a6).l,#" + 4*localonly + ".l,#0.b)");
            }
          }
          TMethodStack stack = new TMethodStack();
          int p = 0;
          DataInputStream codestream = new DataInputStream(new ByteArrayInputStream(code.code));
          try {
            boolean wide = false;
            while (p < code.code.length) {
              int ofs = p;
              if (m.Labels.get(ofs)) {
                asmout.println(Mangle(m.name, m.signature) + "__" + ofs + ":");
                if (stack.touched()) {
                  if (m.LabelOriginStack[ofs] != null && m.LabelOriginStack[ofs].touched() && !stack.equals(m.LabelOriginStack[ofs])) {
                    ASSERT.fail("Internal error: Stack mismatch, expected="+stack+", actual="+m.LabelOriginStack[ofs]);
                    System.exit(1);
                  }
                  m.LabelOriginStack[ofs] = stack.copy();
                } else if (m.LabelOriginStack[ofs] != null) {
                  stack = m.LabelOriginStack[ofs].copy();
                }
              }
              //asmout.println("; "+stack);
              int t = 0, u = 0;
              int op = codestream.readUnsignedByte();
              p++;
              switch (op) {
                case op_nop:
                  break;
                case op_aconst_null:
                  asmout.println("\tclr.l\t-(a7)");
                  stack.push('L');
                  break;
                case op_iconst_m1:
                  asmout.println("\tmoveq.l\t#-1,d0");
                  asmout.println("\tmove.l\td0,-(a7)");
                  stack.push('I');
                  break;
                case op_iconst_0:
                  asmout.println("\tclr.l\t-(a7)");
                  stack.push('I');
                  break;
                case op_iconst_1:
                case op_iconst_2:
                case op_iconst_3:
                case op_iconst_4:
                case op_iconst_5:
                  asmout.println("\tmoveq.l\t#" + (op-op_iconst_0) + ",d0");
                  asmout.println("\tmove.l\td0,-(a7)");
                  stack.push('I');
                  break;
                case op_lconst_0:
                  asmout.println("\tclr.l\t-(a7)");
                  asmout.println("\tclr.l\t-(a7)");
                  stack.push('J');
                  break;
                case op_lconst_1:
                  asmout.println("\tmoveq.l\t#1,d0");
                  asmout.println("\tmove.l\td0,-(a7)");
                  asmout.println("\tclr.l\t-(a7)");
                  stack.push('J');
                  break;
                case op_fconst_0:
                  asmout.println("\tclr.l\t-(a7)");
                  stack.push('F');
                  break;
                case op_fconst_1:
                  asmout.println("\tmove.l\tF_CONST_1,-(a7)");
                  stack.push('F');
                  break;
                case op_fconst_2:
                  asmout.println("\tmove.l\tF_CONST_2,-(a7)");
                  stack.push('F');
                  break;
                case op_dconst_0:
                  ASSERT.fail("Unimplemented: 'double' type");
                  break;
                case op_dconst_1:
                  ASSERT.fail("Unimplemented: 'double' type");
                  break;
                case op_bipush:
                  t = codestream.readByte();
                  p++;
                  if (t < 128) {
                    asmout.println("\tmoveq.l\t#" + t + ",d0");
                    asmout.println("\tmove.l\td0,-(a7)");
                  } else {
                    asmout.println("\tmove.l\t#" + t + ",-(a7)");
                  }
                  stack.push('I');
                  break;
                case op_sipush:
                  t = codestream.readShort();
                  p += 2;
                  if (t >= -128 && t < 128) {
                    asmout.println("\tmoveq.l\t#" + t + ",d0");
                    asmout.println("\tmove.l\td0,-(a7)");
                  } else {
                    asmout.println("\tmove.l\t#" + t + ",-(a7)");
                  }
                  stack.push('I');
                  break;
                case op_ldc1:
                  t = codestream.readUnsignedByte();
                  p++;
                  push_constant(asmout, t, stack);
                  break;
                case op_ldc2:
                case op_ldc2w:
                  t = codestream.readUnsignedShort();
                  p += 2;
                  push_constant(asmout, t, stack);
                  break;
                case op_iload:
                  if (wide) {
                    t = codestream.readUnsignedShort();
                    p += 2;
                  } else {
                    t = codestream.readUnsignedByte();
                    p++;
                  }
                  asmout.println("\tmove.l\t" + m.local(t) + "(a6),-(a7)");
                  stack.push('I');
                  break;
                case op_lload:
                  if (wide) {
                    t = codestream.readUnsignedShort();
                    p += 2;
                  } else {
                    t = codestream.readUnsignedByte();
                    p++;
                  }
                  asmout.println("\tmove.l\t" + m.local(t) + "(a6),-(a7)");
                  asmout.println("\tmove.l\t" + m.local(t+1) + "(a6),-(a7)");
                  stack.push('J');
                  break;
                case op_fload:
                  if (wide) {
                    t = codestream.readUnsignedShort();
                    p += 2;
                  } else {
                    t = codestream.readUnsignedByte();
                    p++;
                  }
                  asmout.println("\tmove.l\t" + m.local(t) + "(a6),-(a7)");
                  stack.push('F');
                  break;
                case op_dload:
                  ASSERT.fail("Unimplemented: 'double' type");
                  break;
                case op_aload:
                  if (wide) {
                    t = codestream.readUnsignedShort();
                    p += 2;
                  } else {
                    t = codestream.readUnsignedByte();
                    p++;
                  }
                  asmout.println("\tmove.l\t" + m.local(t) + "(a6),-(a7)");
                  stack.push('L');
                  break;
                case op_iload_0:
                case op_iload_1:
                case op_iload_2:
                case op_iload_3:
                  asmout.println("\tmove.l\t" + m.local(op-op_iload_0) + "(a6),-(a7)");
                  stack.push('I');
                  break;
                case op_lload_0:
                case op_lload_1:
                case op_lload_2:
                case op_lload_3:
                  asmout.println("\tmove.l\t" + m.local(op-op_lload_0) + "(a6),-(a7)");
                  asmout.println("\tmove.l\t" + m.local(op-op_lload_0+1) + "(a6),-(a7)");
                  stack.push('J');
                  break;
                case op_fload_0:
                case op_fload_1:
                case op_fload_2:
                case op_fload_3:
                  asmout.println("\tmove.l\t" + m.local(op-op_fload_0) + "(a6),-(a7)");
                  stack.push('F');
                  break;
                case op_dload_0:
                case op_dload_1:
                case op_dload_2:
                case op_dload_3:
                  ASSERT.fail("Unimplemented: 'double' type");
                  break;
                case op_aload_0:
                case op_aload_1:
                case op_aload_2:
                case op_aload_3:
                  asmout.println("\tmove.l\t" + m.local(op-op_aload_0) + "(a6),-(a7)");
                  stack.push('L');
                  break;
                case op_iaload:
                  stack.pop('I');
                  stack.pop('L');
                  asmout.println("\tjsr\top_iaload(pc)");
                  stack.push('I');
                  break;
                case op_laload:
                  stack.pop('I');
                  stack.pop('L');
                  asmout.println("\tjsr\top_laload(pc)");
                  stack.push('J');
                  break;
                case op_faload:
                  stack.pop('I');
                  stack.pop('L');
                  asmout.println("\tjsr\top_faload(pc)");
                  stack.push('F');
                  break;
                case op_daload:
                  stack.pop('I');
                  stack.pop('L');
                  asmout.println("\tjsr\top_daload(pc)");
                  stack.push('D');
                  break;
                case op_aaload:
                  stack.pop('I');
                  stack.pop('L');
                  asmout.println("\tjsr\top_aaload(pc)");
                  stack.push('L');
                  break;
                case op_baload:
                  stack.pop('I');
                  stack.pop('L');
                  asmout.println("\tjsr\top_baload(pc)");
                  stack.push('I');
                  break;
                case op_caload:
                  stack.pop('I');
                  stack.pop('L');
                  asmout.println("\tjsr\top_caload(pc)");
                  stack.push('I');
                  break;
                case op_saload:
                  stack.pop('I');
                  stack.pop('L');
                  asmout.println("\tjsr\top_saload(pc)");
                  stack.push('I');
                  break;
                case op_istore:
                  if (wide) {
                    t = codestream.readUnsignedShort();
                    p += 2;
                  } else {
                    t = codestream.readUnsignedByte();
                    p++;
                  }
                  stack.pop('I');
                  asmout.println("\tmove.l\t(a7)+," + m.local(t) + "(a6)");
                  break;
                case op_lstore:
                  if (wide) {
                    t = codestream.readUnsignedShort();
                    p += 2;
                  } else {
                    t = codestream.readUnsignedByte();
                    p++;
                  }
                  stack.pop('J');
                  asmout.println("\tmove.l\t(a7)+," + m.local(t) + "(a6)");
                  asmout.println("\tmove.l\t(a7)+," + m.local(t+1) + "(a6)");
                  break;
                case op_fstore:
                  if (wide) {
                    t = codestream.readUnsignedShort();
                    p += 2;
                  } else {
                    t = codestream.readUnsignedByte();
                    p++;
                  }
                  stack.pop('F');
                  asmout.println("\tmove.l\t(a7)+," + m.local(t) + "(a6)");
                  break;
                case op_dstore:
                  ASSERT.fail("Unimplemented: 'double' type");
                  break;
                case op_astore:
                  if (wide) {
                    t = codestream.readUnsignedShort();
                    p += 2;
                  } else {
                    t = codestream.readUnsignedByte();
                    p++;
                  }
                  stack.pop('L');
                  asmout.println("\tmove.l\t(a7)+," + m.local(t) + "(a6)");
                  break;
                case op_istore_0:
                case op_istore_1:
                case op_istore_2:
                case op_istore_3:
                  stack.pop('I');
                  asmout.println("\tmove.l\t(a7)+," + m.local(op-op_istore_0) + "(a6)");
                  break;
                case op_lstore_0:
                case op_lstore_1:
                case op_lstore_2:
                case op_lstore_3:
                  stack.pop('J');
                  asmout.println("\tmove.l\t(a7)+," + m.local(op-op_lstore_0) + "(a6)");
                  asmout.println("\tmove.l\t(a7)+," + m.local(op-op_lstore_0+1) + "(a6)");
                  break;
                case op_fstore_0:
                case op_fstore_1:
                case op_fstore_2:
                case op_fstore_3:
                  stack.pop('F');
                  asmout.println("\tmove.l\t(a7)+," + m.local(op-op_fstore_0) + "(a6)");
                  break;
                case op_dstore_0:
                case op_dstore_1:
                case op_dstore_2:
                case op_dstore_3:
                  ASSERT.fail("Unimplemented: 'double' type");
                  break;
                case op_astore_0:
                case op_astore_1:
                case op_astore_2:
                case op_astore_3:
                  stack.pop('L');
                  asmout.println("\tmove.l\t(a7)+," + m.local(op-op_astore_0) + "(a6)");
                  break;
                case op_iastore:
                  stack.pop('I', 2);
                  stack.pop('L');
                  asmout.println("\tjsr\top_iastore(pc)");
                  break;
                case op_lastore:
                  stack.pop('J');
                  stack.pop('I');
                  stack.pop('L');
                  asmout.println("\tjsr\top_lastore(pc)");
                  break;
                case op_fastore:
                  stack.pop('F');
                  stack.pop('I');
                  stack.pop('L');
                  asmout.println("\tjsr\top_fastore(pc)");
                  break;
                case op_dastore:
                  stack.pop('D');
                  stack.pop('I');
                  stack.pop('L');
                  asmout.println("\tjsr\top_dastore(pc)");
                  break;
                case op_aastore:
                  stack.pop('L');
                  stack.pop('I');
                  stack.pop('L');
                  asmout.println("\tjsr\top_aastore(pc)");
                  break;
                case op_bastore:
                  stack.pop('I');
                  stack.pop('I');
                  stack.pop('L');
                  asmout.println("\tjsr\top_bastore(pc)");
                  break;
                case op_castore:
                  stack.pop('I');
                  stack.pop('I');
                  stack.pop('L');
                  asmout.println("\tjsr\top_castore(pc)");
                  break;
                case op_sastore:
                  stack.pop('I');
                  stack.pop('I');
                  stack.pop('L');
                  asmout.println("\tjsr\top_sastore(pc)");
                  break;
                case op_pop:
                  stack.pop();
                  asmout.println("\taddq.l\t#4,a7");
                  break;
                case op_pop2:
                  asmout.println("\taddq.l\t#8,a7");
                  break;
                case op_dup:
                  asmout.println("\tmove.l\t(a7),-(a7)");
                  stack.push(stack.top());
                  break;
                case op_dup_x1: {
                  char t1 = stack.pop();
                  char t2 = stack.pop();
                  asmout.println("\tmove.l\t(a7),-(a7)");
                  asmout.println("\tmove.l\t8(a7),4(a7)");
                  asmout.println("\tmove.l\t(a7),8(a7)");
                  stack.push(t1);
                  stack.push(t2);
                  stack.push(t1);
                  break;
                }
                case op_dup_x2:
                  ASSERT.fail("Unimplemented: Opcode 'dup_x2'");
                  break;
                case op_dup2: {
                  char t1 = stack.pop();
                  char t2 = stack.pop();
                  asmout.println("\tmove.l\t4(a7),-(a7)");
                  asmout.println("\tmove.l\t4(a7),-(a7)");
                  stack.push(t2);
                  stack.push(t1);
                  stack.push(t2);
                  stack.push(t1);
                  break;
                }
                case op_dup2_x1:
                  ASSERT.fail("Unimplemented: Opcode 'dup2_x1'");
                  break;
                case op_dup2_x2:
                  ASSERT.fail("Unimplemented: Opcode 'dup2_x2'");
                  break;
                case op_swap:
                  char t1 = stack.pop();
                  char t2 = stack.pop();
                  asmout.println("\tmove.l\t(a7),d0");
                  asmout.println("\tmove.l\t4(a7),(a7)");
                  asmout.println("\tmove.l\td0,4(a7)");
                  stack.push(t1);
                  stack.push(t2);
                  break;
                case op_iadd:
                  stack.pop('I', 2);
                  asmout.println("\tmove.l\t(a7)+,d0");
                  asmout.println("\tadd.l\td0,(a7)");
                  stack.push('I');
                  break;
                case op_ladd:
                  ASSERT.fail("Unimplemented: 'long' add");
                  break;
                case op_fadd:
                  asmout.println("\tjsr\top_fadd(pc)");
                  break;
                case op_dadd:
                  ASSERT.fail("Unimplemented: 'double' type");
                  break;
                case op_isub:
                  stack.pop('I', 2);
                  asmout.println("\tmove.l\t(a7)+,d0");
                  asmout.println("\tsub.l\td0,(a7)");
                  stack.push('I');
                  break;
                case op_lsub:
                  ASSERT.fail("Unimplemented: 'long' subtract");
                  break;
                case op_fsub:
                  asmout.println("\tjsr\top_fsub(pc)");
                  break;
                case op_dsub:
                  ASSERT.fail("Unimplemented: 'double' type");
                  break;
                case op_imul:
                  stack.pop('I', 2);
                  asmout.println("\tjsr\top_imul(pc)");
                  stack.push('I');
                  break;
                case op_lmul:
                  ASSERT.fail("Unimplemented: 'long' multiply");
                  break;
                case op_fmul:
                  asmout.println("\tjsr\top_fmul(pc)");
                  break;
                case op_dmul:
                  ASSERT.fail("Unimplemented: 'double' type");
                  break;
                case op_idiv:
                  stack.pop('I', 2);
                  asmout.println("\tjsr\top_idiv(pc)");
                  stack.push('I');
                  break;
                case op_ldiv:
                  ASSERT.fail("Unimplemented: 'long' divide");
                  break;
                case op_fdiv:
                  asmout.println("\tjsr\top_fdiv(pc)");
                  break;
                case op_ddiv:
                  ASSERT.fail("Unimplemented: 'double' type");
                  break;
                case op_irem:
                  stack.pop('I', 2);
                  asmout.println("\tjsr\top_irem(pc)");
                  stack.push('I');
                  break;
                case op_lrem:
                  ASSERT.fail("Unimplemented: 'long' remainder");
                  break;
                case op_frem:
                  asmout.println("\tjsr\top_frem(pc)");
                  break;
                case op_drem:
                  ASSERT.fail("Unimplemented: 'double' type");
                  break;
                case op_ineg:
                  asmout.println("\tneg.l\t(a7)");
                  break;
                case op_lneg:
                  ASSERT.fail("Unimplemented: 'long' negate");
                  break;
                case op_fneg:
                  asmout.println("\tjsr\top_fneg(pc)");
                  break;
                case op_dneg:
                  ASSERT.fail("Unimplemented: 'double' type");
                  break;
                case op_ishl:
                  stack.pop('I', 2);
                  asmout.println("\tmove.l\t(a7)+,d0");
                  asmout.println("\tmove.l\t(a7),d1");
                  asmout.println("\tlsl.l\td0,d1");
                  asmout.println("\tmove.l\td1,(a7)");
                  stack.push('I');
                  break;
                case op_lshl:
                  stack.pop('I');
                  stack.pop('J');
                  asmout.println("\tjsr\top_lshl(pc)");
                  stack.push('J');
                  break;
                case op_ishr:
                  stack.pop('I', 2);
                  asmout.println("\tmove.l\t(a7)+,d0");
                  asmout.println("\tmove.l\t(a7),d1");
                  asmout.println("\tasr.l\td0,d1");
                  asmout.println("\tmove.l\td1,(a7)");
                  stack.push('I');
                  break;
                case op_lshr:
                  ASSERT.fail("Unimplemented: 'long' shift right");
                  break;
                case op_iushr:
                  stack.pop('I', 2);
                  asmout.println("\tmove.l\t(a7)+,d0");
                  asmout.println("\tmove.l\t(a7),d1");
                  asmout.println("\tlsr.l\td0,d1");
                  asmout.println("\tmove.l\td1,(a7)");
                  stack.push('I');
                  break;
                case op_lushr:
                  ASSERT.fail("Unimplemented: 'long' unsigned shift right");
                  break;
                case op_iand:
                  stack.pop('I', 2);
                  asmout.println("\tmove.l\t(a7)+,d0");
                  asmout.println("\tand.l\td0,(a7)");
                  stack.push('I');
                  break;
                case op_land:
                  stack.pop('J', 2);
                  asmout.println("\tmove.l\t(a7)+,d0");
                  asmout.println("\tand.l\td0,4(a7)");
                  asmout.println("\tmove.l\t(a7)+,d0");
                  asmout.println("\tand.l\td0,4(a7)");
                  stack.push('J');
                  break;
                case op_ior:
                  stack.pop('I', 2);
                  asmout.println("\tmove.l\t(a7)+,d0");
                  asmout.println("\tor.l\td0,(a7)");
                  stack.push('I');
                  break;
                case op_lor:
                  stack.pop('J', 2);
                  asmout.println("\tmove.l\t(a7)+,d0");
                  asmout.println("\tor.l\td0,4(a7)");
                  asmout.println("\tmove.l\t(a7)+,d0");
                  asmout.println("\tor.l\td0,4(a7)");
                  stack.push('J');
                  break;
                case op_ixor:
                  stack.pop('I', 2);
                  asmout.println("\tmove.l\t(a7)+,d0");
                  asmout.println("\teor.l\td0,(a7)");
                  stack.push('I');
                  break;
                case op_lxor:
                  stack.pop('J', 2);
                  asmout.println("\tmove.l\t(a7)+,d0");
                  asmout.println("\teor.l\td0,4(a7)");
                  asmout.println("\tmove.l\t(a7)+,d0");
                  asmout.println("\teor.l\td0,4(a7)");
                  stack.push('J');
                  break;
                case op_iinc:
                  if (wide) {
                    t = codestream.readUnsignedShort();
                    p += 2;
                    u = codestream.readShort();
                    p += 2;
                  } else {
                    t = codestream.readUnsignedByte();
                    p++;
                    u = codestream.readByte();
                    p++;
                  }
                  asmout.println("\tadd.l\t#" + u + "," + m.local(t) + "(a6)");
                  break;
                case op_i2l:
                  stack.pop('I');
                  asmout.println("\tclr.l\t-(a7)");
                  stack.push('J');
                  break;
                case op_i2f:
                  asmout.println("\tjsr\top_i2f(pc)");
                  break;
                case op_i2d:
                  ASSERT.fail("Unimplemented: 'double' type");
                  break;
                case op_l2i:
                  stack.pop('J');
                  asmout.println("\taddq.l\t#4,a7");
                  stack.push('I');
                  break;
                case op_l2f:
                  ASSERT.fail("Unimplemented: 'float' type");
                  break;
                case op_l2d:
                  ASSERT.fail("Unimplemented: 'double' type");
                  break;
                case op_f2i:
                  asmout.println("\tjsr\top_f2i(pc)");
                  break;
                case op_f2l:
                  ASSERT.fail("Unimplemented: 'float' type");
                  break;
                case op_f2d:
                  ASSERT.fail("Unimplemented: 'double' type");
                  break;
                case op_d2i:
                  ASSERT.fail("Unimplemented: 'double' type");
                  break;
                case op_d2l:
                  ASSERT.fail("Unimplemented: 'double' type");
                  break;
                case op_d2f:
                  ASSERT.fail("Unimplemented: 'double' type");
                  break;
                case op_int2byte:
                  asmout.println("\tmove.l\t(a7),d0");
                  asmout.println("\text.w\td0");
                  asmout.println("\text.l\td0");
                  asmout.println("\tmove.l\td0,(a7)");
                  break;
                case op_int2char:
                  asmout.println("\tclr.w\t(a7)");
                  break;
                case op_int2short:
                  asmout.println("\tmove.l\t(a7),d0");
                  asmout.println("\text.l\td0");
                  asmout.println("\tmove.l\td0,(a7)");
                  break;
                case op_lcmp:
                  stack.pop('J', 2);
                  asmout.println("\tjsr\top_lcmp(pc)");
                  stack.push('I');
                  break;
                case op_fcmpl:
                  asmout.println("\tjsr\top_fcmpl(pc)");
                  break;
                case op_fcmpg:
                  asmout.println("\tjsr\top_fcmpg(pc)");
                  break;
                case op_dcmpl:
                  ASSERT.fail("Unimplemented: 'double' type");
                  break;
                case op_dcmpg:
                  ASSERT.fail("Unimplemented: 'double' type");
                  break;
                case op_ifeq:
                  t = codestream.readShort();
                  p += 2;
                  stack.pop('I');
                  asmout.println("\tmove.l\t(a7)+,d0");
                  asmout.println("\tbeq\t" + Mangle(m.name, m.signature) + "__" + (ofs+t));
                  m.CheckStack(ofs+t, stack);
                  break;
                case op_ifne:
                  t = codestream.readShort();
                  p += 2;
                  stack.pop('I');
                  asmout.println("\tmove.l\t(a7)+,d0");
                  asmout.println("\tbne\t" + Mangle(m.name, m.signature) + "__" + (ofs+t));
                  m.CheckStack(ofs+t, stack);
                  break;
                case op_iflt:
                  t = codestream.readShort();
                  p += 2;
                  stack.pop('I');
                  asmout.println("\tmove.l\t(a7)+,d0");
                  asmout.println("\tblt\t" + Mangle(m.name, m.signature) + "__" + (ofs+t));
                  m.CheckStack(ofs+t, stack);
                  break;
                case op_ifge:
                  t = codestream.readShort();
                  p += 2;
                  stack.pop('I');
                  asmout.println("\tmove.l\t(a7)+,d0");
                  asmout.println("\tbge\t" + Mangle(m.name, m.signature) + "__" + (ofs+t));
                  m.CheckStack(ofs+t, stack);
                  break;
                case op_ifgt:
                  t = codestream.readShort();
                  p += 2;
                  stack.pop('I');
                  asmout.println("\tmove.l\t(a7)+,d0");
                  asmout.println("\tbgt\t" + Mangle(m.name, m.signature) + "__" + (ofs+t));
                  m.CheckStack(ofs+t, stack);
                  break;
                case op_ifle:
                  t = codestream.readShort();
                  p += 2;
                  stack.pop('I');
                  asmout.println("\tmove.l\t(a7)+,d0");
                  asmout.println("\tble\t" + Mangle(m.name, m.signature) + "__" + (ofs+t));
                  m.CheckStack(ofs+t, stack);
                  break;
                case op_if_icmpeq:
                  t = codestream.readShort();
                  p += 2;
                  stack.pop('I', 2);
                  asmout.println("\tmove.l\t(a7)+,d0");
                  asmout.println("\tcmp.l\t(a7)+,d0");
                  asmout.println("\tbeq\t" + Mangle(m.name, m.signature) + "__" + (ofs+t));
                  m.CheckStack(ofs+t, stack);
                  break;
                case op_if_icmpne:
                  t = codestream.readShort();
                  p += 2;
                  stack.pop('I', 2);
                  asmout.println("\tmove.l\t(a7)+,d0");
                  asmout.println("\tcmp.l\t(a7)+,d0");
                  asmout.println("\tbne\t" + Mangle(m.name, m.signature) + "__" + (ofs+t));
                  m.CheckStack(ofs+t, stack);
                  break;
                case op_if_icmplt:
                  t = codestream.readShort();
                  p += 2;
                  stack.pop('I', 2);
                  asmout.println("\tmove.l\t(a7)+,d0");
                  asmout.println("\tcmp.l\t(a7)+,d0");
                  asmout.println("\tbgt\t" + Mangle(m.name, m.signature) + "__" + (ofs+t));
                  m.CheckStack(ofs+t, stack);
                  break;
                case op_if_icmpge:
                  t = codestream.readShort();
                  p += 2;
                  stack.pop('I', 2);
                  asmout.println("\tmove.l\t(a7)+,d0");
                  asmout.println("\tcmp.l\t(a7)+,d0");
                  asmout.println("\tble\t" + Mangle(m.name, m.signature) + "__" + (ofs+t));
                  m.CheckStack(ofs+t, stack);
                  break;
                case op_if_icmpgt:
                  t = codestream.readShort();
                  p += 2;
                  stack.pop('I', 2);
                  asmout.println("\tmove.l\t(a7)+,d0");
                  asmout.println("\tcmp.l\t(a7)+,d0");
                  asmout.println("\tblt\t" + Mangle(m.name, m.signature) + "__" + (ofs+t));
                  m.CheckStack(ofs+t, stack);
                  break;
                case op_if_icmple:
                  t = codestream.readShort();
                  p += 2;
                  stack.pop('I', 2);
                  asmout.println("\tmove.l\t(a7)+,d0");
                  asmout.println("\tcmp.l\t(a7)+,d0");
                  asmout.println("\tbge\t" + Mangle(m.name, m.signature) + "__" + (ofs+t));
                  m.CheckStack(ofs+t, stack);
                  break;
                case op_if_acmpeq:
                  t = codestream.readShort();
                  p += 2;
                  stack.pop('L', 2);
                  asmout.println("\tmove.l\t(a7)+,a0");
                  asmout.println("\tcmpa.l\t(a7)+,a0");
                  asmout.println("\tbeq\t" + Mangle(m.name, m.signature) + "__" + (ofs+t));
                  m.CheckStack(ofs+t, stack);
                  break;
                case op_if_acmpne:
                  t = codestream.readShort();
                  p += 2;
                  stack.pop('L', 2);
                  asmout.println("\tmove.l\t(a7)+,a0");
                  asmout.println("\tcmpa.l\t(a7)+,a0");
                  asmout.println("\tbne\t" + Mangle(m.name, m.signature) + "__" + (ofs+t));
                  m.CheckStack(ofs+t, stack);
                  break;
                case op_goto:
                  t = codestream.readShort();
                  p += 2;
                  asmout.println("\tbra\t" + Mangle(m.name, m.signature) + "__" + (ofs+t));
                  m.CheckStack(ofs+t, stack);
                  stack.clear();
                  break;
                case op_jsr:
                  t = codestream.readShort();
                  p += 2;
                  asmout.println("\tbsr\t" + Mangle(m.name, m.signature) + "__" + (ofs+t));
                  m.CheckStack(ofs+t, stack);
                  break;
                case op_ret:
                  if (wide) {
                    t = codestream.readUnsignedShort();
                    p += 2;
                  } else {
                    t = codestream.readUnsignedByte();
                    p++;
                  }
                  asmout.println("\tmove.l\t" + m.local(t) + "(a6),a0");
                  asmout.println("\tjmp\t(a0)");
                  break;
                case op_tableswitch: {
                  codestream.skipBytes((4 - p % 4) % 4);
                  p += (4 - p % 4) % 4;
                  stack.pop('I');
                  asmout.println("\tmove.l\t(a7)+,d0");
                  int def = codestream.readInt();
                  p += 4;
                  int low = codestream.readInt();
                  p += 4;
                  int high = codestream.readInt();
                  p += 4;
                  for (int x = low; x <= high; x++) {
                    asmout.println("\tcmp.l\t#" + x + ",d0");
                    t = codestream.readInt();
                    p += 4;
                    asmout.println("\tbeq\t" + Mangle(m.name, m.signature) + "__" + (ofs+t));
                    m.CheckStack(ofs+t, stack);
                  }
                  asmout.println("\tbra\t" + Mangle(m.name, m.signature) + "__" + (ofs+def));
                  m.CheckStack(ofs+def, stack);
                  break;
                }
                case op_lookupswitch: {
                  codestream.skipBytes((4 - p % 4) % 4);
                  p += (4 - p % 4) % 4;
                  stack.pop('I');
                  asmout.println("\tmove.l\t(a7)+,d0");
                  int def = codestream.readInt();
                  p += 4;
                  int n = codestream.readInt();
                  p += 4;
                  while (n-- > 0) {
                    t = codestream.readInt();
                    p += 4;
                    asmout.println("\tcmp.l\t#" + t + ",d0");
                    t = codestream.readInt();
                    p += 4;
                    asmout.println("\tbeq\t" + Mangle(m.name, m.signature) + "__" + (ofs+t));
                    m.CheckStack(ofs+t, stack);
                  }
                  asmout.println("\tbra\t" + Mangle(m.name, m.signature) + "__" + (ofs+def));
                  m.CheckStack(ofs+def, stack);
                  break;
                }
                case op_ireturn:
                  stack.pop('I');
                  if (p < code.code.length) {
                    asmout.println("\tbra\t" + Mangle(m.name, m.signature) + "__out");
                    stack.clear();
                  }
                  break;
                case op_lreturn:
                  stack.pop('J');
                  if (p < code.code.length) {
                    asmout.println("\tbra\t" + Mangle(m.name, m.signature) + "__out");
                    stack.clear();
                  }
                  break;
                case op_freturn:
                  stack.pop('F');
                  if (p < code.code.length) {
                    asmout.println("\tbra\t" + Mangle(m.name, m.signature) + "__out");
                    stack.clear();
                  }
                  break;
                case op_dreturn:
                  ASSERT.fail("Unimplemented: 'double' type");
                  break;
                case op_areturn:
                  stack.pop('L');
                  if (p < code.code.length) {
                    asmout.println("\tbra\t" + Mangle(m.name, m.signature) + "__out");
                    stack.clear();
                  }
                  break;
                case op_return:
                  if (p < code.code.length) {
                    asmout.println("\tbra\t" + Mangle(m.name, m.signature) + "__out");
                    stack.clear();
                  }
                  break;
                case op_getstatic: {
                  t = codestream.readUnsignedShort();
                  p += 2;
                  CONSTANT_Fieldref_info info = (CONSTANT_Fieldref_info)constant_pool.table[t];
                  Klass cls = Klass.Load(info.class_name);
                  field_info f = cls.FindField(info.name);
                  ConstantValue_attribute cv = (ConstantValue_attribute)f.attributes.get("ConstantValue");
                  if ((f.access_flags & ACC_FINAL) != 0 && cv != null) {
                    cls.push_constant(asmout, cv.constantvalue_index, stack);
                  } else {
                    switch (info.signature.charAt(0)) {
                      case 'B':
                        asmout.println("\tmove.b\t" + cls.Mangle(info.name) + "(a5),d0\t; " + info.name);
                        asmout.println("\text.w\td0");
                        asmout.println("\text.l\td0");
                        asmout.println("\tmove.l\td0,-(a7)");
                        break;
                      case 'C':
                      case 'Z':
                        asmout.println("\tmove.w\t" + cls.Mangle(info.name) + "(a5),-(a7)\t; " + info.name);
                        asmout.println("\tclr.w\t-(a7)");
                        break;
                      case 'S':
                        asmout.println("\tmove.w\t" + cls.Mangle(info.name) + "(a5),d0\t; " + info.name);
                        asmout.println("\text.l\td0");
                        asmout.println("\tmove.l\td0,-(a7)");
                        break;
                      case 'F':
                      case 'I':
                      case 'L':
                      case '[':
                        asmout.println("\tmove.l\t" + cls.Mangle(info.name) + "(a5),-(a7)\t; " + info.name);
                        break;
                      case 'D':
                      case 'J':
                        ASSERT.fail("Unimplemented: 'long' static field get");
                        break;
                      default:
                        ASSERT.fail("Internal error: Unknown field signature ("+ClassName+"."+info.name+"): "+info.signature);
                        break;
                    }
                    stack.push(info.signature.charAt(0));
                  }
                  break;
                }
                case op_putstatic: {
                  t = codestream.readUnsignedShort();
                  p += 2;
                  CONSTANT_Fieldref_info info = (CONSTANT_Fieldref_info)constant_pool.table[t];
                  Klass cls = Klass.Load(info.class_name);
                  stack.pop(info.signature.charAt(0));
                  field_info f = cls.FindField(info.name);
                  ASSERT.check((f.access_flags & ACC_FINAL) == 0, "Trying to modify a static final field: "+cls.ClassName+"."+info.name);
                  switch (info.signature.charAt(0)) {
                    case 'B':
                      asmout.println("\tmove.l\t(a7)+,d0");
                      asmout.println("\tmove.b\td0," + cls.Mangle(info.name) + "(a5)\t; " + info.name);
                      break;
                    case 'C':
                    case 'S':
                    case 'Z':
                      asmout.println("\tmove.l\t(a7)+,d0");
                      asmout.println("\tmove.w\td0," + cls.Mangle(info.name) + "(a5)\t; " + info.name);
                      break;
                    case 'F':
                    case 'I':
                    case 'L':
                    case '[':
                      asmout.println("\tmove.l\t(a7)+," + cls.Mangle(info.name) + "(a5)\t; " + info.name);
                      break;
                    case 'D':
                    case 'J':
                      ASSERT.fail("Unimplemented: 'long' static field put");
                      break;
                    default:
                      ASSERT.fail("Internal error: Unknown field signature ("+ClassName+"."+info.name+"): "+info.signature);
                      break;
                  }
                  break;
                }
                case op_getfield: {
                  t = codestream.readUnsignedShort();
                  p += 2;
                  CONSTANT_Fieldref_info info = (CONSTANT_Fieldref_info)constant_pool.table[t];
                  Klass cls = Klass.Load(info.class_name);
                  field_info f = cls.FindField(info.name);
                  stack.pop('L');
                  asmout.println("\tmove.l\t(a7)+,a0");
                  switch (info.signature.charAt(0)) {
                    case 'B':
                      ASSERT.check(f.Offset+1 <= cls.DataSize, "Internal error: Structure access out of bounds");
                      asmout.println("\tmove.b\t" + f.Offset + "(a0),d0\t; " + info.class_name + "." + info.name);
                      asmout.println("\text.w\td0");
                      asmout.println("\text.l\td0");
                      asmout.println("\tmove.l\td0,-(a7)");
                      break;
                    case 'C':
                    case 'Z':
                      ASSERT.check(f.Offset+2 <= cls.DataSize, "Internal error: Structure access out of bounds");
                      asmout.println("\tmove.w\t" + f.Offset + "(a0),-(a7)\t; " + info.class_name + "." + info.name);
                      asmout.println("\tclr.w\t-(a7)");
                      break;
                    case 'S':
                      ASSERT.check(f.Offset+2 <= cls.DataSize, "Internal error: Structure access out of bounds");
                      asmout.println("\tmove.w\t" + f.Offset + "(a0),d0\t; " + info.class_name + "." + info.name);
                      asmout.println("\text.l\td0");
                      asmout.println("\tmove.l\td0,-(a7)");
                      break;
                    case 'F':
                    case 'I':
                    case 'L':
                    case '[':
                      ASSERT.check(f.Offset+4 <= cls.DataSize, "Internal error: Structure access out of bounds");
                      asmout.println("\tmove.l\t" + f.Offset + "(a0),-(a7)\t; " + info.class_name + "." + info.name);
                      break;
                    case 'D':
                    case 'J':
                      ASSERT.check(f.Offset+8 <= cls.DataSize, "Internal error: Structure access out of bounds");
                      ASSERT.fail("Unimplemented: 'long' field get");
                      break;
                    default:
                      ASSERT.fail("Internal error: Unknown field signature ("+ClassName+"."+info.name+"): "+info.signature);
                      break;
                  }
                  stack.push(info.signature.charAt(0));
                  break;
                }
                case op_putfield: {
                  t = codestream.readUnsignedShort();
                  p += 2;
                  CONSTANT_Fieldref_info info = (CONSTANT_Fieldref_info)constant_pool.table[t];
                  Klass cls = Klass.Load(info.class_name);
                  field_info f = cls.FindField(info.name);
                  stack.pop(info.signature.charAt(0));
                  stack.pop('L');
                  asmout.println("\tmove.l\t(a7)+,d0");
                  asmout.println("\tmove.l\t(a7)+,a0");
                  switch (info.signature.charAt(0)) {
                    case 'B':
                      ASSERT.check(f.Offset+1 <= cls.DataSize, "Internal error: Structure access out of bounds");
                      asmout.println("\tmove.b\td0," + f.Offset + "(a0)\t; " + info.class_name + "." + info.name);
                      break;
                    case 'C':
                    case 'S':
                    case 'Z':
                      ASSERT.check(f.Offset+2 <= cls.DataSize, "Internal error: Structure access out of bounds");
                      asmout.println("\tmove.w\td0," + f.Offset + "(a0)\t; " + info.class_name + "." + info.name);
                      break;
                    case 'F':
                    case 'I':
                    case 'L':
                    case '[':
                      ASSERT.check(f.Offset+4 <= cls.DataSize, "Internal error: Structure access out of bounds");
                      asmout.println("\tmove.l\td0," + f.Offset + "(a0)\t; " + info.class_name + "." + info.name);
                      break;
                    case 'D':
                    case 'J':
                      ASSERT.check(f.Offset+8 <= cls.DataSize, "Internal error: Structure access out of bounds");
                      ASSERT.fail("Unimplemented: 'long' field put");
                      break;
                    default:
                      ASSERT.fail("Internal error: Unknown field signature ("+ClassName+"."+info.name+"): "+info.signature);
                      break;
                  }
                  break;
                }
                case op_invokevirtual: {
                  t = codestream.readUnsignedShort();
                  p += 2;
                  CONSTANT_Methodref_info info = (CONSTANT_Methodref_info)constant_pool.table[t];
                  Klass cls = Klass.Load(info.class_name);
                  method_info mm = cls.FindMethod(info.name, info.signature);
                  asmout.println("\tmove.l\t" + 4*(new TArgs(info.signature)).Words() + "(a7),a0");
                  asmout.println("\tmove.l\t#" + mm.VtableIndex + ",d1\t; " + cls.ClassName + "." + info.name + info.signature);
                  asmout.println("\tjsr\top_invokevirtual(pc)");
                  invoke_epilogue(asmout, info.signature, true, stack);
                  break;
                }
                case op_invokenonvirtual:
                case op_invokestatic: {
                  t = codestream.readUnsignedShort();
                  p += 2;
                  CONSTANT_Methodref_info info = (CONSTANT_Methodref_info)constant_pool.table[t];
                  Klass cls = Klass.Load(info.class_name);
                  asmout.println("\tjsr\t" + cls.Mangle(info.name, info.signature) + "(pc)\t; " + cls.ClassName + "." + info.name + info.signature);
                  invoke_epilogue(asmout, info.signature, op != op_invokestatic, stack);
                  break;
                }
                case op_invokeinterface: {
                  t = codestream.readUnsignedShort();
                  p += 2;
                  u = codestream.readUnsignedByte();
                  p++;
                  p++; // reserved byte
                  CONSTANT_InterfaceMethodref_info info = (CONSTANT_InterfaceMethodref_info)constant_pool.table[t];
                  Klass cls = Klass.Load(info.class_name);
                  ASSERT.fail("Unimplemented: Interface method calls");
                  //!
                  invoke_epilogue(asmout, info.signature, true, stack);
                  break;
                }
                case op_new: {
                  t = codestream.readUnsignedShort();
                  p += 2;
                  CONSTANT_Class_info info = (CONSTANT_Class_info)constant_pool.table[t];
                  Klass cls = Klass.Load(info.name);
                  asmout.println("\tmove.l\t#" + cls.Index + ",d0\t; " + cls.ClassName);
                  asmout.println("\tjsr\top_new(pc)");
                  stack.push('L');
                  break;
                }
                case op_newarray: {
                  t = codestream.readUnsignedByte();
                  p++;
                  StringBuffer classname = new StringBuffer(2);
                  classname.setLength(2);
                  classname.setCharAt(0, '[');
                  int elsize = 0;
                  switch (t) {
                    case  4: classname.setCharAt(1, 'Z'); elsize = 1; break;
                    case  5: classname.setCharAt(1, 'C'); elsize = Jump.CHARSIZE; break;
                    case  6: classname.setCharAt(1, 'F'); elsize = 4; break;
                    case  7: classname.setCharAt(1, 'D'); elsize = 8; break;
                    case  8: classname.setCharAt(1, 'B'); elsize = 1; break;
                    case  9: classname.setCharAt(1, 'S'); elsize = 2; break;
                    case 10: classname.setCharAt(1, 'I'); elsize = 4; break;
                    case 11: classname.setCharAt(1, 'J'); elsize = 8; break;
                    default:
                      ASSERT.fail("Internal error: Unknown array type: "+t);
                      break;
                  }
                  Klass cls = Klass.Load(classname.toString());
                  stack.pop('I');
                  asmout.println("\tmove.l\t#" + cls.Index + ",d0");
                  asmout.println("\tmove.l\t#" + elsize + ",d1");
                  asmout.println("\tjsr\top_newarray(pc)");
                  stack.push('L');
                  break;
                }
                case op_anewarray: {
                  t = codestream.readUnsignedShort();
                  p += 2;
                  CONSTANT_Class_info info = (CONSTANT_Class_info)constant_pool.table[t];
                  Klass cls = Klass.Load(info.name);
                  Klass acls;
                  if (info.name.charAt(0) == '[') {
                    acls = Klass.Load("["+info.name);
                  } else {
                    acls = Klass.Load("[L"+info.name+";");
                  }
                  stack.pop('I');
                  asmout.println("\tmove.l\t#" + acls.Index + ",d0");
                  asmout.println("\tmove.l\t#" + cls.Index + ",d1");
                  asmout.println("\tjsr\top_anewarray(pc)");
                  stack.push('L');
                  break;
                }
                case op_arraylength:
                  stack.pop('L');
                  asmout.println("\tmove.l\t(a7)+,a0");
                  asmout.println("\tmove.w\tArray.length(a0),-(a7)");
                  asmout.println("\tclr.w\t-(a7)");
                  stack.push('I');
                  break;
                case op_athrow:
                  asmout.println("\tjsr\top_athrow(pc)");
                  stack.clear();
                  break;
                case op_checkcast: {
                  t = codestream.readUnsignedShort();
                  p += 2;
                  CONSTANT_Class_info info = (CONSTANT_Class_info)constant_pool.table[t];
                  Klass cls = Klass.Load(info.name);
                  stack.pop('L');
                  asmout.println("\tmove.l\t#" + cls.Index + ",d0");
                  asmout.println("\tjsr\top_checkcast(pc)");
                  stack.push('L');
                  break;
                }
                case op_instanceof: {
                  t = codestream.readUnsignedShort();
                  p += 2;
                  CONSTANT_Class_info info = (CONSTANT_Class_info)constant_pool.table[t];
                  Klass cls = Klass.Load(info.name);
                  stack.pop('L');
                  asmout.println("\tmove.l\t#" + cls.Index + ",d0");
                  asmout.println("\tjsr\top_instanceof(pc)");
                  stack.push('I');
                  break;
                }
                case op_monitorenter:
                  asmout.println("\tjsr\top_monitorenter(pc)");
                  stack.pop('L');
                  break;
                case op_monitorexit:
                  asmout.println("\tjsr\top_monitorexit(pc)");
                  stack.pop('L');
                  break;
                case op_wide:
                  wide = true;
                  break;
                case op_multianewarray: {
                  t = codestream.readUnsignedShort();
                  p += 2;
                  u = codestream.readUnsignedByte();
                  p++;
                  CONSTANT_Class_info info = (CONSTANT_Class_info)constant_pool.table[t];
                  ASSERT.fail("Unimplemented: Multidimensional array initialization");
                  //!const Klass cls = Klass::Load(info->name);
                  //!asmout.println("\tmove.l\t#%d,d0", cls->Index);
                  //!asmout.println("\tmove.l\t#%d,d1", u);
                  //!asmout.println("\tjsr\top_multianewarray(pc)");
                  break;
                }
                case op_ifnull:
                  t = codestream.readShort();
                  p += 2;
                  stack.pop('L');
                  asmout.println("\tmove.l\t(a7)+,d0");
                  asmout.println("\tbeq\t" + Mangle(m.name, m.signature) + "__" + (ofs+t));
                  m.CheckStack(ofs+t, stack);
                  break;
                case op_ifnonnull:
                  t = codestream.readShort();
                  p += 2;
                  stack.pop('L');
                  asmout.println("\tmove.l\t(a7)+,d0");
                  asmout.println("\tbne\t" + Mangle(m.name, m.signature) + "__" + (ofs+t));
                  m.CheckStack(ofs+t, stack);
                  break;
                case op_goto_w:
                  ASSERT.fail("Unimplemented: Opcode 'goto_w'");
                  break;
                case op_jsr_w:
                  ASSERT.fail("Unimplemented: Opcode 'jsr_w'");
                  break;
                case op_breakpoint:
                  asmout.println("\tdc.w\tbreakpoint");
                  break;
                case op_ret_w:
                  t = codestream.readUnsignedShort();
                  p += 2;
                  asmout.println("\tmove.l\t" + m.local(t) + "(a6),a0");
                  asmout.println("\tjmp\t(a0)");
                  break;
                default:
                  ASSERT.fail("Internal error: Unknown bytecode: "+op);
                  break;
              }
              if (op != op_wide) {
                wide = false;
              }
            }
            asmout.println(Mangle(m.name, m.signature) + "__out:");
            switch (new TArgs(m.signature).Ret().charAt(0)) {
              case 'B':
              case 'C':
              case 'F':
              case 'I':
              case 'S':
              case 'Z':
                asmout.println("\tmove.l\t(a7)+,d0");
                break;
              case 'D':
              case 'J':
                asmout.println("\tmove.l\t(a7)+,d0");
                asmout.println("\tmove.l\t(a7)+,d1");
                break;
              case 'L':
              case '[':
                asmout.println("\tmove.l\t(a7)+,a0");
                break;
              case 'V':
                break;
              default:
                ASSERT.fail("Internal error: Unknown return type ("+ClassName+"."+m.name+"): "+m.signature);
                break;
            }
            asmout.println("\tunlk\ta6");
            asmout.println("\taddq.l\t#4,a7\n");
            asmout.println("\trts\n");
            if (Jump.DebugSymbols) {
              asmout.println("\tdc.b\t" + (0x80 | (ClassName.length() + 1 + m.name.length())) + ", '" + ClassName + "." + m.name + "', 0");
              asmout.println("\talign\t2\n");
            }
            if (code.exception_table.length > 0) {
              asmout.println(Mangle(m.name, m.signature)+"__exceptions:");
              // this is struct ExceptionTableHeader
              asmout.println("\tdc.l\t"+Mangle(m.name, m.signature));
              asmout.println("\tdc.w\t"+4*localonly);
              asmout.println("\tdc.w\t"+code.exception_table.length+"\n");
              for (int j = 0; j < code.exception_table.length; j++) {
                // this is struct ExceptionTableEntry
                asmout.println("\tdc.w\t"+Mangle(m.name, m.signature)+"__"+code.exception_table[j].start_pc+"-"+Mangle(m.name, m.signature));
                asmout.println("\tdc.w\t"+Mangle(m.name, m.signature)+"__"+code.exception_table[j].end_pc+"-"+Mangle(m.name, m.signature));
                asmout.println("\tdc.w\t"+Mangle(m.name, m.signature)+"__"+code.exception_table[j].handler_pc+"-"+Mangle(m.name, m.signature));
                asmout.println("\tdc.w\t"+code.exception_table[j].catch_type.Index+"\t; "+code.exception_table[j].catch_type.ClassName);
                asmout.println();
              }
            }
          } catch (IOException e) {
            System.out.println("Fatal: Internal error reading from code stream ("+ClassName+"."+m.name+m.signature+")");
            System.exit(1);
          }
        } else {
          if (Jump.Verbose) {
            System.out.println("native " + ClassName + "." + m.name + m.signature);
          }
          NativeLocation nl = (NativeLocation)Jump.Native.get(Mangle(m.name, null, false));
          if (nl != null) {
            try {
              RandomAccessFile nf = new RandomAccessFile(nl.fn, "r");
              nf.seek(nl.offset);
              while (true) {
                String s = nf.readLine();
                if (s == null) {
                  break;
                }
                asmout.println(s);
                if (s.length() == 0 || s.charAt(0) == '\r') { // grr
                  break;
                }
              }
            } catch (IOException e) {
              System.out.println("Fatal: IOException reading from "+nl.fn);
              System.exit(1);
            }
          } else {
            Jump.NativeMethodsMissing = true;
            asmout.println("        systrap ErrDisplayFileLineMsg(&native_method_missing_msg(pc).l, #0.w, &"+Mangle(m.name, m.signature)+"_name(pc).l)");
            asmout.println(Mangle(m.name, m.signature)+"_name\tdc.b\t'"+ClassName+"."+m.name+"',0");
            asmout.println();
            System.out.println("Warning: Native method not found (using default stub): "+ClassName+"."+m.name);
          }
          if (Jump.DebugSymbols) {
            asmout.println("\tdc.b\t" + (0x80 | (ClassName.length() + 1 + m.name.length())) + ", '" + ClassName + "." + m.name + "', 0");
            asmout.println("\talign\t2\n");
          }
        }
      }
    }
  }

  static String FindFileOnClasspath(String fn)
  {
    String fsep = System.getProperty("file.separator");
    strtok st = new strtok(System.getProperty("java.class.path"), System.getProperty("path.separator"));
    while (true) {
      String s = st.next();
      if (s == null) {
        break;
      }
      if (s.length() > 0) {
        if (s.equals(".")) {
          s = "";
        } else if (!s.endsWith(fsep)) {
          s += fsep;
        }
        if (new File(s+fn).exists()) {
          return new File(s+fn).getAbsolutePath();
        }
      }
    }
    return null;
  }

  /*static String Find(String classname)
  {
    return FindFileOnClasspath(classname + ".class");
  }*/

  static Klass Load(String classname, boolean mainclass)
  {
    Klass cls = (Klass)Classes.get(classname);
    if (cls != null) {
      return cls;
    }
    if (Jump.Verbose) {
      System.out.println("Class " + classname);
    }
    if (classname.charAt(0) == '[') {
      cls = new KlassArray(classname);
    } else {
      InputStream cstream = ClassLoader.getSystemResourceAsStream(classname+".class");
      if (cstream == null) {
        ASSERT.fail("Class "+classname+" not found");
      }
      //String fn = Find(classname);
      //if (fn == null) {
      //  ASSERT.fail("Class "+classname+" not found");
      //}
      try {
        cls = new KlassFile(classname, new BufferedInputStream(cstream));
      } catch (IOException e) {
        System.out.println("Could not read class "+classname/*+" from file "+fn*/);
        System.exit(1);
      }
    }
    Classes.put(classname, cls);
    ClassList.addElement(cls);
    if (mainclass) {
      method_info m = cls.FindMethod("PilotMain", "(III)I");
      if (m == null || (m.access_flags & ACC_STATIC) == 0) {
        System.out.println("Fatal: method 'public static int PilotMain(int cmd, int cmdPBP, int launchFlags)' not found.");
        System.exit(1);
      }
      cls.Preprocess(m);
      Klass.Load("[B");
      Klass c;
      c = Klass.Load("java/lang/Runtime");
      c.Preprocess(c.FindMethod("gc", "()V"));
      c = Klass.Load("java/lang/String");
      c.Preprocess(c.FindMethod("<init>", "([BI)V"));
      c = Klass.Load("java/lang/Throwable");
      c.Preprocess(c.FindMethod("getMessage", "()Ljava/lang/String;"));
    }
    return cls;
  }

  static Klass Load(String classname)
  {
    return Load(classname, false);
  }

  private void Preprocess(method_info m)
  {
    if (m.Generate) {
      return;
    }
    if ((access_flags & ACC_INTERFACE) != 0) {
      return;
    }
    m.Generate = true;
    if ((m.access_flags & ACC_NATIVE) == 0) {
      Code_attribute code = (Code_attribute)m.attributes.get("Code");
      ASSERT.check(code, "No 'Code' attribute for method "+ClassName+"."+m.name+m.signature);
      int p = 0;
      DataInputStream codestream = new DataInputStream(new ByteArrayInputStream(code.code));
      try {
        boolean wide = false;
        while (p < code.code.length) {
          int t = 0;
          int pp = p;
          int op = codestream.readUnsignedByte();
          p++;
          ASSERT.check(op < BytecodeLength.length, "Invalid opcode found in "+ClassName+"."+m.name+m.signature);
          int oplen = BytecodeLength[op];
          if (op != op_tableswitch && op != op_lookupswitch) {
            boolean x = oplen > 0; // work around apparent bug in MS Java JIT
            ASSERT.check(x, "Invalid opcode found in "+ClassName+"."+m.name+m.signature);
          }
          BytecodeUsed[op] = true;
          switch (op) {
            case op_iload:
            case op_lload:
            case op_fload:
            case op_dload:
            case op_aload:
            case op_istore:
            case op_lstore:
            case op_fstore:
            case op_dstore:
            case op_astore:
              if (wide) {
                oplen += 1;
              }
              break;
            case op_iinc:
              if (wide) {
                oplen += 2;
              }
              break;
            case op_ldc1:
              t = codestream.readUnsignedByte();
              p++;
              if (constant_pool.table[t].tag == CONSTANT_String) {
                Klass cls = Klass.Load("java/lang/String");
                method_info mm = cls.FindMethod("<init>", "([BI)V");
                cls.Preprocess(mm);
              }
              break;
            case op_ifeq:
            case op_ifne:
            case op_iflt:
            case op_ifge:
            case op_ifgt:
            case op_ifle:
            case op_if_icmpeq:
            case op_if_icmpne:
            case op_if_icmplt:
            case op_if_icmpge:
            case op_if_icmpgt:
            case op_if_icmple:
            case op_if_acmpeq:
            case op_if_acmpne:
            case op_goto:
            case op_jsr:
            case op_ifnull:
            case op_ifnonnull:
              t = codestream.readShort();
              p += 2;
              m.Labels.set(pp+t);
              break;
            case op_getstatic:
            case op_putstatic:
            case op_getfield:
            case op_putfield: {
              t = codestream.readUnsignedShort();
              p += 2;
              CONSTANT_Fieldref_info info = (CONSTANT_Fieldref_info)constant_pool.table[t];
              Klass cls = Klass.Load(info.class_name);
              field_info f = cls.FindField(info.name);
              f.Generate = true;
              break;
            }
            case op_tableswitch: {
              codestream.skipBytes((4 - p % 4) % 4);
              p += (4 - p % 4) % 4;
              t = codestream.readInt();
              p += 4;
              m.Labels.set(pp+t);
              int low = codestream.readInt();
              p += 4;
              int high = codestream.readInt();
              p += 4;
              while (low <= high) {
                t = codestream.readInt();
                p += 4;
                m.Labels.set(pp+t);
                low++;
              }
              break;
            }
            case op_lookupswitch: {
              codestream.skipBytes((4 - p % 4) % 4);
              p += (4 - p % 4) % 4;
              t = codestream.readInt();
              p += 4;
              m.Labels.set(pp+t);
              int n = codestream.readInt();
              p += 4;
              while (n-- > 0) {
                codestream.skipBytes(4);
                p += 4;
                t = codestream.readInt();
                p += 4;
                m.Labels.set(pp+t);
              }
              break;
            }
            case op_invokevirtual: {
              t = codestream.readUnsignedShort();
              p += 2;
              CONSTANT_Methodref_info info = (CONSTANT_Methodref_info)constant_pool.table[t];
              Klass cls = Klass.Load(info.class_name);
              method_info mm = cls.FindMethod(info.name, info.signature);
              cls.Preprocess(mm); // depth first search of call tree
              // remember class/name/signature to mark all for generate
              break;
            }
            case op_invokenonvirtual:
            case op_invokestatic: {
              t = codestream.readUnsignedShort();
              p += 2;
              CONSTANT_Methodref_info info = (CONSTANT_Methodref_info)constant_pool.table[t];
              Klass cls = Klass.Load(info.class_name);
              method_info mm = cls.FindMethod(info.name, info.signature);
              ASSERT.check(mm, "Method not found: "+cls.ClassName+"."+info.name+info.signature);
              cls.Preprocess(mm); // depth first search of call tree
              break;
            }
            case op_invokeinterface: {
              t = codestream.readUnsignedShort();
              p += 2;
              CONSTANT_InterfaceMethodref_info info = (CONSTANT_InterfaceMethodref_info)constant_pool.table[t];
              Klass cls = Klass.Load(info.class_name);
              method_info mm = cls.FindMethod(info.name, info.signature);
              cls.Preprocess(mm); // depth first search of call tree
              // remember class/name/signature to mark all for generate
              break;
            }
            case op_new: {
              t = codestream.readUnsignedShort();
              p += 2;
              CONSTANT_Class_info info = (CONSTANT_Class_info)constant_pool.table[t];
              Klass cls = Klass.Load(info.name);
              break;
            }
            case op_newarray: {
              t = codestream.readUnsignedByte();
              p++;
              StringBuffer classname = new StringBuffer(2);
              classname.setLength(2);
              classname.setCharAt(0, '[');
              switch (t) {
                case  4: classname.setCharAt(1, 'Z'); break;
                case  5: classname.setCharAt(1, 'C'); break;
                case  6: classname.setCharAt(1, 'F'); break;
                case  7: classname.setCharAt(1, 'D'); break;
                case  8: classname.setCharAt(1, 'B'); break;
                case  9: classname.setCharAt(1, 'S'); break;
                case 10: classname.setCharAt(1, 'I'); break;
                case 11: classname.setCharAt(1, 'J'); break;
                default:
                  ASSERT.fail("Internal error: Unknown array type: "+t);
                  break;
              }
              Klass.Load(classname.toString());
              BytecodeUsed[op_new] = true;
              break;
            }
            case op_anewarray: {
              t = codestream.readUnsignedShort();
              p += 2;
              CONSTANT_Class_info info = (CONSTANT_Class_info)constant_pool.table[t];
              Klass.Load(info.name);
              if (info.name.charAt(0) == '[') {
                Klass.Load("["+info.name);
              } else {
                Klass.Load("[L"+info.name+";");
              }
              break;
            }
            case op_checkcast: {
              t = codestream.readUnsignedShort();
              p += 2;
              CONSTANT_Class_info info = (CONSTANT_Class_info)constant_pool.table[t];
              Klass.Load(info.name);
              break;
            }
            case op_instanceof: {
              t = codestream.readUnsignedShort();
              p += 2;
              CONSTANT_Class_info info = (CONSTANT_Class_info)constant_pool.table[t];
              Klass.Load(info.name);
              break;
            }
            case op_wide:
              wide = true;
              break;
            case op_multianewarray:
              break;
            case op_goto_w:
              break;
            case op_jsr_w:
              break;
          }
          if (op != op_wide) {
            wide = false;
          }
          if (op != op_tableswitch && op != op_lookupswitch) {
            int q = pp + oplen;
            codestream.skipBytes(q - p);
            p = q;
          }
        }
        for (int i = 0; i < code.exception_table.length; i++) {
          m.Labels.set(code.exception_table[i].start_pc);
          m.Labels.set(code.exception_table[i].end_pc);
          m.Labels.set(code.exception_table[i].handler_pc);
          int hpc = code.exception_table[i].handler_pc;
          ASSERT.check(m.LabelOriginStack[hpc] == null, "Duplicate pointer to exception handler: "+ClassName+"."+m.name);
          m.LabelOriginStack[hpc] = new TMethodStack();
          m.LabelOriginStack[hpc].push('L');
        }
      } catch (IOException e) {
        System.out.println("Fatal: Internal error reading from code stream ("+ClassName+"."+m.name+m.signature+")");
        System.exit(1);
      }
    }
  }

  private void push_constant(PrintWriter asmout, int index, TMethodStack stack)
  {
    switch (constant_pool.table[index].tag) {
      case CONSTANT_String: {
        CONSTANT_String_info info = (CONSTANT_String_info)constant_pool.table[index];
        Strings.addElement(info.string);
        asmout.println("\tlea\tS" + (Strings.size()-1) + "(pc),a0");
        asmout.println("\tjsr\tCharPtr_to_String(pc)");
        asmout.println("\tmove.l\ta0,-(a7)");
        stack.push('L');
        break;
      }
      case CONSTANT_Integer: {
        CONSTANT_Integer_info info = (CONSTANT_Integer_info)constant_pool.table[index];
        if (info.bytes >= -32768 && info.bytes < 32768) {
          asmout.println("\tpea.l\t" + info.bytes);
        } else {
          asmout.println("\tmove.l\t#" + info.bytes + ",-(a7)");
        }
        stack.push('I');
        break;
      }
      case CONSTANT_Float: {
        CONSTANT_Float_info info = (CONSTANT_Float_info)constant_pool.table[index];
        asmout.println("\tmove.l\t#" + Float.floatToIntBits(info.bytes) + ",-(a7)");
        stack.push('F');
        break;
      }
      case CONSTANT_Long: {
        CONSTANT_Long_info info = (CONSTANT_Long_info)constant_pool.table[index];
        int t = (int)info.bytes;
        if (t >= -32768 && t < 32768) {
          asmout.println("\tpea.l\t" + t);
        } else {
          asmout.println("\tmove.l\t#" + t + ",-(a7)");
        }
        t = (int)(info.bytes >>> 32);
        if (t == 0) {
          asmout.println("\tclr.l\t-(a7)\n");
        } else if (t >= -128 && t < 128) {
          asmout.println("\tmoveq.l\t#" + t + ",d0");
          asmout.println("\tmove.l\td0,-(a7)");
        } else if (t >= -32768 && t < 32768) {
          asmout.println("\tpea.l\t" + t);
        } else {
          asmout.println("\tmove.l\t#" + t + ",-(a7)");
        }
        stack.push('J');
        break;
      }
      case CONSTANT_Double:
        ASSERT.fail("Unimplemented: 'double' type");
        break;
      default:
        ASSERT.fail("Internal error: Unknown constant pool type: "+constant_pool.table[index].tag);
        break;
    }
  }

  private void invoke_epilogue(PrintWriter asmout, String signature, boolean nonstatic, TMethodStack stack)
  {
    TArgs args = new TArgs(signature);
    int i = args.ArgCount();
    while (i-- > 0) {
      stack.pop(args.Arg(i).charAt(0));
    }
    if (nonstatic) {
      stack.pop('L');
    }
    int w = args.Words();
    if (nonstatic) {
      w++;
    }
    if (w == 1 || w == 2) {
      asmout.println("\taddq.l\t#" + 4*w + ",a7");
    } else if (w > 2) {
      asmout.println("\tlea\t" + 4*w + "(a7),a7");
    }
    char r = args.Ret().charAt(0);
    switch (r) {
      case 'B':
      case 'C':
      case 'I':
      case 'F':
      case 'S':
      case 'Z':
        asmout.println("\tmove.l\td0,-(a7)");
        break;
      case 'J':
      case 'D':
        asmout.println("\tmove.l\td1,-(a7)");
        asmout.println("\tmove.l\td0,-(a7)");
        break;
      case 'L':
      case '[':
        asmout.println("\tmove.l\ta0,-(a7)");
        break;
      case 'V':
        break;
      default:
        ASSERT.fail("Internal error: Unknown return type: "+r);
        break;
    }
    if (r != 'V') {
      stack.push(r);
    }
  }
}

class KlassFile extends Klass {
  KlassFile(String classname, InputStream instream) throws IOException
  {
    super(classname);
    DataInputStream in = new DataInputStream(instream);
    magic = in.readInt();
    minor_version = in.readShort();
    major_version = in.readShort();
    constant_pool = new ConstantPool(in);
    access_flags = in.readShort();
    this_class = in.readShort();
    super_class = in.readShort();
    interfaces_count = in.readShort();
    interfaces = new short[interfaces_count];
    for (int i = 0; i < interfaces_count; i++) {
      interfaces[i] = in.readShort();
    }
    fields_count = in.readShort();
    fields = new field_info[fields_count];
    for (int i = 0; i < fields_count; i++) {
      fields[i] = new field_info(constant_pool, in);
    }
    methods_count = in.readShort();
    methods = new method_info[methods_count];
    for (int i = 0; i < methods_count; i++) {
      methods[i] = new method_info(constant_pool, in);

      //!!if (classname.equals("java/util/BitSet")) {
      //  methods[i].Generate = true;
      //}

    }
    attributes = new attribute_table(constant_pool, in);
    ASSERT.check(in.available() == 0, "Internal error: Extra data at end of class file");
    if (super_class > 0) {
      SuperClass = Load(((CONSTANT_Class_info)constant_pool.table[super_class]).name);
    }
    PostConstruct();
  }
}

class KlassArray extends Klass {
  KlassArray(String classname)
  {
    super(classname);
    // change "struct Array" declaration when changing this
    fields_count = 4;
    fields = new field_info[fields_count];
    fields[0] = new field_info();
    fields[0].name = "length";
    fields[0].signature = "S";
    fields[1] = new field_info();
    fields[1].name = "elsize";
    fields[1].signature = "S";
    fields[2] = new field_info();
    fields[2].name = "objtype";
    fields[2].signature = "S";
    fields[3] = new field_info();
    fields[3].name = "data";
    fields[3].signature = "L;";
    SuperClass = Load("java/lang/Object");
    PostConstruct();
  }
}

class Jump implements JVM {

  static final int CHARSIZE = 1; // or 2

  static final boolean ARRAYPADDING = true;

  static String Nativepath;
  static String Javac;
  static String Pila;

  static boolean CompileCheck = false;
  static boolean DeleteAsm = false;
  static boolean Listing = false;
  static boolean Optimize = false;
  static boolean DebugSymbols = false;
  static boolean Verbose = false;

  static boolean NativeMethodsMissing = false;

  static Hashtable Native = new Hashtable();

  static void ProcessOptions(String opt)
  {
    for (int i = 0; i < opt.length(); i++) {
      switch (Character.toLowerCase(opt.charAt(i))) {
        case 'c':
          CompileCheck = true;
          break;
        case 'd':
          DeleteAsm = true;
          break;
        case 'l':
          Listing = true;
          break;
        case 'o':
          Optimize = true;
          break;
        case 's':
          DebugSymbols = true;
          break;
        case 'v':
          Verbose = true;
          break;
        case '?':
          System.out.print(Usage);
          System.exit(1);
      }
    }
  }

  static void CreateNativeIndex(Vector natives) throws IOException
  {
    if (Verbose) {
      System.out.println("Creating "+Nativepath+"native.index");
    }
    PrintWriter index = new PrintWriter(new BufferedOutputStream(new FileOutputStream(Nativepath+"native.index")));
    for (int i = 0; i < natives.size(); i++) {
      index.println(Nativepath+natives.elementAt(i)+" "+new File(Nativepath+natives.elementAt(i)).lastModified());
    }
    index.println();
    for (int i = 0; i < natives.size(); i++) {
      if (Verbose) {
        System.out.println("Reading " + Nativepath+natives.elementAt(i));
      }
      RandomAccessFile nf = new RandomAccessFile(Nativepath+natives.elementAt(i), "r");
      try {
        while (true) {
          String s = nf.readLine();
          if (s == null) {
            break;
          }
          if (s.length() > 0 && !Character.isWhitespace(s.charAt(0))) {
            int p = s.indexOf(':');
            if (p == -1) {
              p = s.length();
            }
            index.println(s.substring(0, p)+" "+Nativepath+natives.elementAt(i)+" "+nf.getFilePointer());
          }
        }
      } catch (EOFException e) {
      }
    }
    index.close();
  }

  static void DoOptimize(String fn) throws IOException
  {
    BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fn)));
    PrintWriter out = new PrintWriter(new BufferedOutputStream(new FileOutputStream(fn+".temp")));
    Vector Sources = new Vector();
    Vector CombinedMoves = new Vector();
    while (true) {
      String s = in.readLine();
      if (s == null) {
        break;
      }
      strtok st = new strtok(s, "\t");
      String t = st.next();
      if (t == null) {
        continue;
      }
      if (t.equals("move.l")) {
        String src = st.next(",");
        String dest = st.next("");
        if (dest.equals("-(a7)")) {
          while (!CombinedMoves.isEmpty()) {
            out.println(CombinedMoves.firstElement());
            CombinedMoves.removeElementAt(0);
          }
          Sources.addElement(src);
        } else if (src.equals("(a7)+") && !Sources.isEmpty()) {
          String ss = (String)Sources.lastElement();
          Sources.removeElementAt(Sources.size()-1);
          if (ss.equals("#0")) {
            CombinedMoves.addElement("\tclr.l\t" + dest);
          } else {
            CombinedMoves.addElement("\tmove.l\t" + ss + "," + dest);
          }
        } else {
          while (!Sources.isEmpty()) {
            String ss = (String)Sources.firstElement();
            Sources.removeElementAt(0);
            if (ss.equals("#0")) {
              out.println("\tclr.l\t-(a7)");
            } else {
              out.println("\tmove.l\t" + ss + ",-(a7)");
            }
          }
          while (!CombinedMoves.isEmpty()) {
            out.println(CombinedMoves.firstElement());
            CombinedMoves.removeElementAt(0);
          }
          out.println(s);
        }
      } else if (t.equals("clr.l")) {
        String dest = st.next("");
        if (dest.equals("-(a7)")) {
          Sources.addElement("#0");
        } else {
          while (!Sources.isEmpty()) {
            String src = (String)Sources.firstElement();
            Sources.removeElementAt(0);
            if (src.equals("#0")) {
              out.println("\tclr.l\t-(a7)");
            } else {
              out.println("\tmove.l\t" + src + ",-(a7)");
            }
          }
          while (!CombinedMoves.isEmpty()) {
            out.println(CombinedMoves.firstElement());
            CombinedMoves.removeElementAt(0);
          }
          out.println(s);
        }
      } else if (s.equals("\taddq.l\t#4,a7") && Sources.size() > 0) {
        Sources.setSize(Sources.size()-1);
      } else {
        while (!Sources.isEmpty()) {
          String src = (String)Sources.firstElement();
          Sources.removeElementAt(0);
          if (src.equals("#0")) {
            out.println("\tclr.l\t-(a7)");
          } else {
            out.println("\tmove.l\t" + src + ",-(a7)");
          }
        }
        while (!CombinedMoves.isEmpty()) {
          out.println(CombinedMoves.firstElement());
          CombinedMoves.removeElementAt(0);
        }
        out.println(s);
      }
    }
    in.close();
    out.close();
    new File(fn).delete();
    new File(fn+".temp").renameTo(new File(fn));
  }

  static int Run(String cmdline) throws IOException
  {
    System.out.println("Running: " + cmdline);
    Process p = Runtime.getRuntime().exec(cmdline);
    InputStream in = p.getInputStream();
    while (true) {
      int c = in.read();
      if (c < 0) {
        break;
      }
      System.out.write(c);
    }
    return p.exitValue();
  }

  static final String Usage =
    "Usage: jump [-cdlosv] classname\n\n"+
    "Options: -c  Compile .class file from .java file if necessary\n"+
    "         -d  Delete .asm file after running Pila\n"+
    "         -l  Pass on to Pila to generate assembly listing file\n"+
    "         -o  Enable peephole optimizer\n"+
    "         -s  Include debugging symbols in output\n"+
    "         -v  Verbose output\n";

  public static void main(String[] args)
  {
    System.out.println(
      "Jump  Java(tm) User Module for Pilot  Version 1.0 Beta 4\n"+
      "Copyright (c) 1996,1997 by Greg Hewgill\n"+
      "http://userzweb.lightspeed.net/~gregh/pilot\n"+
      "Please see the file COPYING for distribution rights.\n");
    PrintWriter asmout = null;
    try {
      String classname = null;
      Properties prop = new Properties();
      String pfn = Klass.FindFileOnClasspath("Jump.properties");
      if (pfn != null) {
        prop.load(new FileInputStream(pfn));
      }
      Nativepath = new String(prop.getProperty("nativepath", pfn == null ? "." : pfn.substring(0, pfn.length()-15)));
      if (!Nativepath.endsWith(System.getProperty("file.separator"))) {
        Nativepath += System.getProperty("file.separator");
      }
      Javac = new String(prop.getProperty("javac", "javac"));
      Pila = new String(prop.getProperty("pila", "pila"));
      ProcessOptions(prop.getProperty("options", ""));
      for (int i = 0; i < args.length; i++) {
        if (args[i].charAt(0) == '-') {
          ProcessOptions(args[i]);
        } else if (classname == null) {
          classname = args[i];
        } else {
          System.out.println("Only specify one class name on the command line.");
          System.exit(1);
        }
      }
      if (classname == null) {
        System.out.print(Usage);
        System.exit(1);
      }
      if (classname.toLowerCase().endsWith(".class")) {
        classname = classname.substring(0, classname.length()-6);
      }
      //if (Klass.Find("java/lang/Object") == null) {
      //  System.out.println("Jump cannot find the Object.class file in the classpath:");
      //  System.out.println();
      //  System.out.println("  "+System.getProperty("java.class.path"));
      //  System.out.println();
      //  System.out.println("This probably means you need to unzip the classes.zip file in the");
      //  System.out.println("system class directory.");
      //  System.out.println("See the Jump.htm documentation for more information.");
      //  System.exit(1);
      //}
      if (CompileCheck) {
        long javafiletime = 0;
        File f = new File(classname + ".java");
        if (f != null) {
          javafiletime = f.lastModified();
        }
        long classfiletime = 0;
        f = new File(classname + ".class");
        if (f != null) {
          classfiletime = f.lastModified();
        }
        if (javafiletime > classfiletime) {
          if (Run(Javac + " " + classname + ".java") != 0) {
            System.exit(1);
          }
        }
      }
      System.out.println("Compiling: "+classname+".class");
      String[] files = new File(Nativepath).list();
      Vector natives = new Vector();
      for (int i = 0; i < files.length; i++) {
        String t = files[i].toLowerCase();
        if (t.startsWith("native-") && t.endsWith(".asm")) {
          natives.addElement(files[i]);
        }
      }
      BufferedReader f = null;
      loadindex: while (true) {
        try {
          f = new BufferedReader(new InputStreamReader(new FileInputStream(Nativepath+"native.index")));
        } catch (FileNotFoundException e) {
          CreateNativeIndex(natives);
          continue loadindex;
        }
        int count = 0;
        while (true) {
          String s = f.readLine();
          if (s.length() == 0) {
            break;
          }
          strtok t = new strtok(s, " ");
          String fn = t.next();
          String time = t.next();
          File file = new File(fn);
          if (file.exists() && file.lastModified() == Long.parseLong(time)) {
            count++;
          } else {
            f.close();
            CreateNativeIndex(natives);
            continue loadindex;
          }
        }
        if (count != natives.size()) {
          f.close();
          CreateNativeIndex(natives);
          continue loadindex;
        }
        break;
      }
      if (Verbose) {
        System.out.println("Loading "+Nativepath+"native.index");
      }
      while (true) {
        String s = f.readLine();
        if (s == null) {
          break;
        }
        strtok t = new strtok(s, " ");
        String m = t.next();
        String fn = t.next();
        long offset = Long.parseLong(t.next());
        Native.put(m, new NativeLocation(fn, offset));
      }
      Properties def = new Properties();
      try {
        def.load(new FileInputStream(classname+".jump"));
      } catch (FileNotFoundException e) {
      }
      String Appname = new String(def.getProperty("appname", classname));
      String AppId = new String(def.getProperty("appid", "test"));
      Klass mainclassfile = Klass.Load(classname, true);
      for (boolean any = true; any; ) {
        any = false;
        for (int i = 0; i < Klass.ClassList.size(); i++) {
          Klass cls = (Klass)Klass.ClassList.elementAt(i);
          any |= cls.CheckGeneratedSuperClassMethods();
        }
      }
      asmout = new PrintWriter(new BufferedOutputStream(new FileOutputStream(classname + ".asm")));
      asmout.println("; Generated from " + classname + ".class by Jump\n");
      asmout.println("\tappl\t\"" + Appname + "\",'" + AppId + "'");
      asmout.println("\tinclude\t\"Pilot.inc\"");
      asmout.println("\tinclude\t\"startup.inc\"\n");
      asmout.println("kidrTVER\tequ\t1\n");
      asmout.println("breakpoint\tequ\t$4afc\n");
      asmout.println("struct ClassInfo"); // size is 18
      asmout.println("    ClassClass.w"); // must be first
      asmout.println("    SuperClass.w");
      asmout.println("    ObjectSizePlusHeader.w");
      asmout.println("    Flags.w");
      asmout.println("    Name.l");
      asmout.println("    Vtable.l");
      asmout.println("    VtableCount.w");
      asmout.println("endstruct\n");
      asmout.println("ClassInfo_array\tequ\t$0001");
      asmout.println("ClassInfo_arrayofobjects\tequ\t$0002\n");
      asmout.println("ObjectHeader_LastPtr\tequ\t-10");
      asmout.println("ObjectHeader_NextObject\tequ\t-6");
      asmout.println("ObjectHeader_ClassIndex\tequ\t-2");
      asmout.println("ObjectHeader_sizeof\tequ\t10\n");
      // change KlassArray.KlassArray when changing this
      asmout.println("struct Array");
      asmout.println("    length.w");
      asmout.println("    elsize.w");
      asmout.println("    objtype.w");
      asmout.println("    data.l");
      asmout.println("endstruct\n");
      asmout.println("struct ExceptionTableHeader");
      asmout.println("    method_offset.l");
      asmout.println("    local_space.w");
      asmout.println("    table_length.w");
      asmout.println("endstruct\n");
      asmout.println("struct ExceptionTableEntry");
      asmout.println("    start_pc_offset.w");
      asmout.println("    end_pc_offset.w");
      asmout.println("    handler_pc_offset.w");
      asmout.println("    catch_type_index.w");
      asmout.println("endstruct\n");
      asmout.println("F_EXCESS\tequ\t126");
      asmout.println("F_SIGNBIT\tequ\t$80000000");
      asmout.println("F_HIDDEN\tequ\t$00800000");
      asmout.println("F_MANT\tequ\t$7fffff");
      asmout.println("F_CONST_1\tequ\t$3f800000");
      asmout.println("F_CONST_2\tequ\t$40000000\n");
      asmout.println("\tcode\n");
      asmout.println("\talign\t2");
      asmout.println("ClassTable:");
      for (int i = 0; i < Klass.ClassList.size(); i++) {
        Klass cls = (Klass)Klass.ClassList.elementAt(i);
        ASSERT.check(cls.Index == i, "Internal error: Class index mismatch");
        cls.GenerateClassInfo(asmout);
      }
      for (int i = 0; i < Klass.ClassList.size(); i++) {
        Klass cls = (Klass)Klass.ClassList.elementAt(i);
        cls.GenerateVtable(asmout);
      }
      int objects = 0;
      for (int i = 0; i < Klass.ClassList.size(); i++) {
        Klass cls = (Klass)Klass.ClassList.elementAt(i);
        objects += cls.GenerateStatic(null, true);
      }
      asmout.println("StaticObjectCount\tdc.w\t" + objects + "\n");
      asmout.println("\tdata\n");
      asmout.println("StackEnd\tds.l\t1");
      asmout.println("FirstObject\tds.l\t1\n");
      asmout.println("StaticObjects:\n");
      for (int i = 0; i < Klass.ClassList.size(); i++) {
        Klass cls = (Klass)Klass.ClassList.elementAt(i);
        cls.GenerateStatic(asmout, true);
      }
      asmout.println("\n; Other static fields follow");
      for (int i = 0; i < Klass.ClassList.size(); i++) {
        Klass cls = (Klass)Klass.ClassList.elementAt(i);
        cls.GenerateStatic(asmout, false);
      }
      asmout.println("\n\tcode\n");
      asmout.println("PilotMain:\n"+
                     "cmd     set     12\n"+
                     "cmdPBP  set     14\n"+
                     "launchFlags     set     18\n"+
                     "        move.l  #-1,-(a7)\n"+
                     "        link    a6,#0\n"+
                     "        move.l  a5,-(a7)\n"+
                     "        move.w  launchFlags(a6),d0\n"+
                     "        and.w   #sysAppLaunchFlagNewGlobals,d0\n"+
                     "        bne.s   PilotMain_have_globals\n"+
                     "        systrap MemPtrNew(#DataSize.l)\n"+
                     "        move.l  a0,a5\n"+
                     "PilotMain_have_globals:\n"+
                     "        clr.l   FirstObject(a5)\n"+
                     "        clr.l   d0\n"+
                     "        move.w  cmd(a6),d0\n"+
                     "        move.l  d0,-(a7)\n"+
                     "        move.l  cmdPBP(a6),-(a7)\n"+
                     "        clr.l   d0\n"+
                     "        move.w  launchFlags(a6),d0\n"+
                     "        move.l  d0,-(a7)\n"+
                     "        move.l  a7,StackEnd(a5)\n"+
                     "        jsr     " + mainclassfile.Mangle("PilotMain", "(III)I") + "(pc)\n"+
                     "        lea     12(a7),a7\n"+
                     "        move.w  launchFlags(a6),d0\n"+
                     "        and.w   #sysAppLaunchFlagNewGlobals,d0\n"+
                     "        bne.s   PilotMain_had_globals\n"+
                     "        systrap MemChunkFree(a5.l)\n"+
                     "PilotMain_had_globals:\n"+
                     "        move.l  (a7)+,a5\n"+
                     "        unlk    a6\n"+
                     "        addq.l  #4,a7\n"+
                     "        rts\n");
      if (DebugSymbols) {
        asmout.println("\tdc.b\t$89,'PilotMain',0");
        asmout.println("\talign\t2\n");
      }
      for (int i = 0; i < Klass.ClassList.size(); i++) {
        Klass cls = (Klass)Klass.ClassList.elementAt(i);
        cls.GenerateCode(asmout);
      }
      asmout.println(
        "getclassinfo_a0:\n"+
        "        move.w  ObjectHeader_ClassIndex(a0),d0\n"+
        "getclassinfo:\n"+
        "        ext.l   d0\n"+
        "        move.w  d0,d7\n"+ // *sizeof(ClassInfo)
        "        asl.w   #4,d0\n"+
        "        asl.w   #1,d7\n"+
        "        add.w   d7,d0\n"+
        "        lea     ClassTable(pc),a0\n"+
        "        add.l   d0,a0\n"+
        "        rts\n");
      asmout.println(
        "instancecheck:\n"+
        "        move.w  d0,d1\n"+
        "        move.w  ObjectHeader_ClassIndex(a0),d0\n"+
        "instancecheck_loop:\n"+
        "        cmp.w   d0,d1\n"+
        "        beq.s   instancecheck_ok\n"+
        "        bsr.s   getclassinfo\n"+
        "        move.w  ClassInfo.SuperClass(a0),d0\n"+
        "        bpl.s   instancecheck_loop\n"+
        "        clr.l   d0\n"+
        "        rts\n"+
        "instancecheck_ok:\n"+
        "        clr.l   d0\n"+
        "        addq.l  #1,d0\n"+
        "        rts\n");
      asmout.println(
        "makevoidptr:\n"+
        "        move.l  4(a7),a0\n"+
        "        bsr.s   getclassinfo_a0\n"+
        "        move.w  ClassInfo.Flags(a0),d0\n"+
        "        and.w   #ClassInfo_array,d0\n"+
        "        beq.s   makevoidptr_out\n"+
        "        move.l  4(a7),a0\n"+
        "        move.l  Array.data(a0),4(a7)\n"+
        "makevoidptr_out:\n"+
        "        rts\n");
      for (int i = 0; i < 256; i++) {
        if (Klass.BytecodeUsed[i]) {
          switch (i) {
            case op_iaload:
              asmout.println(
                "op_iaload:\n"+
                "        move.l  (a7)+,a4\n"+
                "        move.l  (a7)+,d1\n"+
                "        move.l  (a7),a0\n"+
                "        clr.l   d0\n"+
                "        cmp.w   Array.length(a0),d1\n"+
                "        bcc.s   op_iaload_out\n"+
                "        move.l  Array.data(a0),a0\n"+
                "        asl.w   #2,d1\n"+
                "        move.l  (a0,d1),(a7)\n"+
                "op_iaload_out:\n"+
                "        jmp     (a4)\n");
              break;
            case op_laload:
              asmout.println(
                "op_laload:\n"+
                "        move.l  (a7)+,a4\n"+
                "        move.l  (a7)+,d1\n"+
                "        move.l  (a7),a0\n"+
                "        clr.l   d0\n"+
                "        cmp.w   Array.length(a0),d1\n"+
                "        bcc.s   op_laload_out\n"+
                "        move.l  Array.data(a0),a0\n"+
                "        asl.w   #3,d1\n"+
                "        move.l  4(a0,d1),(a7)\n"+
                "        move.l  (a0,d1),-(a7)\n"+
                "op_laload_out:\n"+
                "        jmp     (a4)\n");
              break;
            case op_faload:
              asmout.println(
                "op_faload:\n"+
                "        move.l  (a7)+,a4\n"+
                "        move.l  (a7)+,d1\n"+
                "        move.l  (a7),a0\n"+
                "        clr.l   d0\n"+
                "        cmp.w   Array.length(a0),d1\n"+
                "        bcc.s   op_faload_out\n"+
                "        move.l  Array.data(a0),a0\n"+
                "        asl.w   #2,d1\n"+
                "        move.l  (a0,d1),(a7)\n"+
                "op_faload_out:\n"+
                "        jmp     (a4)\n");
              break;
            case op_aaload:
              asmout.println(
                "op_aaload:\n"+
                "        move.l  (a7)+,a4\n"+
                "        move.l  (a7)+,d1\n"+
                "        move.l  (a7),a0\n"+
                "        clr.l   d0\n"+
                "        cmp.w   Array.length(a0),d1\n"+
                "        bcc.s   op_aaload_out\n"+
                "        move.l  Array.data(a0),a0\n"+
                "        asl.w   #2,d1\n"+
                "        move.l  (a0,d1),(a7)\n"+
                "op_aaload_out:\n"+
                "        jmp     (a4)\n");
              break;
            case op_baload:
              asmout.println(
                "op_baload:\n"+
                "        move.l  (a7)+,a4\n"+
                "        move.l  (a7)+,d1\n"+
                "        move.l  (a7),a0\n"+
                "        clr.l   d0\n"+
                "        cmp.w   Array.length(a0),d1\n"+
                "        bcc.s   op_baload_out\n"+
                "        move.l  Array.data(a0),a0\n"+
                "        move.b  (a0,d1),d0\n"+
                "        move.l  d0,(a7)\n"+
                "op_baload_out:\n"+
                "        jmp     (a4)\n");
              break;
            case op_caload:
              asmout.print(
                "op_caload:\n"+
                "        move.l  (a7)+,a4\n"+
                "        move.l  (a7)+,d1\n"+
                "        move.l  (a7),a0\n"+
                "        clr.l   d0\n"+
                "        move.w  Array.length(a0),d2\n"+
                "        cmp.w   d2,d1\n"+
                "        bcc.s   op_caload_out\n"+
                "        move.l  Array.data(a0),a0\n");
              if (CHARSIZE == 2) {
                asmout.print(
                  "        add.w   d1,d2\n"+
                  "        move.b  (a0,d2),d0\n"+
                  "        lsl.w   #8,d0\n");
              }
              asmout.println(
                "        or.b    (a0,d1),d0\n"+
                "        move.l  d0,(a7)\n"+
                "op_caload_out:\n"+
                "        jmp     (a4)\n");
              break;
            case op_saload:
              asmout.println(
                "op_saload:\n"+
                "        move.l  (a7)+,a4\n"+
                "        move.l  (a7)+,d1\n"+
                "        move.l  (a7),a0\n"+
                "        clr.l   d0\n"+
                "        cmp.w   Array.length(a0),d1\n"+
                "        bcc.s   op_saload_out\n"+
                "        move.l  Array.data(a0),a0\n"+
                "        asl.w   #1,d1\n"+
                "        move.w  (a0,d1),d0\n"+
                "        move.l  d0,(a7)\n"+
                "op_saload_out:\n"+
                "        jmp     (a4)\n");
              break;
            case op_iastore:
              asmout.println(
                "op_iastore:\n"+
                "        move.l  (a7)+,a4\n"+
                "        move.l  (a7)+,d0\n"+
                "        move.l  (a7)+,d1\n"+
                "        move.l  (a7)+,a0\n"+
                "        cmp.w   Array.length(a0),d1\n"+
                "        bcc.s   op_iastore_out\n"+
                "        move.l  Array.data(a0),a0\n"+
                "        asl.w   #2,d1\n"+
                "        move.l  d0,(a0,d1)\n"+
                "op_iastore_out:\n"+
                "        jmp     (a4)\n");
              break;
            case op_lastore:
              asmout.println(
                "op_lastore:\n"+
                "        move.l  (a7)+,a4\n"+
                "        move.l  8(a7),d1\n"+
                "        move.l  12(a7),a0\n"+
                "        cmp.w   Array.length(a0),d1\n"+
                "        bcc.s   op_lastore_out\n"+
                "        move.l  Array.data(a0),a0\n"+
                "        asl.w   #3,d1\n"+
                "        move.l  (a7),(a0,d1)\n"+
                "        move.l  4(a7),4(a0,d1)\n"+
                "op_lastore_out:\n"+
                "        add.l   #16,a7\n"+
                "        jmp     (a4)\n");
              break;
            case op_fastore:
              asmout.println(
                "op_fastore:\n"+
                "        move.l  (a7)+,a4\n"+
                "        move.l  (a7)+,d0\n"+
                "        move.l  (a7)+,d1\n"+
                "        move.l  (a7)+,a0\n"+
                "        cmp.w   Array.length(a0),d1\n"+
                "        bcc.s   op_fastore_out\n"+
                "        move.l  Array.data(a0),a0\n"+
                "        asl.w   #2,d1\n"+
                "        move.l  d0,(a0,d1)\n"+
                "op_fastore_out:\n"+
                "        jmp     (a4)\n");
              break;
            case op_aastore:
              asmout.println(
                "op_aastore:\n"+
                "        move.l  (a7)+,a4\n"+
                "        move.l  (a7)+,d0\n"+
                "        move.l  (a7)+,d1\n"+
                "        move.l  (a7)+,a0\n"+
                "        cmp.w   Array.length(a0),d1\n"+
                "        bcc.s   op_aastore_out\n"+
                "        move.l  Array.data(a0),a0\n"+
                "        asl.w   #2,d1\n"+
                "        move.l  d0,(a0,d1)\n"+
                "op_aastore_out:\n"+
                "        jmp     (a4)\n");
              break;
            case op_bastore:
              asmout.println(
                "op_bastore:\n"+
                "        move.l  (a7)+,a4\n"+
                "        move.l  (a7)+,d0\n"+
                "        move.l  (a7)+,d1\n"+
                "        move.l  (a7)+,a0\n"+
                "        cmp.w   Array.length(a0),d1\n"+
                "        bcc.s   op_bastore_out\n"+
                "        move.l  Array.data(a0),a0\n"+
                "        move.b  d0,(a0,d1)\n"+
                "op_bastore_out:\n"+
                "        jmp     (a4)\n");
              break;
            case op_castore:
              asmout.print(
                "op_castore:\n"+
                "        move.l  (a7)+,a4\n"+
                "        move.l  (a7)+,d0\n"+
                "        move.l  (a7)+,d1\n"+
                "        move.l  (a7)+,a0\n"+
                "        move.w  Array.length(a0),d2\n"+
                "        cmp.w   d2,d1\n"+
                "        bcc.s   op_castore_out\n"+
                "        move.l  Array.data(a0),a0\n"+
                "        move.b  d0,(a0,d1)\n");
              if (CHARSIZE == 2) {
                asmout.print(
                  "        add.w   d2,d1\n"+
                  "        lsr.w   #8,d0\n"+
                  "        move.b  d0,(a0,d1)\n");
              }
              asmout.println(
                "op_castore_out:\n"+
                "        jmp     (a4)\n");
              break;
            case op_sastore:
              asmout.println(
                "op_sastore:\n"+
                "        move.l  (a7)+,a4\n"+
                "        move.l  (a7)+,d0\n"+
                "        move.l  (a7)+,d1\n"+
                "        move.l  (a7)+,a0\n"+
                "        cmp.w   Array.length(a0),d1\n"+
                "        bcc.s   op_sastore_out\n"+
                "        move.l  Array.data(a0),a0\n"+
                "        asl.w   #1,d1\n"+
                "        move.w  d0,(a0,d1)\n"+
                "op_sastore_out:\n"+
                "        jmp     (a4)\n");
              break;
            case op_fadd:
              ASSERT.fail("Unimplemented: Opcode 'fadd' (float addition)");
              asmout.println(
                "op_fadd:\n"+
                "        dc.w    breakpoint\n"+
                "        rts\n");
              break;
            case op_fsub:
              asmout.println(
                "op_fsub:\n"+
                "        move.l  8(a7),d0\n"+
                "        beq.s   op_fsub_z1\n"+
                "        move.l  4(a7),d0\n"+
                "        beq.s   op_fsub_z2\n"+
                "        eor.l   #F_SIGNBIT,4(a7)\n"+
                "        bra     op_fadd\n"+
                "op_fsub_z1:\n"+
                "        move.l  (a7)+,a4\n"+
                "        move.l  (a7)+,d0\n"+
                "        eor.l   #F_SIGNBIT,d0\n"+
                "        move.l  d0,(a7)\n"+
                "        jmp     (a4)\n"+
                "op_fsub_z2:\n"+
                "        move.l  (a7)+,a4\n"+
                "        addq.l  #4,a7\n"+
                "        jmp     (a4)\n");
              break;
            case op_imul:
              asmout.println(
                "op_imul:\n"+
                "        move.l  (a7)+,a4\n"+
                "        move.l  (a7)+,d0\n"+
                "        muls    2(a7),d0\n"+
                "        move.l  d0,(a7)\n"+
                "        jmp     (a4)\n");
              break;
            case op_lmul:
              ASSERT.fail("Unimplemented: Opcode 'lmul' (long multiplication)");
              asmout.println(
                "op_lmul:\n"+
                "        dc.w    breakpoint\n"+
                "        rts\n");
              break;
            case op_fmul:
              ASSERT.fail("Unimplemented: Opcode 'fmul' (float multiplication)");
              asmout.println(
                "op_fmul:\n"+
                "        dc.w    breakpoint\n"+
                "        rts\n");
              break;
            case op_idiv:
              asmout.println(
                "op_idiv:\n"+
                "        move.l  (a7)+,a4\n"+
                "        move.l  (a7)+,d0\n"+
                "        move.l  (a7),d1\n"+
                "        divs    d0,d1\n"+
                "        ext.l   d1\n"+
                "        move.l  d1,(a7)\n"+
                "        jmp     (a4)\n");
              break;
            case op_ldiv:
              ASSERT.fail("Unimplemented: Opcode 'ldiv' (long division)");
              asmout.println(
                "op_ldiv:\n"+
                "        dc.w    breakpoint\n"+
                "        rts\n");
              break;
            case op_fdiv:
              ASSERT.fail("Unimplemented: Opcode 'fdiv' (float division)");
              asmout.println(
                "op_fdiv:\n"+
                "        dc.w    breakpoint\n"+
                "        rts\n");
              break;
            case op_irem:
              asmout.println(
                "op_irem:\n"+
                "        move.l  (a7)+,a4\n"+
                "        move.l  (a7)+,d0\n"+
                "        move.l  (a7),d1\n"+
                "        divs    d0,d1\n"+
                "        asr.l   #8,d1\n"+
                "        asr.l   #8,d1\n"+
                "        move.l  d1,(a7)\n"+
                "        jmp     (a4)\n");
              break;
            case op_lrem:
              ASSERT.fail("Unimplemented: Opcode 'lrem' (long remainder)");
              asmout.println(
                "op_lrem:\n"+
                "        dc.w    breakpoint\n"+
                "        rts\n");
              break;
            case op_frem:
              ASSERT.fail("Unimplemented: Opcode 'frem' (float remainder)");
              asmout.println(
                "op_frem:\n"+
                "        dc.w    breakpoint\n"+
                "        rts\n");
              break;
            case op_fneg:
              asmout.println(
                "op_fneg:\n"+
                "        move.l  4(a7),d0\n"+
                "        beq.s   op_fneg_out\n"+
                "        eor.l   #F_SIGNBIT,d0\n"+
                "        move.l  d0,4(a7)\n"+
                "op_fneg_out:\n"+
                "        rts\n");
              break;
            case op_lshl:
              asmout.println(
                "op_lshl:\n"+
                "        move.l (a7)+,a4\n"+
                "        move.l (a7)+,d2\n"+
                "        and.l  #$3f,d2\n"+
                "        subq.l #1,d2\n"+
                "        bmi.s  op_lshl_out\n"+
                "        move.l (a7),d0\n"+
                "        move.l 4(a7),d1\n"+
                "op_lshl_loop:\n"+
                "        shl    #1,d1\n"+
                "        roxl   #1,d0\n"+
                "        dbra   d2,op_lshl_loop\n"+
                "        move.l d0,(a7)\n"+
                "        move.l d1,4(a7)\n"+
                "op_lshl_out:\n"+
                "        jmp    (a4)\n");
              break;
            case op_lshr:
              ASSERT.fail("Unimplemented: Opcode 'lshr' (long shift right)");
              asmout.println(
                "op_lshr:\n"+
                "        dc.w    breakpoint\n"+
                "        rts\n");
              break;
            case op_i2f:
              ASSERT.fail("Unimplemented: Opcode 'i2f' (int to float)");
              asmout.println(
                "op_i2f:\n"+
                "        dc.w    breakpoint\n"+
                "        rts\n");
              break;
            case op_f2i:
              ASSERT.fail("Unimplemented: Opcode 'f2i' (float to int)");
              asmout.println(
                "op_f2i:\n"+
                "        dc.w    breakpoint\n"+
                "        rts\n");
              break;
            case op_lcmp:
              ASSERT.fail("Unimplemented: Opcode 'lcmp' (long compare)");
              asmout.println(
                "op_lcmp:\n"+
                "        dc.w    breakpoint\n"+
                "        rts\n");
              break;
            case op_fcmpl:
              asmout.println(
                "op_fcmpl:\n"+
                "        move.l  (a7)+,a4\n"+
                "        move.l  (a7)+,d2\n"+
                "        move.l  (a7)+,d1\n"+
                "        clr.l   d0\n"+
                "        move.l  d1,d3\n"+
                "        and.l   d2,d3\n"+
                "        bpl.s   op_fcmpl_notbothneg\n"+
                "        move.l  #F_SIGNBIT,d3\n"+
                "        eor.l   d3,d1\n"+
                "        eor.l   d3,d2\n"+
                "op_fcmpl_notbothneg:\n"+
                "        cmp.l   d2,d1\n"+
                "        beq.s   op_fcmpl_out\n"+
                "        sgt     d0\n"+
                "        asl.b   #1,d0\n"+
                "        subq.l  #1,d0\n"+
                "op_fcmpl_out:\n"+
                "        move.l  d0,-(a7)\n"+
                "        jmp     (a4)\n");
              break;
            case op_fcmpg:
              asmout.println(
                "op_fcmpg:\n"+
                "        bra.s   op_fcmpl\n");
              break;
            case op_invokevirtual:
              asmout.println(
                "op_invokevirtual:\n"+
                "        move.w  ObjectHeader_ClassIndex(a0),d0\n"+
                "op_invokevirtual_top:\n"+
                "        jsr     getclassinfo(pc)\n"+
                "        lea     0(pc),a1\n"+
                "        move.l  ClassInfo.Vtable(a0),a2\n"+
                "        move.w  ClassInfo.VtableCount(a0),d2\n"+
                "        add.l   a1,a2\n"+
                "        bra.s   op_invokevirtual_loopend\n"+
                "op_invokevirtual_loop:\n"+
                "        cmp.w   (a2),d1\n"+
                "        beq.s   op_invokevirtual_found\n"+
                "        add.l   #6,a2\n"+
                "op_invokevirtual_loopend:\n"+
                "        dbra    d2,op_invokevirtual_loop\n"+
                "        move.w  ClassInfo.SuperClass(a0),d0\n"+
                "        bpl     op_invokevirtual_top\n"+
                "        systrap ErrDisplayFileLineMsg(&op_invokevirtual_msg(pc).l, #0.w, &op_invokevirtual_text(pc).l)\n"+
                "        dc.w    breakpoint\n"+
                "op_invokevirtual_found:\n"+
                "        add.l   2(a2),a1\n"+
                "        jmp     (a1)\n");
              asmout.println(
                "op_invokevirtual_msg    dc.b    'Java internal error',0\n"+
                "op_invokevirtual_text   dc.b    'Virtual method not found',0\n"+
                "        align   2\n");
              break;
            case op_new:
              asmout.println(
                "op_new:\n"+
                "        move.w  d0,d2\n"+
                "        jsr     " + Klass.Load("java/lang/Runtime").Mangle("gc", "()V") + "(pc)\n"+
                "        move.w  d2,d0\n"+
                "        jsr     getclassinfo(pc)\n"+
                "        clr.l   d3\n"+
                "        move.w  ClassInfo.ObjectSizePlusHeader(a0),d3\n"+
                "        systrap MemPtrNew(d3.l)\n"+
                "        lea     ObjectHeader_sizeof(a0),a2\n"+
                "        systrap MemSet(a0.l,d3.l,#0.b)\n"+
                "        move.l  FirstObject(a5),ObjectHeader_NextObject(a2)\n"+
                "        move.l  a2,FirstObject(a5)\n"+
                "        move.w  d2,ObjectHeader_ClassIndex(a2)\n"+
                "        move.l  (a7),a4\n"+
                "        move.l  a2,(a7)\n"+
                "        jmp     (a4)\n");
              break;
            case op_newarray:
              asmout.print(
                "op_newarray:\n"+
                "        move.w  d1,-(a7)\n"+
                "        bsr     op_new\n"+
                "        move.l  (a7)+,a3\n"+
                "        move.w  (a7)+,d3\n"+
                "        move.l  4(a7),d0\n"+
                "        move.w  d0,Array.length(a3)\n");
              if (ARRAYPADDING) {
                asmout.print(
                  "        addq.l  #1,d0\n");
              }
              asmout.println(
                "        move.w  d3,Array.elsize(a3)\n"+
                "        move.l  a3,4(a7)\n"+
                "        mulu    d0,d3\n"+
                "        systrap MemPtrNew(d3.l)\n"+
                "        move.l  a0,Array.data(a3)\n"+
                "        systrap MemSet(a0.l,d3.l,#0.b)\n"+
                "        rts\n");
              break;
            case op_anewarray:
              asmout.println(
                "op_anewarray:\n"+
                "        move.w  d1,-(a7)\n"+
                "        bsr     op_new\n"+
                "        move.l  (a7)+,a3\n"+
                "        move.w  (a7)+,d2\n"+
                "        move.l  4(a7),d0\n"+
                "        move.w  d0,Array.length(a3)\n"+
                "        move.w  #4,Array.elsize(a3)\n"+
                "        move.w  d2,Array.objtype(a3)\n"+
                "        move.l  a3,4(a7)\n"+
                "        clr.l   d3\n"+
                "        move.w  d0,d3\n"+
                "        asl.l   #2,d3\n"+
                "        systrap MemPtrNew(d3.l)\n"+
                "        move.l  a0,Array.data(a3)\n"+
                "        systrap MemSet(a0.l,d3.l,#0.b)\n"+
                "        rts\n");
              break;
            case op_athrow:
              asmout.println(
                "op_athrow:\n"+
                "        move.l  (a7)+,d2\n"+
                "        move.l  (a7)+,a2\n"+
                "op_athrow_loop:\n"+
                "        move.l  4(a6),a3\n"+
                "        cmp.l   #0,a3\n"+
                "        beq.s   op_athrow_skipframe\n"+
                "        cmp.l   #-1,a3\n"+
                "        beq.s   op_athrow_notfound\n"+
                "        lea     0(pc),a0\n"+
                "        sub.l   a0,d2\n"+
                "        sub.l   ExceptionTableHeader.method_offset(a3),d2\n"+
                "        move.w  ExceptionTableHeader.table_length(a3),d3\n"+
                "        move.l  a3,a1\n"+
                "        addq.l  #sizeof(ExceptionTableHeader),a1\n"+
                "        bra.s   op_athrow_searchend\n"+
                "op_athrow_searchloop:\n"+
                "        cmp.w   ExceptionTableEntry.start_pc_offset(a1),d2\n"+
                "        bls.s   op_athrow_searchnext\n"+
                "        cmp.w   ExceptionTableEntry.end_pc_offset(a1),d2\n"+
                "        bhi.s   op_athrow_searchnext\n"+
                "        move.w  ExceptionTableEntry.catch_type_index(a1),d0\n"+
                "        move.l  a2,a0\n"+
                "        jsr     instancecheck(pc)\n"+
                "        tst.l   d0\n"+
                "        beq.s   op_athrow_searchnext\n"+
                "        clr.l   d0\n"+
                "        move.w  ExceptionTableHeader.local_space(a3),d0\n"+
                "        move.l  a6,d1\n"+
                "        sub.l   d0,d1\n"+
                "        move.l  d1,a7\n"+
                "        move.l  a2,-(a7)\n"+
                "        clr.l   d0\n"+
                "        move.w  ExceptionTableEntry.handler_pc_offset(a1),d0\n"+
                "        lea     0(pc),a0\n"+
                "        add.l   ExceptionTableHeader.method_offset(a3),a0\n"+
                "        add.l   d0,a0\n"+
                "        jmp     (a0)\n"+
                "op_athrow_searchnext:\n"+
                "        addq.l  #sizeof(ExceptionTableEntry),a1\n"+
                "op_athrow_searchend:\n"+
                "        dbra    d3,op_athrow_searchloop\n"+
                "op_athrow_skipframe:\n"+
                "        move.l  8(a6),d2\n"+
                "        move.l  (a6),a6\n"+
                "        bra.s   op_athrow_loop\n"+
                "op_athrow_notfound:\n"+
                "        move.w  ObjectHeader_ClassIndex(a2),d0\n"+
                "        jsr     getclassinfo(pc)\n"+
                "        lea     0(pc),a3\n"+
                "        add.l   ClassInfo.Name(a0),a3\n"+
                "        move.l  a2,-(a7)\n"+
                "        jsr     "+Klass.Load("java/lang/Throwable").Mangle("getMessage", "()Ljava/lang/String;")+"(pc)\n"+
                "        move.l  (a0),a1\n"+
                "        systrap ErrDisplayFileLineMsg(a3.l, #0.w, Array.data(a1).l)\n"+
                "        dc.w    breakpoint\n"+
                "        rts\n");
              break;
            case op_checkcast:
              asmout.println(
                "op_checkcast:\n"+
                "        move.l  4(a7),a0\n"+
                "        jsr     instancecheck(pc)\n"+
                "        tst.l   d0\n"+
                "        bne.s   op_checkcast_out\n"+
                "        dc.w    breakpoint\n"+ //!! throw invalid cast exception
                "op_checkcast_out:\n"+
                "        rts\n");
              break;
            case op_instanceof:
              asmout.println(
                "op_instanceof:\n"+
                "        move.l  4(a7),a0\n"+
                "        jsr     instancecheck(pc)\n"+
                "        move.l  d0,4(a7)\n"+
                "        rts\n");
              break;
            case op_monitorenter:
              asmout.println(
                "op_monitorenter:\n"+
                "        move.l  (a7)+,a4\n"+
                "        addq.l  #4,a7\n"+
                "        jmp     (a4)\n");
              break;
            case op_monitorexit:
              asmout.println(
                "op_monitorexit:\n"+
                "        move.l  (a7)+,a4\n"+
                "        addq.l  #4,a7\n"+
                "        jmp     (a4)\n");
              break;
            case op_multianewarray:
              ASSERT.fail("Unimplemented: Opcode 'multianewarray' (new multidimensional array initialization)");
              asmout.println(
                "op_multianewarray:\n"+
                "        dc.w    breakpoint\n"+
                "        rts\n");
              break;
          }
        }
      }
      asmout.println(
        "proc CharPtr_to_String()\n"+
        "local a.Array\n"+
        "beginproc\n"+
        "        move.l  a0,a.data(a6)\n"+
        "        move.w  #1,a.elsize(a6)\n"+
        "        systrap StrLen(a0.l)\n"+
        "        move.w  d0,a.length(a6)\n"+
        "        move.w  #" + Klass.Load("java/lang/String").Index + ",d0\n"+
        "        bsr     op_new\n"+
        "        move.l  (a7),-(a7)\n"+
        "        pea     a(a6)\n"+
        "        clr.l   -(a7)\n"+
        "        jsr     " + Klass.Load("java/lang/String").Mangle("<init>", "([BI)V") + "(pc)\n"+
        "        addq.l  #8,a7\n"+
        "        move.l  (a7)+,a0\n"+
        "endproc\n");
      for (int i = 0; i < Klass.Strings.size(); i++) {
        asmout.println("S" + i + ":\n\tdc.b\t'" + Klass.Strings.elementAt(i) + "',0");
      }
      asmout.println();
      if (NativeMethodsMissing) {
        asmout.println("native_method_missing_msg\tdc.b\t'Native method missing',0");
        asmout.println();
      }
      asmout.println("\tdata\n");
      asmout.println("DataSize\tds.w\t0");
      asmout.println();
      try {
        InputStream res = new BufferedInputStream(new FileInputStream(classname + ".res"));
        if (Verbose) {
          System.out.println("appending " + classname + ".res");
        }
        while (true) {
          int c = res.read();
          if (c < 0) {
            break;
          }
          asmout.write(c);
        }
        asmout.println();
      } catch (FileNotFoundException e) {
      }
      asmout.println("\tres\t'tver', kidrTVER");
      asmout.println("\tdc.b\t'1.0', 0\n");
      //asmout.println("\tres\t'pref', kidrPREF");
      //asmout.println("\tdc.w\tsysAppLaunchFlagNewStack|sysAppLaunchFlagNewGlobals|sysAppLaunchFlagUIApp|sysAppLaunchFlagSubCall");
      //asmout.println("\tdc.l\t$1000");
      //asmout.println("\tdc.l\t$1000\n");
      asmout.println("\tend");
      asmout.close();
      if (Optimize) {
        if (Verbose) {
          System.out.println("Optimizing...");
        }
        DoOptimize(classname + ".asm");
      }
      if (Run(Pila + " -" + (Listing ? "l" : "") + (DebugSymbols ? "s" : "") + " " + classname + ".asm") != 0) {
        System.exit(1);
      }
      if (DeleteAsm) {
        new File(classname+".asm").delete();
      }
    } catch (Throwable e) {
      if (asmout != null) {
        asmout.close();
      }
      e.printStackTrace();
      System.exit(1);
    }
  }
}
