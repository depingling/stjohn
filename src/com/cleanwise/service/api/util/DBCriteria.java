package com.cleanwise.service.api.util;

/**
 * This is a utility class for creating SQL criteria
 *
 * Copyright:   Copyright (c) 2001
 * Company:     CleanWise, Inc.
 * @author      Tim Besser
 *
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import java.text.SimpleDateFormat;

import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.framework.DataAccess;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.view.utils.Constants;

/**
 * <code>DBCriteria</code> is a utility class used to generate SQL criteria
 *
 * @author <a href="mailto:tbesser@TBESSER"></a>
 */
public class DBCriteria {
    // This is the maximum number of items in a SQL list (Oracle)
    public static final int MAX_SQL_ITEMS=1000;

    private List conditions = new ArrayList();
    private List orderConditions  = new ArrayList();
    private Set joinTables = new HashSet();

    private static final int NONE = 0;
    private static final int AND = 1;
    private static final int OR = 2;
    private static final int EQUAL = 3;
    private static final int EQUAL_IGNORE_CASE = 4;
    private static final int NOT_EQUAL = 5;
    private static final int NOT_EQUAL_IGNORE_CASE = 6;
    private static final int GREATER_THAN = 7;
    private static final int GREATER_OR_EQUAL = 8;
    private static final int LESS_THAN = 9;
    private static final int LESS_OR_EQUAL = 10;
    private static final int LIKE = 11;
    private static final int LIKE_IGNORE_CASE = 12;
    private static final int IS_NULL = 13;
    private static final int IS_NOT_NULL = 14;
    private static final int IN = 15;
    private static final int NOT_IN = 16;
    private static final int IN_IGNORE_CASE = 17;
    private static final int IS_NULL_OR_0 = 18;
    private static final int IS_NULL_OR_SPACE = 19;




    private class ExprCriteria {
        protected String name;
        protected Object value;
        protected int operator;
        protected boolean prependTableName;
        protected boolean joinCondition;
        protected DBCriteria isolatedCriteria;
        protected boolean fullDateFormat;

        public ExprCriteria(String name, int operator, Object value,boolean prependTableName, boolean joinCondition,
                            boolean fullDateFormat) {
            this.name = name;
            this.operator = operator;
            // XXX - we really should be cloning the object at this point,
            // don't want the value to change from beneath us
            this.value = value;
            this.prependTableName = prependTableName;
            this.joinCondition = joinCondition;
            this.fullDateFormat = fullDateFormat;
        }

        public ExprCriteria(String name, int operator, Object value,boolean prependTableName, boolean joinCondition) {
            this(name, operator, value, prependTableName, joinCondition, false);
        }
        public ExprCriteria(String name, int operator, Object value) {
            this(name, operator, value, true, false);
        }

        public ExprCriteria(String name) {
            this(name, NONE, null);
        }

        public ExprCriteria(DBCriteria pIsolatedCriteria) {
            isolatedCriteria = pIsolatedCriteria;
        }

