package org.kas.ws.be.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginationResponse<T> {
    private List<T> listData;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
