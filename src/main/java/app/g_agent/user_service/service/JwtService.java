package app.g_agent.user_service.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	@Value("${security.jwt.secret-key}")
	private String secretKey;

	@Value("${security.jwt.expiration-time}")
	private long jwtExpiration;

	@Value("${security.jwt.refresh-expiration-time}")
	private long jwtRefreshExpiration;

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public String generateToken(UserDetails userDetails, Long expirationPeriod) {
		return generateToken(new HashMap<>(), userDetails, expirationPeriod);
	}

	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails,Long expirationPeriod) {
		return buildToken(extraClaims, userDetails, expirationPeriod);
	}

	public long getExpirationTime() {
		return jwtExpiration;
	}
	
	public long getRefreshExpirationTime() {
		return jwtRefreshExpiration;
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}

	private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
		return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private Claims extractAllClaims(String token) {
		Claims claims = null;
		try {
			claims = Jwts.parserBuilder().setSigningKey(getSignInKey()) // Pass the SecretKey directly
					.build().parseClaimsJws(token).getBody();
			return claims;
		} catch (ExpiredJwtException e) {
			throw new RuntimeException("Token has expired");
		} catch (MalformedJwtException e) {
			throw new RuntimeException("Malformed token");
		} catch (SignatureException e) {
			throw new RuntimeException("Invalid token signature");
		} catch (Exception e) {
			throw new RuntimeException("Invalid token");
		}

	}

	private SecretKey getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes); // This returns a SecretKey
	}

}