        /**
         * <code>asSqlClause</code> creates an sql clause for the search
         * condition.  The form is "column" | "operator" | "expression".
         * The right hand side is an sql data type or another column.
         *
         * @return a <code>String</code> value representing the sql clause
         */
        public String asSqlClause(String pTableName) {

            StringBuffer sb = new StringBuffer();
            if(isolatedCriteria != null){
                isolatedCriteria.addJoinTables(joinTables);
                sb.append("(");
                sb.append(isolatedCriteria.getWhereClause());
                sb.append(")");
                return sb.toString();
            }

            // if ignoring the case, we'll upcase both the value and attr
            boolean ignoreCase = false;
            if (operator == EQUAL_IGNORE_CASE ||
                    operator == NOT_EQUAL_IGNORE_CASE ||
                    operator == LIKE_IGNORE_CASE ||
                    operator == IN_IGNORE_CASE) {
                ignoreCase = true;
                sb.append("UPPER(");
                if(pTableName != null && prependTableName){
                    sb.append(pTableName);
                    sb.append(".");
                }
                sb.append(name);
                sb.append(")");
            } else if (operator == IS_NULL_OR_0){
            	sb.append("(");
            	if(pTableName != null && prependTableName){
                    sb.append(pTableName);
                    sb.append(".");
                }
                sb.append(name);
                sb.append(" IS NULL OR ");
                if(pTableName != null && prependTableName){
                    sb.append(pTableName);
                    sb.append(".");
                }
                sb.append(name);
                sb.append(" = 0) ");
                return sb.toString();
            } else if (operator == IS_NULL_OR_SPACE){
            	sb.append("(");
            	if(pTableName != null && prependTableName){
                    sb.append(pTableName);
                    sb.append(".");
                }
                sb.append(name);
                sb.append(" IS NULL OR ");
                if(pTableName != null && prependTableName){
                    sb.append(pTableName);
                    sb.append(".");
                }
                sb.append(name);
                sb.append(" = ' ') ");
                return sb.toString();
            } else {
                if(pTableName != null && prependTableName){
                    sb.append(pTableName);
                    sb.append(".");
                }
                sb.append(name);
            }

            // append the operator
            switch (operator) {
                case NONE:
                    return sb.toString();
                case EQUAL:
                case EQUAL_IGNORE_CASE:
                    sb.append(" = ");
                    break;
                case NOT_EQUAL:
                case NOT_EQUAL_IGNORE_CASE:
                    sb.append(" <> ");
                    break;
                case GREATER_THAN:
                    sb.append(" > ");
                    break;
                case GREATER_OR_EQUAL:
                    sb.append(" >= ");
                    break;
                case LESS_THAN:
                    sb.append(" < ");
                    break;
                case LESS_OR_EQUAL :
                    sb.append(" <= ");
                    break;
                case LIKE :
                case LIKE_IGNORE_CASE :
                    sb.append(" LIKE ");
                    break;
                case IS_NULL :
                    sb.append(" IS NULL");
                    break;
                case IS_NULL_OR_0 :
                	//see above
                	throw new IllegalStateException("IS_NULL_OR_0 specified in wrong spot");
                case IS_NOT_NULL :
                    sb.append(" IS NOT NULL");
                    break;
                case IN_IGNORE_CASE:
                case IN:
                    sb.append(" IN (");
                    break;
                case NOT_IN:
                    sb.append(" NOT IN (");
                    break;
            }

            // now the value
            if (value == null) {
                return sb.toString();
            }

            if (joinCondition) {
                sb.append(value);
            }else if (value instanceof String) {
                if (ignoreCase) {
                    sb.append(DBAccess.toQuoted(((String)value).toUpperCase()));
                } else {
                    if(operator!=IN && operator!=NOT_IN && operator!=IN_IGNORE_CASE ) {
                        sb.append(DBAccess.toQuoted((String)value));
                    } else {
                        sb.append(value).append(")");
                    }
                }
                if (operator == LIKE || operator == LIKE_IGNORE_CASE ) {
                    sb.append(" ESCAPE '" + Constants.DB_ESCAPE_SYMBOL + "' ");
                }                
            } else if (value instanceof Integer) {
                sb.append(value);
            } else if (value instanceof Long) {
                sb.append(value);
            } else if (value instanceof Float) {
                sb.append(value);
            } else if (value instanceof Double) {
                sb.append(value);
            } else if (value instanceof Boolean) {
                if (((Boolean)value).booleanValue()) {
                    sb.append(1);
                } else {
                    sb.append(0);
                }
            } else if (value instanceof Date) {
                // XXX - we need some way of doing 'TIME' comparisons; in
                // the db we store date and time components separately
                sb.append("TO_DATE('");
                if (fullDateFormat) {
                    Date dt = (Date)value;
                    //String pattern = "mm-dd-yyyy hh24:mi:ss";
                    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss"); 
                    sb.append(sdf.format(dt));
                    sb.append("','MM-DD-YYYY HH24:MI:SS')");
                } else {
                    java.sql.Date dt = new java.sql.Date(((Date)value).getTime());
                    sb.append(dt.toString());
                    sb.append("','YYYY-MM-DD')");
                }
            } else if (value instanceof List) {
                // if the list is empty, an invalid 'IN' clause will
                // be generated
                Iterator iter = ((List)value).iterator();
                if (iter.hasNext()) {
                    Object item = iter.next();
                    if (item instanceof Integer) {
                        sb.append((Integer)item);
                    } else {
                        if(operator==IN_IGNORE_CASE){
                            sb.append(DBAccess.toQuoted((String)item).toUpperCase());
                        }else{
                            sb.append(DBAccess.toQuoted((String)item));
                        }
                    }
                }
                while (iter.hasNext()) {
                    Object item = iter.next();
                    sb.append(",");
                    if (item instanceof Integer) {
                        sb.append((Integer)item);
                    } else {
                        if(operator==IN_IGNORE_CASE){
                            sb.append(DBAccess.toQuoted((String)item).toUpperCase());
                        } else {
                            sb.append(DBAccess.toQuoted((String)item));
                        }
                    }
                }
                sb.append(")");
            }

            return sb.toString();
        }
    }

    private class OrderCriteria {
        protected String name;
        protected boolean isAscending;

        public OrderCriteria(String name, boolean isAscending) {
            this.name = name;
            this.isAscending = isAscending;
        }

        public String asSqlClause() {
            if (isAscending) {
                return name + " ASC";
            }
            return name + " DESC";
        }
    }

    private void addCondition(String attributeName, int operator,
            Object value) {
        conditions.add(new ExprCriteria(attributeName, operator, value));
    }

    private void addCondition(String attributeName, int operator,
            Object value, boolean fullDateFormat) {
        conditions.add(new ExprCriteria(attributeName, operator, value, false, false, fullDateFormat));
    }

    private void addCondition(String attributeName, int operator,
            boolean value) {
        addCondition(attributeName, operator, new Boolean(value));
    }

    private void addCondition(String attributeName, int operator,
            double value) {
        addCondition(attributeName, operator, new Double(value));
    }

    private void addCondition(String attributeName, int operator, float value) {
        addCondition(attributeName, operator, new Float(value));
    }

    private void addCondition(String attributeName, int operator, int value) {
        addCondition(attributeName, operator, new Integer(value));
    }

    private void addCondition(String attributeName, int operator, long value) {
        addCondition(attributeName, operator, new Long(value));
    }

