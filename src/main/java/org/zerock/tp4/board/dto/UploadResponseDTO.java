package org.zerock.tp4.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadResponseDTO {

    private String uuid;
    private String fileName;
    private boolean image;
    private String uploadPath;

    //메소드 정의
    public String getThumbnail() {//썸네일 링크

        return uploadPath + "/s_" + uuid + "_" + fileName;
        //메소드를 만든 후 생기는일?->upload를하면 이와같은화면,
        //get메서드로 만들어주면 제이슨으로 처리가 가능하다..
        //이제 jsp변수에 추가해주기 thumbnail변수를.
    }

    public String getFileLink(){
        return uploadPath+"/"+uuid+"_"+fileName;
    }//원본이미지

}