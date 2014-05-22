-- STJ-4560 : Added for 'Exclude Order from Budget' functionality in New UI.

ALTER TABLE CLW_ORDER_GUIDE ADD (ORDER_BUDGET_TYPE_CD VARCHAR2(255 CHAR));

COMMIT;