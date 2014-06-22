<%@ page pageEncoding="UTF-8" 
	import="java.util.*, java.text.*, videoshare.model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Video</title>
		<style type="text/css">
			<!--
			body {
				font: 100%/1.4 Verdana, Arial, Helvetica, sans-serif;
				background-color: #FFFFFF;
				margin: 0;
				padding: 0;
				color: #000;
			}
			
			/* ~~ 元素/标签选择器 ~~ */
			ul, ol, dl { /* 由于浏览器之间的差异，最佳做法是在列表中将填充和边距都设置为零。为了保持一致，您可以在此处指定需要的数值，也可以在列表所包含的列表项（LI、DT 和 DD）中指定需要的数值。请注意，除非编写一个更为具体的选择器，否则您在此处进行的设置将会层叠到 .nav 列表。 */
				padding: 0;
				margin: 0;
			}
			h1, h2, h3, h4, h5, h6, p, a, table, input, video {
				margin-top: 0;	 /* 删除上边距可以解决边距会超出其包含的 div 的问题。剩余的下边距可以使 div 与后面的任何元素保持一定距离。 */
				padding-right: 15px;
				padding-left: 15px; /* 向 div 内的元素侧边（而不是 div 自身）添加填充可避免使用任何方框模型数学。此外，也可将具有侧边填充的嵌套 div 用作替代方法。 */
			}
			a img { /* 此选择器将删除某些浏览器中显示在图像周围的默认蓝色边框（当该图像包含在链接中时） */
				border: none;
			}
			/* ~~ 站点链接的样式必须保持此顺序，包括用于创建悬停效果的选择器组在内。 ~~ */
			a:link {
				color: #42413C;
				text-decoration: underline; /* 除非将链接设置成极为独特的外观样式，否则最好提供下划线，以便可从视觉上快速识别 */
			}
			a:visited {
				color: #6E6C64;
				text-decoration: underline;
			}
			a:hover, a:active, a:focus { /* 此组选择器将为键盘导航者提供与鼠标使用者相同的悬停体验。 */
				text-decoration: none;
			}
			
			/* ~~ 此固定宽度容器包含其它 div ~~ */
			.container {
				width: 960px;
				background-color: #FFF;
				margin: 0 auto; /* 侧边的自动值与宽度结合使用，可以将布局居中对齐 */
			}
			
			/* ~~ 标题未指定宽度。它将扩展到布局的完整宽度。标题包含一个图像占位符，该占位符应替换为您自己的链接徽标 ~~ */
			.header {
				background-color: #FFFFFF;
				font-family: "Century Gothic";
				font-size: 36px;
				color: #000;
				font-style: normal;
			}
			
			/* ~~ 这是布局信息。 ~~ 
			
			1) 填充只会放置于 div 的顶部和/或底部。此 div 中的元素侧边会有填充。这样，您可以避免使用任何“方框模型数学”。请注意，如果向 div 自身添加任何侧边填充或边框，这些侧边填充或边框将与您定义的宽度相加，得出 *总计* 宽度。您也可以选择删除 div 中的元素的填充，并在该元素中另外放置一个没有任何宽度但具有设计所需填充的 div。
			
			*/
			
			.content {
			
				padding: 10px 0;
			}
			
			/* ~~ 脚注 ~~ */
			.footer {
				padding: 10px 0;
				background-color: #FFFFFF;
				font-family: "Century Gothic";
				font-size: 12px;
			}
			
			/* ~~ 其它浮动/清除类 ~~ */
			.fltrt {  /* 此类可用于在页面中使元素向右浮动。浮动元素必须位于其在页面上的相邻元素之前。 */
				float: right;
				margin-left: 8px;
			}
			.fltlft { /* 此类可用于在页面中使元素向左浮动。浮动元素必须位于其在页面上的相邻元素之前。 */
				float: left;
				margin-right: 8px;
			}
			.clearfloat { /* 如果从 #container 中删除或移出了 #footer，则可以将此类放置在 <br /> 或空 div 中，作为 #container 内最后一个浮动 div 之后的最终元素 */
				clear:both;
				height:0;
				font-size: 1px;
				line-height: 0px;
			}
			body,td,th {
				font-family: "Century Gothic";
			}
			-->
		</style>
	</head>
	<body>
		<div class="container">
  		<div class="header">
    		<p>SCUT Video Share Platform</p>
   			<p><img src="images/line.png" /></p>
  		<!-- end .header --></div>
  		<div class="content">
		<a href='index.jsp'>返回主页</a><br><br>
		<%
			DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, Locale.CHINA);
			Video video = (Video) request.getAttribute("video");
		%>
		<tr>
			<td style='vertical-align: top;'>
				<%= video.getTitle() %><br>
				<%= video.getUsername() %><br>
				<%= dateFormat.format(video.getDate()) %>
				<hr>
			</td>
		</tr> 
		<video width="320" height="240" controls = "control">
			<source src="<%=video.getLocation()%>" type="video/mp4">
		</video>
		
		<table style='text-align: left; width: 510px; height: 88px;' border='0' cellpadding='2' cellspacing='2'>
			<thead>
				<tr>
					<th><hr></th>
				</tr>
			</thead>
			<tbody>
				<%
			        List<Comment> comments = (List<Comment>) request.getAttribute("comments");
					for (Comment comment : comments) {
				%>
				<tr>
					<td style='vertical-align: top;'>
						<%= comment.getUsername() %><br>
						<%= dateFormat.format(comment.getDate()) %>
						<%
							if (comment.getUsername().equals((String) request.getSession().getAttribute("login"))) {
						%>
						<a href='deletecomment.do?videoid=<%= video.getId() %>&date=<%= comment.getDate().getTime() %>'>删除</a>
						<%
							}
						%> 	
						<br>			
						<%= comment.getText() %><br>
						<hr>
					</td>
				</tr>
				<%
			        }
				%>
			</tbody>
		</table>
		<form method='post' action='addcomment.do?videoid=<%= video.getId() %>'>
			添加评论...<br>
			<%
				String text = request.getParameter("text");
		        if (text == null) {
		        	text = "";
		        }
		        else {
	        %>
	        	信息要140字以内<br>
	        <%
	        	}
			%>
			<textarea cols='60' rows='4' name='text'>${ requestScope.text }</textarea><br>
			<button type='submit'>送出</button>
		</form>
		<!-- end .content --></div>
	  	<div class="footer">
	  		<p><img src="images/line.png" /></p>
	   		<p>SCUT Video Share Platform</p>
	    <!-- end .footer --></div>
  	<!-- end .container --></div>
	</body>
</html>