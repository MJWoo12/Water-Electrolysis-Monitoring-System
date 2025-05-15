<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<html>
<head>
    <style>
        html, body {
            margin: 0;
            padding: 0;
            overflow: auto;
            width: 100vw;
            height: 100vh;
            position: relative;
        }
        .button-container {
            position: absolute;
            bottom: 20px;
            right: 220px;
            z-index: 10;
        }

        .button-container button {
            margin-left: 45px;
            padding: 8px 10px;
            font-size: 10px;
            border: none;
            cursor: pointer;
            border-radius: 5px;
        }

        .button-container button:hover {
            background-color: #aaa;
        }
    </style>
</head>
<body>

    <img src="../../../images/drySection.png" style="width:100vw; height:100vh; object-fit:cover;">
    <div class="button-container">
        <button onclick="location.href='/monitor/water'">Water Section</button>
        <button onclick="location.href='/monitor/dry'">DRY H2 & VENT Section</button>
    </div>
</body>
</html>
