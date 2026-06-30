package org.kas.ws.be.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KasSummaryResponse {
    private Integer pemasukan;
    private Integer pengeluaran;
    private Integer saldo;
}
