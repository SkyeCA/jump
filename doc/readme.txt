Jump Release Notes
==================
Greg Hewgill (gregh@lightspeed.net)
Jump Home Page: http://userzweb.lightspeed.net/~gregh/pilot/jump

------------------------------------------------------------------------------
Known problems in the current release
------------------------------------------------------------------------------
- See Jump.htm, section 1.6 (Limitations) for a list of unimplemented Java 
  features.

- Jump will not correctly process the Life sample if you use Sun's javac.
  Microsoft's jvc works ok for now.

- Microsoft's VM sometimes hangs when running Jump on some computers.
  I believe this is caused by the Microsoft JIT compiler. To turn off the
  JIT compiler, load Internet Explorer and go to View/Options/Advanced. 
  Uncheck the "Enable Java JIT compiler" option. This causes Java programs
  to run a little bit slower, but at least they will work properly.

- If you install Jump in a directory whose full path contains spaces (ie.
  "C:\Program Files\Pilot\Jump"), Jump will not work properly. Relocate it
  to a path without spaces.

------------------------------------------------------------------------------
10/23/97 Version 1.0 beta 4
------------------------------------------------------------------------------

- The Jump source code is now included! Have fun everybody.

- JDK 1.1 is now supported.

------------------------------------------------------------------------------
1/2/97 Version 1.0 beta 3a
------------------------------------------------------------------------------

- Oops. I forgot one file (AssertionFailedException.class).

------------------------------------------------------------------------------
1/1/97 Version 1.0 beta 3
------------------------------------------------------------------------------

- Fixed a few more problems with static final fields not being generated
  correctly.

- Fixed problems with static fields that were not 32 bits wide. The generated
  code was accessing them as 32-bit variables.

- The 'tableswitch' opcode is now supported, so there should not be any more
  problems compiling switch statements.

- Fixed more problems with PalmOS functions take take enumeration parameters
  (which are byte sized in the Metrowerks compiler).

- Fixed a fairly major bug that essentially broke polymorphism. Calling
  subclass methods from a superclass should work now.

- Changed the palmos package so that all PalmOS API functions appear in a
  class called "Palm". You should delete all *.class files from your palmos
  subdirectory before unzipping the new palmos.zip.

- Added all the MemXxx functions to the Palm class.

- Native functions that do not have definitions in a native-*.asm file will
  now cause a warning to be generated. A default stub will be compiled into
  the application that brings up a warning box with the name of the missing
  native method.

- Added a bunch more methods for accessing the fields of an Event object.

- Better documentation for unimplemented opcodes and APIs (all unimplemented
  features should be listed now).

------------------------------------------------------------------------------
12/14/96 Version 1.0 beta 2
------------------------------------------------------------------------------

- Java exceptions are now supported. Uncaught exceptions bring up a 
  "Fatal Error" box with the class of the exception and the exception message.

- Fixed problem with -o (Optimize) flag causing a NullPointerException.

- Fixed problems with PalmOS stubs for functions that take Byte parameters.

- Fixed DateToAscii and DateToDOWDMFormat so that the last parameter is a
  StringBuffer instead of a String (it is where the value is returned).
  When using these functions, you should create a new StringBuffer with at
  least 16 characters of space available. If you don't create one with enough
  space, bad things will probably happen.

------------------------------------------------------------------------------
12/11/96 Version 1.0 beta 1
------------------------------------------------------------------------------

- The throw exception routine now brings up a "Fatal Error" box instead of
  just crashing. It will give you the text of the exception.

- Added a new -v (Verbose) option so that now by default, Jump's output is
  very concise.

------------------------------------------------------------------------------
12/10/96 Version 1.0 alpha 2
------------------------------------------------------------------------------

- Improved error messages on startup if Jump cannot find the system class
  files.

- Changed the way Jump locates class files and native .asm files. Please see
  Jump.htm, section 1.5 (Installation) for more information. This should be
  simpler than the previous version.

- Fixed a problem with accessing static final fields if the compiler did not
  already optimize the field reference to a literal constant. Specifically,
  this turned up when using the "developer" classes extracted from classd.exe
  in the Microsoft Java SDK.

- Fixed problems with storing byte and word sized values into arrays (it used
  to corrupt data or crash).

- Fixed problems with java.lang.System.arraycopy() native function, this
  caused problems when concatenating strings.

- Fixed problems with arrays of arrays not being scanned properly by the
  garbage collector, causing the contained arrays to be prematurely freed.

- Fixed problem with accessing byte sized object fields.

- Minor optimizations in the code generator.

------------------------------------------------------------------------------
11/24/96 Version 1.0 alpha 1
------------------------------------------------------------------------------

This is the first alpha test version of Jump. If you have any comments
on the program or documentation, please let me know at the email address
at the top of this file.
