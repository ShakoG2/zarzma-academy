package com.zarzmaacademy.configuration;

import com.zarzmaacademy.Service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String token = getTokenFromRequest(httpServletRequest);
			//System.out.println("Token-- " + token);
			if (token != null && jwtTokenUtil.validateJwtToken(token)) {
				String username = jwtTokenUtil.getUserNameFromJwtToken(token);
				//System.out.println("User Name--JwtTokenFilter-- " + username);
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				//System.out.println("Authorities--JwtTokenFilter-- " + userDetails.getAuthorities());
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			//logger.error("Cannot set user authentication: {}", e);
			throw new RuntimeException("Cannot set user authentication" + e.getMessage());
		}
		filterChain.doFilter(httpServletRequest, httpServletResponse);

	}

	private String getTokenFromRequest(HttpServletRequest request) {
		String token = request.getHeader("Authorization");

		if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
			// remove "Bearer "
			return token.split(" ")[1].trim();
		}
		return null;
	}
}
