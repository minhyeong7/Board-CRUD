// ✅ CSRF 토큰을 가져오는 함수
function csrf() {
  // meta 태그에서 CSRF 토큰과 헤더 이름을 추출
  const token = document.querySelector('meta[name="_csrf"]')?.content;
  const header = document.querySelector('meta[name="_csrf_header"]')?.content || 'X-CSRF-TOKEN'; // 기본값 설정

  if (!token) console.warn('CSRF token not found in meta tags.'); // 토큰 없으면 경고

  // header와 token 값을 객체 형태로 반환
  return { header, token };
}

// ✅ 삭제 기능
const deleteButton = document.getElementById('delete-btn');
if (deleteButton) {
  deleteButton.addEventListener('click', async () => {
    const id = document.getElementById('article-id')?.value;
    if (!id) return alert('id가 없습니다');

    const { header, token } = csrf();

    // DELETE 요청 (CSRF 헤더 포함)
    const res = await fetch(`/api/articles/${id}`, {
      method: 'DELETE',
      headers: token ? { [header]: token } : undefined,
      credentials: 'same-origin' // 쿠키 기반 인증을 위한 설정
    });

    if (!res.ok) return alert('삭제 실패: ' + res.status);

    alert("삭제가 완료되었습니다.");
    location.replace('/articles'); // 삭제 후 목록으로 이동
  });
}

// ✅ 수정 기능
const modifyButton = document.getElementById('modify-btn');
if (modifyButton) {
  modifyButton.addEventListener('click', async () => {
    const id = document.getElementById('article-id')?.value;
    if (!id) return alert('id가 없습니다.');

    const body = {
      title: document.getElementById('title').value,
      content: document.getElementById('content').value
    };

    const { header, token } = csrf();

    // PUT 요청 (게시글 수정)
    const res = await fetch(`/api/articles/${id}`, {
      method: 'PUT',
      headers: {
        "Content-Type": "application/json", // JSON 형식 명시
        ...(token ? { [header]: token } : {}) // CSRF 헤더 동적 포함
      },
      body: JSON.stringify(body), // 본문 JSON으로 전송
      credentials: 'same-origin' // 인증 쿠키 포함
    });

    if (!res.ok) return alert('수정 실패: ' + res.status);

    alert('수정이 완료되었습니다.');
    location.replace(`/articles/${id}`); // 수정 후 상세 페이지로 이동
  });
}

// ✅ 생성 기능
const createButton = document.getElementById('create-btn');
if (createButton) {
  createButton.addEventListener('click', async () => {
    const body = {
      title: document.getElementById('title').value,
      content: document.getElementById('content').value
    };

    const { header, token } = csrf();

    // POST 요청 (게시글 생성)
    const res = await fetch('/api/articles', {
      method: 'POST',
      headers: {
        "Content-Type": "application/json",
        ...(token ? { [header]: token } : {}) // CSRF 헤더 동적 포함
      },
      body: JSON.stringify(body),
      credentials: 'same-origin' // 인증 쿠키 포함 (Spring Security 보호 리소스 접근 가능하게)
    });

    if (!res.ok) return alert('등록 실패: ' + res.status);

    alert('등록 완료되었습니다');
    location.replace('/articles'); // 등록 후 목록으로 이동
  });
}