    /**
     * Kludge to allow user-defined conditions.
     * @param condition a <code>String</code> value with the expression
     */
    public void addCondition(String condition) {
        conditions.add(new ExprCriteria(condition));
    }

    /**
     * Kludge to allow user-defined conditions.
     * @param condition a <code>String</code> value with the expression
     */
    public void addCondition(String condition, boolean prependTable) {
    	ExprCriteria exprCriteria = new ExprCriteria(condition, NONE, null, prependTable, false, false);
        conditions.add(exprCriteria);
    }

    /**
     * <code>addEqualTo</code> adds a "=" expression.
     *
     * @param attributeName a <code>String</code> value
     * @param value an <code>Object</code> value.  Supported object classes
     * are: String, Integer, Long, Boolean, Double, and Date.
     */
    public void addEqualTo(String attributeName, Object value) {
        addCondition(attributeName, EQUAL, value);
    }

    /**
     * <code>addEqualToIgnoreCase</code> adds a "=" expression that is
     * case-insensitive. (Case-insensitive switch only applies to Strings).
     *
     * @param attributeName a <code>String</code> value
     * @param value an <code>Object</code> value
     */
    public void addEqualToIgnoreCase(String attributeName, Object value) {
        addCondition(attributeName, EQUAL_IGNORE_CASE, value);
    }

    /**
     * <code>addEqualTo</code> adds a "=" expression.
     *
     * @param attrName a <code>String</code> value
     * @param value an <code>int</code> value
     */
    public void addEqualTo(String attrName, int value) {
        addCondition(attrName, EQUAL, value);
    }

    /**
     * <code>addEqualTo</code> adds a "=" expression.
     *
     * @param attrName a <code>String</code> value
     * @param value a <code>long</code> value
     */
    public void addEqualTo(String attrName, long value) {
        addCondition(attrName, EQUAL, value);
    }

    /**
     * <code>addEqualTo</code> adds a "=" expression.
     *
     * @param attrName a <code>String</code> value
     * @param value a <code>double</code> value
     */
    public void addEqualTo(String attrName, double value) {
        addCondition(attrName, EQUAL, value);
    }

    /**
     * <code>addEqualTo</code> adds a "=" expression.
     *
     * @param name a <code>String</code> value
     * @param value a <code>boolean</code> value
     */
    public void addEqualTo(String name, boolean value) {
        addCondition(name, EQUAL, value);
    }

    /**
     * <code>addOneOf</code> adds an 'IN' expression.
     *
     * @param attributeName a <code>String</code> value
     * @param value a <code>List</code> value.  Lists
     * of Strings and Integers are supported.  This yields
     * "IN" expressions with lists of values.
     */
    public void addOneOf(String attributeName, List value) {
        addOneOf(IN,attributeName,value);
    }


    /**
     * <code>addOneOf</code> adds an 'IN' expression that is
     * case-insensitive. (Case-insensitive switch only applies to Strings).
     *
     * @param attributeName a <code>String</code> value
     * @param value a <code>List</code> value.  Lists
     * of Strings and Integers are supported.  This yields
     * "IN" expressions with lists of values.
     */
    public void addOneOfIgnoreCase(String attributeName, List value) {
        addOneOf(IN_IGNORE_CASE,attributeName,value);
    }

    private void addOneOf(int inType,String attributeName, List value) {
        // Need to break up like:
        // (X IN (n1, n2,...,n1000) OR X IN (n1001, n1002,...,n2000) OR ...)
        int n = value.size();
        if(n==0) {
            DBCriteria critComposed = new DBCriteria();
            critComposed.addCondition("1=2");
            addIsolatedCriterita(critComposed);
            return;
        }
        if (n < MAX_SQL_ITEMS) {
            addCondition(attributeName, inType, value);
        } else {
            DBCriteria critComposed = new DBCriteria();
            critComposed.addCondition(attributeName, inType, value.subList(0,MAX_SQL_ITEMS));
            // Debatable whether this is wise to do - tests by Yuri seem
            // to indicate that the resulting SQL queries are pretty slow
            // - slower than separate queries
            //addCondition(attributeName, IN, value.subList(0,MAX_SQL_ITEMS));
            ArrayList exprs = new ArrayList();
            for (int i = MAX_SQL_ITEMS; i < n; i+=MAX_SQL_ITEMS) {
                int end = (i + MAX_SQL_ITEMS > n) ? n : i + MAX_SQL_ITEMS;
                if(inType == IN){
                   DBCriteria crit = new DBCriteria();
                   crit.addCondition(attributeName, inType, value.subList(i,end));
                   critComposed.addOrCriteria(crit);
                }else if(inType == NOT_IN){
                   critComposed.addCondition(attributeName, inType, value.subList(i,end));
                }else{
                	throw new IllegalArgumentException("inType parameter not known.  Must be IN or NOT_IN");
                }

            }
            addCondition("("+critComposed.getWhereClause()+")");
        }
    }

    /**
     * <code>addOneOf</code> adds an 'IN' expression.
     *
     * @param attributeName a <code>String</code> value
     * @param value a <code>String</code> value.  This yields
     * "IN" expression with value string.
     */
    public void addOneOf(String attributeName, String value) {
        addCondition(attributeName, IN, value);
    }

