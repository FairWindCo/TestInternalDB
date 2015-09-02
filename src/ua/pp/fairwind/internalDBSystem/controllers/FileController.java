package ua.pp.fairwind.internalDBSystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.pp.fairwind.internalDBSystem.datamodel.Files;
import ua.pp.fairwind.internalDBSystem.services.repository.FileRepository;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Сергей on 02.09.2015.
 */
@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    FileRepository fileService;

    @RequestMapping(value = "/view", method = {RequestMethod.POST,RequestMethod.GET})
    public void doDownload(@RequestParam Long fileID,HttpServletRequest request,HttpServletResponse response) throws IOException {
        if(fileID!=null) {
            Files fileConten=fileService.getOne(fileID);
            if(fileConten!=null) {
                //response.setContentType(mimeType);
                //ServletContext context = request.getServletContext();
                byte[] data=fileConten.getFileData();
                if(data!=null) {
                    response.setContentType(fileConten.getFileMimeType());
                    response.setContentLength((int) data.length);
                    // get output stream of the response
                    OutputStream outStream = response.getOutputStream();
                    outStream.write(data);
                    outStream.close();
                } else {
                    response.setContentLength((int) 0);
                }
            } else {
                response.setContentLength((int) 0);
            }
        }

    }


    @RequestMapping(value = "/info", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Files info(@RequestParam Long fileID){
        if(fileID!=null) {
            Files fileConten=fileService.getOne(fileID);
            return fileConten;
        }
        return null;
    }
}
