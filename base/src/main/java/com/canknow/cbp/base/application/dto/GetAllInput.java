package com.canknow.cbp.base.application.dto;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@Data
public class GetAllInput implements ISortedResultRequest {
    private String sort;

    public Sort buildSort () {
        if (sort == null) {
            return Sort.unsorted();
        }
        return Sort.by(this.parseOrders());
    }

    private List<Sort.Order> parseOrders() {
        List<Sort.Order> orders = new ArrayList<>();
        String[] orderStrings = sort.split("\\;");

        for (String orderString : orderStrings) {
            String[] order = orderString.split("\\,");

            if (order.length != 2) {
                continue;
            }
            Sort.Direction direction;

            if (order[1].contains("desc")) {
                direction = Sort.Direction.DESC;
            }
            else if (order[1].contains("asc")) {
                direction = Sort.Direction.ASC;
            }
            else {
                continue;
            }
            if (StringUtils.isBlank(order[0])) {
                continue;
            }
            orders.add(new Sort.Order(direction, order[0]));
        }
        return orders;
    }
}
