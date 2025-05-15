<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<html>
<head>
    <title>Title</title>
    <style>
        html, body {
            margin: 0;
            padding: 0;
            overflow: auto;
            width: 100vw;
            height: 100vh;
            position: relative;
        }

        img.background {
            width: 100vw;
            height: 100vh;
            object-fit: cover;
            position: absolute;
            top: 0;
            left: 0;
            z-index: 0;
        }

        .button-container {
            position: absolute;
            bottom: -45px;
            right: 130px;
            z-index: 10;
        }

        .button-container button {
            margin: 0px 20px;
            font-size: 12px;
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
    <div class="content">
        <img src="../../../images/waterSection.png" style="width:100vw; height:100vh; object-fit:cover;">
        <div class="button-container">
            <button onclick="location.href='/monitor/wet'">WET H2 Section</button>
            <button onclick="location.href='/monitor/dry'">DRY H2 & VENT Section</button>
        </div>
    </div>
</body>
</html>
