<td> 
�༭<a href="${ourl}/edit?id=${obj.id}"  id="sample_editable_1_new" class="btn btn-xs green dropdown-toggle"><i class="fa fa-edit"></i></a>
ɾ��<a href="${ourl}/goPlaySort?id=${obj.id}"  id="sample_editable_1_new" class="btn btn-xs red dropdown-toggle"><i class="fa fa-trash-o"></i></a>
�ϼ�<a href="${ourl}/modifyFz?id=${obj.id}&fz=0"  id="sample_editable_1_new" class="btn btn-xs green dropdown-toggle"><i class="glyphicon glyphicon-chevron-up">�ϼ�</i></a>
�¼�<a href="${ourl}/modifyFz?id=${obj.id}&fz=1"  id="sample_editable_1_new" class="btn btn-xs red dropdown-toggle"><i class="glyphicon glyphicon-chevron-down">�¼�</i></a>
<a href="${ourl}/changePersonalFz?id=${obj.id}&fz=${obj.fz}" id="sample_editable_1_new" class="<c:if test="${obj.fz==0}">btn btn-xs red</c:if>
<c:if test="${obj.fz==1}">btn btn-xs blue</c:if>  dropdown-toggle">
����<c:if test="${obj.fz==0}">����</c:if>
�ⶳ<c:if test="${obj.fz==1}">�ⶳ</c:if>
</a>
<a href="#" onclick="changeState('${obj.id}')" id="sample_editable_1_new" 
	class="<c:if test="${obj.state eq 0}">btn btn-xs red</c:if>
	<c:if test="${obj.state eq 1}">btn btn-xs blue</c:if>
	dropdown-toggle">
		<c:if test="${obj.state eq 0}">����</c:if>
		<c:if test="${obj.state eq 1}">�ⶳ</c:if>
</a>

	function changeState(id){
		$.ajax({  
	          type : "post",  
	          url :  "${ourl}/changeState",  
	          data : "id":id,
	          async : true,  
	          success : function(data){  
	        	  window.location.reload();
	           }  
	      	});
	}
	

</td>


<div class="btn-group">
<a  href="${ourl}/add" id="sample_editable_1_new" class="btn sbold green"><i class="fa fa-plus"> ����</i></a>
</div>
<div class="btn-group">
<a id="Odels" class="btn sbold red"><i class="fa fa-trash-o"> ����ɾ��</i></a>
</div>


ʱ��������

<form action="" id="sp">
	<div class="table-toolbar">
		  <div class="row">
			 <div class="col-md-14">
			  <div class="btn-group col-md-3">
				<label class="control-label">ѡ��ʱ�䷶Χ</label>
				 <input type="text" name="theTime" class="layui-input" value="${theTime}" placeholder="��ѡ��ʱ��" id="theTime">
			  </div>
			</div>
		</div>
	 </div>
 </form>
js:
//ʱ��ѡ����
//range����Ϊtrue��Ϊʱ�䷶Χ
layui.laydate.render({
	elem: '#theTime'
	,type: 'datetime'
	,range: true
}); 

��ť���ܰ�
<input disabled="true" />

������
<div class="form-group col-md-3">
<label class="form-label col-sm-4 " style="text-align:right">
</label>
<div class="col-md-8" >
<select name="type" data-live-search="true" class="selectpicker" data-width="100%">
<option value="0">����</option>
<option value="1">1</option>
<option value="2">2</option>
</select>
</div>
</div>
<div class="form-group col-md-3">
	<label class="form-label col-sm-4 " style="text-align:right">
		״̬
	</label>
	<div class="col-md-8" >
		<select name="state" data-live-search="true" class="selectpicker" data-width="100%">
			<option  value="0">ȫ��</option>
			<option <c:if test="${state eq 1 }">selected</c:if> value="1">����</option>
			<option <c:if test="${state eq 2 }">selected</c:if> value="2">�Ѷ���</option>
		</select>
	</div>
</div>
<input class="form-control" id="mask_date" type="text" value="<c:if test="${------------ == '0'}">δ֧��</c:if>" disabled="disabled"/> 