    /**
     * <code>addNotOneOf</code> adds an 'NOT IN' expression.
     *
     * @param attributeName a <code>String</code>, column name
     * @param value a <code>List</code> value.  All the ids
     *  to be omitted.
     */
    public void addNotOneOf(String attributeName, List value) {
        addOneOf(NOT_IN,attributeName,  value);
    }

    /**
     * <code>addNotOneOf</code> adds an 'NOT IN' expression.
     *
     * @param attributeName a <code>String</code>, column name
     * @param value a <code>String</code> value.  .  This yields
     * "NOT_IN" expression with value string.
     */
    public void addNotOneOf(String attributeName, String value) {
        addCondition(attributeName, NOT_IN, value);
    }
    /**
     * <code>addLessThan</code> adds a "<" expression.
     *
     * @param attrName a <code>String</code> value
     * @param value an <code>Object</code> value
     */
    public void addLessThan(String attrName, Object value) {
        addCondition(attrName, LESS_THAN, value);
    }

    /**
     * <code>addLessThan</code> adds a "<" expression.
     *
     * @param attrName a <code>String</code> value
     * @param value an <code>int</code> value
     */
    public void addLessThan(String attrName, int value) {
        addCondition(attrName, LESS_THAN, value);
    }

    /**
     * <code>addLessThan</code> adds a "<" expression.
     *
     * @param attrName a <code>String</code> value
     * @param value a <code>long</code> value
     */
    public void addLessThan(String attrName, long value) {
        addCondition(attrName, LESS_THAN, value);
    }

    /**
     * <code>addLessThan</code> adds a "<" expression.
     *
     * @param attrName a <code>String</code> value
     * @param value a <code>double</code> value
     */
    public void addLessThan(String attrName, double value) {
        addCondition(attrName, LESS_THAN, value);
    }

    /**
     * <code>addLessThanOrEqual</code> adds a "<=" expression.
     *
     * @param attrName a <code>String</code> value
     * @param value an <code>Object</code> value
     */
    public void addLessOrEqual(String attrName, Object value) {
        addCondition(attrName, LESS_OR_EQUAL, value);
    }

    public void addLessOrEqual(String attrName, Object value, boolean fullDateFormat) {
        addCondition(attrName, LESS_OR_EQUAL, value, fullDateFormat);
    }

    /**
     * <code>addLessThanOrEqual</code> adds a "<=" expression.
     *
     * @param attrName a <code>String</code> value
     * @param value an <code>int</code> value
     */
    public void addLessOrEqual(String attrName, int value) {
        addCondition(attrName, LESS_OR_EQUAL, value);
    }

    /**
     * <code>addLessThanOrEqual</code> adds a "<=" expression.
     *
     * @param attrName a <code>String</code> value
     * @param value a <code>long</code> value
     */
    public void addLessOrEqual(String attrName, long value) {
        addCondition(attrName, LESS_OR_EQUAL, value);
    }

    /**
     * <code>addLessThanOrEqual</code> adds a "<=" expression.
     *
     * @param attrName a <code>String</code> value
     * @param value a <code>double</code> value
     */
    public void addLessOrEqual(String attrName, double value) {
        addCondition(attrName, LESS_OR_EQUAL, value);
    }

    /**
     * <code>addGreaterThan</code> adds a ">" expression.
     *
     * @param attrName a <code>String</code> value
     * @param value an <code>Object</code> value
     */
    public void addGreaterThan(String attrName, Object value) {
        addCondition(attrName, GREATER_THAN, value);
    }

    /**
     * <code>addGreaterThan</code> adds a ">" expression.
     *
     * @param attrName a <code>String</code> value
     * @param value an <code>int</code> value
     */
    public void addGreaterThan(String attrName, int value) {
        addCondition(attrName, GREATER_THAN, value);
    }

    /**
     * <code>addGreaterThan</code> adds a ">" expression.
     *
     * @param attrName a <code>String</code> value
     * @param value a <code>long</code> value
     */
    public void addGreaterThan(String attrName, long value) {
        addCondition(attrName, GREATER_THAN, value);
    }

    /**
     * <code>addGreaterThan</code> adds a ">" expression.
     *
     * @param attrName a <code>String</code> value
     * @param value a <code>double</code> value
     */
    public void addGreaterThan(String attrName, double value) {
        addCondition(attrName, GREATER_THAN, value);
    }

    /**
     * <code>addGreaterThanOrEqual</code> adds a ">=" expression.
     *
     * @param attrName a <code>String</code> value
     * @param value an <code>Object</code> value
     */
    public void addGreaterOrEqual(String attrName, Object value) {
        addCondition(attrName, GREATER_OR_EQUAL, value);
    }

    public void addGreaterOrEqual(String attrName, Object value, boolean fullDateFormat) {
        addCondition(attrName, GREATER_OR_EQUAL, value, fullDateFormat);
    }

    /**
     * <code>addGreaterThanOrEqual</code> adds a ">=" expression.
     *
     * @param attrName a <code>String</code> value
     * @param value an <code>int</code> value
     */
    public void addGreaterOrEqual(String attrName, int value) {
        addCondition(attrName, GREATER_OR_EQUAL, value);
    }

    /**
     * <code>addGreaterThanOrEqual</code> adds a ">=" expression.
     *
     * @param attrName a <code>String</code> value
     * @param value a <code>long</code> value
     */
    public void addGreaterOrEqual(String attrName, long value) {
        addCondition(attrName, GREATER_OR_EQUAL, value);
    }

