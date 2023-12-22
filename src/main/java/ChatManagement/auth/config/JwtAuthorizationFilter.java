package ChatManagement.auth.config;

import ChatManagement.auth.application.JwtProvider;
import ChatManagement.auth.error.InvalidTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {

        if (request.getRequestURI().startsWith("/oauth2") | request.getRequestURI().startsWith("/refresh")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String accessToken = jwtProvider.parseAccessToken(authHeader);

        if (StringUtils.hasText(accessToken) && jwtProvider.validateToken(accessToken)) {
            // 권한이 USER 뿐이므로 로그인 한 모든 사용자에게 권한 부여
            List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
            Authentication authentication = new UsernamePasswordAuthenticationToken(jwtProvider.getUserIdFromToken(accessToken), null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            throw new InvalidTokenException("예상치 못한 토큰 오류");
        }

        // 다음 Filter를 실행하기 위한 코드. 마지막 필터라면 필터 실행 후 리소스를 반환한다.
        filterChain.doFilter(request, response);
    }
}
