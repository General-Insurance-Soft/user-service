package app.g_agent.user_service.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import app.g_agent.user_service.dto.Token;
import app.g_agent.user_service.dto.UserTokenResponse;
import app.g_agent.user_service.model.TokenBlackList;
import app.g_agent.user_service.model.User;
import app.g_agent.user_service.repository.TokenRepository;
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

	private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

	@Autowired
	TokenRepository tokenRepository;

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

//	public String generateToken(UserDetails userDetails, Long expirationPeriod) {
//		return generateToken(new HashMap<>(), userDetails, expirationPeriod);
//	}

	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails, Long expirationPeriod) {
		return buildToken(extraClaims, userDetails, expirationPeriod);
	}

	public long getExpirationTime() {
		return jwtExpiration;
	}

	public long getRefreshExpirationTime() {
		return jwtRefreshExpiration;
	}

	public boolean isTokenValid(String token, UserDetails userDetails, String type) {
		tokenRepository.findByToken(token).ifPresent(value -> {
			throw new RuntimeException("Invalid token");
		});
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token) && isCorrectType(token, type);
	}

	public UserTokenResponse getTokenUserResponse(User user) {
		logger.info("Attempting to generate JWT =====>");
		Map<String, Object> extraClaims = new HashMap<String, Object>();
		extraClaims.put("type", "access");
		extraClaims.put("user-id", user.getId());
		extraClaims.put("authorities", user.getAuthorities());
		String jwtToken = this.generateToken(extraClaims, user, jwtExpiration);
		extraClaims = new HashMap<String, Object>();
		extraClaims.put("type", "refresh");
		String jwtRefreshToken = this.generateToken(extraClaims, user, jwtRefreshExpiration);

		UserTokenResponse userTokenResponse = new UserTokenResponse();

		Token jwtTokenObj = new Token.Builder().token(jwtToken).expiresIn(jwtExpiration).build();

		Token jwtRefreshTokenObj = new Token.Builder().token(jwtRefreshToken).expiresIn(jwtRefreshExpiration).build();

		userTokenResponse.setJwtRefreshToken(jwtRefreshTokenObj);
		userTokenResponse.setJwtToken(jwtTokenObj);
		return userTokenResponse;
	}

	public void persistUsedToken(String token) {
		TokenBlackList tokenBlackList = new TokenBlackList();
		tokenBlackList.setId(UUID.randomUUID());
		tokenBlackList.setToken(token);
		tokenBlackList.setExpriDate(this.extractExpiration(token));
		tokenRepository.save(tokenBlackList);
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

	private boolean isCorrectType(String token, String type) {
		Claims claims = extractAllClaims(token);
		String tokenTypeString = (String) claims.get("type");
		boolean bool = tokenTypeString.equals(type);
		logger.info(
				"Validate token of type===============> " + type + " against " + tokenTypeString + " value: " + bool);
		if (tokenTypeString == null)
			return false;
		return tokenTypeString.equals(type);
	}

	private Claims extractAllClaims(String token) {
		try {
			return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
		} catch (ExpiredJwtException e) {
			throw new TokenExpiredException("Token has expired");
		} catch (MalformedJwtException e) {
			throw new MalformedTokenException("Malformed token");
		} catch (SignatureException e) {
			throw new InvalidTokenSignatureException("Invalid token signature");
		} catch (Exception e) {
			throw new InvalidTokenException("Invalid token");
		}
	}

	private SecretKey getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes); // This returns a SecretKey
	}

	public class TokenExpiredException extends RuntimeException {
		public TokenExpiredException(String message) {
			super(message);
		}
	}

	public class MalformedTokenException extends RuntimeException {
		public MalformedTokenException(String message) {
			super(message);
		}
	}

	public class InvalidTokenSignatureException extends RuntimeException {
		public InvalidTokenSignatureException(String message) {
			super(message);
		}
	}

	public class InvalidTokenException extends RuntimeException {
		public InvalidTokenException(String message) {
			super(message);
		}
	}

}
