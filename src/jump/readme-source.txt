Jump source notes (10/23/97)
----------------------------

After 10 months of basically not working on Jump, I have finally
(with some prodding by fellow developers :) decided to release the
source code for Jump. I had every intention of doing this once Jump
got to "a certain point", but it was clear that point wasn't going
to be reached any time in the near future.

I hope that the release of this code will help other developers
extend and enhance Jump. Or, it could be used as a starting point
for a Personal or Embedded Java Virtual Machine implementation.
Whatever you decide to do with it, I hope it helps even just a 
little bit.

Jump and its source are now distributed under the GNU General 
Public License. Please see the file COPYING in the Jump distribution
file for details.

I am still available to answer questions about the Jump source, feel
free to email me <gregh@lightspeed.net> with your comments and
questions.

Building Jump
-------------

These instructions are for the Sun JDK. Substitute your compiler
and interpreter name as appropriate if you're not using the JDK.

1. Unzip the Jump package into a clean directory.
2. Unzip palmos.zip and jump-source.zip. When unzipping palmos.zip,
   make sure it creates the "palmos" subdirectory.
3. Run "javac *.java" to compile all the *.java files. Or do them one 
   by one. This should create 37 *.class files.
4. Run "java mkapi". This should dump out a list of all the PalmOS API
   functions. This step creates native-palmos.asm and palmos/Palm.java
   from the palmos.api source.
5. (optional) If you're using the Microsoft Java SDK and want to create
   a native .exe file (Jump.exe), run:
     jexegen /main:Jump /out:Jump.exe @jump.lst

You should now be able to run Jump using "java Jump <args>". You will
probably want to add the directory containing Jump.class and friends to
your CLASSPATH environment variable.

Known problems
--------------

There are several known problems in Jump as it stands today.

The source has virtually no comments. Sorry, I'm bad about that.

I believe the garbage collector has a bug. PalmPilot RAM gets trashed as
Jumped programs run on the PalmPilot. This causes all kinds of neat crashes
from Jump and the Palm OS.

JDK 1.1 modified the Character class so it initializes lookup tables 
in RAM that document Unicode character properties. The total size of 
these tables is 9328 bytes (according to java/lang/Character.java).
Besides the fact that Jump does not run class initialization fucntions,
this is an unacceptable amount of memory to use on a Personal with only
14k of usable dynamic RAM to start with.

The palmos.api file is hand-maintained and may contain errors. It does not 
contain any of the PalmOS v2 functions.

There need to be classes to directly manipulate bytes in PalmOS database
records (absolute addressing).

There is probably more stuff that should go here. If I think of it I'll 
add it and update this package.

Things to do
------------

Section 1.6 of the Jump.htm documentation contains a list of things
that are not yet done. Here are some more notes to myself that I found:

- more verbose option for asm comments (explain what it's doing better)
- auto generate res file from rcp file
- CharPtr_to_String should bail out when param null
- single letter class names cause a problem
- when getting an ascii string from java.lang.String, the substring
  case is not handled properly and the whole buffer is passed on
- need a function to return actual byte size of object
- the native indexing code does not deal with paths with spaces in them
- zero out static data areas
- optimize the palmos stubs better
- floating point library
- source level debugger
