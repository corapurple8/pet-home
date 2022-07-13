package cn.itsource.pethome.basic.controller;

import cn.itsource.pethome.config.CrossAndRest;
import cn.itsource.pethome.constant.Constant;
import cn.itsource.pethome.util.AjaxResult;
import cn.itsource.pethome.util.FastDfsUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/fastdfs")
public class FastDfsController {

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/uploadFile")// 文件上传只能post请求 requestpart接收复杂请求 文件接收默认用file
    public AjaxResult upload(MultipartFile file){
        try {
            //System.out.println(file.getOriginalFilename() + ":" + file.getSize());
            //获取文件名
            String originalFilename = file.getOriginalFilename();
            //获取文件后缀
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
            //获取上传后文件路径
            String filePath =  FastDfsUtil.upload(file.getBytes(), extName);
            //返回
            return AjaxResult.me().setData(Constant.FASTDFS_HOST+filePath).setMsg("上传成功"); //把上传后的路径返回回去
        } catch (IOException e) {
            e.printStackTrace();
            //上传失败
            return AjaxResult.me().setSuccess(false).setMsg("上传失败!"+e.getMessage());
        }
    }

    /**
     * 参数：完整路径 /goup1/xxxxx/yyyy
     * 返回值：成功与否，还要返回地址
     * 删除
     * 前端请求地址：GET  地址?参数名称=值
     * 后端：@GetMapping("/地址")
     *      方法名称(String 参数名称)
     *
     * 前端请求地址：GET  地址/值
     * 后端：@GetMapping("/地址/{变量}")
     *      方法名称(@PathVariable("路径变量") String 参数名称)
     *
     *
     */
    @GetMapping("/delete")
    public AjaxResult del(String path){
        String pathTmp = path.substring(1); // goup1/xxxxx/yyyy
        String groupName =  pathTmp.substring(0, pathTmp.indexOf("/")); //goup1
        String remotePath = pathTmp.substring(pathTmp.indexOf("/")+1);// /xxxxx/yyyy
        System.out.println(groupName);
        System.out.println(remotePath);
        FastDfsUtil.delete(groupName, remotePath);
        return  AjaxResult.me();
    }
}