<td> 
编辑<a href="${ourl}/edit?id=${obj.id}"  id="sample_editable_1_new" class="btn btn-xs green dropdown-toggle"><i class="fa fa-edit"></i></a>
删除<a href="${ourl}/goPlaySort?id=${obj.id}"  id="sample_editable_1_new" class="btn btn-xs red dropdown-toggle"><i class="fa fa-trash-o"></i></a>
上架<a href="${ourl}/modifyFz?id=${obj.id}&fz=0"  id="sample_editable_1_new" class="btn btn-xs green dropdown-toggle"><i class="glyphicon glyphicon-chevron-up">上架</i></a>
下架<a href="${ourl}/modifyFz?id=${obj.id}&fz=1"  id="sample_editable_1_new" class="btn btn-xs red dropdown-toggle"><i class="glyphicon glyphicon-chevron-down">下架</i></a>
<a href="${ourl}/changePersonalFz?id=${obj.id}&fz=${obj.fz}" id="sample_editable_1_new" class="<c:if test="${obj.fz==0}">btn btn-xs red</c:if>
<c:if test="${obj.fz==1}">btn btn-xs blue</c:if>  dropdown-toggle">
冻结<c:if test="${obj.fz==0}">冻结</c:if>
解冻<c:if test="${obj.fz==1}">解冻</c:if>
</a>
<a href="#" onclick="changeState('${obj.id}')" id="sample_editable_1_new" 
	class="<c:if test="${obj.state eq 0}">btn btn-xs red</c:if>
	<c:if test="${obj.state eq 1}">btn btn-xs blue</c:if>
	dropdown-toggle">
		<c:if test="${obj.state eq 0}">冻结</c:if>
		<c:if test="${obj.state eq 1}">解冻</c:if>
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
<a  href="${ourl}/add" id="sample_editable_1_new" class="btn sbold green"><i class="fa fa-plus"> 新增</i></a>
</div>
<div class="btn-group">
<a id="Odels" class="btn sbold red"><i class="fa fa-trash-o"> 批量删除</i></a>
</div>


时间搜索器

<form action="" id="sp">
	<div class="table-toolbar">
		  <div class="row">
			 <div class="col-md-14">
			  <div class="btn-group col-md-3">
				<label class="control-label">选择时间范围</label>
				 <input type="text" name="theTime" class="layui-input" value="${theTime}" placeholder="请选择时间" id="theTime">
			  </div>
			</div>
		</div>
	 </div>
 </form>
js:
//时间选择器
//range设置为true则为时间范围
layui.laydate.render({
	elem: '#theTime'
	,type: 'datetime'
	,range: true
}); 

按钮不能按
<input disabled="true" />

下拉框
<div class="form-group col-md-3">
<label class="form-label col-sm-4 " style="text-align:right">
</label>
<div class="col-md-8" >
<select name="type" data-live-search="true" class="selectpicker" data-width="100%">
<option value="0">所有</option>
<option value="1">1</option>
<option value="2">2</option>
</select>
</div>
</div>
<div class="form-group col-md-3">
	<label class="form-label col-sm-4 " style="text-align:right">
		状态
	</label>
	<div class="col-md-8" >
		<select name="state" data-live-search="true" class="selectpicker" data-width="100%">
			<option  value="0">全部</option>
			<option <c:if test="${state eq 1 }">selected</c:if> value="1">正常</option>
			<option <c:if test="${state eq 2 }">selected</c:if> value="2">已冻结</option>
		</select>
	</div>
</div>
<input class="form-control" id="mask_date" type="text" value="<c:if test="${------------ == '0'}">未支付</c:if>" disabled="disabled"/> 

遍历的下拉框
<div class="form-group" id="role_form">
	<label class="control-label col-md-3">关联教师</label>
	<div class="col-md-4">
		<select class="bs-select form-control" name="course.userId">
			<c:forEach items="${teacherList}" var="obj">
				<option value="${obj.id}" <c:if test="${obj.id eq course.userId }">selected</c:if> >${obj.nick}</option>
			</c:forEach>
		</select>
	</div>
</div>
默认选择哪个下拉框 js
	var course.userId = '${course.userId}'.trim()==""?-1:'${course.userId}';
	$("select[name='course.userId']").selectpicker('val',course.userId);

不换行
<form action="" id="tp">
	<div class="form-group col-md-3">
		<label class="form-label col-sm-4 "  style="text-align:right">选择时间范围</label>
		<div class="col-md-8" >
			<input type="text" name="theTime" class="layui-input" value="${theTime}" placeholder="请选择时间" id="theTime">
		</div>
	</div>
