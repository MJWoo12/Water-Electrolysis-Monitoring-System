<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>수전해 모니터링 시스템</title>
   <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <link rel="icon" href="../../../icon/favicon.png" type="image/png">
    <script  src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
</head>
<body>
<div class="content">
  <div class="card" id="loginCard">
<%--    <h1 class="business-logo">H2NET</h1>--%>
    <span class="title">수전해 모니터링 시스템</span>
    <p class="sub-title">
      Water Electrolysis Monitoring System<br/>
      Management Platform
    </p>

    <form class="login-form form" id="loginForm">
      <div class="input-container">
        <label id="loginId-label" class="label">아이디</label>
        <input class="input" type="text" id="loginId" name="loginId" placeholder="아이디"/>
      </div>

      <div class="input-container">
        <label id="password-label" class="label">비밀번호</label>
        <input class="input" type="password" id="password" name="password" placeholder="비밀번호" />
        <i class="button-icon view-icon" id="password-view-button" style="cursor:pointer;"></i>
      </div>

      <button class="login-button" type="submit" id="loginButton" >로그인</button>
      <p style="margin-top:10px; text-align: center;">
        아직 회원이 아니신가요?
        <span id="joinText" style="color:blue; cursor:pointer; text-decoration:underline;">
        회원가입
        </span>
      </p>
    </form>
  </div>

    <div class="card" id="joinCard" style="display:none;">
        <span class="title">회원가입</span>

        <form class="signup-form form" id="signupForm">
            <div class="input-container">
                <label for="joinUserNm" class="label">이름 <span class="required">*</span></label>
                <input class="input" type="text" id="joinUserNm" name="joinUserNm" placeholder="이름" required
                       pattern="^[가-힣]{2,5}$"
                       title="이름은 2~5글자의 한글만 입력 가능합니다."/>
            </div>

            <div class="input-container">
                <label for="joinUserId" class="label">아이디 <span class="required">*</span></label>
                <div style="display: flex; gap: 10px; width: 100%;">
                    <input class="input" type="text" id="joinUserId" name="joinUserId" placeholder="아이디를 입력해 주세요." required/>
                    <button type="button" id="idCheckButton">중복확인</button>
                </div>
            </div>

            <div class="input-container">
                <label for="joinUserPw" class="label">비밀번호 <span class="required">*</span></label>
                <input class="input" type="password" id="joinUserPw" name="joinUserPw" placeholder="비밀번호" required/>
            </div>

            <div class="input-container">
                <label for="joinPwConf" class="label">비밀번호 확인 <span class="required">*</span></label>
                <input class="input" type="password" id="joinPwConf" name="joinPwConf" placeholder="비밀번호 확인" required/>
                <div id="pwConfMessage" style="color: red; margin-top: 5px;"></div>
            </div>

            <div class="input-container">
                <label for="joinUserTel" class="label">전화번호 <span class="required">*</span></label>
                <input class="input" type="text" id="joinUserTel" name="joinUserTel" placeholder="전화번호(-제외)" required/>
            </div>

            <div class="input-container">
                <label for="joinUserOrg" class="label">업체명 <span class="required">*</span></label>
                    <select id="joinUserOrg" name="joinUserOrg">
                        <option value="">업체명을 선택하세요.</option>
                        <option value="에이치투넷">에이치투넷</option>
                        <option value="현대 로템">현대 로템</option>
                        <option value="라이트 브릿지">라이트 브릿지</option>
                        <option value="서울 시립대학교">서울 시립대학교</option>
                    </select>
            </div>

            <button class="join-button" type="submit" id="joinButton">회원가입</button>
            <p style="margin-top:10px; text-align: center;">
                이미 회원이신가요?
                <span id="loginText" style="color:blue; cursor:pointer; text-decoration:underline;">
          로그인
        </span>
            </p>
        </form>
    </div>

