<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, java.text.*" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>관리자 페이지</title>
    <style>
        body { font-family: 'Noto Sans KR', sans-serif; background: #f4f6f8; padding: 60px; }
        .container { background: white; max-width: 900px; margin: 0 auto; padding: 30px; border-radius: 10px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border-bottom: 1px solid #eee; padding: 12px; text-align: center; }
        th { background: #f1f5f9; }
        .btn-back { margin-top: 20px; background: #00aaff; color: white; padding: 10px 20px; border: none; border-radius: 6px; text-decoration: none; display: inline-block; }
        /* 셀렉트 박스 스타일 */
        select[id^="roleSelect-"] {
            padding: 6px 10px;
            border: 1px solid #ccc;
            border-radius: 6px;
            background-color: #f9f9f9;
            font-size: 14px;
            outline: none;
            transition: border-color 0.3s;
        }

        select[id^="roleSelect-"]:hover {
            border-color: #888;
        }

        /* 권한 부여 버튼 스타일 */
        .btn-grant {
            margin-left: 8px;
            padding: 6px 12px;
            background-color: #00aaff; /* 녹색 */
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 14px;
            transition: background-color 0.3s, transform 0.2s;
        }

        .btn-grant:hover {
            background-color: #0088cc;
            transform: scale(1.05);
        }
    </style>
    <script  src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
</head>
<body>
<div class="container">
    <h2>승인 요청 목록</h2>

    <table id="userTable">
        <thead>
        <tr>
            <th>아이디</th>
            <th>이름</th>
            <th>업체명</th>
            <th>권한</th>
            <th>가입일</th>
            <th>권한부여</th>
        </tr>
        </thead>
        <tbody id="userTableBody">

        </tbody>
    </table>

    <a href="#" class="btn-back" id="btnBack">← 돌아가기</a>
</div>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        loadUserList();
    });

    document.getElementById('btnBack').addEventListener('click', function(event) {
        event.preventDefault();
        window.location.href = "/monitor/water"

    });
    function loadUserList() {
        fetch('/api/admin')
            .then(response => {
                if(response.status === 204){
                    const tbody = document.getElementById('userTableBody');
                    tbody.innerHTML = '<tr><td colspan="6">미승인 사용자가 없습니다.</td></tr>';
                    return;
                }
                if (!response.ok) {
                    const tbody = document.getElementById('userTableBody');
                    tbody.innerHTML = '<tr><td colspan="6">미승인 사용자가 없습니다.</td></tr>';
                    return;
                }
                return response.json();
            })
            .then(data => {
                if (!data) {
                    const tbody = document.getElementById('userTableBody');
                    tbody.innerHTML = '<tr><td colspan="6">미승인 사용자가 없습니다.</td></tr>';
                    return;
                }
                const tbody = document.getElementById('userTableBody');
                tbody.innerHTML = ''; // 기존 내용 초기화
                data.forEach(user => {
                    const regDate = new Date(user.reg_date).toLocaleDateString();
                    let auth = user.auth;
                    if(auth === "99"){
                        auth = "미승인";
                    }
                    const tr = document.createElement('tr');
                    tr.innerHTML =
                        '<td>' + user.id + '</td>' +
                        '<td>' + user.name + '</td>' +
                        '<td>' + user.organization + '</td>' +
                        '<td>' + auth + '</td>' +
                        '<td>' + regDate + '</td>' +
                        '<td>' +
                        '<select id="roleSelect-' + user.id + '">' +
                        '<option value=""' + (user.auth === "" ? ' selected' : '') + '>권한을 선택하세요.</option>' +
                        '<option value="02"' + (user.auth === "02" ? ' selected' : '') + '>사용자</option>' +
                        '<option value="01"' + (user.auth === "01" ? ' selected' : '') + '>관리자</option>' +
                        '</select>' +
                        '<button class="btn-grant" onclick="grantAuth(\'' + user.id + '\')">확인</button>' +
                        '</td>';
                    tbody.appendChild(tr);
                });
            })
            .catch(error => {
                console.error(error);
                const tbody = document.getElementById('userTableBody');
                tbody.innerHTML = '<tr><td colspan="6">데이터를 불러오는 중 오류가 발생했습니다.</td></tr>';
            });
    }

    function grantAuth(userId) {
        const selectedAuth = document.getElementById('roleSelect-' + userId).value;

        if(selectedAuth === ""){
            Swal.fire({
                icon:"warning",
                title:"권한을 선택하세요."
            })
        }else{
            Swal.fire({
                icon: "warning",
                title: "해당 사용자에게 권한을 부여하시겠습니까?",
                showCancelButton: true,
                confirmButtonText: "확인",
                cancelButtonText: "취소"
            }).then(result => {
                if (result.isConfirmed) {
                    fetch('/api/updateAuth', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify({
                            id: userId,
                            auth: selectedAuth })
                    })
                        .then(res => {
                            if (res.ok) {
                                Swal.fire({
                                    icon: "success",
                                    title: "권한이 성공적으로 부여되었습니다."
                                });
                                loadUserList(); // 성공하면 리스트 새로고침
                            } else {
                                Swal.fire({
                                    icon: "error",
                                    title: "권한 부여에 실패했습니다."
                                });
                            }
                        })
                        .catch(err => {
                            console.error(err);
                            Swal.fire({
                                icon: "error",
                                title: "오류가 발생했습니다."
                            });
                        });
                }
            });
        }

    }
</script>
</body>
</html>