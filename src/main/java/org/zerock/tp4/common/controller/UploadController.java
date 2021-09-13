package org.zerock.tp4.common.controller;


import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.tp4.board.dto.UploadResponseDTO;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Log4j2
public class UploadController {

    @GetMapping("/sample/upload")
    public void uploadGet() {//내용은없고 화면을 보여주기위해 만든 컨트롤러

    }

    @ResponseBody
    @PostMapping("/removeFile")
    public ResponseEntity<String> removeFile(@RequestBody Map<String, String> data) throws Exception {
//제이슨형태 키 값을받으려고.. Map- 키,값...엑시오는 기본적으로 값을 전달할때 json형태로 전달하기때문이다.

        //현재 파일네임은 2021/09/08/이런식으로 들어옴
        File file = new File("C:\\upload"+File.separator+data.get("fileName"));
        //보낼때는 header메세지와 contentType이 달라져야한다.

        boolean checkImage = Files.probeContentType(file.toPath()).startsWith("image");

        file.delete();//삭제처리!
        //그리고 이제 썸네일 파일도 확인해서 썸네일도 삭제하기.

        if(checkImage){
            File thumbnail = new File(file.getParent(),"s_"+file.getName());//parent=폴더, 상위 파일의 이름.
                    log.info(thumbnail);
                    thumbnail.delete();
        }
            return ResponseEntity.ok().body("deleted");
    }



    @GetMapping("/downFile")
    public ResponseEntity<byte[]> download(@RequestParam("file") String fileName) throws Exception {

        File file = new File("C:\\upload"+File.separator+fileName);
        //보낼때는 header메세지와 contentType이 달라져야한다.

        String originalFileName = fileName.substring(fileName.indexOf("_") +1);//이제 파일 이름에서 _ 이하에 있는것 따로 잘라내기,

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/octet-stream");//다운 받을때 컨텐트타입은 이걸로 해
        headers.add("Content-Disposition","attachment; filename="
                +new String(originalFileName.getBytes(StandardCharsets.UTF_8),"ISO-8859-1")); //대소문자조심,,
        byte[] data = FileCopyUtils.copyToByteArray(file);//바이트타입으로 받아주기
        return ResponseEntity.ok().headers(headers).body(data);//다운로드 할거니까 결과데이터~

    }


    @GetMapping("/viewFile")
    @ResponseBody
    public ResponseEntity<byte[]> viewFile(@RequestParam("file") String fileName) throws Exception {//보낼땐 file로, 받을떈 fileName이라는 변수로받기위해 사용
    //viewFile = 이미지를 조회하는 코드를 작성한것!

        //C:\\upload\\2021\\09/08/cat.jpg 슬래시가 jdk6버전이상부터느상관없어짐 섞어쓸수있어짐,
        File file = new File("C:\\upload"+File.separator+fileName);

        log.info(file);

        ResponseEntity<byte[]> result = null;

        byte[] data = FileCopyUtils.copyToByteArray(file);//파일을 바이트로

        //mimeType을 알아야 응답 메세지를 보내줄수있다.
        String mimeType = Files.probeContentType(file.toPath());//적합한 mimeType을 가져올수있다.

        log.info("mimeType:"+mimeType);

        result = ResponseEntity.ok().header("Content-Type",mimeType).body(data);//ok = 200응답

        return result;//컨텐츠타입이 매번 달라진다.image/gif image/jpg 등등->header메시지 조작이필요해짐짐

    }


    @ResponseBody //restController처럼 리턴값이 제이슨으로 처리된다.
    @PostMapping("/upload")
    public List<UploadResponseDTO> uploadPost(MultipartFile[] uploadFiles) {//여러개 받을 수 있으니 배열,
        //uploadFiles 라고 지정, ! 아까jsp에서 받은 파라미터값을 받아야함
        log.info("----");


        if (uploadFiles != null && uploadFiles.length > 0) {

            List<UploadResponseDTO> uploadedList = new ArrayList<>();//uploadProcess에서 나온 결과가 uploadedList임.

            for (MultipartFile multipartFile : uploadFiles) {
                try {
                    uploadedList.add(uploadProcess(multipartFile));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return uploadedList;

        }
        return null;
    }

    private UploadResponseDTO uploadProcess(MultipartFile multipartFile) throws Exception{

        String uploadPath = "C:\\upload";//mac

        String folderName = makeFolder(uploadPath);
        String fileName=multipartFile.getOriginalFilename();
        String uuid= UUID.randomUUID().toString();//UUID 랜덤으로 ID를 만들어서 주는것. 중복되지않도록.
        String originalFileName = fileName;

        fileName = uuid+"_"+fileName;

        File savedFile = new File(uploadPath+File.separator+folderName, fileName);//중간에 있는 슬래시 File.separator

        FileCopyUtils.copy(multipartFile.getBytes(), savedFile);

        //thumbnail처리
        String mimeType = multipartFile.getContentType();
        boolean checkImage = mimeType.startsWith("image");
        if(checkImage){
            File thumbnailFile = new File(uploadPath+File.separator+folderName, "s_"+fileName);
            Thumbnailator.createThumbnail(savedFile, thumbnailFile, 100,100);
        }

        return UploadResponseDTO.builder()
                .uuid(uuid)
                .uploadPath(folderName.replace(File.separator,"/"))
                .fileName(originalFileName)
                .image(checkImage)
                .build();

    }

    private String makeFolder(String uploadPath){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String str = simpleDateFormat.format(date);//오늘 날짜의 문자열이 만들어짐 ex)2021-09-07
        String folderName = str.replace("-", File.separator);//win \\mac /
        //이폴더가 있으면만들고 없으면x
        File uploadFolder = new File(uploadPath, folderName);
        if(uploadFolder.exists() == false){//만약에 없었으면,
            uploadFolder.mkdirs();
        }
        return folderName;
    }

}
