package ecom.bookstore.wbsbackend.services.impls;

import ecom.bookstore.wbsbackend.entities.Location;
import ecom.bookstore.wbsbackend.repositories.LocationRepo;
import ecom.bookstore.wbsbackend.services.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author minh phuong
 * @created 26/09/2022 - 3:36 PM
 */
@Service
@Transactional
public class LocationServiceImpl implements LocationService {
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  private LocationRepo locationRepo;

  @Autowired
  public void LocationRepo(LocationRepo locationRepo) {
    this.locationRepo = locationRepo;
  }

  @Override
  public Location getLocation(Location location) {
    if (location.getProvince() == null) {
      return null;
    }
    List<Location> locationList = this.locationRepo.findAllByCommuneAndDistrictAndProvince(location.getCommune(), location.getDistrict(), location.getProvince());
    if (locationList.size() < 1) {
      return null;
    }
    return locationList.get(0);
  }

  @Override
  public Location saveLocation(Location location) {
    if (location.getProvince() == null) {
      return null;
    }
    List<Location> locationList = this.locationRepo.findAllByCommuneAndDistrictAndProvince(location.getCommune(), location.getDistrict(), location.getProvince());
    if (locationList.size() < 1) {
      return this.locationRepo.save(location);
    }
    return locationList.get(0);
  }
}
