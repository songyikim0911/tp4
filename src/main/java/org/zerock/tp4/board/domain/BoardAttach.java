package org.zerock.tp4.board.domain;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class BoardAttach {

    private String uuid;
    private Long bno;
    private String fileName;
    private String path;
    private boolean image;
//문제 mysql에 bno를 AutoIncrement걸어놔서, 게시물이 등록될떄까지 번호를알수없지만,,,
    //우리가 번호를 따논게 있으니 그걸 연결예정!

    public void setBno(Long bno){
        this.bno = bno;
    }

}
