ALTER TABLE clw_budget ADD CONSTRAINT clw_budget_unique
UNIQUE(budget_status_cd, BUS_ENTITY_ID, COST_CENTER_ID, BUDGET_YEAR);
