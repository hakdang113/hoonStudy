<%@page import="Mission1DB.WifiInfo"%>
<%@page import="Mission1DB.WifiInfoOption"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%-- 디바이스 해상도에 맞게 설정되는 부분 --%>
<meta name="viewport" content="width=device-width", initial-scale="1">
<link rel="stylesheet" href="css/bootstrap.css">
<title>Insert title here</title>
<style>
      table {
        width: 100%;
        border-top: 1px solid #444444;
        border-collapse: collapse;
       
      }
      th, td {
      	border: 1px solid #444444;
        border-bottom: 1px solid #444444;
        padding: 10px;
        text-align: center;
        border-color: #F9FFFF;
      }
      
      thead tr {
      	background-color: #50C785;
      	color: white;
      }
      tbody tr:nth-child(2n) {
   		background-color: #dcdcdc;
  	  }
  	  tbody tr:nth-child(2n+1) {
    	background-color: #EFEFEF;
  	  }
  	  
  	  
</style>
</head>

<%-- 내 위치 주변 20개의 와이파이 정보 불러오기 & 상세 정보 보기  --%>

<body>
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap.js"></script>
	
	<nav class="navbar navbar-default">
		<div class="navbar-header">
			<a class= "navbar-brand" href="Mission1_Home.jsp">와이파이 정보 구하기</a>
		</div>
		<div class="collapse navbar-collapse" id="wifi-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="Histroy_List.jsp">위치 히스토리 목록</a></li>
				<li><a href="WifiInfoLoad.jsp">Open API 와이파이 정보 가져오기</a></li>
				<li><a href="BookmarkList.jsp">북마크 보기</a></li>
				<li><a href="BookmarkGroup_Manage.jsp">북마크 그룹 관리</a></li>
			</ul>
		</div>
	</nav>

	<h2><b>와이파이 정보 구하기</b></h2>
	<div>
		<a href="Mission1_Home.jsp">홈</a> | <a href="History_List.jsp">위치 히스토리 목록</a> | <a href="WifiInfoLoad.jsp">Open API 와이파이 정보 가져오기</a>
		| <a href="BookmarkList.jsp">북마크 보기</a> | <a href="BookmarkGroup_Manage.jsp">북마크 그룹 관리</a>
		<br></br>

		<form method="post" action="NearWifiList.jsp"> 
			<button>내 위치 가져오기</button>&nbsp;&nbsp; <button type="submit">근처 WIFI 정보 보기</button>&nbsp;&nbsp;
		</form>
	</div>
	
	
	<table>
		<thead>
					<tr>
						<th style=" text-align: center;">거리(Km)</th>
						<th style=" text-align: center;">관리번호</th>
						<th style=" text-align: center;">자치구</th>
						<th style=" text-align: center;">와이파이명</th>
						<th style=" text-align: center;">도로명주소</th>
						<th style=" text-align: center;">상세주소</th>
						<th style=" text-align: center;">설치위치(층)</th>
						<th style=" text-align: center;">설치유형</th>
						<th style=" text-align: center;">설치기관</th>
						<th style=" text-align: center;">서비스구분</th>
						<th style=" text-align: center;">망종류</th>
						<th style=" text-align: center;">설치년도</th>
						<th style=" text-align: center;">실내외구분</th>
						<th style=" text-align: center;">WIFI 접속환경</th>
						<th style=" text-align: center;">X좌표</th>
						<th style=" text-align: center;">Y좌표</th>
						<th style=" text-align: center;">작업일자</th>
					</tr>
		</thead>
		
	<% 
		WifiInfoOption wifiInfoOption = new WifiInfoOption();
		List<WifiInfo> wifiInfoList = wifiInfoOption.wifiList();

		for(int i=0; i < wifiInfoList.size(); i++){
	%>	
		<tr>
			<td>11</td>
			<td><%=wifiInfoList.get(i).getX_SWIFI_MGR_NO() %></td>
			<td><%=wifiInfoList.get(i).getX_SWIFI_WRDOFC() %></td>
			 <td>
				<a href="WifiDetail.jsp?X_SWIFI_MGR_NO=<%=wifiInfoList.get(i).getX_SWIFI_MGR_NO() %>">
					<%=wifiInfoList.get(i).getX_SWIFI_MAIN_NM() %>
				</a>
			</td>

			<%-- <td><%=wifiInfoList.get(i).getX_SWIFI_MAIN_NM() %></td> --%>
			<td><%=wifiInfoList.get(i).getX_SWIFI_ADRES1() %></td>
			<td><%=wifiInfoList.get(i).getX_SWIFI_ADRES2() %></td>
			<td><%=wifiInfoList.get(i).getX_SWIFI_INSTL_FLOOR() %></td>
			<td><%=wifiInfoList.get(i).getX_SWIFI_INSTL_TY() %></td>
			<td><%=wifiInfoList.get(i).getX_SWIFI_INSTL_MBY() %></td>
			<td><%=wifiInfoList.get(i).getX_SWIFI_SVC_SE() %></td>
			<td><%=wifiInfoList.get(i).getX_SWIFI_CMCWR() %></td>
			<td><%=wifiInfoList.get(i).getX_SWIFI_CNSTC_YEAR() %></td>
			<td><%=wifiInfoList.get(i).getX_SWIFI_INOUT_DOOR() %></td>
			<td><%=wifiInfoList.get(i).getX_SWIFI_REMARS3() %></td>
			<td><%=wifiInfoList.get(i).getLNT() %></td>
			<td><%=wifiInfoList.get(i).getLAT() %></td>
			<td><%=wifiInfoList.get(i).getWORK_DTTM() %></td>
		</tr>
	<% 
		}		
	%>
		
	</table>

	<div>
		<a href="Mission1_Home.jsp">홈으로 돌아가기</a>
	</div>

</body>
</html>