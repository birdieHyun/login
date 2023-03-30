package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.web.session.SessionManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;

class SessionManagerTest {

    SessionManager sessionManager = new SessionManager();

    @Test
    void sessionTest() {

        MockHttpServletResponse response = new MockHttpServletResponse();

        // 세션 생성
        Member member = new Member();
        sessionManager.createSession(member, response);

        // 요청에 응답 쿠키 저장
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies()); // Mock만 getCookies 존재

        // 세션 조회
        Object session = sessionManager.getSession(request);

        Assertions.assertThat(session).isEqualTo(member);

        // 세션 만료
        sessionManager.expire(request);
        Object session1 = sessionManager.getSession(request);
        Assertions.assertThat(session1).isNull();
    }
}
