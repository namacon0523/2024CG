<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="poker.PokerModel" %>
<%
PokerModel model = new PokerModel();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>役の判別</title>
</head>
<body>
<%
model.reset();
model.nextgame();
model.setHandcards(9,10,11,12,0);
model.evaluate();
%>
<img src="cards/<%= model.getHandcardAt(0) %>.png" width="60" height="90">
<img src="cards/<%= model.getHandcardAt(1) %>.png" width="60" height="90">
<img src="cards/<%= model.getHandcardAt(2) %>.png" width="60" height="90">
<img src="cards/<%= model.getHandcardAt(3) %>.png" width="60" height="90">
<img src="cards/<%= model.getHandcardAt(4) %>.png" width="60" height="90">
<%= model.getMessage() %><br>

<hr>
<% for (int i=0; i<5; i++) {
	model.reset();
	model.nextgame();
	model.evaluate();
%>
<img src="cards/<%= model.getHandcardAt(0) %>.png" width="60" height="90">
<img src="cards/<%= model.getHandcardAt(1) %>.png" width="60" height="90">
<img src="cards/<%= model.getHandcardAt(2) %>.png" width="60" height="90">
<img src="cards/<%= model.getHandcardAt(3) %>.png" width="60" height="90">
<img src="cards/<%= model.getHandcardAt(4) %>.png" width="60" height="90">
<%= model.getMessage() %><br>
<%}%>

</body>
</html>