    /**
     * <code>addGreaterThanOrEqual</code> adds a ">=" expression.
     *
     * @param attrName a <code>String</code> value
     * @param value a <code>double</code> value
     */
    public void addGreaterOrEqual(String attrName, double value) {
        addCondition(attrName, GREATER_OR_EQUAL, value);
    }

    /**
     * <code>addNotEqualTo</code> adds a "<>" expression.
     *
     * @param attrName a <code>String</code> value
     * @param value an <code>Object</code> value
     */
    public void addNotEqualTo(String attrName, Object value) {
        addCondition(attrName, NOT_EQUAL, value);
    }

    /**
     * <code>addNotEqualToIgnoreCase</code> adds a "<>" expression that is
     * case-insensitive. (Case-insensitive switch only applies to Strings).
     *
     * @param attrName a <code>String</code> value
     * @param value an <code>Object</code> value
     */
    public void addNotEqualToIgnoreCase(String attrName, Object value) {
        addCondition(attrName, NOT_EQUAL_IGNORE_CASE, value);
    }

    /**
     * <code>addNotEqualTo</code> adds a "<>" expression.
     *
     * @param attrName a <code>String</code> value
     * @param value an <code>int</code> value
     */
    public void addNotEqualTo(String attrName, int value) {
        addCondition(attrName, NOT_EQUAL, value);
    }

    /**
     * <code>addNotEqualTo</code> adds a "<>" expression.
     *
     * @param attrName a <code>String</code> value
     * @param value a <code>long</code> value
     */
    public void addNotEqualTo(String attrName, long value) {
        addCondition(attrName, NOT_EQUAL, value);
    }

    /**
     * <code>addNotEqualTo</code> adds a "<>" expression.
     *
     * @param attrName a <code>String</code> value
     * @param value a <code>double</code> value
     */
    public void addNotEqualTo(String attrName, double value) {
        addCondition(attrName, NOT_EQUAL, value);
    }

    /**
     * <code>addLike</code> adds a "LIKE" expression.
     *
     * @param attrName a <code>String</code> value
     * @param value a <code>String</code> value
     */
    public void addLike(String attrName, String value) {
        addCondition(attrName, LIKE, value);
    }

    /**
     * <code>addLikeIgnoreCase</code> adds a "LIKE" expression that is
     * case insensitive.
     *
     * @param attrName a <code>String</code> value
     * @param value a <code>String</code> value
     */
    public void addLikeIgnoreCase(String attrName, String value) {
        addCondition(attrName, LIKE_IGNORE_CASE, value);
    }

    /**
     * <code>addContains</code> adds a "LIKE '%value%'" expression.
     *
     * @param attrName a <code>String</code> value
     * @param value a <code>String</code> value
     */
    public void addContains(String attrName, String value) {
        addCondition(attrName, LIKE,
                Utility.isSet(value) ?  "%" + value + "%" : value);
    }

    /**
     * <code>addContainsIgnoreCase</code> adds a "LIKE '%value%" expression
     * that is case insensitive.
     *
     * @param attrName a <code>String</code> value
     * @param value a <code>String</code> value
     */
    public void addContainsIgnoreCase(String attrName, String value) {
        addCondition(attrName, LIKE_IGNORE_CASE,
                Utility.isSet(value) ?  "%" + value + "%" : value);
    }

    /**
     * <code>addJoinTableContainsIgnoreCase</code> adds a "LIKE '%value%" expression
     * that is case insensitive.
     *
     * @param attrName a <code>String</code> value
     * @param value a <code>String</code> value
     */
    public void addJoinTableContainsIgnoreCase(String joinTable,String joinAttrName, String value) {
        addCondition(joinTable+"."+joinAttrName, LIKE_IGNORE_CASE,
                Utility.isSet(value) ?  "%" + value + "%" : value);
    }

    /**
     * <code>addBeginsWith</code> adds a "LIKE 'value%'" expression.
     *
     * @param attrName a <code>String</code> value
     * @param value a <code>String</code> value
     */
    public void addBeginsWith(String attrName, String value) {
        addCondition(attrName, LIKE,
                Utility.isSet(value) ? value + "%" : value);
    }

    /**
     * <code>addBeginsWithIgnoreCase</code> adds a "LIKE 'value%'" expression
     * that is case insensitive.
     *
     * @param attrName a <code>String</code> value
     * @param value a <code>String</code> value
     */
    public void addBeginsWithIgnoreCase(String attrName, String value) {
        addCondition(attrName, LIKE_IGNORE_CASE,
                Utility.isSet(value) ? value + "%" : value);
    }

    /**
     * <code>addJoinTableBeginsWithIgnoreCase</code> adds a "LIKE 'value%'" expression
     * that is case insensitive.
     *
     * @param attrName a <code>String</code> value
     * @param value a <code>String</code> value
     */
    public void addJoinTableBeginsWithIgnoreCase(String joinTable, String joinAttrName, String value) {
        addCondition(joinTable+"."+joinAttrName, LIKE_IGNORE_CASE,
                Utility.isSet(value) ? value + "%" : value);
    }

