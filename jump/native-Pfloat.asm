palmos$Pfloat__init:
        systrap FplInit()
        rts

palmos$Pfloat__free:
        systrap FplFree()
        rts

palmos$Pfloat__initInt:
        link    a6,#0
        systrap FplLongToFloat(12(a6).l)
        unlk    a6
        rts

palmos$Pfloat__toString:
        link    a6,#-20
        systrap FplFToA(8(a6).l,&-20(a6))
        lea     -20(a6),a0
        jsr     CharPtr_to_String
        unlk    a6
        rts
