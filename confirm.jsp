<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/include/include.Taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<base target="_self" />
<s:include value="/WEB-INF/pages/include/include.Scripts.jsp" />
<script type="text/javascript" src="<s:url value="/ddscPlugin/ddsc.gridList.plugin.js"/>"></script>
<script language="javascript">
var oTable;
$(document).ready(function() {
	oTable =$("#tblGrid").initGrid({height:'480'});

});
</script>
</head>
<body>
<s:form id="frmExam02001K" method="post" theme="simple" action="%{progAction}">
	<s:hidden name="labMemberMst.ver" />
	<div class="progTitle"> 
       <!-- 程式標題 --> <s:include value="/WEB-INF/pages/include/include.ConfirmTitle.jsp" /> <!-- 程式標題 -->
    </div>
    <div id="tb">
    <table width="100%" border="0" cellpadding="4" cellspacing="0" >
			<tr class="trBgOdd">
				<td width="20%" class="colNameAlign required">*<s:text name="membId" />：</td>
				<td width="30%">
					<s:property  value="labMemberMst.membId" />
					<s:hidden name="labMemberMst.membId"/></td>
				<td width="20%" class="colNameAlign required">*<s:text name="membName" />：</td>
				<td width="30%">
					<s:property value="labMemberMst.membName" />
					<s:hidden name="labMemberMst.membName"/></td>
			</tr>
			<tr class="trBgEven">
				<td width="20%" class="colNameAlign required">*<s:text name="membTel" />：</td>
				<td width="30%">
					<s:property  value="labMemberMst.membTel" />
					<s:hidden name="labMemberMst.membTel"/>
				</td>
				<td width="20%" class="colNameAlign required">*<s:text name="membBirth" />：</td>
				<td width="30%">
					<s:property  value="labMemberMst.membBirth" />
					<s:hidden name="labMemberMst.membBirth" />
				</td>
			</tr>
			<tr class="trBgOdd">
				<td width="20%" class="colNameAlign">&nbsp;<s:text name="membAddress" />：</td>
				<td colspan="3">
					<s:property value="labMemberMst.membAddress" />
					<s:hidden name="labMemberMst.membAddress"/>
				</td>
			</tr>
			<tr class="trBgEven">
				<td width="20%" class="colNameAlign">&nbsp;<s:text name="membEmail" />：</td>
				<td colspan="3">
					<s:property  value="labMemberMst.membEmail" />
					<s:hidden name="labMemberMst.membEmail"/>
				</td>
			</tr>
			<tr class="trBgOdd">
				<td width="20%" class="colNameAlign required">*<s:text name="membGrade" />：</td>
				<td width="30%">
					<span>
						<s:iterator value="membGradeList" status="status" var="obj">
							<input type="radio" id="membGrade_<s:property value="#status.index" />" name="labMemberMst.membGrade.optCde" value="<s:property value="optCde" />"
							<s:if test="labMemberMst.membGrade.optCde == optCde">checked</s:if> disabled />
							<s:property value="#obj.optCdeNam" />
						</s:iterator>
						<s:hidden name="labMemberMst.membGrade.optCde"  />
					</span>	
				</td>
				<td width="20%" class="colNameAlign required">*<s:text name="membStatus" />：</td>
				<td width="30%">
					<span>
						<s:iterator value="membStatuesList" status="status" var="obj">
							<input type="radio" id="membStatues_<s:property value="#status.index" />" name="labMemberMst.membStatus" value="<s:property value="optCde" />"
							<s:if test="labMemberMst.membStatus == optCde">checked</s:if> disabled />
							<s:property value="#obj.optCdeNam" />
						</s:iterator>
						<s:hidden name="labMemberMst.membStatus"  />
					</span>
				</td>
			</tr>
			<tr class="trBgEven">
			<td width="20%" class="colNameAlign">&nbsp;<s:text name="prepaidAmt" />：</td>
			<td width="30%">
				<s:property  value="labMemberMst.labMemberPrepaidHis[0].prepaidAmt" />
				<s:hidden name="labMemberMst.labMemberPrepaidHis[0].prepaidAmt"/>
			</td>
			<td width="20%" class="colNameAlign">&nbsp;<s:text name="membPrepaidVal" />：</td>
			<td width="30%">
				<s:label id="membPrepaidVal" name="labMemberMst.membPrepaidVal" />
				<s:hidden id="hiddmembPrepaidVal" name="labMemberMst.membPrepaidVal" />
			</td>
		</tr>
		</table>
    </div>
    <!-- 按鍵組合 -->
        <s:include value="/WEB-INF/pages/include/include.ConfirmButton.jsp" /> 
    <!-- 按鍵組合 -->
</s:form>
</body>
</html>