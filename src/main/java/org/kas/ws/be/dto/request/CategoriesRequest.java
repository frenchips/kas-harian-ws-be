package org.kas.ws.be.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriesRequest {
    private String categoriesName;
    private String type;
}
