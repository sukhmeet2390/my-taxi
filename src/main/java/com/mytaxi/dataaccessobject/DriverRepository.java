package com.mytaxi.dataaccessobject;

import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.OnlineStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface DriverRepository extends JpaRepository<DriverDO, Long>, JpaSpecificationExecutor<DriverDO> {

    List<DriverDO> findByOnlineStatus(OnlineStatus onlineStatus);

    List<DriverDO> findAll(@Nullable Specification<DriverDO> spec);
}