������������
<div class="form-group" id="role_form">
	<label class="control-label col-md-3">������ʦ</label>
	<div class="col-md-4">
		<select class="bs-select form-control" name="course.userId">
			<c:forEach items="${teacherList}" var="obj">
				<option value="${obj.id}" <c:if test="${obj.id eq course.userId }">selected</c:if> >${obj.nick}</option>
			</c:forEach>
		</select>
	</div>
</div>
Ĭ��ѡ���ĸ������� js
	var course.userId = '${course.userId}'.trim()==""?-1:'${course.userId}';
	$("select[name='course.userId']").selectpicker('val',course.userId);

������
<form action="" id="tp">
	<div class="form-group col-md-3">
		<label class="form-label col-sm-4 "  style="text-align:right">ѡ��ʱ�䷶Χ</label>
		<div class="col-md-8" >
			<input type="text" name="theTime" class="layui-input" value="${theTime}" placeholder="��ѡ��ʱ��" id="theTime">
		</div>
	</div>
</form>
��Ƶ��ǩ

	<video width="320" height="240" controls="controls">
        <source src="${ctx2}${anchor.videoUrl}">
    </video>

��ѡ��
<form action="" id="tf" style="margin-top: -15px;">
	<table class="table table-striped table-bordered table-hover table-checkable " id="sample_2">
		<thead>
			<tr>
				<th style="width: 40px;"><label
					class="mt-checkbox mt-checkbox-single mt-checkbox-outline">
						<input type="checkbox" class="group-checkable"
						data-set="#sample_1 .checkboxes" /> <span></span>
				</label></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="obj">
				<tr class="odd gradeX">
					<td>
						<label class="mt-checkbox mt-checkbox-single mt-checkbox-outline"> <input type="checkbox" class="checkboxes" name="ids" id="${obj.id}" value="${obj.id}" /> <span></span></label>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</form>
����js

		$(function(){
			var arry = "${specIds}";
			if(arry == null){
				return ;
			}
			if(arry.indexOf(",") == -1 ){
				$("input[id='"+arry+"']").attr("checked", true);
				return;
			}
			var list = arry.split(",");
			for(var i=0;i<list.length;i++){
				$("input[id='"+list[i]+"']").attr("checked", true);
			}
		});
		


	<div class="form-group">
		<label class="control-label col-md-3">��������</label>
		<div class="col-md-4">
			<label class="mt-radio">
				<input type="radio" name="course.type" id="optionsRadios1" value="0" <c:if test="${course.type == null || course.type eq 0}">checked</c:if>>����<span></span>
			</label>
			<label class="mt-radio">
				<input type="radio" name="course.type" id="optionsRadios2" value="1" <c:if test="${course.type eq 1}">checked</c:if>>���<span></span>
			</label>
		</div>
	</div>
	
	
	<div class="form-group">
		<label class="control-label col-md-3">�˺�</label>
		<div class="col-md-4">
			<input class="form-control" type="text" value="${account.mobile}" name="account.mobile" datatype="m" errormsg="�����ֻ�����ע�ᣡ" nullmsg="�˺Ų���Ϊ�գ�"/> <span class="Validform_checktip"></span>
		</div>
	</div>

js��ȡѡ���id
var ids = $("#tf").serialize();

