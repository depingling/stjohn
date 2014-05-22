package com.cleanwise.service.api.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Category;

import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.StoreMessageViewVector;
import com.cleanwise.service.api.value.StoreMessageView;

public class StoreMessageDAO {

	private static final String CLW_STORE_MESSAGE = "CLW_STORE_MESSAGE";
	private static Category log = Category.getInstance(StoreMessageDAO.class
			.getName());
	
	public StoreMessageViewVector getMessagesForUser(Connection conn,
			DBCriteria dbCriteria, int pMaxRows) throws SQLException {
		return getMessagesForUser(conn,dbCriteria,pMaxRows,null);
	}
	
	public StoreMessageViewVector getMessagesForUser(Connection conn,
			DBCriteria dbCriteria, int pMaxRows, List messageTypeCds) throws SQLException {
		StringBuffer sqlBuf;
		String where;

		dbCriteria
				.addJoinTable(StoreMessageDetailDataAccess.CLW_STORE_MESSAGE_DETAIL
						+ " DETAIL");
		dbCriteria.addCondition(StoreMessageDataAccess.STORE_MESSAGE_ID + " = "
				+ "DETAIL." + StoreMessageDetailDataAccess.STORE_MESSAGE_ID);

		sqlBuf = new StringBuffer("SELECT CLW_STORE_MESSAGE.STORE_MESSAGE_ID,"
				+ "DETAIL.STORE_MESSAGE_DETAIL_ID," + "DETAIL.MESSAGE_TITLE,"
				+ "CLW_STORE_MESSAGE.SHORT_DESC," + "CLW_STORE_MESSAGE.MESSAGE_TYPE,"
				+ "CLW_STORE_MESSAGE.POSTED_DATE," + "CLW_STORE_MESSAGE.END_DATE,"
				+ "CLW_STORE_MESSAGE.FORCED_READ_COUNT," + "DETAIL.LANGUAGE_CD,"
				+ "DETAIL.COUNTRY," + "DETAIL.MESSAGE_AUTHOR,"
				+ "DETAIL.MESSAGE_ABSTRACT," + "DETAIL.MESSAGE_BODY,"
				+ "CLW_STORE_MESSAGE.STORE_MESSAGE_STATUS_CD,"
				+ "CLW_STORE_MESSAGE.ADD_BY," + "CLW_STORE_MESSAGE.ADD_DATE,"
				+ "CLW_STORE_MESSAGE.MOD_BY," + "CLW_STORE_MESSAGE.MOD_DATE "
				+ "FROM CLW_STORE_MESSAGE");

		Collection otherTables = dbCriteria.getJoinTables();
		where = dbCriteria.getSqlClause(CLW_STORE_MESSAGE);

		Iterator it = otherTables.iterator();
		while (it.hasNext()) {
			String otherTable = (String) it.next();
			if (!CLW_STORE_MESSAGE.equalsIgnoreCase(otherTable)) {
				sqlBuf.append(",");
				sqlBuf.append(otherTable);
			}
		}

		if (where != null && !where.equals("")) {
			sqlBuf.append(" WHERE ");

			if(messageTypeCds!=null){
				sqlBuf.append(" CLW_STORE_MESSAGE.MESSAGE_TYPE IN ("+Utility.toCommaSting(messageTypeCds,'\'')+") AND ");
				
			}
			
			sqlBuf.append(where);
		}

		String sql = sqlBuf.toString();
		if (log.isDebugEnabled()) {
			log.debug("SQL: " + sql);
		}
		Statement stmt = conn.createStatement();
		if (pMaxRows > 0) {
			// Insure that only positive values are set.
			stmt.setMaxRows(pMaxRows);
		}
		ResultSet rs = stmt.executeQuery(sql);
		StoreMessageViewVector v = new StoreMessageViewVector();
		while (rs.next()) {
			StoreMessageView view = StoreMessageView.createValue();

			view.setStoreMessageId(rs.getInt(1));
			view.setStoreMessageDetailId(rs.getInt(2));
			view.setShortDesc(rs.getString(4));
			view.setStoreMessageStatusCd(rs.getString(14));
			view.setMessageTitle(rs.getString(3));
			view.setMessageAbstract(rs.getString(12));
			view.setMessageBody(rs.getString(13));
			view.setMessageType(rs.getString(5));
			view.setPostedDate(rs.getDate(6));
			view.setEndDate(rs.getDate(7));
			view.setForcedReadCount(rs.getInt(8));
			// view.setDisplayOrder(rs.getString(2));
			view.setLanguageCd(rs.getString(9));
			view.setCountry(rs.getString(10));
			view.setMessageAuthor(rs.getString(11));
			view.setAddBy(rs.getString(15));
			view.setAddDate(rs.getDate(16));
			view.setModBy(rs.getString(17));
			view.setModDate(rs.getDate(18));

			v.add(view);
		}

		rs.close();
		stmt.close();

		return v;
	}
}
