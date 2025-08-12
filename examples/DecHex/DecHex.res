;
; Resources -----------------------------------------------------------------
;

; Application Icon Bitmap resource. Is automatically converted from Windows
; format to Pilot format and written as a 'tAIB' rather than 'Tbmp' because
; kidbAIB is a special value ($7ffe)

        res 'WBMP', $7ffe, "DecHex.bmp"

; Form resource

        res 'tFRM', 1000, "tfrm03e8.bin"

; Menu resource

        res 'MBAR', 1000, "mbar03e8.bin"

; Alert resources

        res 'Talt', 1000, "talt03e8.bin"
        res 'Talt', 1001, "talt03e9.bin"
        res 'Talt', 1002, "talt03ea.bin"
