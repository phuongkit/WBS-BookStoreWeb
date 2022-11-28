package ecom.bookstore.wbsbackend.handlers.user;

import java.util.Map;

/**
 * @author minh phuong
 * @created 27/11/2022 - 8:58 PM
 */
public class FacebookOAuth2UserInfo extends OAuth2UserInfo {
  public FacebookOAuth2UserInfo(Map<String, Object> attributes) {
    super(attributes);
  }

  @Override
  public String getId() {
    return (String) attributes.get("id");
  }

  @Override
  public String getName() {
    return (String) attributes.get("name");
  }

  @Override
  public String getEmail() {
    return (String) attributes.get("email");
  }

  @Override
  public String getImageUrl() {
    if(attributes.containsKey("picture")) {
      Map<String, Object> pictureObj = (Map<String, Object>) attributes.get("picture");
      if(pictureObj.containsKey("data")) {
        Map<String, Object>  dataObj = (Map<String, Object>) pictureObj.get("data");
        if(dataObj.containsKey("url")) {
          return (String) dataObj.get("url");
        }
      }
    }
    return null;
  }
}