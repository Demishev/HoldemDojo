This project is a server-side java application for playing holdem pocker in DOJO style.

Main sense is to write pocker playing algorithm that will beat opponents algorithm.
Users can watch game via web in log-in or log-outed statuses (when log-in player see his cards and card combinations).
There are no principal difference between connection by browser or client program because of what same web-socket connection is opened
to transport game data. That is why you can get javascript client template directly from game viewing page and
adopt it to send respond moves.

Game data and clients respond sends by web-sockets. On server side web-sockets are provided by jetty.

Steps to run the server:
    1. Download source.
    2. Set your server's IP-address in webapp/gameData.js
    3. Build war file.
    4. Deploy it on your web-server (now only jetty, it will be fixed later).

By default 2 server bots are connected to table and game started. You can change game settings, add bots or kick players by
admin panel, that is available at /admin. Default password is 1234 and it can be changed there.

Now game data and settings are not saving and resets at server restart.


Server API:

    Output commands:

        Game data sends to connections by web-sockets as JSON string. It contains:
        gameStatus: String;
        gameRound: String;
        dealer: String;
        mover: String;
        event: String;
        combination: String;
        deskCards: array of {cardValue: String; carsSuit: String}
        players: array of {name: String; status: String; balance: int; pot: int;
            cards: array of {cardValue: String, cardSuit: String}}

    Input commands:

        Player can respond with generated move. This move is a String with move type and moveValue separated by coma.

    Example:

        "Rise,20"

    If mover do not send wanted move - there is default respond: Fold.
    If mover do many responds - they override each one. After move default respond set.

    Move types:

        Fold
        Check
        Call
        Rise
        AllIn

What steps you should do to play with server:

    1. Write a program on any programming language, that handles web-socket connections and parses JSON game data
    or download one of available templates. Now you can download java client template
        at Demishev/HoldemDojoJavaClient
    2. Connect to the server with login and password (automatic registration on first login provided).
    3. Parse game data.
    4. Analyze parsed data.
    5. Write respond move and send it to web-socket connection.

Server can be started by using maven goal jetty:run-war using embedded jetty.

