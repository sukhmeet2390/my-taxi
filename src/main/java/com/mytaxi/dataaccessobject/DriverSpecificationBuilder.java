package com.mytaxi.dataaccessobject;

import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.util.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.jpa.domain.Specifications.where;

public class DriverSpecificationBuilder {
    private final List<SearchCriteria> params;

    public DriverSpecificationBuilder() {
        params = new ArrayList<>();
    }

    public DriverSpecificationBuilder with(final String key, final String operation,
                                           final String value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<DriverDO> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification<DriverDO>> specs = new ArrayList<>();
        for (SearchCriteria param : params) {
            specs.add(new DriverSpecification(param));
        }

        Specification<DriverDO> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = where(result).and(specs.get(i));
        }
        return result;
    }
}
