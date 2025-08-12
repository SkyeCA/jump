java$lang$Class__getName:
        move.l  4(a7),a0
        move.l  ClassInfo.Name(a0),a0
        lea     0(pc),a1
        add.l   a1,a0
        jsr     CharPtr_to_String(pc)
        rts

java$lang$Class__isInterface:
        clr.l   d0
        rts

java$lang$Object__getClass:
        link    a6,#0
        move.l  8(a6),a0
        jsr     getclassinfo_a0(pc)
        add.l   #2,a0
        unlk    a6
        rts

java$lang$Object__hashCode:
        move.l  4(a7),d0
        rts

java$lang$Runtime__gc:
        lea     FirstObject(a5),a2
gc_loop:
        move.l  (a2),a1
        cmp.l   #0,a1
        beq     gc_out
        move.l  ObjectHeader_LastPtr(a1),a0
        cmp.l   (a0),a1
        beq     gc_next
        move.l  a7,a0
        move.l  StackEnd(a5),d0
        sub.l   a0,d0
        lsr.w   #1,d0
        moveq.l #2,d1
        bsr     gc_search
        cmp.l   #0,a0
        bne.s   gc_found
        lea     StaticObjects(a5),a0
        move.w  StaticObjectCount(pc),d0
        moveq.l #4,d1
        bsr.s   gc_search
        cmp.l   #0,a0
        bne.s   gc_found
        move.l  FirstObject(a5),a3
gc_obj_loop:
        cmp.l   #0,a3
        beq.s   gc_obj_out
        cmp.l   a3,a1
        beq.s   gc_obj_next
        move.w  ObjectHeader_ClassIndex(a3),d0
        jsr     getclassinfo(pc)
        move.w  ClassInfo.Flags(a0),d0
        and.w   #ClassInfo_arrayofobjects,d0
        bne.s   gc_obj_array
        move.w  ClassInfo.ObjectSizePlusHeader(a0),d0
        sub.w   #ObjectHeader_sizeof,d0
        lsr.w   #1,d0
        moveq.l #2,d1
        move.l  a3,a0
        pea     gc_obj_check(pc)
        bra.s   gc_search
gc_obj_array:
        move.w  Array.length(a3),d0
        moveq.l #4,d1
        move.l  Array.data(a3),a0
        bsr.s   gc_search
gc_obj_check:
        cmp.l   #0,a0
        bne.s   gc_found
gc_obj_next:
        move.l  ObjectHeader_NextObject(a3),a3
        bra     gc_obj_loop
gc_obj_out:
        move.l  ObjectHeader_NextObject(a1),(a2)
        bsr.s   gc_zap
        bra     gc_loop
gc_found:
        move.l  a0,ObjectHeader_LastPtr(a1)
gc_next:
        move.l  (a2),a2
        lea     ObjectHeader_NextObject(a2),a2
        bra     gc_loop
gc_out:
        rts
gc_search_loop:
        cmp.l   (a0),a1
        beq.s   gc_search_out
        add.l   d1,a0
gc_search:
        dbra    d0,gc_search_loop
        move.l  #0,a0
gc_search_out:
        rts
gc_zap:
        move.w  ObjectHeader_ClassIndex(a1),d0
        jsr     getclassinfo(pc)
        move.w  ClassInfo.Flags(a0),d1
        and.w   #ClassInfo_array,d1
        beq.s   gc_zap_notarray
        movem.l a0/a1,-(a7)
        systrap MemChunkFree(Array.data(a1).l)
        movem.l (a7)+,a0/a1
gc_zap_notarray:
        clr.l   d0
        move.w  ClassInfo.ObjectSizePlusHeader(a0),d0
        lea     -ObjectHeader_sizeof(a1),a3
        systrap MemSet(a3.l,d0.l,#205.b)
        systrap MemChunkFree(a3.l)
        rts

java$lang$System__arraycopy:
        link    a6,#0
        move.l  8(a6),d2
        move.l  12(a6),d1
        move.l  16(a6),a1
        move.l  20(a6),d0
        move.l  24(a6),a0
        tst.l   d2
        beq     arraycopy_out
        cmp.w   Array.length(a0),d0
        bcc     arraycopy_out
        move.l  d0,d3
        add.l   d2,d3
        cmp.w   Array.length(a0),d3
        bhi     arraycopy_out
        cmp.w   Array.length(a1),d1
        bcc     arraycopy_out
        move.l  d1,d3
        add.l   d2,d3
        cmp.w   Array.length(a1),d3
        bhi     arraycopy_out
        mulu    Array.elsize(a0),d2
        mulu    Array.elsize(a0),d0
        mulu    Array.elsize(a0),d1
        move.l  Array.data(a0),a0
        add.l   d0,a0
        move.l  Array.data(a1),a1
        add.l   d1,a1
        systrap MemMove(a1.l,a0.l,d2.l)
arraycopy_out:
        unlk    a6
        rts

java$lang$Throwable__fillInStackTrace:
        rts
