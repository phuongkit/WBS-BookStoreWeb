package ecom.bookstore.wbsbackend.services;

import ecom.bookstore.wbsbackend.entities.Location;

/**
 * @author minh phuong
 * @created 26/09/2022 - 3:27 PM
 */
public interface LocationService {
  Location getLocation(Location location);
  Location saveLocation(Location location);
}
