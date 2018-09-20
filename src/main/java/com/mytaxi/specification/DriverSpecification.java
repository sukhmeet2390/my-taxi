package com.mytaxi.specification;

import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
/**
 * <p>
 * Specification to encapsulate the link between repository and driver Search.
 * <p/>
 */
public class DriverSpecification implements Specification<DriverDO> {
    private final SearchCriteria criteria;

    public DriverSpecification(SearchCriteria criteria) {
        super();
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<DriverDO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
        final String operation = criteria.getOperation();
        final String key = criteria.getKey();
        final String value = criteria.getValue();
        switch (operation) {
            case ":":
                if (root.get(key).getJavaType() == String.class) {
                    return builder.like(root.get(key), "%" + value + "%");
                } else {
                    return builder.equal(root.get(key), value);
                }
            default:
                return null;
        }
    }
}