��ʾ�б�
ͼƬ:<td><img src="<c:if test="${obj.avatar == null}">${ctx}/assets/metronic/global/img/no.png</c:if><c:if test="${obj.avatar != null}">${ctx}${obj.avatar}</c:if>" style="width: 50px;"/></td>
�ֶ�<td><c:out value="${obj.createTime}" default="" /></td>
ѡ����ʾ<td><c:if test="${obj.fz eq 0}">����</c:if><c:if test="${obj.fz eq 1}">����</c:if></td>
    
	<div class="form-group">
		<label class="control-label col-md-3">����ͼƬ</label>
		<div class="col-md-9">
			<div class="fileinput fileinput-new" data-provides="fileinput">
				<div class="fileinput-new thumbnail"
					style="width: 200px; height: 150px;">
					<img
						src="<c:if test="${matchModel.imgUrl eq null}">https://worldmodel.oss-cn-shenzhen.aliyuncs.com/upload/default/default.png</c:if><c:if test="${matchModel.imgUrl ne null}">${ctx2}${matchModel.imgUrl}</c:if> " />
				</div>
				<div class="fileinput-preview fileinput-exists thumbnail"
					style="max-width: 200px; max-height: 150px;"></div>
				<div>
					<span class="btn default btn-file"> <span
						class="fileinput-new">ѡ��ͼƬ </span> <span class="fileinput-exists">�ı�
					</span> <input type="file" name="matchModelImage">
					</span> <a href="javascript:;" class="btn red fileinput-exists"
						data-dismiss="fileinput"> ɾ�� </a>
				</div>
			</div>
			<div class="clearfix margin-top-10">
				<span class="label label-danger">NOTE!</span> ���� IE10+,
				FF3.6+,Safari6.0+, Chrome6.0+ and Opera11.1+.���ϵ������
			</div>
		</div>
	</div>                    																												

											<input class="form-control" type="text" value="${enName}" name="enName" placeholder="Ӣ�ı�������"/>
<small>�����������ڱ�ǩ��</small><br> 
<strong>�����������ڱ�ǩ��</strong><br>
<em>�����������ڱ�ǩ�ڣ�������Ϊб��</em><br>
<p class="text-left">��������ı�</p>
<p class="text-center">���ж����ı�</p>
<p class="text-right">���Ҷ����ı�</p>
<p class="text-muted">���������Ǽ�����</p> 
<p class="text-primary">�������ݴ���һ�� primary class</p>
<p class="text-success">�������ݴ���һ�� success class</p>
<p class="text-info">�������ݴ���һ�� info class</p> 
<p class="text-warning">�������ݴ���һ�� warning class</p>
<p class="text-danger">�������ݴ���һ�� danger class</p>

text-capitalize ����
text-center ����
text-danger �Ӻ�Σ��
text-hide ��������
text-info ��Ϣ
text-justify ������루���С���
text-left ���������
text-lowercase Сд����Ӣ�ģ�
text-muted ����
text-nowrap ������
text-primary ԭ��Ч��
text-right ���־���
text-success �ɹ�
text-uppercase ���ִ�д����Ӣ�ģ�
text-warning �����ɫ

��Ҫ��ʾ����ɫ�ǣ�
��ͻң�text-muted����
��Ҫ����text-primary����
�ɹ��̣�text-success����
��Ϣ����text-info����
����ƣ�text-warning����
Σ�պ죨text-danger��

input����Ϊ��
<form id="validform">
	<input  datatype="*1-200" />
	<span class="Validform_checktip"></span>
</form>

js:
<script src="${ctx}/assets/js/validform_min.js"></script>
$(function() {
	$("#validform").Validform({
		tiptype : 3
	});
});
<div class="table-toolbar pull-right">
										<button id="search" class="btn green"><i class="fa fa-search"> ����</i></button>
                                    </div>

�ļ��ϴ��ж�
	<div class="form-group">
		<label class="control-label col-md-3">�����ļ�</label>
		<div class="col-md-4"><!-- class="form-control" -->
			<input  class="file"  type="file" onchange="fileChange(this);"
				 name="course.recordUrl" datatype="*1-200"/> 
				<span class="Validform_checktip"></span>
		</div>
		<div class="col-md-4"  style="display: none" id="noChangeFile">
			<input value="��������Ƶ�ļ�" type="button"  class="btn default" onclick="deleteTrRow(this)">
		</div>
	</div>

function fileChange(target, id) {
	var fileSize = 0;
	var filepath = target.value;
	var filemaxsize = 1024 * 1024 * 1.9;//���ܵ��ļ����1.9G
	if (!target.files) {
	var filePath = target.value;
	var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
	if (!fileSystem.FileExists(filePath)) {
		alert("���������ڣ����������룡");
		return false;
	}
	var file = fileSystem.GetFile(filePath);
		fileSize = file.Size;
	} else {
		fileSize = target.files[0].size;
	}
	var size = fileSize / 1024;
	if (size > filemaxsize) {
		alert("�ļ���С���ܴ���" + filemaxsize / (1024*1024) + "G��");
		target.value = "";
		return false;
	}
	if (size <= 0) {
		alert("�ļ���С����Ϊ0M��");
		target.value = "";
		return false;
	}
}
$("#submitFrom").click(function(){
	alert("���ȷ�Ͻ����ϴ��ļ�,�����ĵȴ��ϴ�ʱ��!");
	$("#validform").submit();
	$("#submitFrom").attr('disabled',true);
});

