package org.zerock.tp4.board.dto;

import lombok.Getter;
import lombok.ToString;
import org.zerock.tp4.board.domain.Criteria;

@Getter
@ToString
public class PageDTO {

    private int startPage;
    private int endPage;
    private boolean prev, next;

    private int total;
    private Criteria cri;

    public PageDTO(Criteria cri, int total){

        this.cri = cri;
        this.total = total;

        this.endPage = (int)(Math.ceil(cri.getPageNum()/10.0)) * 10;

        this.startPage = this.endPage - 9;

        int realEnd = (int) (Math.ceil(total*1.0)/cri.get)

    }

}
