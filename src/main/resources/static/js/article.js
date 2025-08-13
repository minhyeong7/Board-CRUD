function csrf() {
  const token = document.querySelector('meta[name="_csrf"]')?.content;
  const header = document.querySelector('meta[name="_csrf_header"]')?.content || 'X-CSRF-TOKEN';
  if (!token) console.warn('CSRF token not found in meta tags.');
  return { header, token };
}

// 삭제 기능: 삭제 버튼 클릭 시 해당 게시글을 삭제 요청
const deleteButton = document.getElementById('delete-btn');
if(deleteButton){
    deleteButton.addEventListener('click', async () => {
        // 게시글 ID 가져오기 (없으면 경고 후 종료)
        const id = document.getElementById('article-id')?.value;
        if(!id) return alert('id가 없습니다');

        // DELETE 요청 보내기
//        const res = await fetch(`/api/articles/${id}`, { method: 'DELETE' });

        const { header, token } = csrf();
           const res = await fetch(`/api/articles/${id}`, {
              method: 'DELETE',
              headers: token ? { [header]: token } : undefined,
              credentials: 'same-origin'
            });

        // 응답 실패 시 에러 메시지 출력
        if(!res.ok) return alert('삭제 실패: ' + res.status);

        // 성공 시 알림 후 게시글 목록 페이지로 이동
        alert("삭제가 완료되었습니다.");
        location.replace('/articles');
    });
}

// 수정 기능: 수정 버튼 클릭 시 해당 게시글 수정 요청
const modifyButton = document.getElementById('modify-btn');
if(modifyButton) {
    modifyButton.addEventListener('click', async () => {
        // 게시글 ID 가져오기 (없으면 경고 후 종료)
        const id = document.getElementById('article-id')?.value;
        if(!id) return alert('id가 없습니다.');

        // 입력된 제목과 내용을 객체로 준비
        const body = {
            title: document.getElementById('title').value,
            content: document.getElementById('content').value
        };
        const { header, token } = csrf();
            const res = await fetch(`/api/articles/${id}`, {
              method: 'PUT',
              headers: {
                "Content-Type": "application/json",
                ...(token ? { [header]: token } : {})
              },
              body: JSON.stringify(body),
              credentials: 'same-origin'
            });
//        // PUT 요청 보내기 (JSON 데이터 전송)
//        const res = await fetch(`/api/articles/${id}`, {
//            method: 'PUT',
//            headers: { "Content-Type": "application/json" },
//            body: JSON.stringify(body)
//        });

        // 응답 실패 시 에러 메시지 출력
        if(!res.ok) return alert('수정 실패: ' + res.status);

        // 성공 시 알림 후 해당 게시글 상세 페이지로 이동
        alert('수정이 완료되었습니다.');
        location.replace(`/articles/${id}`);
    });
}

// 생성 기능: 생성 버튼 클릭 시 새 게시글 생성 요청
const createButton = document.getElementById('create-btn');
if(createButton){
    createButton.addEventListener('click', async () => {
        // 입력된 제목과 내용을 객체로 준비
        const body = {
            title: document.getElementById('title').value,
            content: document.getElementById('content').value
        };


        const { header, token } = csrf();
            const res = await fetch('/api/articles', {
              method: 'POST',
              headers: {
                "Content-Type": "application/json",
                ...(token ? { [header]: token } : {})
              },
              body: JSON.stringify(body),
              credentials: 'same-origin'
            });



//        // POST 요청 보내기 (JSON 데이터 전송)
//        const res = await fetch('/api/articles', {
//            method: 'POST',
//            headers: { "Content-Type": "application/json" },
//            body: JSON.stringify(body)
//        });

        // 응답 실패 시 에러 메시지 출력
        if(!res.ok) return alert('등록 실패: ' + res.status);

        // 성공 시 알림 후 게시글 목록 페이지로 이동
        alert('등록 완료되었습니다');
        location.replace('/articles');
    });
}
