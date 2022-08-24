package com.codewithashish.blog.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.codewithashish.blog.config.AppConstants;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String jwtToken = null;
		String userName = null;
		String requestToken = request.getHeader("Authorization");

		System.out.println("AuthFilter requestToken############### " + requestToken);
		if (requestToken != null && requestToken.startsWith(AppConstants.TOKEN_PREFIX)) {
			jwtToken = requestToken.substring(7);
			System.out.println("AuthFilter###############" + jwtToken);
			try {
				userName = jwtTokenHelper.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {

				System.out.println("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token has expired.");

			} catch (MalformedJwtException e) {
				System.out.println("Invalid JWT Token");
			}

			// Once we get the token, now validate
			if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
				if (jwtTokenHelper.validateToken(jwtToken, userDetails)) {
					// Sb kuchh sahi chl rha hai
					// Authentication krna hai

					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

				} else {
					System.out.println("Invalid jwt token");
				}

			} else {
				System.out.println("Username is null or context is not null");
			}

		} else {
			System.out.println("Token is not started with bearer");
		}

		filterChain.doFilter(request, response);

	}

}
