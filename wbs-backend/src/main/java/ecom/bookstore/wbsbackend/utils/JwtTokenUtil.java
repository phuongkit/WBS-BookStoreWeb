//package ecom.bookstore.wbsbackend.utils;
//
//import ecom.bookstore.wbsbackend.entities.User;
//import io.jsonwebtoken.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Date;
//
///**
// * @author minh phuong
// * @created 08/09/2022 - 5:45 PM
// * @project gt-backend
// */
//@Component
//public class JwtTokenUtil {
//
//  private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
//
//  @Value("${app.jwt.jwtExpirationInMs}")
//  private long EXPIRE_DURATION;
//
//  @Value("${app.jwt.refreshExpirationDateInMs}")
//  private long REFRESH_EXPIRATION;
//
//  @Value("${app.jwt.secret}")
//  private String SECRET_KEY;
//
//  public String generateAccessToken(User userPrincipal) {
//    return Jwts.builder()
//        .setSubject(String.format("%s,%s", userPrincipal.getId(), userPrincipal.getEmail()))
//        .setIssuedAt(new Date())
//        .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
//        .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
//        .compact();
//
//  }
//
//  //used to verify a given JWT. It returns true if the JWT is verified, or false otherwise.
//  public boolean validateAccessToken(String token) {
//    try {
//      Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
//      return true;
//    } catch (SignatureException e) {
//      LOGGER.error("Invalid JWT signature: {}", e.getMessage());
//    } catch (MalformedJwtException e) {
//      LOGGER.error("Invalid JWT token: {}", e.getMessage());
//    } catch (ExpiredJwtException e) {
//      LOGGER.error("JWT token is expired: {}", e.getMessage());
//    } catch (UnsupportedJwtException e) {
//      LOGGER.error("JWT token is unsupported: {}", e.getMessage());
//    } catch (IllegalArgumentException e) {
//      LOGGER.error("JWT claims string is empty: {}", e.getMessage());
//    }
//
//    return false;
//  }
//
//  //gets the value of the subject field of a given token.
//  //The subject contains User ID and email, which will be used to recreate a User object.
//  public String getSubject(String token) {
//    return parseClaims(token).getSubject();
//  }
//
//  private Claims parseClaims(String token) {
//    return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
//  }
//
////  public String getUserNameFromJwtToken(String token) {
////    return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
////  }
//
//  public String getAccessToken(HttpServletRequest request) {
//    String token = null;
//    try {
//      String header = request.getHeader("Authorization");
//      token = header.split(" ")[1].trim();
//    } catch(ArrayIndexOutOfBoundsException ex){
//      LOGGER.error("Bearer is null", ex.getMessage());
//    }
//    return token;
//  }
//
//  public String getUserNameFromJwtToken(HttpServletRequest request) {
//    return this.getSubject(getAccessToken(request)).split(",")[1];
//  }
//
//  public String setExpiredJwtToken(String token) {
//    return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().setExpiration(new Date()).getSubject();
//  }
//
//  public String refreshToken(String token) {
//    final Date createdDate = new Date();
//    final Date expirationDate = new Date(createdDate.getTime() + REFRESH_EXPIRATION);
//
//    final Claims claims = parseClaims(token);
//    claims.setIssuedAt(createdDate);
//    claims.setExpiration(new Date());
//
//    return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
//  }
//}
