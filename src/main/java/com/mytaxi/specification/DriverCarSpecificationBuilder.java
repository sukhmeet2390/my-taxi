package com.mytaxi.specification;

import com.mytaxi.domainobject.DriverCarDO;
import com.mytaxi.domainvalue.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
/**
 * <p>
 * Builder for encapsulating  the link between Specification and driverCar Search.
 * <p/>
 */
public class DriverCarSpecificationBuilder {
    private final List<SearchCriteria> params;

    public DriverCarSpecificationBuilder() {
        params = new ArrayList<>();
    }

    public DriverCarSpecificationBuilder with(final String key, final String operation,
                                              final String value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<DriverCarDO> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification<DriverCarDO>> specs = new ArrayList<>();
        for (SearchCriteria param : params) {
            specs.add(new DriverCarSpecification(param));
        }
        Specification<DriverCarDO> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }
        return result;
    }
}