</form>
视频标签

	<video width="320" height="240" controls="controls">
        <source src="${ctx2}${anchor.videoUrl}">
    </video>

多选框
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
回显js

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
		<label class="control-label col-md-3">付费类型</label>
		<div class="col-md-4">
			<label class="mt-radio">
				<input type="radio" name="course.type" id="optionsRadios1" value="0" <c:if test="${course.type == null || course.type eq 0}">checked</c:if>>付费<span></span>
			</label>
			<label class="mt-radio">
				<input type="radio" name="course.type" id="optionsRadios2" value="1" <c:if test="${course.type eq 1}">checked</c:if>>免费<span></span>
			</label>
		</div>
	</div>
	
	
	<div class="form-group">
		<label class="control-label col-md-3">账号</label>
		<div class="col-md-4">
			<input class="form-control" type="text" value="${account.mobile}" name="account.mobile" datatype="m" errormsg="请用手机号码注册！" nullmsg="账号不能为空！"/> <span class="Validform_checktip"></span>
		</div>
	</div>

js获取选择的id
var ids = $("#tf").serialize();

显示列表
图片:<td><img src="<c:if test="${obj.avatar == null}">${ctx}/assets/metronic/global/img/no.png</c:if><c:if test="${obj.avatar != null}">${ctx}${obj.avatar}</c:if>" style="width: 50px;"/></td>
字段<td><c:out value="${obj.createTime}" default="" /></td>
选择显示<td><c:if test="${obj.fz eq 0}">正常</c:if><c:if test="${obj.fz eq 1}">冻结</c:if></td>
    
	<div class="form-group">
		<label class="control-label col-md-3">参赛图片</label>
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
						class="fileinput-new">选择图片 </span> <span class="fileinput-exists">改变
					</span> <input type="file" name="matchModelImage">
					</span> <a href="javascript:;" class="btn red fileinput-exists"
						data-dismiss="fileinput"> 删除 </a>
				</div>
			</div>
			<div class="clearfix margin-top-10">
				<span class="label label-danger">NOTE!</span> 兼容 IE10+,
				FF3.6+,Safari6.0+, Chrome6.0+ and Opera11.1+.以上的浏览器
			</div>
		</div>
	</div>                    																												

											<input class="form-control" type="text" value="${enName}" name="enName" placeholder="英文标题名称"/>
<small>本行内容是在标签内</small><br> 
<strong>本行内容是在标签内</strong><br>
<em>本行内容是在标签内，并呈现为斜体</em><br>
<p class="text-left">向左对齐文本</p>
<p class="text-center">居中对齐文本</p>
<p class="text-right">向右对齐文本</p>
<p class="text-muted">本行内容是减弱的</p> 
<p class="text-primary">本行内容带有一个 primary class</p>
<p class="text-success">本行内容带有一个 success class</p>
<p class="text-info">本行内容带有一个 info class</p> 
<p class="text-warning">本行内容带有一个 warning class</p>
<p class="text-danger">本行内容带有一个 danger class</p>

text-capitalize 利用
text-center 居中
text-danger 加红危险
text-hide 隐藏文字
text-info 信息
text-justify 字体对齐（齐行——
text-left 文字左对齐
text-lowercase 小写（仅英文）
text-muted 静音
text-nowrap 不换行
text-primary 原生效果
text-right 文字居右
text-success 成功
text-uppercase 文字大写（仅英文）
text-warning 警告红色

主要显示的颜色是：
柔和灰（text-muted）、
主要蓝（text-primary）、
成功绿（text-success）、
信息蓝（text-info）、
警告黄（text-warning）、
危险红（text-danger）

input不能为空
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
										<button id="search" class="btn green"><i class="fa fa-search"> 搜索</i></button>
                                    </div>

文件上传判断
	<div class="form-group">
		<label class="control-label col-md-3">播放文件</label>
		<div class="col-md-4"><!-- class="form-control" -->
			<input  class="file"  type="file" onchange="fileChange(this);"
				 name="course.recordUrl" datatype="*1-200"/> 
				<span class="Validform_checktip"></span>
		</div>
		<div class="col-md-4"  style="display: none" id="noChangeFile">
			<input value="不更改视频文件" type="button"  class="btn default" onclick="deleteTrRow(this)">
		</div>
	</div>

