package com.hlxd.microcloud.config;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hlxd.microcloud.entity.R;

/***
 * -全局异常捕获
 * @version 1.0
 * @author SmallOath
 * @date 2019年11月21日
 */
@Component
@ControllerAdvice
public class GlobalExceptionHandler {
	
    /***
     * -全局异常捕获
     * @param e
     * @return
     */
    @ExceptionHandler
    @ResponseBody
    public R<String> exceptionHandle(RuntimeException e){
    	R<String> r = new R<>();
    	r.setCode(R.SYSTEM_EXCEPTION);
    	r.setMsg(R.SYSTEM_EXCEPTION_MSG);
    	r.setData(e.getMessage());
        return r;
    }

}
