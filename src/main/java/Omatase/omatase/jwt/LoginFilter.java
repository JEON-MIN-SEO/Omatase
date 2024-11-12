package Omatase.omatase.jwt;

import Omatase.omatase.DTO.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collection;
import java.util.Iterator;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {

        super();
        this.setFilterProcessesUrl("/api/login"); // 로그인 경로 변경
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        //클라이언트 요청에서 username, password 추출
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        System.out.println(username);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

        //tokenに盛り込んだ検証のためのAuthentication Managerで伝達
        return authenticationManager.authenticate(authToken);
    }

    //ログイン成功時に実行するメソッド(ここでJWTを発行する)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        // CustomUserDetailsからuserIdを取得する
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        // ユーザーID抽出
        Long userId = customUserDetails.getUserEntity().getId();

        // 権限抽出（ちゅうしゅつ）
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();
        // JWT生成(userIdを含めて)
        String token = jwtUtil.createJwt(userId, role);
        if (token == null) {
            // トークン生成失敗ログ追加
            System.out.println("JWT 토큰 생성 실패");
        }

        //返還する際、{Authorization:Bearerトークンナンバー}で構成されなければならない。
        response.addHeader("Authorization", "Bearer " + token);
    }

    //ログイン失敗時に実行するメソッド
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
    }
}