    /**
     * <code>addIsNull</code> adds a "IS NULL" expression.
     *
     * @param attrName a <code>String</code> value
     */
    public void addIsNull(String attrName) {
        addCondition(attrName, IS_NULL, null);
    }

    /**
     * <code>addIsNull</code> return only data where this column is null
     * or it is set to a 0 value.
     *
     * @param attrName a <code>String</code> value
     */
    public void addIsNullOr0(String attrName) {
        addCondition(attrName, IS_NULL_OR_0, null);
    }

    public void addIsNullOrSpace(String attrName) {
        addCondition(attrName, IS_NULL_OR_SPACE, null);
    }

    /**
     * <code>addIsNotNull</code> adds a "IS NOT NULL" expression.
     *
     * @param attrName a <code>String</code> value
     */
    public void addIsNotNull(String attrName) {
        addCondition(attrName, IS_NOT_NULL, null);
    }

    /**
     * <code>addOrCriteria</code> adds a "OR" expression.
     *
     * @param criteria a <code>DBCriteria</code> value
     */
    public void addOrCriteria(DBCriteria criteria) {
        conditions.add(criteria);
    }

    /**
     * <code>addOrderBy</code> adds an "ORDER BY" expression.
     *
     * @param attrName a <code>String</code> value
     * @param isAscending a <code>boolean</code> value
     */
    public void addOrderBy(String attrName, boolean isAscending) {
        orderConditions.add(new OrderCriteria(attrName, isAscending));
    }

    /**
     * <code>addOrderBy</code> adds an "ORDER BY" expression.
     *
     * @param attrName a <code>String</code> value
     */
    public void addOrderBy(String attrName) {
        addOrderBy(attrName, true);
    }


    /**
     * <code>addOrderBy</code> adds an "ORDER BY" expression generally speaking
     * for a numberic field where the order by should be in alpha not numeric
     * format:  5,20,300,1000 becomes  100,20,300,10000
     *
     * @param attrName a <code>String</code> value
     */
    public void addOrderByAlpha(String attrName) {
        addOrderBy("to_char("+attrName+")", true);

    }

    /**
     * Transform a criteria list into an sql clause.
     *  Recursion is used to support nested "OR" criteria.
     * @return a <code>String</code> value
     */
    public String getWhereClause() {
        return getWhereClause(null);
    }


    public String getWhereClause(String pTableName) {
        if (conditions.size() == 0) return null;

        boolean first = true;
        StringBuffer sb = new StringBuffer();
        Iterator iter = conditions.iterator();
        while (iter.hasNext()) {
            Object obj = iter.next();
            if  (obj instanceof DBCriteria) {
                sb.append(" OR (");
                ((DBCriteria)obj).addJoinTables(getJoinTables());
                sb.append(((DBCriteria)obj).getWhereClause());
                sb.append(")");
            } else {
                if (first == true) {
                    first = false;
                } else {
                    sb.append(" AND ");
                }
                sb.append(((ExprCriteria)obj).asSqlClause(pTableName));
            }
        }

        return sb.toString();
    }
    /**
     * Builds a string for the 'ORDER BY' clause
     *
     * @return an SQL string for the 'ORDER BY' clause
     */
    public String getOrderByClause() {
        if (orderConditions.size() == 0) return null;

        StringBuffer sb = new StringBuffer();
        sb.append("ORDER BY ");
        Iterator iter = orderConditions.iterator();
        boolean first = true;
        while (iter.hasNext()) {
            OrderCriteria criteria = (OrderCriteria)iter.next();
            if (!first) {
                sb.append(", ");
            } else {
                first = false;
            }
            sb.append(criteria.asSqlClause());
        }
        return sb.toString();
    }

    /**
     * Return a string representing the sql clause.
     *
     * @return a <code>String</code> value
     */
    public String getSqlClause() {
        return getSqlClause(null);
    }

    /**
     * Return a string representing the sql clause.
     *
     * @return a <code>String</code> value
     */
    public String getSqlClause(String pTableName) {
        String w = getWhereClause(pTableName);
        String o = getOrderByClause();
        StringBuffer rtn = new StringBuffer();
        if (w != null) {
            rtn.append(w);
            if (o != null) {
                rtn.append(" ");
            }
        }
        if (o != null) {
            rtn.append(o);
        }
        return rtn.toString();
    }

    /**
     *Joins this table to anouther table.
     */
    public void addJoinCondition(String attrName, String joinTable, String joinAttrName){
        addJoinTable(joinTable);
        conditions.add(new ExprCriteria(attrName,EQUAL,joinTable+"."+joinAttrName,true,true));
    }

    /**
     *Joins an alternate table to anouther alternate table
     */
    public void addJoinCondition(String join1Table, String join1AttrName, String join2Table, String join2AttrName){
        addJoinTable(join1Table);
        addJoinTable(join2Table);
        conditions.add(new ExprCriteria(join1Table+"."+join1AttrName,EQUAL,join2Table+"."+join2AttrName,false,true));
    }

    public void addJoinTable(String tableName){
        if(!joinTables.contains(tableName)){
            joinTables.add(tableName);
        }
    }

    /**
     *Adds an equal condition to a join table
     */
    public void addJoinTableEqualTo(String joinTable, String joinAttrName, String value){
        addJoinTable(joinTable);
        conditions.add(new ExprCriteria(joinTable+"."+joinAttrName,EQUAL,value,false,false));
    }

