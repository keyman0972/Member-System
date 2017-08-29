<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/include/include.Taglib.jsp"%>
<html>
<head>
<title></title>
<s:include value="/WEB-INF/pages/include/include.Scripts.jsp" />
<script type="text/javascript" src="<s:url value="/jquery/ui/jquery.ui.datepicker.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/jquery/jquery.alphanumeric.js"/>"></script>
<script type="text/javascript" src="<s:url value="/ddscPlugin/ddsc.gridList.plugin.js"/>"></script>
<script type="text/javascript" src="<s:url value="/ddscPlugin/ddsc.popupWindow.plugin.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/ddsc.input.js"/>"></script>
<script type="text/javascript" src="<s:url value="/ddscPlugin/ddsc.validation.plugin.js"/>"></script>
<script type="text/javascript">
function getParameter(actType) {
	
	if(actType.startsWith('delete')){
		var hiddmembPrepaidVal= $("#hiddmembPrepaidVal").val();
		if(hiddmembPrepaidVal>0){
			alert('<s:text name="exam.e0001"><s:param value="getText(\"membPrepaidVal\")"/></s:text>');
			return;
		}
	}
	
	var param = "labMemberMst.membId=" + $("#tblGrid").getSelectedRow().find('td').eq(2).text();
	return param;
}
$(document).ready(function() {
	$("#tblGrid").initGrid({lines:3});
	$('#tb').initPopupWindow({dailogWidth:'960', dailogHeight:'640'});
	for(var i=0;i<$("#tblGrid tr").length;i++){
		if($("#tblGrid tr").eq(i).find("#membStatus").val() == "9"){
			$("#tblGrid tr").eq(i).find("input.imgCopyAct").hide();
			$("#tblGrid tr").eq(i).find("input.imgUpdAct").hide();
			$("#tblGrid tr").eq(i).find("input.imgDelAct").hide();
		}

	}
	
});
</script>
</head>
<body> 
<s:form id="frm02001K" theme="simple" action="%{progAction}" >
	<div class="progTitle">
  		<s:include value="/WEB-INF/pages/include/include.Title.jsp" />
	</div>
	<div id="tb">
		<fieldset id="listFieldset">
		<table width="100%" border="0" cellpadding="2" cellspacing="0">
			<tr class="trBgOdd">
				<td width="20%" class="colNameAlign">&nbsp;<s:text name="membId"/>：</td>
				<td width="30%"><s:textfield name="labMemberMst.membId" cssClass="enKey" maxlength="16" size="16"/></td>
				<td width="20%" class="colNameAlign">&nbsp;<s:text name="membName"/>：</td>
				<td width="30%"><s:textfield name="labMemberMst.membName" maxlength="64" size="32"/></td>
			</tr>
			<tr class="trBgEven">
				<td width="20%" class="colNameAlign">&nbsp;<s:text name="membTel"/>：</td>
				<td width="30%"><s:textfield name="labMemberMst.membTel" maxlength="20" size="32"/></td>
				<td width="20%" class="colNameAlign">&nbsp;<s:text name="membStatus"/>：</td>
				<td width="30%">
					<s:iterator value="membStatuesList" status="status" var="obj">
						<input type="checkbox" id="membGrade_<s:property value="#status.index" />" 
						name="statusList[<s:property value="#status.index" />]" 
						value="<s:property value="optCde" />"
						<s:if test="statusList[#status.index] == optCde">checked</s:if> />
						<s:property value="#obj.optCdeNam" />
					</s:iterator>
				</td>
			</tr>
		</table>
		<!-- 按鍵組合 --><s:include value="/WEB-INF/pages/include/include.ListButton.jsp" /><!-- 按鍵組合 --> 
		</fieldset>
		<table id="tblGrid" width="100%" border="0" cellpadding="2" cellspacing="1">
			<thead>
				<tr align="center" bgcolor="#e3e3e3">
					<th width="3%"><s:text name="fix.00164" /></th>
					<th width="10%"><s:text name="fix.00090" /></th>
					<th width="12%"><s:text name="membId" /></th>   
					<th width="12%"><s:text name="membName" /></th> 
					<th width="12%"><s:text name="membTel" /></th>
					<th width="12%"><s:text name="membGrade" /></th>
					<th width="12%"><s:text name="membStatus" /></th>
					<th width="12%"><s:text name="membPrepaidVal" /></th>
					<th><s:text name="exam.00001" /></th>
				</tr>
			 </thead>
			 <tbody>
				 <s:iterator value="labMemberMstList" status="status" var="obj1">
				 	<tr>
						<td width="3%" id="sn" align="center"><s:property value="#status.index+1" /></td>
						<!-- 表單按鍵 --> 
						<td width="10%"><s:include value="/WEB-INF/pages/include/include.actionButton.jsp" /></td>
						<!-- 表單按鍵 -->
						<td width="12%"><label><s:property value="MEMB_ID" /></label></td>
						<td width="12%"><label><s:property value="MEMB_NAME" /></label></td>
						<td width="12%"><label><s:property value="MEMB_TEL" /></label></td>	
						<td width="12%">
							<label><s:property value="OPT_GRADE_CDE" /></label>&nbsp;-&nbsp;
							<label><s:property value="OPT_GRADE_CDE_NAM" /></label>
						</td>
						<td width="12%">
							<label><s:property value="OPT_STATUES_CDE" /></label>&nbsp;-&nbsp;
							<label><s:property value="OPT_STATUES_CDE_NAM" /></label>
							<s:hidden id="membStatus" value="%{OPT_STATUES_CDE}" disabled="true" />
						</td>
						<td width="12%" align="right">
							<label>
								<s:property value="MEMB_PREPAID_VAL" />
								<s:hidden id="hiddmembPrepaidVal" value="%{MEMB_PREPAID_VAL}" disabled="true" />
							</label>
						</td>
						<td align="center"><label><s:property value="PREPAID_DATE" /></label></td>
					</tr>
				 </s:iterator>
			 </tbody>
		</table>
	</div>
	<!-- 分頁按鍵列 --><s:include value="/WEB-INF/pages/include/include.PaginationBar.jsp" /><!-- 分頁按鍵列 -->
</s:form>
</body>
</html>