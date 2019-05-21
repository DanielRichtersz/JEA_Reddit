package danielrichtersz.security;

import danielrichtersz.models.JwtAuthenticationToken;
import danielrichtersz.models.Redditor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;
import java.io.IOException;

public class JwtAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter {

    public JwtAuthenticationTokenFilter() {
        super("/api/**");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {

        String header = httpServletRequest.getHeader("Authorization");

        JwtGenerator jwtGenerator = new JwtGenerator();
        String newToken = jwtGenerator.generate(new Redditor("newRedditor", "newPassword"));

        if (header == null || !header.startsWith("Token ")) {
            if (httpServletRequest.getMethod().equals("OPTIONS")) {
                JwtAuthenticationToken newAuthToken = new JwtAuthenticationToken(newToken);
                return getAuthenticationManager().authenticate(newAuthToken);
            }
            throw new RuntimeException("JWT Token is missing");
        }

        String authenticationToken = header.substring(6);

        JwtAuthenticationToken token = new JwtAuthenticationToken(authenticationToken);
        return getAuthenticationManager().authenticate(token);
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
        }
        else {
            super.successfulAuthentication(request, response, chain, authResult);
            chain.doFilter(request, response);
        }
    }
}