</div>
<script>
    let flag = false;
    function toggleScroll() {
        if (window.innerWidth < document.documentElement.clientWidth) {
            document.body.style.overflow = 'hidden'; // 창 크기가 클 때 스크롤 숨김
        } else {
            document.body.style.overflow = 'auto';  // 창 크기가 작을 때 스크롤 보이게
        }
    }

    toggleScroll();

    window.addEventListener('resize', toggleScroll);

  document.addEventListener('DOMContentLoaded', function () {
      const joinPw = document.getElementById('joinUserPw');
      const joinPwConf = document.getElementById('joinPwConf');
      const pwConfMessage = document.getElementById('pwConfMessage');

      function checkPasswordMatch() {
          if (!joinPwConf.value) {
              pwConfMessage.textContent = '';
              return;
          }

          if (joinPw.value === joinPwConf.value) {
              pwConfMessage.style.color = 'green';
              pwConfMessage.textContent = '비밀번호가 일치합니다.';
          } else {
              pwConfMessage.style.color = 'red';
              pwConfMessage.textContent = '비밀번호가 일치하지 않습니다.';
          }
      }

      joinPw.addEventListener('input', checkPasswordMatch);
      joinPwConf.addEventListener('input', checkPasswordMatch);
  })


  document.getElementById('loginForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const userId = document.getElementById('loginId').value.trim();
    const userPw = document.getElementById('password').value.trim();

    if(userId == null || userPw == null){
        Swal.fire({
            icon: 'warning',
            title: '아이디와 비밀번호를 모두 입력해주세요.',
        });
        return;
    }

    // JSON 데이터 생성
    const data = {
      id: userId,
      password: userPw
    };

    fetch('/api/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
    }).then(response => response.json())
            .then(data => {
              if (data.login === true || data.login === "true") {
                if(data.user.auth === "99"){
                  Swal.fire({
                      icon: 'warning',
                      title: '관리자 승인이 필요합니다.',
                      text: '관리자에게 문의 후 로그인하세요.'
                  })
                } else if(data.user.auth === "01" || data.user.auth === "02"){
                    window.location.href = '/monitor';
                } else
                    Swal.fire({
                        icon: 'error',
                        title: data.message
                    })
              } else {
                  Swal.fire({
                      icon: 'error',
                      title: data.message
                  })
              }
            })
            .catch(error => {
              alert(error.message);
            })
  });


  const idCheckBtn = document.getElementById('idCheckButton');

  idCheckBtn.addEventListener('click', function(e) {
      e.preventDefault();

      const userIdInput = document.getElementById('joinUserId');
      const userId = document.getElementById('joinUserId').value.trim();
      console.log(userId);
      if (!userId) {
          Swal.fire({
              icon: 'warning',
              title: "아이디를 입력해주세요."
          })
          return;
      }

      if (userId.length < 8) {
          Swal.fire({
              icon: 'warning',
              title: "아이디는 8자 이상 입력해야 합니다."
          })
          return;
      }

      const validIdRegex = /^[a-zA-Z0-9]+$/;
      if (!validIdRegex.test(userId)) {
          Swal.fire({
              icon: 'warning',
              title: "아이디는 영어와 숫자만 사용할 수 있습니다."
          })
          return;
      }

      fetch('/api/join/' + userId, {
          method: 'POST'
      })
          .then(response => {
              if (response.status === 200) {
                  return response.text().then(text => {
                      Swal.fire({
                          icon: 'success',
                          title: text
                      }) // "사용 가능 아이디"
                      idCheckBtn.style.backgroundColor = '#28a745';
                      idCheckBtn.style.color = '#fff';
                      idCheckBtn.innerText = "사용 가능";
                      userIdInput.readOnly = true; // input 잠금
                      flag = true;
                  });
              } else if (response.status === 500) {
                  return response.text().then(text => {
                      Swal.fire({
                          icon: 'error',
                          title: text
                      }) // "중복된 아이디"
                      idCheckBtn.style.backgroundColor = ''; // 기본 색상으로
                      idCheckBtn.style.color = '';
                      idCheckBtn.innerText = "중복확인";
                  });
              } else if (response.status === 400) {
                  return response.text().then(text => {
                      Swal.fire({
                          icon: 'error',
                          title: text
                      }) // "잘못된 접근입니다."
                      idCheckBtn.style.backgroundColor = ''; // 기본 색상으로
                      idCheckBtn.style.color = '';
                      idCheckBtn.innerText = "중복확인";
                  });
              } else {
                  Swal.fire({
                      icon: 'error',
                      title: "예상치 못한 오류가 발생했습니다."
                  })

              }
          })
          .catch(error => {
              console.error('통신 오류:', error);
              Swal.fire({
                  icon: 'error',
                  title: "서버와 통신 중 오류가 발생했습니다."
              })
              idCheckBtn.style.backgroundColor = ''; // 기본 색상으로
              idCheckBtn.style.color = '';
              idCheckBtn.innerText = "중복확인";
          });
  });

  document.getElementById('joinUserTel').addEventListener('input', function () {
      this.value = this.value.replace(/-/, '');
      this.value = this.value.replace(/[^0-9]/g, '');
  })


  document.getElementById('signupForm').addEventListener('submit', function (e) {
      e.preventDefault();
      const userId = document.getElementById('joinUserId').value.trim();
      const userPw = document.getElementById('joinUserPw').value.trim();
      const userPwConf = document.getElementById('joinPwConf').value.trim();
      const userNm = document.getElementById('joinUserNm').value.trim();
      const userTel = document.getElementById('joinUserTel').value.trim();
      const userOrg = document.getElementById('joinUserOrg').value;

      const data = {
          userId: userId,
          userPw: userPw,
          userNm: userNm,
          phone: userTel,
          organization: userOrg
      };

      if(!flag){
          Swal.fire({
              icon: 'warning',
              title: '아이디 중복 확인 버튼을 눌러주세요.'
          })
          return;
      }
      if (!userPw) {
          Swal.fire({
              icon: 'warning',
              title: '비밀번호를 입력해주세요.'
          })
          return;
      }
      if (!userNm) {
          Swal.fire({
              icon: 'warning',
              title: '이름을 입력해주세요.'
          })
          return;
      }
      if(userPw !== userPwConf){
          Swal.fire({
              icon: 'error',
              title: '비밀번호가 일치하지 않습니다.',
          })
          return;
      }
      if(userTel.length > 12){
          Swal.fire({
              icon: 'warning',
              title: '전화번호는 11자까지만 입력해주세요.'
          })
          return;
      }
      if (userPw.length < 8) {
          Swal.fire({
              icon: 'warning',
              title: '비밀번호는 8글자 이상 입력해주세요.'
          })
          return;
      }
      if(!userOrg){
          Swal.fire({
              icon: 'warning',
              title: '업체명이 선택되지 않았습니다.'
          })
          return;
      }
      const nameRegex = /^[가-힣]{2,5}$/;

      if (!nameRegex.test(userNm)) {
          Swal.fire({
              icon: 'info',
              title: '이름은 2~5글자의 한글만 입력 가능합니다.'
          })
          return;
      }

      fetch('/api/join/insertUser', {
          method: 'POST',
          headers: {
              'Content-Type': 'application/json'
          },
          body: JSON.stringify(data)
      })
          .then(response => {
              if (response.status === 200) {
                  Swal.fire({
                      icon: 'success',
                      title: '회원가입 완료'
                  }).then((result)=> {
                      if(result.isConfirmed){
                          window.location.reload();
                      }
                  })
                  document.getElementById('signupForm').reset();
              } else if (response.status === 400) {
                  alert('잘못된 요청입니다.');
              } else if (response.status === 500) {
                  alert('가입 실패, 서버 오류가 발생했습니다.');
              } else {
                  alert('알 수 없는 오류가 발생했습니다. 상태 코드: ' + response.status);
              }
          })
          .catch(error => {
              console.error('통신 오류:', error);
              alert('서버와 통신 중 오류가 발생했습니다.');
          });
  })


  const loginCard = document.getElementById('loginCard');
  const joinCard = document.getElementById('joinCard');

  // '회원가입' 링크 클릭 시
  document.getElementById('joinText').addEventListener('click', function() {
      loginCard.style.display = 'none';
      joinCard.style.display = 'block';
  });

  // '로그인' 링크 클릭 시
  document.getElementById('loginText').addEventListener('click', function() {
      document.getElementById('signupForm').reset();
      flag = false;
      idCheckBtn.style.backgroundColor = ''; // 기본 색상으로
      idCheckBtn.style.color = '';
      idCheckBtn.innerText = "중복확인";
      document.getElementById('joinUserId').readOnly = false;
      document.getElementById('idCheckButton')
      joinCard.style.display = 'none';
      loginCard.style.display = 'block';
  });


</script>
<footer>
  <img src="../../../icon/logoImage.png" alt="H2NET" class="footer-logo"
       style="display: block; margin: 0 auto; width: 150px; position: absolute; bottom: 0; left: 50%; transform: translateX(-50%);"/>
</footer>
</body>
</html>
