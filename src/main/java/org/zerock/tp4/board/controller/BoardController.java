package org.zerock.tp4.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.tp4.board.domain.Criteria;
import org.zerock.tp4.board.dto.BoardDTO;
import org.zerock.tp4.board.service.BoardService;
import org.zerock.tp4.board.service.TimeService;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final TimeService timeService;

    private final BoardService boardService;


    //get 으로 들어오면 입력하는화면
    //post로 들어오면 실제 입력 처리


    @GetMapping("/time")
    public void getTime(Model model){
        log.info("....");
        model.addAttribute("time", timeService.getNow());
    }


    @GetMapping("register")
    public void registerGet(){

    }
    //자동으로 해당하는 url의 jsp를 찾아감

    @PostMapping("/register")
    public String registerPost(BoardDTO boardDTO, RedirectAttributes redirectAttributes){

        log.info("boardDTOM" + boardDTO);

        Long bno = boardService.register(boardDTO);

        log.info("===== c registerpost====");
        log.info(bno);

        redirectAttributes.addFlashAttribute("result",bno);
        //리다이렉트할때 일회성으로 값을 끄집어 내서 쓸 수 있다!!!!! 한번 ! 저번에 hello에서 값 한번 띄워낸것처럼.
        return "redirect:/board/list";

    }
//
//    @GetMapping("/list")
//    public void getList(Model model){
//        //이제 jsp로 담아서 보내야 하기 때문에 model필요!
//
//        log.info("c getList......");
//        //c찍어놓으면 controller에서 나오는 로그, s로찍으면 service에서 나오는 로그!
//
//        model.addAttribute("dtoList", boardService.getDTOList());
//        //이렇게 하고나면 이제 board/list/jsp로 가는것이다.
//    }


    @GetMapping("/list")
    public void list(Criteria cri, Model model){
        log.info("list:" +cri);
        model.addAttribute("list", boardService.getDTOList(cri));
    }


    @GetMapping(value = {"/read", "/modify"})//read로 들어오면 read.jsp, modify로 들어오면 modify.jsp 2개 경로로 가능
    public void read(Long bno, Model model){
        log.info("c read" + bno);
        model.addAttribute("boardDTO", boardService.read(bno));
    }

    @PostMapping("/remove")
    public String remove(Long bno,RedirectAttributes redirectAttributes){
        log.info("c    remove:" +bno);

        if(boardService.remove(bno)){
            log.info("remove success");
            redirectAttributes.addFlashAttribute("result","success");
        }//삭제 완료하면 모달 창 뜰 수 있게, result 값을 전달해주는것.
        return "redirect:/board/list";
    }

    @PostMapping("/modify")
    public String modify(BoardDTO boardDTO, RedirectAttributes redirectAttributes){

        log.info(" c  modify: " + boardDTO);

        if(boardService.modify(boardDTO)){
            redirectAttributes.addFlashAttribute("result","modified");
        }
        redirectAttributes.addAttribute("bno", boardDTO.getBno());
        return "redirect:/board/read";
    }

}
