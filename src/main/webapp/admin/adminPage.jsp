<%@ page import="java.util.ArrayList" %>
<%--
  User: Konstantin Demishev
  Date: 25.03.13
  Time: 16:02
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin panel</title>
    <script>
        function validatePassword() {
            pass = document.forms["changePassword"]["ChangePassword"].value;
            ver = document.forms["changePassword"]["verification"].value;
            if (pass != undefined && pass == ver) {
                return true;
            } else {
                alert("Verify password and verification");
                return false;
            }
        }
    </script>

</head>
<body>
<div class="serverOperations">
    Server status: <%= request.getAttribute("GameStatus") %>
    <form action="" method="post">
        <input type="hidden" name="Start" value="Start">
        <input type="hidden" name="password" value="<%= request.getParameter("password")%>">
        <input type="submit" value="Start">
    </form>
    <form action="" method="post">
        <input type="hidden" name="Stop" value="Stop">
        <input type="hidden" name="password" value="<%= request.getParameter("password")%>">
        <input type="submit" value="Stop">
    </form>
    <form action="" method="post">
        <input type="hidden" name="Pause" value="Pause">
        <input type="hidden" name="password" value="<%= request.getParameter("password")%>">
        <input type="submit" value="Pause">
    </form>
</div>

<div class="kickDiv">
    <form name="kickForm" action="" method="post">
        <label>
            <select name="Kick">
                <%
                    ArrayList<String> names = (ArrayList<String>) request.getAttribute("Players");
                    for (String name : names) {
                        out.println("<option value=\"" + name + "\">" + name + "</option>");
                    }
                %>
            </select>
        </label>
        <input type="hidden" name="password" value='<%= request.getParameter("password")%>'>
        <input type="submit" value="Kick player!">
    </form>
</div>

<div class="addBots">
    <form name="addBots" action="" method="post">
        <label>
            <select name="AddBot">
                <%
                    String[] bots = (String[]) request.getAttribute("Bots");
                    for (String botName : bots) {
                        out.println("<option value=\"" + botName + "\">" + botName + "</option>");
                    }
                %>
            </select>
        </label>
        <label>
            <input name="BotName">
        </label>
        <label>
            <input type="hidden" name="password" value="<%= request.getParameter("password")%>">
        </label>
        <input type="submit" value="Add bot">
    </form>
</div>

<div class="gameSettings">
    <form action="" method="post">
        Coins at start:
        <label>
            <input name="CoinsAtStart" value="<%= request.getAttribute("CoinsAtStart")%>">
        </label>
        <input type="hidden" name="password" value="<%= request.getParameter("password")%>">
        <input type="submit" value="Set coins at start">
    </form>
    <form action="" method="post">
        Small blind:
        <label>
            <input name="SmallBlind" value="<%= request.getAttribute("SmallBlind")%>">
        </label>
        <input type="hidden" name="password" value="<%= request.getParameter("password")%>">
        <input type="submit" value="Set small blind">
    </form>
    <form action="" method="post">
        Game delay:
        <label>
            <input name="GameDelay" value="<%= request.getAttribute("GameDelay")%>">
        </label>
        <input type="hidden" name="password" value="<%= request.getParameter("password")%>">
        <input type="submit" value="Set game delay">
    </form>

    <form action="" method="post">
        End game delay:
        <label>
            <input name="EndGameDelay" value="<%= request.getAttribute("EndGameDelay")%>">
        </label>
        <input type="hidden" name="password" value="<%= request.getParameter("password")%>">
        <input type="submit" value="Set end game delay">
    </form>
</div>

<div class="changeAdminPassword">
    <form action="" name="changePassword" method="post" onsubmit="return validatePassword()">
        Old password:
        <label>
            <input type="password" name="password">
        </label>
        New password:
        <label>
            <input type="password" name="ChangePassword">
        </label>
        Verification:
        <label>
            <input type="password" name="verification">
        </label>
        <input type="submit" value="Change password">
    </form>

</div>

</body>
</html>