/*-
 * #%L
 * Holdem dojo project is a server-side java application for playing holdem pocker in DOJO style.
 * %%
 * Copyright (C) 2016 Holdemdojo
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
/**
 * Date: 16.03.13
 * Time: 12:24
 */
document.write("<script src='http://ajax.googleapis.com/ajax/libs/jquery/1.2.6/jquery.min.js' type='text/javascript'></script>");
document.write("<script src='gameData.js' type='text/javascript'></script> ");
var socket;


var connect = function (path) {
    socket = new WebSocket(path);

    socket.onopen = function () {

    };

    socket.onclose = function () {

    };

    socket.onmessage = function (message) {
        gameData = JSON.parse(message.data);
        drawGameData();
    }
};
