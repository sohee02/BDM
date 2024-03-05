--------------------------------------------------------
--  DDL for Table IMAGE
--------------------------------------------------------

  CREATE TABLE "BDM"."IMAGE" 
   (	"UUID" VARCHAR2(45 BYTE), 
	"SEQ" NUMBER(2,0), 
	"ORG_FILE_NAME" VARCHAR2(300 BYTE), 
	"SAVE_FILE_NAME" VARCHAR2(100 BYTE), 
	"EXTENSION" VARCHAR2(5 BYTE), 
	"FILE_SIZE" NUMBER(8,0), 
	"SAVE_PATH" VARCHAR2(150 BYTE), 
	"REG_DT" DATE DEFAULT SYSDATE, 
	"REG_ID" VARCHAR2(20 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;

   COMMENT ON COLUMN "BDM"."IMAGE"."UUID" IS 'UUID';
   COMMENT ON COLUMN "BDM"."IMAGE"."SEQ" IS '순번';
   COMMENT ON COLUMN "BDM"."IMAGE"."ORG_FILE_NAME" IS '원본파일명';
   COMMENT ON COLUMN "BDM"."IMAGE"."SAVE_FILE_NAME" IS '저장파일명';
   COMMENT ON COLUMN "BDM"."IMAGE"."EXTENSION" IS '확장자';
   COMMENT ON COLUMN "BDM"."IMAGE"."FILE_SIZE" IS '사이즈';
   COMMENT ON COLUMN "BDM"."IMAGE"."SAVE_PATH" IS '저장경로';
   COMMENT ON COLUMN "BDM"."IMAGE"."REG_DT" IS '등록일';
   COMMENT ON COLUMN "BDM"."IMAGE"."REG_ID" IS '등록자';
   COMMENT ON TABLE "BDM"."IMAGE"  IS '이미지';
