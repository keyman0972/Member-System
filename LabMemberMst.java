package com.ddsc.km.exam.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ddsc.common.comm.entity.CommOptCde;
import com.ddsc.core.entity.BaseEntity;

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

@Entity
@Table(name="LAB_MEMBER_MST")
@NamedQuery(name="findLabMemberMst", query="select lmm.membId, lmm.membName, lmm.membPrepaidVal from LabMemberMst lmm where lmm.membId=:membId")
public class LabMemberMst extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 8199649647828062226L;
	
	private String membId;
	private String membName;
	private String membTel;
	private String membAddress;
	private String membEmail;
	private String membBirth;
	private CommOptCde membGrade;
	private String membStatus;
	private BigDecimal membPrepaidVal;
	private BigDecimal membOverdueTimes;
	//儲值紀錄檔
	private List<LabMemberPrepaidHis> labMemberPrepaidHis;
	//租借作業檔
	private List<LabMembRentHis> labMembRentHisList;
	//租借歸還比數
	private String labMembRentHisCount;
	@Id
	@Column(name="MEMB_ID")
	public String getMembId() {
		return membId;
	}
	
	public void setMembId(String membId) {
		this.membId = membId;
	}
	
	@Column(name="MEMB_NAME")
	public String getMembName() {
		return membName;
	}
	
	public void setMembName(String membName) {
		this.membName = membName;
	}
	
	@Column(name="MEMB_TEL")
	public String getMembTel() {
		return membTel;
	}
	
	public void setMembTel(String membTel) {
		this.membTel = membTel;
	}
	
	@Column(name="MEMB_ADDRESS")
	public String getMembAddress() {
		return membAddress;
	}
	
	public void setMembAddress(String membAddress) {
		this.membAddress = membAddress;
	}
	
	@Column(name="MEMB_EMAIL")
	public String getMembEmail() {
		return membEmail;
	}

	public void setMembEmail(String membEmail) {
		this.membEmail = membEmail;
	}
	
	@Column(name="MEMB_BIRTH")
	public String getMembBirth() {
		return membBirth;
	}

	public void setMembBirth(String membBirth) {
		this.membBirth = membBirth;
	}
	
	@OneToOne(targetEntity = CommOptCde.class, fetch = FetchType.EAGER)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "MEMB_GRADE", referencedColumnName = "OPT_CDE_OID")
	public CommOptCde getMembGrade() {
		return membGrade;
	}

	public void setMembGrade(CommOptCde membGrade) {
		this.membGrade = membGrade;
	}
	
	@Column(name="MEMB_STATUS")
	public String getMembStatus() {
		return membStatus;
	}
	
	public void setMembStatus(String membStatus) {
		this.membStatus = membStatus;
	}
	
	@Column(name="MEMB_PREPAID_VAL")
	public BigDecimal getMembPrepaidVal() {
		return membPrepaidVal;
	}
	
	public void setMembPrepaidVal(BigDecimal membPrepaidVal) {
		this.membPrepaidVal = membPrepaidVal;
	}
	
	@Column(name="MEMB_OVERDUE_TIMES")
	public BigDecimal getMembOverdueTimes() {
		return membOverdueTimes;
	}
	
	public void setMembOverdueTimes(BigDecimal membOverdueTimes) {
		this.membOverdueTimes = membOverdueTimes;
	}
	
	@Transient
	public List<LabMemberPrepaidHis> getLabMemberPrepaidHis() {
		return labMemberPrepaidHis;
	}

	public void setLabMemberPrepaidHis(List<LabMemberPrepaidHis> labMemberPrepaidHis) {
		this.labMemberPrepaidHis = labMemberPrepaidHis;
	}
	
	@Transient
	public List<LabMembRentHis> getLabMembRentHisList() {
		return labMembRentHisList;
	}

	public void setLabMembRentHisList(List<LabMembRentHis> labMembRentHisList) {
		this.labMembRentHisList = labMembRentHisList;
	}
	
	@Transient
	public String getLabMembRentHisCount() {
		return labMembRentHisCount;
	}

	public void setLabMembRentHisCount(String labMembRentHisCount) {
		this.labMembRentHisCount = labMembRentHisCount;
	}
}
