package org.zerock.tp4.common.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class PageRequestDTO {
    //모든 게시판에서 사용할 예정

    @Builder.Default
    private int page = 1;//책에 pageNum과 같음
    @Builder.Default
    private int size = 10;//한페이지당 몇개? 책에 amount와 같음
    //int로 선언한 이유
    //int는 무조건 값을 가진다.
    //무조건 값을 우선 셋팅해놓기위해 builder.default 설정

    private String type;
    private String keyword;

    public int getSkip() {return (page -1 ) * size;}

    public String getType(){//타입으로 생기는 문제를 거럴내기위함

        if(type == null ||type.trim().length() ==0 ){//타입이 null 이거나, type trim한 길이가 0이면,
            return null;//null로 반환.
        }
        return type;//그게아니면 타입반환. 더 엄격히하자면, 타입이 T/W/C가 아니라면 으로 처리 가능.
    }


    public String[] getArr(){
        return type == null? new String[]{}:type.split("");
    }

//
//    public int getSkip(){
//        return (page -1 ) * size ;
//    }
}



