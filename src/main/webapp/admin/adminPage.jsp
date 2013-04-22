<%--
  User: Konstantin Demishev
  Date: 25.03.13
  Time: 16:02
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="gameData" class="com.nedogeek.holdem.server.GameDataBean" scope="request"></jsp:useBean>


<html>
<head>
    <title>Admin panel</title>
    <script src='http://ajax.googleapis.com/ajax/libs/jquery/1.2.6/jquery.min.js' type='text/javascript'></script>

    <script>
        function submitForm(command, params) {
            form = $(document).find("#submitForm");
            $commandName = $(document).find("#commandName");
            $commandParams = $(document).find("#commandParams");

            $commandName.val(command);
            $commandParams.val(params);

            form.submit();
        }

        function changePassword() {
            password = $(document).find("#password").val();
            confirmation = $(document).find("#confirmation").val();

            if (password == confirmation && password != "") {
                submitForm("changePassword", password);
            } else {
                alert("Confirmation does not math password or password is empty.");
            }
        }

        function kickPlayer() {
            playerName = $("#playerNames").find("option:selected").text();

            submitForm("kick", playerName);
        }

        function addBot() {
            botType = $("#botTypes").find("option:selected").text();

            botName = $(document).find("#botName").val();

            if (botName != "" && botName.indexOf("\n") === -1) {
                submitForm("addBot", botType + "\n" + botName);
            }
        }

        function setMinimumBlind() {
            minimumBlind = $(document).find("#smallBlind").val();

            submitForm("setMinimumBlind", minimumBlind);
        }

        function setCoinsAtStart() {
            coins = $(document).find("#coinsAtStart").val();

            submitForm("setInitialCoins", coins);
        }

        function setGameDelay() {
            gameDelay = $(document).find("#gameDelay").val();

            submitForm("setGameDelay", gameDelay);
        }

        function setEndGameDelay() {
            endGameDelay = $(document).find("#endGameDelay").val();

            submitForm("setEndGameDelay", endGameDelay);
        }

        function refreshPage() {
            submitForm("refreshPage");
        }

    </script>

</head>
<body>
<form action="" id="submitForm" method="post">
    <input type="hidden" name="command" id="commandName">
    <input type="hidden" name="params" id="commandParams">
</form>


<div class="refreshDiv">
    <button onclick="refreshPage()">Refresh page</button>
</div>

<div class="serverOperations">
    Server status:
    <jsp:getProperty name="gameData" property="gameStatus"/>
    <button onClick="submitForm('start', '')"> Start</button>
    <button onClick="submitForm('stop', '')"> Stop</button>
    <button onClick="submitForm('pause', '')"> Pause</button>
</div>

<div class="kickDiv">
    <select id="playerNames">
        <c:forEach var="name" items="${gameData.players}">
            <option value="${name}">${name}</option>
        </c:forEach>
    </select>
    <button onclick="kickPlayer()">Kick player.</button>
</div>

<div class="addBots">
    <select id="botTypes">
        <c:forEach var="name" items="${gameData.botTypes}">
            <option id="${name}">${name}</option>
        </c:forEach>
    </select>
    <input id="botName">
    <button onclick="addBot()">Add bot.</button>
</div>

<div class="gameSettings">
    Coins at start:
    <input type="number" id="coinsAtStart"
           value='<jsp:getProperty name="gameData" property="coinsAtStart"></jsp:getProperty>'>
    <button onclick="setCoinsAtStart()">Set coins at start.</button>
    <br>

    Small blind:
    <input type="number" id="smallBlind"
           value='<jsp:getProperty name="gameData" property="minimumBind"></jsp:getProperty>'>
    <button onclick="setMinimumBlind()">Set small blind.</button>
    <br>

    Game delay:
    <input type="number" id="gameDelay"
           value='<jsp:getProperty name="gameData" property="gameDelay"></jsp:getProperty>'>
    <button onclick="setGameDelay()">Set game delay.</button>
    <br>

    End game delay:
    <input type="number" id="endGameDelay"
           value='<jsp:getProperty name="gameData" property="endGameDelay"></jsp:getProperty>'>
    <button onclick="setEndGameDelay()">Set end game delay.</button>
</div>

<div class="changeAdminPassword">
    New password: <input type="password" id="password">
    Confirmation: <input type="password" id="confirmation">

    <button onclick="changePassword()">Change password</button>
</div>

</body>
</html>