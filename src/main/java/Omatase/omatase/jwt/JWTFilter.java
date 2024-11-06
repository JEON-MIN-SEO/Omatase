package Omatase.omatase.jwt;

import Omatase.omatase.DTO.CustomUserDetails;
import Omatase.omatase.entity.UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //requestでAuthorizationヘッダーを探す
        String authorization= request.getHeader("Authorization");

        //Authorizationヘッダー検証
        if (authorization == null || !authorization.startsWith("Bearer ")) {

            System.out.println("token null");
            filterChain.doFilter(request, response);
            return;
            //条件が該当する場合はメソッド終了(必須)

        }
        System.out.println("authorization now");
        //Bearer部分を除去した後、純粋トークンのみ獲得
        String token = authorization.split(" ")[1];

        //トークン消滅時間検証
        if (jwtUtil.isExpired(token)) {

            System.out.println("token expired");
            filterChain.doFilter(request, response);

            //条件が該当する場合はメソッド終了(必須)
            return;
        }

        Long userId = jwtUtil.getUserId(token);
        String role = jwtUtil.getRole(token);

        //userEntityを生成して値set
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        userEntity.setPassword("temppassword");
        userEntity.setRole(role);

        //UserDetailsに会員情報オブジェクトを入れる
        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

        //スプリング セキュリティ認証トークンの作成
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        //セッションにユーザー登録
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
