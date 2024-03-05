--------------------------------------------------------
--  Constraints for Table CODE
--------------------------------------------------------

  ALTER TABLE "BDM"."CODE" MODIFY ("CATEGORY" NOT NULL ENABLE);
  ALTER TABLE "BDM"."CODE" MODIFY ("DIVS" NOT NULL ENABLE);
  ALTER TABLE "BDM"."CODE" MODIFY ("DIV_NAME" NOT NULL ENABLE);
  ALTER TABLE "BDM"."CODE" MODIFY ("SEQ" NOT NULL ENABLE);
  ALTER TABLE "BDM"."CODE" MODIFY ("USE_YN" NOT NULL ENABLE);
  ALTER TABLE "BDM"."CODE" ADD CONSTRAINT "PK_CODE" PRIMARY KEY ("CATEGORY", "DIVS")
  USING INDEX "BDM"."PK_CODE"  ENABLE;