    /**
     * <code>addJoinTableBeginsWithIgnoreCase</code> adds a "LIKE 'value%'" expression
     * that is case insensitive.
     *
     * @param attrName a <code>String</code> value
     * @param value a <code>String</code> value
     */
    public void addJoinTableEqualToIgnoreCase(String joinTable, String joinAttrName, String value) {
        addJoinTable(joinTable);
        conditions.add(new ExprCriteria(joinTable+"."+joinAttrName,EQUAL_IGNORE_CASE,value,false,false));
    }

    /**
     *Adds a like condition to a join table
     */
    public void addJoinTableLike(String joinTable, String joinAttrName, String value){
        addJoinTable(joinTable);
        conditions.add(new ExprCriteria(joinTable+"."+joinAttrName,LIKE,value,false,false));
    }

    /**
     *Adds a like condition to a join table ignoring the case
     */
    public void addJoinTableLikeIgnoreCase(String joinTable, String joinAttrName, String value){
        addJoinTable(joinTable);
        conditions.add(new ExprCriteria(joinTable+"."+joinAttrName,LIKE_IGNORE_CASE,value,false,false));
    }

    /**
     *Adds an equal condition to a join table
     */
    public void addJoinTableEqualTo(String joinTable, String joinAttrName, int value){
        addJoinTable(joinTable);
        conditions.add(new ExprCriteria(joinTable+"."+joinAttrName,EQUAL,new Integer(value),false,false));
    }

    /**
     *Adds an not equal condition to a join table
     */
    public void addJoinTableNotEqualTo(String joinTable, String joinAttrName, String value){
        addJoinTable(joinTable);
        conditions.add(new ExprCriteria(joinTable+"."+joinAttrName,NOT_EQUAL,value,false,false));
    }

    /**
     *Adds an not equal condition to a join table
     */
    public void addJoinTableNotEqualTo(String joinTable, String joinAttrName, int value){
        addJoinTable(joinTable);
        conditions.add(new ExprCriteria(joinTable+"."+joinAttrName,NOT_EQUAL,new Integer(value),false,false));
    }

    /**
     *Adds an is null condition to a join table
     */
    public void addJoinTableIsNull(String joinTable,String joinAttrName) {
        addJoinTable(joinTable);
        conditions.add(new ExprCriteria(joinTable+"."+joinAttrName,IS_NULL,null,false,false));
    }

    public void addJoinTableIsNullOrSpace(String joinTable,String joinAttrName) {
        addJoinTable(joinTable);
        conditions.add(new ExprCriteria(joinTable+"."+joinAttrName,IS_NULL_OR_SPACE,null,false,false));
    }

    /**
     *Adds a greater than condition to a join table
     */
    public void addJoinTableGreaterThan(String joinTable,String joinAttrName,Object value) {
        addJoinTable(joinTable);
        conditions.add(new ExprCriteria(joinTable+"."+joinAttrName,GREATER_THAN,value,false,false));
    }

    /**
     *Adds a greater than condition to a join table
     */
    public void addJoinTableGreaterThan(String joinTable,String joinAttrName,int value) {
        addJoinTable(joinTable);
        conditions.add(new ExprCriteria(joinTable+"."+joinAttrName,GREATER_THAN,new Integer(value),false,false));
    }

    /**
     *Adds a less than condition to a join table
     */
    public void addJoinTableLessThan(String joinTable,String joinAttrName,Object value) {
        addJoinTable(joinTable);
        conditions.add(new ExprCriteria(joinTable+"."+joinAttrName,LESS_THAN,value,false,false));
    }

    /**
     *Adds a greater or equal to  than condition to a join table
     */
    public void addJoinTableGreaterOrEqual(String joinTable,String joinAttrName,Object value) {
        addJoinTable(joinTable);
        conditions.add(new ExprCriteria(joinTable+"."+joinAttrName,GREATER_OR_EQUAL,value,false,false));
    }

    /**
     *Adds a greater or equal to  than condition to a join table
     */
    public void addJoinTableGreaterOrEqual(String joinTable,String joinAttrName,int value) {
        addJoinTable(joinTable);
        conditions.add(new ExprCriteria(joinTable+"."+joinAttrName,GREATER_OR_EQUAL,new Integer(value),false,false));
    }

    /**
     *Adds a less or equal to than condition to a join table
     */
    public void addJoinTableLessOrEqual(String joinTable,String joinAttrName,Object value) {
        addJoinTable(joinTable);
        conditions.add(new ExprCriteria(joinTable+"."+joinAttrName,LESS_OR_EQUAL,value,false,false));
    }

    /**
     *Adds an is not null condition to a join table
     */
    public void addJoinTableIsNotNull(String joinTable,String joinAttrName) {
        addJoinTable(joinTable);
        conditions.add(new ExprCriteria(joinTable+"."+joinAttrName,IS_NOT_NULL,null,false,false));
    }

    /**
     *Adds in a dbcriteria object and seperates it in parens
     */
    public void addIsolatedCriterita(DBCriteria pIsolatedCriterita){
        conditions.add(new ExprCriteria(pIsolatedCriterita));
    }


