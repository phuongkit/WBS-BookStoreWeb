package ecom.bookstore.wbsbackend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author minh phuong
 * @created 27/11/2022 - 8:39 PM
 */
@ConfigurationProperties(prefix = "app")
@Configuration("appProperties")
public class AppProperties {
  private final Auth auth = new Auth();
  private final OAuth2 oauth2 = new OAuth2();

  public static class Auth {
    private String secret;
    private long jwtExpirationInMs;

    public String getTokenSecret() {
      return secret;
    }

    public void setTokenSecret(String tokenSecret) {
      this.secret = tokenSecret;
    }

    public long getTokenExpirationMsec() {
      return jwtExpirationInMs;
    }

    public void setTokenExpirationMsec(long tokenExpirationMsec) {
      this.jwtExpirationInMs = tokenExpirationMsec;
    }
  }

  public static final class OAuth2 {
    private List<String> authorizedRedirectUris = new ArrayList<>();

    public List<String> getAuthorizedRedirectUris() {
      return authorizedRedirectUris;
    }

    public OAuth2 authorizedRedirectUris(List<String> authorizedRedirectUris) {
      this.authorizedRedirectUris = authorizedRedirectUris;
      return this;
    }
  }

  public Auth getAuth() {
    return auth;
  }

  public OAuth2 getOauth2() {
    return oauth2;
  }
}