function fileChange(target, id) {
	var fileSize = 0;
	var filepath = target.value;
	var filemaxsize = 1024 * 1024 * 1.9;//接受的文件最大1.9G
	if (!target.files) {
	var filePath = target.value;
	var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
	if (!fileSystem.FileExists(filePath)) {
		alert("附件不存在，请重新输入！");
		return false;
	}
	var file = fileSystem.GetFile(filePath);
		fileSize = file.Size;
	} else {
		fileSize = target.files[0].size;
	}
	var size = fileSize / 1024;
	if (size > filemaxsize) {
		alert("文件大小不能大于" + filemaxsize / (1024*1024) + "G！");
		target.value = "";
		return false;
	}
	if (size <= 0) {
		alert("文件大小不能为0M！");
		target.value = "";
		return false;
	}
}
$("#submitFrom").click(function(){
	alert("点击确认进行上传文件,请耐心等待上传时间!");
	$("#validform").submit();
	$("#submitFrom").attr('disabled',true);
});

//移除文件上传
function deleteTrRow(tr){
$(tr).parent().parent().remove();
};

判空
empty topic

三级联动

 	 <div class="form-group">
        <div class="control-label col-md-3">
      		 地区
        </div>
        <div class="col-sm-3">
            <select  data-live-search="true" class="selectpicker" data-width="100%"  name="country" id="country">
                <option>请选择</option>
            	<c:forEach items="${country}" var="obj">
					<option value="${obj.id}" <c:if test="${obj.id eq countryId }">selected</c:if> >${obj.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="col-sm-3">
            <select  data-live-search="true" class="selectpicker" data-width="100%"  name="province" id="province">
                <option>请选择</option>
            	<c:forEach items="${province}" var="obj">
					<option value="${obj.id}" <c:if test="${obj.id eq provinceId }">selected</c:if> >${obj.name}</option>
                </c:forEach>       	
            </select>
        </div>       
        <div class="col-sm-3">
            <select data-live-search="true" class="selectpicker" data-width="100%"  name="city" id="city" dataType="/^(?!请选择$)/" errormsg="请选择地区！" nullmsg="请选择地区">
                <option>请选择</option>
            	<c:forEach items="${city}" var="obj">
					<option value="${obj.id}" <c:if test="${obj.id eq cityId }">selected</c:if> >${obj.name}</option>
                </c:forEach>
            </select>
        </div>
    </div>
	
	
	
	下拉框不能为请选择
	dataType="/^(?!请选择$)/" errormsg="请选择地区！" 
				//刷新所有搜索框
	            $(".selectpicker" ).selectpicker('refresh');
	
	js代码
	 $(function () {
	    //默认绑定省
	    //ProviceBind();
	    //绑定事件
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
	    //判断省份这个下拉框选中的值是否为空
	    if (provice == "") {
	        return;
	    }
	    $("#City").html("");
	    var str = "<option>请选择</option>";
	    $.ajax({
	        type: "POST",
	        url: "${ourl}/getSonArea",
	        data: {"areaId": provice},
	        dataType: "JSON",
	        async: false,
	        success: function (data) {
	            //从服务器获取数据进行绑定
	            $.each(data.sonArea, function (i, item) {
	                str += "<option value=" + item.id + ">" + item.name + "</option>";
	            })
	            //将数据添加到省份这个下拉框里面
	            $("#City").append(str);
	            $(".selectpicker" ).selectpicker('refresh');
	        },
	        error: function () { alert("Error"); }
	    });
	}
	
	
	
	function VillageBind() {
	    var provice = $("#City").val();
	    //判断市这个下拉框选中的值是否为空
	    if (provice == "") {
	        return;
	    }
	    $("#Village").html("");
	    var str = "<option>请选择</option>";
	    //将市的ID拿到数据库进行查询，查询出他的下级进行绑定
	    $.ajax({
	        type: "POST",
	        url: "${ourl}/getSonArea",
	        data: { "areaId": provice},
	        dataType: "JSON",
	        async: false,
	        success: function (data) {
	            //从服务器获取数据进行绑定
	            $.each(data.sonArea, function (i, item) {
	                str += "<option value=" + item.Id + ">" + item.name + "</option>";
	            })
	            //将数据添加到省份这个下拉框里面
	            $("#Village").append(str);
	            $(".selectpicker" ).selectpicker('refresh');
	        },
	        error: function () { alert("Error"); }
	    });
	}	不能为空加提示加不能修改
	
			更改
			<input  readOnly class="form-control" type="text" value="${room.title}" name="room.title"  placeholder="yyyy-MM/dd" datatype="*1-100" nullmsg="直播中文名称不能为空！"/> <span class="Validform_checktip"></span>

			动态校验
<input class="form-control" type="text" value="" name="mobile" datatype="*11-11" errormsg="请输入11位的手机号码" ajaxurl="${ourl }/isExit" />

			
动态获取下来框
	<div class="form-group">
		<label class="control-label col-md-3">分类对应的规格</label>
		<div class="col-md-7" id="skus">
		
		</div>
		<input type="button" class="btn green" id="addSku" value="新增规格">
	</div>
			//点击新增一个规格
	          $("#addSku").click(function(){
	        	//判断这个id下有没有内容
	        	var skus = "";
	        	var num = 0;
        		$("#skus select").each(function(){
        			//如果是第二个值就跳过
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
  			        		alert("规格已经用尽,规格不足请前往规格管理添加");
  			        		return;
  			        	}
  			        	var sonStr="";
  			       		var str = "<div><div class='col-sm-10'><select name='sku' class='form-control'>";
  			        	$.each(data.specList, function (i, item) {
  			    	 		str+="<option value="+item.id+" >" + item.value + "</option>"
  			        	})
  			        	str+="</select></div>"
  			        	+"<div class='col-md-2'><input onclick='deleteSkuTrRow(this);'  class='btn sbold red' type='button' value='移除'/></div></div>";
	  					var addtr = $(str);
	  		      		addtr.appendTo(tables); 
  			         }
  				 });
	          });
	        function deleteSkuTrRow(i){
	        	$(i).parent().parent().remove();
			}
			

