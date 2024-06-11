<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="poker.PokerModel" %>
<%
PokerModel model = (PokerModel) request.getAttribute("model");
String label = model.getButtonLabel();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Poker</title>
    <script>
        function playSwapSoundAndSubmit(form) {
            var audio = new Audio('カードめくり1.mp3');
            audio.play();
            setTimeout(function() {
                form.submit();
            }, 400); 
        }

        
    </script>
    <style>
        .card-img {
            width: 100px;
            height: 150px;
        }
        .bottom-right-container {
            position: fixed;
            right: 10px;
            bottom: 10px;
            display: flex;
            align-items: center; 
        }
        .bottom-right {
            width: 250px;  
            height: 375px;  
        }
    </style>
</head>

<body>
ポーカーゲーム
<hr>
ゲーム回数: <%= model.getGames() %>
<br>
チップ: <%= model.getChips() %>
<hr>
<%= model.getMessage() %>
<%= model.evaluateHand() %>
<form action="/s2232133/PokerServlet" method="POST" onsubmit="event.preventDefault(); playSwapSoundAndSubmit(this);">
<table>
<tr>
<td><img src="cards/<%= model.getHandcardAt(0) %>.png" class="card-img"></td>
<td><img src="cards/<%= model.getHandcardAt(1) %>.png" class="card-img"></td>
<td><img src="cards/<%= model.getHandcardAt(2) %>.png" class="card-img"></td>
<td><img src="cards/<%= model.getHandcardAt(3) %>.png" class="card-img"></td>
<td><img src="cards/<%= model.getHandcardAt(4) %>.png" class="card-img"></td>
</tr>
<tr align="center">
<td><input type="checkbox" name="change" value="0" onclick="toggleHighlight(this)"></td>
<td><input type="checkbox" name="change" value="1" onclick="toggleHighlight(this)"></td>
<td><input type="checkbox" name="change" value="2" onclick="toggleHighlight(this)"></td>
<td><input type="checkbox" name="change" value="3" onclick="toggleHighlight(this)"></td>
<td><input type="checkbox" name="change" value="4" onclick="toggleHighlight(this)"></td>
</tr>
</table>
<input type="submit" value="<%= label %>">
</form>
<hr>
<a href="/s2232133/PokerServlet">リセット</a>　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　*スートとは絵柄のこと
<hr>
<% if (model.getGames() < 5) { %>
5回未満
<% } else { %>
5回以上
<% } %>

<!-- 右下に写真を追加 -->
<div class="bottom-right-container">
    <img src="ポーカー　役柄.png" alt="Photo" class="bottom-right">
</div>

</body>
</html>
