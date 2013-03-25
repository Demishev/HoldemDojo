<%--
  User: Konstantin Demishev
  Date: 25.03.13
  Time: 16:02
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin panel</title>
</head>
<body>
<div class="serverOperations">
    Server status: <%= request.getAttribute("GameStatus") %>
    <form action="" method="post">
        <input type="hidden" name="Start" value="Start">
        <input type="hidden" name="password" value="<%= request.getParameter("password")%>">
        <input type="submit" name="Start">
    </form>
    <form action="" method="post">
        <input type="hidden" name="Stop" value="Stop">
        <input type="hidden" name="password" value="<%= request.getParameter("password")%>">
        <input type="submit" name="Stop">
    </form>
    <form action="" method="post">
        <input type="hidden" name="Pause" value="Pause">
        <input type="hidden" name="password" value="<%= request.getParameter("password")%>">
        <input type="submit" name="Pause">
    </form>
</div>

<div class="kickDiv">

</div>

<div class="addBots">

</div>

<div class="gameSettings">
    <form action="" method="post">
        <label>
            <input name="CoinsAtStart" value="<%= request.getAttribute("CoinsAtStart")%>">
        </label>
        <input type="hidden" name="password" value="<%= request.getParameter("password")%>">
        <input type="submit" name="Set coins at start">
    </form>
    <form action="" method="post">
        <label>
            <input name="SmallBlind" value="<%= request.getAttribute("SmallBlind")%>">
        </label>
        <input type="hidden" name="password" value="<%= request.getParameter("password")%>">
        <input type="submit" name="Set small blind">
    </form>
    <form action="" method="post">
        <label>
            <input name="GameDelay" value="<%= request.getAttribute("GameDelay")%>">
        </label>
        <input type="hidden" name="password" value="<%= request.getParameter("password")%>">
        <input type="submit" name="Set game delay">
    </form>

    <form action="" method="post">
        <label>
            <input name="EndGameDelay" value="<%= request.getAttribute("EndGameDelay")%>">
        </label>
        <input type="hidden" name="password" value="<%= request.getParameter("password")%>">
        <input type="submit" name="Set end game delay">
    </form>
</div>

<div class="changeAdminPassword">

</div>

</body>
</html>