//�Ƴ��ļ��ϴ�
function deleteTrRow(tr){
$(tr).parent().parent().remove();
};

�п�
empty topic

��������

 	 <div class="form-group">
        <div class="control-label col-md-3">
      		 ����
        </div>
        <div class="col-sm-3">
            <select  data-live-search="true" class="selectpicker" data-width="100%"  name="country" id="country">
                <option>��ѡ��</option>
            	<c:forEach items="${country}" var="obj">
					<option value="${obj.id}" <c:if test="${obj.id eq countryId }">selected</c:if> >${obj.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="col-sm-3">
            <select  data-live-search="true" class="selectpicker" data-width="100%"  name="province" id="province">
                <option>��ѡ��</option>
            	<c:forEach items="${province}" var="obj">
					<option value="${obj.id}" <c:if test="${obj.id eq provinceId }">selected</c:if> >${obj.name}</option>
                </c:forEach>       	
            </select>
        </div>       
        <div class="col-sm-3">
            <select data-live-search="true" class="selectpicker" data-width="100%"  name="city" id="city" dataType="/^(?!��ѡ��$)/" errormsg="��ѡ�������" nullmsg="��ѡ�����">
                <option>��ѡ��</option>
            	<c:forEach items="${city}" var="obj">
					<option value="${obj.id}" <c:if test="${obj.id eq cityId }">selected</c:if> >${obj.name}</option>
                </c:forEach>
            </select>
        </div>
    </div>
	
	
	
	��������Ϊ��ѡ��
	dataType="/^(?!��ѡ��$)/" errormsg="��ѡ�������" 
				//ˢ������������
	            $(".selectpicker" ).selectpicker('refresh');
	
	js����
	 $(function () {
	    //Ĭ�ϰ�ʡ
	    //ProviceBind();
	    //���¼�
	    $("#Province").change( function () {
	        CityBind();
	    })
	    $("#City").change(function () {
	        VillageBind();
	    })
	})
	function Bind(str) {
	    alert($("#Province").html());
	    $("#Province").val(str);
	}
	function CityBind() {
	    var provice = $("#Province").val();
	    //�ж�ʡ�����������ѡ�е�ֵ�Ƿ�Ϊ��
	    if (provice == "") {
	        return;
	    }
	    $("#City").html("");
	    var str = "<option>��ѡ��</option>";
	    $.ajax({
	        type: "POST",
	        url: "${ourl}/getSonArea",
	        data: {"areaId": provice},
	        dataType: "JSON",
	        async: false,
	        success: function (data) {
	            //�ӷ�������ȡ���ݽ��а�
	            $.each(data.sonArea, function (i, item) {
	                str += "<option value=" + item.id + ">" + item.name + "</option>";
	            })
	            //��������ӵ�ʡ���������������
	            $("#City").append(str);
	            $(".selectpicker" ).selectpicker('refresh');
	        },
	        error: function () { alert("Error"); }
	    });
	}
	
	
	
	function VillageBind() {
	    var provice = $("#City").val();
	    //�ж������������ѡ�е�ֵ�Ƿ�Ϊ��
	    if (provice == "") {
	        return;
	    }
	    $("#Village").html("");
	    var str = "<option>��ѡ��</option>";
	    //���е�ID�õ����ݿ���в�ѯ����ѯ�������¼����а�
	    $.ajax({
	        type: "POST",
	        url: "${ourl}/getSonArea",
	        data: { "areaId": provice},
	        dataType: "JSON",
	        async: false,
	        success: function (data) {
	            //�ӷ�������ȡ���ݽ��а�
	            $.each(data.sonArea, function (i, item) {
	                str += "<option value=" + item.Id + ">" + item.name + "</option>";
	            })
	            //��������ӵ�ʡ���������������
	            $("#Village").append(str);
	            $(".selectpicker" ).selectpicker('refresh');
	        },
	        error: function () { alert("Error"); }
	    });
	}	����Ϊ�ռ���ʾ�Ӳ����޸�
	
			����
			<input  readOnly class="form-control" type="text" value="${room.title}" name="room.title"  placeholder="yyyy-MM/dd" datatype="*1-100" nullmsg="ֱ���������Ʋ���Ϊ�գ�"/> <span class="Validform_checktip"></span>

			��̬У��
<input class="form-control" type="text" value="" name="mobile" datatype="*11-11" errormsg="������11λ���ֻ�����" ajaxurl="${ourl }/isExit" />

			
��̬��ȡ������
	<div class="form-group">
		<label class="control-label col-md-3">�����Ӧ�Ĺ��</label>
		<div class="col-md-7" id="skus">
		
		</div>
		<input type="button" class="btn green" id="addSku" value="�������">
	</div>
			//�������һ�����
	          $("#addSku").click(function(){
	        	//�ж����id����û������
	        	var skus = "";
	        	var num = 0;
        		$("#skus select").each(function(){
        			//����ǵڶ���ֵ������
        			if(num%2 == 0){
	        			skus +=$(this).val()+",";
        			}
        			num++;
        		});
  				var tables = $("#skus");
  				 $.ajax({
  			        type: "post",
  			        url: "${ourl}/getSku",
  			        data: { "skus":skus},
  		          	async : true,  
  			        success: function (data) {
  			        	if(!data.flag){
  			        		alert("����Ѿ��þ�,�������ǰ�����������");
  			        		return;
  			        	}
  			        	var sonStr="";
  			       		var str = "<div><div class='col-sm-10'><select name='sku' class='form-control'>";
  			        	$.each(data.specList, function (i, item) {
  			    	 		str+="<option value="+item.id+" >" + item.value + "</option>"
  			        	})
  			        	str+="</select></div>"
  			        	+"<div class='col-md-2'><input onclick='deleteSkuTrRow(this);'  class='btn sbold red' type='button' value='�Ƴ�'/></div></div>";
	  					var addtr = $(str);
	  		      		addtr.appendTo(tables); 
  			         }
  				 });
	          });
	        function deleteSkuTrRow(i){
	        	$(i).parent().parent().remove();
			}
			

datatype��ʹ��
����
<link href="${ctx}/assets/validform/validform.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/assets/validform/validform_min.js"></script> <!-- ע��Ҫ���뵽jq��js���� -->
����from��ǩ��id ,����:
id="validform"
����id����
	$(function() {
		$("#validform").Validform({
			tiptype : 3
		});
	})
		
			
<script>




//ҳ��ˢ��
window.location.reload();
//ɾ���ж�
if(confirm("ȷ��ɾ������Ϣ��?")){ 
};

$(obj).parents("tr").find("td").eq(6).html("����");
		//��֤ʧ��
		$("#authenticationFail").click(function(){
			var realNameId = $("#realNameId").val();
			var context = prompt("ȡ����֤����(���İ�)","��Ƭ������");
			if(context != null  && context.trim() != ""){
				var enContext = prompt("ȡ����֤����(Ӣ�İ�)","The picture is not clear");
				if( enContext!=null && enContext.trim() !=""){
					$.ajax({  
			          type : "post",  
			          url :  "${ourl}/authentication",  
			          data : {"context":context
			        	  ,"enContext":enContext
			        	  ,"state":2
			        	  ,"realNameId":realNameId},  
			          async : true,  
			          success : function(data){
			        	  if(data.flag){
			        		  alert("�����ɹ�");
			        	  }else{
			        		  alert("����ʧ��");
			        	  }
			        	  window.location.href="page";
			           }  
			      	});
				}else{
					alert("��������֤ʧ�ܵ���Ӣ����,����Ϊ��");
				}
			}else{
				alert("��������֤ʧ�ܵ�����,����Ϊ��");
			}
		});


 //ɾ����Ϣ
function deleteNotice(id){
	if(confirm("ȷ��ɾ������Ϣ��?")){ 
		$.ajax({  
			type : "post",  
			url :  "${ourl}/deleteNotice",  
			dataType: "json",
			data : {"id":id},  
			async : true,  
			success : function(data){
				if(data.flag){
					alert("ɾ���ɹ�!")
				}else{
					alert("ɾ��ʧ��!");
				}
				window.location.reload();
			}  
		});
	}
};

$("#Odels").click(function(event) {
	if(confirm("ȷ��ɾ����?")){ 
		var ids = $("#tf").serialize();
		if(ids==null || ids.length==0){
			alert("�빴ѡ���ٽ�������ɾ������");
			return;
		}
		$.ajax({  
		  type : "post",  
		  url :  "${ourl}/dels",  
		  data : ids,  
		  async : true,  
		  success : function(data){
			  if(data.flag == false){
				  alert("ɾ��ʧ��!");
			  }else{
				  alert("ɾ���ɹ�!");
			  }
			  window.location.reload();
		   }  
		});
	}
 });
 
 
			//�������һ�����дʸ�
			$("#add").click(function(){
				var tables = $("#sensitiveFrom");

				var addtr = $("<div class='form-body'>"
							+   "<div class='form-group'>"
							+		"<label class='control-label col-md-3'>���д�</label>"
							+		"<div class='col-md-4'>"
							+			"<input id='mask_date' type='text' name='work' value='${work}' datatype='*1-200'/>"
							+		"</div>"
							+		"<div class='col-md-2'>"
							+			"<input onclick='deleteTrRow(this);'  class='btn sbold red' id='mask_date' type='button' value='�Ƴ�'/>" 
							+		"</div>"
							+	 "</div>"
							+  "</div>");

		      	addtr.appendTo(tables); 
		      	
			});
//�Ƴ��ļ��ϴ�
function deleteTrRow(tr){
$(tr).parent().parent().remove();
};


$("#submit").click(function(){

			var formData = new FormData($("#form")[0]);
			$.ajax({
				type : "post",
				url : "#(curl)/synthesis",
		        async: false,  
		        cache: false,  
		        contentType: false,  
		        processData: false,
				data: formData,
				success : function(data) {
					//���϶�ά��
					 $("#qrcode").qrcode({
						width : 36, //���  
						height : 36, //�߶�  
						text : data.qrCode, //��������  
					});
					//������
					//var width = document.getElementById("canbox").offsetWidth; //���  
					//var height = document.getElementById("canbox").offsetHeight; // �߶�
					var width = 600; //���  
					var height = 600; // �߶�
					var c = document.getElementById("myCanvas");
					c.width = width;
					c.height = height;
					var ctx = c.getContext("2d");
					//���Ȼ��ϱ���ͼ
					var img = new Image();
					img.src = data.image;
					img.setAttribute("crossOrigin", 'Anonymous')
					var x_bot = height - 44 //��������
					ctx.font = "19px Georgia";

					function convertCanvasToImage(canvas) {
						var image = new Image();
						image.src = canvas.toDataURL(data.image);
						return image;
					}
					var mycans = $('canvas')[1];//��ά�����ڵ�canvas
					var codeimg = convertCanvasToImage(mycans);
					var xw = width - 72 - 29
					var xh = height - 6 - 72

					img.onload = function() { //����ȴ�ͼƬ�������
						ctx.drawImage(img, 0, 0, width, height); //����ͼ���������
						//ctx.fillText(data.image, 28, x_bot);
						var right = $("#right").val();
						var lower = $("#lower").val();
						var wide = $("#wide").val();
						var high = $("#high").val();
						if(!right){ right=xw; }
						if(!lower){ lower=xh; }
						if(!wide){ wide=72; }
						if(!high){ high=72; }
						ctx.drawImage(codeimg, right, lower, wide, high);
						//�������,תΪͼƬ
						setTimeout(function() { //��ios���޷��ڻ���֮��ȡ�������������ݣ����˸�settimeout
							var bigcan = $('canvas')[0];
							let images = new Image();
							images.src = bigcan.toDataURL(data.image);
							images.setAttribute("crossOrigin", 'Anonymous')
							$('.canimg').attr('src', bigcan.toDataURL(data.image))
						}, 0);
					}

				},
				error : function() {
					alert("����!");
				}

				});
			});

			
//����			
$("#hide").click(function(){
  $("p").hide();
});
//��ʾ
$("#show").click(function(){
  $("p").show();
});

//����
$("#add").click(function(){
		    // ��ȡ�����DIV 
		    var $div_obj = $("#pop-div"); 
		    // �������Ļ�߶� 
		    var windowWidth = $(window).width(); 
		    // �������Ļ���� 
		    var windowHeight = $(window).height(); 
		    // ȡ�ô���DIV�ĸ߶� 
		    var popupHeight = $div_obj.height(); 
		    // ȡ�ô���DIV�ĳ��� 
		    var popupWidth = $div_obj.width(); 
		     
		    // // ��Ӳ���ʾ���ֲ� 
		    $("<div id='bg' style='background:#000; color:#FFF' ></div>").width(windowWidth * 2) 
		        .height(windowHeight * 2).click(function() { 
		          //hideDiv(div_id); 
		        }).appendTo("body").fadeIn(200); 
		     
		    // ��ʾ������DIV 
		    var left = ((windowWidth/2) - (popupWidth/2));
		    var top = ((windowHeight/2) - (popupHeight/2));
		    $div_obj.css({ 
		      "position" : "absloute"
		    }).animate({ 
		      left : left, 
		      top : top, 
		      opacity : "show" 
		    }, "slow"); 
		     
		  }); 
		  /*���ص���DIV*/ 
		  function hideDiv() {
		    $("#bg").remove(); 
		    $("#pop-div").animate({ 
		      left : 0, 
		      top : 0, 
		      opacity : "hide" 
		    }, "slow"); 
		  }
		  
</script>
<!--����html����-->
<div id='pop-div' style="width: 600px;height:900px;background:#FFF"  class="pop-box">
	<form action=""  class="form-horizontal form-bordered"  enctype="multipart/form-data" method="post" id="lecturer">
		
		<input type="hidden" name="curriculum.id" value="${curriculum.id}" />
		<div class="form-body">
			<div class="form-group">
				<label class="control-label col-md-3">��ʦ���</label>
				<div class="col-md-4">
					<input class="form-control" type="text" value="${name}" name="name" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-3">ͷ��</label>
				<div class="col-md-9">
					<div class="fileinput fileinput-new" data-provides="fileinput">
						<div class="fileinput-new thumbnail"
							style="width: 200px; height: 150px;">
							<img
								src="<c:if test="${curriculum.cover == null}">${image}upload/motion/assets/metronic/global/img/no.png</c:if><c:if test="${curriculum.cover ne null}">${image}${curriculum.cover}</c:if> " />
						</div>
						<div class="fileinput-preview fileinput-exists thumbnail"
							style="max-width: 200px; max-height: 150px;"></div>
						<div>
							<span class="btn default btn-file"> <span
								class="fileinput-new">ѡ��ͼƬ </span> <span class="fileinput-exists">�ı�
							</span> <input type="file" name="head">
							</span> <a href="javascript:;" class="btn red fileinput-exists"
								data-dismiss="fileinput"> ɾ�� </a>
						</div>
					</div>
					<div class="clearfix margin-top-10">
						<span class="label label-danger">NOTE!</span> ���� IE10+,
						FF3.6+,Safari6.0+, Chrome6.0+ and Opera11.1+.���ϵ������
					</div>
				</div>
			</div>
			<div class="form-actions">
				<div class="row">
					<div class="col-md-offset-3 col-md-9">
						<button type="button" class="btn blue" id="lecturerSubmit">
							<i class="fa fa-check"></i> Submit
						</button>
						<button type="button" class="btn default" onclick="hideDiv()">�ر�</button>
					</div>
				</div>
			</div>
		</div>
	</form>
</div>