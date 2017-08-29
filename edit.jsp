<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/include/include.Taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<base target="_self" />
<s:include value="/WEB-INF/pages/include/include.Scripts.jsp" />
<script type="text/javascript" src="<s:url value="/ddscPlugin/ddsc.gridEditList.plugin.js"/>"></script>
<script type="text/javascript" src="<s:url value="/ddscPlugin/ddsc.validation.plugin.js"/>"></script>
<script type="text/javascript" src="<s:url value="/jquery/jquery.alphanumeric.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/ddsc.input.js"/>"></script>
<script type="text/javascript" src="<s:url value="/ddscPlugin/ddsc.date.plugin.js"/>"></script>
<script type="text/javascript" src="<s:url value="/jquery/ui/jquery.ui.datepicker.min.js"/>"></script>
<script language="javascript">
var oTable;
//畫面欄位檢核
function validate() {
	$("#frm02001K").validate("clearPrompt"); 
	
	$("#membId").validateRequired({fieldText:'<s:text name="membId" />'});
	$("#membName").validateRequired({fieldText:'<s:text name="membName" />'});
	$("#membTel").validateRequired({fieldText:'<s:text name="membTel" />'});
	$("#membBirth").validateRequired({fieldText:'<s:text name="membBirth" />'});
	$("#membEmail").validateMail({fieldText:'<s:text name="membEmail" />'});
	$("#membGrade0").validateRequired({fieldText:'<s:text name="membGrade" />'});
	$("#membStatues0").validateRequired({fieldText:'<s:text name="membStatus" />'});

	if($("#checkdisabled").prop("checked")){
		var prepaidAmt = $("#prepaidAmt").val();
		if(prepaidAmt!= "" && prepaidAmt%100 != 0){
			$("#prepaidAmt").validate("sendPrompt",{message:'<s:text name="exam.e0002" />'});
		}
	}
	<%-- --%>
    return $("#frm02001K").validate("showPromptWithErrors");
}
function AddAmt(){
	var prepaidAmt = $("#prepaidAmt").val().toNumber();//儲值金額
	var hiddmembPrepaidVal = $("#hiddmembPrepaidVal").val().toNumber();//總儲值金額
	var oldmembPrepaidVal = $("#oldmembPrepaidVal").val().toNumber();
	
	//如果原本輸入的值不是0時，則先減去原本的值
	if(oldmembPrepaidVal!=0){
		hiddmembPrepaidVal-=oldmembPrepaidVal;
	}
	//在增加新的值
	hiddmembPrepaidVal+=prepaidAmt;
	
	//計算完成放進總金額中
	$("#membPrepaidVal").text(hiddmembPrepaidVal);
	$("#hiddmembPrepaidVal").val(hiddmembPrepaidVal);
	//原本輸入值放入hidden暫存
	$("#oldmembPrepaidVal").val(prepaidAmt);
}
$(document).ready(function() {
	oTable = $('#tblGrid').initEditGrid({height:'480'});
	
	$("#checkdisabled").bind("click",function(){
		var prepaidAmt = $("#prepaidAmt").val().toNumber();
		var hiddmembPrepaidVal = $("#hiddmembPrepaidVal").val().toNumber();
		if($("#checkdisabled").prop("checked")){//判斷有打勾時
			//將disabled關掉
			$("#prepaidAmt").prop("disabled", false);
			//再將判斷是否儲值金額是否有值
			if(prepaidAmt!=0){
				//有值時加進總金額裡
				hiddmembPrepaidVal +=prepaidAmt;
				$("#membPrepaidVal").text(hiddmembPrepaidVal);
				$("#hiddmembPrepaidVal").val(hiddmembPrepaidVal);
			}
			$("#prepaidAmt").bind("change",AddAmt);
		}else{//沒有打勾時
			//判斷沒有disabled時，是否為0
			if(prepaidAmt != 0){
				//不是0時減回去
				hiddmembPrepaidVal-=prepaidAmt;
				$("#membPrepaidVal").text(hiddmembPrepaidVal);
				$("#hiddmembPrepaidVal").val(hiddmembPrepaidVal);
			}
			$("#prepaidAmt").prop("disabled", true);
		}
	});
	if($("#hiddmembPrepaidVal").val().toNumber() ==0){
		$("#membPrepaidVal").text(0);
		$("#hiddmembPrepaidVal").val(0);
	}

});
</script>
</head>
<body>
<s:form id="frm02001K" method="post" theme="simple" action="%{progAction}" target="ifrConfirm">
<s:hidden name="labMemberMst.ver" />
 	<div class="progTitle"> 
		<!-- 程式標題 --> <s:include value="/WEB-INF/pages/include/include.EditTitle.jsp" /> <!-- 程式標題 -->
    </div>
    <div id="tb">
    <table width="100%" border="0" cellpadding="4" cellspacing="0" >
		<tr class="trBgOdd">
			<td width="20%" class="colNameAlign required">*<s:text name="membId" />：</td>
			<td width="30%">
				<s:textfield id="membId" name="labMemberMst.membId" maxlength="16" size="20" cssClass="enKey" />
			</td>
			<td width="20%" class="colNameAlign required">*<s:text name="membName" />：</td>
			<td width="30%">
				<s:textfield id="membName" name="labMemberMst.membName" maxlength="64" size="32" />
			</td>
		</tr>
		<tr class="trBgEven">
			<td width="20%" class="colNameAlign required">*<s:text name="membTel" />：</td>
			<td width="30%">
				<s:textfield id="membTel" name="labMemberMst.membTel" maxlength="20" size="20" cssClass="numKey" />
			</td>
			<td width="20%" class="colNameAlign required">*<s:text name="membBirth" />：</td>
			<td width="30%">
				<s:textfield id="membBirth" name="labMemberMst.membBirth" maxlength="10" size="16" cssClass="inputDate" />
			</td>
		</tr>
		<tr class="trBgOdd">
			<td width="20%" class="colNameAlign">&nbsp;<s:text name="membAddress" />：</td>
			<td colspan="3">
				<s:textfield id="membAddress" name="labMemberMst.membAddress"  maxlength="256" size="50" />
			</td>
		</tr>
		<tr class="trBgEven">
			<td width="20%" class="colNameAlign">&nbsp;<s:text name="membEmail" />：</td>
			<td colspan="3">
				<s:textfield id="membEmail" name="labMemberMst.membEmail"  maxlength="64" size="50" />
			</td>
		</tr>
		<tr class="trBgOdd">
			<td width="20%" class="colNameAlign required">*<s:text name="membGrade" />：</td>
			<td width="30%">
				<span>
					<s:iterator value="membGradeList" status="status" var="obj">
						<input type="radio" id="membGrade<s:property value="#status.index" />" name="labMemberMst.membGrade.optCde" value="<s:property value="optCde" />"
						<s:if test="labMemberMst.membGrade.optCde == optCde">checked</s:if> />
						<s:property value="#obj.optCdeNam" />
					</s:iterator>
				</span>
			</td>
			<td width="20%" class="colNameAlign required">*<s:text name="membStatus" />：</td>
			<td width="30%">
				<span>
					<s:iterator value="membStatuesList" status="status" var="obj">
						<input type="radio" id="membStatues<s:property value="#status.index" />" name="labMemberMst.membStatus" value="<s:property value="optCde" />"
						<s:if test="labMemberMst.membStatus == optCde">checked</s:if> />
						<s:property value="#obj.optCdeNam" />
					</s:iterator>
				</span>
			</td>
		</tr>
		<tr class="trBgEven">
			<td width="20%" class="colNameAlign">&nbsp;<s:text name="prepaidAmt" />：</td>
			<td width="30%">
				<input type="checkbox" id="checkdisabled">
				<s:textfield id="prepaidAmt" name="labMemberMst.labMemberPrepaidHis[0].prepaidAmt" maxlength="8" size="20"  cssClass="numKey" disabled="true" />
				
			</td>
			<td width="20%" class="colNameAlign">&nbsp;<s:text name="membPrepaidVal" />：</td>
			<td width="30%">
				<s:label id="membPrepaidVal" value="%{labMemberMst.membPrepaidVal}" />
				<s:hidden id="hiddmembPrepaidVal" name="labMemberMst.membPrepaidVal" />
				<s:hidden id="oldmembPrepaidVal" disabled="true" />
			</td>
		</tr>
	</table>
    </div>
	<!-- 按鍵組合 --> 
	<s:include value="/WEB-INF/pages/include/include.EditButton.jsp" />
	<!-- 按鍵組合 -->
</s:form>
<iframe id="ifrConfirm" name="ifrConfirm" width="100%" height="768" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" style="display:none; border: 0px none"></iframe>
</body>
</html>