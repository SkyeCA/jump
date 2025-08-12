palmos$Palm__AlmGetAlarm:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	move.w	18(a6),-(a7)
	trap	#15
	dc.w	sysTrapAlmGetAlarm
	lea	10(a7),a7
	unlk	a6
	rts

palmos$Palm__AlmSetAlarm:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	move.l	20(a6),-(a7)
	move.w	26(a6),-(a7)
	trap	#15
	dc.w	sysTrapAlmSetAlarm
	lea	16(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__CategoryCreateList:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.l	16(a6),-(a7)
	move.l	20(a6),-(a7)
	trap	#15
	dc.w	sysTrapCategoryCreateList
	lea	12(a7),a7
	unlk	a6
	rts

palmos$Palm__CategoryEdit:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapCategoryEdit
	addq.l	#8,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__CategoryFind:
	link	a6,#0
	move.l	8(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapCategoryFind
	addq.l	#8,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__CategoryFreeList:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapCategoryFreeList
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__CategoryGetName:
	link	a6,#0
	move.l	8(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	move.w	14(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapCategoryGetName
	lea	10(a7),a7
	unlk	a6
	rts

palmos$Palm__CategoryGetNext:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapCategoryGetNext
	addq.l	#6,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__CategoryTruncateName:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	trap	#15
	dc.w	sysTrapCategoryTruncateName
	addq.l	#6,a7
	unlk	a6
	rts

palmos$Palm__CategorySetTriggerLabel:
	link	a6,#0
	move.l	8(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapCategorySetTriggerLabel
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__CategorySelect:
	link	a6,#0
	move.l	8(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	move.l	12(a6),-(a7)
	move.w	18(a6),-(a7)
	move.w	22(a6),-(a7)
	move.w	26(a6),-(a7)
	move.l	28(a6),-(a7)
	move.l	32(a6),-(a7)
	trap	#15
	dc.w	sysTrapCategorySelect
	lea	22(a7),a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__ClipboardAddItem:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	jsr	makevoidptr(pc)
	move.b	19(a6),-(a7)
	trap	#15
	dc.w	sysTrapClipboardAddItem
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__ClipboardGetItem:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.b	15(a6),-(a7)
	trap	#15
	dc.w	sysTrapClipboardGetItem
	addq.l	#6,a7
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__CtlDrawControl:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapCtlDrawControl
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__CtlEraseControl:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapCtlEraseControl
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__CtlGetLabel:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapCtlGetLabel
	addq.l	#4,a7
	jsr	CharPtr_to_String(pc)
	unlk	a6
	rts

palmos$Palm__CtlGetValue:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapCtlGetValue
	addq.l	#4,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__CtlHandleEvent:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapCtlHandleEvent
	addq.l	#8,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__CtlHideControl:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapCtlHideControl
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__CtlHitControl:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapCtlHitControl
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__CtlEnabled:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapCtlEnabled
	addq.l	#4,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__CtlSetEnabled:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapCtlSetEnabled
	addq.l	#6,a7
	unlk	a6
	rts

palmos$Palm__CtlSetLabel:
	link	a6,#0
	move.l	8(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapCtlSetLabel
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__CtlSetUsable:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapCtlSetUsable
	addq.l	#6,a7
	unlk	a6
	rts

palmos$Palm__CtlSetValue:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapCtlSetValue
	addq.l	#6,a7
	unlk	a6
	rts

palmos$Palm__CtlShowControl:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapCtlShowControl
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__DmArchiveRecord:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmArchiveRecord
	addq.l	#6,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__DmAttachRecord:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	move.l	20(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmAttachRecord
	lea	16(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__DmAttachResource:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	move.l	20(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmAttachResource
	lea	14(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__DmCloseDatabase:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmCloseDatabase
	addq.l	#4,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__DmCreateDatabase:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	move.l	20(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	move.w	26(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmCreateDatabase
	lea	16(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__DmCreateDatabaseFromImage:
	link	a6,#0
	move.l	8(a6),-(a7)
	jsr	makevoidptr(pc)
	trap	#15
	dc.w	sysTrapDmCreateDatabaseFromImage
	addq.l	#4,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__DmDatabaseInfo:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	move.l	20(a6),-(a7)
	move.l	24(a6),-(a7)
	move.l	28(a6),-(a7)
	move.l	32(a6),-(a7)
	move.l	36(a6),-(a7)
	move.l	40(a6),-(a7)
	move.l	44(a6),-(a7)
	move.l	48(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	move.l	52(a6),-(a7)
	move.w	58(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmDatabaseInfo
	lea	50(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__DmDatabaseSize:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	move.l	20(a6),-(a7)
	move.w	26(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmDatabaseSize
	lea	18(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__DmDeleteDatabase:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.w	14(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmDeleteDatabase
	addq.l	#6,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__DmDeleteRecord:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmDeleteRecord
	addq.l	#6,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__DmDetachRecord:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.w	14(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmDetachRecord
	lea	10(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__DmDetachResource:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.w	14(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmDetachResource
	lea	10(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__DmFindDatabase:
	link	a6,#0
	move.l	8(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	move.w	14(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmFindDatabase
	addq.l	#6,a7
	unlk	a6
	rts

palmos$Palm__DmFindRecordByID:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmFindRecordByID
	lea	12(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__DmFindResource:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.w	14(a6),-(a7)
	move.l	16(a6),-(a7)
	move.l	20(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmFindResource
	lea	14(a7),a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__DmFindResourceType:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmFindResourceType
	lea	10(a7),a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__DmGetAppInfoID:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmGetAppInfoID
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__DmGetDatabase:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmGetDatabase
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__DmGetLastErr:
	link	a6,#0
	trap	#15
	dc.w	sysTrapDmGetLastErr
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__DmGetNextDatabaseByTypeCreator:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	move.w	18(a6),-(a7)
	move.l	20(a6),-(a7)
	move.l	24(a6),-(a7)
	move.l	28(a6),-(a7)
	move.w	34(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmGetNextDatabaseByTypeCreator
	lea	24(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__DmGetRecord:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmGetRecord
	addq.l	#6,a7
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__DmGetResource:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmGetResource
	addq.l	#6,a7
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__DmGetResourceIndex:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmGetResourceIndex
	addq.l	#6,a7
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__DmGet1Resource:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmGet1Resource
	addq.l	#6,a7
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__DmMoveCategory:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.w	18(a6),-(a7)
	move.l	20(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmMoveCategory
	lea	10(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__DmMoveRecord:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmMoveRecord
	addq.l	#8,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__DmNewHandle:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmNewHandle
	addq.l	#8,a7
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__DmNextOpenDatabase:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmNextOpenDatabase
	addq.l	#4,a7
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__DmNextOpenResDatabase:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmNextOpenResDatabase
	addq.l	#4,a7
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__DmNewRecord:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmNewRecord
	lea	12(a7),a7
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__DmNewResource:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.w	14(a6),-(a7)
	move.l	16(a6),-(a7)
	move.l	20(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmNewResource
	lea	14(a7),a7
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__DmNumDatabases:
	link	a6,#0
	move.w	10(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmNumDatabases
	addq.l	#2,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__DmNumRecords:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmNumRecords
	addq.l	#4,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__DmNumRecordsInCategory:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmNumRecordsInCategory
	addq.l	#6,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__DmNumResources:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmNumResources
	addq.l	#4,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__DmOpenDatabase:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	move.w	18(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmOpenDatabase
	addq.l	#8,a7
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__DmOpenDatabaseByTypeCreator:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmOpenDatabaseByTypeCreator
	lea	10(a7),a7
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__DmOpenDatabaseInfo:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	move.l	20(a6),-(a7)
	move.l	24(a6),-(a7)
	move.l	28(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmOpenDatabaseInfo
	lea	24(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__DmPositionInCategory:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmPositionInCategory
	addq.l	#8,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__DmQueryNextInCategory:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmQueryNextInCategory
	lea	10(a7),a7
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__DmQueryRecord:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmQueryRecord
	addq.l	#6,a7
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__DmRecordInfo:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	move.w	22(a6),-(a7)
	move.l	24(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmRecordInfo
	lea	18(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__DmResourceInfo:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	move.w	22(a6),-(a7)
	move.l	24(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmResourceInfo
	lea	18(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__DmReleaseRecord:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmReleaseRecord
	addq.l	#8,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__DmReleaseResource:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmReleaseResource
	addq.l	#4,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__DmRemoveRecord:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmRemoveRecord
	addq.l	#6,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__DmRemoveResource:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmRemoveResource
	addq.l	#6,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__DmRemoveSecretRecords:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmRemoveSecretRecords
	addq.l	#4,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__DmResetRecordStates:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmResetRecordStates
	addq.l	#4,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__DmResizeRecord:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.w	14(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmResizeRecord
	lea	10(a7),a7
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__DmResizeResource:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmResizeResource
	addq.l	#8,a7
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__DmSearchRecord:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmSearchRecord
	addq.l	#8,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__DmSearchResource:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	move.w	18(a6),-(a7)
	move.l	20(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmSearchResource
	lea	14(a7),a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__DmSeekRecordInCategory:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.w	18(a6),-(a7)
	move.l	20(a6),-(a7)
	move.l	24(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmSeekRecordInCategory
	lea	14(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__DmSet:
	link	a6,#0
	move.b	11(a6),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	move.l	20(a6),-(a7)
	jsr	makevoidptr(pc)
	trap	#15
	dc.w	sysTrapDmSet
	lea	14(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__DmSetDatabaseInfo:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	move.l	20(a6),-(a7)
	move.l	24(a6),-(a7)
	move.l	28(a6),-(a7)
	move.l	32(a6),-(a7)
	move.l	36(a6),-(a7)
	move.l	40(a6),-(a7)
	move.l	44(a6),-(a7)
	move.l	48(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	move.l	52(a6),-(a7)
	move.w	58(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmSetDatabaseInfo
	lea	50(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__DmSetRecordInfo:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	move.w	18(a6),-(a7)
	move.l	20(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmSetRecordInfo
	lea	14(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__DmSetResourceInfo:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	move.w	18(a6),-(a7)
	move.l	20(a6),-(a7)
	trap	#15
	dc.w	sysTrapDmSetResourceInfo
	lea	14(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__DmStrCopy:
	link	a6,#0
	move.l	8(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	jsr	makevoidptr(pc)
	trap	#15
	dc.w	sysTrapDmStrCopy
	lea	12(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__DmWrite:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	jsr	makevoidptr(pc)
	move.l	16(a6),-(a7)
	move.l	20(a6),-(a7)
	jsr	makevoidptr(pc)
	trap	#15
	dc.w	sysTrapDmWrite
	lea	16(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__DmWriteCheck:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	jsr	makevoidptr(pc)
	trap	#15
	dc.w	sysTrapDmWriteCheck
	lea	12(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__ErrDisplayFileLineMsg:
	link	a6,#0
	move.l	8(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	move.w	14(a6),-(a7)
	move.l	16(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	trap	#15
	dc.w	sysTrapErrDisplayFileLineMsg
	lea	10(a7),a7
	unlk	a6
	rts

palmos$Palm__EvtAddEventToQueue:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapEvtAddEventToQueue
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__EvtCopyEvent:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapEvtCopyEvent
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__EvtDequeuePenPoint:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapEvtDequeuePenPoint
	addq.l	#4,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__EvtDequeuePenStrokeInfo:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapEvtDequeuePenStrokeInfo
	addq.l	#8,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__EvtEnableGraffiti:
	link	a6,#0
	move.w	10(a6),-(a7)
	trap	#15
	dc.w	sysTrapEvtEnableGraffiti
	addq.l	#2,a7
	unlk	a6
	rts

palmos$Palm__EvtEnqueueKey:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.w	18(a6),-(a7)
	trap	#15
	dc.w	sysTrapEvtEnqueueKey
	addq.l	#6,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__EvtFlushKeyQueue:
	link	a6,#0
	trap	#15
	dc.w	sysTrapEvtFlushKeyQueue
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__EvtFlushNextPenStroke:
	link	a6,#0
	trap	#15
	dc.w	sysTrapEvtFlushNextPenStroke
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__EvtFlushPenQueue:
	link	a6,#0
	trap	#15
	dc.w	sysTrapEvtFlushPenQueue
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__EvtGetEvent:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapEvtGetEvent
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__EvtGetPen:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapEvtGetPen
	lea	12(a7),a7
	unlk	a6
	rts

palmos$Palm__EvtKeyQueueEmpty:
	link	a6,#0
	trap	#15
	dc.w	sysTrapEvtKeyQueueEmpty
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__EvtKeyQueueSize:
	link	a6,#0
	trap	#15
	dc.w	sysTrapEvtKeyQueueSize
	unlk	a6
	rts

palmos$Palm__EvtPenQueueSize:
	link	a6,#0
	trap	#15
	dc.w	sysTrapEvtPenQueueSize
	unlk	a6
	rts

palmos$Palm__EvtProcessSoftKeyStroke:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapEvtProcessSoftKeyStroke
	addq.l	#8,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__EvtResetAutoOffTimer:
	link	a6,#0
	trap	#15
	dc.w	sysTrapEvtResetAutoOffTimer
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__EvtWakeup:
	link	a6,#0
	trap	#15
	dc.w	sysTrapEvtWakeup
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__FindStrInStr:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	move.l	16(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	trap	#15
	dc.w	sysTrapFindStrInStr
	lea	12(a7),a7
	unlk	a6
	rts

palmos$Palm__FldCalcFieldHeight:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	trap	#15
	dc.w	sysTrapFldCalcFieldHeight
	addq.l	#6,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__FldCompactText:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldCompactText
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__FldCopy:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldCopy
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__FldCut:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldCut
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__FldDelete:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldDelete
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__FldDirty:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldDirty
	addq.l	#4,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__FldDrawField:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldDrawField
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__FldEraseField:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldEraseField
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__FldFreeMemory:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldFreeMemory
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__FldGetAttributes:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldGetAttributes
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__FldGetBounds:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldGetBounds
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__FldGetFont:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldGetFont
	addq.l	#4,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__FldGetInsPtPosition:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldGetInsPtPosition
	addq.l	#4,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__FldGetMaxChars:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldGetMaxChars
	addq.l	#4,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__FldGetScrollPosition:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldGetScrollPosition
	addq.l	#4,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__FldGetSelection:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldGetSelection
	lea	12(a7),a7
	unlk	a6
	rts

palmos$Palm__FldGetTextAllocatedSize:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldGetTextAllocatedSize
	addq.l	#4,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__FldGetTextHandle:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldGetTextHandle
	addq.l	#4,a7
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__FldGetTextHeight:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldGetTextHeight
	addq.l	#4,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__FldGetTextLength:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldGetTextLength
	addq.l	#4,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__FldGetTextPtr:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldGetTextPtr
	addq.l	#4,a7
	jsr	CharPtr_to_String(pc)
	unlk	a6
	rts

palmos$Palm__FldGetVisibleLines:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldGetVisibleLines
	addq.l	#4,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__FldGrabFocus:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldGrabFocus
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__FldHandleEvent:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldHandleEvent
	addq.l	#8,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__FldInsert:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldInsert
	lea	10(a7),a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__FldMakeFullyVisible:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldMakeFullyVisible
	addq.l	#4,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__FldPaste:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldPaste
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__FldRecalculateField:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldRecalculateField
	addq.l	#6,a7
	unlk	a6
	rts

palmos$Palm__FldReleaseFocus:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldReleaseFocus
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__FldScrollable:
	link	a6,#0
	move.b	11(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldScrollable
	addq.l	#6,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__FldScrollField:
	link	a6,#0
	move.b	11(a6),-(a7)
	move.w	14(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldScrollField
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__FldSendChangeNotification:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldSendChangeNotification
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__FldSendHeightChangeNotification:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldSendHeightChangeNotification
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__FldSetAttributes:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldSetAttributes
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__FldSetBounds:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldSetBounds
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__FldSetDirty:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldSetDirty
	addq.l	#6,a7
	unlk	a6
	rts

palmos$Palm__FldSetFont:
	link	a6,#0
	move.b	11(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldSetFont
	addq.l	#6,a7
	unlk	a6
	rts

palmos$Palm__FldSetInsPtPosition:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldSetInsPtPosition
	addq.l	#6,a7
	unlk	a6
	rts

palmos$Palm__FldSetMaxChars:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldSetMaxChars
	addq.l	#6,a7
	unlk	a6
	rts

palmos$Palm__FldSetScrollPosition:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldSetScrollPosition
	addq.l	#6,a7
	unlk	a6
	rts

palmos$Palm__FldSetSelection:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldSetSelection
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__FldSetText:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.l	16(a6),-(a7)
	move.l	20(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldSetText
	lea	12(a7),a7
	unlk	a6
	rts

palmos$Palm__FldSetTextAllocatedSize:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldSetTextAllocatedSize
	addq.l	#6,a7
	unlk	a6
	rts

palmos$Palm__FldSetTextHandle:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldSetTextHandle
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__FldSetTextPtr:
	link	a6,#0
	move.l	8(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldSetTextPtr
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__FldSetUsable:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldSetUsable
	addq.l	#6,a7
	unlk	a6
	rts

palmos$Palm__FldUndo:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapFldUndo
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__FldWordWrap:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	trap	#15
	dc.w	sysTrapFldWordWrap
	addq.l	#6,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__FntAverageCharWidth:
	link	a6,#0
	trap	#15
	dc.w	sysTrapFntAverageCharWidth
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__FntBaseLine:
	link	a6,#0
	trap	#15
	dc.w	sysTrapFntBaseLine
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__FntCharHeight:
	link	a6,#0
	trap	#15
	dc.w	sysTrapFntCharHeight
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__FntCharsInWidth:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	move.l	20(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	trap	#15
	dc.w	sysTrapFntCharsInWidth
	lea	16(a7),a7
	unlk	a6
	rts

palmos$Palm__FntCharsWidth:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	trap	#15
	dc.w	sysTrapFntCharsWidth
	addq.l	#6,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__FntCharWidth:
	link	a6,#0
	move.w	10(a6),-(a7)
	trap	#15
	dc.w	sysTrapFntCharWidth
	addq.l	#2,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__FntDescenderHeight:
	link	a6,#0
	trap	#15
	dc.w	sysTrapFntDescenderHeight
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__FntGetFont:
	link	a6,#0
	trap	#15
	dc.w	sysTrapFntGetFont
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__FntGetFontPtr:
	link	a6,#0
	trap	#15
	dc.w	sysTrapFntGetFontPtr
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__FntLineHeight:
	link	a6,#0
	trap	#15
	dc.w	sysTrapFntLineHeight
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__FntLineWidth:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	trap	#15
	dc.w	sysTrapFntLineWidth
	addq.l	#6,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__FntSetFont:
	link	a6,#0
	move.b	11(a6),-(a7)
	trap	#15
	dc.w	sysTrapFntSetFont
	addq.l	#2,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__FrmAlert:
	link	a6,#0
	move.w	10(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmAlert
	addq.l	#2,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__FrmCloseAllForms:
	link	a6,#0
	trap	#15
	dc.w	sysTrapFrmCloseAllForms
	unlk	a6
	rts

palmos$Palm__FrmCopyLabel:
	link	a6,#0
	move.l	8(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	move.w	14(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmCopyLabel
	lea	10(a7),a7
	unlk	a6
	rts

palmos$Palm__FrmCopyTitle:
	link	a6,#0
	move.l	8(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmCopyTitle
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__FrmCustomAlert:
	link	a6,#0
	move.l	8(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	move.l	12(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	move.l	16(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	move.w	22(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmCustomAlert
	lea	14(a7),a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__FrmDeleteForm:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmDeleteForm
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__FrmDispatchEvent:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmDispatchEvent
	addq.l	#4,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__FrmDoDialog:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmDoDialog
	addq.l	#4,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__FrmDrawForm:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmDrawForm
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__FrmEraseForm:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmEraseForm
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__FrmGetActiveForm:
	link	a6,#0
	trap	#15
	dc.w	sysTrapFrmGetActiveForm
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__FrmGetActiveFormID:
	link	a6,#0
	trap	#15
	dc.w	sysTrapFrmGetActiveFormID
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__FrmGetControlGroupSelection:
	link	a6,#0
	move.b	11(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmGetControlGroupSelection
	addq.l	#6,a7
	and.l	#$ff,d0
	unlk	a6
	rts

palmos$Palm__FrmGetControlValue:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmGetControlValue
	addq.l	#6,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__FrmGetFirstForm:
	link	a6,#0
	trap	#15
	dc.w	sysTrapFrmGetFirstForm
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__FrmGetFocus:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmGetFocus
	addq.l	#4,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__FrmGetFormBounds:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmGetFormBounds
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__FrmGetFormId:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmGetFormId
	addq.l	#4,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__FrmGetFormPtr:
	link	a6,#0
	move.w	10(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmGetFormPtr
	addq.l	#2,a7
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__FrmGetGadgetData:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmGetGadgetData
	addq.l	#6,a7
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__FrmGetLabel:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmGetLabel
	addq.l	#6,a7
	jsr	CharPtr_to_String(pc)
	unlk	a6
	rts

palmos$Palm__FrmGetNumberOfObjects:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmGetNumberOfObjects
	addq.l	#4,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__FrmGetObjectBounds:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.w	14(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmGetObjectBounds
	lea	10(a7),a7
	unlk	a6
	rts

palmos$Palm__FrmGetObjectId:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmGetObjectId
	addq.l	#6,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__FrmGetObjectIndex:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmGetObjectIndex
	addq.l	#6,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__FrmGetObjectPosition:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	move.w	18(a6),-(a7)
	move.l	20(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmGetObjectPosition
	lea	14(a7),a7
	unlk	a6
	rts

palmos$Palm__FrmGetObjectPtr:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmGetObjectPtr
	addq.l	#6,a7
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__FrmGetObjectType:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmGetObjectType
	addq.l	#6,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__FrmGetTitle:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmGetTitle
	addq.l	#4,a7
	jsr	CharPtr_to_String(pc)
	unlk	a6
	rts

palmos$Palm__FrmGetUserModifiedState:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmGetUserModifiedState
	addq.l	#4,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__FrmGetWindowHandle:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmGetWindowHandle
	addq.l	#4,a7
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__FrmGotoForm:
	link	a6,#0
	move.w	10(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmGotoForm
	addq.l	#2,a7
	unlk	a6
	rts

palmos$Palm__FrmHandleEvent:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmHandleEvent
	addq.l	#8,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__FrmHelp:
	link	a6,#0
	move.w	10(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmHelp
	addq.l	#2,a7
	unlk	a6
	rts

palmos$Palm__FrmHideObject:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmHideObject
	addq.l	#6,a7
	unlk	a6
	rts

palmos$Palm__FrmInitForm:
	link	a6,#0
	move.w	10(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmInitForm
	addq.l	#2,a7
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__FrmPopupForm:
	link	a6,#0
	move.w	10(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmPopupForm
	addq.l	#2,a7
	unlk	a6
	rts

palmos$Palm__FrmReturnToForm:
	link	a6,#0
	move.w	10(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmReturnToForm
	addq.l	#2,a7
	unlk	a6
	rts

palmos$Palm__FrmSaveAllForms:
	link	a6,#0
	trap	#15
	dc.w	sysTrapFrmSaveAllForms
	unlk	a6
	rts

palmos$Palm__FrmSetActiveForm:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmSetActiveForm
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__FrmSetCategoryLabel:
	link	a6,#0
	move.l	8(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	move.w	14(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmSetCategoryLabel
	lea	10(a7),a7
	unlk	a6
	rts

palmos$Palm__FrmSetControlGroupSelection:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.b	15(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmSetControlGroupSelection
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__FrmSetControlValue:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmSetControlValue
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__FrmSetFocus:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmSetFocus
	addq.l	#6,a7
	unlk	a6
	rts

palmos$Palm__FrmSetGadgetData:
	link	a6,#0
	move.l	8(a6),-(a7)
	jsr	makevoidptr(pc)
	move.w	14(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmSetGadgetData
	lea	10(a7),a7
	unlk	a6
	rts

palmos$Palm__FrmSetNotUserModified:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmSetNotUserModified
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__FrmSetObjectPositon:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.w	18(a6),-(a7)
	move.l	20(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmSetObjectPositon
	lea	10(a7),a7
	unlk	a6
	rts

palmos$Palm__FrmSetTitle:
	link	a6,#0
	move.l	8(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmSetTitle
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__FrmShowObject:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmShowObject
	addq.l	#6,a7
	unlk	a6
	rts

palmos$Palm__FrmUpdateScrollers:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.w	18(a6),-(a7)
	move.w	22(a6),-(a7)
	move.l	24(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmUpdateScrollers
	lea	12(a7),a7
	unlk	a6
	rts

palmos$Palm__FrmUpdateForm:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmUpdateForm
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__FrmVisible:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapFrmVisible
	addq.l	#4,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__FtrGet:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.w	14(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapFtrGet
	lea	10(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__FtrGetByIndex:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	move.w	22(a6),-(a7)
	move.w	26(a6),-(a7)
	trap	#15
	dc.w	sysTrapFtrGetByIndex
	lea	16(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__FtrSet:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.w	14(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapFtrSet
	lea	10(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__FtrUnregister:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapFtrUnregister
	addq.l	#6,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__GrfAddMacro:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	jsr	makevoidptr(pc)
	move.l	16(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	trap	#15
	dc.w	sysTrapGrfAddMacro
	lea	10(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__GrfAddPoint:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapGrfAddPoint
	addq.l	#4,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__GrfCleanState:
	link	a6,#0
	trap	#15
	dc.w	sysTrapGrfCleanState
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__GrfDeleteMacro:
	link	a6,#0
	move.w	10(a6),-(a7)
	trap	#15
	dc.w	sysTrapGrfDeleteMacro
	addq.l	#2,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__GrfFindBranch:
	link	a6,#0
	move.w	10(a6),-(a7)
	trap	#15
	dc.w	sysTrapGrfFindBranch
	addq.l	#2,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__GrfFilterPoints:
	link	a6,#0
	trap	#15
	dc.w	sysTrapGrfFilterPoints
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__GrfFlushPoints:
	link	a6,#0
	trap	#15
	dc.w	sysTrapGrfFlushPoints
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__GrfGetAndExpandMacro:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	jsr	makevoidptr(pc)
	move.l	16(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	trap	#15
	dc.w	sysTrapGrfGetAndExpandMacro
	lea	12(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__GrfGetGlyphMapping:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	jsr	makevoidptr(pc)
	move.l	20(a6),-(a7)
	move.w	26(a6),-(a7)
	trap	#15
	dc.w	sysTrapGrfGetGlyphMapping
	lea	18(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__GrfGetMacro:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	jsr	makevoidptr(pc)
	move.l	16(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	trap	#15
	dc.w	sysTrapGrfGetMacro
	lea	12(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__GrfGetMacroName:
	link	a6,#0
	move.l	8(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	move.w	14(a6),-(a7)
	trap	#15
	dc.w	sysTrapGrfGetMacroName
	addq.l	#6,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__GrfGetNumPoints:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapGrfGetNumPoints
	addq.l	#4,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__GrfGetPoint:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.w	14(a6),-(a7)
	trap	#15
	dc.w	sysTrapGrfGetPoint
	addq.l	#6,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__GrfGetState:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	move.l	20(a6),-(a7)
	trap	#15
	dc.w	sysTrapGrfGetState
	lea	16(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__GrfInitState:
	link	a6,#0
	trap	#15
	dc.w	sysTrapGrfInitState
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__GrfProcessStroke:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapGrfProcessStroke
	lea	10(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__GrfSetState:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.w	18(a6),-(a7)
	trap	#15
	dc.w	sysTrapGrfSetState
	addq.l	#6,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__GsiEnable:
	link	a6,#0
	move.w	10(a6),-(a7)
	trap	#15
	dc.w	sysTrapGsiEnable
	addq.l	#2,a7
	unlk	a6
	rts

palmos$Palm__GsiEnabled:
	link	a6,#0
	trap	#15
	dc.w	sysTrapGsiEnabled
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__GsiInitialize:
	link	a6,#0
	trap	#15
	dc.w	sysTrapGsiInitialize
	unlk	a6
	rts

palmos$Palm__GsiSetLocation:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	trap	#15
	dc.w	sysTrapGsiSetLocation
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__GsiSetShiftState:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	trap	#15
	dc.w	sysTrapGsiSetShiftState
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__InsPtEnable:
	link	a6,#0
	move.w	10(a6),-(a7)
	trap	#15
	dc.w	sysTrapInsPtEnable
	addq.l	#2,a7
	unlk	a6
	rts

palmos$Palm__InsPtEnabled:
	link	a6,#0
	trap	#15
	dc.w	sysTrapInsPtEnabled
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__InsPtGetHeight:
	link	a6,#0
	trap	#15
	dc.w	sysTrapInsPtGetHeight
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__InsPtGetLocation:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapInsPtGetLocation
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__InsPtSetHeight:
	link	a6,#0
	move.w	10(a6),-(a7)
	trap	#15
	dc.w	sysTrapInsPtSetHeight
	addq.l	#2,a7
	unlk	a6
	rts

palmos$Palm__InsPtSetLocation:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	trap	#15
	dc.w	sysTrapInsPtSetLocation
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__KeyCurrentState:
	link	a6,#0
	trap	#15
	dc.w	sysTrapKeyCurrentState
	unlk	a6
	rts

palmos$Palm__KeyRates:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	move.l	20(a6),-(a7)
	move.w	26(a6),-(a7)
	trap	#15
	dc.w	sysTrapKeyRates
	lea	18(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__LstDrawList:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapLstDrawList
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__LstEraseList:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapLstEraseList
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__LstGetNumberOfItems:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapLstGetNumberOfItems
	addq.l	#4,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__LstGetSelection:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapLstGetSelection
	addq.l	#4,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__LstGetSelectionText:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapLstGetSelectionText
	addq.l	#6,a7
	jsr	CharPtr_to_String(pc)
	unlk	a6
	rts

palmos$Palm__LstHandleEvent:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapLstHandleEvent
	addq.l	#8,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__LstMakeItemVisible:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapLstMakeItemVisible
	addq.l	#6,a7
	unlk	a6
	rts

palmos$Palm__LstPopupList:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapLstPopupList
	addq.l	#4,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__LstSetHeight:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapLstSetHeight
	addq.l	#6,a7
	unlk	a6
	rts

palmos$Palm__LstSetPosition:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapLstSetPosition
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__LstSetSelection:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapLstSetSelection
	addq.l	#6,a7
	unlk	a6
	rts

palmos$Palm__LstSetTopItem:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapLstSetTopItem
	addq.l	#6,a7
	unlk	a6
	rts

palmos$Palm__MemCardInfo:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	move.l	20(a6),-(a7)
	move.l	24(a6),-(a7)
	move.l	28(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	move.l	32(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	move.w	38(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemCardInfo
	lea	30(a7),a7
	move.l	d0,d2
	move.l	28(a6),a2
	move.l	(a2),a0
	systrap	StrLen(Array.data(a0).l)
	move.w	d0,4+2(a2)
	move.l	d2,d0
	move.l	d0,d2
	move.l	32(a6),a2
	move.l	(a2),a0
	systrap	StrLen(Array.data(a0).l)
	move.w	d0,4+2(a2)
	move.l	d2,d0
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__MemChunkFree:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemChunkFree
	addq.l	#4,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__MemDebugMode:
	link	a6,#0
	trap	#15
	dc.w	sysTrapMemDebugMode
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__MemHandleDataStorage:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemHandleDataStorage
	addq.l	#4,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__MemHandleCardNo:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemHandleCardNo
	addq.l	#4,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__MemHandleFree:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemHandleFree
	addq.l	#4,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__MemHandleHeapID:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemHandleHeapID
	addq.l	#4,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__MemHandleLock:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemHandleLock
	addq.l	#4,a7
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__MemHandleNew:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemHandleNew
	addq.l	#4,a7
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__MemHandleResize:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemHandleResize
	addq.l	#8,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__MemHandleSize:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemHandleSize
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__MemHandleToLocalID:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemHandleToLocalID
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__MemHandleUnlock:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemHandleUnlock
	addq.l	#4,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__MemHeapCheck:
	link	a6,#0
	move.w	10(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemHeapCheck
	addq.l	#2,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__MemHeapCompact:
	link	a6,#0
	move.w	10(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemHeapCompact
	addq.l	#2,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__MemHeapDynamic:
	link	a6,#0
	move.w	10(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemHeapDynamic
	addq.l	#2,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__MemHeapFlags:
	link	a6,#0
	move.w	10(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemHeapFlags
	addq.l	#2,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__MemHeapFreeBytes:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	move.w	18(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemHeapFreeBytes
	lea	10(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__MemHeapID:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemHeapID
	addq.l	#4,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__MemHeapScramble:
	link	a6,#0
	move.w	10(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemHeapScramble
	addq.l	#2,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__MemHeapSize:
	link	a6,#0
	move.w	10(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemHeapSize
	addq.l	#2,a7
	unlk	a6
	rts

palmos$Palm__MemLocalIDKind:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemLocalIDKind
	addq.l	#4,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__MemLocalIDToGlobal:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemLocalIDToGlobal
	addq.l	#6,a7
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__MemLocalIDToLockedPtr:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemLocalIDToLockedPtr
	addq.l	#6,a7
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__MemLocalIDToPtr:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemLocalIDToPtr
	addq.l	#6,a7
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__MemMove:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemMove
	lea	12(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__MemNumCards:
	link	a6,#0
	trap	#15
	dc.w	sysTrapMemNumCards
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__MemNumHeaps:
	link	a6,#0
	move.w	10(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemNumHeaps
	addq.l	#2,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__MemNumRAMHeaps:
	link	a6,#0
	move.w	10(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemNumRAMHeaps
	addq.l	#2,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__MemPtrCardNo:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemPtrCardNo
	addq.l	#4,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__MemPtrDataStorage:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemPtrDataStorage
	addq.l	#4,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__MemPtrFree:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemPtrFree
	addq.l	#4,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__MemPtrHeapID:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemPtrHeapID
	addq.l	#4,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__MemPtrToLocalID:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemPtrToLocalID
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__MemPtrNew:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemPtrNew
	addq.l	#4,a7
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__MemPtrRecoverHandle:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemPtrRecoverHandle
	addq.l	#4,a7
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__MemPtrResize:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemPtrResize
	addq.l	#8,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__MemSet:
	link	a6,#0
	move.b	11(a6),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemSet
	lea	10(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__MemSetDebugMode:
	link	a6,#0
	move.w	10(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemSetDebugMode
	addq.l	#2,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__MemPtrSize:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemPtrSize
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__MemPtrUnlock:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemPtrUnlock
	addq.l	#4,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__MemStoreInfo:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	move.l	20(a6),-(a7)
	move.l	24(a6),-(a7)
	move.l	28(a6),-(a7)
	move.l	32(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	move.l	36(a6),-(a7)
	move.l	40(a6),-(a7)
	move.w	46(a6),-(a7)
	move.w	50(a6),-(a7)
	trap	#15
	dc.w	sysTrapMemStoreInfo
	lea	40(a7),a7
	move.l	d0,d2
	move.l	32(a6),a2
	move.l	(a2),a0
	systrap	StrLen(Array.data(a0).l)
	move.w	d0,4+2(a2)
	move.l	d2,d0
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__MenuDispose:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapMenuDispose
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__MenuDrawMenu:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapMenuDrawMenu
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__MenuEraseStatus:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapMenuEraseStatus
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__MenuGetActiveMenu:
	link	a6,#0
	trap	#15
	dc.w	sysTrapMenuGetActiveMenu
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__MenuHandleEvent:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapMenuHandleEvent
	lea	12(a7),a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__MenuInit:
	link	a6,#0
	move.w	10(a6),-(a7)
	trap	#15
	dc.w	sysTrapMenuInit
	addq.l	#2,a7
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__MenuSetActiveMenu:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapMenuSetActiveMenu
	addq.l	#4,a7
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__AbtShowAbout:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapAbtShowAbout
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__Crc16CalcBlock:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.l	16(a6),-(a7)
	jsr	makevoidptr(pc)
	trap	#15
	dc.w	sysTrapCrc16CalcBlock
	addq.l	#8,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__DayHandleEvent:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapDayHandleEvent
	addq.l	#8,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__PenCalibrate:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	move.l	20(a6),-(a7)
	trap	#15
	dc.w	sysTrapPenCalibrate
	lea	16(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__PenResetCalibration:
	link	a6,#0
	trap	#15
	dc.w	sysTrapPenResetCalibration
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__PrefGetAppPreferences:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	jsr	makevoidptr(pc)
	move.w	18(a6),-(a7)
	move.l	20(a6),-(a7)
	trap	#15
	dc.w	sysTrapPrefGetAppPreferences
	lea	12(a7),a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__PrefGetPreferences:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapPrefGetPreferences
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__PrefOpenPreferenceDB:
	link	a6,#0
	trap	#15
	dc.w	sysTrapPrefOpenPreferenceDB
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__PrefSetAppPreferences:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	jsr	makevoidptr(pc)
	move.w	18(a6),-(a7)
	move.l	20(a6),-(a7)
	trap	#15
	dc.w	sysTrapPrefSetAppPreferences
	lea	12(a7),a7
	unlk	a6
	rts

palmos$Palm__PrefSetPreferences:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapPrefSetPreferences
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__SerClearErr:
	link	a6,#0
	move.w	10(a6),-(a7)
	trap	#15
	dc.w	sysLibTrapSerClearErr
	addq.l	#2,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__SerClose:
	link	a6,#0
	move.w	10(a6),-(a7)
	trap	#15
	dc.w	sysLibTrapSerClose
	addq.l	#2,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__SerGetSettings:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.w	14(a6),-(a7)
	trap	#15
	dc.w	sysLibTrapSerGetSettings
	addq.l	#6,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__SerGetStatus:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	move.w	18(a6),-(a7)
	trap	#15
	dc.w	sysLibTrapSerGetStatus
	lea	10(a7),a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__SerOpen:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.w	14(a6),-(a7)
	move.w	18(a6),-(a7)
	trap	#15
	dc.w	sysLibTrapSerOpen
	addq.l	#8,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__SerReceive:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	jsr	makevoidptr(pc)
	move.w	22(a6),-(a7)
	trap	#15
	dc.w	sysLibTrapSerReceive
	lea	14(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__SerReceiveCheck:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.w	14(a6),-(a7)
	trap	#15
	dc.w	sysLibTrapSerReceiveCheck
	addq.l	#6,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__SerReceiveFlush:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.w	14(a6),-(a7)
	trap	#15
	dc.w	sysLibTrapSerReceiveFlush
	addq.l	#6,a7
	unlk	a6
	rts

palmos$Palm__SerReceiveWait:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	move.w	18(a6),-(a7)
	trap	#15
	dc.w	sysLibTrapSerReceiveWait
	lea	10(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__SerSend:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	jsr	makevoidptr(pc)
	move.w	18(a6),-(a7)
	trap	#15
	dc.w	sysLibTrapSerSend
	lea	10(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__SerSend:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	move.w	18(a6),-(a7)
	trap	#15
	dc.w	sysLibTrapSerSend
	lea	10(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__SerSendWait:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.w	14(a6),-(a7)
	trap	#15
	dc.w	sysLibTrapSerSendWait
	addq.l	#6,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__SerSetReceiveBuffer:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	jsr	makevoidptr(pc)
	move.w	18(a6),-(a7)
	trap	#15
	dc.w	sysLibTrapSerSetReceiveBuffer
	addq.l	#8,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__SerSetSettings:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.w	14(a6),-(a7)
	trap	#15
	dc.w	sysLibTrapSerSetSettings
	addq.l	#6,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__SlkClose:
	link	a6,#0
	trap	#15
	dc.w	sysTrapSlkClose
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__SlkCloseSocket:
	link	a6,#0
	move.w	10(a6),-(a7)
	trap	#15
	dc.w	sysTrapSlkCloseSocket
	addq.l	#2,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__SlkFlushSocket:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.w	14(a6),-(a7)
	trap	#15
	dc.w	sysTrapSlkFlushSocket
	addq.l	#6,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__SlkOpen:
	link	a6,#0
	trap	#15
	dc.w	sysTrapSlkOpen
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__SlkOpenSocket:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	move.w	18(a6),-(a7)
	trap	#15
	dc.w	sysTrapSlkOpenSocket
	addq.l	#8,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__SlkReceivePacket:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.w	14(a6),-(a7)
	move.l	16(a6),-(a7)
	jsr	makevoidptr(pc)
	move.l	20(a6),-(a7)
	move.w	26(a6),-(a7)
	move.w	30(a6),-(a7)
	trap	#15
	dc.w	sysTrapSlkReceivePacket
	lea	18(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__SlkSocketRefNum:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.w	14(a6),-(a7)
	trap	#15
	dc.w	sysTrapSlkSocketRefNum
	addq.l	#6,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__SlkSocketSetTimeout:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.w	14(a6),-(a7)
	trap	#15
	dc.w	sysTrapSlkSocketSetTimeout
	addq.l	#6,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__SndDoCmd:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	jsr	makevoidptr(pc)
	trap	#15
	dc.w	sysTrapSndDoCmd
	lea	10(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__SndGetDefaultVolume:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapSndGetDefaultVolume
	lea	12(a7),a7
	unlk	a6
	rts

palmos$Palm__SndPlaySystemSound:
	link	a6,#0
	move.b	11(a6),-(a7)
	trap	#15
	dc.w	sysTrapSndPlaySystemSound
	addq.l	#2,a7
	unlk	a6
	rts

palmos$Palm__SndSetDefaultVolume:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapSndSetDefaultVolume
	lea	12(a7),a7
	unlk	a6
	rts

palmos$Palm__StrAToI:
	link	a6,#0
	move.l	8(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	trap	#15
	dc.w	sysTrapStrAToI
	addq.l	#4,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__StrCaselessCompare:
	link	a6,#0
	move.l	8(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	move.l	12(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	trap	#15
	dc.w	sysTrapStrCaselessCompare
	addq.l	#8,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__StrCompare:
	link	a6,#0
	move.l	8(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	move.l	12(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	trap	#15
	dc.w	sysTrapStrCompare
	addq.l	#8,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__StrLen:
	link	a6,#0
	move.l	8(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	trap	#15
	dc.w	sysTrapStrLen
	addq.l	#4,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__SysAppLaunch:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	jsr	makevoidptr(pc)
	move.w	18(a6),-(a7)
	move.w	22(a6),-(a7)
	move.l	24(a6),-(a7)
	move.w	30(a6),-(a7)
	trap	#15
	dc.w	sysTrapSysAppLaunch
	lea	18(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__SysBatteryInfo:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	move.l	20(a6),-(a7)
	move.l	24(a6),-(a7)
	move.w	30(a6),-(a7)
	trap	#15
	dc.w	sysTrapSysBatteryInfo
	lea	22(a7),a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__SysBroadcastActionCode:
	link	a6,#0
	move.l	8(a6),-(a7)
	jsr	makevoidptr(pc)
	move.w	14(a6),-(a7)
	trap	#15
	dc.w	sysTrapSysBroadcastActionCode
	addq.l	#6,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__SysCopyStringResource:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	trap	#15
	dc.w	sysTrapSysCopyStringResource
	addq.l	#6,a7
	unlk	a6
	rts

palmos$Palm__SysCurAppDatabase:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapSysCurAppDatabase
	addq.l	#8,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__SysFormPointerArrayToStrings:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	trap	#15
	dc.w	sysTrapSysFormPointerArrayToStrings
	addq.l	#6,a7
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__SysHandleEvent:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapSysHandleEvent
	addq.l	#4,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__SysKeyboardDialog:
	link	a6,#0
	trap	#15
	dc.w	sysTrapSysKeyboardDialog
	unlk	a6
	rts

palmos$Palm__SysLibFind:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	trap	#15
	dc.w	sysTrapSysLibFind
	addq.l	#8,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__SysRandom:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapSysRandom
	addq.l	#4,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__SysReset:
	link	a6,#0
	trap	#15
	dc.w	sysTrapSysReset
	unlk	a6
	rts

palmos$Palm__SysSetAutoOffTime:
	link	a6,#0
	move.w	10(a6),-(a7)
	trap	#15
	dc.w	sysTrapSysSetAutoOffTime
	addq.l	#2,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__SysTaskDelay:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapSysTaskDelay
	addq.l	#4,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__SysUIAppSwitch:
	link	a6,#0
	move.l	8(a6),-(a7)
	jsr	makevoidptr(pc)
	move.w	14(a6),-(a7)
	move.l	16(a6),-(a7)
	move.w	22(a6),-(a7)
	trap	#15
	dc.w	sysTrapSysUIAppSwitch
	lea	12(a7),a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__TblDrawTable:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblDrawTable
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__TblEditing:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblEditing
	addq.l	#4,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__TblEraseTable:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblEraseTable
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__TblFindRowData:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblFindRowData
	lea	12(a7),a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__TblFindRowID:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.w	14(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblFindRowID
	lea	10(a7),a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__TblGetBounds:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblGetBounds
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__TblGetColumnSpacing:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblGetColumnSpacing
	addq.l	#6,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__TblGetColumnWidth:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblGetColumnWidth
	addq.l	#6,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__TblGetCurrentField:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblGetCurrentField
	addq.l	#4,a7
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__TblGetItemBounds:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.w	14(a6),-(a7)
	move.w	18(a6),-(a7)
	move.l	20(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblGetItemBounds
	lea	12(a7),a7
	unlk	a6
	rts

palmos$Palm__TblGetItemInt:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblGetItemInt
	addq.l	#8,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__TblGetLastUsableRow:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblGetLastUsableRow
	addq.l	#4,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__TblGetNumberOfRows:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblGetNumberOfRows
	addq.l	#4,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__TblGetRowData:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblGetRowData
	addq.l	#6,a7
	unlk	a6
	rts

palmos$Palm__TblGetRowHeight:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblGetRowHeight
	addq.l	#6,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__TblGetRowID:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblGetRowID
	addq.l	#6,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__TblGetSelection:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblGetSelection
	lea	12(a7),a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__TblGrabFocus:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblGrabFocus
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__TblHandleEvent:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblHandleEvent
	addq.l	#8,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__TblInsertRow:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblInsertRow
	addq.l	#6,a7
	unlk	a6
	rts

palmos$Palm__TblMarkRowInvalid:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblMarkRowInvalid
	addq.l	#6,a7
	unlk	a6
	rts

palmos$Palm__TblMarkTableInvalid:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblMarkTableInvalid
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__TblRedrawTable:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblRedrawTable
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__TblReleaseFocus:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblReleaseFocus
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__TblRemoveRow:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblRemoveRow
	addq.l	#6,a7
	unlk	a6
	rts

palmos$Palm__TblRowInvalid:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblRowInvalid
	addq.l	#6,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__TblRowSelectable:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblRowSelectable
	addq.l	#6,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__TblRowUsable:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblRowUsable
	addq.l	#6,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__TblSelectItem:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblSelectItem
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__TblSetColumnSpacing:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblSetColumnSpacing
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__TblSetColumnUsable:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblSetColumnUsable
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__TblSetColumnWidth:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblSetColumnWidth
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__TblSetItemInt:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.w	18(a6),-(a7)
	move.l	20(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblSetItemInt
	lea	10(a7),a7
	unlk	a6
	rts

palmos$Palm__TblSetItemPtr:
	link	a6,#0
	move.l	8(a6),-(a7)
	jsr	makevoidptr(pc)
	move.w	14(a6),-(a7)
	move.w	18(a6),-(a7)
	move.l	20(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblSetItemPtr
	lea	12(a7),a7
	unlk	a6
	rts

palmos$Palm__TblSetItemStyle:
	link	a6,#0
	move.b	11(a6),-(a7)
	move.w	14(a6),-(a7)
	move.w	18(a6),-(a7)
	move.l	20(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblSetItemStyle
	lea	10(a7),a7
	unlk	a6
	rts

palmos$Palm__TblSetRowData:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.w	14(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblSetRowData
	lea	10(a7),a7
	unlk	a6
	rts

palmos$Palm__TblSetRowHeight:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblSetRowHeight
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__TblSetRowID:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblSetRowID
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__TblSetRowSelectable:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblSetRowSelectable
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__TblSetRowUsable:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblSetRowUsable
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__TblUnhighlightSelection:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapTblUnhighlightSelection
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__DateAdjust:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapDateAdjust
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__DateDaysToDate:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapDateDaysToDate
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__DateSecondsToDate:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapDateSecondsToDate
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__DateToAscii:
	link	a6,#0
	move.l	8(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	move.b	15(a6),-(a7)
	move.w	18(a6),-(a7)
	move.b	23(a6),-(a7)
	move.b	27(a6),-(a7)
	trap	#15
	dc.w	sysTrapDateToAscii
	lea	12(a7),a7
	move.l	d0,d2
	move.l	8(a6),a2
	move.l	(a2),a0
	systrap	StrLen(Array.data(a0).l)
	move.w	d0,4+2(a2)
	move.l	d2,d0
	unlk	a6
	rts

palmos$Palm__DateToDays:
	link	a6,#0
	move.w	10(a6),-(a7)
	trap	#15
	dc.w	sysTrapDateToDays
	addq.l	#2,a7
	unlk	a6
	rts

palmos$Palm__DateToDOWDMFormat:
	link	a6,#0
	move.l	8(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	move.b	15(a6),-(a7)
	move.w	18(a6),-(a7)
	move.b	23(a6),-(a7)
	move.b	27(a6),-(a7)
	trap	#15
	dc.w	sysTrapDateToDOWDMFormat
	lea	12(a7),a7
	move.l	d0,d2
	move.l	8(a6),a2
	move.l	(a2),a0
	systrap	StrLen(Array.data(a0).l)
	move.w	d0,4+2(a2)
	move.l	d2,d0
	unlk	a6
	rts

palmos$Palm__DayOfMonth:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.w	18(a6),-(a7)
	trap	#15
	dc.w	sysTrapDayOfMonth
	addq.l	#6,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__DayOfWeek:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.w	18(a6),-(a7)
	trap	#15
	dc.w	sysTrapDayOfWeek
	addq.l	#6,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__DaysInMonth:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	trap	#15
	dc.w	sysTrapDaysInMonth
	addq.l	#4,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__SelectDay:
	link	a6,#0
	move.l	8(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	move.l	12(a6),-(a7)
	move.l	16(a6),-(a7)
	move.l	20(a6),-(a7)
	trap	#15
	dc.w	sysTrapSelectDay
	lea	16(a7),a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__TimAdjust:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapTimAdjust
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__TimDateTimeToSeconds:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapTimDateTimeToSeconds
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__TimGetSeconds:
	link	a6,#0
	trap	#15
	dc.w	sysTrapTimGetSeconds
	unlk	a6
	rts

palmos$Palm__TimGetTicks:
	link	a6,#0
	trap	#15
	dc.w	sysTrapTimGetTicks
	unlk	a6
	rts

palmos$Palm__TimSecondsToDateTime:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapTimSecondsToDateTime
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__TimSetSeconds:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapTimSetSeconds
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__TimeToAscii:
	link	a6,#0
	move.l	8(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	move.b	15(a6),-(a7)
	move.b	19(a6),-(a7)
	move.b	23(a6),-(a7)
	trap	#15
	dc.w	sysTrapTimeToAscii
	lea	10(a7),a7
	unlk	a6
	rts

palmos$Palm__WinAddWindow:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinAddWindow
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__WinClipRectangle:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinClipRectangle
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__WinCopyRectangle:
	link	a6,#0
	move.b	11(a6),-(a7)
	move.w	14(a6),-(a7)
	move.w	18(a6),-(a7)
	move.l	20(a6),-(a7)
	move.l	24(a6),-(a7)
	move.l	28(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinCopyRectangle
	lea	18(a7),a7
	unlk	a6
	rts

palmos$Palm__WinCreateWindow:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.w	14(a6),-(a7)
	move.w	18(a6),-(a7)
	move.w	22(a6),-(a7)
	move.l	24(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinCreateWindow
	lea	14(a7),a7
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__WinCreateOffscreenWindow:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.b	15(a6),-(a7)
	move.w	18(a6),-(a7)
	move.w	22(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinCreateOffscreenWindow
	lea	10(a7),a7
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__WinDeleteWindow:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinDeleteWindow
	addq.l	#6,a7
	unlk	a6
	rts

palmos$Palm__WinDisableWindow:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinDisableWindow
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__WinDisplayToWindowPt:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinDisplayToWindowPt
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__WinDrawBitmap:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinDrawBitmap
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__WinDrawChars:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.w	18(a6),-(a7)
	move.l	20(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	trap	#15
	dc.w	sysTrapWinDrawChars
	lea	10(a7),a7
	unlk	a6
	rts

palmos$Palm__WinDrawGrayLine:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.w	18(a6),-(a7)
	move.w	22(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinDrawGrayLine
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__WinDrawGrayRectangleFrame:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.w	14(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinDrawGrayRectangleFrame
	addq.l	#6,a7
	unlk	a6
	rts

palmos$Palm__WinDrawInvertedChars:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.w	18(a6),-(a7)
	move.l	20(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	trap	#15
	dc.w	sysTrapWinDrawInvertedChars
	lea	10(a7),a7
	unlk	a6
	rts

palmos$Palm__WinDrawLine:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.w	18(a6),-(a7)
	move.w	22(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinDrawLine
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__WinDrawRectangle:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinDrawRectangle
	addq.l	#6,a7
	unlk	a6
	rts

palmos$Palm__WinDrawRectangleFrame:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.w	14(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinDrawRectangleFrame
	addq.l	#6,a7
	unlk	a6
	rts

palmos$Palm__WinDrawWindowFrame:
	link	a6,#0
	trap	#15
	dc.w	sysTrapWinDrawWindowFrame
	unlk	a6
	rts

palmos$Palm__WinEnableWindow:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinEnableWindow
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__WinEraseChars:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.w	18(a6),-(a7)
	move.l	20(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	trap	#15
	dc.w	sysTrapWinEraseChars
	lea	10(a7),a7
	unlk	a6
	rts

palmos$Palm__WinEraseLine:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.w	18(a6),-(a7)
	move.w	22(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinEraseLine
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__WinEraseRectangle:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinEraseRectangle
	addq.l	#6,a7
	unlk	a6
	rts

palmos$Palm__WinEraseRectangleFrame:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.w	14(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinEraseRectangleFrame
	addq.l	#6,a7
	unlk	a6
	rts

palmos$Palm__WinEraseWindow:
	link	a6,#0
	trap	#15
	dc.w	sysTrapWinEraseWindow
	unlk	a6
	rts

palmos$Palm__WinFillLine:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.w	18(a6),-(a7)
	move.w	22(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinFillLine
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__WinFillRectangle:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinFillRectangle
	addq.l	#6,a7
	unlk	a6
	rts

palmos$Palm__WinGetActiveWindow:
	link	a6,#0
	trap	#15
	dc.w	sysTrapWinGetActiveWindow
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__WinGetClip:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinGetClip
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__WinGetDisplayExtent:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinGetDisplayExtent
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__WinGetDisplayWindow:
	link	a6,#0
	trap	#15
	dc.w	sysTrapWinGetDisplayWindow
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__WinGetDrawWindow:
	link	a6,#0
	trap	#15
	dc.w	sysTrapWinGetDrawWindow
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__WinGetFirstWindow:
	link	a6,#0
	trap	#15
	dc.w	sysTrapWinGetFirstWindow
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__WinGetFramesRectangle:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	move.w	18(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinGetFramesRectangle
	lea	10(a7),a7
	unlk	a6
	rts

palmos$Palm__WinGetWindowBounds:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinGetWindowBounds
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__WinGetWindowExtent:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinGetWindowExtent
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__WinGetWindowFrameRect:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinGetWindowFrameRect
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__WinInitializeWindow:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinInitializeWindow
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__WinInvertChars:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.w	18(a6),-(a7)
	move.l	20(a6),a0
	move.l	(a0),a0
	move.l	Array.data(a0),-(a7)
	trap	#15
	dc.w	sysTrapWinInvertChars
	lea	10(a7),a7
	unlk	a6
	rts

palmos$Palm__WinInvertLine:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.w	18(a6),-(a7)
	move.w	22(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinInvertLine
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__WinInvertRectangle:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinInvertRectangle
	addq.l	#6,a7
	unlk	a6
	rts

palmos$Palm__WinInvertRectangleFrame:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.w	14(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinInvertRectangleFrame
	addq.l	#6,a7
	unlk	a6
	rts

palmos$Palm__WinModal:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinModal
	addq.l	#4,a7
	and.l	#$ffff,d0
	unlk	a6
	rts

palmos$Palm__WinRemoveWindow:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinRemoveWindow
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__WinResetClip:
	link	a6,#0
	trap	#15
	dc.w	sysTrapWinResetClip
	unlk	a6
	rts

palmos$Palm__WinRestoreBits:
	link	a6,#0
	move.w	10(a6),-(a7)
	move.w	14(a6),-(a7)
	move.l	16(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinRestoreBits
	addq.l	#8,a7
	unlk	a6
	rts

palmos$Palm__WinSaveBits:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinSaveBits
	addq.l	#8,a7
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__WinScrollRectangle:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.w	14(a6),-(a7)
	move.b	19(a6),-(a7)
	move.l	20(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinScrollRectangle
	lea	12(a7),a7
	unlk	a6
	rts

palmos$Palm__WinSetActiveWindow:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinSetActiveWindow
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__WinSetClip:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinSetClip
	addq.l	#4,a7
	unlk	a6
	rts

palmos$Palm__WinSetDrawWindow:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinSetDrawWindow
	addq.l	#4,a7
	move.l	a0,d0
	unlk	a6
	rts

palmos$Palm__WinSetUnderlineMode:
	link	a6,#0
	move.l	8(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinSetUnderlineMode
	addq.l	#4,a7
	and.l	#$ffff,d0
	ext.l	d0
	unlk	a6
	rts

palmos$Palm__WinWindowToDisplayPt:
	link	a6,#0
	move.l	8(a6),-(a7)
	move.l	12(a6),-(a7)
	trap	#15
	dc.w	sysTrapWinWindowToDisplayPt
	addq.l	#8,a7
	unlk	a6
	rts

