<%--
  Created by IntelliJ IDEA.
  User: ksi64
  Date: 2021-09-07
  Time: 오후 2:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<input type ="file" name="uploadFiles" multiple><button id="uploadBtn">UPLOAD</button>

<div class="uploadResult">

</div>

<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<script>

    const uploadResultDiv = document.querySelector(".uploadResult")

    document.querySelector("#uploadBtn").addEventListener("click",(e)=>{

        const formData = new FormData()
       const fileInput = document.querySelector("input[name='uploadFiles']")//input[name='uploadFiles']제이쿼리..

        for(let i = 0; i< fileInput.files.length;i++){
            formData.append("uploadFiles", fileInput.files[i])//중요! uploadFiles는 파라미터이름, 컨트롤러에서 받는 이름임.
        }

        console.dir(formData)
        //console.log(fileInput)
        //console.dir(fileInput)//속성,값이 더 잘 나옴.확인가능
        console.log(formData)

        const headerObj = { headers: { 'Content-Type' : 'multipart/form-data'}}

        axios.post("/upload", formData, headerObj).then((response)=>{//데이터가 제이슨 키:값으로전달.
            const arr = response.data //배열 데이터이므로, 배열로 넣어준 것
            console.log(arr)
            let str=""
           //배열이기때문에, 루프를 돌 필요가 있다.
            for(let i = 0; i < arr.length; i++){
                const {uuid, fileName, uploadPath, image, thumbnail, fileLink} = {...arr[i]}//스프레드 연산자 이걸로 이제 div만들어줄 예정
                //각각의 데이터가 파일업로드의 결과. json데이터의 하나하나임, (uuid,fileName,image,uploadPath값이들어있는!)
                if(image){//이미지인 경우
                    //보안필요, 접근할 수 있는사람만 접근하도록 하기. 그리고 resources-img에 값을넣어도 안보일수있고
                    //우리가 값을넣는것은 war에 묶어서 값을 넣어서 쏘기때문에
                    //1) 이미지 서버를 따로 만드는 방식, 웹서버가 이미지 경로처리
                    //2) 클라우드서버가 업로드 경로..
                    str +=`<div data-uuid='\${uuid}'><img src='/viewFile?file=\${thumbnail}'/><span>\${fileName}</span>
                           <button onclick="javascript:removeFile('\${fileLink}',this)">x</button></div>`//기존문자열에 더해주기
                }else {//이미지가 아닌 경우: 즉 일반파일
                    str += `<div data-uuid='\${uuid}'><a href='/downFile?file=\${fileLink}'>\${fileName}</a></div>`
                }
            //
            // else{
            //         str +=`<div data-uuid='\${uuid}'><a href='/downFile?file=\${fileLink}'>\${fileName}</a></div>`//기존문자열에 더해주기
            //     }


            }//end for
           uploadResultDiv.innerHTML +=str //완성된 문자열을 넣어주기, 업로드가 여러번 이루어질 수 있기떄문에
            //기존값을 유지하면서 값을 넣어야함!
        });

    },false)

    function removeFile(fileLink,ele){
        console.log(fileLink)
        axios.post("/removeFile", {fileName:fileLink}).then(response =>{
            const targetDiv = ele.parentElement
            targetDiv.remove()//삭제 처리

        })
    }

</script>
</body>
</html>