datatype的使用
引入
<link href="${ctx}/assets/validform/validform.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/assets/validform/validform_min.js"></script> <!-- 注意要导入到jq的js下面 -->
设置from标签的id ,例如:
id="validform"
根据id设置
	$(function() {
		$("#validform").Validform({
			tiptype : 3
		});
	})
		
			
<script>




//页面刷新
window.location.reload();
//删除判断
if(confirm("确认删除该信息吗?")){ 
};

$(obj).parents("tr").find("td").eq(6).html("冻结");
		//认证失败
		$("#authenticationFail").click(function(){
			var realNameId = $("#realNameId").val();
			var context = prompt("取消认证理由(中文版)","照片不清晰");
			if(context != null  && context.trim() != ""){
				var enContext = prompt("取消认证理由(英文版)","The picture is not clear");
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
			        		  alert("操作成功");
			        	  }else{
			        		  alert("操作失败");
			        	  }
			        	  window.location.href="page";
			           }  
			      	});
				}else{
					alert("请输入认证失败的中英理由,不能为空");
				}
			}else{
				alert("请输入认证失败的理由,不能为空");
			}
		});


 //删除信息
function deleteNotice(id){
	if(confirm("确认删除该信息吗?")){ 
		$.ajax({  
			type : "post",  
			url :  "${ourl}/deleteNotice",  
			dataType: "json",
			data : {"id":id},  
			async : true,  
			success : function(data){
				if(data.flag){
					alert("删除成功!")
				}else{
					alert("删除失败!");
				}
				window.location.reload();
			}  
		});
	}
};

$("#Odels").click(function(event) {
	if(confirm("确认删除吗?")){ 
		var ids = $("#tf").serialize();
		if(ids==null || ids.length==0){
			alert("请勾选后再进行批量删除操作");
			return;
		}
		$.ajax({  
		  type : "post",  
		  url :  "${ourl}/dels",  
		  data : ids,  
		  async : true,  
		  success : function(data){
			  if(data.flag == false){
				  alert("删除失败!");
			  }else{
				  alert("删除成功!");
			  }
			  window.location.reload();
		   }  
		});
	}
 });
 
 
			//点击新增一个敏感词格
			$("#add").click(function(){
				var tables = $("#sensitiveFrom");

				var addtr = $("<div class='form-body'>"
							+   "<div class='form-group'>"
							+		"<label class='control-label col-md-3'>敏感词</label>"
							+		"<div class='col-md-4'>"
							+			"<input id='mask_date' type='text' name='work' value='${work}' datatype='*1-200'/>"
							+		"</div>"
							+		"<div class='col-md-2'>"
							+			"<input onclick='deleteTrRow(this);'  class='btn sbold red' id='mask_date' type='button' value='移除'/>" 
							+		"</div>"
							+	 "</div>"
							+  "</div>");

		      	addtr.appendTo(tables); 
		      	
			});
