package com.ddsc.km.exam.action;

import java.util.*;

import org.apache.commons.lang3.StringUtils;

import com.ddsc.common.comm.entity.CommOptCde;
import com.ddsc.common.comm.service.ICommOptCdeService;
import com.ddsc.core.action.AbstractAction;
import com.ddsc.core.action.IBaseAction;
import com.ddsc.core.exception.DdscApplicationException;
import com.ddsc.core.exception.DdscAuthException;
import com.ddsc.core.util.Pager;
import com.ddsc.km.exam.entity.LabMemberMst;
import com.ddsc.km.exam.service.ILabMemberMstService;

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

public class Exam02001KAction extends AbstractAction implements IBaseAction {
	
	private static final long serialVersionUID = 5230048970635014400L;
	
	private ILabMemberMstService labMemberMstService;
	private List<LabMemberMst> labMemberMstList;
	private LabMemberMst labMemberMst;	
	
	private ICommOptCdeService commOptCdeService;
	private List<CommOptCde> membStatuesList;			//list.jsp 動態產生 "會員狀態"
	private List<CommOptCde> membGradeList;				//edit.jsp 動態產生 "會員等級"
	private List<String> statusList;
	
	@Override
	public String init() throws Exception {
		try {
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()),e.getMsgFullMessage() }));
		}
		setNextAction(ACTION_SEARCH);
		return SUCCESS;
	}

	@Deprecated
	@Override
	public String approve() throws Exception {
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String search() throws Exception {
		try {
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("memberId", labMemberMst.getMembId());
			conditions.put("memberName", labMemberMst.getMembName());
			conditions.put("memberTel", labMemberMst.getMembTel());
			
			if(statusList!= null){
				
				List<String> alist= new ArrayList<String>();
				alist.addAll(statusList);
				alist.removeAll(Collections.singleton(null));
				conditions.put("membereStatues", alist);
			}
			
			//主檔資料
			Pager resultPager = getLabMemberMstService().searchByConditions(conditions, getPager(), this.getUserInfo());
			
			labMemberMstList = (List<LabMemberMst>) resultPager.getData();
			
			setPager(resultPager);
			if (labMemberMstList == null || labMemberMstList.size() <= 0) {
				this.addActionError(this.getText("w.0001"));
			}
		} catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()),e.getMsgFullMessage() }));
		} catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()),e.getMsgFullMessage() }));
		}
		setNextAction(ACTION_SEARCH);
		return SUCCESS;
	}
	
	@Override
	public String query() throws Exception {
		try{
			labMemberMst = getLabMemberMstService().get(labMemberMst.getMembId(), this.getUserInfo());
		} catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()),e.getMsgFullMessage() }));
		} catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()),e.getMsgFullMessage() }));
		}
		setNextAction(ACTION_QUERY);
		return SUCCESS;
	}
	
	@Override
	public String create() throws Exception {
		try{
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
		setNextAction(ACTION_CREATE_SUBMIT);
		return SUCCESS;
	}

	@Override
	public String createSubmit() throws Exception {
		try {
			if (this.hasConfirm() == true) {
				setNextAction(ACTION_CREATE_CONFIRM);
				return RESULT_CONFIRM;
			}else {
				return this.createConfirm();
			}
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			return RESULT_EDIT;
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			return RESULT_EDIT;
		}
	}

	@Override
	public String createConfirm() throws Exception {
		try{
			labMemberMst = getLabMemberMstService().create(labMemberMst, getUserInfo());
			setNextAction(ACTION_CREATE);
			return RESULT_SHOW;
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_CREATE_SUBMIT);
			return RESULT_EDIT;
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_CREATE_SUBMIT);
			return RESULT_EDIT;
		}
	}

	@Override
	public String update() throws Exception {
		try{
			labMemberMst = getLabMemberMstService().get(labMemberMst.getMembId(), this.getUserInfo());
			
			setNextAction(ACTION_UPDATE_SUBMIT);
			return SUCCESS;
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			return RESULT_EDIT;
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			return RESULT_EDIT;
		}
	}

	@Override
	public String updateSubmit() throws Exception {
		try {
			if (hasConfirm()) {
				setNextAction(ACTION_UPDATE_CONFIRM);
				return RESULT_CONFIRM;
			}else {
				return this.updateConfirm();
			}
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_UPDATE_SUBMIT);
			return RESULT_EDIT;
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_UPDATE_SUBMIT);
			return RESULT_EDIT;
		}
	}

	@Override
	public String updateConfirm() throws Exception {
		try{
			labMemberMst = getLabMemberMstService().update(labMemberMst, getUserInfo());
			setNextAction(ACTION_UPDATE);
			return RESULT_SHOW;
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_UPDATE_SUBMIT);
			return RESULT_EDIT;
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_UPDATE_SUBMIT);
			return RESULT_EDIT;
		}
	}

	@Override
	public String delete() throws Exception {
		try{
			labMemberMst = getLabMemberMstService().get(labMemberMst.getMembId(),  this.getUserInfo());
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
		setNextAction(ACTION_DELETE_CONFIRM);
		return SUCCESS;
	}

	@Override
	public String deleteConfirm() throws Exception {
		try {
			labMemberMst = getLabMemberMstService().delete(labMemberMst, this.getUserInfo());
			setNextAction(ACTION_DELETE);
			return RESULT_SHOW;
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_DELETE_CONFIRM);
			return RESULT_CONFIRM;
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_DELETE_CONFIRM);
			return RESULT_CONFIRM;
		}
	}
	
	@Override
	public String copy() throws Exception {
		try{
			labMemberMst = getLabMemberMstService().get(labMemberMst.getMembId(), this.getUserInfo());
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
		setNextAction(ACTION_COPY_SUBMIT);
		return SUCCESS;
	}

	@Override
	public String copySubmit() throws Exception {
		try {
			if (this.hasConfirm() == true) {
				// 有確認頁
				setNextAction(ACTION_COPY_CONFIRM);
				return RESULT_CONFIRM;
			}
			else {
				// 沒有確認頁, 直接存檔
				return this.copyConfirm();
			}
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_COPY_SUBMIT);
			return RESULT_EDIT;
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_COPY_SUBMIT);
			return SUCCESS;
		}
	}

	@Override
	public String copyConfirm() throws Exception {
		try{
			labMemberMst = this.getLabMemberMstService().create(labMemberMst, this.getUserInfo());
			
			setNextAction(ACTION_COPY);
			return RESULT_SHOW;
		}catch (DdscApplicationException e) {
			// 取得 SQL 錯誤碼，並依多國語系設定顯示於Message box
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_COPY_SUBMIT);
			return RESULT_EDIT;
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_COPY_SUBMIT);
			return RESULT_EDIT;
		}
	}
	
	@Override
	public void validate() {
		try {
			setUpInfo();
		}catch (DdscApplicationException e) {
			// 取得 SQL 錯誤碼，並依多國語系設定顯示於Message box
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}
	}
	
	/**
	 * 檢核 - 按送出鈕(新增頁)
	 */
	public void validateCreateSubmit() {
		try {
			this.checkPrimaryKey();
			this.checkValidateRule();
		}
		catch (DdscAuthException e) {
			throw e;
		}
		catch (DdscApplicationException e) {
			// 取得 SQL 錯誤碼，並依多國語系設定顯示於Message box
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
		catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
	}

	/**
	 * 檢核 - 按確定鈕(新增頁)
	 */
	public void validateCreateConfirm() {
		// 先執行Action所對應的 validate, 再執行 validate(). (即 validateCreateSubmit 執行完後, 再執行 validate())
		try {
			this.checkPrimaryKey();
			this.checkValidateRule();
		}
		catch (DdscAuthException e) {
			throw e;
		}
		catch (DdscApplicationException e) {
			// 取得 SQL 錯誤碼，並依多國語系設定顯示於Message box
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
		catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
	}

	/**
	 * 檢核 - 按送出鈕(新增頁)
	 */
	public void validateUpdateSubmit() {
		try {
			this.checkValidateRule();
		}
		catch (DdscAuthException e) {
			throw e;
		}
		catch (DdscApplicationException e) {
			// 取得 SQL 錯誤碼，並依多國語系設定顯示於Message box
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
		catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
	}

	/**
	 * 檢核 - 按確定鈕(新增頁)
	 */
	public void validateUpdateConfirm() {
		// 先執行Action所對應的 validate, 再執行 validate(). (即 validateCreateSubmit 執行完後, 再執行 validate())
		try {
			this.checkValidateRule();
		}
		catch (DdscAuthException e) {
			throw e;
		}
		catch (DdscApplicationException e) {
			// 取得 SQL 錯誤碼，並依多國語系設定顯示於Message box
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
		catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
	}
	
	/**
	 * 檢核 - 按確定鈕(刪除頁)
	 */
	public void validateDeleteConfirm() {
		try {
			this.checkValidateRule();
			
		}
		catch (DdscAuthException e) {
			throw e;
		}
		catch (DdscApplicationException e) {
			// 取得 SQL 錯誤碼，並依多國語系設定顯示於Message box
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}
		catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}
	}

	/**
	 * 檢核 - 按送出鈕(複製頁)
	 */
	public void validateCopySubmit() {
		// 先執行Action所對應的 validate, 再執行 validate(). (即 validateCreateSubmit 執行完後, 再執行 validate())
		try {
			this.checkPrimaryKey();
			this.checkValidateRule();
		}
		catch (DdscAuthException e) {
			throw e;
		}
		catch (DdscApplicationException e) {
			// 取得 SQL 錯誤碼，並依多國語系設定顯示於Message box
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
		catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
	}

	/**
	 * 檢核 - 按確定鈕(複製頁)
	 */
	public void validateCopyConfirm() {
		// 先執行Action所對應的 validate, 再執行 validate(). (即 validateCreateSubmit 執行完後, 再執行 validate())
		try {
			this.checkPrimaryKey();
			this.checkValidateRule();
		}
		catch (DdscAuthException e) {
			throw e;
		}
		catch (DdscApplicationException e) {
			// 取得 SQL 錯誤碼，並依多國語系設定顯示於Message box
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
		catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
	}

	/**
	 * 檢核ID是否重複
	 *
	 * @return
	 * @throws Exception
	 */
	private boolean checkPrimaryKey() throws Exception {
		boolean isValid = true;
		
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("membId",labMemberMst.getMembId());
		if (labMemberMstService.getDataRowCountByConditions(conditions, this.getUserInfo()) > 0) {
			this.addFieldError("membId", this.getText("membId") + this.getText("eP.0004"));
			isValid = false;
		}
		return isValid;
	}

	/**
	 * 資料檢核
	 * 
	 * @return
	 * @throws Exception
	 */
	private boolean checkValidateRule() throws Exception {
		boolean isValid = true;
		try {
			// 檢查參數代碼是否存在 (會員等級)
			if (null != labMemberMst.getMembGrade().getOptCde() && StringUtils.isNotEmpty(labMemberMst.getMembGrade().getOptCde())) {
				CommOptCde membGrade = getCommOptCdeService().getByKey("F0003", labMemberMst.getMembGrade().getOptCde(), this.getUserInfo());
				if (membGrade == null) {
					this.addFieldError("membGrade", this.getText("membGrade")+ this.getText("eP.0003"));
					isValid = false;
				}else{
					labMemberMst.setMembGrade(membGrade);
				}
			}
			
			// 檢查參數代碼是否存在 (會員等級)
			if (null != labMemberMst.getMembStatus() && StringUtils.isNotEmpty(labMemberMst.getMembStatus())) {
				CommOptCde membStatues = getCommOptCdeService().getByKey("F0004", labMemberMst.getMembStatus(), this.getUserInfo());
				if (membStatues == null) {
					this.addFieldError("membStatues", this.getText("membStatues")+ this.getText("eP.0003"));
					isValid = false;
				}else{
					labMemberMst.setMembStatus(membStatues.getOptCde());
				}
			}
			
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
		return isValid;
	}

	public ILabMemberMstService getLabMemberMstService() {
		return labMemberMstService;
	}

	public void setLabMemberMstService(ILabMemberMstService labMemberMstService) {
		this.labMemberMstService = labMemberMstService;
	}

	public List<LabMemberMst> getLabMemberMstList() {
		return labMemberMstList;
	}

	public void setLabMemberMstList(List<LabMemberMst> labMemberMstList) {
		this.labMemberMstList = labMemberMstList;
	}

	public LabMemberMst getLabMemberMst() {
		return labMemberMst;
	}

	public void setLabMemberMst(LabMemberMst labMemberMst) {
		this.labMemberMst = labMemberMst;
	}

	public ICommOptCdeService getCommOptCdeService() {
		return commOptCdeService;
	}

	public void setCommOptCdeService(ICommOptCdeService commOptCdeService) {
		this.commOptCdeService = commOptCdeService;
	}

	public List<CommOptCde> getMembStatuesList() {
		if(membStatuesList == null){
			this.setMembStatuesList(this.getCommOptCdeService().getList("F0004", this.getUserInfo()));
		}
		return membStatuesList;
	}

	public void setMembStatuesList(List<CommOptCde> membStatuesList) {
		this.membStatuesList = membStatuesList;
	}

	public List<CommOptCde> getMembGradeList() {
		if(membGradeList == null){
			this.setMembGradeList(this.getCommOptCdeService().getList("F0003", this.getUserInfo()));
		}
		return membGradeList;
	}

	public void setMembGradeList(List<CommOptCde> membGradeList) {
		this.membGradeList = membGradeList;
	}

	public List<String> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}
}
