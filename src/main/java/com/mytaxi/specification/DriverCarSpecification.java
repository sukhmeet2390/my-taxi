package com.mytaxi.specification;

import com.mytaxi.domainobject.DriverCarDO;
import com.mytaxi.domainvalue.SearchCriteria;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * <p>
 * Specification to encapsulate the link between repository and driverCar Search.
 * <p/>
 */
@Getter
public class DriverCarSpecification implements Specification<DriverCarDO> {
    private final SearchCriteria criteria;

    public DriverCarSpecification(SearchCriteria criteria) {
        super();
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<DriverCarDO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
        final String operation = criteria.getOperation();
        final String key = criteria.getKey();
        final String value = criteria.getValue();
        final Join<Object, Object> carJoin = root.join("carDO", JoinType.LEFT);
        switch (operation) {
            case ">":
                if (key.contains("username") || key.contains("onlineStatus")) {
                    return builder.greaterThanOrEqualTo(root.join("driverDO").get(key), value);
                } else {
                    return builder.greaterThanOrEqualTo(carJoin.get(key), value);
                }
            case "<":
                if (key.contains("username") || key.contains("onlineStatus")) {
                    return builder.lessThanOrEqualTo(root.join("driverDO").get(key), value);
                } else {
                    return builder.lessThanOrEqualTo(carJoin.get(key), value);
                }

            case ":":
                if (key.contains("username") || key.contains("onlineStatus")) {
                    if (root.join("driverDO").get(key).getJavaType() == String.class) {
                        return builder.like(root.join("driverDO").get(key), "%" + value + "%");
                    } else {
                        return builder.equal(root.join("driverDO").get(key), value);
                    }
                } else {
                    if (carJoin.get(key).getJavaType() == String.class) {
                        return builder.like(carJoin.get(key), "%" + value + "%");
                    } else {
                        return builder.equal(carJoin.get(key), value);
                    }
                }
            default:
                return null;
        }
    }
}
