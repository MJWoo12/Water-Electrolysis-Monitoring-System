<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
  <title>수전해 모니터링 시스템</title>
  <link rel="icon" href="../../../icon/favicon.png" type="image/png">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">
  <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
  <%@ page session="true" %>
  <%
    String userAuth = (String) session.getAttribute("auth");
  %>
  <div class="header">
    <div class="logo">
      <a href="/monitor">
        <img src="/icon/logoImage.png" alt="Logo" class="logo-img">
      </a>
    </div>
    <div class="header-icons">
      <% if ("01".equals(userAuth)) { %>
      <div class="bell-wrapper">
        <a href="#" class="bell-icon" id="bellBtn" title="관리자 페이지">
          🔔
        </a>
      </div>
      <% } %>
      <div class="user-menu">
        <img src="../../../icon/user.png" alt="User Icon" class="user-icon">
        <div class="dropdown">
          <a href="/mypage">마이페이지</a>
          <a href="#" id="logoutBtn">로그아웃</a>
        </div>
      </div>
    </div>
  </div>
  <script>
    const newLogin = '<%= session.getAttribute("newLogin") %>' === 'true';
    const userAuth = '<%= session.getAttribute("auth") %>';
    const seenToast = localStorage.getItem('seenToast');
    if(userAuth === "01"){
      if (newLogin && !seenToast) {
        showToast("새로운 권한 요청이 있습니다.");
        localStorage.setItem('seenToast', 'true');
      }
    }
    document.getElementById('logoutBtn').addEventListener('click', function (e) {
      e.preventDefault();
      localStorage.removeItem('seenToast');
      window.location.href = "/logout";
    })

      const bellBtn = document.getElementById('bellBtn');
      bellBtn.addEventListener('click', function (e) {
        e.preventDefault();
        window.location.href = "/admin";
      });

    // SweetAlert2로 토스트 메시지 표시 함수
    function showToast(message) {
      Swal.fire({
        toast: true, // 토스트 모드 활성화
        position: 'top-end', // 위치 설정
        icon: 'info',
        title: message,
        showConfirmButton: true,
        timer: 3000, // 3초 후 자동으로 사라짐
        timerProgressBar: true, // 타이머 진행 바 표시
        didOpen: () => {
          Swal.showLoading() // 로딩 애니메이션 표시
        },
        willClose: () => {
          Swal.hideLoading() // 로딩 애니메이션 숨기기
        }
      });
    }

  </script>

  <style>
    /* 헤더 전체 */
    .header {
      height: 60px;
      background-color: #00aaff; /* 기존 톤에 맞게 */
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 0 20px;
      color: white;
      position: relative;
    }
    .header-icons {
      display: flex;
      align-items: center;
      gap: 20px;
    }

    .bell-icon {
      font-size: 20px;
      color: white;
      text-decoration: none;
      transition: transform 0.2s;
    }

    .bell-icon:hover {
      transform: scale(1.2);
    }
    /* 로고 텍스트 */
    .logo {
      font-size: 20px;
      font-weight: bold;
    }

    .logo a {
      display: flex;
      align-items: center;
      text-decoration: none;
    }

    .logo-img {
      height: 40px; /* 헤더 높이(60px) 안에 자연스럽게 맞게 */
    }

    /* 우측 메뉴 */
    .user-menu {
      position: relative;
      cursor: pointer;
    }

    .user-icon {
      font-size: 22px;
    }

    .bell-wrapper {
      position: relative;
      display: inline-block;
    }

    .notification-dot {
      position: absolute;
      top: 0;
      right: 0;
      width: 8px;
      height: 8px;
      background-color: red;
      border-radius: 50%;
    }

    /* 드롭다운 메뉴 */
    .dropdown {
      position: absolute;
      top: 100%;
      right: 0;
      background-color: white;
      color: black;
      border-radius: 6px;
      box-shadow: 0 2px 8px rgba(0,0,0,0.15);
      overflow: hidden;
      display: none;
      flex-direction: column;
      min-width: 120px;
    }

    .dropdown a {
      padding: 10px;
      text-decoration: none;
      color: black;
      display: block;
    }

    .dropdown a:hover {
      background-color: #f0f0f0;
    }

    /* 아이콘 클릭 시 메뉴 토글 */
    .user-menu:hover .dropdown {
      display: flex;
    }
  </style>
</head>

