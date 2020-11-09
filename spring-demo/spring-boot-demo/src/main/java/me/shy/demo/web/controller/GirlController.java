/**
 * @Since: 2019-12-07 14:59:02
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 * @LastTime: 2019-12-07 22:17:55
 */
package me.shy.demo.web.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.shy.demo.web.domain.Girl;
import me.shy.demo.web.response.ResponseEnum;
import me.shy.demo.web.response.ResponseWarpper;
import me.shy.demo.web.service.GirlService;
import me.shy.demo.web.util.ResponseUtil;

@RestController @RequestMapping(value = "/girls") public class GirlController {

    @Autowired private GirlService girlService;

    // insert
    @PostMapping public ResponseWarpper add(@Valid Girl girl) throws Exception {
        this.girlService.save(girl);
        return ResponseUtil.success(ResponseEnum.SECCESS.getCode(), girl);

    }

    // delete
    @DeleteMapping("{id}") public String remove(@PathVariable("id") Long id) {
        this.girlService.forgetById(id);
        return "ok";
    }

    // update
    @PutMapping("{id}") public ResponseWarpper modify(@Valid Girl girl) throws Exception {
        this.girlService.save(girl);
        return ResponseUtil.success(ResponseEnum.SECCESS.getCode(), girl);
    }

    // select one
    @GetMapping("{id}") public Optional<Girl> takeOne(@PathVariable("id") Long id) {
        return this.girlService.takeById(id);
    }

    // select all
    @GetMapping public List<Girl> takeAll() {
        return this.girlService.takeAll();
    }

    // select by age
    @GetMapping("age/{age}") public List<Girl> takeByAge(@PathVariable("age") int age) {
        return this.girlService.takeByAge(age);
    }
}