    /**
     * <code>addJoinTableOneOf</code> adds an 'IN' expression.
     *
     * @param joinTable the join table this condition applies to
     * @param joinAttrName a <code>String</code> value
     * @param value a <code>List</code> value.  Lists
     * of Strings and Integers are supported.  This yields
     * "IN" expressions with lists of values.
     */
    public void addJoinTableOneOf(String joinTable,String joinAttrName, List value) {
        addJoinTable(joinTable);
        conditions.add(new ExprCriteria(joinTable+"."+joinAttrName,IN,value,false,false));
    }

    /**
     * <code>addJoinTableNotOneOf</code> adds an 'NOT IN' expression.
     *
     * @param joinTable the join table this condition applies to
     * @param joinAttrName a <code>String</code> value
     * @param value a <code>List</code> value.  Lists
     * of Strings and Integers are supported.  This yields
     * "NOT IN" expressions with lists of values.
     */
    public void addJoinTableNotOneOf(String joinTable,String joinAttrName, List value) {
        int n = value.size();
        if(n==0) {
            return;
        }
        addJoinTable(joinTable);
        conditions.add(new ExprCriteria(joinTable+"."+joinAttrName, NOT_IN, value,false,false));
    }

   public void addJoinTableNotOneOf(String joinTable,String joinAttrName, String value) {
        addJoinTable(joinTable);
        addCondition(joinTable+"."+joinAttrName, NOT_IN, value);
    }

    /**
     * <code>addJoinTableOneOf</code> adds an 'IN' expression.
     *
     * @param joinTable the join table this condition applies to
     * @param joinAttrName a <code>String</code> value
     * @param value a <code>String</code> value.  This yields
     * "IN" expression with value string.
     */
    public void addJoinTableOneOf(String joinTable,String joinAttrName, String value) {
        addJoinTable(joinTable);
        addCondition(joinTable+"."+joinAttrName, IN, value);
    }

    /**
     * <code>addJoinTableOneOf</code> adds an 'IN' expression that is
     * case-insensitive. (Case-insensitive switch only applies to Strings).
     *
     * @param joinTable the join table this condition applies to
     * @param joinAttrName a <code>String</code> value
     * @param value a <code>List</code> value.  Lists
     * of Strings and Integers are supported.  This yields
     * "IN" expressions with lists of values.
     */
    public void addJoinTableOneOfIgnoreCase(String joinTable,String joinAttrName, List value) {
        addJoinTable(joinTable);
        addCondition(joinTable+"."+joinAttrName, IN_IGNORE_CASE, value);
    }


    /**
     * <code>addOrderBy</code> adds an "ORDER BY" expression.
     *
     * @param attrName a <code>String</code> value
     * @param isAscending a <code>boolean</code> value
     */
    public void addJoinTableOrderBy(String joinTable,String joinAttrName, boolean isAscending) {
        orderConditions.add(new OrderCriteria(joinTable+"."+joinAttrName, isAscending));
    }

    /**
     * <code>addOrderBy</code> adds an "ORDER BY" expression.
     *
     * @param attrName a <code>String</code> value
     */
    public void addJoinTableOrderBy(String joinTable,String joinAttrName) {
        addJoinTableOrderBy(joinTable,joinAttrName, true);
    }

    /**
     *Returns a list of tables that this criteria object specifies have been joined.
     */
    public Set getJoinTables(){
        return joinTables;
    }

    /**
     *Meant to be used by or joins
     */
    void addJoinTables(Set pJoinTables){
        if(joinTables != null){
            joinTables.addAll(pJoinTables);
        }else{
            joinTables = pJoinTables;
        }
    }

    List dataAccessList = new ArrayList();
    /**
     *Order in which the DataAccess objects are added will determine the
     *order in which they are returned using the JoinDataAccess object.
     */
    public void addDataAccessForJoin(DataAccess dataAccess){
        dataAccessList.add(dataAccess);
    }

    /**
     *Returns the list of dataAccess objects that are used in this join operation.
     */
    public List getDataAccessObjectsForJoin(){
        return dataAccessList;
    }
    // This method can be used instead of regular method addOneOf(String, List)
    // in case of joining tables to generate correct condition
    // if list's size might be more than MAX_SQL_ITEMS.
    public void addOneOfIsolated(String tableName, String attributeName, List valueList){
      int MAX_SQL_ITEMS =1000;
      int n = valueList.size();
      DBCriteria critComposed = new DBCriteria();
      if (MAX_SQL_ITEMS < n) {
        String attribute = tableName + "." +attributeName;
        String values = IdVector.toCommaString(valueList.subList(0, MAX_SQL_ITEMS));
        StringBuffer buffer = new StringBuffer();
        buffer.append(attribute + " IN (" + values + ")");
        for (int i = MAX_SQL_ITEMS; i < n; i += MAX_SQL_ITEMS) {
          int end = (i + MAX_SQL_ITEMS > n) ? n : i + MAX_SQL_ITEMS;
          values = IdVector.toCommaString(valueList.subList(i, end));
          buffer.append(" OR (" + attribute + " IN (" + values + "))");
        }
        critComposed.addCondition("(" + buffer.toString() + ")");
        addIsolatedCriterita(critComposed);
      } else {
        addOneOf(attributeName, valueList);
      }
    }
}
