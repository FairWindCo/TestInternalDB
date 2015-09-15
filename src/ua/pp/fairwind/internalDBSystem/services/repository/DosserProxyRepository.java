package ua.pp.fairwind.internalDBSystem.services.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ua.pp.fairwind.internalDBSystem.datamodel.proxy.DosserProxy;

/**
 * Created by Сергей on 15.09.2015.
 */
public interface DosserProxyRepository extends JpaSpecificationExecutor<DosserProxy> {
}
