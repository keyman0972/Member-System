package com.ddsc.km.exam.service.impl;

import java.util.*;

import com.ddsc.core.entity.UserInfo;
import com.ddsc.core.exception.DdscApplicationException;
import com.ddsc.core.util.BeanUtilsHelper;
import com.ddsc.core.util.DateHelper;
import com.ddsc.core.util.Pager;
import com.ddsc.km.exam.dao.ILabMemberMstDao;
import com.ddsc.km.exam.dao.ILabMemberPrepaidHisDao;
import com.ddsc.km.exam.entity.LabMemberMst;
import com.ddsc.km.exam.entity.LabMemberPrepaidHis;
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

public class LabMemberMstServiceImpl implements ILabMemberMstService {
	
	private ILabMemberMstDao labMemberMstDao;
	private ILabMemberPrepaidHisDao labMemberPrepaidHisDao;

	@Override
	public LabMemberMst create(LabMemberMst entity, UserInfo info) throws DdscApplicationException {
		try{
			String dateHelper = new DateHelper().getSystemDate("/");
			
			labMemberMstDao.save(entity, info);
			String memberId= entity.getMembId();
			
			List<LabMemberPrepaidHis> labMemberPrepaidHisList = entity.getLabMemberPrepaidHis();
			if(labMemberPrepaidHisList != null && !labMemberPrepaidHisList.isEmpty()){
				for(LabMemberPrepaidHis labMemberPrepaidHis:labMemberPrepaidHisList){
					if(labMemberPrepaidHis.getPrepaidAmt() !=null && !labMemberPrepaidHis.getPrepaidAmt().equals(0)){
						labMemberPrepaidHis.setPrepaidDate(dateHelper);
						labMemberPrepaidHis.setMembId(memberId);
						labMemberPrepaidHisDao.save(labMemberPrepaidHis, info);					
						
					}
				}
			}
			return entity;
		}catch (DdscApplicationException e) {
			throw e;
		}catch (Exception e) {
			throw new DdscApplicationException(e, info);
		}
	}

	@Override
	public LabMemberMst update(LabMemberMst entity, UserInfo info) throws DdscApplicationException {
		try{
			LabMemberMst labMemberMstPo = this.labMemberMstDao.get(entity.getMembId(), info);
			if(entity.getVer().getTime() == labMemberMstPo.getVer().getTime()){
				
				String dateHelper = new DateHelper().getSystemDate("/");
				List<LabMemberPrepaidHis> labMemberPrepaidHisList = entity.getLabMemberPrepaidHis();
				if(labMemberPrepaidHisList != null && !labMemberPrepaidHisList.isEmpty()){					
					for(LabMemberPrepaidHis labMemberPrepaidHis:labMemberPrepaidHisList){
						if(labMemberPrepaidHis.getPrepaidAmt() != null && !labMemberPrepaidHis.getPrepaidAmt().equals(0)){
							
							labMemberPrepaidHis.setPrepaidDate(dateHelper);
							labMemberPrepaidHis.setMembId(entity.getMembId());
							
							labMemberPrepaidHisDao.save(labMemberPrepaidHis, info);		
						}
					}
				}
				
				BeanUtilsHelper.copyProperties(labMemberMstPo, entity, entity.obtainLocaleFieldNames());
				labMemberMstDao.update(labMemberMstPo, info);
				return labMemberMstPo;
			}else{
				throw new DdscApplicationException(DdscApplicationException.DDSCEXCEPTION_TYPE_ERROR, "eP.0013");
			}
		}catch (DdscApplicationException e) {
			throw e;
		}catch (Exception e) {
			throw new DdscApplicationException(e, info);
		}
	}

	@Override
	public LabMemberMst delete(LabMemberMst entity, UserInfo info) throws DdscApplicationException {
		try{
			LabMemberMst labMemberMstPo = this.labMemberMstDao.get(entity.getMembId(), info);
			if(entity.getVer().getTime() == labMemberMstPo.getVer().getTime()){
				
				BeanUtilsHelper.copyProperties(labMemberMstPo, entity, entity.obtainLocaleFieldNames());
				labMemberMstPo.setMembStatus("9");
				labMemberMstDao.update(labMemberMstPo, info);
				
				return entity;
				
			}else{
				throw new DdscApplicationException(DdscApplicationException.DDSCEXCEPTION_TYPE_ERROR,"eP.0013");
			}
		}catch (DdscApplicationException e) {
			throw e;
		}catch (Exception e) {
			throw new DdscApplicationException(e, info);
		}
	}
	
	@Override
	public LabMemberMst get(String id, UserInfo info) throws DdscApplicationException {
		try{
			LabMemberMst labMemberMst = this.labMemberMstDao.get(id, info);
			
			return labMemberMst;
		}catch (DdscApplicationException e) {
			throw e;
		}catch (Exception e) {
			throw new DdscApplicationException(e, info);
		}
	}
	
	@Override
	public Pager searchByConditions(Map<String, Object> conditions, Pager pager, UserInfo userInfo) throws DdscApplicationException {
		try{
			
			return labMemberMstDao.searchByConditions(conditions, pager, userInfo);
		}catch (DdscApplicationException e) {
			throw e;
		} catch (Exception e) {
			throw new DdscApplicationException(e, userInfo);
		}
	}
	
	@Deprecated
	@Override
	public List<LabMemberMst> searchAll(UserInfo info) throws DdscApplicationException {
		return null;
	}
	
	@Deprecated
	@Override
	public List<LabMemberMst> searchByConditions(Map<String, Object> conditions, UserInfo userInfo) throws DdscApplicationException {
		return null;
	}
	
	@Deprecated
	@Override
	public List<Object> queryDataByParamsByService(Map<String, Object> conditions, UserInfo userInfo) throws DdscApplicationException {
		return null;
	}

	@Override
	public int getDataRowCountByConditions(Map<String, Object> conditions, UserInfo info) throws DdscApplicationException {
		try{
			return 0;			
		}catch (DdscApplicationException e) {
			throw e;
		} catch (Exception e) {
			throw new DdscApplicationException(e, info);
		}
	}
	

	
	public ILabMemberMstDao getLabMemberMstDao() {
		return labMemberMstDao;
	}

	public void setLabMemberMstDao(ILabMemberMstDao labMemberMstDao) {
		this.labMemberMstDao = labMemberMstDao;
	}

	public ILabMemberPrepaidHisDao getLabMemberPrepaidHisDao() {
		return labMemberPrepaidHisDao;
	}

	public void setLabMemberPrepaidHisDao(ILabMemberPrepaidHisDao labMemberPrepaidHisDao) {
		this.labMemberPrepaidHisDao = labMemberPrepaidHisDao;
	}
}
