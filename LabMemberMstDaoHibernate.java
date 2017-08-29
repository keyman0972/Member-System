package com.ddsc.km.exam.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;

import com.ddsc.core.dao.hibernate.GenericDaoHibernate;
import com.ddsc.core.entity.UserInfo;
import com.ddsc.core.exception.DdscApplicationException;
import com.ddsc.core.util.HibernateScalarHelper;
import com.ddsc.core.util.LocaleDataHelper;
import com.ddsc.core.util.Pager;
import com.ddsc.km.exam.dao.ILabMemberMstDao;
import com.ddsc.km.exam.entity.LabMemberMst;

/**
 * <table>
 * <tr>
 * <th>版本</th>
 * <th>日期</th>
 * <th>詳細說明</th>
 * <th>modifier</th>
 * </tr>
 * <tr>
 * <td>1.0</td>
 * <td>2017/8/15</td>
 * <td>新建檔案</td>
 * <td>"keyman"</td>
 * </tr>
 * </table>
 * @author "keyman"
 *
 * 類別說明 :
 *
 *
 * 版權所有 Copyright 2008 © 中菲電腦股份有限公司 本網站內容享有著作權，禁止侵害，違者必究。 <br>
 * (C) Copyright Dimerco Data System Corporation Inc., Ltd. 2009 All Rights
 */

public class LabMemberMstDaoHibernate extends GenericDaoHibernate<LabMemberMst,String> implements ILabMemberMstDao {

	@Override
	public Pager searchByConditions(Map<String, Object> conditions, Pager pager, UserInfo userInfo) throws DdscApplicationException {
		
		String optCdeNam_lang = LocaleDataHelper.getPropertityWithLocalUpper("OPT_CDE_NAM", userInfo.getLocale());
		
		StringBuffer sbsql = new StringBuffer();
		sbsql.append("SELECT LMM.MEMB_ID, LMM.MEMB_NAME, LMM.MEMB_TEL, ");
		sbsql.append("	GRADE.OPT_CDE AS OPT_GRADE_CDE, GRADE."+optCdeNam_lang+" AS OPT_GRADE_CDE_NAM, ");
		sbsql.append("	STATUES.OPT_CDE AS OPT_STATUES_CDE, STATUES."+optCdeNam_lang+" AS OPT_STATUES_CDE_NAM, ");
		sbsql.append("	LMM.MEMB_PREPAID_VAL, MAX(LMPH.PREPAID_DATE) AS PREPAID_DATE ");
		sbsql.append("FROM LAB_MEMBER_MST LMM ");
		sbsql.append("LEFT JOIN LAB_MEMBER_PREPAID_HIS LMPH ON LMM.MEMB_ID=LMPH.MEMB_ID ");
		sbsql.append("LEFT JOIN COMM_OPT_CDE GRADE ON LMM.MEMB_GRADE = GRADE.OPT_CDE_OID ");
		sbsql.append("LEFT JOIN COMM_OPT_CDE STATUES ON LMM.MEMB_STATUS = STATUES.OPT_CDE AND STATUES.OPT_CTG_CDE = 'F0004' ");

		String keyword = "WHERE ";
		List<Object> value = new ArrayList<Object>();
		if (StringUtils.isNotEmpty((String) conditions.get("memberId"))) {
			sbsql.append(keyword + "LMM.MEMB_ID LIKE ? ");
			value.add(conditions.get("memberId") + "%");
			keyword = "AND ";
		}
		if (StringUtils.isNotEmpty((String) conditions.get("memberName"))) {
			sbsql.append(keyword +"LMM.MEMB_NAME LIKE ? ");
			value.add("%"+conditions.get("memberName") + "%");
			keyword = "AND ";
		}
		if (StringUtils.isNotEmpty((String) conditions.get("memberTel"))) {
			sbsql.append(keyword +" LMM.MEMB_TEL LIKE ? ");
			value.add(conditions.get("memberTel") + "%");
			keyword = "AND ";
		}
		
		List<String> membereStatues = (List<String>) conditions.get("membereStatues");
		if (membereStatues != null && !membereStatues.isEmpty()) {
			sbsql.append(keyword +"LMM.MEMB_STATUS IN( "+ this.getSqlQuestionMark(membereStatues.size())+" ) ");
			value.addAll(membereStatues);
			keyword = "AND ";
		}
		sbsql.append("GROUP BY LMM.MEMB_ID, LMM.MEMB_NAME, LMM.MEMB_TEL, LMM.MEMB_TEL, ");
		sbsql.append("	GRADE.OPT_CDE, GRADE."+optCdeNam_lang+", STATUES.OPT_CDE, ");
		sbsql.append("	STATUES."+optCdeNam_lang+", LMM.MEMB_PREPAID_VAL ");
		sbsql.append("ORDER BY LMM.MEMB_ID ");
		
		// 建立List<HibernateScalarHelper> scalarList = new ArrayList<HibernateScalarHelper>(); 並add
		List<HibernateScalarHelper> scalarList = new ArrayList<HibernateScalarHelper>();
		scalarList.add(new HibernateScalarHelper("MEMB_ID", Hibernate.STRING));
		scalarList.add(new HibernateScalarHelper("MEMB_NAME", Hibernate.STRING));
		scalarList.add(new HibernateScalarHelper("MEMB_TEL", Hibernate.STRING));
		scalarList.add(new HibernateScalarHelper("OPT_GRADE_CDE", Hibernate.STRING));
		scalarList.add(new HibernateScalarHelper("OPT_GRADE_CDE_NAM", Hibernate.STRING));
		scalarList.add(new HibernateScalarHelper("OPT_STATUES_CDE", Hibernate.STRING));
		scalarList.add(new HibernateScalarHelper("OPT_STATUES_CDE_NAM", Hibernate.STRING));
		scalarList.add(new HibernateScalarHelper("MEMB_PREPAID_VAL", Hibernate.STRING));
		scalarList.add(new HibernateScalarHelper("PREPAID_DATE", Hibernate.STRING));
		
		// 回傳
		return super.findBySQLQueryMapPagination(sbsql.toString(), pager, scalarList, value, userInfo);
	}



}
