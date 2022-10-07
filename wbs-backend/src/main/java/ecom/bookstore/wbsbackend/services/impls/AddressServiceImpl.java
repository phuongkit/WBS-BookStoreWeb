package ecom.bookstore.wbsbackend.services.impls;

import ecom.bookstore.wbsbackend.entities.Address;
import ecom.bookstore.wbsbackend.repositories.AddressRepo;
import ecom.bookstore.wbsbackend.services.AddressService;
import ecom.bookstore.wbsbackend.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author minh phuong
 * @created 01/10/2022 - 1:02 PM
 * @project gt-backend
 */
@Service
@Transactional
public class AddressServiceImpl implements AddressService {

  private AddressRepo addressRepo;

  @Autowired public void addressRepo(AddressRepo addressRepo) {
    this.addressRepo = addressRepo;
  }

  private LocationService locationService;

  @Autowired public void LocationService(LocationService locationService) {
    this.locationService = locationService;
  }

  @Override public Address saveAddress(Address address) {
    return this.addressRepo.findById(address.getId()).orElseGet(() -> this.addressRepo.save(address));
  }
}
