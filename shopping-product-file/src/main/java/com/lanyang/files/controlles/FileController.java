package com.lanyang.files.controlles;

import com.lanyang.files.domains.File;
import com.lanyang.files.services.FileService;
import com.shopping.utils.MD5Util;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

/**
 * Created by lany on 2019/4/3.
 */
@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/")
public class FileController {

    @Value(value = "${server.address:localhost}")
    private String serverAddress;

    @Value(value = "${server.port:80}")
    private String serverPort;

    @Autowired
    private FileService fileService;

    @GetMapping(value = "/")
    public String index(Model model){
        Page<File> filePages = fileService.listFilePagesByPage(0,3);
        model.addAttribute("files",filePages.getContent());
        model.addAttribute("indexPage",1);
        model.addAttribute("totalPage",filePages.getTotalPages());
        model.addAttribute("totalSize",filePages.getTotalElements());
        return "index";
    }

    @GetMapping(value = "/page/{page}")
    public String pages(Model model,@PathVariable int page){
        Page<File> filePages;
        if(0 >= page-1){
            filePages = fileService.listFilePagesByPage(0,3);
        }else
            filePages = fileService.listFilePagesByPage(page-1,3);

        model.addAttribute("files",filePages.getContent());
        model.addAttribute("indexPage",page);
        model.addAttribute("totalPage",filePages.getTotalPages());
        model.addAttribute("totalSize",filePages.getTotalElements());
        return "index";
    }

    @GetMapping(value = "/view/{id}")
    @ResponseBody
    public ResponseEntity<Object> viewFileOnLine(@PathVariable String id){

        File file = fileService.getFileById(id);
        if(null == file){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File is not found");
        }

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "fileName=\"" + file.getName() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, file.getContentType() )
                .header(HttpHeaders.CONTENT_LENGTH, file.getSize()+"")
                .header("Connection",  "close")
                .body( file.getContent());
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        try {
            File f = new File(file.getOriginalFilename(),  file.getContentType(), file.getSize(), file.getBytes());
            f.setMd5( MD5Util.getMD5(file.getInputStream()) );
            fileService.saveFile(f);
        } catch (IOException ex) {
            ex.printStackTrace();
            redirectAttributes.addFlashAttribute("message",
                    "Your " + file.getOriginalFilename() + " is wrong!");
            return "redirect:/";
        }

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }

    //download file
    @GetMapping("files/{id}")
    @ResponseBody
    public ResponseEntity<Object> serveFile(@PathVariable String id) {

        File file = fileService.getFileById(id);

        if (file != null) {
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + file.getName() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream" )
                    .header(HttpHeaders.CONTENT_LENGTH, file.getSize()+"")
                    .header("Connection",  "close")
                    .body( file.getContent());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not fount");
        }

    }

    @PostMapping(value = "/uploadFile")
    @ResponseBody
    public ResponseEntity<Object> handleFileUpload(@RequestParam(value = "inputFile") MultipartFile inputFile){

        if(null == inputFile){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File not found");
        }
        File file_return = null;
        try {
            File f = new File(inputFile.getOriginalFilename(),inputFile.getContentType(),inputFile.getSize(),inputFile.getBytes());

            f.setMd5(MD5Util.getMD5(inputFile.getInputStream()));
            file_return = fileService.saveFile(f);//save file
            String path = "http://"+ serverAddress +":"+ serverPort + "/view/" + file_return.getId();
            file_return.setPath(path);
            fileService.saveFile(f);//update file's path
            JSONObject obj_return = new JSONObject();
            obj_return.put("file_return",file_return);
            return ResponseEntity.ok(obj_return.toString());

        } catch (Exception  e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/deleteFile/{id}")
    @ResponseBody
    public ResponseEntity<Object> deleteFile(@PathVariable String id){
        try {
            fileService.removeFile(id);
            return ResponseEntity.ok("delete success");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }



}
