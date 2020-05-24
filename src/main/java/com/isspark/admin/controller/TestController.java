package com.isspark.admin.controller;


import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.isspark.admin.common.domain.Result;
import com.isspark.admin.common.excel.ExcelAutoWidthStrategy;
import com.isspark.admin.domain.entity.User;
import com.isspark.admin.domain.vo.request.UserReqVO;
import com.isspark.admin.domain.vo.response.UserRespVO;
import com.isspark.admin.service.UserService;
import com.isspark.admin.utils.FileTypeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xuzheng
 * @since 2020-04-09
 */
@RestController
@RequestMapping("/user")
@Api(value = "用户服务", tags = "用户服务")
@Slf4j
@Validated
public class TestController {

    @Autowired
    private UserService userService;

    private static String EXCEL_NAME = "用户列表";

    @GetMapping(value = "/all")
    @ApiOperation(value = "获取所有用户", notes = "获取所有用户", response = Result.class)
    public Result all() {
        try{
            List<User> result = userService.list();
            return Result.success(result);
        }catch (Exception e){
            log.error("获取所有用户异常", e);
            return Result.fail(Result.ERROR_CODE, "获取所有用户异常");
        }
    }

    @GetMapping(value = "/age/{age}")
    @ApiOperation(value = "根据年龄查找用户", notes = "根据年龄查找用户", response = UserRespVO.class)
    public Result findByAge(@NotNull(message = "年龄不能为空") @PathVariable("age") Integer  age) {
        try{
            List<UserRespVO> result = userService.findByAge(age);
            return Result.success(result);
        }catch (Exception e){
            log.error("获取所有用户异常", e);
            return Result.fail(Result.ERROR_CODE, "获取所有用户异常");
        }
    }

    @PostMapping(value = "/find")
    @ApiOperation(value = "查找用户（分页）", notes = "查找用户（分页）", response = UserRespVO.class)
    public Result find(@RequestBody UserReqVO reqVO) {
        try{
            IPage<UserRespVO> result = userService.find(reqVO);
            return Result.success(result);
        }catch (Exception e){
            log.error("获取所有用户异常", e);
            return Result.fail(Result.ERROR_CODE, "获取所有用户异常");
        }
    }

    @GetMapping(value = "/exportExcel")
    @ApiOperation(value = "导出用户信息",notes = "导出用户信息")
    public void export(HttpServletResponse response){
        try{
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码
            String fileName = URLEncoder.encode(EXCEL_NAME + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN), "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName +".xlsx");
            EasyExcel.write(response.getOutputStream(),UserRespVO.class)
                    .registerWriteHandler(new ExcelAutoWidthStrategy())
                    .sheet(EXCEL_NAME)
                    .doWrite(userService.getAllUsers());
        }catch (Exception e){
            log.error("导出所有用户异常", e);
        }
    }


    @GetMapping(value = "/download")
    @ApiOperation(value = "下载文件",notes = "下载文件")
    public void download(HttpServletResponse response,@NotNull(message = "文件路径不能为空") @RequestParam("pathname") String pathname) throws FileNotFoundException {
        if(StringUtils.isBlank(pathname)){
            throw new FileNotFoundException("文件未找到！");
        }
        File file = new File(pathname);
        log.info("下载文件名：{}，类型：{}",file.getName(), FileTypeUtils.getFileByFile(file));
        FileInputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(file);
            response.setContentType("application/force-download");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode(file.getName(), "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + FileTypeUtils.getFileByFile(file));
            int len;
            byte[] buffer = new byte[1024];
            out = response.getOutputStream();
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null)
                    in.close();
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