//移除文件上传
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
					//画上二维码
					 $("#qrcode").qrcode({
						width : 36, //宽度  
						height : 36, //高度  
						text : data.qrCode, //任意内容  
					});
					//画海报
					//var width = document.getElementById("canbox").offsetWidth; //宽度  
					//var height = document.getElementById("canbox").offsetHeight; // 高度
					var width = 600; //宽度  
					var height = 600; // 高度
					var c = document.getElementById("myCanvas");
					c.width = width;
					c.height = height;
					var ctx = c.getContext("2d");
					//首先画上背景图
					var img = new Image();
					img.src = data.image;
					img.setAttribute("crossOrigin", 'Anonymous')
					var x_bot = height - 44 //画上名字
					ctx.font = "19px Georgia";

					function convertCanvasToImage(canvas) {
						var image = new Image();
						image.src = canvas.toDataURL(data.image);
						return image;
					}
					var mycans = $('canvas')[1];//二维码所在的canvas
					var codeimg = convertCanvasToImage(mycans);
					var xw = width - 72 - 29
					var xh = height - 6 - 72

					img.onload = function() { //必须等待图片加载完成
						ctx.drawImage(img, 0, 0, width, height); //绘制图像进行拉伸
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
						//绘制完成,转为图片
						setTimeout(function() { //在ios上无法在画完之后取到整个画布内容，加了个settimeout
							var bigcan = $('canvas')[0];
							let images = new Image();
							images.src = bigcan.toDataURL(data.image);
							images.setAttribute("crossOrigin", 'Anonymous')
							$('.canimg').attr('src', bigcan.toDataURL(data.image))
						}, 0);
					}

				},
				error : function() {
					alert("出错!");
				}

				});
			});

			
//隐藏			
$("#hide").click(function(){
  $("p").hide();
});
//显示
$("#show").click(function(){
  $("p").show();
});

//弹窗
$("#add").click(function(){
		    // 获取传入的DIV 
		    var $div_obj = $("#pop-div"); 
		    // 计算机屏幕高度 
		    var windowWidth = $(window).width(); 
		    // 计算机屏幕长度 
		    var windowHeight = $(window).height(); 
		    // 取得传入DIV的高度 
		    var popupHeight = $div_obj.height(); 
		    // 取得传入DIV的长度 
		    var popupWidth = $div_obj.width(); 
		     
		    // // 添加并显示遮罩层 
		    $("<div id='bg' style='background:#000; color:#FFF' ></div>").width(windowWidth * 2) 
		        .height(windowHeight * 2).click(function() { 
		          //hideDiv(div_id); 
		        }).appendTo("body").fadeIn(200); 
		     
		    // 显示弹出的DIV 
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
		  /*隐藏弹出DIV*/ 
		  function hideDiv() {
		    $("#bg").remove(); 
		    $("#pop-div").animate({ 
		      left : 0, 
		      top : 0, 
		      opacity : "hide" 
		    }, "slow"); 
		  }
		  
</script>
<!--弹窗html代码-->
<div id='pop-div' style="width: 600px;height:900px;background:#FFF"  class="pop-box">
	<form action=""  class="form-horizontal form-bordered"  enctype="multipart/form-data" method="post" id="lecturer">
		
		<input type="hidden" name="curriculum.id" value="${curriculum.id}" />
		<div class="form-body">
			<div class="form-group">
				<label class="control-label col-md-3">讲师简称</label>
				<div class="col-md-4">
					<input class="form-control" type="text" value="${name}" name="name" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-3">头像</label>
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
								class="fileinput-new">选择图片 </span> <span class="fileinput-exists">改变
							</span> <input type="file" name="head">
							</span> <a href="javascript:;" class="btn red fileinput-exists"
								data-dismiss="fileinput"> 删除 </a>
						</div>
					</div>
					<div class="clearfix margin-top-10">
						<span class="label label-danger">NOTE!</span> 兼容 IE10+,
						FF3.6+,Safari6.0+, Chrome6.0+ and Opera11.1+.以上的浏览器
					</div>
				</div>
			</div>
			<div class="form-actions">
				<div class="row">
					<div class="col-md-offset-3 col-md-9">
						<button type="button" class="btn blue" id="lecturerSubmit">
							<i class="fa fa-check"></i> Submit
						</button>
						<button type="button" class="btn default" onclick="hideDiv()">关闭</button>
					</div>
				</div>
			</div>
		</div>
	</form>
</div>